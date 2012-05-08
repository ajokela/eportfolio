/* $Name:  $ */
/* $Id: elementViewPanel.js,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
/**
 * 
 */
EPF.widget.ElementViewPanel = Class.create( {
    initialize : function(options) {
        this.options = options;
        this.window = options.window;
        this.entryKeyId = options.entryKeyId;
        this.attachedTo = options.attachedTo;
        this.parentEl = options.parentEl;
        this.onRender = options.onRender;
        this.entry = new EPF.model.Entry(options.entryKeyId, function() {
            try{
            this.render();
            if (this.onRender) { this.onRender(this.entry); }
            } catch(e){EPF.log(e);}
        }.bind(this));
        
        document.observe('epf:element.deleted', function(event){
            if (this.entry.entryKeyId == event.memo.entryKeyId) {
                this.window.close();
            }
        }.bind(this));
    },

    render : function() {
        var data = this.entry.data;
        var templateData = {entry:data.entry, readOnly:data.readOnly};
        if (this.attachedTo) {
            templateData.attachedToEntry = this.attachedTo.data.entry;
        }
        
        this.parentEl.update(EPF.template.elementwindow.viewElement(templateData));
        
        var backLink = this.parentEl.down('.attachedTo a');
        if (backLink) {
            backLink.observe('click', function(event){
                event.stop();
                this.window.backFromAttachment();
            }.bind(this));
        }
        
        this.options.entry = this.entry;
        
        new EPF.widget.ElementViewToolbar(this.options);
        new EPF.widget.TagsPanel(this.options);
        new EPF.widget.AttachmentsPanel(this.options);
        
        this.window.setFluidElement(this.parentEl.down('.fluid'));
    }
});


EPF.widget.ElementViewToolbar = Class.create( { 
    initialize : function(options) {
        this.window = options.window;
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        
        var editLink = this.parentEl.down('.toolbar .edit a');
        var deleteLink = this.parentEl.down('.toolbar .delete a');
        var tagLink = this.parentEl.down('.toolbar .tag button');
        var tagMenuOverlay = this.parentEl.down('.toolbar .tag .yui-overlay');
        var attachLink = this.parentEl.down('.toolbar .attach button');
        var printLink = this.parentEl.down('.toolbar .print a');
    
        if (editLink) {
            new YAHOO.widget.Button(editLink).on("click", this.onEditClick.bind(this));
        }
        if (deleteLink) {
            new YAHOO.widget.Button(deleteLink).on("click", this.onDeleteClick.bind(this));
        }
        if (tagLink) {
            var tagButton = new YAHOO.widget.Button(tagLink, { type: "menu", menu: tagMenuOverlay }); 
            new EPF.widget.AddTagsPanel(options, tagButton);
        }
        if (attachLink) {
            var attachButton = new YAHOO.widget.Button(attachLink, {
                type: 'menu',
                menu: this.createAttachMenuItems()
            }); 
        }
        if (printLink) {
            new YAHOO.widget.Button(printLink).on("click", this.onPrintClick.bind(this));
        }
    },
    
    createAttachMenuItems : function() {
        var id = new Date().getTime();
        return [
             { 
                 text: "File", classname: 'file',
                 submenu: {
                     id:'filesub' + id,
                     itemdata:[
                         { text: "New", classname: 'new', onclick: { fn: this.window.showAttachNewFile.bind(this.window)}},
                         { text: "Existing", classname: 'existing', onclick: { fn: this.window.showAttachFiles.bind(this.window) }}
                     ]
                 }
             },
             { 
                 text: "Link", classname: 'link', 
                 submenu: {
                     id:'linksub' + id,
                     itemdata:[
                         { text: "New", classname: 'new', onclick: { fn: this.window.showAttachNewLink.bind(this.window) }},
                         { text: "Existing", classname: 'existing', onclick: { fn: this.window.showAttachLinks.bind(this.window) }}
                     ]
                 }
             },
             { 
                 text: "Photo", classname: 'photo', 
                 submenu: {
                     id:'photosub' + id,
                     itemdata:[
                         { text: "New", classname: 'new', onclick: { fn: this.window.showAttachNewPhoto.bind(this.window) }},
                         { text: "Existing", classname: 'existing', onclick: { fn: this.window.showAttachPhotos.bind(this.window) }}
                    ]
                }
             }
         ]
    },

    onEditClick : function(oEvent) {
        YAHOO.util.Event.stopEvent(oEvent);
        this.window.showEdit();
    },
    
    onDeleteClick : function(oEvent) {
        YAHOO.util.Event.stopEvent(oEvent);
        new dialog.Confirm( {
            messageText : 'Are you sure that you want to delete this element?',
            onConfirm : this.entry.deleteEntry.bind(this.entry)
        }).show();
    },

    onPrintClick : function(oEvent) {
        window.open('/element/print/?' + Object.toQueryString({entryKeyId: this.entry.entryKeyId}));
    }
});

/**
 * Tags panel for element view
 */
EPF.widget.AddTagsPanel = Class.create( {
    initialize : function(options, addTagsButton) {
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        
        this.addTagsButton = addTagsButton;
        
        this.addTagForm = this.parentEl.down('.addTagForm');
        this.addTagInput = this.parentEl.down('.addTagInput');

        if (this.addTagForm) {
            this.addTagForm.observe('submit', this.onAddSubmit.bind(this));
        }
    },

    onAddSubmit : function(event) {
        event.stop();
        var text = $F(this.addTagInput);
        if (text) {
            this.entry.addTag(text);
            this.addTagInput.value = '';
            this.addTagsButton.getMenu().hide();
            this.addTagsButton.blur();
        }
    }
});

/**
 * Photos panel for element view
 */
EPF.widget.AttachmentsPanel = Class.create( {
    initialize : function(options) {
        this.window = options.window;
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        this.attachmentHash = $H();

        this.parentEl.select('li.photo, li.file, li.link').each(function(e) {
            var attachmentId = e.down('.entryKeyId').innerHTML;
            this.attachmentHash.set(attachmentId, e);
            if (e.down('.deleteLink')) {
                e.down('.deleteLink').observe('click', function(event){
                    event.stop();
                    this.entry.detach(attachmentId);
                }.bind(this));
            }
            e.down('.gotoLink').observe('click', function(event){
                event.stop();
                this.window.showViewAttachment(attachmentId);
            }.bind(this));
        }.bind(this));
        
        if (this.attachmentHash.size() == 0) {
            if (this.parentEl.down('.attachmentsSection')) {
                this.parentEl.down('.attachmentsSection').hide();   
            }
        }
        
        document.observe('epf:element.attachment.removed', this.handleAttachmentRemoved.bind(this));
    },
    
    handleAttachmentRemoved : function(event) {
        if (event.memo.entryKeyId == this.entry.entryKeyId && this.attachmentHash.keys().include(event.memo.attachmentId)) {
            var el = this.attachmentHash.unset(event.memo.attachmentId);
            el.fade({afterFinish : function(effect) {
                effect.element.remove();
                if (this.attachmentHash.size() == 0) {
                    this.parentEl.down('.attachmentsSection').hide();
                }
            }.bind(this)});
        }
    }
});

/**
 * Tags panel for element view
 */
EPF.widget.TagsPanel = Class.create( {
    initialize : function(options) {
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        
        this.elementTagList = this.parentEl.down('.elementTagList');

        document.observe('epf:element.tag.added', this.handleTagAdded.bind(this));
        document.observe('epf:element.tag.removed', this.handleTagRemoved.bind(this));
        
        this.getTagElements().each(this.onTagLoaded.bind(this));

        if (this.getTagElements().size() == 0) {
            this.parentEl.down('.tagsSection').hide();
        }
    },
    
    handleTagAdded: function(event) {
        if (event.memo.entryKeyId == this.entry.entryKeyId) {
            if (this.getTagElements().size() == 0) {
                this.parentEl.down('.tagsSection').show();
            }
            
            this.addNewTagElement(event.memo.text);
        }
    },
    
    handleTagRemoved: function(event) {
        if (event.memo.entryKeyId == this.entry.entryKeyId) {
            var tagElement = this.getTagElements().find(function(li){ return event.memo.text == li.down('span').innerHTML });
            if (tagElement) {
                tagElement.fade({
                    afterFinish : function(effect) {
                        effect.element.remove();
                        if (this.getTagElements().size() == 0) {
                            this.parentEl.down('.tagsSection').hide();
                        }
                    }.bind(this)
                });
            }
        }
    },
    
    onTagLoaded : function(e) {
        var deleteLink = e.down('a');
        if (deleteLink) {
            deleteLink.observe('click', this.onDeleteClick.bindAsEventListener(this, e));
        }
    },
    
    addNewTagElement : function(text) {
        var newEl = Element.fromText(EPF.template.elementwindow.tag({tag:text, readOnly:false}));
        this.elementTagList.insert(newEl.show());
        this.onTagLoaded(newEl);
    },
    
    getTagElements : function() {
        return this.parentEl.select('.elementTagList > li');
    },
    
    onDeleteClick : function(event, li) {
        event.stop();
        var tagText = li.down('span').innerHTML;
        this.entry.removeTag(tagText);
    }
});


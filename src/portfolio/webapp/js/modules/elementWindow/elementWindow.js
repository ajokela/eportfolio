/* $Name:  $ */
/* $Id: elementWindow.js,v 1.10 2011/02/18 19:52:42 ajokela Exp $ */
EPF.namespace('widget');

EPF.widget.ElementWindow = Class.create(
    /** @lends EPF.widget.ElementWindow.prototype */
{
    /**
     * Represents the whole modal window. Dispatches to the different views.
     *
     * @constructs
     * @param options
     * @param {String} [options.entryKeyId] the entryKey of the element to view
     * @param {String} [options.elementDefId] the elementDefId of the elementDef to create an instance of
     * @param {function} [options.onCreate] a callback for when a new entry is created.
     */
    initialize : function(options) {
        this.modalElement = Element.fromText(EPF.template.elementwindow.window());
        this.headerEl = this.modalElement.down('.element_window_header');
        this.contentEl = this.modalElement.down('.element_window_content');
        var closeButton = this.modalElement.down('.element_window_close');
        closeButton.observe('click', this.close.bind(this));

        document.body.appendChild(this.modalElement);

        var draggable = new Draggable(this.modalElement, {
            handle: this.modalElement.down('.element_window_header')
        });

        var resize = new YAHOO.util.Resize(this.modalElement, {
            handles: ['br'],
            minWidth: 520,
            minHeight: 350,
            width: 520,
            height: 440
        });

        this.modalElement.center();
        this.modalElement.setStyle({ visibility : 'visible' });

        resize.on('resize', this.sizeFluidElement.bind(this));

        if (options.entryKeyId) {
            this.currentEntryKeyId = options.entryKeyId;
            this.showView();
        } else if (options.elementDefId) {
            this.headerEl.update('Untitled');
            this.showCreate(options.elementDefId, options.onCreate, options.callback);
        }
        
    },

    setTitle : function(title) {
        this.headerEl.update(title);
    },

    setLoading : function(isLoading) {
        if (isLoading) {
            this.modalElement.addClassName('loading');
            this.contentEl.update();
        } else {
            this.modalElement.removeClassName('loading');
        }
    },

    showView : function() {
        this.setLoading(true);
        new EPF.widget.ElementViewPanel({
            window: this,
            entryKeyId: this.currentEntryKeyId,
            parentEl: this.contentEl,
            attachedTo: this.attachedTo,
            onRender: function(entry) {
                this.currentEntry = entry;
                var data = entry.data;
                this.setTitle(data.entry.entryName);
                this.setLoading(false);
                this.sizeFluidElement();
            }.bind(this)
        });
    },

    showViewAttachment : function(attachmentEntryKeyId) {
        this.attachedTo = this.currentEntry;
        this.currentEntryKeyId = attachmentEntryKeyId;
        this.showView();
    },

    showCreate : function(elementDefId, onCreate, callback) {
        this.setLoading(true);
        new EPF.widget.ElementEditPanel({
            window: this,
            elementDefId: elementDefId,
            parentEl: this.contentEl,
            attachedTo: this.attachedTo,
            onCreate : onCreate,
            callback : callback,
            onRender:  function() {
                this.setLoading(false);
                this.sizeFluidElement();
            }.bind(this)
        });
    },

    showAttachNewLink : function() {
        this.attachedTo = this.currentEntry;
        this.showCreate('epf_link');
    },

    showAttachNewFile : function() {
        this.attachedTo = this.currentEntry;
        this.showCreate('020101');
    },

    showAttachNewPhoto : function() {
        this.attachedTo = this.currentEntry;
        this.showCreate('020101');
    },

    showEdit : function() {
        this.setLoading(true);
        new EPF.widget.ElementEditPanel({
            window: this,
            entry: this.currentEntry,
            parentEl: this.contentEl,
            attachedTo: this.attachedTo,
            onRender: function() {
                this.setLoading(false);
                this.sizeFluidElement();
            }.bind(this)
        });
    },

    showAttachLinks : function() {
        this.setLoading(true);
        new EPF.widget.AttachLinksPanel({
            window: this,
            entry: this.currentEntry,
            parentEl: this.contentEl,
            onRender: function() {
                this.setLoading(false);
                this.sizeFluidElement();
            }.bind(this)
        });
    },

    showAttachFiles : function() {
        this.setLoading(true);
        new EPF.widget.AttachFilesPanel({
            window: this,
            entry: this.currentEntry,
            parentEl: this.contentEl,
            onRender: function() {
                this.setLoading(false);
                this.sizeFluidElement();
            }.bind(this)
        });
    },

    showAttachPhotos : function() {
        this.setLoading(true);
        new EPF.widget.AttachPhotosPanel({
            window: this,
            entry: this.currentEntry,
            parentEl: this.contentEl,
            onRender: function() {
                this.setLoading(false);
                this.sizeFluidElement();
            }.bind(this)
        });
    },

    backFromAttachment : function() {
        this.currentEntryKeyId = this.attachedTo.entryKeyId;
        this.attachedTo = null;
        this.showView();
    },

    down : function(selector) {
        return this.modalElement.down(selector);
    },

    select : function(selector) {
        return this.modalElement.select(selector);
    },

    close : function() {
        this.modalElement.remove();
    },

    setFluidElement : function(el) {
        this.fluidElement = el;
    },

    resize: function() {
        this.sizeFluidElement();
    },

    /** @private */
    sizeFluidElement : function() {
        var headerHeight = this.headerEl.getHeight();
        var modalHeight = this.modalElement.clientHeight;
        var targetContentHeight = modalHeight - headerHeight;
        var actualContentHeight = this.contentEl.getHeight();
        var diff = (targetContentHeight - actualContentHeight);   // <-- super f'ing important.

        var fluidElementHeight = parseInt(this.fluidElement.getStyle('height'));
        var newFluidElementHeight = fluidElementHeight + diff;
        this.fluidElement.style.height = newFluidElementHeight + 'px';
    }
});

EPF.widget.AttachLinksPanel = Class.create(/** @lends EPF.widget.AttachLinksPanel.prototype */ {

    /**
     * Panel for attaching links
     *
     * @constructs
     * @param options
     * @param {EPF.widget.ElementWindow} options.window
     * @param {EPF.model.Entry} options.entry
     * @param {HTMLElement} options.parentEl
     * @param {Function} [onRender]
     */
    initialize : function(options) {
        this.window = options.window;
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        this.onRender = options.onRender;

        new Ajax.Request('/element/attach/link/list', {
            parameters : {
                entry : this.entry.entryKeyId
            },
            onSuccess : function(transport) {
                this.render(transport.responseText);
                if (this.onRender) {
                    this.onRender();
                }
            }.bind(this)
        });
    },

    render : function(html) {
        try {
            this.parentEl.update(html);
            this.parentEl.down('.attachLinksForm').observe('submit', function(event) {
                event.stop();
                var attachmentIds = this.parentEl.select('input[name=attachment]:checked').collect(function(e) {
                    return $F(e)
                });
                this.entry.attach(attachmentIds, this.onSaveSuccess.bind(this));
            }.bind(this));

            this.parentEl.down('.attachLinksCancel').observe('click', this.onCancel.bind(this));

            var searchInputElement = this.parentEl.down('.searchLinks .wrapper input[type=text]');
            searchInputElement.observe('focus', function(event) {
                this.previous().hide();
            });
            searchInputElement.observe('blur', function(event) {
                if ($F(this).length == 0) {
                    this.previous().show();
                }
            });

            new Form.Element.Observer(
                    searchInputElement,
                    0.2, // 200 milliseconds
                    function(el, value) {
                        if (value.length > 0) {
                            el.previous().hide();
                        }
                        this.searchEntries(value);
                    }.bind(this)
                    );

            this.parentEl.down('.searchLinks .wrapper img').observe('click', function(event) {
                searchInputElement.clear();
                searchInputElement.focus();
            });

            this.updateCount();

            this.window.setFluidElement(this.parentEl.down('.contents'));
        } catch(e) {
            EPF.log(e);
        }
    },

    searchEntries: function(query) {
        this.parentEl.select('.linkItem').each(function(e) {
            var entryName = e.down('.attachLinkName').innerHTML + ' ' + e.down('.attachLinkUrl').innerHTML;
            if (query.blank() || entryName.toLowerCase().indexOf(query.toLowerCase()) != -1) {
                e.show();
            } else {
                e.hide();
            }
        });
        this.updateCount();
    },

    updateCount: function() {
        var numEntries = this.parentEl.select('.linkItem').findAll(function(e) {
            return e.visible();
        }).size();
        var msg;
        if (numEntries == 0) {
            msg = 'No links';
        } else {
            msg = '1 - ' + numEntries + ' of ' + numEntries;
        }
        this.parentEl.down('.numLinks').update(msg);
    },

    onSaveSuccess : function(transport) {
        this.window.showView();
    },

    onCancel : function(event) {
        event.stop();
        this.window.showView();
    }
});

/**
 * Panel for attaching files
 */
EPF.widget.AttachFilesPanel = Class.create({
    initialize : function(options) {
        this.window = options.window;
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        this.onRender = options.onRender;

        new Ajax.Request('/element/attach/file/list', {
            parameters : {
                entry : this.entry.entryKeyId
            },
            onSuccess : function(transport) {
                this.render(transport.responseText);
                if (this.onRender) {
                    this.onRender();
                }
            }.bind(this)
        });
    },

    render : function(html) {
        try {
            this.parentEl.update(html);
            this.parentEl.down('.attachFilesForm').observe('submit', function(event) {
                event.stop();
                var attachmentIds = this.parentEl.select('input[name=attachment]:checked').collect(function(e) {
                    return $F(e)
                });
                this.entry.attach(attachmentIds, this.onSaveSuccess.bind(this));
            }.bind(this));

            this.parentEl.down('.attachFilesCancel').observe('click', this.onCancel.bind(this));

            var searchInputElement = this.parentEl.down('.searchFiles .wrapper input[type=text]');
            searchInputElement.observe('focus', function(event) {
                this.previous().hide();
            });
            searchInputElement.observe('blur', function(event) {
                if ($F(this).length == 0) {
                    this.previous().show();
                }
            });

            new Form.Element.Observer(
                    searchInputElement,
                    0.2, // 200 milliseconds
                    function(el, value) {
                        if (value.length > 0) {
                            el.previous().hide();
                        }
                        this.searchEntries(value);
                    }.bind(this)
                    );

            this.parentEl.down('.searchFiles .wrapper img').observe('click', function(event) {
                searchInputElement.clear();
                searchInputElement.focus();
            });

            this.updateCount();

            this.window.setFluidElement(this.parentEl.down('.contents'));
        } catch(e) {
            EPF.log(e);
        }
    },

    searchEntries: function(query) {
        this.parentEl.select('.fileItem').each(function(e) {
            var entryName = e.down('.attachFileName').innerHTML;
            if (query.blank() || entryName.toLowerCase().indexOf(query.toLowerCase()) != -1) {
                e.show();
            } else {
                e.hide();
            }
        });
        this.updateCount();
    },

    updateCount: function() {
        var numEntries = this.parentEl.select('.fileItem').findAll(function(e) {
            return e.visible();
        }).size();
        var msg;
        if (numEntries == 0) {
            msg = 'No files';
        } else {
            msg = '1 - ' + numEntries + ' of ' + numEntries;
        }
        this.parentEl.down('.numFiles').update(msg);
    },

    onSaveSuccess : function(transport) {
        this.window.showView();
    },

    onCancel : function(event) {
        event.stop();
        this.window.showView();
    }
});

/**
 * Panel for attaching photos
 */
EPF.widget.AttachPhotosPanel = Class.create({
    initialize : function(options) {
        this.window = options.window;
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        this.onRender = options.onRender;

        new Ajax.Request('/element/attach/photo/list', {
            parameters : {
                entry : this.entry.entryKeyId
            },
            onSuccess : function(transport) {
                this.render(transport.responseText);
                if (this.onRender) {
                    this.onRender();
                }
            }.bind(this)
        });
    },

    render : function(html) {
        this.parentEl.update(html);
        this.parentEl.down('.attachPhotosForm').observe('submit', function(event) {
            event.stop();
            var attachmentIds = this.parentEl.select('input[name=attachment]:checked').collect(function(e) {
                return $F(e)
            });
            this.entry.attach(attachmentIds, this.onSaveSuccess.bind(this));
        }.bind(this));

        this.parentEl.down('.attachPhotosCancel').observe('click', this.onCancel.bind(this));

        var searchInputElement = this.parentEl.down('.searchPhotos .wrapper input[type=text]');
        searchInputElement.observe('focus', function(event) {
            this.previous().hide();
        });
        searchInputElement.observe('blur', function(event) {
            if ($F(this).length == 0) {
                this.previous().show();
            }
        });

        new Form.Element.Observer(
                searchInputElement,
                0.2, // 200 milliseconds
                function(el, value) {
                    if (value.length > 0) {
                        el.previous().hide();
                    }
                    this.searchEntries(value);
                }.bind(this)
                );

        this.parentEl.down('.searchPhotos .wrapper img').observe('click', function(event) {
            searchInputElement.clear();
            searchInputElement.focus();
        });

        this.updateCount();

        this.window.setFluidElement(this.parentEl.down('.contents'));
    },

    searchEntries: function(query) {
        this.parentEl.select('.photoItem').each(function(e) {
            var entryName = e.down('.photoName span').innerHTML;
            if (query.blank() || entryName.toLowerCase().indexOf(query.toLowerCase()) != -1) {
                e.show();
            } else {
                e.hide();
            }
        });
        this.updateCount();
    },

    updateCount: function() {
        var numEntries = this.parentEl.select('.photoItem').findAll(function(e) {
            return e.visible();
        }).size();
        var msg;
        if (numEntries == 0) {
            msg = 'No photos';
        } else {
            msg = '1 - ' + numEntries + ' of ' + numEntries;
        }
        this.parentEl.down('.numPhotos').update(msg);
    },

    onSaveSuccess : function(transport) {
        this.window.showView();
    },

    onCancel : function(event) {
        event.stop();
        this.window.showView();
    }
});


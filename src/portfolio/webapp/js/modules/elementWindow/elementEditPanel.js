/* $Name:  $ */
/* $Id: elementEditPanel.js,v 1.12 2011/02/17 21:57:02 ajokela Exp $ */
EPF.widget.ElementEditPanel = Class.create( /** @lends EPF.widget.ElementEditPanel.prototype */
{

    /**
     * 
     * @constructs
     * @param {Object} options
     * @param options.window
     * @param {String} [options.elementDefId]
     * @param options.entry
     * @param {HTMLElement} options.parentEl
     * @param {Function} [options.onRender]
     * @param [options.attachedTo]
     * @param {Function} [options.onCreate]
     */
    initialize : function(options) {
        this.window = options.window;
        this.elementDefId = options.elementDefId;
        this.entry = options.entry;
        this.parentEl = options.parentEl;
        this.onRender = options.onRender;
        this.attachedTo = options.attachedTo;
        this.onCreate = options.onCreate;
        this.callback = options.callback;

        var url;
        if (this.elementDefId) {
            // we are creating a new entry
            this.hasBeenSaved = false;
            url = '/element/create/' + this.elementDefId;
        } else {
            this.hasBeenSaved = true;
            url = '/element/edit/' + this.entry.entryKeyId;
        }

        new Ajax.Request(url, {
            onSuccess : function(transport) {
                this.render(transport.responseText);
                if (this.onRender) {
                    this.onRender();
                }
            }.bind(this)
        });
    },

    render : function(responseText) {
        this.parentEl.update(responseText);

        if (this.parentEl.down('.uploadFile')) {
            this.onUploadFileLoaded();
        } else {
            this.setUpForm();
            this.parentEl.down('.elementEdit .cancelLink').observe('click', this.onCancelClick.bind(this));
            this.window.setFluidElement(this.parentEl.down('.contents'));
        }
    },

    onCancelClick : function(event) {
        event.stop();
        if (this.entry) {
            this.window.showView();
        } else {
            this.window.close();
        }
    },

    onUploadFileLoaded : function() {
        this.parentEl.down('.uploadCancelLink').observe('click', this.onCancelClick.bind(this));
        this.parentEl.down('.uploadActionButton').observe(
                'click',
                function(event) {
                    event.stop();
                    if (this.attachedTo) {
                        this.parentEl.down('.uploadFileForm').appendChild(new Element('input', {
                            type : 'hidden',
                            name : 'attachToEntryKeyId',
                            value : this.attachedTo.entryKeyId
                        }));
                    }
                    YAHOO.util.Connect.setForm(this.parentEl.down('.uploadFileForm'), true, true);
                    YAHOO.util.Connect.asyncRequest('POST', '/file/upload/', {
                        upload : function(o) {
                            var resp = eval("(" + o.responseText + ")");
                            if (resp.stat == 'ok') {
                                this.hasBeenSaved = true;
                                this.window.currentEntryKeyId = resp.data.entry.entryKeyId;
                                document.fire('epf:element.saved', {
                                    entry : resp.data.entry,
                                    entryKeyId : resp.data.entry.entryKeyId,
                                    isNew : true 
                                });
                                
                                if (this.callback != null) {
                                	this.callback();
                                }
                                
                                this.window.showView();
                            } else {
                                alert('The upload has failed. Please refresh the page and try again.');
                            }
                        }.bind(this)
                    });

                    this.parentEl.down('.uploadFileInstructions').update('Uploading...');
                    this.parentEl.down('.uploadFileButtons').hide();
                    this.parentEl.down('.uploadFileDetails').show();

                    var fileSelectInput = this.parentEl.down('.fileSelectInput');
                    this.parentEl.down('.uploadFileProgressName').update(fileSelectInput.value);
                    fileSelectInput.hide();

                    var pb = new YAHOO.widget.ProgressBar( {
                        value : 0,
                        width : '180px',
                        height : '20px'
                    }).render(this.parentEl.down('.uploadFileProgressBar'));
                    new PeriodicalExecuter(function(pe) {
                        new Ajax.Request('/file/upload/progress', {
                            method : 'get',
                            onSuccess : function(transport) {
                                var resp = transport.responseJSON;
                                if (resp.stat == 'ok') {
                                    if (resp.contentLength) {
                                        this.parentEl.down('.uploadFileProgressSize').update(
                                                resp.bytesRead + ' of ' + resp.contentLength);
                                    }
                                    if (resp.listenerAvailable) {
                                        if (resp.finished) {
                                            pe.stop();
                                        }
                                        pb.set('value', resp.percentComplete);
                                    }
                                } else {
                                    pe.stop();
                                    alert('The upload has failed. Please refresh the page and try again.');
                                }
                            }.bind(this)
                        });
                    }.bind(this), 1);
                }.bind(this));
    },

    setUpForm : function() {
        // create WYSIWYGs
        var textareas = this.parentEl.select('.editElementForm textarea:not(.plainText)');
        
        textareas.invoke('addClassName', 'mceEditor');
        tinyMCE.init({
          mode : "specific_textareas",
          editor_selector : "mceEditor",
          plugins : "paste,fullscreen",
          theme : "advanced",
          theme_advanced_toolbar_location : "top",
          theme_advanced_toolbar_align : "left",
          theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,pasteword,|,bullist,numlist,|,link,unlink,|,fullscreen",
          theme_advanced_buttons2 : "",
          theme_advanced_buttons3 : ""
        });
        
        this.parentEl.down('.saveButton').observe('click', this.onFormSubmitListener.bindAsEventListener(this, true));
       
        /*
        this.parentEl.down('.saveContinueButton').observe('click',
                this.onFormSubmitListener.bindAsEventListener(this, false));
         */
    },

    onFormSubmitListener : function(event, closeAfterSave) {
        event.stop();
        this.closeAfterSave = closeAfterSave;
        var theForm = this.parentEl.down('.editElementForm');
        tinyMCE.triggerSave();

        this.parentEl.select('.editElementForm ul.formErrors').invoke('remove');
        var params = theForm.serialize(true);

        if (this.attachedTo) {
            params['attachToEntryKeyId'] = this.attachedTo.entryKeyId;
        }

        new Ajax.Request('/element/save/', {
            parameters : params,
            onSuccess : this.onSaveSuccessListener.bind(this)
        });
    },

    onSaveSuccessListener : function(transport) {
        var rsp = transport.responseJSON;
        if (rsp.stat == 'ok') {
            this.entryKeyId = rsp.data.entry.entryKeyId;
            this.window.currentEntryKeyId = this.entryKeyId;
            
            var isNew = !this.hasBeenSaved

            if (isNew && this.onCreate) {
                this.onCreate(this.entryKeyId);
            }
            this.hasBeenSaved = true;

            document.fire('epf:element.saved', {
                entry: rsp.data.entry,
                entryKeyId : rsp.data.entry.entryKeyId,
                isNew : isNew 
            });
            
            if (this.closeAfterSave) {
                this.window.showView();
            } else {
                this.window.select('.elementEdit .lastUpdatedDate').invoke('update',
                        rsp.data.entry.modifiedDate.toString());
            }
        } else {
            var myList = new Element('ul', {
                'class' : 'formErrors'
            });
            rsp.errors.each(function(error) {
                error.messages.each(function(message) {
                    myList.appendChild(new Element('li').update(message));
                });
            });
            this.parentEl.down('.contents').insert( {
                top : myList
            });
            this.parentEl.down('.contents').scrollTop = 0;
        }
    }
});

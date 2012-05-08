/* $Name:  $ */
/* $Id: entry.js,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
EPF.namespace('model');

/**
 * 
 */
EPF.model.Entry = Class.create( {
    initialize : function(entryKeyId, callback) {
        this.entryKeyId = entryKeyId;
        
        new Ajax.Request('/element/data/' + this.entryKeyId, {
            onComplete : function(transport) {
                var rsp = transport.responseJSON;
                this.data = rsp.data;
                if (callback) {
                    callback();
                }
            }.bind(this)
        });
    },
    
    removeTag : function(tag, callback) {
        new Ajax.Request('/element/tag/remove', {
            parameters : {
                entryKeyId : this.entryKeyId,
                tag : tag
            },
            onComplete : function(transport){
                // TODO check stat
                document.fire('epf:element.tag.removed', {
                    entryKeyId : this.entryKeyId,
                    text : tag
                });
                if (callback) {
                    callback();
                }
            }.bind(this)
        });
    },
    
    addTag : function(tag, callback) {
        new Ajax.Request('/element/tag/add', {
            parameters : {
                entryKeyId : this.entryKeyId,
                tag : tag
            },
            onComplete : function(transport) {
                // TODO check stat
                document.fire('epf:element.tag.added', {
                    entryKeyId : this.entryKeyId,
                    text : tag
                });
                if (callback) {
                    callback();
                }
            }.bind(this)
        });
    },
    
    detach : function(attachmentId, callback) {
        new Ajax.Request('/element/detach', {
            parameters : {
                entry : this.entryKeyId,
                attachment : attachmentId
            },
            onComplete : function(transport) {
                // TODO check stat
                document.fire('epf:element.attachment.removed', {
                    entryKeyId : this.entryKeyId,
                    attachmentId : attachmentId
                });
                if (callback) {
                    callback();
                }
            }.bind(this)
        });
    },
    
    attach : function(attachmentIds, callback) {
        new Ajax.Request('/element/attach', {
            parameters : {
                attachment: attachmentIds,
                entry : this.entryKeyId
            },
            onComplete : function(transport) {
                // TODO check stat
                $A(attachmentIds).each(function(attachmentId){
                    document.fire('epf:element.attachment.added', {
                        entryKeyId : this.entryKeyId,
                        attachmentId : attachmentId
                    });
                });
                if (callback) {
                    callback();
                }
            }.bind(this)
        });
    },
    
    deleteEntry : function(callback) {
        new Ajax.Request('/element/delete/', {
            parameters : {
                entryKeyId : this.entryKeyId
            },
            onComplete : function(transport) {
                // TODO check stat
                var event = document.fire('epf:element.deleted', {
                    entryKeyId : this.entryKeyId
                });
                if (callback) {
                    callback();
                }
            }.bind(this)
        });
    }
});

/* $Name:  $ */
/* $Id: dialog.js,v 1.9 2011/02/16 17:39:21 rkavajec Exp $ */
var dialog = {}; // namespace

dialog.Confirm = Class.create( /** @lends dialog.Confirm.prototype */ {
    
    /** 
     * @constructs 
     * @param {Object} obj
     * @param {String} [obj.messageText]
     * @param {Function} [obj.onConfirm]
     * @param {String} [obj.confirmUrl]
     */
    initialize : function(obj) {
        var onConfirm = null;
    
        if (obj.confirmUrl) { // go to new url
            var oUrl = obj.confirmUrl;
            onConfirm = function() {
                window.location.href = oUrl;
            };
        } else {
        	if (obj.onConfirm) { // do something, usually delete
        		onConfirm = obj.onConfirm;
        	} else { // do nothing, close the sending window
        		onConfirm = function() {
        			window.close();
        		};
        	};
        }
        
        var modal = new EPF.widget.modal.Modal({
            title: 'Confirm',
            width: 450,
            content: EPF.template.dialog.confirm({
                messageText: obj.messageText || 'Are you sure?'
            })
        });

        modal.getContentParent().down('.buttons .ok').observe('click', function(event){
            onConfirm();
            modal.close();
        });
        
        modal.getContentParent().down('.buttons .cancel').observe('click', function(event){
            modal.close();
        });
    
    },

    /** @deprecated */
    show : function() {
    }
});

dialog.Alert = Class.create( /** @lends dialog.Alert.prototype */ {
    
    /** 
     * @constructs 
     * @param {String|Object} obj a message String or a config object.
     * @param {String} [obj.messageText]
     */
    initialize : function(obj) {
        var title, messageText, okButtonText;
        if (typeof (obj) == 'string') {
            messageText = obj;
        } else {
            messageText = obj.messageText || '';
        }

        var modal = new EPF.widget.modal.Modal({
            title: 'Alert',
            width: 450,
            content: EPF.template.dialog.alert({
                messageText: messageText
            })
        });
        
        modal.getContentParent().down('.buttons .ok').observe('click', function(event){
            modal.close();
        }).focus();
        
    },

    /** @deprecated */
    show : function() {
    }
});

dialog.Prompt = Class.create( /** @lends dialog.Prompt.prototype */  {
    
    /** 
     * @constructs 
     * @param {Object} obj
     * @param {String} obj.messageText
     * @param {Function} obj.onOk function(name){} where name is the input value.
     */
    initialize : function(obj) {
        this.messageText = obj.messageText || '';
        var onOk = obj.onOk;
        
        var modal = new EPF.widget.modal.Modal({
            title: 'Prompt',
            width: 375,
            content: EPF.template.dialog.prompt({
                messageText: obj.messageText
            })
        });
    
        modal.getContentParent().down('.buttons .ok').observe('click', function(event){
            var inputEl = modal.getContentParent().down('.input input');
            onOk(inputEl.value);
            modal.close();
        });
        
        modal.getContentParent().down('.buttons .cancel').observe('click', function(event){
            modal.close();
        });
        
        modal.getContentParent().down('.input input').focus();
    },
    
    /** @deprecated */
    show : function() {
    }
});


dialog.AjaxWindow = Class.create( /** @lends dialog.AjaxWindow.prototype */  {
    
    /** 
     * @deprecated
     * @constructs 
     * @param {String} url
     * @param {Object} ajaxOptions
     * @param {Object} windowOptions
     */
    initialize : function(url, ajaxOptions, windowOptions) {
        windowOptions = windowOptions || {};
        ajaxOptions.onComplete = function(transport) {
            var resEl = new Element('div', {
                style : 'display: none'
            }).update(transport.responseText);
            document.body.appendChild(resEl);
            var win = new Window(windowOptions);
            win.setContent(resEl, true, false);
            win.showCenter();
            win.updateWidth();
            win.updateHeight();
        };
        new Ajax.Request(url, ajaxOptions);
    }
});

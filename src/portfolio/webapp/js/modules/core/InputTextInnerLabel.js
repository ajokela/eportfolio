/* $Name:  $ */
/* $Id: InputTextInnerLabel.js,v 1.2 2010/10/27 19:24:57 ajokela Exp $ */
EPF.namespace('widget');

/**
 * This adds a label inside a text field if it is empty. Updates on focus and
 * blur. Also clears label if present when the form submits. CSS class for label
 * is 'defaulted'.
 */
EPF.widget.InputTextInnerLabel = Class.create( {
    initialize : function(fieldId, labelText, options) {
        this.options = options || {};
        this.el = $(fieldId);
        this.labelText = labelText;
        this.el.observe('focus', this.onFocus.bind(this));
        this.el.observe('blur', this.onBlur.bind(this));
        if (this.el.form) {
            $(this.el.form).observe('submit', this.onSubmit.bind(this));
        }
        if ($F(this.el).blank()) {
            this.onDefaulted();
        }
    },

    onFocus : function(event) {
        if (this.el.hasClassName('defaulted')) {
            this.onWriting();
        }
    },

    onBlur : function(event) {
        if ($F(this.el).blank()) {
            this.onDefaulted();
        }
    },

    onSubmit : function(event) {
        if (this.el.hasClassName('defaulted')) {
            this.onWriting();
        }
    },

    onDefaulted : function() {
        this.el.addClassName('defaulted').value = this.labelText;
        if (this.options.onDefaulted != null) {
            this.options.onDefaulted();
        }
    },

    onWriting : function() {
        this.el.removeClassName('defaulted').clear();
        if (this.options.onWriting != null) {
            this.options.onWriting();
        }
    }
});

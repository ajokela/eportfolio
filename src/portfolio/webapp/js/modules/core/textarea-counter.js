/* $Name:  $ */
/* $Id: textarea-counter.js,v 1.2 2010/10/27 19:24:57 ajokela Exp $ */
EPF.namespace('widget');

EPF.widget.TextareaCounter = Class.create( /** @lends EPF.widget.TextareaCounter.prototype */ {
    
    /** 
     * @constructs 
     * @param config
     * @param {HTMLElement} config.textareaEl
     * @param {HTMLElement} config.counterEl
     * @param {Number} config.max
     */
    initialize: function(config){
        this.textareaEl = config.textareaEl;
        this.counterEl = config.counterEl;
        this.max = config.max;

        this._updateCounter();
        $(this.textareaEl)
            .observe('keyup', this._changeListener.bind(this))
            .observe('input', this._changeListener.bind(this))
            .observe('paste', this._changeListener.bind(this));
    },

    /** @private */
    _changeListener: function(event) {
        if ($F(this.textareaEl).length > this.max) {
            alert('Your comment can not be longer than ' + this.max + ' characters. The extra characters will be truncated.');
            $(this.textareaEl).setValue($F(this.textareaEl).substring(0, this.max));
        }
        this._updateCounter();
    },

    /** @private */
    _updateCounter: function() {
        var length = $F(this.textareaEl).length;
        $(this.counterEl).update(length + ' of ' + this.max);
    }
});

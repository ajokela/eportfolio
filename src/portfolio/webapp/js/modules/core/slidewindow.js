/* $Name:  $ */
/* $Id: slidewindow.js,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
/**
 * <div id="container">
 *   <ol>
 *     <li>page 1 contents</li>
 *     <li>page 2 contents</li>
 *   </ol>
 * </div>
 */
EPF.widget.SlideWindow = Class.create( /** @lends EPF.widget.SlideWindow.prototype */ {
    
    /** 
     * @constructs 
     * @param config
     * @param {Number} config.width in px
     * @param {Number} [config.height] in px. max height if not specified.
     * @param {Function} [config.onChange]
     * @param {HTMLElement} config.containerEl
     */
    initialize: function(config){
        this.width = config.width;
        this.height = config.height;
        this.onChange = config.onChange;
        this.containerEl = $(config.containerEl);
        
        this.listEl = this.containerEl.down();
        this.pageEls = this.listEl.childElements();

        this.currentPage = 1;
        this.numPages = this.pageEls.size();
        
        this.containerEl.setStyle({
            position: 'relative',
            width: this.width + 'px',
            overflow: 'hidden'
        });
        
        this.listEl.setStyle({
            position: 'relative',
            //height: this.height + 'px',
            width: (this.width * this.numPages) + 'px',
            top: '0',
            left: '0'
        });
        
        this.pageEls.each(function(e, index) {
            e.setStyle({
                width: this.width + 'px',
                //height: this.height + 'px',
                //position: 'absolute',
                float: 'left',
                top: '0',
                left: (this.width * index) + 'px'
            });
        }.bind(this));
        
        if (!this.height) {
            EPF.log(this.pageEls.collect(function(el){return el.getHeight();}));
            this.height = this.pageEls.collect(function(el){return el.getHeight();}).max();
        }
        
        this.listEl.setStyle({
            height: this.height + 'px'
        });
    },
    
    next: function() {
        if (this.currentPage < this.numPages) {
            this.currentPage = this.currentPage + 1;
            this._move();
        }
    },
    
    prev: function() {
        if (this.currentPage > 1) {
            this.currentPage = this.currentPage - 1;
            this._move();
        }
    },
    
    /** @private */
    _move: function() {
        if (typeof(this.onChange) == 'function') {
            this.onChange(this.currentPage);
        }
        
        var newLeft = (this.currentPage-1) * this.width * -1;
        new Effect.Morph(this.listEl, {
            style: {left: newLeft + 'px'},
            duration: 0.5 
        });

    }
});

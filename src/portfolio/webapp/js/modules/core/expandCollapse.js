/* $Name:  $ */
/* $Id: expandCollapse.js,v 1.2 2010/10/27 19:24:57 ajokela Exp $ */
EPF.namespace('util');

EPF.util.ExpandCollapseControl = Class.create({
    initialize : function(config) {
      this.expandEl = $(config.expandEl);
      this.collapseEl = $(config.collapseEl);
      this.contentEl = $(config.contentEl);
      this.expandEffect = config.expandEffect;
      this.collaspseEffect = config.collaspseEffect;

      this.expandEl.observe('click', this._onExpandClick.bind(this));
      this.collapseEl.observe('click', this._onCollapseClick.bind(this));
    },

    _onExpandClick : function(event){
        event.stop();
        this.expandEl.hide();
        this.collapseEl.show();
        if (this.expandEffect) {
            this.expandEffect(this.contentEl);
        } else {
            this.contentEl.show();
        }
    },

    _onCollapseClick : function(event){
        event.stop();
        this.expandEl.show();
        this.collapseEl.hide();
        if (this.collaspseEffect) {
            this.collaspseEffect(this.contentEl);
        } else {
            this.contentEl.hide();
        }
    }
});

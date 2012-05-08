/* $Name:  $ */
/* $Id: paginator.js,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
EPF.namespace('widget');

EPF.widget.Paginator = Class.create( /** @lends EPF.widget.Paginator.prototype */ {

    /**
     * @constructs
     * @param conf
     * @param {Number} conf.totalRecords
     * @param {HTMLElement} conf.container
     * @param {Number} conf.rowsPerPage
     * @param {Number} conf.pageLinks must be odd and >= 3
     * @param {Function} [conf.onChange]
     */
    initialize : function(conf) {
        this.totalRecords = conf.totalRecords;
        this.container = $(conf.container);
        this.rowsPerPage = conf.rowsPerPage || 20;
        this.numPageLinks = conf.pageLinks || 5;
        this.currentPage = 1;
        this.onChange = conf.onChange || Prototype.emptyFunction
        this.numPages = Math.ceil(this.totalRecords / this.rowsPerPage);
        this.midRange = this._calcMidRange(this.currentPage, this.numPageLinks, this.numPages);
        this.collectionObj = conf.collectionObj || null;
        this.shareObj      = conf.shareObj || null;
    },
    
    render : function() {
        if (this.numPages == 1) {
            return;
        }
    
        var ul = new Element('ul', {
            'class' : 'pag-list'
        });
    
        this.buttons = {
            prev : new Element('li', {
                'class' : 'pag-prev'
            }).update('\u25C0'),
            first : new Element('li', {
                'class' : 'pag-num'
            }).update(1),
            firstBreak : new Element('li', {
                'class' : 'pag-break'
            }).update('\u2026'),
            middle : this.midRange.collect(function(n) {
                return new Element('li', {
                    'class' : 'pag-num'
                });
            }),
            lastBreak : new Element('li', {
                'class' : 'pag-break'
            }).update('\u2026'),
            last : new Element('li', {
                'class' : 'pag-num'
            }).update(this.numPages),
            next : new Element('li', {
                'class' : 'pag-next'
            }).update('\u25B6')
        };
    
        ul.appendChild(this.buttons.prev);
        ul.appendChild(this.buttons.first);
        ul.appendChild(this.buttons.firstBreak);
        this.buttons.middle.each(function(e) {
            ul.appendChild(e);
        });
        ul.appendChild(this.buttons.lastBreak);
        ul.appendChild(this.buttons.last);
        ul.appendChild(this.buttons.next);
    
        if (this.numPages <= this.numPageLinks) {
            this.buttons.prev.hide();
            this.buttons.next.hide();
            this.buttons.firstBreak.hide();
            this.buttons.lastBreak.hide();
        }
    
        this.setCurrentPage(this.currentPage);
    
        this.buttons.prev.observe('click', function(event) {
            this.requestPage(this.currentPage - 1);
        }.bind(this));
        this.buttons.first.observe('click', function(event) {
            this.requestPage(1);
        }.bind(this));
        this.buttons.last.observe('click', function(event) {
            this.requestPage(this.numPages);
        }.bind(this));
        this.buttons.next.observe('click', function(event) {
            this.requestPage(this.currentPage + 1);
        }.bind(this));
        this.buttons.middle.each(function(e, index) {
            e.observe('click', function(event) {
                this.requestPage(this.midRange[index]);
            }.bind(this));
        }.bind(this));
    
        this.container.appendChild(ul);
        
    },
    
    getCurrentPage : function() {
        return this.currentPage;
    },
    
    getNumPages : function() {
        return this.numPages;
    },
    
    requestPage : function(page) {
        if (page != this.currentPage) {
        	
        	if(this.collectionObj != null) {
        		this.collectionObj.pullElementRows(((page - 1) * this.rowsPerPage), (page * this.rowsPerPage) - 1);
        	}
        	
        	this.onChange(this, page);
            this.setCurrentPage(page);
        }
    },
    
    setCurrentPage : function(page) {
        this.currentPage = page;
        this.midRange = this._calcMidRange(this.currentPage, this.numPageLinks, this.numPages);
    
        this.midRange.each(function(page, index) {
            this.buttons.middle[index].update(page);
        }.bind(this));
    
        if (this.numPages > this.numPageLinks) {
            if (this.midRange.first() == 2) {
                this.buttons.firstBreak.hide();
            } else {
                this.buttons.firstBreak.show();
            }
            if (this.midRange.last() == this.numPages - 1) {
                this.buttons.lastBreak.hide();
            } else {
                this.buttons.lastBreak.show();
            }
            if (this.currentPage == 1) {
                this.buttons.prev.hide();
            } else {
                this.buttons.prev.show();
            }
            if (this.currentPage == this.numPages) {
                this.buttons.next.hide();
            } else {
                this.buttons.next.show();
            }
        }
    
        $$('ul.pag-list > li.pag-current').invoke('removeClassName', 'pag-current');
        if (this.currentPage == 1) {
            this.buttons.first.addClassName('pag-current');
        } else if (this.currentPage == this.numPages) {
            this.buttons.last.addClassName('pag-current');
        } else {
            this.buttons.middle[this.midRange.indexOf(this.currentPage)].addClassName('pag-current');
        }
    },
    
    getPageIndexes : function(page) {
        // EPF.log(Math.min(page * this.rowsPerPage, this.totalRecords - 1));
        return [ (page - 1) * this.rowsPerPage, Math.min((page * this.rowsPerPage) - 1, this.totalRecords - 1) ];
    },
    
    getState : function() {
        var records = this.getPageIndexes(this.currentPage);
        return {
            paginator : this,
            page : this.currentPage, // the current page
            records : records, // index offsets of first and last records
            // on the current page
            recordOffset : records[0], // index offset of the first record on
            // the current page
            totalRecords : this.totalRecords, // current totalRecords value
            rowsPerPage : this.rowsPerPage
        // current rowsPerPage value
        };
    },
    
    /** @private */
    _calcMidRange : function(currentPage, numPageLinks, numPages) {
        if (numPages == 2) {
            return [];
        }
        var middleIndex = Math.min(Math.max(currentPage, 3), numPages - 2);
        return $A($R(Math.max(middleIndex - 1, 2), Math.min(middleIndex + 1, numPages - 1)));
    }
});

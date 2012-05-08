/* $Name:  $ */
/* $Id: share.js,v 1.18 2011/02/24 21:49:16 ajokela Exp $ */
EPF.namespace('controller.share');

EPF.controller.share.Main = Class.create( /** @lends EPF.controller.share.Main.prototype */ {
    
    /** 
     * Main controller for Manage Portfolios page.
     * 
     * @constructs 
     * @param {HTMLElement} containerEl
     */
    initialize : function(containerEl) {
        $(containerEl).insert(EPF.template.share.page());
        
        this.layout = new YAHOO.widget.Layout('portfolioTable', {
            height: 600,
            units: [
                { 
                    header: 'Loading...',
                    position: 'left', 
                    width: 200, 
                    body: EPF.template.share.filtersSection(), 
                    resize: true, 
                    gutter: '5px',
                    proxy: true, 
                    minWidth: 100, 
                    maxWidth: 350 
                }, { 
                    header: 'Loading...',
                    position: 'center',
                    gutter: '5px 5px 5px 0', 
                    body: EPF.template.share.resultsSection()
                }
            ]
        }).render();
        
        new YAHOO.widget.Button('createLink');
        new YAHOO.widget.Button('searchPortfoliosButton');
        
        $('searchPortfoliosButton').observe('click', function(event) {
            window.location.hash = 'search/' + $F('searchPortfoliosInput');
            this.updateData();
            event.stop();
        }.bind(this));

        this.defaultSortType = 'Date';
        this.currentSortType = this.defaultSortType;
        this.sortType        = this.defaultSortType;
        this.start           = -1;
        this.end             = -1;

        this.buttons = {
            'delete' : new YAHOO.widget.Button("deleteButton"),
            'move' : new YAHOO.widget.Button("moveButton", {
                type : "menu",
                menu : "moveButtonMenu"
            }),
            'sort' : new YAHOO.widget.Button("sortButton", {
                type : "menu",
                menu : "sortButtonMenu"
            })
        };
        this.buttons['delete'].on("click", this.onDeleteButtonClick.bind(this));
        this.buttons['sort'].getMenu().addItems(
                [ this.createSortButtonMenuItem('Title'), this.createSortButtonMenuItem('Author'),
                        this.createSortButtonMenuItem('Date') ]);
        this.buttons['sort'].getMenu().render();

        this.updateMoveButtonMenu();

        this.tree = new YAHOO.widget.TreeView("filters");
        this.tree.subscribe("clickEvent", this.onTreeViewClick.bind(this));
        this.updateTreeView();
        
        this.updateData();
        $('portfolioTableLoading').hide();
        $('portfolioTable').show();
    },

    createSortButtonMenuItem : function(sortType) {
        var oThis = this;
        var oSortType = sortType;
        return {
            text : oSortType,
            onclick : {
                fn : function() {
                    this.sort(oSortType);
                    $A(this.buttons['sort'].getMenu().getItems()).each( function(item) {
                        item.cfg.setProperty('checked', item.cfg.getProperty('text') == oSortType);
                    });
                },
                scope : oThis
            },
            checked : (oSortType == oThis.defaultSortType)
        }
    },

    updateMoveButtonMenu : function() {
        new Ajax.Request('/portfolioFolders.do', {
            parameters : { method : 'list' },
            onSuccess : function(transport) {
                this.updateMoveButtonMenuFromJson(transport.responseJSON);
            }.bind(this)
        });
    },

    updateMoveButtonMenuFromJson : function(json) {
        var menu = this.buttons['move'].getMenu();
        $A(menu.getItems()).each( function(item) { menu.removeItem(item) });
    
        menu.addItem(new YAHOO.widget.MenuItem("unfiled", {
            onclick : {
                fn : this.onUnfiledFolderClick,
                scope : this
            }
        }));
        
        json.each( function(e) {
            var menuItem = new YAHOO.widget.MenuItem(e.name, {
                classname : 'folderLink',
                onclick : {
                    fn : this.onMoveButtonMenuItemClick,
                    obj : e.id,
                    scope : this
                }
            });
            menu.addItem(menuItem);
        }.bind(this));
        
        menu.addItem(new YAHOO.widget.MenuItem("new folder...", {
            onclick : {
                fn : this.onNewFolderClick,
                scope : this
            }
        }));
        menu.render();
    },

    /**
     *  YAHOO event handler
     *  @private 
     */
    onUnfiledFolderClick : function() {
        var portfolioIds = this.getSelectedPortfolioIds();
        if (portfolioIds.size() == 0) {
            this.showAlert('You must select at least one portfolio.');
        } else {
            new Ajax.Request('/portfolioFolders.do', {
                parameters : {
                    method : 'unfile',
                    portfolioIds : portfolioIds
                },
                onSuccess : function(transport) {
                    if ('folder' == this.getCurrentSearchType()) {
                        this.removePortfoliosFromView(portfolioIds);
                    } else {
                        this.unselectAll();
                    }
                }.bind(this)
            });
        }
    },


    /**
     *  YAHOO event handler
     *  @private 
     */
    onNewFolderClick : function() {
        var portfolioIds = this.getSelectedPortfolioIds();
        if (portfolioIds.size() == 0) {
            this.showAlert('You must select at least one portfolio.');
        } else {
            var oThis = this;
            new dialog.Prompt( {
                messageText : 'Enter the name of the folder to create.',
                onOk : function(folderName) {
                    new Ajax.Request('/portfolioFolders.do', {
                        parameters : {
                            method : 'newFolder',
                            portfolioIds : portfolioIds,
                            name : folderName
                        },
                        onSuccess : function(transport) {
                            var folderId = transport.responseJSON;
                            this.updateMoveButtonMenu();
                            window.location.hash = 'folder/' + folderId;
                            this.updateData();
                            this.updateTreeView();
                        }.bind(oThis)
                    });
                }
            }).show();
        }
    },


    /**
     *  YAHOO event handler
     *  @private 
     */
    onMoveButtonMenuItemClick : function(eventName, oEvent, folderId) {
        var portfolioIds = this.getSelectedPortfolioIds();
        if (portfolioIds.size() == 0) {
            this.showAlert('You must select at least one portfolio.');
        } else {
            new Ajax.Request('/portfolioFolders.do', {
                parameters : {
                    method : 'move',
                    portfolioIds : portfolioIds,
                    folderId : folderId
                },
                onSuccess : function(transport) {
                    if ( [ 'folder', 'unfiled' ].include(this.getCurrentSearchType())) {
                        this.updateData();
                    } else {
                        this.unselectAll();
                    }
                    
                    this.updateMoveButtonMenu();
                    window.location.hash = 'folder/' + folderId;
                    this.updateData();
                    this.updateTreeView();
                    
                    /* TODO message */
                }.bind(this)
            });
        }
    },

    getCurrentSearchType : function() {
        var hash = window.location.hash.slice(1);
        var index = hash.indexOf('/');
        if (index > 0) {
            hash = hash.substring(0, index);
        }
        return hash;
    },

    updateTreeView : function() {
        this.layout.getUnitByPosition('left').set('header', 'Loading...');
        $('filtersOuter').addClassName('loading');
        this.tree.removeChildren(this.tree.getRoot());
        new Ajax.Request('/portfolioSearchFilters.do', {
            method : 'get',
            onComplete : this.onTreeViewObjectLoaded.bind(this)
        });
    },

    onTreeViewObjectLoaded : function(transport) {
        this.tree.buildTreeFromObject(transport.responseJSON.result);
        this.tree.render();
        this.layout.getUnitByPosition('left').set('header', 'Filters');
        $('filtersOuter').removeClassName('loading');
    },

    /* YAHOO event handler */
    onTreeViewClick : function(oArgs) {
        if (oArgs.node.data.hash) {
            window.location.hash = oArgs.node.data.hash;
            this.updateData();
            return false;
        } else {
            return true;
        }
    },

    /* YAHOO event handler */
    onDeleteButtonClick : function(oEvent) {
        var selectedIds = this.getSelectedPortfolioIds();
        if (selectedIds.size() == 0) {
            this.showAlert('You must select at least one portfolio.');
        } else {
            this.showConfirm('Are you sure you want to delete these portfolios?', this.deletePortfolios.bind(this,
                    selectedIds));
        }
    },

    deletePortfolios : function(selectedIds) {
        new Ajax.Request('/deletePortfolios.do?', {
            parameters : {
                ids : selectedIds
            },
            onSuccess : function(transport) {
                this.removePortfoliosFromView(selectedIds);
            }.bind(this)
        });
    },
    
    removePortfoliosFromView: function(ids) {
        ids.each(function(id) {
            $('resultOuter_' + id).fade( {
                afterFinish : function() {
                    $('resultOuter_' + id).remove()
                }
            });
        });
    },

    showAlert : function(message) {
        new dialog.Alert(message).show();
    },

    showConfirm : function(message, func) {
        new dialog.Confirm( {
            messageText : message,
            onConfirm : func
        }).show();
    },

    getSelectedPortfolioIds : function() {
        return $$('.resultTools input[type="checkbox"]:checked').pluck('id').collect( function(n) {
            return n.split('_')[1];
        });
    },

    toggleActivePortfolio : function(portfolioId) {
        var outerEl = $('resultOuter_' + portfolioId);
        if (outerEl.hasClassName('active')) {
            outerEl.removeClassName('active');
        } else {
            $$('.resultOuter.active').invoke('removeClassName', 'active');
            outerEl.addClassName('active');
        }

        this.resizeLayout();
    },

    updateData : function() {
        $('results').hide();

        this.layout.getUnitByPosition('center').set('header', 'Loading...');
        $('resultsSection').addClassName('loading');

        var rs = $('resultSet');
        
        if(rs != null) {
        	rs.update('');
        }
        
        new Ajax.Request('/portfolio/search', {
            parameters : {
            	searchType : this.getCurrentSearchType(),
                hash : unescape(window.location.hash).slice(1)
            },
            onSuccess : function(transport) {
                var resp = transport.responseJSON;
                
                var results = EPF.template.share.newResults({cnt: resp.data.count});
                
                $('results').update(results);
                
                $('results').show();
                
                this.layout.getUnitByPosition('center').set('header', resp.data.description);
                
                $('resultsSection').removeClassName('loading');

                this.onResultSetLoaded(resp.data.count);
                
            }.bind(this)
        });
    },

    selectAll : function() {
    	
    	this.unselectAll();
    	
        $$('.resultTools input[type="checkbox"]').each(function(e) {

    		e.checked = true;
        	
        });

    },

    unselectAll : function() {
        $$('.resultTools input[type="checkbox"]').each( function(e) {
            e.checked = false;
        });
    },

    onResultLoaded : function(id) {
        var oThis = this;
        $('resultTop_' + id).observe('click', function(event) {
            if (event.target.type != 'checkbox') {
                oThis.toggleActivePortfolio(id);
            }
        });

        $('deleteAction_' + id).observe('click', function(event) {
            event.stop();
            this.showConfirm('Are you sure you want to delete this portfolio?', function() {
                this.deletePortfolios([id]);
            }.bind(this));
        }.bind(this));

        $('star_' + id).observe('click', function(event) {
            var flag = this.src.endsWith('star_empty.png');
            this.src = '/images/fugue/icon_shadowless/' + (flag ? 'star.png' : 'star_empty.png');
            new Ajax.Request('/portfolioFlag.do', {
                parameters : {
                    method : flag ? 'flag' : 'unflag',
                    portfolioId : id
                },
                onSuccess : function(transport) {
                }
            });
            event.stop();
        });

        $('resultBottom_' + id).select('.shareAction').invoke('observe', 'click', function(event) {
            event.stop();
            new EPF.widget.portfolio.Sharing(id, {
                onShareChange: function(event){
                    var resultShareContent = EPF.template.share.resultShare({portfolio:event.memo.portfolio});
                    $('resultTop_' + id).down('.resultShare').update(resultShareContent);
                }
            });
        });
    },

    onResultSetLoaded : function(count) {
        if ($('selectAll')) {
            $('selectAll').observe('click', function(event) {
                this.selectAll();
                event.stop();
            }.bind(this));
        }
        if ($('selectNone')) {
            $('selectNone').observe('click', function(event) {
                this.unselectAll();
                event.stop();
            }.bind(this));
        }

        var totalRecords = count;
        
        if ($('paging')) {
            
        	this.paginator = new EPF.widget.Paginator( {
                rowsPerPage  : 20,
                totalRecords : totalRecords,
                container    : 'paging',
                pageLinks    : 5,
                onChange     : this.handlePagination,
                shareObj     : this
            });
        	
            this.paginator.render();
            
            var currentState = this.paginator.getState();
            
        	this.start = currentState.records[0];
        	this.end   = (currentState.records[1] + 1);
            
            this.handlePortfolios(currentState.records[0], (currentState.records[1] + 1));
            
        }
        
        setTimeout(this.resizeLayout.bind(this), 50);
    },
    
    handlePortfolios : function(start, end) {
    	
    	$('resultRows').hide();
    	$('resultsSection').addClassName('loading');
    	
    	new Ajax.Request('/portfolio/search', {
            parameters : {
            	searchType : this.getCurrentSearchType(),
                hash       : unescape(window.location.hash).slice(1),
                start      : start,
                end        : end,
                sortType   : this.sortType
            },
            onSuccess : function(transport) {
                var resp = transport.responseJSON;
                
                var txt = EPF.template.share.results({matches: resp.data.matches});
                
                $('resultRows').update(txt);
                
                // this.layout.getUnitByPosition('center').set('header', resp.data.description);
                
                $('resultsSection').removeClassName('loading');

                $$('#resultSet .resultOuter').each(function(e){
                    var id = e.id.split('_')[1];
                    this.onResultLoaded(id);
                    e.show();
                }.bind(this));

                $$('.history').each(function(item){
                	// {literal}Effect.toggle('historyReport_{$portfolio.id|id}', 'slide', { delay: 0.25, queue: 'end' });return false;{/literal}
                	
                	item.observe('click', function(e) {
                		var id = e.target.id;
                		
                		if(id != null) {
                			var parts = id.split('_');
                			
                			if(parts.length == 2) {
                				Effect.toggle('historyReport_' + parts[1], 'slide', { delay: 0.25, queue: 'end' });
                			}
                		}
                		
                	}.bind(this));
                	
                }.bind(this));
                
                $('resultRows').show();
                
                setTimeout(this.resizeLayout.bind(this), 50);
                
            }.bind(this)
        });

    },
    
    resizeLayout : function() {
        var heightDiff = $('resultsOuter').getHeight() - $('resultsSection').getHeight();
        var min = 600; // TODO config
        this.layout.set('height', newHeight);
        var oldHeight = this.layout.get('height');
        var newHeight = Math.max((oldHeight + heightDiff), min);
        this.layout.set('height', newHeight);
        this.layout.resize();
    },

    handlePagination : function(paginator, pageRequested) {
    	
    	var newIndexes = paginator.getPageIndexes(pageRequested);
    	
    	this.start = newIndexes[0];
    	this.end   = newIndexes[1] + 1;
    	
    	// console.log(newIndexes);
    	
    	if(paginator.shareObj != null) {
    		paginator.shareObj.handlePortfolios(newIndexes[0], (newIndexes[1] + 1));
    	}

    },

    sort : function(sortType) {
    	
    	this.sortType = sortType;
    	this.handlePortfolios(this.start, this.end)

    }
});

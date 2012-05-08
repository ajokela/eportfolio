/* $Name:  $ */
/* $Id: collection.js,v 1.27 2011/01/27 17:37:01 ajokela Exp $ */
EPF.namespace('controller.collection');

EPF.controller.collection.Main = Class.create(/** @lends EPF.controller.collection.Main.prototype */ {

    /**
     * Main controller for Manage Collection page.
     *
     * @constructs
     * @param {HTMLElement} containerEl
     */
	
    initialize : function(containerEl) {
        $(containerEl).insert(EPF.template.collection.page());

        this.layout = new YAHOO.widget.Layout('layout', {
            height: 600,
            units: [
                {
                    header: 'Loading...',
                    position: 'left',
                    width: 200,
                    body: EPF.template.collection.filtersSection(),
                    resize: true,
                    gutter: '5px',
                    proxy: true,
                    minWidth: 100,
                    maxWidth: 350
                },
                {
                    header: 'Loading...',
                    position: 'center',
                    gutter: '5px 5px 5px 0',
                    body: EPF.template.collection.resultsSection()
                }
            ]
        }).render();
        
        document.observe('epf:resize', this.handleResize.bind(this));

        this.filtersPanel = new EPF.widget.share.ElementFiltersPanel({
            onUpdate: function() {
                this.layout.getUnitByPosition('left').set('header', 'Filters');
            }.bind(this),
            onLoading: function() {
                this.layout.getUnitByPosition('left').set('header', 'Loading...');
            }.bind(this)
        });
        this.resultsPanel = new EPF.widget.share.ElementsTableResultsPanel({
            
        	onUpdate: function(description, numMatches) {
                // EPF.log('onUpdate');
            	
                // setTimeout(this.resizeLayout.bind(this), 50);

                var headerText = description + ' (' + numMatches + ')';

                this.layout.getUnitByPosition('center').set('header', headerText);
                
                document.fire('epf:resize', {} );
                		
            }.bind(this),
            
            onLoading: function() {
                // EPF.log('onLoading');
                this.layout.getUnitByPosition('center').set('header', 'Loading...');
            }.bind(this)
        });

        var searchElementsButton = new YAHOO.widget.Button("searchElementsButton");
        searchElementsButton.on("click", function(oEvent) {
            YAHOO.util.Event.stopEvent(oEvent);
            window.location.hash = 'search/' + $F('searchElementsInput');
            document.fire('epf:collection.update.requested');
        }.bind(this));

        var createElementButton = new YAHOO.widget.Button("createLink");
        createElementButton.on("click", function(oEvent) {
            YAHOO.util.Event.stopEvent(oEvent);
            new EPF.widget.modal.Modal({
                title: 'Choose a type of element to create',
                width: 400,
                ajax: {
                    url: '/element/create/',
                    options: {
                        onSuccess : this.onCreateElementLoaded.bind(this)
                    }
                }
            });
        }.bind(this));

        var uploadFileButton = new YAHOO.widget.Button("uploadLink");
        uploadFileButton.on("click", function(oEvent) {
            YAHOO.util.Event.stopEvent(oEvent);
            new EPF.widget.ElementWindow({
                elementDefId : '020101'
            });
        });

        this.resultsPanel.updateData();
    },
    
    handleResize: function() {
    	setTimeout(this.resizeLayout.bind(this), 50);
    },

    resizeLayout : function() {
        var heightDiff = $('resultsOuter').getHeight() - $('resultsSection').getHeight();
        var min = 620; // TODO config
        this.layout.set('height', newHeight);
        var oldHeight = this.layout.get('height');
        var newHeight = Math.max((oldHeight + heightDiff), min);
        this.layout.set('height', newHeight);
        this.layout.resize();
    },

    onCreateElementLoaded : function(transport) {
        $$('.elementListing a').each(function(e) {
            var id = e.id;
            var elementDefId = id.substring(id.indexOf('_') + 1);
            e.observe('click', function(event) {
                event.stop();
                EPF.widget.modal.Modal.closeAll();
                new EPF.widget.ElementWindow({
                    elementDefId : elementDefId
                });
                // setTimeout(this.resizeLayout.bind(this), 50);
                document.fire('epf:resize', {} );
            }.bind(this));
        }.bind(this));
    }

});

EPF.namespace('widget.share');
EPF.widget.share.ElementsTableResultsPanel = Class.create(/** @lends EPF.widget.share.ElementsTableResultsPanel.prototype */ {

    /**
     * @constructs
     * @param {Function} config.onUpdate
     * @param {Function} config.onLoading
     */
    initialize : function(config) {
        this.config = config;

        this.defaultSortType = 'Date';
        this.currentSortType = this.defaultSortType;
        this.toolbar = new EPF.widget.share.ElementsTableToolbar(this);

        document.observe('epf:element.deleted', this.handleElementDeleted.bind(this));
        document.observe('epf:collection.update.requested', this.updateData.bind(this));
        document.observe('epf:element.batch.deleted', this.handleElementsDeleted.bind(this));
        document.observe('epf:element.saved', this.handleElementSaved.bind(this));
    },

    getCurrentSearchType : function() {
        var hash = window.location.hash.slice(1);
        var index = hash.indexOf('/');
        if (index > 0) {
            hash = hash.substring(0, index);
        }
        return hash;
    },

    /** @private */
    handleElementSaved : function(event) {
        var entryKeyId = event.memo.entryKeyId;
        var isNew = event.memo.isNew;

        if (isNew) {
            var hashParams = unescape(window.location.hash).slice(1).split('/');
            var searchType = hashParams[0];
            var searchArgs = hashParams[1];
            new Ajax.Request('/element/search/contains', {
                parameters: {
                    entryKeyId: entryKeyId,
                    searchType: searchType,
                    searchArgs: searchArgs
                },
                onSuccess: function(transport) {
                    var resp = transport.responseJSON;
                    if (resp.data.contains) {
                        this.addEntry(resp.data.entry);
                    }
                }.bind(this)
            });
        } else {
            this.updateEntry(event.memo.entry);
        }
    },

    /** @private */
    handleElementDeleted: function(event) {
        this.removeElementsFromView([event.memo.entryKeyId]);
    },

    /** @private */
    handleElementsDeleted : function(event) {
        this.removeElementsFromView(event.memo.entryKeyIds);
    },

    removeElementsFromView : function(entryKeyIds) {
    	
        entryKeyIds.each(function(id) {
        	
        	var isDeletable = $('deletable_' + id);
        	
        	if(isDeletable != null) {
	        	
        		if(isDeletable.value == 'true') {
	        		
		            var el = $('resultOuter_' + id);
		            if (el) {
		                el.fade({ afterFinish : function(effect) {
		                    effect.element.remove();
		                    this.fireOnUpdate();
		                    
		                }.bind(this) });
		            }
        		
        		}
        		else {
        			$('check_' + id).checked = false;
        		}
        		
        	}
        	
        }.bind(this));
    },

    getSelectedElementIds : function() {
        return $$('.resultTools input[type="checkbox"]:checked').pluck('id').collect(function(n) {
            return n.split('_')[1];
        });
    },

    updateData : function() {
        if (typeof this.config.onLoading == 'function') {
            this.config.onLoading();
        }

        $('results').hide();
        $('resultsSection').addClassName('loading');
        window.scrollTo(0, 0);

        var hashParams = unescape(window.location.hash).slice(1).split('/');
        var searchType = hashParams[0];
        var searchArgs = hashParams[1];

        new Ajax.Request('/element/search', {
            parameters : {
                searchType : searchType,
                searchArgs : searchArgs
            },
            onSuccess : function(transport) {
                var resp = transport.responseJSON;

                $('resultsSection').removeClassName('loading');

                this.updateResults(resp.data.matches);
                this.description = resp.data.description;

                this.fireOnUpdate();
                $('results').show();
            }.bind(this)
        });
    },

    fireOnUpdate : function() {

        if (typeof this.config.onUpdate == 'function') {
            this.config.onUpdate(this.description, $$('#resultSet .resultOuter').size());
            
            var currentState = this.paginator.getState();
            $A($$('.resultOuter').slice(currentState.records[0], currentState.records[1] + 1)).invoke('show');
            
            document.fire('epf:resize', {} );
            

        }
    },

    updateResults : function(records) {
        $('results').update(EPF.template.collection.resultList({matches:records}));
        records.each(this.onEntryLoaded.bind(this));

        if ($('selectAll')) {
            $('selectAll').observe('click', function(event) {
                event.stop();
                this.selectAll();
            }.bind(this));
        }
        if ($('selectNone')) {
            $('selectNone').observe('click', function(event) {
                event.stop();
                this.unselectAll();
            }.bind(this));
        }
        if (this.currentSortType != this.defaultSortType) {
            this.sort(this.currentSortType);
        }


        if ($('paging')) {
            var totalRecords = records.size();
            this.paginator = new EPF.widget.Paginator({
                rowsPerPage : 20,
                totalRecords : totalRecords,
                container : 'paging',
                pageLinks : 5,
                onChange : this.handlePagination
            });
            this.paginator.render();

            var currentState = this.paginator.getState();
            $A($$('.resultOuter').slice(currentState.records[0], currentState.records[1] + 1)).invoke('show');
        }
    },

    addEntry : function(entry) {
        var element = Element.fromText(EPF.template.collection.result({element: entry}));
        $('resultSet').insert({top: element});
        element.appear();
        this.onEntryLoaded(entry);
        this.fireOnUpdate();
    },

    /** @private */
    onEntryLoaded : function(entry) {
		$('resultTop_' + entry.entryKeyId).observe('click', function(event) {
            if (event.target.type != 'checkbox') {
                event.stop();
                new EPF.widget.ElementWindow({ 
                	entryKeyId : entry.entryKeyId
                    });
            }
        }.bind(this));
        $('resultOuter_' + entry.entryKeyId).epf_record = entry;
    },

    updateEntry : function(entry) {
        var entryKeyId = entry.entryKeyId;
        var el = $('resultOuter_' + entryKeyId);
        if (el) {
            var visible = el.visible();
            Element.replace(el, EPF.template.collection.result({element: entry}));
            el = $('resultOuter_' + entryKeyId);
            if (visible) {
                el.show();
            }
            this.onEntryLoaded(entry);
        }
    },

    selectAll : function() {
    	
    	this.unselectAll();
    	
    	var currentPage = this.paginator.getCurrentPage();
    	var start = (currentPage * 20) - 20;
    	var finish = (currentPage * 20);
    	var idx   = 0;
    	
    	// alert("Page[" + currentPage + "] - start[" + start + "], finish[" + finish + "]");
    	
        $$('.resultTools input[type="checkbox"]').each(function(e) {

        	idx++;
        	
        	if (idx >= start && idx <= finish) {
        		e.checked = true;
        	}
        	
        });
        
    },

    unselectAll : function() {
        $$('.resultTools input[type="checkbox"]').each(function(e) {
            e.checked = false;
        });
    },

    /** @private */
    handlePagination : function(paginator, pageRequested) {
        var allRecords = $$('.resultOuter');
        var currentState = paginator.getState();
        var currentIndexes = paginator.getPageIndexes(paginator.getCurrentPage());
        $A(allRecords.slice(currentIndexes[0], currentIndexes[1] + 1)).invoke('hide');
        var newIndexes = paginator.getPageIndexes(pageRequested);
        $A(allRecords.slice(newIndexes[0], newIndexes[1] + 1)).invoke('show');
    },

    sort : function(sortType) {
        var arr = $$('#resultSet .resultOuter');
        arr = arr.sortBy(this.sortFunctions[sortType]);
        this.currentSortType = sortType;
        arr.each(function(e) {
            $('resultSet').insert(e)
        });
        arr.invoke('hide');
        var newIndexes = this.paginator.getPageIndexes(this.paginator.getCurrentPage());
        $A(arr.slice(newIndexes[0], newIndexes[1] + 1)).invoke('show');
    },

    sortFunctions : {
        'Title' : function(e) {
            return e.epf_record.entryName.toLowerCase();
        },
        'Type' : function(e) {
            return e.epf_record.elementDefinition.name.toLowerCase();
        },
        'Date' : function(e) {
            var millis = e.epf_record.modifiedDateMillis;
            if (millis) {
                return millis * -1;
            } else {
                return 0;
            }
        }
    }
});

/**
 *
 */
EPF.widget.share.ElementsTableToolbar = Class.create(/** @lends EPF.widget.share.ElementsTableToolbar.prototype */ {

    /**
     * @constructs
     * @param {EPF.widget.ElementsTableResultsPanel} resultsView
     */
    initialize : function(resultsView) {
        this.resultsView = resultsView;
        this.buttons = {
            'share' : new YAHOO.widget.Button("shareButton"),
            'delete' : new YAHOO.widget.Button("deleteButton"),
            'print' : new YAHOO.widget.Button("printButton"),
            'move' : new YAHOO.widget.Button("moveButton", {
                type : "menu",
                menu : "moveButtonMenu"
            }),
            'sort' : new YAHOO.widget.Button("sortButton", {
                type : "menu",
                menu : "sortButtonMenu"
            })
        };

        this.buttons['share'].on("click", this.onShareButtonClick.bind(this));
        this.buttons['delete'].on("click", this.onDeleteButtonClick.bind(this));
        this.buttons['print'].on("click", this.onPrintButtonClick.bind(this));


        ['Title', 'Type', 'Date'].each(function(sortType) {
            this.buttons['sort'].getMenu().addItem(this.createSortButtonMenuItem(sortType));
        }.bind(this));
        this.buttons['sort'].getMenu().render();

        this.updateMoveButtonMenu();

    },

    createSortButtonMenuItem : function(sortType) {
        return {
            text : sortType,
            onclick : {
                fn : function() {
                    this.resultsView.sort(sortType);
                    var items = $A(this.buttons['sort'].getMenu().getItems());
                    items.each(function(item) {
                        item.cfg.setProperty('checked', item.cfg.getProperty('text') == sortType);
                    });
                }.bind(this)
            },
            checked : (sortType == this.resultsView.defaultSortType)
        }
    },

    updateSortButtonMenuChecks : function() {
        $A(this.buttons['sort'].getMenu().getItems()).each(function(item) {
            item.cfg.setProperty('checked', item.cfg.getProperty('text') == this.resultsView.currentSortType);
        });
    },

    updateMoveButtonMenu : function() {
        new Ajax.Request('/elementFolder/list/', {
            onSuccess : function(transport) {
                this.updateMoveButtonMenuFromJson(transport.responseJSON);
            }.bind(this)
        });
    },

    updateMoveButtonMenuFromJson : function(json) {
        var menu = this.buttons['move'].getMenu();
        $A(menu.getItems()).each(function(item) {
            menu.removeItem(item)
        });

        menu.addItem(new YAHOO.widget.MenuItem("unfiled", {
            onclick : {
                fn : this.onUnfiledFolderClick,
                scope : this
            }
        }));

        json.each(function(e) {
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
        var entryKeyIds = this.resultsView.getSelectedElementIds();
        if (entryKeyIds.size() == 0) {
            this.showAlert('You must select at least one element.');
        } else {
            new Ajax.Request('/elementFolder/unfile/', {
                parameters : {
                    ids : entryKeyIds
                },
                onSuccess : function(transport) {
                    if ('folder' == this.resultsView.getCurrentSearchType()) {
                        this.resultsView.removeElementsFromView(entryKeyIds);
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
        var portfolioIds = this.resultsView.getSelectedElementIds();
        if (portfolioIds.size() == 0) {
            this.showAlert('You must select at least one element.');
        } else {
            new dialog.Prompt({
                messageText : 'Enter the name of the folder to create.',
                onOk : function(folderName) {
                    new Ajax.Request('/elementFolder/new/', {
                        parameters : {
                            ids : portfolioIds,
                            name : folderName
                        },
                        onSuccess : function(transport) {
                            var folderId = transport.responseJSON;
                            this.updateMoveButtonMenu();
                            window.location.hash = 'folder/' + folderId;
                            document.fire('epf:collection.update.requested');
                            document.fire('epf:filters.update.requested');
                        }.bind(this)
                    });
                }.bind(this)
            }).show();
            
            // this.updateTreeView();
        }
    },

    /* YAHOO event handler */
    onMoveButtonMenuItemClick : function(eventName, oEvent, folderId) {
        var entryKeyIds = this.resultsView.getSelectedElementIds();
        if (entryKeyIds.size() == 0) {
            this.showAlert('You must select at least one element.');
        } else {
            new Ajax.Request('/elementFolder/move/', {
                parameters : {
                    ids : entryKeyIds,
                    folderId : folderId
                },
                onSuccess : function(transport) {
                	/*
                    if ([ 'folder', 'unfiled' ].include(this.resultsView.getCurrentSearchType())) {
                        
                    } else {
                        
                    }
                	*/
                    /* TODO message */
                	
                	// this.unselectAll();
                	this.updateMoveButtonMenu();
                	window.location.hash = 'folder/' + folderId;
                    document.fire('epf:collection.update.requested');
                    document.fire('epf:filters.update.requested');
                    
                }.bind(this)
            });
        }
    },

    /* YAHOO event handler */
    onDeleteButtonClick : function(oEvent) {
        EPF.log('onDeleteButtonClick');
        try {
            var selectedIds = this.resultsView.getSelectedElementIds();
            if (selectedIds.size() == 0) {
                this.showAlert('You must select at least one element.');
            } else {
                this.showConfirm('Are you sure you want to delete these elements?', this.deleteElements.bind(this,
                        selectedIds));
            }
        } catch(e) {
            EPF.log(e);
        }
    },

    randomString : function randomString() {
    	
    	var chars = "0123456789abcdefghiklmnopqrstuvwxyz";
    	var string_length = 16;
    	var randomstring = '';
    	for (var i=0; i<string_length; i++) {
    		var rnum = Math.floor(Math.random() * chars.length);
    		randomstring += chars.substring(rnum,rnum+1);
    	}
    	
    	return randomstring;
    },

    
    /* YAHOO event handler */
    onShareButtonClick : function(oEvent) {
        var selectedIds = this.resultsView.getSelectedElementIds();
        if (selectedIds.size() == 0) {
            this.showAlert('You must select at least one element.');
            return;
        }
       
        var form = new Element('form', {
            method : 'post',
            action : '/quickshare?seed=' + this.randomString(),
            target : '_blank'
        });

        selectedIds.each(function(id) {
            form.insert(new Element('input', {type: 'hidden', name: 'entryKeyId', value: id}));
        });
        document.body.appendChild(form);
        form.submit();
    },

    /* YAHOO event handler */
    onPrintButtonClick : function(oEvent) {
        var selectedIds = this.resultsView.getSelectedElementIds();
        if (selectedIds.size() == 0) {
            this.showAlert('You must select at least one element.');
        } else {
            window.open('/element/print/?' + Object.toQueryString({entryKeyId: selectedIds}));
        }
    },

    deleteElements : function(selectedIds) {
        new EPF.bus.ElementManager().deleteElements(selectedIds);
    },

    showAlert : function(message) {
        new dialog.Alert(message).show();
    },

    showConfirm : function(message, func) {
        new dialog.Confirm({
            messageText : message,
            onConfirm : func
        }).show();
    }
});

EPF.widget.share.ElementFiltersPanel = Class.create(/** @lends EPF.widget.share.ElementFiltersPanel.prototype */ {

    /**
     * @constructs
     * @param config
     * @param config.onLoading
     * @param config.onUpdate
     */
    initialize : function(config) {
        this.config = config;
        this.tree = new YAHOO.widget.TreeView("filters");
        this.tree.subscribe("clickEvent", this.onTreeViewClick.bind(this));
        this.updateTreeView();
        document.observe('epf:filters.update.requested', this.updateTreeView.bind(this));
    },

    updateTreeView : function() {
        if (typeof this.config.onUpdate == 'function') {
            this.config.onUpdate();
        }
        
        this.tree.removeChildren(this.tree.getRoot());
        $('filtersOuter').addClassName('loading');
        new Ajax.Request('/collection/searchFilters', {
            method : 'get',
            onComplete : this.onTreeViewObjectLoaded.bind(this)
        });
    },

    onTreeViewObjectLoaded : function(transport) {
        if (typeof this.config.onLoading == 'function') {
            this.config.onUpdate();
        }
        this.tree.buildTreeFromObject(transport.responseJSON.result);
        $('filtersOuter').removeClassName('loading');
        this.tree.render();
    },

    /* YAHOO event handler */
    onTreeViewClick : function(oArgs) {
        if (oArgs.node.data.hash) {
            window.location.hash = oArgs.node.data.hash;
            var event = document.fire('epf:collection.update.requested');
            return false;
        } else {
            return true;
        }
    }
});

EPF.namespace('bus');

EPF.bus.ElementManager = Class.create(/** @lends EPF.bus.ElementManager.prototype */ {

    /**
     * @constructs
     */
    initialize : function() {

    },

    deleteElements : function(selectedIds) {
        new Ajax.Request('/element/delete/', {
            parameters : {
                entryKeyId : selectedIds
            },
            onSuccess : function(transport) {
                document.fire('epf:element.batch.deleted', {
                    entryKeyIds : selectedIds
                });
            }
        });
    }

});

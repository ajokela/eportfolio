/* $Name:  $ */
/* $Id: collection.js,v 1.27 2011/01/27 17:37:01 ajokela Exp $ */

var communityId = "";

EPF.namespace('controller.communityCollection');

EPF.controller.communityCollection.Main = Class.create(/** @lends EPF.controller.communityCollection.Main.prototype */ {

    /**
     * Main controller for Manage Collection page.
     *
     * @constructs
     * @param {HTMLElement} containerEl
     */
	
    initialize : function(containerEl, community_id) {
    	
    	communityId = community_id;
    	
        $(containerEl).insert(EPF.template.communityCollection.page());

        this.manageCollectionHidden = false;
        
        this.layout = new YAHOO.widget.Layout('layout', {
            height: 600,
            units: [
                {
                    header: 'Loading...',
                    position: 'center',
                    gutter: '3px 3px 3px 3px',
                    body: EPF.template.communityCollection.resultsSection()
                }
            ]
        }).render();

        this.resultsPanel = new EPF.widget.share.communityElementsTableResultsPanel({
            onUpdate: function(description, numMatches) {
                
                setTimeout(this.resizeLayout.bind(this), 50);

                var headerText = description + ' (' + numMatches + ')';

                this.layout.getUnitByPosition('center').set('header', headerText);
            }.bind(this),
            onLoading: function() {
                
                this.layout.getUnitByPosition('center').set('header', 'Loading...');
            }.bind(this)
        });

        /*
        var searchElementsButton = new YAHOO.widget.Button("searchElementsButton");
        searchElementsButton.on("click", function(oEvent) {
            YAHOO.util.Event.stopEvent(oEvent);
            window.location.hash = 'search/' + $F('searchElementsInput');
            document.fire('epf:collection.update.requested');
        }.bind(this));

        var uploadFileButton = new YAHOO.widget.Button("uploadLink");
        uploadFileButton.on("click", function(oEvent) {
            YAHOO.util.Event.stopEvent(oEvent);
            new EPF.widget.ElementWindow({
                elementDefId : '020101'
            });
        });
        */

        this.resultsPanel.updateData();
        
        $('manageCollectionLink').observe('click', function(event) {
            event.stop();
            
            if (this.manageCollectionHidden) {
                $('manageCollection').show();
                $('manageCollectionLink').innerHTML = "Hide Available Files List";
            }
            else if (!this.manageCollectionHidden) {
                $('manageCollection').hide();
                $('manageCollectionLink').innerHTML = "Show Available Files List";
            }
            
            this.manageCollectionHidden = ! this.manageCollectionHidden;
            
        }.bind(this));
        
    },

    resizeLayout : function() {
        var heightDiff = $('resultsOuter').getHeight() - $('resultsSection').getHeight();
        var min = 600; 
        this.layout.set('height', newHeight);
        var oldHeight = this.layout.get('height');
        var newHeight = Math.max((oldHeight + heightDiff), min);
        this.layout.set('height', newHeight);
        this.layout.resize();
    },


});

EPF.controller.communityCollection.Resources = Class.create(/** @lends EPF.controller.communityCollection.Resources.prototype */ {

    /**
     * Controller for Resource page.
     *
     * @constructs
     * @param {HTMLElement} containerEl
     */
	
    initialize : function(containerEl, community_id) {
        
    	communityId = community_id;
    	
    	this.updateResources(containerEl, "");
    	
    	
    },
 
    updateResources : function(containerEl, searchParam) {
    	
   	new Ajax.Request('/community/resources/ajax/' + communityId, 
		{
			parameters : {
				searchParam : Base64.encode(searchParam)
			},
	
			onSuccess: function(transport) {
				
				try {
    				
    				var resp = transport.responseJSON;
    				
    				if(resp != null) {
	    				
	    				// this.updateList(resp.data.resources);
	    				
	    				var random = ( Math.floor ( Math.random ( ) * 3 ) );
	    				
	    				$(containerEl).update(EPF.template.communityCollection.mainResources({resources: resp.data.resources, links: resp.data.links, random: random}));

    				}
    				
    				// listSearchButton  

    				if($('listSearchText') != null) {
    					$('listSearchText').value = searchParam;
    				}
    				
    		        this.buttons = {
    		                'search' : new YAHOO.widget.Button("listSearchButton"),
    		                'clear'  : new YAHOO.widget.Button("listClearButton")
    		            };

		            this.buttons['search'].on("click", function(oEvent) {
		            	YAHOO.util.Event.stopEvent(oEvent);
		            	
		            	this.updateResources(containerEl, $F('listSearchText'));
		            	
		            }.bind(this));
		            
		            this.buttons['clear'].on("click", function(oEvent) {
		            	YAHOO.util.Event.stopEvent(oEvent);
		            	
		            	if($('listSearchText') != null) {
		            		$('listSearchText').value = '';
		            	}
		            	
		            	this.updateResources(containerEl, '');
		            }.bind(this));
				
    				var cnt = 1;
    				var hiddenResource = 0;
    					        				
    				var hiddenLink = 0;
    				
    				resp.data.resources.collect(function(resource) {
    					
    					if(cnt > 5) {
    						$('resource_' + resource.id).hide();
    						$('resource_' + resource.id).addClassName('hidden_resource');
    						++hiddenResource;
    					}
    					
    					++cnt;
    				});

    				cnt = 1;
    				
    				resp.data.links.collect(function(link){
    					
    					if(cnt > 5) {
    						$('link_' + link.id).hide();
    						$('link_' + link.id).addClassName('hidden_link');
    						++hiddenLink;
    					}
    					
    					++cnt;
    				});
    				
    				if(hiddenResource > 0) {
    					
        				$('resources_info').update(EPF.template.communityCollection.resourcesShowInfo({number: hiddenResource}));
        				$('resourcesInfo').observe('click', function(event){this.resourcesShow(event);}.bind(this));
    					
    				}
    				
    				if(hiddenLink > 0) {
    					
        				$('links_info').update(EPF.template.communityCollection.linksShowInfo({number: hiddenLink}));
        				$('linksInfo').observe('click', function(event){this.linksShow(event);}.bind(this));

    				}
    				
    				if($('resourcesResources') != null) {
    					$('resourcesResources').show();
    				}
    				
    				if($('linksResources') != null) {
    					$('linksResources').show();
    				}
    				
				}
				catch(err) {
					EPF.log(err);
				}
				
			}.bind(this),
			
			onFailure: function(transport) {
				EPF.log("Error getting community resources...")
			}.bind(this)
			
		}
        );

    	
    },
    
    resourcesShow : function(event) {
    	event.stop();
    	
    	var hidden = 0;
    	
    	$$('.hidden_resource').each(function(e){
    		
    		e.removeClassName('hidden_resource');
    		e.addClassName('visible_resource');
    		
    		e.show();
    		++hidden;
    	});
    	
    	$('resources_info').update(EPF.template.communityCollection.resourcesHideInfo({number: hidden}));
      
        $('resourcesInfo').observe('click', function(event){this.resourcesHide(event);}.bind(this));
    },

    resourcesHide : function(event) {
    	event.stop();
    	
    	var visible = 0;
    	
    	$$('.visible_resource').each(function(e){
    		
    		e.removeClassName('visible_resource');
    		e.addClassName('hidden_resource');
    		
    		e.hide();
    		++visible;
    	});
    	
            // $('resources_info').update(EPF.template.communityCollection.resourcesHideInfo({number: len}));
    	
        $('resources_info').update(EPF.template.communityCollection.resourcesShowInfo({number: visible}));
      
        $('resourcesInfo').observe('click', function(event){this.resourcesShow(event);}.bind(this));
    },

    
    linksShow : function(event) {
    	event.stop();
    	
    	var hidden = 0;
    	
    	$$('.hidden_link').each(function(e){
    		
    		e.removeClassName('hidden_link');
    		e.addClassName('visible_link');
    		
    		e.show();
    		++hidden;
    	});
    	
    	$('links_info').update(EPF.template.communityCollection.linksHideInfo({number: hidden}));

        $('linksInfo').observe('click', function(event){this.linksHide(event);}.bind(this));
    },

    linksHide : function(event) {
    	event.stop();

    	var visible = 0;
    	
    	$$('.visible_link').each(function(e){
    		
    		e.removeClassName('visible_link');
    		e.addClassName('hidden_link');
    		
    		e.hide();
    		++visible;
    	});
    	
    	$('links_info').update(EPF.template.communityCollection.linksShowInfo({number: visible}));
      
        $('linksInfo').observe('click', function(event){this.linksShow(event);}.bind(this));
    },

});


EPF.namespace('widget.share');
EPF.widget.share.communityElementsTableResultsPanel = Class.create(/** @lends EPF.widget.share.communityElementsTableResultsPanel.prototype */ {

    /**
     * @constructs
     * @param config
     * @param {Function} config.onUpdate
     * @param {Function} config.onLoading
     */
    initialize : function(config) {
        this.config = config;

        this.defaultSortType = 'Date';
        this.currentSortType = this.defaultSortType;
        this.toolbar = new EPF.widget.share.communityElementsTableToolbar(this);

        document.observe('epf:collection.update.requested', this.updateData.bind(this));

    },

    getCurrentSearchType : function() {
        var hash = window.location.hash.slice(1);
        var index = hash.indexOf('/');
        if (index > 0) {
            hash = hash.substring(0, index);
        }
        return hash;
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
        
        var searchType = 'type';
        var searchArgs = 'file';

        if(hashParams.length > 1) {
        	searchArgs += "|" + Base64.encode(hashParams[1]);
        }
        
        EPF.log("Searching -- '" + searchArgs + "'")
        
        $('resultsSection').addClassName('loading');
        
        new Ajax.Request('/element/search', {
            parameters : {
                searchType : searchType,
                searchArgs : searchArgs
            },
            onSuccess : function(transport) {
                var resp = transport.responseJSON;

                $('resultsSection').removeClassName('loading');

                this.updateResults(resp.data.matches);
                this.description = resp.data.description.replace("UCard Photo", "Available Files").replace("Elements by type:", "");

                this.fireOnUpdate();
                $('results').show();
            }.bind(this),
            
            onFailure : function(transport) {
            	EPF.log("Element/Search Failed");
            }.bind(this)
        });

    },

    fireOnUpdate : function() {

        if (typeof this.config.onUpdate == 'function') {
            this.config.onUpdate(this.description, $$('#resultSet .resultOuter').size());
        }
        
        this.fetchResources();

    },
    
    fetchResources : function() {

    	new Ajax.Request('/community/resources/ajax/' + communityId, 
        		{
        			onSuccess: function(transport) {
        				
        				var resp = transport.responseJSON;
        				
        				this.updateList(resp.data.resources);
        				
        			}.bind(this)
        			
        		}
        );
	
    },

    
    updateList : function(resources) {
    	$('resourcesList').update(EPF.template.communityCollection.resources({resources: resources, communityid: communityId}));
    	
    	$('resources').show();
    	
        var items = $$(".deleteResource");
        
        for(var i=0; i<items.length; ++i) {
        	items[i].observe('click', function(event) {
        		var element = event.element();

        		var parts = element.id.split('_');
        		
        		if(parts.length == 3) {
        			
        	        new Ajax.Request('/community/resources/del/' + communityId + "/" + parts[2], 
        	        		{
        	        			onSuccess: function(transport) {
        	        				$('resource_' + parts[2]).fade();
        	        			}.bind(this)
        	        		}
        	        );
        			
        		}
        		
        	}.bind(this));
        }
    	    	
    },

    updateResults : function(records) {
    	
        $('results').update(EPF.template.communityCollection.resultList({matches:records}));
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
        var element = Element.fromText(EPF.template.communityCollection.result({element: entry}));
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
                new EPF.widget.ElementWindow({ entryKeyId : entry.entryKeyId });
            }
        }.bind(this));
        $('resultOuter_' + entry.entryKeyId).epf_record = entry;
    },

    updateEntry : function(entry) {
        var entryKeyId = entry.entryKeyId;
        var el = $('resultOuter_' + entryKeyId);
        if (el) {
            var visible = el.visible();
            Element.replace(el, EPF.template.communityCollection.result({element: entry}));
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
        'Size' : function(e) {
            return e.epf_record.uploadSize;
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
EPF.widget.share.communityElementsTableToolbar = Class.create(/** @lends EPF.widget.share.communityElementsTableToolbar.prototype */ {

    /**
     * @constructs
     * @param {EPF.widget.ElementsTableResultsPanel} resultsView
     */
    initialize : function(resultsView) {
        this.resultsView = resultsView;
        this.buttons = {
        	'upload' : new YAHOO.widget.Button("uploadButton"),
            'attach' : new YAHOO.widget.Button("attachButton"),
            'sort' : new YAHOO.widget.Button("sortButton", {
                type : "menu",
                menu : "sortButtonMenu"
            }),
            'search' : new YAHOO.widget.Button("searchElementsButton"),
            'clear'  : new YAHOO.widget.Button("clearSearchButton")
        };

        this.buttons['attach'].on("click", function(oEvent) {
        	YAHOO.util.Event.stopEvent(oEvent);
        	this.onAttachButtonClick();
        }.bind(this));
        
        this.buttons['upload'].on("click", function(oEvent) {
        	YAHOO.util.Event.stopEvent(oEvent);
            new EPF.widget.ElementWindow({
                elementDefId : '020101', 
                callback : function() {
                	window.location.reload();
                }
            });

        }.bind(this));
        
        this.buttons['search'].on("click", function(oEvent) {
        	YAHOO.util.Event.stopEvent(oEvent);
            window.location.hash = 'search/' + $F('searchElementsInput');
            document.fire('epf:collection.update.requested');
        }.bind(this));


        this.buttons['clear'].on("click", function(oEvent) {
        	YAHOO.util.Event.stopEvent(oEvent);
            window.location.hash = '';
            $('searchElementsInput').value = '';
            document.fire('epf:collection.update.requested');
        }.bind(this));


        ['Title', 'Size', 'Date'].each(function(sortType) {
            this.buttons['sort'].getMenu().addItem(this.createSortButtonMenuItem(sortType));
        }.bind(this));
        this.buttons['sort'].getMenu().render();

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
    onAttachButtonClick : function(oEvent) {
        var selectedIds = this.resultsView.getSelectedElementIds();
    
        if(selectedIds.size() > 0) {
        	
	        new Ajax.Request('/community/resources/add/' + communityId + "/" + selectedIds, 
	        		{
	        			onSuccess: function(transport) {
	        				this.fetchResources();
	        			}.bind(this),
 	        		}
	        );
        
        }
        
    },
    
    fetchResources : function() {

    	new Ajax.Request('/community/resources/ajax/' + communityId, 
        		{
        			onSuccess: function(transport) {
        				
        				var resp = transport.responseJSON;
        				
        				this.updateList(resp.data.resources);
        				
        			}.bind(this)
        			
        		}
        );
	
    },
    
    updateList : function(resources) {
    	$('resourcesList').update(EPF.template.communityCollection.resources({resources: resources}));
    	
    	$('resources').show();
    	
        var items = $$(".deleteResource");
        
        for(var i=0; i<items.length; ++i) {
        	items[i].observe('click', function(event) {
        		var element = event.element();

        		var parts = element.id.split('_');
        		
        		if(parts.length == 3) {
        			
        	        new Ajax.Request('/community/resources/del/' + communityId + "/" + parts[2], 
        	        		{
        	        			onSuccess: function(transport) {
        	        				$('resource_' + parts[2]).fade();
        	        			}.bind(this)
        	        		}
        	        );
        			
        		}
        		
        	}.bind(this));
        }

    	
    },
    
    showAlert : function(message) {
        new dialog.Alert(message).show();
    },

    showConfirm : function(message, func) {
        new dialog.Confirm({
            messageText : message,
            onConfirm : func
        }).show();
    },
        
});


EPF.namespace('bus');

EPF.bus.communityElementManager = Class.create(/** @lends EPF.bus.communityElementManager.prototype */ {

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

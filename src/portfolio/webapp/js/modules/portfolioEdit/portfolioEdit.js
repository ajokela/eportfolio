EPF.namespace('controller.portfolioEdit');

var existing_resp = null;


EPF.controller.portfolioEdit.Main = Class.create(/** @lends EPF.controller.portfolioEdit.Main.prototype */ {

    /**
     * @constructs
     */
    initialize: function(options) {

    	this.hasSortable = false;
    	this.isSaving    = false;
    	
        new Ajax.Request('/element/search', {
            parameters : { searchType : 'all' },
            onComplete : function(transport) {
                
            	existing_resp = transport.responseJSON;

            	var loading_elements = $$('.loading_elements');
            	
            	if(loading_elements != null) {
            		loading_elements.collect(function(e){
            			e.hide();
            		}.bind(this));
            	}
            	
            	var add_content = $$('.add_content');
            	
            	if(add_content != null) {
            		add_content.collect(function(e){
            			e.show();
            		}.bind(this));
            	}

            	
            }.bind(this)
        });
    	
        this.num = 0;
        
        this.heading_num = 0;
        
        this.main_heading = null;
        
        if (options.portfolio) {
            this.initPortfolio(options.portfolio);
        }

        $('contentWrapper').scrollTop = 0;

        Event.observe(window, 'resize', this.resizePanes.bind(this));
        this.resizePanes();

        if(this.heading_num == 0) {
        	EPF.log("==> Adding new Heading - no headings present");
        	this.addH1('', 'menu', 0);
        }
    },

    initPortfolio: function(portfolio) {
    	
        this.portfolio = portfolio;
        this.portfolioId = this.portfolio.id;

        // EPF.log("content: " + this.portfolio.content);
        
        var json = JSON.parse(this.portfolio.content);
        this.buildPortfolio(json);
        
        new Ajax.InPlaceEditor('portfolioTitle', '/portfolio/' + this.portfolioId + '/updateTitle', {
            okControl: true,
            cancelControl: true,
            submitOnBlur: true,
            onLeaveEditMode: function(form, value) {
            	
            	var title = document.title;
            	
            	var parts = title.split('|');
            	
            	var owner = this.chomp(parts[0]);
            	
            	document.title = owner + " | " + $('portfolioTitle').innerHTML;
            	
            }.bind(this)
        });

        if ($$('#portfolioDescriptionSection > .description')[0].innerHTML.blank()) {
            $('portfolioDescriptionSection').addClassName('empty');
        }

        new Ajax.InPlaceEditor($$('#portfolioDescriptionSection > .description')[0], '/portfolio/' + this.portfolioId + '/updateDescription', {
            rows: 10,
            okControl: true,
            cancelControl: true,
            submitOnBlur: true,
            onEnterEditMode: function(form, value) {
                $('portfolioDescriptionSection').removeClassName('empty').addClassName('editing');
                var pEl = $$('#portfolioDescriptionSection > .description')[0];
                pEl.innerHTML = pEl.innerHTML.strip();
            },
            onLeaveEditMode: function(form, value) {
                $('portfolioDescriptionSection').removeClassName('editing');
                if ($$('#portfolioDescriptionSection > .description')[0].innerHTML.blank()) {
                    $('portfolioDescriptionSection').addClassName('empty');
                }
            }
        });

        var tagsButton = new YAHOO.widget.Button("tagsButton");
        var viewButton = new YAHOO.widget.Button("viewButton");

        var savePortfolio = new YAHOO.widget.Button("savePortfolio");
        
        savePortfolio.on("click", function(){
            this.saveContent();
            this.initSortable();        	
        }.bind(this));

        this.saveContent();
        this.initSortable();

    },
    
    buildPortfolio: function(json) {
    	
    	json.each(function(item){
    		this._recurseItem(item, 0);
    	}.bind(this));
    	
    },
    
    /** @private */
    _getObjectKeys: function(obj) {
    	var keys = "";
    	
    	for(var key in obj){
    		keys += " " + key;
    	}
    	
    	return keys;
	},
    
	/** @private */
	_isEmpty: function(obj) {
		var keys = true;
		
    	for(var key in obj){
    		keys = false;
    		break;
    	}
		
    	return keys;
	},
	
	/** @private */
	_hasParentId: function(obj) {
		var found = false;
		
		for(var key in obj) {
			if(key == 'parentId') {
				found = true;
				break;
			}
		}
		
		return found;
	},
	
	/** @private */
	
	_stringMult: function(str, num) {
		return num ? Array(num + 1).join(str) : "";
	},
	
    /** @private */
    _recurseItem: function(item, level) {
    	
    	if(item instanceof Object) {
    		
    		if(!this._isEmpty(item)) {
    			
    			if(!this._hasParentId(item)) {
	    			for(var i=0; i<item.size(); ++i) {
	    				var it = item[i];
	    				this._recurseItem(it, (level+1));
	    			}
    			}
    			else {
    				
    				// EPF.log(this._stringMult("+", level) + "==> " + this._getObjectKeys(item));

    				if (item.type == 'heading1') {

    					var e = this.addH1(item.title, $(item.parentId), (level+1));
	                } else if (item.type == 'entry') {
	                    // var e = this.addEntry(item.entryKeyId, $(item.parentId), (level+1));
	                	
	                    new Ajax.Request('/portfolio/entries', {
	                        parameters : { entryKeyId : item.entryKeyId },
	                        onSuccess : function(transport) {
	                                // EPF.log(transport);
	                            var resp = transport.responseJSON;
	                            var entries = resp.data.entries;
	                            entries.each(function(entry) {
	                                
	                            	try {
	                            		
	                                	this.addEntry(entry, $(item.parentId), (level+1));
	                                } catch (e) {
	                                	EPF.log(e);
	                                }
	                                
	                                this.saveContent();
	                                this.initSortable();
	                                
	                            }.bind(this));
	                        }.bind(this)
	                    });
	                    
	                }
    	 
    			}
    			
    		}
    		
    	}
    	else if (item instanceof Array) {
    		this._recurseItem(item, (level+1));
    	}
    	
    },
    
    /** @private */
    chomp: function (raw_text) {
    	return raw_text.replace(/(^( )+)|((\n|\r| )+$)/, '');
    },

    /** @private */
    handleAddContentButtonClicked: function(parent_area, level) {
    	
        var html = EPF.template.portfolio.portfolioEdit.addContentModal();
        var modal = new EPF.widget.modal.Modal({
            title: 'Add Content',
            width: 250,
            content: html
        });
        
    	var addH1Button = new YAHOO.widget.Button("addSubHeading");
        addH1Button.on("click", function() {
            this.addH1('', parent_area, level);
            
            $('contentWrapper').scrollTop = $('content').getHeight();
            
            this.saveContent();
            this.initSortable();
            
            modal.close();
        }.bind(this));
        
        var addNewElementButton = new YAHOO.widget.Button("addNewElement");
        addNewElementButton.on("click", function() {
            modal.close();
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

        var addExistingElementButton = new YAHOO.widget.Button("addExistingElement");
        addExistingElementButton.on("click", function() {
            modal.close();
            new EPF.controller.portfolioEdit.SelectFromExistingView({
                onAdd: function(entryKeyIds) {
                    new Ajax.Request('/portfolio/entries', {
                        parameters : { entryKeyId : entryKeyIds },
                        onSuccess : function(transport) {
                                // EPF.log(transport);
                            var resp = transport.responseJSON;
                            var entries = resp.data.entries;
                            entries.each(function(entry) {
                                
                            	try {
                            		// EPF.log("handleAddContentButtonClicked(): - Adding Entry - parent_area.id = " + parent_area.id);
                                	this.addEntry(entry, parent_area, level);
                                } catch (e) {
                                	EPF.log(e);
                                }
                                
                                this.saveContent();
                                this.initSortable();
                                
                            }.bind(this));
                        }.bind(this)
                    });
                }.bind(this)
            });
        }.bind(this));

    },

    /** @private */
    resizePanes: function() {
        var height = document.viewport.getHeight() - $('toolbar').getHeight();
        $('contentWrapper').setStyle({height: height + 'px'});
    },

    /** @private */
    initSortable: function() {
    	
        // EPF.log('init sortable');
        Sortable.destroy('menu');
        Sortable.create('menu', {tree:true,
        	ghosting: true,
        	scroll:window, 
        	hoverclass: 'hoverround',
            handle: $$('#draggable'),
            onUpdate: function(container) {
                this.saveContent();
                this.initSortable();
            }.bind(this)
        });
        
        this.hasSortable = true;
        
        this.hideFirstNodeDelete();
        
    },
    
    isChildOf: function(suspectChild, parent) {
    	    	
    	if(parent.hasChildNodes()) {
    		var childs = parent.childNodes;
    		for(var i=0; i<childs.length; ++i) {
    			
    			if(childs[i].id == suspectChild.id) {
    				
    				return true;
    			}
    			else {
    				var ret = this.isChildOf(suspectChild, childs[i]);
    				    				
    				if(ret) {
    					return true;
    				}
    				
    			}
    		}
    	}
    	
    	return false;
    	
    },
    
    addH1: function(text, parent_area, level) {
    	
        var e = Element.fromText(EPF.template.portfolio.portfolioEdit.h1({title: text ? text : 'New Heading'}));
        
        if(parent_area == null || $(parent_area) == null) {
        	parent_area = $('menu');
        }

        if(!Object.isElement(parent_area)) {
        	parent_area = $(parent_area);
        }
        
        if(existing_resp != null) {
        	
        	e.down('.loading_elements').hide();
        	e.down('.add_content').show();

        }
    	       
        e.down('.delete').observe('click', this.onDeleteHeadingClick.bind(this));
        
        var ipe = new Ajax.InPlaceEditor(e.down('.item > span'), '/portfolio/' + this.portfolioId + '/updateHeading', {
            okControl: 'link',
            cancelControl: 'link',
            
            submitOnBlur: true,
            callback: function(form, value) {
                var li = e;
                
                if(value.length == 0) {
                	value = "New Heading";
                }
                
                var index = li.contentItem.title = value;
                
                this.saveContent();
                this.initSortable();
                
                // e.down('.addHeading').show();
                
                return 'value=' + value;
            }.bind(this)
        });
        
        if (!text) {
        	
        	// e.down('.addHeading').hide();
            ipe.enterEditMode();
            
        }
        
        e.id = 'item_' + (this.num);

        var e_content = e.down('.edit_list');
        var pId = parent_area.id;
        
        e_content.id = 'content_' + this.num;

        if(pId != 'menu') {
        	pId = e_content.id;
        }
        
        e.contentItem = {type:'heading1', title: text, id: e.id, parentId: pId};

        e.down('.add_content').observe('click', function(event) {
            event.stop();
            this.handleAddContentButtonClicked(e_content, (level+1));
        }.bind(this));
        
        e.down('.new_heading').observe('click', function(event){
        	event.stop();
        	this.addH1('', parent_area, level);
            $('contentWrapper').scrollTop = $('content').getHeight();
            
            this.saveContent();
            this.initSortable();
            
        }.bind(this));
        
        e.down('a.delete').id = 'delete_' + this.num;
        
        parent_area.appendChild(e);
        
        this.heading_num++;
        this.num++;
        
        this.saveContent();
        this.initSortable();
        
        return e;

    },

    addEntry: function(entry, parent_area, level) {
    	
        var e = Element.fromText(EPF.template.portfolio.portfolioEdit.entry({entry: entry}));
        
        if(parent_area == null || $(parent_area) == null) {
        	parent_area = $('menu');
        }
        
        if(!Object.isElement(parent_area)) {
        	parent_area = $(parent_area);
        }
       
        e.down('a.delete').id = 'delete_' + this.num;
        
        e.down('a.delete').observe('click', this.onDeleteEntryClick.bind(this));

        e.down('.more_info').observe('click', function(event) {
            new EPF.widget.ElementWindow({ entryKeyId : entry.entryKeyId });
        });
        
        e.id = 'item_' + this.num;
        
        var pId = parent_area.id;

        /*
        if(pId.match(/item_/) != null) {
        	var ct = $(pId).down('.edit_list');
        	
        	if(ct != null) {
        		parent_area = ct;
        		pId = parent_area.id;
        	}
        	else {
        		EPF.log('Unable to find .edit_list under ' + pId);
        	}
        	
        }
        */
        
        e.contentItem = {type:'entry', entryKeyId: entry.entryKeyId, id: e.id, parentId: pId};

        parent_area.appendChild(e);
        
        this.num++;
        
        this.saveContent();
        this.initSortable();
        
        return e;
    },
    
    /** @private */
    hideFirstNodeDelete: function() {
    	
    	if(this.hasSortable) {
	    	
	    	var cereal = Sortable.serialize('menu');
	        var firstId = null;
	        
			// EPF.log(cereal);
			
			var parts = cereal.split('&');
			
			if(parts != null && parts.length > 0) {
				parts = parts[0].split('=');
				
				try {
	    			
	    			if(parts != null && parts.length > 0) {
	    				firstId = parseInt(parts[1]);
	    				
	    				if($('delete_' + firstId) != null) {
	    				
	    					$('delete_' + firstId).hide();
	    				
	    				}
	    				
	    				$$('a.delete').collect(function(e){
	    					if(e.id != 'delete_' + firstId) {
	    						e.show();
	    					}
	    				}.bind(this));
	    				
	    			}
				}
				catch(ex) {
					EPF.log(ex);
				}
			}
    	
    	}
    	
    },

    onDeleteHeadingClick: function(event) {
        event.stop();
    	
        if(this.hasSortable) {
    		
        	var cereal = Sortable.serialize('menu');
	        var firstId = null;
	        
    		// EPF.log(cereal);
    		
    		var parts = cereal.split('&');
    		
    		if(parts != null && parts.length > 0) {
    			parts = parts[0].split('=');
    			
    			try {
	    			
	    			if(parts != null && parts.length > 0) {
	    				firstId = parseInt(parts[1]);
	    				
	    			}
    			}
    			catch(ex) {
    				
    			}
    		}
    		
	        var e = event.element().up('.edit_link');
	        
	        if(firstId != null && e != null) {

	        	parts = e.id.split('_');
	        	
	        	if(parts != null && parts.length > 0) {
		        
	        		try {
		        		
	        			if(parseInt(parts[1]) == firstId) {
	        				EPF.log("Attempt to delete head node detected");
		        		}
		        		else {
					        
		        			e.remove();
					        
					        this.saveContent();
					        this.initSortable();
		        		
		        		}
	        		}
	        		catch(ex) {
	        			EPF.log(ex);
	        		}
	        	}		        
	        }
        }
    },

    onDeleteEntryClick: function(event) {
        event.stop();
        
        var e = event.element().up('.entry');
        e.remove();
        
        this.num--;
        
        this.saveContent();
        this.initSortable();
        
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
            }.bind(this));
        }.bind(this));
    },
    
    saveContent: function() {
        
    	if(this.hasSortable) {
    		
    		this.isSaving = true;
    		
    		var status = $('statusODoom');
    		
    		status.show();

    		var tree = Sortable.tree($('menu')).children.map( function (item) {
    	    	
    	    	var ci = null;
			    var index = '';
			    var node = item;
			    
			    do {
			      
			  	  if (node.id) { 
			  		  index = '[' + node.position + ']' + index;
			  	  }
			  	  
			    } while ((node = node.parent) != null);

			    ci = index;
			    
    	    	var li = $('item_' + item.id);
	    		
	    		if(li != null) {
	    			
	    			var contentItem = li.contentItem;
	    			
    	        	return ['menu' + ci + "=" + encodeURIComponent(Object.toJSON(contentItem))].concat(item.children.map(arguments.callee));
	    		
	    		}
	    		
    	    }).flatten().join('&');

    	    // new Effect.Highlight('statusODoom');
    	    
	        new Ajax.Request('/portfolio/' + this.portfolioId + '/saveContent', {
	            method: 'post',
	            parameters: {json: tree},
	            onSuccess: function(transport) {
	            	$('statusODoom').hide();
	            }
	        });
    	
    	}
    },
    
    dump: function (arr,level) {
    	
    	var dumped_text = "";
    	if(!level) level = 0;

    	//The padding given at the beginning of the line.
    	var level_padding = "";
    	for(var j=0;j<level+1;j++) level_padding += "    ";

    	if(typeof(arr) == 'object') { //Array/Hashes/Objects
    		for(var item in arr) {
    			var value = arr[item];
    	 
    			if(typeof(value) == 'object') { //If it is an array,
    				dumped_text += level_padding + "'" + item + "' ...\n";
    				dumped_text += dump(value,level+1);
    			} else {
    				dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
    			}
    		}
    	} else { //Stings/Chars/Numbers etc.
    		dumped_text = "===>"+arr+"<===("+typeof(arr)+")";
    	}
    	
    	return dumped_text;
    } 

});

/**
 * TODO merge with the one grom guide.js
 */
EPF.controller.portfolioEdit.SelectFromExistingView = Class.create(/** @lends EPF.widget.portfolioEdit.SelectFromExistingView.prototype */{

    /**
     * @constructs
     * @param config
     */
    initialize: function(config) {

    	if(existing_resp == null) {
    		alert("Please wait while your existing elements are loaded.");
    		return;
    	} 
    	
    	var resp = existing_resp;
        var matches = resp.data.matches;
        
   	
        this.modal = new EPF.widget.modal.Modal({
            title: 'Select from existing elements',
            width: 600,
            content: EPF.template.portfolio.portfolioEdit.selectExisting({entries: matches})
        });

        this.contentParent = this.modal.getContentParent();
        this.contentParent.down('.addButton').observe('click', function(event) {

            var entryKeyIds = this.getSelectedEntryKeyIds();
            if (entryKeyIds.size() == 0) {
                new dialog.Alert('You must select at least one element.').show();
                return;
            }
            this.modal.close();
            if (typeof config.onAdd == 'function') {
                config.onAdd(entryKeyIds);
            }
        }.bind(this));

        this.contentParent.down('.cancelLink').observe('click', function(event) {
            event.stop();
            this.modal.close();
        }.bind(this));

        var searchInputElement = this.contentParent.down('.searchEntries .wrapper input[type="text"]');

        searchInputElement.observe('focus', function(event) {
            this.previous().hide();
        });
        searchInputElement.observe('blur', function(event) {
            if ($F(this).length == 0) {
                this.previous().show();
            }
        });

        new Form.Element.Observer(
                searchInputElement,
                0.75, // 750 milliseconds
                        function(el, value) {
                            if (value.length > 0) {
                                el.previous().hide();
                            }
                            this.searchEntries(value);
                        }.bind(this)
                );

        this.contentParent.down('.searchEntries .wrapper img').observe('click', function(event) {
            searchInputElement.clear();
            searchInputElement.focus();
        });

        this.sizeFluidElement();
                
    },


    /** @private */
    sizeFluidElement : function() {
        try {
            var targetContentHeight = this.modal.getContentParent().getHeight();
            var actualContentHeight = this.modal.getContentParent().down('.selectFromExisting').getHeight();
            var diff = targetContentHeight - actualContentHeight;

            if (diff < 0) {
                var fluidElement = this.modal.getContentParent().down('.fluid');
                var fluidElementHeight = parseInt(fluidElement.getStyle('height'));
                var newFluidElementHeight = fluidElementHeight + diff;
                fluidElement.style.height = newFluidElementHeight + 'px';
            }
        } catch(e) {
            EPF.log(e);
        }
    },

    searchEntries: function(query) {
        this.contentParent.select('.elementEntry .entryTitle').each(function(e) {
            var entryName = e.innerHTML;
            if (query.blank() || entryName.toLowerCase().indexOf(query.toLowerCase()) != -1) {
                e.up('.elementEntry').show();
            } else {
                e.up('.elementEntry').hide();
            }
        });
        var numEntries = this.contentParent.select('.elementEntry').findAll(
                function(e) {
                    return e.visible();
                }).size();
        this.contentParent.down('.numEntries').update(EPF.template.portfolio.portfolioEdit.selectExistingNumEntries({numEntries: numEntries}));
    },

    getSelectedEntryKeyIds : function() {

        var selectedEls = this.contentParent.select('.elementEntryCheckBox').findAll(
                function(e) {

                    if ("" + e.checked == 'true') {
                        return true;
                    }
                }
        	);

        return selectedEls.collect(function(el) {
            return el.id.split('_')[1];
        });

    }
});
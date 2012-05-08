/* $Name:  $ */
/* $Id: guide.js,v 1.21 2011/02/24 21:49:16 ajokela Exp $ */
EPF.namespace('controller.guide');

EPF.controller.guide.Main = Class.create( /** @lends EPF.controller.guide.Main.prototype */ {
    
    /** 
     * Main controller for Collection Guide page.
     * 
     * @constructs 
     * @param config
     * @param {String|Number} config.guideId
     */
    initialize : function(config) {
        this.guideId = config.guideId;

        var tabView = new YAHOO.widget.TabView('navset'); 

        new Ajax.Request('/guide/json/'+this.guideId, {
            onComplete: function(transport){
                var resp = transport.responseJSON;
                this.guide = new EPF.model.guide.Guide(resp.data.guide);
                new EPF.widget.guide.GuideView(this.guide);
                this.updateStatus();
                

                this.guide.observeEntries({
                    onAdd: function(entry) {
                        this.updateStatus();
                    }.bind(this),
                    onRemove: function(entry) {
                        this.updateStatus();
                    }.bind(this)
                });
                
                $('aboutLink').observe('click', function(event){
                  new EPF.widget.modal.Modal({
                      title: 'About',
                      width: 500,
                      content: EPF.template.guide.about({description: resp.data.guide.description})
                  });    
                });
            }.bind(this)
        });
    },
    
    updateStatus: function() {
        $('statusNumElements').update(this.guide.getNumberOfElements());
        $('statusNumDefs').update(this.guide.getNumberOfCompletedDefs() + ' of ' + this.guide.getNumberOfDefs());
    }
});

EPF.namespace('widget.guide');

EPF.widget.guide.GuideView = Class.create( /** @lends EPF.widget.guide.GuideView.prototype */ {
    
    /** 
     * Collection guide view widget.
     * 
     * @constructs 
     * @param {EPF.model.guide.Guide} guide
     */
    initialize : function(guide) {
        this.guide = guide;
        this.containerEl = $('enterContent');
        this.showOverview();
    },
    
    showOverview: function() {
        try {
        var cats = this.guide.getCats().collect(function(cat){
            return {
                title: cat.title,
                subcats: cat.subcats.collect(function(subcat) {
                    return {
                        id: subcat.id,
                        title: subcat.title,
                        numDefs: subcat.defs.size(),
                        numCompletedDefs: subcat.defs.findAll(function(def){return def.entries.size() > 0;}).size(),
                        numElements: subcat.defs.pluck('entries').flatten().size()
                    };
                })
            }
        });
        
        var html = EPF.template.guide.overview({
            cats: cats,
            enterTip: this.guide.json.enterTip
        });
        this.containerEl.update(html);

        $$('.subcat').each(function(e){
            var subcatId = e.id.split('_')[1];
            e.down('.openSubcat').observe('click', function(event){
                event.stop();
                this.showSubcat(subcatId);
            }.bind(this));
        }.bind(this));
        } catch(e){EPF.log(e)}
    },
    
    showSubcat: function(subcatId) {
        try {
        new EPF.widget.guide.GuideSectionView({
            guide: this.guide,
            subcatId: subcatId,
            containerEl: this.containerEl,
            guideView: this
        });
        } catch(e){EPF.log(e)}
    }
});

EPF.widget.guide.GuideSectionView = Class.create( /** @lends EPF.widget.guide.GuideSectionView.prototype */ { 
    
    /** 
     * @constructs 
     * @param config
     * @param {EPF.model.guide.Guide} config.guide
     * @param {Number} config.subcatId
     * @param {HTMLElement} config.containerEl
     * @param {EPF.widget.guide.GuideView} config.guideView
     */
    initialize: function(config) {
        this.guide = config.guide;
        this.subcatId = config.subcatId;
        this.containerEl = config.containerEl;
        this.guideView = config.guideView;
        
        var subcat = this.guide.getSubcatById(this.subcatId);
        var cat = this.guide.getCatById(subcat.parentCategoryId);
        
        var html = EPF.template.guide.subcat({cat: cat, subcat: subcat});
        this.containerEl.update(html);
        
        var slideWindow = new EPF.widget.SlideWindow({
            width: 886,
            containerEl:this.containerEl.down('.subcatDefs'),
            onChange: function(pageNum) {
                var html = EPF.template.guide.pageState({currentPage: pageNum, numPages: subcat.defs.size()});
                this.containerEl.down('.pageState').update(html);
            }.bind(this)
        });
        
        this.containerEl.down('button.doneSection').observe('click', function(event){
            event.stop();
            this.guideView.showOverview();
        }.bind(this));
        
        this.containerEl.down('button.prev').observe('click', function(event){
            event.stop();
            slideWindow.prev();
        }.bind(this));
        
        this.containerEl.down('button.next').observe('click', function(event){
            event.stop();
            slideWindow.next();
        }.bind(this));
        
        this.containerEl.select('.guideElementSection').each(function(sectionEl){
            new EPF.widget.guide.GuideElementSectionView({
                guide: this.guide,
                sectionEl: sectionEl
            });
        }.bind(this));
    }
});

EPF.widget.guide.GuideElementSectionView = Class.create( /** @lends EPF.widget.guide.GuideElementSectionView.prototype */{ 
    
    /** 
     * @constructs 
     * @param config
     * @param {EPF.model.guide.Guide} config.guide
     * @param {HTMLElement} config.sectionEl
     */
    initialize: function(config) {
        this.sectionEl = config.sectionEl;
        this.guide = config.guide;
        
        var guideElementId = this.sectionEl.id.split('_')[1];
        this.def = this.guide.getDefById(guideElementId);
        
        var addButton = new YAHOO.widget.Button(this.sectionEl.down('.toolbar .add button'), {
            type: 'menu',
            disabled: this.def.viewOnly && this.def.autoImport,
            menu: [
                {
                    text: 'Create a new element',
                    disabled: this.def.viewOnly,
                    onclick: { fn: function(oEvent) {this.showElementWindow()}.bind(this) }
                },
                {
                    text: 'Use an existing element',
                    disabled: this.def.autoImport,
                    onclick: { fn: function(oEvent) {this.showSelectExisting(guideElementId)}.bind(this) }
                }
            ]
        });
        
        var detachButton = new YAHOO.widget.Button(this.sectionEl.down('.toolbar .detach button'), {
            disabled: this.def.autoImport,
            onclick: {fn: function(oEvent) {

            	var selectedEls = this.sectionEl.select('.elementEntryCheckBox').findAll(
                		function(e){
                			
                			if ("" + e.checked == 'true') {
                				return true;
                			}
                		}
                	);

            	var entryKeyIds = selectedEls.collect(function(el){return el.id.split('_')[1];});
            	
                if (entryKeyIds.size() == 0) {
                    new dialog.Alert('You must select at least one element.').show();
                    return;
                }
                
                new Ajax.Request('/guide/element/detach', {
                    parameters : {
                        guideDefId : guideElementId,
                        entryKeyId : entryKeyIds
                    },
                    onSuccess : function(transport) {

                    	entryKeyIds.each(
                        		function(entryKeyId){
                        			
                                    // var el = this.sectionEl.down('#elementEntry_' + entryKeyId);
                                    // el.remove();
                        			
                        			var els = this.sectionEl.select('[id="elementEntry_' + entryKeyId + '"]').findAll(
                        				
                        					function (e) {
                        						e.fade({afterFinish:function(effect){effect.element.remove()}});
                        						
                        					}
                        					
                        				);
                        			
                        			this.guide.removeEntry(entryKeyId);
                        			
                        		}.bind(this)
                        	);
                        
                    	
                    }.bind(this)
                });
            	
            }.bind(this)}
        });
        
        this.sectionEl.select('.elementEntry').each(function(e) {
            this.onEntryRendered(e);
        }.bind(this));
        
        this.guide.observeEntries({
            guideElementId: guideElementId,
            onAdd: function(entry) {
            	
                var html = EPF.template.guide.elementEntry({entry:entry});
                var el = Element.fromText(html);
                this.sectionEl.down('.elementEntryList').insert({top: el});
                this.onEntryRendered(el);
                
                try {
                	el.highlight();
                }
                catch(e){
                	EPF.log(e);
                }
                
            }.bind(this),
            onRemove: function(entry) {
            	
            	/*
                var el = this.sectionEl.down('#elementEntry_' + entry.entryKeyId);
                el.fade({afterFinish:function(effect){effect.element.remove()}});
                */
            	
            }.bind(this),
            onUpdate: function(entry) {
                var oldEl = this.sectionEl.down('#elementEntry_' + entry.entryKeyId);
                var html = EPF.template.guide.elementEntry({entry:entry});
                var newEl = Element.fromText(html);
                this.sectionEl.down('.elementEntryList').replaceChild(newEl, oldEl);
                this.onEntryRendered(newEl);
            }.bind(this)
        });
    },
    
    /** @private */
    onEntryRendered: function(el) {
        var entryKeyId = el.id.split('_')[1];
        el.observe('click', function(event) {
            if (event.target.type != 'checkbox') {
                event.stop();
                new EPF.widget.ElementWindow( {
                    entryKeyId: entryKeyId
                });
            }
        });
    },
    
    /** @private */
    showElementWindow: function() {
        new EPF.widget.ElementWindow( { 
            elementDefId : this.def.elementDef.id,
            onCreate: function(entryKeyId) {
                new Ajax.Request('/guide/element/attach', {
                    parameters: {
                        guideDefId: this.def.id,
                        entryKeyId: entryKeyId
                    },
                    onComplete: function (transport){
                        var resp = transport.responseJSON;
                        var entry = resp.data.entries[0];
                        this.guide.addEntry(this.def.id, entry);
                    }.bind(this)
                });
            }.bind(this)
        });
    },
    
    /** @private */
    showSelectExisting: function(guideElementId) {
        new Ajax.Request('/element/search', {
            parameters : {
                searchType : 'elementDefinition',
                searchArgs : this.def.elementDef.id
            },
            onComplete : function(transport) {
                var resp = transport.responseJSON;
                var matches = resp.data.matches;
                var currentEntries = this.def.entries;
                var currentEntryKeyIds = currentEntries.pluck('entryKeyId');
                var attachableEntries = matches.reject(function(entry){return currentEntryKeyIds.include(entry.entryKeyId);});
                
                new EPF.widget.guide.SelectFromExistingView({
                    entries: attachableEntries,
                    guide: this.guide,
                    guideDef: this.def
                });
            }.bind(this)
        });
    }
});

EPF.widget.guide.SelectFromExistingView = Class.create( /** @lends EPF.widget.guide.SelectFromExistingView.prototype */{ 
    
    /** 
     * @constructs 
     * @param config
     * @param config.entries
     * @param {EPF.model.guide.Guide} config.guide
     * @param config.guideDef
     */
    initialize: function(config) {
        this.guide = config.guide;
        this.guideDef = config.guideDef;
    
        var modal = new EPF.widget.modal.Modal({
            title: 'Select from existing elements', 
            width: 600,
            content: EPF.template.guide.selectExisting({entries: config.entries})
        });
        
        this.contentParent = modal.getContentParent();
    
        this.contentParent.down('.addButton').observe('click', function(event){
            
        	var entryKeyIds = this.getSelectedEntryKeyIds();
            
            if (entryKeyIds.size() == 0) {
                new dialog.Alert('You must select at least one element.').show();
                return;
            }
            new Ajax.Request('/guide/element/attach', {
                parameters : {
                    guideDefId : this.guideDef.id,
                    entryKeyId : entryKeyIds
                },
                onSuccess : function(transport) {
                    modal.close();
                    var resp = transport.responseJSON;
                    var entries = resp.data.entries;
                    entries.each(function(entry){
                        this.guide.addEntry(this.guideDef.id, entry);
                    }.bind(this));
                }.bind(this)
            });
        }.bind(this));
        
        this.contentParent.down('.cancelLink').observe('click', function(event){
            event.stop();
            modal.close();
        });
        
        var searchInputElement = this.contentParent.down('.searchEntries .wrapper input[type="text"]');

        searchInputElement.observe('focus', function(event){
            this.previous().hide();
        });
        searchInputElement.observe('blur', function(event){
            if ($F(this).length == 0) {
                this.previous().show();   
            }
        });
        
        new Form.Element.Observer(
          searchInputElement,
          0.2,  // 200 milliseconds
          function(el, value){
            if (value.length > 0) {
                el.previous().hide();
            }
            this.searchEntries(value);
          }.bind(this)
        );
        
        this.contentParent.down('.searchEntries .wrapper img').observe('click', function(event){
            searchInputElement.clear();
            searchInputElement.focus();
        });

    },
    
    searchEntries: function(query) {
        this.contentParent.select('.elementEntry').each(function(e){
            var entryName = e.down('.entryTitle').innerHTML;
            if (query.blank() || entryName.toLowerCase().indexOf(query.toLowerCase()) != -1) {
                e.show();
            } else {
                e.hide();
            }
        });
        var numEntries = this.contentParent.select('.elementEntry').findAll(function(e){return e.visible();}).size();
        this.contentParent.down('.numEntries').update(EPF.template.guide.selectExistingNumEntries({numEntries: numEntries}));
    },

    getSelectedEntryKeyIds : function() {
        
    	var selectedEls = this.contentParent.select('.elementEntryCheckBox').findAll(
    		function(e){
    			
    			if ("" + e.checked == 'true') {
    				return true;
    			}
    		}
    	);
    	    	
    	return selectedEls.collect(function(el){return el.id.split('_')[1];});
    	
    }
});

EPF.namespace('model.guide');

EPF.model.guide.Guide = Class.create( /** @lends EPF.model.guide.Guide.prototype */ {
    
    /** 
     * @constructs 
     * @param json
     */
    initialize: function(json) {
        this.json = json;
        this.listeners = [];

        document.observe('epf:element.deleted', function(event) {
            var entryKeyId = event.memo.entryKeyId;
            this.removeEntry(entryKeyId);
        }.bind(this));
    },

    getDefsBySubcatId: function(id) {
        return this.defHash.get(id);
    },
    
    getCats: function() {
        return this.json.cats;
    },
    
    getCatById: function(id) {
        return this.json.cats.find(function(cat){return cat.id == id;});
    },

    getSubcatById: function(id) {
        return this.json.cats.pluck('subcats').flatten().find(function(subcat){return subcat.id == id;});
    },

    getDefById: function(id) {
        return this.json.cats.pluck('subcats').flatten().pluck('defs').flatten().find(function(def){return def.id == id;});
    },
    
    /**
     * @returns {Number}
     */
    getNumberOfElements: function() {
        
    	return this.json.cats.pluck('subcats').flatten().pluck('defs').flatten().pluck('entries').flatten().size();
    },
    
    /**
     * @returns {Number}
     */
    getNumberOfDefs: function() {
        return this.json.cats.pluck('subcats').flatten().pluck('defs').flatten().size();
    },
    
    /**
     * @returns {Number}
     */
    getNumberOfCompletedDefs: function() {
        return this.json.cats.pluck('subcats').flatten().pluck('defs').flatten().findAll(function(def){return def.entries.size() > 0;}).size();
    },
    
    addEntry: function(defId, entry) {
        var def = this.getDefById(defId);
        def.entries.splice(0, 0, entry);
        
        this.notify('onAdd', defId, entry);
    },
    
    removeEntry: function(entryKeyId){
        var defs = this.json.cats.pluck('subcats').flatten().pluck('defs').flatten();
        defs.each(function(def){
            var entry = def.entries.find(function(entry) { return entry.entryKeyId == entryKeyId });
            if (entry) {
                var index = def.entries.indexOf(entry);
                def.entries.splice(index,1);
                this.notify('onRemove', def.id, entry);
            }
        }.bind(this));
    },
    
    /** @private */
    notify: function(method, defId, entry){
        this.listeners.each(function(listener){
            if (!listener.guideElementId || listener.guideElementId == defId) {
                if (Object.isFunction(listener[method])) {
                    listener[method](entry);
                }
            }
        });
    },
    
    /**
     * @param listener
     * @param [listener.guideElementId] a specific guideElementId to listen to. all if not specified.
     * @param {Function} [listener.onAdd]
     * @param {Function} [listener.onRemove]
     * @param {Function} [listener.onUpdate]
     */
    observeEntries: function(listener){
        this.listeners.push(listener);
    }
});


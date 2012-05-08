/* $Name:  $ */
/* $Id: Loader.js,v 1.27 2010/11/30 04:36:17 shee0109 Exp $ */
EPF.namespace('util');

EPF.util.Loader = Class.create( {
    initialize : function(version) {
    	this.version = version;
        this.loader = this._createLoader(version);
    },
    _createLoader : function(version) {
        var myLoader = new YAHOO.util.YUILoader( {
            base : "/js/yui/2.8.1/build/",
            loadOptional : true
        });
        
        myLoader.addModule( {
            name : 'dhtmlcalendar-main',
            type : 'js',
            fullpath : '/js/jscalendar-1.0/calendar.js?version=' + version
        });
        myLoader.addModule( {
            name : 'dhtmlcalendar-en',
            type : 'js',
            fullpath : '/js/jscalendar-1.0/lang/calendar-en.js?version=' + version
        });
        myLoader.addModule( {
            name : 'dhtmlcalendar-css',
            type : 'css',
            fullpath : '/js/jscalendar-1.0/skins/aqua/theme.css?version=' + version
        });
        myLoader.addModule( {
            name : 'dhtmlcalendar',
            type : 'js',
            fullpath : '/js/jscalendar-1.0/calendar-setup.js?version=' + version,
            requires : [ 'dhtmlcalendar-main', 'dhtmlcalendar-en', 'dhtmlcalendar-css' ]
        });
        myLoader.addModule( {
            name : 'share-css',
            type : 'css',
            fullpath : '/css/modules/share.css?version=' + version
        });
        myLoader.addModule( {
            name : 'share',
            type : 'js',
            fullpath : '/js/modules/share.js?version=' + version,
            requires : [ 'button', 'menu', 'treeview', 'layout', 'resize', 'share-css', 'portfolioTools' ]
        });
        myLoader.addModule( {
            name : 'elementWindow-css',
            type : 'css',
            fullpath : '/css/modules/elementWindow.css?version=' + version
        });
        myLoader.addModule( {
            name : 'elementWindow',
            type : 'js',
            fullpath : '/js/modules/elementWindow.js?version=' + version,
            requires : [ 'resize', 'button', 'menu', 'containercore', 'dom', 'element', 'event', 'progressbar', 'elementWindow-css' ]
        });
        myLoader.addModule( {
            name : 'collection-css',
            type : 'css',
            fullpath : '/css/modules/collection.css?version=' + version
        });
        myLoader.addModule( {
            name : 'collection',
            type : 'js',
            fullpath : '/js/modules/collection.js?version=' + version,
            requires : [ 'treeview', 'button', 'menu', 'resize', 'connection', 'layout', 'elementWindow', 'collection-css' ]
        });

        myLoader.addModule( {
            name : 'communityCollection',
            type : 'js',
            fullpath : '/js/modules/communityCollection.js?version=' + version,
            requires : [ 'treeview', 'button', 'menu', 'resize', 'connection', 'layout', 'elementWindow', 'collection-css' ]
        });

        myLoader.addModule( {
            name : 'login-css',
            type : 'css',
            fullpath : '/css/modules/login.css?version=' + version
        });
        myLoader.addModule( {
            name : 'login',
            type : 'js',
            fullpath : '/js/modules/login.js?version=' + version,
            requires : [ 'login-css' ]
        });
        myLoader.addModule( {
            name : 'welcome-css',
            type : 'css',
            fullpath : '/css/modules/welcome.css?version=' + version
        });
        myLoader.addModule( {
            name : 'umProfile-css',
            type : 'css',
            fullpath : '/css/modules/umProfile.css?version=' + version
        });
        myLoader.addModule( {
            name : 'umProfile',
            type : 'js',
            fullpath : '/js/modules/umProfile.js?version=' + version,
            requires : [ 'elementWindow', 'umProfile-css' ]
        });
        myLoader.addModule( {
            name : 'guide-css',
            type : 'css',
            fullpath : '/css/modules/guide.css?version=' + version
        });
        myLoader.addModule( {
            name : 'guide',
            type : 'js',
            fullpath : '/js/modules/guide.js?version=' + version,
            requires : [ 'progressbar', 'tabview', 'elementWindow', 'guide-css' ]
        });
        myLoader.addModule( {
            name : 'portfolioTools-css',
            type : 'css',
            fullpath : '/css/modules/portfolioTools.css?version=' + version
        });
        myLoader.addModule( {
            name : 'portfolioTools',
            type : 'js',
            fullpath : '/js/modules/portfolioTools.js?version=' + version,
            requires : [ 'calendar', 'portfolioTools-css' ]
        });
        myLoader.addModule( {
            name : 'viewPortfolio',
            type : 'js',
            fullpath : '/js/modules/viewPortfolio.js?version=' + version,
            requires : [ 'button', 'portfolioTools' ]
        });
        myLoader.addModule( {
            name : 'portfolioEdit-css',
            type: 'css',
            fullpath: '/css/modules/portfolioEdit.css?version=' + version
        });
        myLoader.addModule( {
            name : 'portfolioEdit',
            type: 'js',
            fullpath: '/js/modules/portfolioEdit.js?version=' + version,
            requires: ['portfolioEdit-css', 'elementWindow']
        });

        return myLoader;
    },
    require : function(modules, onLoaded) {
        this.loader.insert( {
            require : modules,
            onSuccess : function (){
              if (typeof onLoaded == 'function') {
                  onLoaded();
              }
            }
        });
    }
});

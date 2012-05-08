/* $Name:  $ */
/* $Id: EPF.js,v 1.17 2011/01/31 03:37:12 shee0109 Exp $ */
/**
 * Global object for eportfolio.
 * @namespace
 * @type Object
 */
var EPF = {
    
    /**
     * @param {String} namespace
     */
    namespace : function(namespace){
        var names = namespace.split('.');
        return $A(names).inject(EPF, function(o, name){
            o[name] = o[name] || {};
            return o[name];
        });
    },
    
    /**
     * @param {String} text
     */
    log : function(text) {
        if(typeof(console) !== 'undefined' && console != null) {
            console.log(text);
        }
    },
    
    /**
     * @param {String|Array} modules
     * @param {Function} [onLoaded]
     */
    use : function(modules, version, onLoaded) {
        new EPF.util.Loader().require(modules, onLoaded);
    }
};

EPF.namespace('page'); /* package for page variables */
EPF.namespace('model'); /* package for model objects */
EPF.namespace('widget'); /* package for widget objects */
EPF.namespace('controller'); /* package for page controllers */

//document.fire = document.fire.wrap(function(proceed, name, ops){
//    EPF.log('document.fire: ' + name);
//    proceed(name, ops);
//});

/**
 * http://www.prototypejs.org/api/element/addMethods
 */
Element.addMethods({
    center: function(element) {
        var windowSize = document.viewport.getDimensions();
        var scrollOffsets = document.viewport.getScrollOffsets();
        
        var top, left;
        if (element.getStyle('position') == 'fixed') {
            top = ((windowSize.height - element.offsetHeight) / 2) + 'px';
            left = ((windowSize.width - element.offsetWidth) / 2) + 'px';
        } else {
            top = (((windowSize.height - element.offsetHeight) / 2) + scrollOffsets.top) + 'px';
            left = (((windowSize.width - element.offsetWidth) / 2) + scrollOffsets.left) + 'px';
        }

        element.setStyle( {
            top : top,
            left : left
        });
    }
});

/**
 * @returns {HTMLElement} 
 */
Element.fromText = function(text) {
    var div = document.createElement('div');
    div.innerHTML = text;
    return $(div.firstChild);
};

/**
 * Wrap the function with one that catches and logs any errors. TODO it doesnt seem to work
 * 
 * @returns {Function} the wrapped function 
 */
Function.prototype.catchAndLog = function() {
    var fn = this;
    return function(){
      try {
        return fn.apply(this, arguments);
      } catch(e){
        EPF.log(e);
      }
    };
};

/* $Name:  $ */
/* $Id: borrows.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/**
@constructor
*/
function Layout(p) {
	/** initilize 1 */
	this.init = function(p) {
	}
	
	/** get the id */
	this.getId = function() {
	}
	
	/** @type string */
	this.orientation = "landscape";
	
	function getInnerElements(elementSecretId){
	}
}

/** A static method. */
Layout.units = function() {
}

/**
@constructor
@borrows Layout#orientation
@borrows Layout-getInnerElements
@borrows Layout.units
*/
function Page() {
	/** reset the page */
	this.reset = function(b) {
	}
}

/**
@constructor
@borrows Layout.prototype.orientation as this.orientation
@borrows Layout.prototype.init as #init
@inherits Page.prototype.reset as #reset
*/
function ThreeColumnPage() {
	/** initilize 2 */
	this.init = function(p) {
	}
}

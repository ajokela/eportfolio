/* $Name:  $ */
/* $Id: augments.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/**
@constructor
*/
function Layout(p) {
	this.init = function(p) {
	}
	
	this.getId = function() {
	}
	
	/** @type Page */
	this.orientation = "landscape";
}

/**
@constructor
@augments Layout
*/
function Page() {
	this.reset = function(b) {
	}
}

/**
@extends Page
@constructor
*/
function ThreeColumnPage() {
	this.init = function(resetCode) {
	}
}

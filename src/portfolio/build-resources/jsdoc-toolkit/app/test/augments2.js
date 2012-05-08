/* $Name:  $ */
/* $Id: augments2.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/**
@constructor
*/
function LibraryItem() {
	this.reserve = function() {
	}
}

/**
@constructor
*/
function Junkmail() {
	this.annoy = function() {
	}
}

/**
@inherits Junkmail.prototype.annoy as pester
@augments ThreeColumnPage
@augments LibraryItem
@constructor
*/
function NewsletterPage() {
	this.getHeadline = function() {
	}
}

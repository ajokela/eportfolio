/* $Name:  $ */
/* $Id: namespace_nested.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** 
	@namespace This is the first namespace.
*/
ns1 = {};

/** 
	This is the second namespace.
	@namespace
*/
ns1.ns2 = {};

/**
	This part of ns1.ns2
	@constructor
*/
ns1.ns2.Function1 = function() {
};

ns1.staticFunction = function() {
};

/** A static field in a namespace. */
ns1.ns2.staticField = 1;

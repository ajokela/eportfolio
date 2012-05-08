/* $Name:  $ */
/* $Id: Token.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
if (typeof JSDOC == "undefined") JSDOC = {};

/**
	@constructor
*/
JSDOC.Token = function(data, type, name) {
	this.data = data;
	this.type = type;
	this.name = name;
}

JSDOC.Token.prototype.toString = function() { 
	return "<"+this.type+" name=\""+this.name+"\">"+this.data+"</"+this.type+">";
}

JSDOC.Token.prototype.is = function(what) {
	return this.name === what || this.type === what;
}

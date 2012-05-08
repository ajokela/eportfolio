/* $Name:  $ */
/* $Id: memberof_constructor.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** @constructor */
function Circle(){}

/**
	@constructor
	@memberOf Circle#
 */
Circle.prototype.Tangent = function(){};

// renaming Circle#Tangent to Circle#Circle#Tangent

/**
	@memberOf Circle#Tangent#
 */
Circle.prototype.Tangent.prototype.getDiameter = function(){};



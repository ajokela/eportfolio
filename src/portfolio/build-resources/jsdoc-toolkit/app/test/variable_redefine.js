/* $Name:  $ */
/* $Id: variable_redefine.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** @constructor */
function Foo() {
	var bar = 1;
	bar = 2; // redefining a private
	
	this.baz = 1;
	baz = 2; // global
	
	/** a private */
	var blap = {
		/** in here */
		tada: 1
	}
}

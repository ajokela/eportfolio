/* $Name:  $ */
/* $Id: static_this.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** the parent */
var box = {};

/** @namespace */
box.holder = {}

box.holder.foo = function() {
	/** the counter */
	this.counter = 1;
}

box.holder.foo();
print(box.holder.counter);

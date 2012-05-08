/* $Name:  $ */
/* $Id: Namespace.js,v 1.2 2010/10/27 19:24:55 ajokela Exp $ */
_global_ = this;

function Namespace(name, f) {
	var n = name.split(".");
	for (var o = _global_, i = 0, l = n.length; i < l; i++) {
		o = o[n[i]] = o[n[i]] || {};
	}
	
	if (f) f();
}

/* $Name:  $ */
/* $Id: module.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** @namespace */
myProject = myProject || {};

/** @namespace */
myProject.myModule = (function () {
	/** describe myPrivateVar here */
	var myPrivateVar = "";

	var myPrivateMethod = function () {
	}

	/** @scope myProject.myModule */
	return {
		myPublicMethod: function () {
		}
	};
})();

/* $Name:  $ */
/* $Id: globals.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
function example(/**Circle*/a, b) {
	/** a global defined in function  */
	var number = a;
	
	var hideNumber = function(){
	}
	
	setNumber = function(){
	}
	alert('You have chosen: ' + b);
}

function initPage() {
	var supported = document.createElement && document.getElementsByTagName;
	if (!supported) return;
	// start of DOM script
	var x = document.getElementById('writeroot');
	// etc.
}

/** an example var */
var document = new Document(x, y);

var getNumber = function(){
}

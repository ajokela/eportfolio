/* $Name:  $ */
/* $Id: functionCall.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
JSDOC.PluginManager.registerPlugin(
	"JSDOC.functionCall",
	{
		onFunctionCall: function(functionCall) {
			if (functionCall.name == "dojo.define" && functionCall.arg1) {
				functionCall.doc = "/** @lends "+eval(functionCall.arg1)+".prototype */";
			}
		}
	}
);

/* $Name:  $ */
/* $Id: symbolLink.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
JSDOC.PluginManager.registerPlugin(
	"JSDOC.symbolLink",
	{
		onSymbolLink: function(link) {
			// modify link.linkPath (the href part of the link)
			// or link.linkText (the text displayed)
			// or link.linkInner (the #name part of the link)
		}
	}
);

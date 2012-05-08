/* $Name:  $ */
/* $Id: tagParamConfig.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
JSDOC.PluginManager.registerPlugin(
	"JSDOC.tagParamConfig",
	{
		onDocCommentTags: function(comment) {
			var currentParam = null;
			var tags = comment.tags;
			for (var i = 0, l = tags.length; i < l; i++) {
				
				if (tags[i].title == "param") {
					if (tags[i].name.indexOf(".") == -1) {
						currentParam = i;
					}
				}
				else if (tags[i].title == "config") {
					tags[i].title = "param";
					if (currentParam == null) {
						tags[i].name = "arguments"+"."+tags[i].name;
					}
					else if (tags[i].name.indexOf(tags[currentParam].name+".") != 0) {
						tags[i].name = tags[currentParam].name+"."+tags[i].name;
					}
					currentParam != null
					//tags[currentParam].properties.push(tags[i]);
				}
				else {
					currentParam = null;
				}
			}
		}
	}
);

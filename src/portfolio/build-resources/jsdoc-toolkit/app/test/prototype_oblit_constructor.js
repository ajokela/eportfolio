/* $Name:  $ */
/* $Id: prototype_oblit_constructor.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** @constructor */
function Article() {
}

Article.prototype = {
	/** @constructor */
	Title: function(title) {
		/** the value of the Title instance */
		this.title = title;
	},
	
	init: function(pages) {
		/** the value of the pages of the Article instance */
		this.pages = pages;
	}
}

f = new Article();
f.init("one two three");

t = new f.Title("my title");

print(f.pages);
print(t.title);

/* $Name:  $ */
/* $Id: prototype.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** @constructor */
function Article() {
}

Article.prototype.init = function(title) {
	/** the instance title */
	this.title = title;
	
	/** the static counter */
	Article.counter = 1;
}

a = new Article();
a.Init("my title");

print(a.title);
print(Article.counter);

/* $Name:  $ */
/* $Id: lend.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
 /** @class  */
var Person = Class.create(
    /**
      @lends Person.prototype
    */
    {
      initialize: function(name) {
            this.name = name;
        },
        say: function(message) {
            return this.name + ': ' + message;
        }
    }
 );

/** @lends Person.prototype */
{
	/** like say but more musical */
	sing: function(song) {
	}
}

/** @lends Person */
{
	getCount: function() {
	}
}

/** @lends Unknown.prototype */
{
	notok: function() {
	}
}

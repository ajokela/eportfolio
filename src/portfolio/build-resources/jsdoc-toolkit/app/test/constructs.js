/* $Name:  $ */
/* $Id: constructs.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
var Person = makeClass(
    /**
      @scope Person
    */
    {
        /**
        	This is just another way to define a constructor.
        	@constructs
        	@param {string} name The name of the person.
         */
        initialize: function(name) {
            this.name = name;
        },
        say: function(message) {
            return this.name + " says: " + message;
        }
    }
);

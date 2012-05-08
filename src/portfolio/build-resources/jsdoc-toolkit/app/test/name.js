/* $Name:  $ */
/* $Id: name.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/**
 @name Response
 @class
*/

Response.prototype = {
	/**
	 @name Response#text
	 @function
	 @description
		Gets the body of the response as plain text
	 @returns {String}
		Response as text
	*/

	text: function() {
		return this.nativeResponse.responseText;
	}
}

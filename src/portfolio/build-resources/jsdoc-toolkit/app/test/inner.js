/* $Name:  $ */
/* $Id: inner.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/**
 * @constructor
 */
function Outer() {
  /**
   * @constructor
   */
  function Inner(name) {
    /** The name of this. */
    this.name = name;
  }

  this.open = function(name) {
    return (new Inner(name));
  }
}

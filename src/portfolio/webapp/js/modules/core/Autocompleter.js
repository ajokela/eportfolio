/* $Name:  $ */
/* $Id: Autocompleter.js,v 1.2 2010/10/27 19:24:57 ajokela Exp $ */
EPF.namespace('widget.Autocompleter');

/**
 * This overrides the scriptaculous Autocompleter.Local. See:
 * http://github.com/madrobby/scriptaculous/wikis/autocompleter-local
 * 
 * Added: - triggerElement option that will add an onclick observer to the
 * passed in element id that will trigger the choices menu. - fix scrolling of
 * choices menu. - positioning of choices menu based on menu height, also
 * repositioning as choices change.
 */
EPF.widget.Autocompleter.Local = Class.create(Autocompleter.Local, {

    initialize : function($super, element, update, url, options) {
        $super(element, update, url, options);
        if (this.options.triggerElement) {
            var oThis = this;
            $(this.options.triggerElement).observe('click', function() {
                if (oThis.update.visible()) {
                    oThis.hide();
                    oThis.element.focus();
                } else {
                    oThis.trigger()
                }
            });
        }
    },

    trigger : function() {
        this.element.value = '';
        this.element.focus();
        // To trigger the autocomplete when clicking on the
    // drop down image, we send a fake key event. Hackish!
    var fakeKeyEvent = new Object();
    fakeKeyEvent.keyCode = -100;
    this.onKeyPress(fakeKeyEvent);
},

positionUpdate : function(element, update) {
    var elementTop = element.cumulativeOffset().top;
    var elementHeight = element.getHeight();
    var elementBottom = elementTop + elementHeight;
    var roomBelow = document.viewport.getHeight() + document.viewport.getScrollOffsets().top - elementBottom;
    var roomAbove = elementTop - document.viewport.getScrollOffsets().top;

    var margin = 5;

    var isShowing = update.visible();

    if (!isShowing) {
        // If its not showing, we need to render it
    // invisible so we can get the dimensions.
    update.style.visibility = 'hidden';
    update.style.display = 'block';
}

// align with element
update.clonePosition(element, {
    setHeight : false,
    offsetTop : element.offsetHeight
});

if (update.getHeight() + margin < roomBelow) {
    // set below
} else if (update.getHeight() + margin < roomAbove) {
    // set above
    update.style.top = (elementTop - update.getHeight() - 1) + 'px';
} else {
    // shrink to fit under
    update.style.height = (roomBelow - margin) + 'px';
}

if (!isShowing) {
    // If its not showing, we need to put it back how it was.
    update.style.display = 'none';
    update.style.visibility = 'visible';
}
},

// need to override to do proper scrolling in the update div
    markPrevious : function() {
        if (this.index > 0) {
            this.index--;
            if (this.update.scrollTop > this.getEntry(this.index).positionedOffset().top) {
                this.update.scrollTop = this.update.scrollTop - this.getEntry(this.index).getHeight();
            }
        }
    },

    // need to override to do proper scrolling in the update div
    markNext : function() {
        if (this.index < this.entryCount - 1) {
            this.index++;
            if (this.update.scrollTop
                    + (this.update.clientHeight ? this.update.clientHeight : this.update.offsetHeight) < this.getEntry(
                    this.index).positionedOffset().top
                    + this.getEntry(this.index).getHeight()) {
                this.update.scrollTop = this.update.scrollTop + this.getEntry(this.index).getHeight();
            }
        }
    },
    // need to override this because i want to reposition the choices div.
    updateChoices : function($super, choices) {
        $super(choices);
        this.positionUpdate(this.element, this.update);
    }
});

/* $Name:  $ */
/* $Id: legacy-stuff.js,v 1.9 2011/02/24 21:49:16 ajokela Exp $ */
// JavaScript library of functions for the Portfolio project.
	
//  Opens "MyWindow" to the location specified
//  by String "URL"
function openMyWindow(URL) {
    sFeatures = "width=610,height=400,titlebar=yes,status=yes,scrollbars=yes,resizable=yes,menubar=yes";
    headerWin = window.open (URL, "My", sFeatures);
}

// Opens tip window
function openTipWindow(URL) {
    sFeatures = "width=600,height=400,screenX=400,screenY=10,left=400,top=10,titlebar=yes,status=yes,scrollbars=yes,resizable=yes";
    tipWin = window.open (URL, "Tip", sFeatures);
}

//  Opens "ReportWindow" to the location specified
//  by String "URL"
function openReportWindow(URL) {
    sFeatures = "width=800,height=600,titlebar=yes,toolbar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
    reportWin = window.open (URL, "Report", sFeatures);
}

function limitChar(field, maxlimit){
    var msg1 = "You have reached the maximum character limit of "
    var msg2 = " characters.\n\nExtra characters will be removed."
    var strValue = field.value
    var strLen = strValue.length
    if (strLen > maxlimit){
        field.blur();
        field.focus();
        field.select();
        alert(msg1 + maxlimit + msg2);
        var strNew = strValue.substring(0, maxlimit)
        field.value = strNew;
        return false;
    } else {
        return true;
    }
}

function addHiddenInputToForm(form, name, value) {
    form.appendChild(new Element('input', {type: 'hidden', name: name, value: value}));
}
    
//Expand and Collapse Function
function expandCollapse() {
    $A(arguments).each(function(item) {
        var openImage = $('open'+item);
        if (openImage != null) {
            openImage.toggle();
        }
        var closeImage = $('close'+item);
        if (closeImage != null) {
            closeImage.toggle();
        }

        $(item).toggle();
    });
}

function expand() { 
    $A(arguments).each(function(item) {
        var openImage = $('open'+item);
        if (openImage != null) {
            openImage.hide();
        }
        var closeImage = $('close'+item);
        if (closeImage != null) {
            closeImage.show();
        }
        $(item).show();
    });
}

function collapse() {
    $A(arguments).each(function(item) {
        var openImage = $('open'+item);
        if (openImage != null) {
            openImage.show();
        }
        var closeImage = $('close'+item);
        if (closeImage != null) {
            closeImage.hide();
        }
        $(item).hide();
    });
}
      
// Create Presentation Flow Scripts

function shareCancelConfirm() {
		
    var confirmDialog = new dialog.Confirm( {
        messageText : 'Are you sure you want to cancel? Work you have done on this step will be lost.',
        // confirmUrl : '/share/'
    });
    
    confirmDialog.show();
}

function saveFinish() {
	
	var shareId = $('shareForm').shareId.value;
	
    $('shareForm').nextStep.value="finished";
    $('shareForm').submit();
    
    // window.location = '/portfolio/' + shareId;
    
    // window.open('/portfolio/' + shareId, 'portfolio_' + share_id );
    
}

function goBack(step) {
    var nextStep=step;  
    $('shareForm').nextStep.value=nextStep; 
    $('shareForm').submit();
    return false;
}

function hasChecks(checkboxes) {
    return $A(checkboxes).any(function(e){return e.checked;});
} 

/**
 * Sets up a confirm dialog with the given text.
 * 
 * @param id the id of the link element
 * @param text the message for the confirm dialog
 */
function addLinkConfirm(id, text) {
    $(id).observe('click', function(event) {
        event.stop();
        new dialog.Confirm({
            messageText: text,
            confirmUrl: this.href
        }).show();
    });
}

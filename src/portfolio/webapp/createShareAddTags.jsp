<!-- $Name:  $ -->
<!-- $Id: createShareAddTags.jsp,v 1.10 2011/02/24 21:49:16 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="share" pageTitle="Add tags">
	<div class="MainContent shareAddTags"><jsp:include
		page="shareheader.jsp">
		<jsp:param name="new" value="${share.new}" />
		<jsp:param name="step" value="5" />
	</jsp:include>
	<h1>Step 5. Add Tags</h1>
	<br />
	<strong>Title: <c:out value="${share.shareName}" /> </strong>
	<p>Description: <c:out value="${share.shareDesc}" /></p>
	<%@ include file="messages.jsp"%> <%@ include
		file="errorMsgs.jsp"%> <html:form
		action="saveCreateShare.do" styleId="shareForm">
		<input type="hidden" name="nextStep" value="finished" />
		<input type="hidden" name="shareId" id="shareId" value="${share.shareId}" />
		<h2>Tags</h2>
		<p>These may be suggested by your program instructors and they are
		used to contextualize the information you enter in this portfolio.
		(limit 10 Public Tags, 50 characters each)</p>
		<div class="standardsBox">
		<p id="maxPrivateTagsMessage" style="display: none;"><strong>Only
		10 tags are allowed.</strong></p>
		<p id="noPrivateTagsMessage" style="display: none;">Currently
		there are no tags recorded for this portfolio.</p>
		<div id="currentPrivateTags" class="indent"></div>
		<div id="addPrivateTagDiv" style="display: none" class="commAddForm">
		<input type="text" id="PrivateTagAutocomplete" name="tagText"
			size="25" maxLength="50" style="vertical-align: middle;" /> <img
			id="PrivateTagTrigger" src="/images/dropdown.gif"
			style="cursor: pointer; vertical-align: middle;" /> <input
			id="addPrivateTagButton" type="button" name="Add" value="Add"
			class="btn" />
		<div id="PrivateTagAutocomplete_choices" class="autocomplete"></div>
		</div>
		</div>
		<br />
		<br />
		<div class="share"><input type="button" class="btn" value="Back"
			onclick="goBack('createShareSelectStyle');" /> <input type="button"
			class="btn" onclick="shareCancelConfirm();" value="Cancel" /> <input
			type="button" class="btn" value="Save &amp; Finish" name="save"
			onclick="saveFinish();" /></div>
	</html:form></div>
	<script type="text/javascript">
    if ($('PrivateTagAutocomplete')) {
      // init tags PrivateTagAutocompleter
      new EPF.widget.Autocompleter.Local('PrivateTagAutocomplete', 
                                      'PrivateTagAutocomplete_choices', 
                                      ${empty portfolioTags ? '([])' : portfolioTags}, 
                                      {choices: 10000,
                                        minChars: '0',
                                        partialChars : '0',
                                        fullSearch : true,
                                        triggerElement : 'PrivateTagTrigger'});
                                 
      // init 'add tag' button       
      $('addPrivateTagButton').observe('click', function(){    
        newTag = $F('PrivateTagAutocomplete');
        
        // check if tag already exists
        PrivateTagExists = false;   
        $('currentPrivateTags').PrivateTagsArray.each( function(e) {
          if (e.tag == newTag) {
            PrivateTagExists = true;
          }
        });               
        
        if (PrivateTagExists) {                 
          // new dialog.Alert({messageText : 'The "'+newTag+'" Private Tag already exists!'}).show();
        } else { 
        	
          $('PrivateTagAutocomplete').value='';
          new Ajax.Request('portfolioTagsJson.do', {
            parameters: {
              shareId : '${share.shareId}',
              tag : newTag,
              method : 'add'
              },
            onSuccess: updatePrivateTags
            });
		}
		 
       });
      
      // initially populate private tags
      new Ajax.Request('portfolioTagsJson.do', {
        parameters: {shareId : '${share.shareId}', isPublic: 'false'},
        onSuccess: updatePrivateTags
      });
    }
    /** Deletes a tag and updates list */
    function privateTagDelete(text) {      
      var confirmDialog = new dialog.Confirm( {
          messageText : 'Are you sure you want to remove the "'+text+'" Private Tag from this portfolio?',
          onConfirm : function() {
            new Ajax.Request('portfolioTagsJson.do', {
            parameters: {
              shareId : '${share.shareId}',
              tag : text,
              isPublic: 'false',
              method : 'remove'
              },
            onSuccess: updatePrivateTags
            });
            return true;
          }
        }
      ).show();
    }    /** Updates the list of tags with the JSON object in the transport */
    function updatePrivateTags(transport) {
        $('currentPrivateTags').innerHTML='';
        array = $A(transport.responseJSON);
        
        // save array for validation
        $('currentPrivateTags').PrivateTagsArray = array;
        
        if (array.size() == 0) {
          $('addPrivateTagDiv').show();
          $('noPrivateTagsMessage').show();
          $('maxPrivateTagsMessage').hide();
        } else if (array.size() >= 10){
          $('addPrivateTagDiv').hide();
          $('noPrivateTagsMessage').hide();
          $('maxPrivateTagsMessage').show();
        } else {      
          $('addPrivateTagDiv').show();
          $('noPrivateTagsMessage').hide();
          $('maxPrivateTagsMessage').hide();
        }                                                       
        array.each(function(e){
          el = new Element('div',{style:''});
          el.innerHTML='&#8226 '+e.tag+' &nbsp;';
          link = new Element('a', {href:'javascript:void(0)'}).update('Remove');
          $(link).observe('click',function() {privateTagDelete(e.tag)});
          el.appendChild(link);
          $('currentPrivateTags').appendChild(el);   
        });
    }
  </script>
</tags:portfolioPage>

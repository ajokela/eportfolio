<!-- $Name:  $ -->
<!-- $Id: createShare3.jsp,v 1.11 2011/02/24 21:49:16 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="share" pageTitle="Sharing"
	jsModules="dhtmlcalendar">
	<div class="MainContent"><jsp:include page="shareheader.jsp">
		<jsp:param name="new" value="${share.new}" />
		<jsp:param name="step" value="6" />
	</jsp:include>
	<h1>Step 6. Sharing</h1>
	<br />
	<strong>Title: <c:out value="${share.shareName}" /></strong>
	<p>Description: <c:out value="${share.shareDesc}" /></p>
	<%@ include file="messages.jsp"%> <%@ include
		file="errorMsgs.jsp"%> <html:form
		action="saveCreateShare.do" styleId="shareForm">
		<input type="hidden" name="nextStep" value="finished" />
		<html:hidden property="page" value="3" />
		<html:hidden property="shareName" />
		<input type="hidden" name="shareId" id="shareId" value="${share.shareId}" />
		<input type="hidden" name="back" value="false" />
		<div class="level1Bar">Sharing</div>
		<div style="margin-bottom: 5px;"><strong>Expiration
		Date:</strong> <input type="text" id="dateExpire" name="dateExpireString"
			value="${empty share.dateExpireString ? 'None' : share.dateExpireString}"
			style="width: 100px" readonly="readonly" />&nbsp; <img id="trigger"
			src="/images/calendar.jpg" style="cursor: pointer;" /> <a href="#"
			onclick="$('dateExpire').value='None';return false;">clear</a> <script
			type="text/javascript">
              document.observe('loader:success', function() {
	              var today = new Date();
	              Calendar.setup({
	                  inputField: 'dateExpire',
	                  ifFormat: "%m/%d/%Y",
	                  button: 'trigger',
	                  weekNumbers: false,
	                  dateStatusFunc: function (date) {return date < today;}
	                }
	              );
	             }); 
            </script></div>
		<div><c:choose>
			<c:when test="${share.publicView}">
				<input id="publicPrivate" name="publicView" type="radio"
					value="true" checked="checked" />
			</c:when>
			<c:otherwise>
				<input id="publicPrivate" name="publicView" type="radio"
					value="true" />
			</c:otherwise>
		</c:choose> <label for="publicPrivate">Public Portfolio <small>(Available
		to the entire web - expiration date is required.)</small></label></div>
		<div id="privateFieldsDiv"><c:choose>
			<c:when test="${share.publicView}">
				<input name="publicView" type="radio" value="false"
					id="publicPrivate2" />
			</c:when>
			<c:otherwise>
				<input name="publicView" type="radio" value="false"
					id="publicPrivate2" checked="checked" />
			</c:otherwise>
		</c:choose> <label for="publicPrivate2">Private Portfolio <small>(Selected
		viewers are granted secure access.)</small></label></div>
		<div id="privateFields" class="newIndent1">
		<div>
		<div style="margin: 10px;"><c:set var="viewers"
			value="${share.viewerViewers}" />
		<div id="viewersDiv"><jsp:include page="/jsp/share/viewers.jsp" /></div>
		<div class="addViewer">
		<h3>Add a viewer</h3>
		<p class="instructions">Enter an email address</p>
		
		<input type="text" size="30" name="query" id="query" /> <input
			type="submit" name="addUser" value="Add" id="addUser" /></div>
		<div id="notifyBox"><label for="emailMsg">Your Message</label> <textarea
			id="emailMsg" name="emailMsg" cols="30" rows="4" class="inputsizer90"
			onkeyup="limitChar(this,512)"></textarea> <input name="CCSelf"
			type="checkbox" value="CCSelf" id="CCSelf" /> <label for="CCSelf">Send
		copy of message to yourself</label></div>
		</div>
		</div>
		</div>
		<br />
		<hr />
		<br />
		<div class="share"><input type="button" class="btn" value="Back"
			onclick="goBack('createShareAddTags');" /> <input type="button"
			class="btn" onclick="shareCancelConfirm();" value="Cancel" /> <input
			type="submit" class="btn" value="Save &amp; Finish" name="save" /></div>
	</html:form></div>
	<script language="javascript">
	  new EPF.widget.InputTextInnerLabel('query', 'E-mail address');
	
    $('addUser').observe('click', function(event){
      event.stop();
      if (!$F('query').blank()) {
        new Ajax.Request('/portfolio/viewer/add/'+$F('query'), {
          onSuccess: function(transport) {
            $('viewersDiv').update(transport.responseText);
          }
        });
      }
      $('query').clear();
    });
  
   $('shareForm').observe('submit', function(event){
     if ($('publicPrivate').checked && $F('dateExpire') == 'None') {
       new dialog.Alert('Public portfolios require an expiration date!').show();
       event.stop();
     }
   });
	</script>
</tags:portfolioPage>

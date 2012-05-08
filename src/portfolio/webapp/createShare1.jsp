<!-- $Name:  $ -->
<!-- $Id: createShare1.jsp,v 1.7 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="share" pageTitle="Set up a portfolio">
	<div class="MainContent"><jsp:include page="shareheader.jsp">
		<jsp:param name="new" value="${share.new}" />
		<jsp:param name="step" value="1" />
	</jsp:include>
	<h1>Step 1. Set Up a Portfolio</h1>
	<%@ include file="messages.jsp"%> <html:form
		action="saveCreateShare.do" styleId="shareForm" styleClass="newForm">
		<input type="hidden" name="shareId" value="${share.shareId}" />
		<input type="hidden" name="nextStep" value="createShare2" />
		<input type="hidden" id="templateIdInput" name="templateId"
			value="${share.templateId}" />
		<input type="hidden" id="communityIdInput" name="communityId"
			value="${communityId}" />
		<html:hidden property="page" value="1" />
		<html:hidden property="publicView" />
		<html:hidden property="dateExpireString" />
		<div class="level1Bar">Enter a Title and Description</div>
		<%@ include file="errorMsgs.jsp"%>
		<fieldset>
		<ul>
			<li><label for="shareName">Title: <span
				style="float: none">*Required</span></label> <html:text property="shareName"
				styleId="shareName" maxlength="50" size="50" style="width: 350px;" />&nbsp;
			</li>
			<li>
			<div style="width: 350px;">
			<div>
			<div style="width: 75%; float: left; display: block;"><span>Description:
			(describe the purpose of this portfolio)</span></div>
			<div
				style="width: 25%; float: right; display: block; vertical-align: bottom;">
			<span id="shareDescCounter"></span></div>
			</div>
			<div><html:textarea property="shareDesc" styleId="shareDesc"
				cols="40" rows="5" style="width: 350px;" /></div>
			<script type="text/javascript">
		           new EPF.widget.TextareaCounter({
		               textareaEl: 'shareDesc',
		               counterEl: 'shareDescCounter',
		               max: 800
		           });
	            </script></div>
			</li>
		</ul>
		</fieldset>
		<div class="level1Bar">Select a Template</div>
		<fieldset id="selectTemplate">
		<ul>
			<li><input id="templateTypeCommunity" type="radio"
				name="templateType" value="community" /> <label
				for="templateTypeCommunity"> Use a community template</label>
			<div id="selectTemplateInner"><select name="communityId"
				id="communitySelect">
				<option>Select a community</option>
				<c:forEach var="communityEntry" items="${communitiesByCampus}">
					<optgroup label="${communityEntry.key}">
						<c:forEach var="community" items="${communityEntry.value}">
							<c:choose>
								<c:when
									test="${not empty communityId and community.id eq communityId}">
									<option value="${community.id}" selected="selected">${community.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${community.id}">${community.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</optgroup>
				</c:forEach>
			</select> <img id="indicator" src="/images/indicator_arrows.gif"
				style="display: none">
			<div id="communityTemplates"></div>
			</div>
			</li>
			<li><input id="templateTypeCustom" type="radio"
				name="templateType" value="custom" /> <label
				for="templateTypeCustom"> Use a custom template</label>
			<div style="margin-left: 20px;">Pick any information in your
			entire portfolio to display in this presentation.</div>
			</li>
		</ul>
		</fieldset>
		<br />
		<br />
		<hr />
		<br />
		<div class="share"><input type="button" class="btn"
			onclick="shareCancelConfirm();" value="Cancel" /> <input
			type="submit" class="btn" value="Continue" /> <c:if
			test="${!share.new}">
			<input type="button" class="btn" value="Save &amp; Finish"
				name="save" onclick="saveFinish();" />
		</c:if></div>
	</html:form></div>
	<script language="JavaScript" type="text/javascript">
    
    function updateCommunityTemplates() {
      $('communityTemplates').hide();
      $('indicator').show();
      new Ajax.Request('communityTemplates.do', {
        parameters: {communityId: $F('communitySelect')},
        onComplete: function (transport) {
          $('communityTemplates').update(transport.responseText);
          $('indicator').hide();
          
          // select radio and update templateIdInput
          var radios = $$('input[type="radio"][name="templateIdTemp"]');
          radios.each(function(e){ if (e.value == $F('templateIdInput')) { e.checked = true;} });
          if (!radios.pluck('checked').any()) {
            radios.first().checked = true; 
            $('templateIdInput').value = radios.first().value
          };
          
          //new Effect.SlideDown('communityTemplates', {queue: 'end'});
          $('communityTemplates').show();
        }
      });
    }
    
    $('communitySelect').observe('change', updateCommunityTemplates);
    
    $('templateTypeCustom').observe('click', function(e){$('communitySelect').disable();});
    $('templateTypeCommunity').observe('click', function(e){$('communitySelect').enable();});
    
    $('templateTypeCustom').observe('click', function(e){
      $('templateIdInput').value='1';
      $('communitySelect').disable().selectedIndex = 0;
      $('communityTemplates').hide();
    });
    
    // default to custom template
    if ($F('templateIdInput').blank() && $F('communityIdInput').blank()) {$('templateIdInput').value='1';}
    
    if ($F('templateIdInput') == '1') {
      $('templateTypeCustom').checked = true;
      $('communitySelect').disable().selectedIndex = 0;
    } else {
      $('templateTypeCommunity').checked = true;
      if (!$F('communitySelect').blank()) {updateCommunityTemplates();}
    }
   
   $('shareForm').observe('submit', function(event){
     if ($F('shareName').empty()) {
       new dialog.Alert('You must enter a title.').show();
       event.stop();
     }
   });
    
  </script>
</tags:portfolioPage>

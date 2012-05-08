<!-- $Name:  $ -->
<!-- $Id: templateBuilderName.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Template Builder" id="templateBuilderName">
	<jsp:include page="templateBuilderSteps.jsp">
		<jsp:param name="step" value="name" />
	</jsp:include>

	<h2>Template Name and Description</h2>

	<form action="templateBuilderName.do" method="post"
		class="newForm mediumForm" id="templateForm"><input
		type="hidden" name="templateId"
		value="${empty template ? '' : template.templateId}" /> <input
		type="hidden" name="method" value="save" /> <input type="hidden"
		name="communityId"
		value="${empty template ? param.communityId : template.communityId}" />
	<fieldset><label for="name">Give the portfolio
	template a name:</label> <input class="full" type="text" name="name" id="name"
		value="${empty template ? '' : template.name}" /> <br
		class="clearboth" />
	<label for="description">Description of template:</label> <textarea
		class="full" name="description" id="description">${empty template ? '' : template.description}</textarea>
	<br class="clearboth" />
	</fieldset>
	<fieldset style="${empty template ? '' : 'display:none'}"><input
		id="guideBaseTrue" type="radio" name="guideBase" value="true" /> <label
		for="guideBaseTrue" style="display: inline">Base template on
	an entire collection guide</label> <br />
	<select id="guideSelect" name="guideId" disabled="disabled">
		<option value="">Select a collection guide</option>
		<c:forEach var="guide" items="${collectionGuides}">
			<option value="${guide.id}">${guide.title}</option>
		</c:forEach>
	</select> <br />
	<input id="guideBaseFalse" type="radio" name="guideBase" value="false"
		checked="checked" /> <label for="guideBaseFalse"
		style="display: inline">Select specific selections from one or
	more collection guides</label></fieldset>
	<fieldset class="submitSet"><br />
	<input type="submit" name="next" value="Next: Sections and Elements">
	</fieldset>
	</form>
	<div class="cleardiv"></div>
	<br />
	<br />
	<c:choose>
		<c:when test="${empty template}">
			<a href="/editTemplates.do?communityId=${param.communityId}">Cancel
			new template creation</a>
		</c:when>
		<c:otherwise>
			<a href="/editTemplates.do?communityId=${template.communityId}">Cancel</a>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
    $('guideBaseTrue').observe('click', function(event) {$('guideSelect').enable();});
    $('guideBaseFalse').observe('click', function(event) {$('guideSelect').disable();});
    
    $('templateForm').observe('submit', function(event) {
      if ($('guideBaseTrue').checked && $F('guideSelect').empty()) {
        new dialog.Alert('You must select a collection guide.').show();
        event.stop();
      }
    });
  </script>
</tags:communityPage>

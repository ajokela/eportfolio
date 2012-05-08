<!-- $Name:  $ -->
<!-- $Id: templateBuilderAssessment.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Template Builder" id="templateBuilderAssessment">
	<jsp:include page="templateBuilderSteps.jsp">
		<jsp:param name="step" value="assessment" />
	</jsp:include>
	<h2>Assessment</h2>
	<form action="templateBuilderAssessment.do" method="post"
		class="newForm"><input type="hidden" name="method" value="save">
	<input type="hidden" name="templateId" value="${template.templateId}">
	<p><strong>Do you want portfolios created with this
	template to be assessable?</strong></p>
	<fieldset>
	<ul>
		<li><input id="assessableTrue" type="radio" name="assessable"
			value="true" checked="checked" /> <strong>Yes</strong> (you will
		choose assessment models on the next screen)</li>
		<li><input id="assessableFalse" type="radio" name="assessable"
			value="false" /> <strong>No</strong> (skip assignment of assessment
		models)</li>
	</ul>
	</fieldset>
	<fieldset class="submitSet"><input type="submit" name="prev"
		value="Prev: Sections" /> <input id="nextButton" type="submit"
		name="next" value="Next: Assign Assessment" /></fieldset>
	</form>
	<div class="cleardiv"></div>
	<script type="text/javascript">
    $('assessableTrue').observe('click',function(){$('nextButton').value='Next: Assign Assessment';});
    $('assessableFalse').observe('click',function(){$('nextButton').value='Next: Publish';});
  </script>
</tags:communityPage>

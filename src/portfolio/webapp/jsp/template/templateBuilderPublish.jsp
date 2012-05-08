<!-- $Name:  $ -->
<!-- $Id: templateBuilderPublish.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Template Builder" id="templateBuilderPublish">
	<jsp:include page="templateBuilderSteps.jsp">
		<jsp:param name="step" value="publish" />
	</jsp:include>
	<h2>Save and Publish Portfolio Template</h2>
	<p>Before this portfolio template is available, it must be
	published. You are encouraged to carefully check details of the
	portfolio template before publishing it because once published it
	cannot be edited without compromising the data integrity (changes to
	the elements or assessment models will change all portfolios shared
	with this template).</p>
	<br />
	<form action="/templateBuilderPublish.do" method="post"><input
		type="hidden" name="method" value="togglePublish" /> <input
		type="hidden" name="templateId" value="${template.templateId}">
	<c:choose>
		<c:when test="${template.published}">
			<p><em>Your template has been published.</em></p>
			<input type="submit" name="unpublish" value="Unpublish template">
	        or
	        <a href="/community/${template.communityId}">Return to
			community home</a>
		</c:when>
		<c:otherwise>
			<p><em>Your template has not been published.</em></p>
			<input type="submit" name="publish" value="Publish template">
	        or
	        <a href="/community/${template.communityId}">Save template
			and publish later</a>
		</c:otherwise>
	</c:choose></form>
	<br />
	<br />
	<hr class="clearboth" />
	<br />
	<br />
	<input type="button" value="Prev: Assessment"
		onclick="window.location='templateBuilderAssessment.do?templateId=${template.templateId}'">
</tags:communityPage>

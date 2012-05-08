<!-- $Name:  $ -->
<!-- $Id: assessmentModelBuilder2.jsp,v 1.4 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : ${param.format} Assessment Builder">
	<div class="MainContent builderPage">
	<p class="superhead">ePortfolio Communities &gt; ${param.format}
	Assessment Model Builder</p>

	<jsp:include page="assessmentModelSteps.jsp">
		<jsp:param name="new" value="${assessmentModel.new}" />
		<jsp:param name="step" value="2" />
		<jsp:param name="format" value="${param.format}" />
	</jsp:include>

	<h1>Step 2: Select a Scoring Model</h1>
	<%@ include file="/messages.jsp"%> <html:form
		action="assessmentModelBuilder2.do?format=${param.format}"
		method="post" styleClass="newForm newFormNarrow">
		<input type="hidden" name="communityId"
			value="${assessmentModelForm.communityId}" />
		<fieldset id="selectScoringModel">
		<ul>
			<c:forEach var="scoringModel" items="${scoringModels}">
				<li><html:radio property="scoringId" value="${scoringModel.id}"
					styleClass="radio" styleId="scoring${scoringModel.id}"></html:radio>
				<label class="choice" for="scoring${scoringModel.id}"><strong>${scoringModel.name}</strong><br />
				${scoringModel.description}</label></li>
			</c:forEach>
		</ul>
		</fieldset>
		<br />
		<input type="submit" class="btn" value="Prev:Name and description"
			onclick="$('step').value='step1';return true;" />
		<c:choose>
			<c:when test="${param.format eq 'basic'}">
				<input type="submit" class="btn" value="Save For Later"
					onclick="$('step').value='SaveForLater';return true;" />
				<input type="submit" class="btn" value="Finish"
					onclick="$('step').value='finish';return true;" />
			</c:when>
			<c:otherwise>
				<input type="submit" class="btn" value="Next:Choosing Objectives"
					onclick="$('step').value='step3';return true;" />
			</c:otherwise>
		</c:choose>
		<br />
		<br />
		<hr />
		<br />
		<a
			href="assessmentDirectory.do?communityId=${assessmentModelForm.communityId}">Cancel</a>
		<input id="step" type="hidden" name="step" value="next" />
	</html:form></div>
</tags:portfolioPage>

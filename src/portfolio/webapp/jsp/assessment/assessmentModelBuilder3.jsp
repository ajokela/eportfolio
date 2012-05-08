<!-- $Name:  $ -->
<!-- $Id: assessmentModelBuilder3.jsp,v 1.4 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : ${param.format} Assessment Builder">
	<div class="MainContent builderPage">
	<p class="superhead">ePortfolio Communities > ${param.format}
	Assessment Model Builder</p>

	<jsp:include page="assessmentModelSteps.jsp">
		<jsp:param name="new" value="${assessmentModel.new}" />
		<jsp:param name="step" value="3" />
		<jsp:param name="format" value="${param.format}" />
	</jsp:include>

	<h1>Step 3: Select Objectives</h1>
	<%@ include file="/messages.jsp"%> <html:form
		action="assessmentModelBuilder3.do?format=${param.format}"
		method="post">
		<input type="hidden" name="communityId"
			value="${assessmentModelForm.communityId}" />
		<c:choose>
			<c:when test="${param.format eq 'rubric'}">
          Each objective you select will form one criterion (row) of the rubric. You will have the chance to order the objectives in the next screen.
        </c:when>
			<c:when test="${param.format eq 'outcome'}">
          Select one or more outcomes to be used for this assessment model. You will have the chance to order the objectives in the next screen.           
        </c:when>
			<c:otherwise>
				<!-- Basic assesment models should not see this step. -->
			</c:otherwise>
		</c:choose>
		<ul id="assessmentObjectiveList">
			<c:forEach var="objective"
				items="${assessmentModelForm.communityObjectives}">
				<li class="objectiveList level${(objective.level - 1) mod 8 + 1}">
				<input type="checkbox" name="selected" id="obj_${objective.id}"
					value="${objective.id}"
					<c:if test="${objective.selected == true}"> <c:out value="checked"/> </c:if> />
				${objective.name}</li>
			</c:forEach>
		</ul>
		<input type="submit" class="btn" value="Prev:Choosing Scoring Model"
			onclick="$('step').value='step2';return true;" />
		<input type="submit" class="btn" value="Next:Ordering Objectives"
			onclick="$('step').value='step4';return true;" />
		<br />
		<br />
		<hr />
		<br />
		<a
			href="assessmentDirectory.do?communityId=${assessmentModelForm.communityId}">Cancel</a>
		<input id="step" type="hidden" name="step" value="next" />
	</html:form></div>
</tags:portfolioPage>

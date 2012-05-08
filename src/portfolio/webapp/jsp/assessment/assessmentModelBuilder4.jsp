<!-- $Name:  $ -->
<!-- $Id: assessmentModelBuilder4.jsp,v 1.5 2010/11/23 20:34:58 ajokela Exp $ -->
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

	<script language="javascript">
        function moveUp(id){
            $('moveType').value = 'up';
            $('moveObj').value = id;
            $('stepAction').value = 'move';
            $('assessForm').submit();
        }

        function moveDown(id){
            $('moveType').value = 'down';
            $('moveObj').value = id;
            $('stepAction').value = 'move';
            $('assessForm').submit();
        }
    </script> <jsp:include page="assessmentModelSteps.jsp">
		<jsp:param name="new" value="${assessmentModel.new}" />
		<jsp:param name="step" value="4" />
		<jsp:param name="format" value="${param.format}" />
	</jsp:include>

	<h1>Step 4: Order Objectives</h1>
	<p>Use the up and down arrows to adjust the order of the
	objectives.</p>
	<%@ include file="/messages.jsp"%> <html:form
		action="assessmentModelBuilder4.do?format=${param.format}"
		method="post" styleId="assessForm">
		<input type="hidden" name="communityId"
			value="${assessmentModelForm.communityId}" />
		<ul id="assessmentObjectiveOrder" class="objectiveList">
			<c:forEach var="objective"
				items="${assessmentModelForm.assessedObjectives}">
				<li><img src="/images/iconUp.gif"
					onclick="moveUp(${objective.id})" alt="Move objective up" /> <img
					src="/images/iconDown.gif" onclick="moveDown(${objective.id})"
					alt="Move objective down" /> ${objective.name}</li>
			</c:forEach>
		</ul>

		<input type="submit" class="btn" value="Prev:Choosing Objectives"
			onclick="$('step').value='step3';return true;" />
		<c:choose>
			<c:when test="${param.format eq 'outcome'}">
				<input type="submit" class="btn" value="Save For Later"
					onclick="$('step').value='SaveForLater';return true;" />
				<input type="submit" class="btn" value="Finish"
					onclick="$('step').value='finish';return true;" />
			</c:when>
			<c:otherwise>
				<input type="submit" class="btn" value="Next:Enter Descriptions"
					onclick="$('step').value='step5';return true;" />
			</c:otherwise>
		</c:choose>
		<br />
		<br />
		<hr />
		<br />
		<a
			href="assessmentDirectory.do?communityId=${assessmentModelForm.communityId}">Cancel</a>
		<input type="hidden" id="moveType" name="moveType" value="up" />
		<input type="hidden" id="moveObj" name="moveObj" value="" />
		<input type="hidden" id="stepAction" name="stepAction" value="move" />
		<input id="step" type="hidden" name="step" value="step4" />
	</html:form></div>
</tags:portfolioPage>

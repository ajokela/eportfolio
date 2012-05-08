<!-- $Name:  $ -->
<!-- $Id: assessmentModelWindow.jsp,v 1.4 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<link rel="stylesheet" href="/css/templates/assess.css" type="text/css"/>

<div style="overflow:auto; height: 480px;">

<form action="#" class="assessmentForm"><c:choose>
	<c:when test="${assessmentModel.format eq 'basic'}">
		<tags:evalScore assessmentModel="${assessmentModel}" />
	</c:when>
	<c:when test="${assessmentModel.format eq 'outcome'}">
		<tags:evalOutcome assessmentModel="${assessmentModel}" />
	</c:when>
	<c:when test="${assessmentModel.format eq 'rubric'}">
		<tags:evalRubric assessmentModel="${assessmentModel}" entryId="" />
	</c:when>
</c:choose></form>

</div>

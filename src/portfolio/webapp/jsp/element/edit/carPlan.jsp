<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carPlan.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<h3>Career objective / goal</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="60" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Action Plan</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.actionPlan}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="actionPlan" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>
<h3>Timeline</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />

<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.timeline}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="timeline" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

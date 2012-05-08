<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: skiInformationSkills.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Record your self-assessment of Information skills based on
Information Literacy Competency Standards for Higher Education from the
Association of College &amp; Research Libraries.</p>
<h3>Name of skills</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Recognize and articulate the need for information</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.recognizeNeed}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="recognizeNeed" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Access needed Information</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.accessInfo}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="accessInfo" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Evaluate Information</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.evaluateInfo}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="evaluateInfo" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Use Information effectively to accomplish a specific purpose</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.useEffectively}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="useEffectively" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Use Information ethically and legally</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.useEthically}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="useEthically" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

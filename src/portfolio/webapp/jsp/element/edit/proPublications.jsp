<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: proPublications.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Title of publication</h3>
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
Author(s)</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.author}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="author" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Description / Abstract</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.description}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="description" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>


<h3>Year of publication</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="publDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.YEAR%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="publDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.YEAR%>" />
	</c:otherwise>
</c:choose>
</p>

<h3>Editors(s)</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.editor}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="editor" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Title of collection</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.collTitle}" />
	</c:when>
	<c:otherwise>
		<html:text property="collTitle" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Volume and number of collection</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.collVol}" />
	</c:when>
	<c:otherwise>
		<html:text property="collVol" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Page numbers</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.collPage}" />
	</c:when>
	<c:otherwise>
		<html:text property="collPage" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Location of publisher</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.pubLoc}" />
	</c:when>
	<c:otherwise>
		<html:text property="pubLoc" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Name of publisher</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.pubName}" />
	</c:when>
	<c:otherwise>
		<html:text property="pubName" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

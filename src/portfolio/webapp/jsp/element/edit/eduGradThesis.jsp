<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduGradThesis.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Thesis Title</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="50" maxlength="50" />
		<span class="required">*Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Introduction</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${dataDef == 'view'}">
		<c:out value="${dataDef.introduction}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="introduction" cols="35" rows="15"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>University of Completion</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.university}" />
	</c:when>
	<c:otherwise>
		<html:text property="university" size="50" maxlength="50" />
	</c:otherwise>
</c:choose>
<br />
<br />
<h3>Date of Defense</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="defenseDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="defenseDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.DAY%>" />

	</c:otherwise>
</c:choose>

<h3><br />
<br />
Abstract / Comments</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<h3><span class="formNote"></span></h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.commentary}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="commentary" cols="35" rows="15"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose>

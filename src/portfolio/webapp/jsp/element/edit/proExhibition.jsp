<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: proExhibition.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Name of exhibition</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Type of exhibition</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.type}" />
	</c:when>
	<c:otherwise>
		<html:text property="type" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>From</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="fromDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="fromDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>To</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="toDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="toDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Location</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.location}" />
	</c:when>
	<c:otherwise>
		<html:text property="location" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>
<br />
<br />
<h3>Juried</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<bean:write name="dataDef" property="juried" />
	</c:when>
	<c:otherwise>
		<p>&nbsp;&nbsp;<html:radio property="juried" value="yes" /> yes
		&nbsp;&nbsp;<html:radio property="juried" value="no" /> no</p>
	</c:otherwise>
</c:choose>

<h3><br />
Description</h3>
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

<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: proTeaching.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Teaching position</h3>
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
Course / Subjects</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.courses}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="courses" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Organization</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.organization}" />
	</c:when>
	<c:otherwise>
		<html:text property="organization" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h2>Address</h2>
<br />
<h3>Street line 1</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.street1}" />
	</c:when>
	<c:otherwise>
		<html:text property="street1" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Street line 2</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.street2}" />
	</c:when>
	<c:otherwise>
		<html:text property="street2" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>City</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.city}" />
	</c:when>
	<c:otherwise>
		<html:text property="city" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>State</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.state}" />
	</c:when>
	<c:otherwise>
		<html:text property="state" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Zip</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.zip}" />
	</c:when>
	<c:otherwise>
		<html:text property="zip" size="10" maxlength="10" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Country</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.country}" />
	</c:when>
	<c:otherwise>
		<html:text property="country" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>
<br />
<br />
<h2>Dates of service</h2>
<br />
<h3>Start</h3>
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
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>
<br />
<br />
<h3>End</h3>
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
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Additional comments</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.comments}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="comments" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

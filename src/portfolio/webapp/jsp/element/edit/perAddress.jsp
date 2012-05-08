<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: perAddress.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Type of address (personal, work, etc)</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="50" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Address line 1</h3>
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
<h3>Address line 2</h3>
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

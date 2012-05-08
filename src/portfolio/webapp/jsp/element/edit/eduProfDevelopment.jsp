<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduProfDevelopment.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Record conferences, workshops, seminars and training programs
attended.</p>
<h3>Activity name</h3>
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
<h3>Sponsor</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.sponsor}" />
	</c:when>
	<c:otherwise>
		<html:text property="sponsor" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Description</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.description}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="description" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose> <br />
<br />
<h3>Certificate Earned</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.certificate}" />
	</c:when>
	<c:otherwise>
		<html:text property="certificate" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>
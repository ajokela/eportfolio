<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: proResearch.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Name of activity</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3>Organization</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.organization}" />
	</c:when>
	<c:otherwise>
		<html:text property="organization" size="25" maxlength="60" />
	</c:otherwise>
</c:choose>

<h3>Supervisor</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.supervisor}" />
	</c:when>
	<c:otherwise>
		<html:text property="supervisor" size="25" maxlength="60" />
	</c:otherwise>
</c:choose>

<h3>Description (Limit approximately 600 words)</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.description}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="description" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose>

<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: perPhone.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Type of phone number (personal, work, etc)</h3>
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
<h3>Phone Number</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.phone}" />
	</c:when>
	<c:otherwise>
		<html:text property="phone" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

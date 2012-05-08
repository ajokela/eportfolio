<!-- $Name:  $ -->
<!-- $Id: skiDocumentation.jsp,v 1.5 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Name of Documentation</h3>
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
Text</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.text}" />
	</c:when>
	<c:otherwise>

			<div>

				<html:textarea property="text" cols="40" rows="15"
					onkeyup="limitChar(this,4000)" />
				    
		    </div>

	</c:otherwise>
</c:choose>
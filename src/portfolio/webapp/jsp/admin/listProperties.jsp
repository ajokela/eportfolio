<!-- $Name:  $ -->
<!-- $Id: listProperties.jsp,v 1.6 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="enter" pageTitle="ePortfolio : Properties">
	<div class="MainContent" id="listProperties">
	<h1>Properties</h1>
	<h2>System</h2>
	<table class="basic">
		<c:forEach var="entry" items="${sysProps}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>
		</c:forEach>
	</table>
	<h2>Application</h2>
	<table class="basic">
		<c:forEach var="entry" items="${appProps}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<!-- MainContent -->
</tags:portfolioPage>

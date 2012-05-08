<!-- $Name:  $ -->
<!-- $Id: listAttributes.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="enter" pageTitle="ePortfolio : Attributes">
	<c:forEach items=""></c:forEach>
	<div class="MainContent" id="listAttributes">
	<h1>Attributes</h1>
	<h2>Application</h2>
	<table class="basic">
		<c:forEach var="entry" items="${applicationScope}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>
		</c:forEach>
	</table>
	<h2>Session</h2>
	<table class="basic">
		<c:forEach var="entry" items="${sessionScope}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>
		</c:forEach>
	</table>
	<h2>Request</h2>
	<table class="basic">
		<c:forEach var="entry" items="${requestScope}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>
		</c:forEach>
	</table>
	<h2>Page</h2>
	<table class="basic">
		<c:forEach var="entry" items="${pageScope}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<!-- MainContent -->
</tags:portfolioPage>

<!-- $Name:  $ -->
<!-- $Id: ProfActivities.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<c:if test="${not empty element.name}">
	<tags:elementField heading="Name of Practice">${element.name}</tags:elementField>
</c:if>
<c:if test="${not empty element.fromDate}">
	<tags:elementField heading="From Date">${element.fromDate}</tags:elementField>
</c:if>
<c:if test="${not empty element.toDate}">
	<tags:elementField heading="To Date">${element.toDate}</tags:elementField>
</c:if>
<c:if test="${not empty element.location}">
	<tags:elementField heading="Location"><br />${fn:replace(element.location, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.description}">
	<tags:elementField heading="Description"><br />${fn:replace(element.description, newLine, "<br/>")}</tags:elementField>
</c:if>	
<%-- reworked layout for all BK 03-21-12 --%>
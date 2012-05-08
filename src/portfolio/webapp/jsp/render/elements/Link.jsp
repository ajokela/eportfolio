<!-- $Name:  $ -->
<!-- $Id: Link.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<tags:elementField heading="URL">
	<a href="${element.url}" target="_blank">${element.url}</a>
</tags:elementField>
<c:if test="${not empty element.description}">
	<tags:elementField heading="Description"><br />${fn:replace(element.description, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.author}">
	<tags:elementField heading="Author">${element.author}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
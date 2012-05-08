<!-- $Name:  $ -->
<!-- $Id: InfoInterview.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<tags:elementField heading="Date of Interview">${element.intDate}</tags:elementField><!--  required -->
<c:if test="${not empty element.occupation}">
	<tags:elementField heading="Occupation">${element.occupation}</tags:elementField>
</c:if>
<c:if test="${not empty element.organization}">
	<tags:elementField heading="Organization">${element.organization}</tags:elementField>
</c:if>
<c:if test="${not empty element.infoOccupation}">
	<tags:elementField heading="Information Obtained"><br />${fn:replace(element.infoOccupation, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.addlNotes}">
	<tags:elementField heading="Additional Information"><br />${fn:replace(element.addlNotes, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
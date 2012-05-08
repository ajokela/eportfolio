<!-- $Name:  $ -->
<!-- $Id: Communication.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<c:if test="${not empty element.expository}">
	<tags:elementField heading="Expository Writing"><br />${fn:replace(element.expository, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.creative}">
	<tags:elementField heading="Creative Writing"><br />${fn:replace(element.creative, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.discipline}">
	<tags:elementField heading="Writing Within Discipline"><br />${fn:replace(element.discipline, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.oneOnOne}">
	<tags:elementField heading="One on One Interaction"><br />${fn:replace(element.oneOnOne, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.smallGroup}">
	<tags:elementField heading="Small Group Communication"><br />${fn:replace(element.smallGroup, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.facilitation}">
	<tags:elementField heading="Small Group Facilitation"><br />${fn:replace(element.facilitation, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.publicSpeaking}">
	<tags:elementField heading="Public Speaking"><br />${fn:replace(element.publicSpeaking, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.listening}">
	<tags:elementField heading="Listening"><br />${fn:replace(element.listening, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.conflictRes}">
	<tags:elementField heading="Conflict Resolution"><br />${fn:replace(element.conflictRes, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
<!-- $Name:  $ -->
<!-- $Id: IdNumber.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%--  IMPORTANT: Do not put with the line above. Need to be separated to create newline char--%>

<c:if test="${not empty element.idNumber}">
	<tags:elementField heading="ID number">${element.idNumber}</tags:elementField>
</c:if>
<c:if test="${not empty element.addlInfo or element.addlInfo ne ''}">
	<tags:elementField heading="Additional information"><br />${fn:replace(element.addlInfo, newLine, "<br/>")}</tags:elementField>
</c:if>
<%--  reworked layout for all BK 03-21-12 --%>
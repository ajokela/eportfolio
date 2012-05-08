<!-- $Name:  $ -->
<!-- $Id: CoCurricular.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%--  IMPORTANT: Do not put with the line above. Need to be separated to create newline char --%>

<tags:elementField heading="From">${element.fromDate}</tags:elementField>
<tags:elementField heading="To">${element.toDate}</tags:elementField>

<c:if test="${not empty element.description}">
	<tags:elementField heading="Description"><br />${fn:replace(element.description, newLine, "<br/>")}</tags:elementField>
</c:if>
<%--  reworked layout BK 03-21-12 --%>
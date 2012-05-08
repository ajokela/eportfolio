<!-- $Name:  $ -->
<!-- $Id: MyersBriggs.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<tags:elementField heading="Date Taken">${element.testDate}</tags:elementField>
<c:if test="${not empty element.persType}">
	<tags:elementField heading="Personality Type">${element.persType}</tags:elementField>
</c:if>
<c:if test="${not empty element.interpretation}">
	<tags:elementField heading="Interpretation"><br />${fn:replace(element.interpretation, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
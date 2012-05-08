<!-- $Name:  $ -->
<!-- $Id: RoleModel.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char --%>

<c:if test="${not empty element.description}">
	<tags:elementField heading="Description" wysiwyg="true"><br />${element.description}</tags:elementField>
</c:if>
<c:if test="${not empty element.importance}">
	<tags:elementField heading="Importance of role model"><br />${fn:replace(element.importance, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
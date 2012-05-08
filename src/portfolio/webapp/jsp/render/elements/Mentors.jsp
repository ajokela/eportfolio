<!-- $Name:  $ -->
<!-- $Id: Mentors.jsp,v 1.4 2010/12/06 14:26:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char --%>

<tags:elementField heading="Title">${element.title}</tags:elementField>
<tags:elementField heading="Organization">${element.organization}</tags:elementField>
<c:if test="${not empty element.street1 && not empty element.city}">
	<tags:elementField heading="Address"><br />
		<c:if test="${not empty element.street1}">
	    ${element.street1}<br />
		</c:if>
		<c:if test="${not empty element.street2}">
	    ${element.street2}<br />
		</c:if>
		<c:if test="${not empty element.city}">
	    ${element.city}
	  </c:if>
		<c:if test="${not empty element.state}">
	    ${element.state}
	  </c:if>
		<c:if test="${not empty element.zip}">
	    ${element.zip}<br />
		</c:if>
		<c:if test="${not empty element.country}">
	    ${element.country}
	  </c:if>
	</tags:elementField>
</c:if>
<c:if test="${not empty element.description}">
	<tags:elementField heading="Description"><br />${fn:replace(element.description, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
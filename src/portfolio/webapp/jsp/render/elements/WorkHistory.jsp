<!-- $Name:  $ -->
<!-- $Id: WorkHistory.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<c:if test="${not empty element.institution}">
	<tags:elementField heading="Organization">${element.institution}</tags:elementField>
</c:if>
<c:if test="${not empty element.supervisor}">
	<tags:elementField heading="Supervisor">${element.supervisor}</tags:elementField>
</c:if>
<c:if test="${not empty element.address1}">
	<tags:elementField heading="Address">${element.address1}</tags:elementField>
</c:if>
<c:if test="${not empty element.address2}">
	<tags:elementField heading="Address 2">${element.address2}</tags:elementField>
</c:if>
<c:if test="${not empty element.city}">
	<tags:elementField heading="City">${element.city}</tags:elementField>
</c:if>
<c:if test="${not empty element.state}">
	<tags:elementField heading="State">${element.state}</tags:elementField>
</c:if>
<c:if test="${not empty element.zip}">
	<tags:elementField heading="Zip">${element.zip}</tags:elementField>
</c:if>
<c:if test="${not empty element.country}">
	<tags:elementField heading="Country">${element.country}</tags:elementField>
</c:if>
<c:if test="${not empty element.telephone}">
	<tags:elementField heading="Phone">${element.telephone}</tags:elementField>
</c:if>
<c:if test="${not empty element.fax}">
	<tags:elementField heading="Fax">${element.fax}</tags:elementField>
</c:if>
<c:if test="${not empty element.startDate}">
	<tags:elementField heading="Start Date">${element.startDate}</tags:elementField>
</c:if>
<!-- Changed End Date BK 3/16/12 -->
<c:if test="${not empty element.endDate or not empty element.presentlyEmployed}">
	<tags:elementField heading="End Date">
		<c:if test="${empty element.presentlyEmployed}">${element.endDate}</c:if>
		<c:if test="${not empty element.presentlyEmployed}">(presently employed)</c:if>
	</tags:elementField>
</c:if>
<c:if test="${not empty element.description}">
	<tags:elementField heading="Description"><br />${fn:replace(element.description, newLine, "<br/>")}</tags:elementField>
</c:if>
<c:if test="${not empty element.accomplishments}">
	<tags:elementField heading="Accomplishments"><br />${fn:replace(element.accomplishments, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>
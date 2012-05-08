<!-- $Name:  $ -->
<!-- $Id: References.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:elementField heading="Position">${element.position}</tags:elementField>
<tags:elementField heading="Organization">${element.organization}</tags:elementField>
<!-- Changed address BK 3/16/12 -->
<c:if test="${not empty element.address1 or not empty element.address2 or not empty element.city or not empty element.state or not empty element.zip or not empty element.country}">
<tags:elementField heading="Address"><br />
	<c:if test="${not empty element.address1}">${element.address1}</c:if><br />
	<c:if test="${not empty element.address2}">${element.address2}</c:if><br />
	<c:if test="${not empty element.city}">${element.city}, </c:if>
		<c:if test="${not empty element.state}">${element.state} </c:if>
		<c:if test="${not empty element.zip}">${element.zip}</c:if><br />
	<c:if test="${not empty element.country}">${element.country}</c:if>
</tags:elementField>
</c:if>
<tags:elementField heading="Phone">${element.phone}</tags:elementField>
<tags:elementField heading="E-mail Address">${element.email}</tags:elementField>
<!--  Changed Contact Preferences BK 3/16/12 -->
<c:if test="${not empty element.mailPref or not empty element.phonePref or not empty element.emailPref}">
<tags:elementField heading="Contact Preferences"><br />
	<c:if test="${not empty element.mailPref}">Mail<br/></c:if>
	<c:if test="${not empty element.phonePref}">Phone<br /></c:if>
	<c:if test="${not empty element.emailPref}">E-mail</c:if>
</tags:elementField>
</c:if>
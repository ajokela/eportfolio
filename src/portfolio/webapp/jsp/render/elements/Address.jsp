<!-- $Name:  $ -->
<!-- $Id: Address.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:elementField>
	<c:if test="${not empty element.street1}">
    ${element.street1}<br />
	</c:if>
	<c:if test="${not empty element.street2}">
    ${element.street2}<br />
	</c:if>
	<c:if test="${not empty element.street3}">
    ${element.street3}<br />
	</c:if>
	<c:if test="${not empty element.street4}">
    ${element.street4}<br />
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

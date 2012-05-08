<%@ tag body-content="scriptless"%>
<%@ attribute name="heading" required="false"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<jsp:doBody var="theBody" />
<c:if test="${not empty theBody}">
	<dt>${heading}</dt>
	<dd>${ospi:xssFilter(theBody)}</dd>
</c:if>
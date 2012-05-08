<%@ tag body-content="scriptless"%>
<%@ attribute name="heading" required="false"%>
<%@ attribute name="wysiwyg" required="false"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<jsp:doBody var="theBody" />

<div>

<c:if test="${not empty theBody}">
	<c:if test="${not empty fn:trim(heading)}">
		<div style="display: block; float: left; width: 135px; font-weight: bold;">${heading}:</div>
	</c:if>
	<div style="display: block; float: left;">
	<c:choose>
		<c:when test="${fn:startsWith(theBody,'<p>')}">${ospi:xssFilter(theBody)}</c:when>
		<c:otherwise>
			${ospi:xssFilter(theBody)}
		</c:otherwise>
	</c:choose>
	</div>
	<div style="clear: both;"></div>
</c:if>

</div>
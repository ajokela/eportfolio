<%@ tag body-content="scriptless"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:doBody var="theBody" />
<c:if test="${not empty theBody}">
	<dl class="element_inner">
		${theBody}
	</dl>
</c:if>

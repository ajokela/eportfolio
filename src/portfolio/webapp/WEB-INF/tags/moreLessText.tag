<%@ tag body-content="scriptless"%>
<%@ attribute name="length" required="true" type="java.lang.Integer"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<jsp:doBody var="theBody" />

<c:choose>
	<c:when test="${fn:length(theBody) > length}">
		<c:set var="id" value="${ospi:randomId()}" scope="page"></c:set>
    ${fn:substring(theBody, 0, length)}<span id="${id}_overflow"
			style="display: none;">${fn:substring(theBody, length, -1)}</span>
		<span id="${id}_ellipsis">...</span>
    (<a id="${id}_link" href="#">more</a>)
    <script>
      $('${id}_link').observe('click', function(event) {
        $('${id}_overflow', '${id}_ellipsis').invoke('toggle');
        $('${id}_link').update($('${id}_overflow').visible() ? 'less' : 'more');
        event.stop();
      });
    </script>
	</c:when>
	<c:otherwise>${theBody}</c:otherwise>
</c:choose>



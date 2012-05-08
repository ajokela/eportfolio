<%@ tag body-content="scriptless"
	import="org.portfolio.util.Configuration"%>
<%@ attribute name="person" required="false"
	type="org.portfolio.model.Person"%>
<%@ attribute name="date" required="true" type="java.util.Date"%>
<%@ attribute name="commentHeader" required="true" fragment="true"%>
<%@ attribute name="commentBody" required="true" fragment="true"%>
<%@ attribute name="commentDetail" required="false" fragment="true"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="randomId" value="${ospi:randomId()}" />
<c:if test="${not empty commentDetail}">
	<jsp:invoke fragment="commentDetail" var="commentDetailOutput" />
</c:if>

<div class="comment">
<div class="commentPic"><c:choose>
	<c:when test="${empty person}">
		<img src="/images/anonymous.gif" />
	</c:when>
	<c:otherwise>
		<img
			src="/profilePicture?euid=${ospi:encodeUriComponent(ospi:encrypt(person.personId))}" />
	</c:otherwise>
</c:choose></div>
<div class="commentRight">
<div class="commentDate"><fmt:formatDate value="${date}"
	pattern="yyyy-MM-dd  h:mm a" /></div>
<div class="commentHeader"><strong>${empty person ?
'Anonymous' : person.displayName}</strong> <jsp:invoke fragment="commentHeader" />
</div>
<div class="commentBody"><jsp:invoke fragment="commentBody" /> <c:if
	test="${not empty commentDetailOutput}">
        &nbsp;
        <a href="#"
		onclick="Effect.toggle('commentSlideToggle${randomId}', 'slide', { queue: 'end' });return false;">
	(show details) </a>
</c:if></div>
<c:if test="${not empty commentDetailOutput}">
	<div id="commentSlideToggle${randomId}" style="display: none;"
		class="commentDetail">
	<div class="smoothSlide">${commentDetailOutput}</div>
	</div>
</c:if></div>
</div>
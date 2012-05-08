<!-- $Name:  $ -->
<!-- $Id: adviserComments.jsp,v 1.4 2010/12/06 14:26:05 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<div id="noComments" style="${empty comments ? '' : 'display:none'}">There
are no adviser comments.</div>

<c:if test="${not empty comments}">
	<ul class="commentsList">
		<c:forEach var="comment" items="${comments}">
			<li class="comment">
			<div class="authorPicSection"><img class="profilePic"
				src="/profilePicture?euid=${ospi:encodeUriComponent(ospi:encrypt(comment.adviser.personId))}"
				alt="profilePic" /></div>
			<div class="dataSection">
			<div class="head"><span class="name"><c:out
				value="${comment.adviser.displayName}" /></span> <span class="date"><fmt:formatDate
				value="${comment.dateCreated}" pattern="MMMMM d, yyyy h:mm a" /></span></div>
			<div class="body"><c:out value="${comment.text}" /></div>
			<c:if test="${comment.adviser eq Person}">
				<div class="controls">
					<a class="deleteLink" id="delete_${comment.id}" href="#">delete</a> <!--  a class="editLink" id="edit_${comment.id}" href="#">edit</a -->
				</div>
			</c:if></div>
			</li>
		</c:forEach>
	</ul>
</c:if>

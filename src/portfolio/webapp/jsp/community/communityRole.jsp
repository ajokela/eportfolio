<!-- $Name:  $ -->
<!-- $Id: communityRole.jsp,v 1.5 2011/01/27 19:30:29 rkavajec Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:communityPage community="${community}" pageTitle="Roles"
	id="communityRoles" returnTo="editCommunity">
	<h3>Community Coordinators</h3>
	<ul>
		<c:forEach var="person" items="${communityCoordinators}">
			<li>${person.fullName} <a
				href="communityRole.do?method=delete&communityId=${community.id}&euid=${ospi:encodeUriComponent(ospi:encrypt(person.personId))}&roleType=cc"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a></li>
		</c:forEach>
	</ul>
	<p class="commAdd"><a href="javascript:void(0);"
		onclick="$('addCCDiv').toggle();">+ add Community Coordinator</a></p>
	<div id="addCCDiv" style="display: none;">
	<form action="communityRole.do" class="commAddForm"><input
		type="hidden" name="method" value="add" /> <input type="hidden"
		name="communityId" value="${community.id}" /> <input type="hidden"
		name="roleType" value="cc" />
	<div class="addViewer"><strong>User Email:</strong><br />
	<input type="text" size="30" name="userId" /> <input type="submit"
		name="addUser" value="Add" id="addUserCC" /><br />
	</div>
	</form>
	<br class="clearboth" />
	</div>

	<h3>Assessment Coordinators</h3>
	<ul>
		<c:forEach var="person" items="${assessmentCoordinators}">
			<li>${person.fullName} (${person.email}) <c:if
				test="${Person.admin}">
				<a
					href="communityRole.do?method=delete&communityId=${community.id}&euid=${ospi:encodeUriComponent(ospi:encrypt(person.personId))}&roleType=ac"><img
					src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a>
			</c:if></li>
		</c:forEach>
	</ul>
	<c:if test="${Person.admin}">
		<p class="commAdd"><a href="javascript:void(0);"
			onclick="$('addACDiv').toggle();">+ add Assessment Coordinator</a></p>
		<div id="addACDiv" style="display: none;">
		<form action="communityRole.do" class="commAddForm"><input
			type="hidden" name="method" value="add" /> <input type="hidden"
			name="communityId" value="${community.id}" /> <input type="hidden"
			name="roleType" value="ac" />
		<div class="addViewer"><strong>User Email:</strong><br />
		<input type="text" size="30" name="userId" /> <input type="submit"
			name="addUser" value="Add" id="addUserAC" /><br />
		</div>
		</form>
		<br class="clearboth" />
		</div>
	</c:if>

	<h3>Evaluators</h3>
	<ul>
		<c:forEach var="person" items="${evaluators}">
			<li>${person.fullName} (${person.email}) <a
				href="communityRole.do?method=delete&communityId=${community.id}&euid=${ospi:encodeUriComponent(ospi:encrypt(person.personId))}&roleType=ev"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a></li>
		</c:forEach>
	</ul>
	<p class="commAdd"><a href="javascript:void(0);"
		onclick="$('addEVDiv').toggle();">+ add Evaluator</a></p>
	<div id="addEVDiv" style="display: none;">
	<form action="communityRole.do" class="commAddForm"><input
		type="hidden" name="method" value="add" /> <input type="hidden"
		name="communityId" value="${community.id}" /> <input type="hidden"
		name="roleType" value="ev" />
	<div class="addViewer"><strong>User Email:</strong><br />
	<input type="text" size="30" name="userId" /> <input type="submit"
		name="addUser" value="Add" id="addUserEV" /><br />
	</div>
	<div class="addViewer addViewerNarrow">-OR-</div>
	<div class="addViewer"><strong>Non-U of M User</strong><br />
	<input type="text" size="30" name="guestEmail" /> <input type="submit"
		name="addGuest" value="Add" id="addGuestEV" /><br>
	Enter an email address</div>
	</form>
	<br class="clearboth" />
	</div>

	<h3>Members (<a
		href="communityRole.do?method=export&communityId=${community.id}&roleType=me">Export
	List</a>)</h3>
	<p>${param.feedback}</p>
	<ul>
		<c:forEach var="person" items="${members}">
			<li>${person.fullName} (${person.email}) <a
				href="communityRole.do?method=delete&communityId=${community.id}&euid=${ospi:encodeUriComponent(ospi:encrypt(person.personId))}&roleType=me"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a></li>
		</c:forEach>
	</ul>
	<p class="commAdd"><a href="javascript:void(0);"
		onclick="$('addMEDiv').toggle();">+ add Member</a></p>
	<div id="addMEDiv" style="display: none;">
	<form action="communityRole.do" class="commAddForm"><input
		type="hidden" name="method" value="add" /> <input type="hidden"
		name="communityId" value="${community.id}" /> <input type="hidden"
		name="roleType" value="me" />
	<div class="addViewer"><strong>User Email:</strong><br />
	<input type="text" size="30" name="userId" /> <input type="submit"
		name="addUser" value="Add" id="addUserME" /><br />
	</div>
	</form>
	<br class="clearboth" />
	</div>
</tags:communityPage>

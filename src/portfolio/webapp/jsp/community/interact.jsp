<!-- $Name:  $ -->
<!-- $Id: editCommunity.jsp,v 1.5 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:communityPage community="${community}" pageTitle="Manage Community : Interact" id="interactCommunity" returnTo="editCommunity">

<div>
	<form method="post" action="/community/interact/save/${community.id}">
	<div>
		<ul class="editList" id="linkList">
			<c:forEach var="interact" items="${interacts}">
				<li id="link_${interact.id}">
					${interact.longType} - <a href="${fn:escapeXml(interact.val)}">${fn:escapeXml(interact.val)}</a>&nbsp;<a href="/community/interact/delete/${community.id}/${interact.id}"><img src="/images/delete.gif" style="border: 0px;" alt="Delete" id="delete_${interact.id}" /></a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div>
		<select name="type" title="Select Link Type">
		<c:forEach var="interactType" items="${interactTypes}">
			<option value="${interactType}">${interactType}</option>
		</c:forEach>
		</select>
		<input type="text" name="val" size="40" /><br />
		Link (e.g. http://www.facebook.com/ThisCommunitysFacebookGroup or similar)<br />
		<input type="submit" value="Add" name="action" />
	</div>


	</form>
</div>

<script type="text/javascript">
window.onload = function() {
	Sortable.create('linkList', {
		onUpdate: function() {
			new Ajax.Request('/community/interact/save/position' , {
				method: 'post',
				parameters: {
					data: Sortable.serialize('linkList'),
					communityId: '${community.id}'
					}
			});
		}
	});
}
</script>

<style type="text/css">
li {
	cursor: move; 
	list-style-position: inside !important;
}
</style>

</tags:communityPage>

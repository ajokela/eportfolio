<!-- $Name:  $ -->
<!-- $Id: editLinks.jsp,v 1.7 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" pageTitle="Links"
	id="editLinks" returnTo="editCommunity">
	<a href="#"
		onclick="Effect.toggle('createLinkDiv', 'slide', { queue: 'end' });return false;"
		style="display: block;">+ add link</a>
	<div id="createLinkDiv" class="commAddLinkForm" style="display: none;">
		<div>
		<form action="/communityLink.do" class="newForm narrowForm"
			method="post"><input type="hidden" name="method" value="save" />
		<input type="hidden" name="communityId" value="${community.id}" />
		<fieldset>
		<ul>
			<li><label for="name">Name:</label> <input type="text"
				name="name" id="name" class="full" /></li>
			<li><label for="url">URL:</label> <input type="text" name="url"
				id="url" class="full" /></li>
			<li><label for="description">Description:</label> <textarea
				name="description" id="description" class="full"></textarea></li>
		</ul>
		</fieldset>
		<fieldset class="submitset"><input type="submit"
			value="Create" /></fieldset>
		</form>
		</div>
	</div>
	<ul class="hasRight" id="linkList">
		<p><i>To reorder links, click and drag bullet.</i></p>
		<c:forEach var="link" items="${links}">
			<li id="link_${link.entryKeyId}">
			<div class="right"><a
				href="/communityLink.do?method=edit&entryKeyId=${link.entryKeyId}"><img
				src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit" /></a>
			<a id="delete-${link.entryId}"
				href="/communityLink.do?method=delete&entryKeyId=${link.entryKeyId}&communityId=${community.id}"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a> <script
				type="text/javascript">addLinkConfirm('delete-${link.entryId}', 'Are you sure you want to delete this link?');</script>
			</div>
			<a href="${fn:escapeXml(link.url)}">${fn:escapeXml(link.entryName)}</a>
			</li>
		</c:forEach>
	</ul>
</tags:communityPage>

<script type="text/javascript">
window.onload = function() {
	Sortable.create('linkList', {
		onUpdate: function() {
			new Ajax.Request('/communityLink.do' , {
				method: 'post',
				parameters: {
					data: Sortable.serialize('linkList'),
					communityId: '${community.id}',
					method: 'reorder'}
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

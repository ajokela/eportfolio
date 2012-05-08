<!-- $Name:  $ -->
<!-- $Id: editTemplates.jsp,v 1.6 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:communityPage community="${community}"
	pageTitle="Portfolio Templates" id="editTemplates"
	returnTo="editCommunity" cssClass="yui-skin-sam">

	<a id="new" href="/templateBuilderName.do?communityId=${community.id}"
		style="display: none;">New</a>
	<script type="text/javascript">
    EPF.use('button', "${applicationScope['org.portfolio.project.version']}", function() { 
      new YAHOO.widget.Button('new');
    });
  </script>

	<h3>Published Templates</h3>
	<ul class="templateList hasRight">
		<c:forEach var="template" items="${publishedTemplates}">
			<li>
			<div class="right"><a
				href="/templateBuilderName.do?templateId=${template.id}"><img
				src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit"
				title="Edit portfolio template" /></a> <a
				href="/template.do?method=copy&templateId=${template.id}"><img
				src="/images/fugue/icon_shadowless/documents.png" alt="copy"
				title="Copy portfolio template" /></a> <a id="delete_${template.id}"
				href="/template.do?method=delete&templateId=${template.id}"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete"
				title="Delete portfolio template" /></a> <script type="text/javascript">addLinkConfirm('delete_${template.id}','Are you sure you want to delete this Portfolio Template?');</script>
			</div>
			<c:out value="${template.name}" /></li>
		</c:forEach>
	</ul>
	<br class="clearboth" />

	<h3>Unpublished Templates</h3>
	<ul class="templateList hasRight">
		<c:forEach var="template" items="${unpublishedTemplates}">
			<li>
			<div class="right"><a
				href="/templateBuilderName.do?templateId=${template.id}"><img
				src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit"
				title="Edit portfolio template" /></a> <a
				href="/template.do?method=copy&templateId=${template.id}"><img
				src="/images/fugue/icon_shadowless/documents.png" alt="copy"
				title="Copy portfolio template" /></a> <a id="delete_${template.id}"
				href="/template.do?method=delete&templateId=${template.id}"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete"
				title="Delete portfolio template" /></a> <script type="text/javascript">addLinkConfirm('delete_${template.id}','Are you sure you want to delete this Portfolio Template?');</script>
			</div>
			<c:out value="${template.name}" /></li>
		</c:forEach>
	</ul>
	<br class="clearboth" />
</tags:communityPage>

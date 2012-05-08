<!-- $Name:  $ -->
<!-- $Id: editCollectionGuides.jsp,v 1.6 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:communityPage community="${community}"
	pageTitle="Edit Collection Guides" id="editCollectionGuides"
	cssClass="yui-skin-sam" returnTo="editCommunity">

	<input type="button" id="new" name="new" value="New"
		style="display: none;">
	<div id="newMenu" class="yuimenu newMenu" style="display: none;">
	<div class="bd">
	<ul class="first-of-type">
		<li class="yuimenuitem"><a class="yuimenuitemlabel"
			href="/guideBuilderDetails.do?method=create&communityId=${community.id}">
		Create guide from scratch </a></li>
		<li class="yuimenuitem"><a class="yuimenuitemlabel"
			href="#importMenuItem"> Import guide from another community </a>
		<div id="importMenuItem" class="yuimenu">
		<div class="bd">
		<ul>
			<c:forEach var="communityGuides" items="${guideMap}">
				<li class=""><a class="communityLabel yuimenuitemlabel"
					href="#${communityGuides.key.id}"> <c:out
					value="${communityGuides.key.name}" /> </a>
				<div id="${communityGuides.key.id}" class="yuimenu">
				<div class="bd">
				<ul>
					<c:forEach var="guide" items="${communityGuides.value}">
						<li><a class="yuimenuitemlabel"
							href="/guide.do?method=importGuide&destCommunityId=${community.id}&guideId=${guide.id}">
						<c:out value="${guide.title}" /> </a></li>
					</c:forEach>
				</ul>
				</div>
				</div>
				</li>
			</c:forEach>
		</ul>
		</div>
		</div>
		</li>
	</ul>
	</div>
	</div>

	<h3>Unpublished Guides</h3>
	<ul class="guideList hasRight">
		<c:forEach var="guide" items="${unpublishedGuides}">
			<li>
			<div class="right"><a href="/guide/${guide.id}" target="_blank"><img
				src="/images/fugue/icon_shadowless/binocular.png" alt="view"
				title="View collection guide" /></a> <a
				href="/guideBuilderDetails.do?method=edit&guideId=${guide.id}"><img
				src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit"
				title="Edit collection guide" /></a> <a
				href="/guide.do?method=copy&guideId=${guide.id}"><img
				src="/images/fugue/icon_shadowless/documents.png" alt="copy"
				title="Copy collection guide" /></a> <a id="delete_${guide.id}"
				href="/guide.do?method=delete&guideId=${guide.id}"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete"
				title="Delete collection guide" /></a> <script type="text/javascript">addLinkConfirm('delete_${guide.id}','Are you sure you want to delete this Collection Guide?');</script>
			</div>
			<span id="${guide.id}_title"><c:out value="${guide.title}" /></span>
			</li>
		</c:forEach>
	</ul>
	<br class="clearboth" />
	
	<h3>Published Guides</h3>
	<ul class="guideList hasRight">
		<c:forEach var="guide" items="${publishedGuides}">
			<li>
			<div class="right"><a href="/guide/${guide.id}" target="_blank"><img
				src="/images/fugue/icon_shadowless/binocular.png" alt="view"
				title="View collection guide" /></a> <a
				href="/guideBuilderDetails.do?method=edit&guideId=${guide.id}"><img
				src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit"
				title="Edit collection guide" /></a> <a
				href="/guide.do?method=copy&guideId=${guide.id}"><img
				src="/images/fugue/icon_shadowless/documents.png" alt="copy"
				title="Copy collection guide" /></a> <a id="delete_${guide.id}"
				href="/guide.do?method=delete&guideId=${guide.id}"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete"
				title="Delete collection guide" /></a> <script type="text/javascript">addLinkConfirm('delete_${guide.id}','Are you sure you want to delete this Collection Guide?');</script>
			</div>
			<span id="${guide.id}_title"><c:out value="${guide.title}" /></span>
			</li>
		</c:forEach>
	</ul>
	<br class="clearboth" />

	<script type="text/javascript">
      EPF.use(["button","menu"], "${applicationScope['org.portfolio.project.version']}", function(){
        new YAHOO.widget.Button('new', {type: 'menu', menu: 'newMenu' });
        $('newMenu').show();
      });
  </script>
</tags:communityPage>

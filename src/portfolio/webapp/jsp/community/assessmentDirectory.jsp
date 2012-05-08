<!-- $Name:  $ -->
<!-- $Id: assessmentDirectory.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:communityPage community="${community}"
	pageTitle="Assessment Models" id="manageModels"
	returnTo="editCommunity" jsModules="button,menu"
	cssClass="yui-skin-sam">
	<input type="button" id="new" name="new" value="New"
		style="display: none;">
	<h3>Basic Assessment Models</h3>
	<c:choose>
		<c:when test="${empty basicAssessmentModels}">
			<p>There are no basic assessment models for this community.</p>
		</c:when>
		<c:otherwise>
			<ul class="hasRight modelList">
				<c:forEach var="assessmentModel" items="${basicAssessmentModels}">
					<li>
					<div class="right"><a href="#"
						onclick="new dialog.AjaxWindow('/assessmentModelWindow.do',{parameters:{assessmentModelId: ${assessmentModel.id}}},{maxWidth:780, maxHeight:400}); return false;"><img
						src="/images/fugue/icon_shadowless/magnifier.png" alt="preview" /></a>
					<a id="delete_${assessmentModel.id}"
						href="assessmentDirectory.do?method=remove&communityId=${communityId}&assessmentModelId=${assessmentModel.id}"><img
						src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a></div>
					<a
						href="assessmentModelBuilder1.do?assessmentModelId=${assessmentModel.id}&communityId=${communityId}&assessmentModelId=${assessmentModel.id}&format=basic">${assessmentModel.name}</a>
					<script type="text/javascript">addLinkConfirm('delete_${assessmentModel.id}','Are you sure you want to delete this assessment model?');</script>
					</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>

	<h3>Outcome-based Assessment Models</h3>
	<c:choose>
		<c:when test="${empty outcomeAssessmentModels}">
			<p>There are no outcome assessment models for this community.</p>
		</c:when>
		<c:otherwise>
			<ul class="hasRight modelList">
				<c:forEach var="assessmentModel" items="${outcomeAssessmentModels}">
					<li>
					<div class="right"><a href="#"
						onclick="new dialog.AjaxWindow('/assessmentModelWindow.do',{parameters:{assessmentModelId: ${assessmentModel.id}}},{maxWidth:780, maxHeight:400}); return false;"><img
						src="/images/fugue/icon_shadowless/magnifier.png" alt="preview" /></a>
					<a id="delete_${assessmentModel.id}"
						href="assessmentDirectory.do?method=remove&communityId=${communityId}&assessmentModelId=${assessmentModel.id}"><img
						src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a></div>
					<a
						href="assessmentModelBuilder1.do?assessmentModelId=${assessmentModel.id}&communityId=${communityId}&format=outcome">${assessmentModel.name}</a>
					<script type="text/javascript">addLinkConfirm('delete_${assessmentModel.id}','Are you sure you want to delete this assessment model?');</script>
					</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>

	<h3>Rubric-based Assessment Models</h3>
	<c:choose>
		<c:when test="${empty rubricAssessmentModels}">
			<p>There are no rubric assessment models for this community.</p>
		</c:when>
		<c:otherwise>
			<ul class="hasRight modelList">
				<c:forEach var="assessmentModel" items="${rubricAssessmentModels}">
					<li>
					<div class="right"><a href="#"
						onclick="new dialog.AjaxWindow('/assessmentModelWindow.do',{parameters:{assessmentModelId: ${assessmentModel.id}}},{maxWidth:780, maxHeight:400}); return false;"><img
						src="/images/fugue/icon_shadowless/magnifier.png" alt="preview" /></a>
					<a id="delete_${assessmentModel.id}"
						href="assessmentDirectory.do?method=remove&communityId=${communityId}&assessmentModelId=${assessmentModel.id}"><img
						src="/images/fugue/icon_shadowless/cross.png" alt="delete" /></a></div>
					<a
						href="assessmentModelBuilder1.do?assessmentModelId=${assessmentModel.id}&communityId=${communityId}&format=rubric">${assessmentModel.name}</a>
					<script type="text/javascript">addLinkConfirm('delete_${assessmentModel.id}','Are you sure you want to delete this assessment model?');</script>
					</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
    document.observe('loader:success', function(event) { 
      new YAHOO.widget.Button('new', {
        type: 'menu', 
        menu: [
          { text: "Basic Assessment Model", url: "/assessmentModelBuilder1.do?communityId=${communityId}&format=basic" },
          { text: "Outcome-based Assessment Model", url: "/assessmentModelBuilder1.do?communityId=${communityId}&format=outcome" },
          { text: "Rubric-based Assessment Model", url: "/assessmentModelBuilder1.do?communityId=${communityId}&format=rubric" }
        ]
      });
      $('newMenu').show();
    });
  </script>
</tags:communityPage>

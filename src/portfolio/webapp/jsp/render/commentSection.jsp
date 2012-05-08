<!-- $Name:  $ -->
<!-- $Id: commentSection.jsp,v 1.6 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<a name="comments${entryId}"></a>
<div class="commentActions"><a id="showComments${entryId}"
	href="#">${fn:length(commentList)} Comments</a> | <a
	id="addComment${entryId}" href="#">Add Comment</a> <script
	type="text/javascript">
    $('showComments${entryId}').observe('click', function(event){
      event.stop();
      var sectionEl = $('commentSection${entryId}');
      var formEl = $('commentSlideToggle${entryId}');
      if (sectionEl.visible()) {
        Effect.SlideUp(sectionEl, { queue: 'end', duration: 0.5 });
        if (formEl) formEl.hide();
      } else {
        if (formEl) formEl.hide();
        Effect.SlideDown(sectionEl, { queue: 'end', duration: 0.5 });
      }
    });
    $('addComment${entryId}').observe('click', function(event){
      event.stop();
      var sectionEl = $('commentSection${entryId}');
      var formEl = $('commentSlideToggle${entryId}');
        if (!sectionEl.visible()) {
          Effect.SlideDown(sectionEl, { queue: 'end', duration: 0.5 });
          Effect.Appear(formEl, { queue: 'end', duration: 0.5 });
        } else if (sectionEl.visible() && !formEl.visible()) {
          Effect.Appear(formEl, { queue: 'end', duration: 0.5 });
        }
        $('commentForm${entryId}').focusFirstElement();
    });  
  </script></div>
<div id="commentSection${entryId}"
	style="clear:both; display:${param.showComments ? 'block' : 'none'};">
<div class="smoothSlide"><c:forEach items="${commentList}"
	var="comment">
	<c:choose>
		<c:when test="${parentType eq 'assessment'}">
			<c:set var="commenter"
				value="${comment.creator eq commentParent.evaluator and isAnonymous ? null : comment.creator}" />
		</c:when>
		<c:otherwise>
			<c:set var="commenter" value="${comment.creator}" />
		</c:otherwise>
	</c:choose>
	<!-- Check item ownership and type to set role type. -->
	<c:choose>
		<c:when test="${parentType == 'assessment'}">
			<c:set var="roleType" value="evaluator" />
			<c:if test="${ownerId == Person.personId}">
				<c:set var="roleType" value="assessed item author" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="roleType" value="reviewer" />
			<c:if test="${ownerId == Person.personId}">
				<c:set var="roleType" value="${parentType}&nbsp;author" />
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:set var="newLine" value="
"/>	<!-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 11-29-11-->
	<tags:comment person="${commenter}" date="${comment.commentDate}">
		<jsp:attribute name="commentHeader">entered a ${comment.visibility eq 2 ? 'private' : ''} comment.</jsp:attribute>
		<jsp:attribute name="commentBody">
			<!-- <em>&quot;${fn:escapeXml(comment.commentText)}&quot;</em>  -->
			<c:set var="commentText" value="${fn:escapeXml(comment.commentText)}" /> <!--  BK 11-29-11 and next line -->
			<em>&quot;${fn:replace(commentText, newLine, "<br/>")}&quot;</em>
		</jsp:attribute>
	</tags:comment>
</c:forEach>
<div class="commentEntry" id="commentSlideToggle${entryId}"
	style="display: none;">
<div class="smoothSlide"><c:if test="${not empty Person}">
	<form method="POST" action="/commentSave.do" id="commentForm${entryId}"
		class="commentForm"><input type="hidden"
		name="elementClassName"
		value="<c:out value='${commentParent.class.name}'/>"> <input
		type="hidden" name="entryId" value="${entryId}"> <input
		type="hidden" name="ownerId" value="${ospi:encrypt(ownerId)}">
	<input type="hidden" name="wrapperId" value="${param.wrapperId}" />
	<textarea id="commentText${entryId}" class="full" name="commentText"
		rows="4" cols="33">${empty comment ? '' : comment.commentText}</textarea>
	<span class="commentCounter" id="commentCounter${entryId}"></span> <c:choose>
		<c:when test="${Person.personId ne ownerId}">
			<label>Visible to</label>
			<br class="clearboth" />
			<div class="radiogroup"><input type="radio" name="visibility"
				value="2" id="vis2" checked="checked" /> <label for="vis2">you
			&amp; author</label> <input type="radio" name="visibility" value="3"
				id="vis3" /> <label for="vis3">everyone</label></div>
			<br class="clearboth" />
		</c:when>
		<c:otherwise>
			<input type="hidden" name="visibility" value="3" />
		</c:otherwise>
	</c:choose>
	<fieldset class="submitSet"><input type="submit" name="save"
		value="Save" /> or <a href="#"
		onclick="$('commentSlideToggle${entryId}').hide();return false;">cancel</a>
	</fieldset>
	</form>
	<script type="text/javascript">
                  new EPF.widget.TextareaCounter({
                      textareaEl: 'commentText${entryId}',
                      counterEl: 'commentCounter${entryId}',
                      max: 4000
                  });

                  $('commentForm${entryId}').observe('submit', function(event){
                      event.stop();
                      if ($F('commentText${entryId}').length > 4000) {
                          new dialog.Alert('A comment can not be longer than 4000 characters.');
                          return;
                      }
                      $('commentSlideToggle${entryId}').fade({duration:0.5});
                      this.request({
                          onSuccess: function(transport) {
                            $('${param.wrapperId}').update(transport.responseText);
                          }
                      });
                      this.disable();
                  });
                </script>
</c:if></div>
</div>
</div>
</div>
<script type="text/javascript"> if (window.location.hash == '#comments${entryId}') {$('commentSlideToggle${entryId}').show();};</script>

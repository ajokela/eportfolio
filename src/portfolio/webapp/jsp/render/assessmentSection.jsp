<!-- $Name:  $ -->
<!-- $Id: assessmentSection.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="assessmentActions"><a id="showAssessments${entryId}"
	href="#">${fn:length(assessmentList)} Assessments</a> <script
	type="text/javascript">
    $('showAssessments${entryId}').observe('click', function (event) {
      var sectionEl = $('assessmentSection${entryId}');
      var formEl = $('assessmentSlideToggle${entryId}');
      if (sectionEl.visible()) {
        Effect.SlideUp(sectionEl, { queue: 'end', duration: 0.5 });
        if (formEl) formEl.hide();
      } else {
        if (formEl) formEl.hide();
        Effect.SlideDown(sectionEl, { queue: 'end', duration: 0.5 });
      }
      event.stop();
    });
  </script> <c:if
	test="${viewerRole eq 'evaluator' and hasFinalScore eq 'false'}">
    |
    <a id="addAssessment${entryId}" href="#">Add Assessment</a>
	<script type="text/javascript">
      $('addAssessment${entryId}').observe('click', function (event) {
        var sectionEl = $('assessmentSection${entryId}');
        var formEl = $('assessmentSlideToggle${entryId}');
        if (!sectionEl.visible()) {
          Effect.SlideDown(sectionEl, { queue: 'end', duration: 0.5 });
          Effect.Appear(formEl, { queue: 'end', duration: 0.5 });
        } else if (sectionEl.visible() && !formEl.visible()) {
          Effect.Appear(formEl, { queue: 'end', duration: 0.5 });
        }
        $('evalAssessmentForm${entryId}').focusFirstElement();
        event.stop();
      });
    </script>
</c:if></div>
<div id="assessmentSection${entryId}"
	style="display:${param.showAssessments ? 'block' : 'none'}; clear:both;">
<div class="smoothSlide"><c:forEach var="assessment"
	items="${assessmentList}" varStatus="status">
	<c:set var="format"
		value="${assessmentModelAssignment.assessmentModel.format}" />
	<tags:comment
		person="${assessmentModelAssignment.anonymous ? null : assessment.evaluator}"
		date="${assessment.assessedDate}">
		<jsp:attribute name="commentHeader">entered a ${assessment.assessmentType} assessment.</jsp:attribute>
		<jsp:attribute name="commentBody">
            <c:choose>
              <c:when test="${format eq 'basic'}">
                <strong>Score:</strong> ${assessment.scoreAssignment}
              </c:when>
              <c:when
					test="${format eq 'outcome' or format eq 'rubric'}">
                <strong>Overall Score:</strong> ${assessment.overallScore}
              </c:when>
            </c:choose>
          </jsp:attribute>
		<jsp:attribute name="commentDetail">
            <c:if test="${format eq 'outcome' or format eq 'rubric'}">
              <c:choose>
                <c:when
						test="${assessmentModelAssignment.assessmentModel.format eq 'outcome'}">
                  <c:forEach var="objective"
							items="${assessmentModelAssignment.assessmentModel.assessedObjectives}"
							varStatus="loop">
                    <div class="objectiveDesc"><strong><c:out
								value="${objective.name}" /></strong> ${objective.description}</div>
                    <div class="scoreLabel">Score</div>
							<div class="scoreValue">${assessment.scores[loop.index]}</div>
                    <br class="clearboth" />
                  </c:forEach>
                </c:when>
                <c:when
						test="${assessmentModelAssignment.assessmentModel.format eq 'rubric'}">
                  <tags:displayRubric
							assessmentModel="${assessmentModelAssignment.assessmentModel}"
							assessment="${assessment}" />
                </c:when>
              </c:choose>
            </c:if>
          </jsp:attribute>
	</tags:comment>
	<div class="assessmentCommentOuter">
	<div class="assessmentComment">
	<div id="acwrapper_${assessment.id}"><img alt="loading..."
		src="/images/indicator_arrows.gif" /></div>
	<script type="text/javascript">
	               new Ajax.Updater('acwrapper_${assessment.id}','/commentSection.do',{
	                 evalScripts: true, 
	                 parameters: {
	                   assessmentId: '${assessment.id}',
	                   wrapperId: 'acwrapper_${assessment.id}',
	                   showComments: false
	                 }
	               });
	             </script></div>
	</div>
</c:forEach> <!-- if the person is an evaluator and a final score has not yet been recorded by the person, allow entry of a score -->
<c:if test="${viewerRole eq 'evaluator' and hasFinalScore eq 'false'}">
	<div class="assessmentEntry" id="assessmentSlideToggle${entryId}"
		style="display: none;">
	<form method="post" action="/saveAssessment.do"
		id="evalAssessmentForm${entryId}" class="assessmentForm"><input
		type="hidden" name="shareId" value="${shareId}" /> <input
		type="hidden" name="assessmentModelId"
		value="${assessmentModelAssignment.assessmentModel.id}" /> <input
		type="hidden" name="assessedItemId" value="${entryId}" /> <input
		type="hidden" name="wrapperId" value="${param.wrapperId}" /> <input
		type="hidden" name="assessedItemType" value="${portfolioItemType}" />
	<div class="assessmentEntryHeading">Enter an assessment:</div>
	<!-- Show assessment model --> <c:choose>
		<c:when
			test="${assessmentModelAssignment.assessmentModel.format == 'basic'}">
			<tags:evalScore
				assessmentModel="${assessmentModelAssignment.assessmentModel}" />
		</c:when>
		<c:when
			test="${assessmentModelAssignment.assessmentModel.format == 'outcome'}">
			<tags:evalOutcome
				assessmentModel="${assessmentModelAssignment.assessmentModel}" />
		</c:when>
		<c:when
			test="${assessmentModelAssignment.assessmentModel.format == 'rubric'}">
			<tags:evalRubric
				assessmentModel="${assessmentModelAssignment.assessmentModel}"
				entryId="${entryId}" />
		</c:when>
	</c:choose> <!-- Select assessment type --> <label>Type</label>
	<div class="radiogroup"><input id="prelimScoreType" type="radio"
		name="scoreType" value="preliminary" checked="checked" /> <label
		for="prelimScoreType">preliminary</label> <input id="finalScoreType"
		type="radio" name="scoreType" value="final" /> <label
		for="finalScoreType">final</label></div>
	<br class="clearboth" />
	<fieldset class="submitSet"><label></label> <input
		type="submit" name="save" value="Save" /> &nbsp;or <a href="#"
		onclick="$('assessmentSlideToggle${entryId}').hide();return false;">cancel</a>
	</fieldset>
	</form>
	<script type="text/javascript">
          $('evalAssessmentForm${entryId}').observe('submit', function(event){
              var format = '${assessmentModelAssignment.assessmentModel.format}';
              if (format == 'rubric') {
            	  if ($$('#evalAssessmentForm${entryId} input[name="scoreIndex"]').any(function(e) { return $F(e) < 0;})) {
                   new dialog.Alert({messageText:'You must select a value for each row!'}).show();
                   event.stop();
                   return;
                }
              }    
        	    $('assessmentSlideToggle${entryId}').fade({duration: 0.5});
	            this.request({
	                onSuccess: function(transport) {
	                  $('${param.wrapperId}').update(transport.responseText);
	                }  
	            });
	            this.disable();
	            event.stop();
          });    
        </script></div>
	<!-- end assessmentEntry -->
</c:if></div>
</div>

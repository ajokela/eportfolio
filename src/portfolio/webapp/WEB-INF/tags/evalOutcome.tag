<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ tag body-content="empty" import="org.portfolio.util.Configuration"%>
<%@ attribute name="assessmentModel" required="true"
	type="org.portfolio.model.assessment.AssessmentModel"%>
<%@ attribute name="assessment" required="false"
	type="org.portfolio.model.assessment.Assessment"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div>
	<div>
		<span style="font-size: 10pt; font-weight: bold;">${assessmentModel.name}</span>
	</div>

	<div style="padding-bottom: 5px;">
		${assessmentModel.description}	
	</div>
	
	<div>
	
<!-- This should include validation based on scoringModel.valueType and scoringModel.dataType -->
<c:forEach var="objective" items="${assessmentModel.assessedObjectives}"
	varStatus="loop">
	<div class="objectiveWrapper">
	<div class="objectiveDesc"><strong><c:out
		value="${objective.name}" /></strong> ${objective.description}</div>
	<label>Score</label> <select name="scoreIndex"
		id="scoreValue:${objective.displaySequence}">
		<option value="">(please select:)</option>
		<c:forEach var="score" items="${assessmentModel.scoringModel.scores}"
			varStatus="loop">
			<option name="score" id="score:${loop.index}" value="${loop.index}">${score}</option>
		</c:forEach>
	</select> <br class="clearboth" />
	</div>
</c:forEach>

	</div>

</div>
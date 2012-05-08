<%@ tag body-content="empty" import="org.portfolio.util.Configuration"%>
<%@ attribute name="assessmentModel" required="true"
	type="org.portfolio.model.assessment.AssessmentModel"%>

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
	<!-- Show scoring section -->
	<label>Score</label>
	<select name="scoreIndex" id="scoreValue">
		<option value="">(please select:)</option>
		<c:forEach var="score" items="${assessmentModel.scoringModel.scores}"
			varStatus="loop">
			<option value="${loop.index}"><c:out value="${score}" /></option>
		</c:forEach>
	</select>
	<br class="clearboth" />
	</div>

</div>
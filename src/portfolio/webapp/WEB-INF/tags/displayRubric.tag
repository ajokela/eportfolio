<%@ tag body-content="empty" import="org.portfolio.util.Configuration"%>
<%@ attribute name="assessmentModel" required="true"
	type="org.portfolio.model.assessment.AssessmentModel"%>
<%@ attribute name="assessment" required="true"
	type="org.portfolio.model.assessment.Assessment"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="javascript">
  function showExtPerfDesc(id){
    if($('ext:'+id).value!="")
      new dialog.Alert({messageText:'<b>Extended Performance Descriptor:</b><br/>'+$('ext:'+id).value}).show();
    else
      new dialog.Alert({messageText:'<b>Extended Performance Descriptor:</b><br/>None'}).show();
  }
</script>

<!-- Rubric display creation -->
<c:set var="assessment" value="${assessment}" />
<table class="rubric display" border="0" cellspacing="0">
	<tr>
		<th></th>
		<c:forEach var="col" items="${assessmentModel.scoringModel.scores}"
			varStatus="loop">
			<th class="scoreNameCell"><c:out value="${col}" /></th>
		</c:forEach>
	</tr>
	<c:forEach var="objective"
		items="${assessmentModel.assessedObjectives}" varStatus="outerLoop">
		<tr>
			<td class="objNameCell"><c:out value="${objective.name}" /></td>
			<c:forEach var="col" items="${assessmentModel.scoringModel.scores}"
				varStatus="loop">
				<td
					class="descriptorCell ${assessment.scores[outerLoop.index] == col ? 'selected' : ''}"
					ondblclick="showExtPerfDesc('${objective.displaySequence}:${col}')"
					readonly="true"><c:out
					value="${objective.performanceDescriptors[loop.index].name}"></c:out>
				<%-- <script>
            $('${objective.displaySequence}:${col}').observe('dblclick', function(event) {
              id = '${objective.displaySequence}:${col}';
              if($('ext:'+id).value!="") {
                new dialog.Alert({messageText:'<b>Extended Performance Descriptor:</b><br/>'+$('ext:'+id).value}).show();
              } else {
                new dialog.Alert({messageText:'<b>Extended Performance Descriptor:</b><br/>None'}).show();
              }
            });
          </script>--%></td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>

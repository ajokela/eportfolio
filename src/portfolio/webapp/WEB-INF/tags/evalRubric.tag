<%@ tag body-content="empty" import="org.portfolio.util.Configuration"%>
<%@ attribute name="assessmentModel" required="true"
	type="org.portfolio.model.assessment.AssessmentModel"%>
<%@ attribute name="entryId" required="true"%>
<%@ attribute name="assessment" required="false"
	type="org.portfolio.model.assessment.Assessment"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="rubric" border=0 cellspacing="0">
	<tr>
		<th colspan="${assessmentModelCount}" style="font-size: 10pt; font-weight: bold;">${assessmentModel.name}</th>
	</tr>
	<tr style="padding-bottom: 5px;">
		<td colspan="${assessmentModelCount}">${assessmentModel.description}</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	
	<tr>
		<th>&nbsp;</th>
		<c:forEach var="col" items="${assessmentModel.scoringModel.scores}"
			varStatus="loop">
			<th class="scoreNameCell"><c:out value="${col}" /></th>
		</c:forEach>
	</tr>
	<c:forEach var="objective"
		items="${assessmentModel.assessedObjectives}" varStatus="outerLoop">
		<input type="hidden" name="scoreIndex"
			id="${entryId}:scoreIndex:${objective.displaySequence}" value="-1" />
		<tr id="${entryId}_row${objective.displaySequence}">
			<td class="objNameCell"><c:out value="${objective.name}" /></td>
			<c:forEach var="col" items="${assessmentModel.scoringModel.scores}"
				varStatus="loop">
				<td class="descriptorCell"
					id="${entryId}:${objective.displaySequence}:${col}" readonly="true">
				<c:out value="${objective.performanceDescriptors[loop.index].name}" />
				<input type="hidden" name="extPerformanceDescriptors"
					id="${entryId}:ext:${objective.displaySequence}:${col}"
					value="${objective.performanceDescriptors[loop.index].description}" />
				<script>
            $('${entryId}:${objective.displaySequence}:${col}')
              .observe('click', function(event) {
					      $('${entryId}:scoreIndex:${objective.displaySequence}').value = '${loop.index}';
					      $$('#${entryId}_row${objective.displaySequence} td').invoke('removeClassName','selected');
					      $('${entryId}:${objective.displaySequence}:${col}').addClassName('selected');
              })
              .observe('dblclick', function(event) {
                id = '${entryId}:${objective.displaySequence}:${col}';
						    if($('ext:'+id).value!="") {
						      new dialog.Alert({messageText:'<b>Extended Performance Descriptor:</b><br/>'+$('ext:'+id).value}).show();
						    } else {
						      new dialog.Alert({messageText:'<b>Extended Performance Descriptor:</b><br/>None'}).show();
						    }
              });
          </script></td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>

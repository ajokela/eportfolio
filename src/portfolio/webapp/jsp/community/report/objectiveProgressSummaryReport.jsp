<!-- $Name:  $ -->
<!-- $Id: objectiveProgressSummaryReport.jsp,v 1.6 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<tags:communityPage community="${community}"
	pageTitle="Objective Progress Summary Report"
	id="objectiveSummaryReport" jsModules="button,dhtmlcalendar"
	cssClass="yui-skin-sam report" returnTo="communityReports">
	<div id="reportCriteria"><c:if test="${not empty objectiveSets}">

		<form action="/objectiveProgressSummaryReport.do" method="get">
		<input type="hidden" name="communityId" value="${param.communityId}" />
		<dl>
			<c:if test="${not empty members}">
				<dt>Select members</dt>
				<dd><select name="uid" multiple="multiple" size="5">
					<c:forEach var="person" items="${members}">
						<option value="${person.personId}"
							${ospi:contains(paramValues.uid,person.personId) ? 'selected="selected"' : ''}>${person.displayName}</option>
					</c:forEach>
				</select></dd>
			</c:if>
			<dt>Select objective set</dt>
			<dd><select name="objectiveId">
				<c:forEach var="objective" items="${objectiveSets}">
					<option value="${objective.id}" ${objective.id==
						param.objectiveId ? 'selected="selected"' : ''}>
					${objective.name}</option>
				</c:forEach>
			</select></dd>
			<dt>&nbsp;</dt>

			<dd style="margin-bottom: 5px;">
			<div>
			<div style="display: block; float: left;"><strong>From:</strong>
			<input type="text" id="dateFrom" name="dateFromString" value="None"
				style="width: 100px" readonly="readonly" />&nbsp; <img id="trigger1"
				src="/images/calendar.jpg" style="cursor: pointer;" /> <a href="#"
				onclick="$('dateFrom').value='None';return false;">clear</a> <script
				type="text/javascript">
						document.observe('loader:success', function() {
							var today = new Date();
							Calendar.setup({
							    inputField: 'dateFrom',
							    ifFormat: "%m/%d/%Y",
							    button: 'trigger1',
							    weekNumbers: false
							    
							  }
							);
						}); 
					</script></div>
			</div>
			<div>
			<div style="display: block; float: left;"><strong>To:</strong>
			<input type="text" id="dateTo" name="dateToString" value="None"
				style="width: 100px" readonly="readonly" />&nbsp; <img id="trigger2"
				src="/images/calendar.jpg" style="cursor: pointer;" /> <a href="#"
				onclick="$('dateTo').value='None';return false;">clear</a> <script
				type="text/javascript">
						document.observe('loader:success', function() {
							var today = new Date();
							Calendar.setup({
							    inputField: 'dateTo',
							    ifFormat: "%m/%d/%Y",
							    button: 'trigger2',
							    weekNumbers: false
							    
							  }
							);
						}); 
					</script></div>
			</div>
			</dd>

			<dt>&nbsp;</dt>
			<dd><input type="submit" name="run" value="Run" /></dd>
		</dl>
		</form>

	</c:if> <c:if test="${empty objectiveSets}">
		<b>No Objectives Found - Unable to use this report.</b>
	</c:if></div>
	<c:if test="${param.run != null}">
		<hr />
		<div id="export"><a id="exportToExcel"
			href="${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}&export"
			style="display: none;">Export to Excel</a> <script
			type="text/javascript">
       document.observe('loader:success', function(event) { new YAHOO.widget.Button("exportToExcel");});
     </script></div>
		<div id="report">
		<table class="reportTable">
			<tr>
				<th>&nbsp;</th>
				<th>Number of assessments</th>
				<th class="rightmost">Average score</th>
			</tr>
			<c:forEach var="objective"
				items="${objectiveSet.flatSubObjectivesList}" varStatus="status">
				<tr class="titleRow ${status.first ? 'first' : ''}">
					<td class="title">${objective.name}</td>
					<td>&nbsp;</td>
					<td class="rightmost">&nbsp;</td>
				</tr>
				<c:forEach var="aggregateData" items="${objectiveMap[objective]}">
					<tr>
						<td>${aggregateData.person.displayName}</td>
						<td>${aggregateData.numAssessments}</td>
						<td class="rightmost"><c:if
							test="${not aggregateData.averageScore.naN}">
							<fmt:formatNumber value="${aggregateData.averageScore}"
								maxFractionDigits="2" />
						</c:if> &nbsp;</td>
					</tr>
				</c:forEach>
				<tr>
					<td><strong>Total</strong></td>
					<td><strong>${totalsMap[objective].numAssessments}</strong></td>
					<td class="rightmost"><strong> <c:if
						test="${not totalsMap[objective].averageScore.naN}">
						<fmt:formatNumber value="${totalsMap[objective].averageScore}"
							maxFractionDigits="2" />
					</c:if> &nbsp; </strong></td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</c:if>
</tags:communityPage>

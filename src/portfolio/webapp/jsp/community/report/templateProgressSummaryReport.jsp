<!-- $Name:  $ -->
<!-- $Id: templateProgressSummaryReport.jsp,v 1.5 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<tags:communityPage community="${community}"
	pageTitle="Template Progress Summary Report" id="templateSummaryReport"
	jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report"
	returnTo="communityReports">
	<div id="reportCriteria">
	<form action="/templateProgressSummaryReport.do" method="get"><input
		type="hidden" name="communityId" value="${param.communityId}" />
	<dl>
		<c:if test="${not empty members}">
			<dt>Select member</dt>
			<dd><select name="uid" multiple="multiple" size="5">
				<c:forEach var="person" items="${members}">
					<option value="${person.personId}"
						${ospi:contains(paramValues.uid,person.personId) ? 'selected="selected"' : ''}>${person.displayName}</option>
				</c:forEach>
			</select></dd>
		</c:if>
		<dt>Select template</dt>
		<dd><select name="templateId">
			<c:forEach var="template" items="${assessableTemplates}">
				<option value="${template.id}" ${template.id==
					param.templateId ? 'selected="selected"' : ''}>
				${template.name}</option>
			</c:forEach>
		</select></dd>

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
		<div style="display: block; float: left;"><strong>To:</strong> <input
			type="text" id="dateTo" name="dateToString" value="None"
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
	</div>
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
				<th class="rightmost">Average Score</th>
			</tr>
			<c:if test="${template.assessmentModelAssignment ne null}">
				<tr class="titleRow first">
					<td class="title">entire portfolio</td>
					<td>&nbsp;</td>
					<td class="rightmost">&nbsp;</td>
				</tr>
				<c:forEach var="aggregateData" items="${portfolioData}">
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
					<td><strong>${totalPortfolioData.numAssessments}</strong></td>
					<td class="rightmost"><strong> <c:if
						test="${not totalPortfolioData.averageScore.naN}">
                 ${totalPortfolioData.averageScore}
               </c:if> &nbsp; </strong></td>
				</tr>
			</c:if>
			<c:if test="${not empty elements}">
				<c:forEach var="tem" items="${elements}" varStatus="temStatus">
					<tr
						class="titleRow ${tem.assessmentModelAssignment eq null and temStatus.first ? 'first' : ''}">
						<td class="title">${tem.elementTitle}</td>
						<td>&nbsp;</td>
						<td class="rightmost">&nbsp;</td>
					</tr>
					<c:forEach var="aggregateData" items="${portfolioElementMap[tem]}">
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
						<td><strong>${portfolioElementTotalsMap[tem].numAssessments}</strong></td>
						<td class="rightmost"><strong> <c:if
							test="${not portfolioElementTotalsMap[tem].averageScore.naN}">
							<fmt:formatNumber
								value="${portfolioElementTotalsMap[tem].averageScore}"
								maxFractionDigits="2" />
						</c:if> &nbsp; </strong></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		</div>
	</c:if>
</tags:communityPage>

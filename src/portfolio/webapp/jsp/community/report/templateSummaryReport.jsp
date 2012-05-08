<!-- $Name:  $ -->
<!-- $Id: templateSummaryReport.jsp,v 1.5 2011/02/09 19:40:32 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<tags:communityPage community="${community}" pageTitle="Community Summary Report" id="individualTemplateReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
	<div id="reportCriteria">
	<form action="/community/reports/templateSummaryReport/${communityId}/detailed" method="get">
	
	<dl>
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

		<div style="clear: both;"></div>

		<div>
		
			<input type="hidden" name="group" value="student" />

		</div>
		
		<div style="height: 25px;">
			&nbsp;
		</div>
		
		<div>
			<select name="template">
				<option value="-1" <c:if test="${template eq '-1'}">selected="selected"</c:if>>All Templates</option>
			<c:forEach var="tplate" items="${assessableTemplates}">
				<option value="${tplate.id}" <c:if test="${template eq tplate.id}">selected="selected"</c:if>>${tplate.name}</option>
			</c:forEach>
			
			</select>
		</div>

		<dt>&nbsp;</dt>


		<dd><input type="submit" name="report" value="Run" /></dd>
		
	</dl>
	</form>
	</div>
	
	<c:if test="${report != null}">
		<hr />
		<div id="export"><a id="exportToExcel"
			href="${requestScope['javax.servlet.forward.request_uri']}/${group}/${templateId}/${dateFromString}/${dateToString}/summary"
			style="display: none;">Export to Excel</a> 
			<script type="text/javascript">
          		document.observe('loader:success', function(event) { new YAHOO.widget.Button("exportToExcel"); });
        	</script>
        </div>
        <div style="clear: both;"></div>
        <div></div>
        
			<table style="width: 1100px;">
				<tr>
					<th style="width: 400px;">Student</th>
					<th>Portfolio Name</th>
					<th>Template</th>
					<th>Last Modified</th>
					<th>Evaluator(s)</th>					
					<th>Prelim</th>
					<th>Final</th>
				</tr>					
		
			<c:forEach var="studentPortfolios" items="${portfoliosToStudent}" varStatus="idx">
				
				<c:choose>
					<c:when test="${idx.count % 2 == 0}">
	            		<c:set var="rowColor" scope="page" value="#dddddd"/>
	          		</c:when>
	          		<c:otherwise>
	            		<c:set var="rowColor" scope="page" value="#ffffff"/>
	          		</c:otherwise>
				</c:choose>
				
				
			
				<c:forEach var="portfolio" items="${studentPortfolios.value}">
					<tr>
					<td style="background-color: ${rowColor}; width: 400px;">${studentPortfolios.key.displayName}</td>
					<c:if test="${portfolio.assessmentModelAssignment ne null}">
					
						<c:set var="template" value="${portfolioTemplateMap[portfolio]}" />
						<c:set var="rowData" value="${portfolioMap[portfolio]}" />
						
							<td style="background-color: ${rowColor};">${portfolio.shareName}</td>
							<td style="background-color: ${rowColor};">${template.name}</td>
							<td style="background-color: ${rowColor};"><fmt:formatDate value="${portfolio.dateModified}" pattern="M/d/yy" /></td>	
							
							<td style="background-color: ${rowColor};">
								<table style="width: 100%;">
								<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
									<tr>
										<td>${evaluator.person.displayName}</td>
									</tr>
								</c:forEach>
								</table>
							</td>
					
							<td style="background-color: ${rowColor};">
								<table>
									<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
										<c:set var="assessment" value="${rowData.prelimAssessments[evaluator.person]}" /> 
										<tr>
											<td>
												<c:choose>
													<c:when test="${assessment eq null}">&nbsp;</c:when>
													<c:otherwise>
														${assessment.overallScore}
													</c:otherwise>
												</c:choose>									
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>

							<td style="background-color: ${rowColor};">
								<table>
									<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
										<c:set var="assessment" value="${rowData.finalAssessments[evaluator.person]}" /> 
										<tr>
											<td>
												<c:choose>
													<c:when test="${assessment eq null}">&nbsp;</c:when>
													<c:otherwise>
														${assessment.overallScore}
													</c:otherwise>
												</c:choose>									
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>


					</c:if>

					<c:if test="${portfolio.assessmentModelAssignment eq null}">
						<td style="width: 100%; background-color: ${rowColor};" colspan="6">&nbsp;</td>
					</c:if>
					
					</tr>
					
				</c:forEach>
			
				
				
			</c:forEach>
			
			</table>
			
	</c:if>
</tags:communityPage>

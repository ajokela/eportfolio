<!-- $Name:  $ -->
<!-- $Id: templateDetailedReport.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tags:communityPage community="${community}" pageTitle="Community Detailed Report" id="individualTemplateReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
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
	<!--BK, 11/21/11 added fn:replace and changed to templateSummaryReportExport in href below-->      
	<div id="export"><a id="exportToExcel"
		href="/community/reports/templateSummaryReportExport/${community.id}/detailed/${group}/${templateId}/${fn:replace(dateFromString,'/','')}/${fn:replace(dateToString,'/','')}/export"
		style="display: none;">Export to Excel</a> 
		<script type="text/javascript">
        		document.observe('loader:success', function(event) { new YAHOO.widget.Button("exportToExcel"); });
      	</script>
	</div>
	
		<div>
		
			<c:forEach var="studentPortfolios" items="${portfoliosToStudent}" varStatus="idx">
				
				<div style="font-size: 14pt; padding-top: 10px;">
					${studentPortfolios.key.displayName}
				</div>
				
				<div style="clear: both;"></div>
			
				<c:choose>
					<c:when test="${idx.count % 2 == 0}">
	            		<c:set var="rowColor" scope="page" value="#dddddd"/>
	          		</c:when>
	          		<c:otherwise>
	            		<c:set var="rowColor" scope="page" value="#ffffff"/>
	          		</c:otherwise>
				</c:choose>
		
				<div>
				<c:forEach var="portfolio" items="${studentPortfolios.value}">
					<c:if test="${portfolio.assessmentModelAssignment ne null}">
					
					<h2>${portfolio.shareName} - <fmt:formatDate value="${portfolio.dateModified}" pattern="M/d/yy" /></h2>
					
					<table class="reportTable">
						<tr>
							<th>&nbsp;</th>
							<th>Evaluator</th>
							<th class="rightmost">Prelim</th>
							<th class="rightmost" style="border-left: 1px solid #ddd;">Final</th>
						</tr>
						<c:if test="${portfolio.assessmentModelAssignment ne null}">
							<tr class="titleRow first">
								<td class="title" style="font-size: 12pt; font-weight: bold;">Entire Portfolio</td>
								<td>&nbsp;</td>
								<td class="rightmost">&nbsp;</td>
								<td class="rightmost" style="border-left: 1px solid #ddd;">&nbsp;</td>
							</tr>
							<c:set var="rowData" value="${portfolioMap[portfolio]}" />
							<c:choose>
								<c:when test="${empty rowData.evaluators}">
									<tr>
										<td><img src="/images/fugue/icon_shadowless/document.png" />
											<a target="_blank" href="/portfolio/${portfolio.shareId}">${portfolio.shareName}</a>
										</td>
										<td>&nbsp;</td>
										<td class="rightmost">&nbsp;</td>
										<td class="rightmost" style="border-left: 1px solid #ddd;">&nbsp;</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
										<tr>
											<td>
												<c:choose>
													<c:when test="${status.first}">
														<img src="/images/fugue/icon_shadowless/document.png" />
														<a target="_blank" href="/portfolio/${portfolio.shareId}">${portfolio.shareName}</a>
													</c:when>
													<c:otherwise>
							                        	&nbsp;
							                      	</c:otherwise>
												</c:choose>
											</td>
											<td>${evaluator.person.displayName}</td>

											<td class="rightmost">
											
												<c:set var="assessment" value="${rowData.prelimAssessments[evaluator.person]}" /> 
												<c:choose>
													<c:when test="${assessment eq null}">&nbsp;</c:when>
													<c:otherwise>
														${assessment.overallScore}
													</c:otherwise>
												</c:choose>
											
											</td>

											<td class="rightmost" style="border-left: 1px solid #ddd;">
											
												<c:set var="assessment" value="${rowData.finalAssessments[evaluator.person]}" /> 
												<c:choose>
													<c:when test="${assessment eq null}">&nbsp;</c:when>
													<c:otherwise>
														${assessment.overallScore}
													</c:otherwise>
												</c:choose>
											
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${not empty temMap[portfolio]}">
							<c:forEach var="tem" items="${temMap[portfolio]}" varStatus="temStatus">
								<tr
									class="titleRow ${portfolio.assessmentModelAssignment eq null and temStatus.first ? 'first' : ''}">
									<td class="title">${tem.elementTitle}</td>
									<td>&nbsp;</td>
									<td class="rightmost">&nbsp;</td>
									<td class="rightmost" style="border-left: 1px solid #ddd;">&nbsp;</td>
								</tr>
								<c:choose>
									<c:when test="${empty shareEntryMap[tem]}">
										<tr class="elementRow">
											<td>No entries</td>
											<td>&nbsp;</td>
											<td class="rightmost">&nbsp;</td>
											<td class="rightmost" style="border-left: 1px solid #ddd;">&nbsp;</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="rowData" items="${shareEntryMap[tem]}">
											<c:choose>
												<c:when test="${empty rowData.evaluators}">
													<tr class="elementRow">
														<td><img
															src="/images/fugue/icon_shadowless/document_small.png" />
														<a target="_blank"
															href="/portfolio/${rowData.assessable.shareId}#shareEntry${entry.id}"><c:out
															value="${rowData.assessable.element.entryName}" /></a></td>
														<td>&nbsp;</td>
														<td class="rightmost">&nbsp;</td>
														<td class="rightmost" style="border-left: 1px solid #ddd;">&nbsp;</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
														<tr class="elementRow">
															<td>
																<c:choose>
																	<c:when test="${status.first}">
																		<img src="/images/fugue/icon_shadowless/document_small.png" />
																		<a target="_blank"
																			href="/portfolio/${rowData.assessable.shareId}#shareEntry${entry.id}">${rowData.assessable.element.entryName}</a>
																	</c:when>
																	<c:otherwise>
											                          &nbsp;
											                        </c:otherwise>
																</c:choose>
															</td>
															<td>${evaluator.person.displayName}</td>
															<td class="rightmost">
															
																<c:set var="assessment" value="${rowData.prelimAssessments[evaluator.person]}" /> 
																<c:choose>
																	<c:when test="${assessment eq null}">&nbsp;</c:when>
																	<c:otherwise>
																		${assessment.overallScore}
																	</c:otherwise>
																</c:choose>
															
															</td>
															
															<td class="rightmost" style="border-left: 1px solid #ddd;">
															
																<c:set var="assessment" value="${rowData.finalAssessments[evaluator.person]}" /> 
																<c:choose>
																	<c:when test="${assessment eq null}">&nbsp;</c:when>
																	<c:otherwise>
																		${assessment.overallScore}
																	</c:otherwise>
																</c:choose>
															
															</td>
															
															
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</table>
					
					</c:if>
					
				</c:forEach>
				</div>
			
				<div style="clear: both;"><hr /></div>
			
			</c:forEach>
			
		</div>
		
	</c:if>
</tags:communityPage>

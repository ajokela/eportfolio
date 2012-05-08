<!-- $Name:  $ -->
<!-- $Id: individualTemplateProgressReport.jsp,v 1.7 2011/01/06 15:51:30 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<tags:communityPage community="${community}"
	pageTitle="Individual Template Progress Report"
	id="individualTemplateReport" jsModules="button,dhtmlcalendar"
	cssClass="yui-skin-sam report" returnTo="communityReports">
	<div id="reportCriteria">
	<form action="/individualTemplateProgressReport.do" method="get">
	<input type="hidden" name="communityId" value="${param.communityId}" />
	<dl>
		<c:if test="${not empty members}">
			<dt>Select member</dt>
			<dd><select name="euid">
				<c:forEach var="person" items="${members}">
					<c:set var="euid"
						value="${ospi:encode(ospi:encrypt(person.personId))}" />
					<option value="${euid}" ${euid== param.euid ? 'selected="selected"' : ''}>${person.displayName}</option>
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
		<c:forEach var="portfolio" items="${portfolios}">
			<h2>${portfolio.shareName} - <fmt:formatDate
				value="${portfolio.dateModified}" pattern="M/d/yy" /></h2>
			<table class="reportTable">
				<tr>
					<th>&nbsp;</th>
					<th>Evaluator</th>
					<th class="rightmost">Score</th>
				</tr>
				<c:if test="${portfolio.assessmentModelAssignment ne null}">
					<tr class="titleRow first">
						<td class="title">entire portfolio</td>
						<td>&nbsp;</td>
						<td class="rightmost">&nbsp;</td>
					</tr>
					<c:set var="rowData" value="${portfolioMap[portfolio]}" />
					<c:choose>
						<c:when test="${empty rowData.evaluators}">
							<tr>
								<td><img src="/images/fugue/icon_shadowless/document.png" />
								<a target="_blank" href="/portfolio/${portfolio.shareId}"><c:out
									value="${portfolio.shareName}" /></a></td>
								<td>&nbsp;</td>
								<td class="rightmost">&nbsp;</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="evaluator" items="${rowData.evaluators}"
								varStatus="status">
								<tr>
									<td><c:choose>
										<c:when test="${status.first}">
											<img src="/images/fugue/icon_shadowless/document.png" />
											<a target="_blank" href="/portfolio/${portfolio.shareId}"><c:out
												value="${portfolio.shareName}" /></a>
										</c:when>
										<c:otherwise>
	                        &nbsp;
	                      </c:otherwise>
									</c:choose></td>
									<td>${evaluator.person.displayName}</td>
									<td class="rightmost"><c:set var="assessment"
										value="${rowData.assessments[evaluator.person]}" /> <c:choose>
										<c:when test="${assessment eq null}">&nbsp;</c:when>
										<c:otherwise>${assessment.overallScore}<c:if
												test="${not assessment.final}">*</c:if>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${not empty temMap[portfolio]}">
					<c:forEach var="tem" items="${temMap[portfolio]}"
						varStatus="temStatus">
						<tr
							class="titleRow ${portfolio.assessmentModelAssignment eq null and temStatus.first ? 'first' : ''}">
							<td class="title">${tem.elementTitle}</td>
							<td>&nbsp;</td>
							<td class="rightmost">&nbsp;</td>
						</tr>
						<c:choose>
							<c:when test="${empty shareEntryMap[tem]}">
								<tr class="elementRow">
									<td>No entries</td>
									<td>&nbsp;</td>
									<td class="rightmost">&nbsp;</td>
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
													href="/portfolio/shareId=${rowData.assessable.shareId}#shareEntry${entry.id}"><c:out
													value="${rowData.assessable.element.entryName}" /></a></td>
												<td>&nbsp;</td>
												<td class="rightmost">&nbsp;</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach var="evaluator" items="${rowData.evaluators}"
												varStatus="status">
												<tr class="elementRow">
													<td><c:choose>
														<c:when test="${status.first}">
															<img
																src="/images/fugue/icon_shadowless/document_small.png" />
															<a target="_blank"
																href="/portfolio/shareId=${rowData.assessable.shareId}#shareEntry${entry.id}"><c:out
																value="${rowData.assessable.element.entryName}" /></a>
														</c:when>
														<c:otherwise>
			                          &nbsp;
			                        </c:otherwise>
													</c:choose></td>
													<td>${evaluator.person.displayName}</td>
													<td class="rightmost"><c:set var="assessment"
														value="${rowData.assessments[evaluator.person]}" /> <c:choose>
														<c:when test="${assessment eq null}">&nbsp;</c:when>
														<c:otherwise>${assessment.overallScore}<c:if
																test="${not assessment.final}">*</c:if>
														</c:otherwise>
													</c:choose></td>
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
		</c:forEach>
		</div>
		
	</c:if>
</tags:communityPage>

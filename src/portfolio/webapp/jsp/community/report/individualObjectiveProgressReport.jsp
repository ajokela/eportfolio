<!-- $Name:  $ -->
<!-- $Id: individualObjectiveProgressReport.jsp,v 1.7 2010/11/23 14:27:17 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<tags:communityPage community="${community}" pageTitle="Individual Objective Progress Report"
	id="individualObjectiveReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
	<div id="reportCriteria">
		<form action="/individualObjectiveProgressReport.do" method="get">
			<input type="hidden" name="communityId" value="${param.communityId}" />
			<dl>
				<c:if test="${not empty members}">
					<dt>
						Select member
					</dt>
					<dd>
						<select name="euid">
							<c:forEach var="person" items="${members}">
								<c:set var="euid" value="${ospi:encode(ospi:encrypt(person.personId))}" />
								<option value="${euid}" ${euid eq param.euid ? 'selected="selected"' : ''}>
									${person.displayName}
								</option>
							</c:forEach>
						</select>
					</dd>
				</c:if>
				<dt>
					Select objective set
				</dt>
				<dd>
					<select name="objectiveId">
						<c:forEach var="objective" items="${objectiveSets}">
							<option value="${objective.id}" ${objective.id eq param.objectiveId ? 'selected="selected"' : ''}>
								${objective.name}
							</option>
						</c:forEach>
					</select>
				</dd>
				<dt>
					&nbsp;
				</dt>
				
				<dd style="margin-bottom: 5px;">
					<div>
						<div style="display: block; float: left;">
							<strong>From:</strong>
							<input type="text" id="dateFrom" name="dateFromString" value="None" style="width:100px" readonly="readonly"/>&nbsp;
							<img id="trigger1" src="/images/calendar.jpg" style="cursor: pointer;"/>
							<a href="#" onclick="$('dateFrom').value='None';return false;">clear</a>
							<script type="text/javascript">
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
							</script>
					     </div>
					 </div>
					 <div>
					     <div style="display: block; float: left;">
							<strong>To:</strong>
							<input type="text" id="dateTo" name="dateToString" value="None" style="width:100px" readonly="readonly"/>&nbsp;
							<img id="trigger2" src="/images/calendar.jpg" style="cursor: pointer;"/>
							<a href="#" onclick="$('dateTo').value='None';return false;">clear</a>
							<script type="text/javascript">
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
							</script>
					     </div>
					 </div>
				</dd>
					
				
				<dd>
					<input type="submit" name="run" value="Run" />
				</dd>
			</dl>
		</form>
	</div>
	<c:if test="${param.run != null}">
    <hr />
		<div id="export">
			<a id="exportToExcel"
				href="${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}&export"
				style="display: none;">Export to Excel</a>
			<script type="text/javascript">
				document.observe('loader:success', function(event) {
					new YAHOO.widget.Button("exportToExcel");
				});
			</script>
		</div>
		<div id="report">
			<table class="reportTable">
				<tr>
					<th>
						&nbsp;
					</th>
					<th>
						Evaluator
					</th>
					<th class="rightmost">
						Score
					</th>
				</tr>
				<c:forEach var="objective" items="${objectiveSet.flatSubObjectivesList}" varStatus="status">
					<tr class="titleRow ${status.first ? 'first' : ''}">
						<td class="title">
							${objective.name}
						</td>
						<td>
							&nbsp;
						</td>
						<td class="rightmost">
							&nbsp;
						</td>
					</tr>
					<c:forEach var="rowData" items="${portfoliosMap[objective]}">
						<c:choose>
							<c:when test="${empty rowData.evaluators}">
								<tr class="elementRow">
									<td>
										<img src="/images/fugue/icon_shadowless/document.png" />
										<a target="_blank" href="/portfolio/${rowData.assessable.shareId}"><c:out
												value="${rowData.assessable.shareName}" /> </a>
									</td>
									<td>
										&nbsp;
									</td>
									<td class="rightmost">
										&nbsp;
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
									<tr class="elementRow">
										<td>
											<c:if test="${status.first}">
												<img src="/images/fugue/icon_shadowless/document.png" />
												<a target="_blank" href="/portfolio/${rowData.assessable.shareId}"><c:out
														value="${rowData.assessable.shareName}" /> </a>
											</c:if>
										</td>
										<td>
											${evaluator.person.displayName}
										</td>
										<td class="rightmost">
											<c:set var="assessment" value="${rowData.assessments[evaluator.person]}" />
											<c:choose>
												<c:when test="${assessment == null}">&nbsp;</c:when>
												<c:otherwise>${assessment.scores[rowData.scoreIndex]}<c:if test="${not assessment.final}">*</c:if>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:forEach var="rowData" items="${portfolioElementsMap[objective]}">
						<c:choose>
							<c:when test="${empty rowData.evaluators}">
								<tr class="elementRow">
									<td>
										<img src="/images/fugue/icon_shadowless/document_small.png" />
										<a target="_blank"
											href="/portfolio/${rowData.assessable.shareId}#shareEntry${rowData.assessable.id}"><c:out
												value="${rowData.assessable.element.entryName}" /> </a>
									</td>
									<td>
										&nbsp;
									</td>
									<td class="rightmost">
										&nbsp;
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="evaluator" items="${rowData.evaluators}" varStatus="status">
									<tr class="elementRow">
										<td>
											<c:if test="${status.first}">
												<img src="/images/fugue/icon_shadowless/document_small.png" />
												<a target="_blank"
													href="/portfolio/${rowData.assessable.shareId}#shareEntry${rowData.assessable.id}"><c:out
														value="${rowData.assessable.element.entryName}" /> </a>
											</c:if>
										</td>
										<td>
											${evaluator.person.displayName}
										</td>
										<td class="rightmost">
											<c:set var="assessment" value="${rowData.assessments[evaluator.person]}" />
											<c:choose>
												<c:when test="${assessment == null}">&nbsp;</c:when>
												<c:otherwise>${assessment.scores[rowData.scoreIndex]}<c:if test="${not assessment.final}">*</c:if>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
			</table>
		</div>
	</c:if>
</tags:communityPage>
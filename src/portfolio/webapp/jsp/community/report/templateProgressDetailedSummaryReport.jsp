<!-- $Name:  $ -->
<!-- $Id: templateProgressDetailedSummaryReport.jsp,v 1.5 2011/01/06 15:51:30 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<tags:communityPage community="${community}" pageTitle="Template Progress Detailed Summary Report"
  id="templateSummaryReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
  <div id="reportCriteria">
	  <form action="/community/reports/templateProgressDetailedSummaryReport/${community.id}" method="post">
		<div>
		  	<input type="hidden" name="report" value="true" />
		  	<input type="hidden" name="communityId" value="${community.id}" />
	    
	    	<div>
		      	<span>Select template</span>
		      
		        <select name="templateId">
		          <c:forEach var="template" items="${templates}">
		            <option value="${template.id}" ${template.id == templateid ? 'selected="selected"' : ''}>
		              ${template.name}
		            </option>
		          </c:forEach>
		        </select>
		        
			</div>
			
			<div style="padding-top: 10px;">
				<div>
					<div style="display: block; float: left;">
						<strong>From:</strong>
						<input type="text" id="dateFrom" name="dateFromString" value="None" style="width:100px" readonly="readonly" />&nbsp;
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
				 
			</div>
	      
	        <input type="submit" name="run" value="Run" />
	      
      	</div>
      
      	<c:if test="${report eq true}">
      	<div style="clear: both;"></div>
      	<br /><br />
      	<hr />
      	
      	<div id="export">
      		<a id="exportToExcel" href="/community/reports/templateProgressDetailedSummaryReport/export/${community.id}/${templateid}/${dateFrom}/${dateTo}" style="display: none;">Export to Excel</a>
			<script type="text/javascript">
      		 document.observe('loader:success', function(event) { new YAHOO.widget.Button("exportToExcel");});
     		</script>
     	</div>
      	
      	<div>
	      	<div style="display: block; width: 15%; float: left; font-weight: bold; border 1px solid #ddd; padding: 2px; background-color: #ddd;">Name<br />&nbsp;</div>
	      	
	      	<c:if test="${num_prelim_assess > 0}">
	     		<c:forEach var="i" begin="0" step="1" end="${num_prelim_assess-1}"> 
					<div style="display: block; width: 12%; float: left; font-weight: bold; text-align: center; border 1px solid #ddd; padding: 2px; background-color: #ddd;">Assessment ${i+1}<br/>(Preliminary)</div>
				</c:forEach>
				
				<div style="display: block; width: 12%; float: left; font-weight: bold; text-align: center; border 1px solid #ddd; padding: 2px; background-color: #ddd;">Prelim Avg<br />&nbsp;</div>
				
			</c:if>

			<c:if test="${num_final_assess > 0}">
	      		<c:forEach var="i" begin="0" step="1" end="${num_final_assess-1}"> 
					<div style="display: block; width: 12%; float: left; font-weight: bold; text-align: center; border 1px solid #ddd; padding: 2px; background-color: #ddd;">Assessment ${i+1}<br/>(Final)</div>
				</c:forEach>
				
				<div style="display: block; width: 12%; float: left; font-weight: bold; text-align: center; border 1px solid #ddd; padding: 2px; background-color: #ddd;">Final Avg<br />&nbsp;</div>
				
			</c:if>

			<div style="clear: both;"></div>
				
			<c:forEach var="personAssessment" items="${personAssessmentMap}" varStatus="idx">
			
				<c:choose>
					<c:when test="${idx.count % 2 == 0}">
	            		<c:set var="rowColor" scope="page" value="#dddddd"/>
	          		</c:when>
	          		<c:otherwise>
	            		<c:set var="rowColor" scope="page" value="#ffffff"/>
	          		</c:otherwise>
				</c:choose>
			
			
				<div style="display: block; width: 15%; float: left; margin: 1px; border 1px solid ${rowColor}; padding: 2px; background-color: ${rowColor};">${personAssessment.key.displayName}</div>
				
				
				<c:if test="${num_prelim_assess > 0}">
				<c:forEach var="prelim" items="${personAssessment.value.prelimAssessments}">
					<div style="display: block; width: 12%; float: left; text-align: center; margin: 1px; border 1px solid ${rowColor}; padding: 2px; background-color: ${rowColor};">
					
					<c:if test="${prelim != null}">
						${prelim.overallScore} (${prelim.overallQuantifiedScore})
					</c:if>

					<c:if test="${prelim == null}">
						<span style="font-size: 8pt;">Not Assessed</span>
					</c:if>
					
					</div>
					
				</c:forEach>
				
				<div style="display: block; width: 12%; float: left; text-align: center; margin: 1px; border 1px solid ${rowColor}; padding: 2px; background-color: ${rowColor};">
					${personAssessment.value.prelimAverage}
				</div>
				
				</c:if>
				

				<c:if test="${num_final_assess > 0}">
					<c:forEach var="final" items="${personAssessment.value.finalAssessments}">
						<div style="display: block; width: 12%; float: left; text-align: center; margin: 1px; border 1px solid ${rowColor}; padding: 2px; background-color: ${rowColor};">
						
						<c:if test="${final != null}">
							${final.overallScore} (${final.overallQuantifiedScore})
						</c:if>
	
						<c:if test="${final == null}">
							<span style="font-size: 8pt;">Not Assessed</span>
						</c:if>
						
						</div>
					</c:forEach>
					
					<div style="display: block; width: 12%; float: left; text-align: center; margin: 1px; border 1px solid ${rowColor}; padding: 2px; background-color: ${rowColor};">
						${personAssessment.value.finalAverage}
					</div>
					
				</c:if>
				
				<div style="clear: both;"></div>
				
			</c:forEach>      	
      	</div>

      	</c:if>
      	
	  </form>
  </div>
</tags:communityPage>
<!-- $Name:  $ -->
<!-- $Id: detailedObjectReport.jsp,v 1.1 2010/12/21 21:12:19 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<tags:communityPage community="${community}" pageTitle="Detailed Objective Report"
  id="objectiveReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
  <div id="objectiveReport">
	  <form action="/community/reports/detailedObjectReport/${community.id}" method="get">
		<div>
		  	<input type="hidden" name="report" value="true" />
		  	<input type="hidden" name="communityId" value="${community.id}" />
	    
	      	<div id="export">
	      		<a id="exportToExcel" href="/community/reports/detailedObjectiveReport/export/${community.id}" style="display: none;">Export to Excel</a>
				<script type="text/javascript">
	      		 document.observe('loader:success', function(event) { new YAHOO.widget.Button("exportToExcel");});
	     		</script>
	     	</div>

			<c:forEach var="objectiveKey" items="${objectiveAssessmentModelMap}">
	     	
			</c:forEach>
			
		</div>
	  </form>
  </div>
</tags:communityPage>
<!-- $Name:  $ -->
<!-- $Id: communityReports.jsp,v 1.9 2011/03/14 19:37:44 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tags:communityPage community="${community}" pageTitle="Community Reports" id="communityReports" returnTo="editCommunity">
  <ul class="reportList">
    <li><a href="/individualObjectiveProgressReport.do?communityId=${community.id}">Individual Objective Progress Report</a></li>
    <li><a href="/individualTemplateProgressReport.do?communityId=${community.id}">Individual Template Progress Report</a></li>
    <c:if test="${hasSummaryReportAccess}">
	    <li><a href="/objectiveProgressSummaryReport.do?communityId=${community.id}">Objective Progress Summary Report</a></li>
	    <li><a href="/templateProgressSummaryReport.do?communityId=${community.id}">Template Progress Summary Report</a></li>
	    <!--  li><a href="/community/reports/templateProgressDetailedSummaryReport/${community.id}">Template Progress Detailed Summary Report</a></li>
	    <li><a href="/community/reports/detailedObjectiveReport/${community.id}">Detailed Objective Report</a></li -->
	    <li><a href="/community/reports/templateSummaryReport/${community.id}/detailed">Community Summary Report</a></li>
	    <li><a href="/community/reports/rubric/${community.id}">Rubric Report</a></li>
    </c:if>
  </ul>
</tags:communityPage>

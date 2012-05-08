<!-- $Name:  $ -->
<!-- $Id: myCommunityReports.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<tags:communityPage community="${community}" pageTitle="My Community Reports" id="communityReports" returnTo="communityHome">
  <ul class="reportList">
  	<li><a href="/community/reports/templateSummaryReport/${community.id}/detailed">My Community Detailed Report</a></li>
  	<!--  li><a href="/community/reports/templateDetailReport/${community.id}/detailed">My Community Detailed Summary Report</a></li -->
  </ul>
</tags:communityPage>

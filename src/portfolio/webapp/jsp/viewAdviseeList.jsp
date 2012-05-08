<!-- $Name:  $ -->
<!-- $Id: viewAdviseeList.jsp,v 1.6 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:portfolioPage viewMode="view" pageTitle="Adviser View">
	<div class="MainContent">
	<h1>Adviser View</h1>
	<p>Within <strong>Adviser View</strong> you can access reports and
	Portfolios about your assigned academic advisees. This list of advisees
	is managed in the PeopeSoft database by staff in your department. The
	Portfolio about each of your assigned advisees contains a subset of U
	of M Administrative System information only, and is referred to as the
	&quot;U of M Profile.&quot;</p>
	<p>The information contained in the U of M Profile is subject to <a
		href="http://www1.umn.edu/oit/security/OIT__14644_REGION1.html">FERPA</a>,
	the Family Educational Rights and Privacy Act. By accessing student
	information shown below, you agree to comply with FERPA regulations. In
	a case in which a student has requested supression of his/her U of M
	records, a screen will appear when you open the U of M Profile
	describing FERPA suppression. <br>
	</p>
	<table width="80%" border="0" cellspacing="0" cellpadding="0"
		align="left">
		<tr align="left">
			<td colspan="2" width="100%">
			<ul>
				<li>Run <a
					href="javascript:openReportWindow('https://www.umreports.umn.edu/framework/prompt.aspx?reportid=63')">My
				Advisees Report</a> from UM Reports.</li>
				<li>Use <a target="_blank"
					href="https://advisor.d.umn.edu/advisor">Advisor Connect</a> email
				system.</li>
				<li>Log in to <a target="_blank" href="http://plan.umn.edu">Graduation
				Planner</a>.</li>
				<li>Select advisee name (below) to view the U of M Profile.</li>
			</ul>
			</td>
		</tr>
		<tr align="center">
			<td class="ShareListHeader" colspan="2" width="100%"><a
				name="top"></a>
			<p><c:forEach var="mapEntry" items="${adviseeMap}">
				<c:choose>
					<c:when test="${empty mapEntry.value}">${mapEntry.key}&nbsp;&nbsp;</c:when>
					<c:otherwise>
						<a href="#${mapEntry.key}">${mapEntry.key}</a>&nbsp;&nbsp;</c:otherwise>
				</c:choose>
			</c:forEach></p>
			</td>
		</tr>
		<%-- get the individual alpha blocks --%>
		<logic:iterate name="adviseeMap" id="alphaBlock">
			<tr>
				<td width="1%" align="center">&nbsp;
				<h1><a name="<c:out value="${alphaBlock.key}"/>"><c:out
					value="${alphaBlock.key}" /> </a></h1>
				</td>

				<td width="99%">&nbsp;</td>
			</tr>
			<c:if test="${not empty alphaBlock.value}">
				<tr>
					<td width="1%">&nbsp;</td>
					<td>
					<table width="100%" border="0" cellspacing="0" callpadding="0">
						<c:forEach var="advisee" items="${ alphaBlock.value}"
							varStatus="innerStatus">
							<tr>
								<td>
								<p><a href="/umProfile/emplid/${advisee.personId}"> <c:out
									value="${advisee.lastname}" />, <logic:notEmpty name="advisee"
									property="firstName">
									<c:out value="${advisee.firstName}" />
								</logic:notEmpty> <logic:notEmpty name="advisee" property="middlename">
									<c:out value="${advisee.middlename}" />
								</logic:notEmpty> - <c:out value="${advisee.personId}" /> </a></p>
								</td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				<tr>
					<td width="1%">&nbsp;</td>
					<td width="99%">
					<p><span class="textBold"><a href="#top">to top <img
						src="/images/sort_up_blue.gif" width="12" height="12"
						alt="go to top"> </a> </span></p>
					</td>
				</tr>
			</c:if>
		</logic:iterate>
		<tr>
			<td width="1%" align="center">&nbsp;</td>
			<td width="99%">&nbsp;</td>
		</tr>
	</table>
	</div>
</tags:portfolioPage>

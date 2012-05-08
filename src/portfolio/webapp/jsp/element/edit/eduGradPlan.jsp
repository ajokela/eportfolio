<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduGradPlan.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>This is a student graduation plan.</p>
<logic:empty name="dataDef" property="personId">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText"
				colspan="2">
			<h3>No data available.</h3>
			</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="personId">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Plan Title:</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.title}" /></p>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Created at:</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.dateCreated}" /></p>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Last Updated at:</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.gpLastUpdated}" /></p>
			</td>
		</tr>
		<c:if test="${dataDef.preferredPlan}">
			<tr>
				<td align="left" style="white-space: nowrap;" width="1%">
				<h3>Preferred Plan</h3>
				</td>
				<td width="99%" class="BodyStyle">
				<p><c:out value="Yes" /></p>
				</td>
			</tr>
		</c:if>
	</table>
</logic:notEmpty>
<p><a href="#"
	onclick="openReportWindow('graduationPlanner.jsp?entryId=${ospi:encodeUriComponent(dataDef.encodedEntryId)}'); return false;">Go
to this plan</a></p>



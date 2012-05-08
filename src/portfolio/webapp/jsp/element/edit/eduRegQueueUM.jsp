<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduRegQueueUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="Instructions">
		<h2>Assigned date and time to begin registering for classes</h2>
		</td>
	</tr>
</table>
<logic:empty name="dataDef" property="entryName">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText">
			<h3>No data available.</h3>
			</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="entryName">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label">
			<h3>Term</h3>
			</td>
			<td width="100%" class="BodyStyle">
			<p><c:out value="${dataDef.entryName}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label">
			<h3>Start date</h3>
			</td>
			<td width="100%" class="BodyStyle">
			<p><c:out value="${dataDef.apptStartDate}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label">
			<h3>Start time</h3>
			</td>
			<td width="100%" class="BodyStyle">
			<p><c:out value="${dataDef.apptStartTime}" /></p>
			</td>
		</tr>
	</table>
</logic:notEmpty>

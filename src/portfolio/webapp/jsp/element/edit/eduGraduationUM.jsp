<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduGraduationUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>This is your U of M record of application for graduation.</p>
<logic:empty name="dataDef" property="entryName">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText"
				colspan="2">
			<h3>No data available.</h3>
			</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="entryName">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" class="shareHead" colspan="2">
			<h3><c:out value="${dataDef.entryName}" /></h3>
			</td>
		</tr>
		<tr>
			<td align="left" nowrapwidth="1%">
			<h3>Completion term</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.termDesc}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" nowrapwidth="1%">
			<h3>Granted</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.degrConferDt}" /></p>
			</td>
		</tr>
		<logic:iterate id="plans" name="dataDef" property="recordsList"
			indexId="index">
			<tr>
				<td style="white-space: nowrap;" class="Label" width="1%">
				<h3><c:out value="${plans.planTypeDesc}" /></h3>
				</td>
				<td class="BodyStyle" width="99%">
				<p><c:out value="${plans.acadPlanDesc}" /></p>
				</td>
			</tr>
			<logic:notEmpty name="plans" property="subPlanDesc">
				<tr>
					<td style="white-space: nowrap;" class="Label" width="1%">
					<h3>Emphasis</h3>
					</td>
					<td class="BodyStyle" width="99%">
					<p><c:out value="${plans.subPlanDesc}" /></p>
					</td>
				</tr>
			</logic:notEmpty>
		</logic:iterate>
	</table>
</logic:notEmpty>

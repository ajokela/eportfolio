<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: perVisaUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2" class="Instructions">
		<h2>Type of US visa for non-US citizen</h2>
		</td>
	</tr>
</table>
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
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Visa status</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.visaPermitType}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label">&nbsp;</td>
			<td width="100%" class="BodyStyle">
			<p><c:out value="${dataDef.entryName}" /></p>
			</td>
		</tr>
	</table>
</logic:notEmpty>

<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: perDemographicUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2" class="Instructions">
		<p>The U of M record of your demographic information</p>
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
			<h3>Date of birth</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.birthdate}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Citizenship</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.citizenshipStatus}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Gender</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.sex}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Ethnicity</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.ethnicGroup}" /></p>
			</td>
		</tr>
	</table>
</logic:notEmpty>

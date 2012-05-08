<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduCollegeMajorUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2" style="white-space: nowrap;" xclass="Instructions">
		<p>This is your U of M record of enrollment in college(s) and
		academic program(s).</p>
		</td>
	</tr>
</table>
<br />
<logic:empty name="dataDef" property="acadCareer">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText"
				colspan="2">No data available.</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="acadCareer">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="middle">
			<td align="left" colspan="2">
			<p class="BodyStyle"><c:out value="${dataDef.entryName}" /></b3>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="1%" align="left" valign="top">
			<h3>Term</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.termDesc}" /><br>
			<span class="textBG"><c:out value="${dataDef.effdt}" /> (Date
			of change)</span></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Campus</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.institutionDesc}" /></p>
			</td>
		</tr>
		<tr>
			<td width="1%" align="left" style="white-space: nowrap;">
			<h3>Career</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.acadCareerDesc}" />
			</p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Program Action</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.progActionDescr}" /></p>
			</td>
		</tr>
		<tr>
			<td width="1%" align="left" style="white-space: nowrap;">
			<h3>College</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.progDescr}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" nowrapwidth="1%">
			<h3>Major/ Minor</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.planDesc}" /> - <c:out
				value="${dataDef.planTypeDesc}" /></p>
			</td>
		</tr>
		<logic:notEmpty name="dataDef" property="subPlanDesc">
			<tr>
				<td width="1%" align="left" style="white-space: nowrap;">
				<h3>Emphasis</h3>
				</td>
				<td width="99%">
				<p class="BodyStyle"><c:out value="${dataDef.subPlanDesc}" /></p>
				</td>
			</tr>
		</logic:notEmpty>
	</table>
</logic:notEmpty>

<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduUnofficialTranscriptUM.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>This is an unofficial U of M transcript.</p>
<logic:empty name="dataDef" property="emplid">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText"
				colspan="2">
			<h3>No data available.</h3>
			</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="emplid">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Begin Term</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.startTerm}" /></p>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>End Term</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.endTerm}" /></p>
			</td>
		</tr>
	</table>
	<br>
	<br>
&nbsp;<a
		href="javascript:openReportWindow('<%=Configuration.get("portfolio.transcript.url")%>?institution=<c:out value="${dataDef.institution}"/>&studentID=<%= java.net.URLEncoder.encode(((edu.umn.web.portfolio.model.UnofficialTranscriptPS)pageContext.findAttribute("dataDef")).getEncodedEmplid(), "UTF-8") %>')"><img
		src="/images/genReport.gif" width="123" height="21" border="0"
		alt="Generate Unofficial Transcript" tabindex="23"></a>
</logic:notEmpty>

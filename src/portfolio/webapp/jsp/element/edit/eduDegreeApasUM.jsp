<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduDegreeApasUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ page
	import="edu.umn.web.portfolio.client.action.ApasNotAvailableException,edu.umn.web.portfolio.client.action.ViewApasReportAction,edu.umn.web.portfolio.model.DegreeApasPS"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<%-- Conditional for no dataDef --%>
<logic:present name="dataDef">
	<jsp:useBean id="apasHelper" scope="session"
		class="edu.umn.web.portfolio.util.ApasHelperBean">
		<%-- add the needed params --%>
		<jsp:setProperty name="apasHelper" property="emplid"
			value="${dataDef.personId}" />
		<jsp:setProperty name="apasHelper" property="institution"
			value="${dataDef.institution}" />
		<jsp:setProperty name="apasHelper" property="acadCareer"
			value="${dataDef.acadCareer}" />
		<jsp:setProperty name="apasHelper" property="stdntCarNbr"
			value="${dataDef.stdntCarNbr}" />
		<jsp:setProperty name="apasHelper" property="acadProg"
			value="${dataDef.acadProg}" />
		<jsp:setProperty name="apasHelper" property="acadPlan"
			value="${dataDef.acadPlan}" />
		<jsp:setProperty name="apasHelper" property="effSeq"
			value="${dataDef.effseq}" />
		<jsp:setProperty name="apasHelper" property="effDt"
			value="${dataDef.effdt}" />
		<jsp:setProperty name="apasHelper" property="requester"
			value="${empty PersonId ? 'PORTFOLIOUSER' : PersonId }" />
	</jsp:useBean>
	<%
    // now, start the report rolling.
    try {
        apasHelper =  ViewApasReportAction.requestReport( apasHelper );
        request.setAttribute( "apasHelper", apasHelper );
    } catch ( ApasNotAvailableException e ) {
        // do nothing. Will be caught when we try and read the report.
    }
%>

</logic:present>
<p>This is a report from the U of M Academic Progress Audit System
(APAS).</p>
<br />
<logic:empty name="dataDef" property="acadCareer">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText"
				colspan="2">
			<h3>No data available.</h3>
			</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="acadCareer">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" class="shareHead" colspan="2">
			<p><c:out value="${dataDef.entryName}" /><br>
			<hr width="100%" size="1" noshade="noshade">
			</p>
			</td>
		</tr>
		<tr>
			<td width="1%" valign="top" align="left" style="white-space: nowrap;">
			<h3>Term</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.termDesc}" /><br>
			<c:out value="${dataDef.effdt}" />(Date of change)</p>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" style="white-space: nowrap;" class=""
				width="1%">
			<h3>Campus</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.institutionDesc}" /></p>
			</td>
		</tr>
		<tr>
			<td width="1%" align="left" style="white-space: nowrap;">
			<h3>Career</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.acadCareerDesc}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Program Action</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.progActionDescr}" /></p>
			</td>
		</tr>
		<tr>
			<td width="1%" align="left" style="white-space: nowrap;">
			<h3>College</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.progDescr}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%">
			<h3>Major/Minor</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.planDesc}" /> - <c:out
				value="${dataDef.planTypeDesc}" /></p>
			</td>
		</tr>
		<logic:notEmpty name="dataDef" property="subPlanDesc">
			<tr>
				<td width="1%" align="left" style="white-space: nowrap;">
				<h3>Emphasis</h3>
				</td>
				<td width="99%" class="BodyStyle">
				<p><c:out value="${dataDef.subPlanDesc}" /></p>
				</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td colspan="2" class="textBG">
			<p style="margin-left: 5px; margin-top: 10px">View the report for
			<strong><c:out value="${dataDef.planDesc}" /> - <c:out
				value="${dataDef.planTypeDesc}" /> </strong> by clicking <strong>GENERATE
			REPORT</strong>. <span style="margin-left: 5px; margin-top: 10px">(Pop-up)</span></p>
			</td>
		</tr>
	</table>
	<br>
	<br>
&nbsp;<a href="javascript:openReportWindow('viewApasReport.do')"><img
		src="/images/genReport.gif" width="123" height="19" border="0"
		alt="Generate APAS Report" tabindex="23"></a>
	<br />
</logic:notEmpty>

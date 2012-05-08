<!-- $Name:  $ -->
<!-- $Id: AssessScores.jsp,v 1.5 2010/12/21 21:10:45 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<tags:elementField heading="Exam Name">${element.entryName}</tags:elementField>
<tags:elementField heading="Date Taken">${element.dateTaken}</tags:elementField>

<c:if test="${not empty element.section1 or not empty element.score1 or not empty element.percent1}">
	<table width="100%" border="0" cellspacing="1" cellpadding="4" align="center"
		summary="This table is organized for you to input your academic plan.  It is categorized by class number, subject, course number, class title, and credits.">
		<tr valign="top" align="left">
			<th align="center" scope="col">Sections</th>
			<th align="center" scope="col">Scores</th>
			<th align="center" scope="col">Percentiles</th>
			</tr>
		<tr valign="top" align="left">
			<td align="center"><c:out value="${element.section1}" /></td>
			<td align="center"><c:out value="${element.score1}" /></td>
			<td align="center"><c:out value="${element.percent1}" /></td>
		</tr>
		<tr valign="top" align="left">
			<td align="center"><c:out value="${element.section2}" /></td>
			<td align="center"><c:out value="${element.score2}" /></td>
			<td align="center"><c:out value="${element.percent2}" /></td>
		</tr>
		<tr valign="top" align="left">
			<td align="center"><c:out value="${element.section3}" /></td>
			<td align="center"><c:out value="${element.score3}" /></td>
			<td align="center"><c:out value="${element.percent3}" /></td>
		</tr>
		<tr valign="top" align="left">
			<td align="center"><c:out value="${element.section4}" /></td>
			<td align="center"><c:out value="${element.score4}" /></td>
			<td align="center"><c:out value="${element.percent4}" /></td>
		</tr>
		<tr valign="top" align="left">
			<td align="center"><c:out value="${element.section5}" /></td>
			<td align="center"><c:out value="${element.score5}" /></td>
			<td align="center"><c:out value="${element.percent5}" /></td>
		</tr>
		<tr valign="top" align="left">
			<td align="center"><c:out value="${element.section6}" /></td>
			<td align="center"><c:out value="${element.score6}" /></td>
			<td align="center"><c:out value="${element.percent6}" /></td>
		</tr>
	</table>
	<br />
</c:if>

<c:if test="${not empty element.interpretation}">
	<tags:elementField heading="Interpretation"><br />${fn:replace(element.interpretation, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>

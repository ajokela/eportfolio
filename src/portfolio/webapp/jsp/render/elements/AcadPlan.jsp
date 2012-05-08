<!-- $Name:  $ -->
<!-- $Id: AcadPlan.jsp,v 1.9 2010/12/21 21:10:45 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<p>An academic plan is a plan of course work to satisfy degree requirements.</p>
<tags:elementField heading="Term">${element.entryName}</tags:elementField>

<c:if test="${not empty element.class1 or not empty element.subject1 or not empty element.course1 or not empty element.title1 or not empty credits1}">
<table width="90%" border="0" cellspacing="2" cellpadding="4"
	summary="This table is organized for you to input your academic plan.  It is categorized by class number, subject, course number, class title, and credits.">
	<tr valign="top" align="left">
		<th style="font-size: 12pt">Class</th>
		<th style="font-size: 12pt">Subject</th>
		<th style="font-size: 12pt">Course</th>
		<th style="font-size: 12pt">Class Title</th>
		<th style="font-size: 12pt">Credits</th>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class1)}</td>
		<td>${fn:escapeXml(element.subject1)}</td>
		<td>${fn:escapeXml(element.course1)}</td>
		<td>${fn:escapeXml(element.title1)}</td>
		<td>${fn:escapeXml(element.credits1)}</td>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class2)}</td>
		<td>${fn:escapeXml(element.subject2)}</td>
		<td>${fn:escapeXml(element.course2)}</td>
		<td>${fn:escapeXml(element.title2)}</td>
		<td>${fn:escapeXml(element.credits2)}</td>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class3)}</td>
		<td>${fn:escapeXml(element.subject3)}</td>
		<td>${fn:escapeXml(element.course3)}</td>
		<td>${fn:escapeXml(element.title3)}</td>
		<td>${fn:escapeXml(element.credits3)}</td>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class4)}</td>
		<td>${fn:escapeXml(element.subject4)}</td>
		<td>${fn:escapeXml(element.course4)}</td>
		<td>${fn:escapeXml(element.title4)}</td>
		<td>${fn:escapeXml(element.credits4)}</td>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class5)}</td>
		<td>${fn:escapeXml(element.subject5)}</td>
		<td>${fn:escapeXml(element.course5)}</td>
		<td>${fn:escapeXml(element.title5)}</td>
		<td>${fn:escapeXml(element.credits5)}</td>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class6)}</td>
		<td>${fn:escapeXml(element.subject6)}</td>
		<td>${fn:escapeXml(element.course6)}</td>
		<td>${fn:escapeXml(element.title6)}</td>
		<td>${fn:escapeXml(element.credits6)}</td>
	</tr>
	<tr valign="top" align="left">
		<td>${fn:escapeXml(element.class7)}</td>
		<td>${fn:escapeXml(element.subject7)}</td>
		<td>${fn:escapeXml(element.course7)}</td>
		<td>${fn:escapeXml(element.title7)}</td>
		<td>${fn:escapeXml(element.credits7)}</td>
	</tr>
</table>
<br />
</c:if>

<c:if test="${not empty element.comments}">
	<tags:elementField heading="Comments"><br />${fn:replace(element.comments, newLine, "<br/>")}</tags:elementField>
</c:if>
<%-- reworked layout for all BK 03-21-12 --%>

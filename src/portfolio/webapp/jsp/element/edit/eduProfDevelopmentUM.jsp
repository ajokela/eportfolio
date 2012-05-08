<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduProfDevelopmentUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>This page is an official Training Record maintained by the
University of Minnesota <br>
<br>
</p>
<c:choose>
	<c:when test="${empty dataDef.recordsList}">
		<br />
		<br />
		<p>&nbsp;&nbsp;&nbsp;<strong>You have no records on file.</strong></p>
		<br />
		<br />
	</c:when>
	<c:otherwise>

		<table width="100%" cellpadding="2" cellspacing="0">
			<tr>
				<td>
				<p><b>Course Name</b></p>
				</td>
				<td>
				<p><b>Status</b></p>
				</td>
				<td>
				<p><b>Status Date</b></p>
				</td>
			</tr>
			<% int count=0; %>
			<c:forEach items="${dataDef.recordsList}" var="record">
				<% if (count%2 == 0) { %>
				<c:set value="#EEEEEE" var="color" />
				<% }
if (count%2 != 0){ %>
				<c:set value="#CCCCCC" var="color" />
				<% } %>
				<tr bgcolor="<c:out value="${color}"/>">
					<td>
					<p><c:out value="${record.courseName}" /></p>
					</td>
					<td>
					<p><c:out value="${record.status}" /></p>
					</td>
					<td>
					<p><c:out value="${record.statusDate}" /></p>
					</td>
				</tr>
				<% count=(count +1); %>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>

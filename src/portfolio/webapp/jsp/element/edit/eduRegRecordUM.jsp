<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduRegRecordUM.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>This is your U of M record of enrollment, credits, grades and
GPA.</p>
<table border="0" cellspacing="0" cellpadding="6" width="100%">

	<logic:empty name="dataDef" property="entryName">
		<tr valign="top">
			<td align="center" colspan="2">No data available.</td>
		</tr>
	</logic:empty>
	<logic:notEmpty name="dataDef" property="entryName">
		<tr valign="top">
			<td align="left" colspan="2">
			<p><strong>Term&nbsp;&nbsp;<c:out
				value="${dataDef.entryName}" /> </strong></p>
			</td>
		</tr>
		<tr valign="middle">
			<td align="left" colspan="2">
			<table width="99%" border="0" cellspacing="0" cellpadding="2"
				align="center"
				summary="This table is organized by course, course title, credits taken, grade, grading basis and points.">
				<tr valign="top" align="left">
					<th scope="col">
					<p><strong>Course</strong></p>
					</th>
					<th scope="col">
					<p><strong>Course title</strong></p>
					</th>
					<th align="right" scope="col">
					<p><strong>Credits<br>
					taken&nbsp;&nbsp;</strong></p>
					</th>
					<th align="center" scope="col">
					<p><strong>Grade</strong></p>
					</th>
					<th align="right" scope="col">
					<p><strong>Grading<br>
					basis&nbsp;&nbsp;&nbsp;&nbsp;</strong></p>
					</th>
					<th align="right" scope="col">
					<p><strong>Points</strong></p>
					</th>
				</tr>
				<logic:empty name="dataDef" property="recordsList">
					<tr>
						<td align="center" colspan="6">
						<p>No Records Available</p>
						</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="dataDef" property="recordsList">
					<logic:iterate id="course" name="dataDef" property="recordsList"
						indexId="index">
						<c:choose>
							<c:when test="${index % 2 > 0}">
								<c:set value="#EEEEEE" var="color" />
							</c:when>
							<c:otherwise>
								<c:set value="#CCCCCC" var="color" />
							</c:otherwise>
						</c:choose>
						<tr bgcolor="<c:out value="${color}"/>">
							<td style="white-space: nowrap;">
							<p><c:out value="${course.subject}" /> <c:out
								value="${course.catalogNbr}" /> &nbsp;-&nbsp; <c:out
								value="${course.classSection}" /></p>
							</td>
							<td>
							<p><c:out value="${course.entryName}" /></p>
							</td>
							<td align="right">
							<p><c:out value="${course.untTaken}" /> <img
								src="/images/spacer.gif" width="20" height="1" border="0"
								alt="&nbsp;"></p>
							</td>
							<td align="center">
							<p><c:out value="${course.crseGradeOff}" /> &nbsp;</p>
							</td>
							<td align="right">
							<p><c:out value="${course.gradingBasisEnrl}" /> <img
								src="/images/spacer.gif" width="20" height="8" border="0"
								alt="&nbsp;"></p>
							</td>
							<td align="right">
							<p><c:out value="${course.courseGradePoints}" /> &nbsp;</p>
							</td>
						</tr>
					</logic:iterate>
					<tr>
						<td colspan="6">
						<p><img src="/images/spacer.gif" width="1" height="5"
							border="0" alt="&nbsp;"></p>
						</td>
					</tr>
					<!-- term totals -->
					<tr bgcolor="#CCCCCC">
						<td style="white-space: nowrap;" valign="top" scope="row">
						<p>Term totals:</p>
						</td>
						<td valign="top">
						<p>GPA:&nbsp;&nbsp; <c:out value="${dataDef.curGpa}" />
						&nbsp;</p>
						</td>
						<td align="right" valign="top">
						<p><c:out value="${dataDef.untTakenGpa}" /> <img
							src="/images/spacer.gif" width="20" height="1" border="0"
							alt="&nbsp;"></p>
						</td>
						<td valign="top">
						<p>&nbsp;</p>
						</td>
						<td valign="top">
						<p>&nbsp;</p>
						</td>
						<td align="right" valign="top">
						<p><c:out value="${dataDef.gradePoints}" /> &nbsp;</p>
						</td>
					</tr>
					<!-- Cumulative starts here -->
					<tr bgcolor="#CCCCCC">
						<td style="white-space: nowrap;" valign="top" scope="row">
						<p>Cumulative totals:</p>
						</td>
						<td valign="top">
						<p>GPA:&nbsp;&nbsp; <c:out value="${dataDef.cumGpa}" />
						&nbsp;</p>
						</td>
						<td align="right" valign="top">
						<p><c:out value="${dataDef.cumTotalTaken}" /> <img
							src="/images/spacer.gif" width="20" height="1" border="0"
							alt="&nbsp;"></p>
						</td>
						<td align="center" valign="top" colspan="2"
							style="white-space: nowrap;">
						<p>Credits passed:&nbsp; <c:out
							value="${dataDef.cumTotalPassed}" /></p>
						</td>
						<td align="right" valign="top">
						<p><c:out value="${dataDef.totGradePoints}" /> &nbsp;</p>
						</td>
					</tr>
				</logic:notEmpty>
			</table>
			</td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty name="dataDef" property="honorsDesc">
		<tr>
			<td colspan="2"><c:out value="${dataDef.honorsDesc}" /></td>
		</tr>
	</logic:notEmpty>
	<tr>
		<td style="white-space: nowrap;" colspan="2"></td>
	</tr>
</table>

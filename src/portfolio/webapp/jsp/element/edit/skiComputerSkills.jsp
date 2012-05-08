<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: skiComputerSkills.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Name of skills</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>
<br />
<br />
<table border="0" cellspacing="0" cellpadding="3" width="225"
	summary="This table is organized by skill area and rating for your computer skills.">
	<tr valign="top" align="left">
		<th class="text" bgcolor="#CCCCCC" width="99%" scope="col">
		<p>Skill area</p>
		</th>
		<th class="text" bgcolor="#CCCCCC" width="1%"
			style="white-space: nowrap;" scope="col">
		<p>Rating <c:if test="${param.action != 'view'}">
             (Enter percent)
           </c:if></p>
		</th>
	</tr>
	<tr valign="top">
		<td align="left" style="white-space: nowrap;" class="BodyStyle"
			bgcolor="#CCCCCC" width="99%">
		<p>General Computer Operations</p>
		</td>
		<td bgcolor="#EEEEEE" class="BodyStyle" width="1%"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.generalOps}" />
			</c:when>
			<c:otherwise>
				<html:text property="generalOps" size="15" maxlength="15" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top">
		<td align="left" style="white-space: nowrap;" class="BodyStyle"
			bgcolor="#CCCCCC" width="99%">
		<p>Communication and Internet</p>
		</td>
		<td bgcolor="#EEEEEE" class="BodyStyle" width="1%"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.commInt}" />
			</c:when>
			<c:otherwise>
				<html:text property="commInt" size="15" maxlength="15" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top">
		<td align="left" style="white-space: nowrap;" class="BodyStyle"
			bgcolor="#CCCCCC" width="99%">
		<p>Word Processing</p>
		</td>
		<td bgcolor="#EEEEEE" class="BodyStyle" width="1%"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.wordProc}" />
			</c:when>
			<c:otherwise>
				<html:text property="wordProc" size="15" maxlength="15" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top">
		<td align="left" style="white-space: nowrap;" class="BodyStyle"
			bgcolor="#CCCCCC" width="99%">
		<p>Spreadsheet</p>
		</td>
		<td bgcolor="#EEEEEE" class="BodyStyle" width="1%"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.spreadsheet}" />
			</c:when>
			<c:otherwise>
				<html:text property="spreadsheet" size="15" maxlength="15" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top">
		<td align="left" style="white-space: nowrap;" class="BodyStyle"
			bgcolor="#CCCCCC" width="99%">
		<p>Database</p>
		</td>
		<td bgcolor="#EEEEEE" class="BodyStyle" width="1%"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.database}" />
			</c:when>
			<c:otherwise>
				<html:text property="database" size="15" maxlength="15" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top">
		<td align="left" style="white-space: nowrap;" class="BodyStyle"
			bgcolor="#CCCCCC" width="99%">
		<p>Graphics</p>
		</td>
		<td bgcolor="#EEEEEE" class="BodyStyle" style="white-space: nowrap;"
			width="1%"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.graphics}" />
			</c:when>
			<c:otherwise>
				<html:text property="graphics" size="15" maxlength="15" />
			</c:otherwise>
		</c:choose></td>
	</tr>
</table>

<h3><br />
<br />
Applications</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.applications}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="applications" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Languages</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.languages}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="languages" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Experience</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.experience}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="experience" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

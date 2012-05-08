<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carSelfDirSearch.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Record the results of a Self-Directed Search.</p>
<h3>Name of Inventory</h3>
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
<h3>Date Taken</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="dateTaken"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="dateTaken"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>General occupational themes</h3>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#CCCCCC"
	summary="This table is organized by the RIASEC type and score. Using your Self Directed Search results enter the score for each of the types."
	style="font-size: .75em">
	<tr>
		<td class="BodyStyleBold" scope="col" align="right">RIASEC types</td>
		<td style="white-space: nowrap;" class="BodyStyleBold" scope="col"
			align="center">Scores</td>
	</tr>
	<tr>
		<td class="BodyStyle" scope="row" align="right">Realistic</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.realistic}" />
			</c:when>
			<c:otherwise>
				<html:text property="realistic" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr>
		<td class="BodyStyle" scope="row" align="right">Investigative</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.investigative}" />
			</c:when>
			<c:otherwise>
				<html:text property="investigative" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr>
		<td class="BodyStyle" scope="row" align="right">Artistic</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.artistic}" />
			</c:when>
			<c:otherwise>
				<html:text property="artistic" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr>
		<td class="BodyStyle" scope="row" align="right">Social</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.social}" />
			</c:when>
			<c:otherwise>
				<html:text property="social" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr>
		<td class="BodyStyle" scope="row" align="right">Enterprising</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.enterprising}" />
			</c:when>
			<c:otherwise>
				<html:text property="enterprising" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr>
		<td class="BodyStyle" scope="row" align="right">Conventional</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.conventional}" />
			</c:when>
			<c:otherwise>
				<html:text property="conventional" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr>
		<td class="BodyStyleBold" scope="row" align="right">Summary code</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.summaryCode}" />
			</c:when>
			<c:otherwise>
				<html:text property="summaryCode" size="15" maxlength="30" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
</table>

<h3><br />
<br />
Occupational information</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.occuInfo}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="occuInfo" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>
<h3>Interpretation / Reaction</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.reaction}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="reaction" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: skiTransSkills.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
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
<h3>Date taken</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="surveyDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="surveyDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:otherwise>
</c:choose>
<br />
<br />
<table border="0" cellspacing="0" cellpadding="2" bgcolor="#CCCCCC"
	width="70%"
	summary="This table is organized by dmoain, total score and average score for your survey results.">
	<tr valign="bottom">
		<th align="center" scope="col">
		<p>Domain</p>
		</th>
		<th align="center" scope="col">
		<p>Total score</p>
		</th>
		<th align="center" scope="col">
		<p>Average score</p>
		</th>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" bgcolor="#EEEEEE" align="right">
		<p>Self Knowledge</p>
		</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.selfKnowledgeTotal}" />
			</c:when>
			<c:otherwise>
				<html:text property="selfKnowledgeTotal" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.selfKnowledgeAve}" />
			</c:when>
			<c:otherwise>
				<html:text property="selfKnowledgeAve" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" bgcolor="#EEEEEE" align="right">
		<p>Effective Communication</p>
		</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.effectiveCommTotal}" />
			</c:when>
			<c:otherwise>
				<html:text property="effectiveCommTotal" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.effectiveCommAve}" />
			</c:when>
			<c:otherwise>
				<html:text property="effectiveCommAve" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" bgcolor="#EEEEEE" align="right">
		<p>Process Control</p>
		</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.processControlTotal}" />
			</c:when>
			<c:otherwise>
				<html:text property="processControlTotal" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.processControlAve}" />
			</c:when>
			<c:otherwise>
				<html:text property="processControlAve" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" bgcolor="#EEEEEE" align="right">
		<p>Visioning</p>
		</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.visioningTotal}" />
			</c:when>
			<c:otherwise>
				<html:text property="visioningTotal" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.visioningAve}" />
			</c:when>
			<c:otherwise>
				<html:text property="visioningAve" size="6" maxlength="6" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
</table>

<h3><br />
<br />
Interpretation / Reaction</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.interpretation}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="interpretation" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<!-- $Name:  $ -->
<!-- $Id: eduAssessmentScores.jsp,v 1.5 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Exam Name</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="60" />
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

<table width="100%" border="0" cellspacing="1" cellpadding="4"
	align="center"
	summary="This table is orgaized for you to input your academic plan.  It is categorized by class number, subject, course number, class title, and credits.">
	<tr valign="top" align="left">
		<th align="center" scope="col">
		<p>Sections</p>
		</th>
		<th align="center" scope="col">
		<p>Scores</p>
		</th>
		<th align="center" scope="col">
		<p>Percentiles</p>
		</th>
	</tr>
	<tr valign="top" align="left">
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.section1}" />
			</c:when>
			<c:otherwise>
				<html:text property="section1" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.score1}" />
			</c:when>
			<c:otherwise>
				<html:text property="score1" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.percent1}" />
			</c:when>
			<c:otherwise>
				<html:text property="percent1" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.section2}" />
			</c:when>
			<c:otherwise>
				<html:text property="section2" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.score2}" />
			</c:when>
			<c:otherwise>
				<html:text property="score2" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.percent2}" />
			</c:when>
			<c:otherwise>
				<html:text property="percent2" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.section3}" />
			</c:when>
			<c:otherwise>
				<html:text property="section3" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.score3}" />
			</c:when>
			<c:otherwise>
				<html:text property="score3" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.percent3}" />
			</c:when>
			<c:otherwise>
				<html:text property="percent3" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.section4}" />
			</c:when>
			<c:otherwise>
				<html:text property="section4" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.score4}" />
			</c:when>
			<c:otherwise>
				<html:text property="score4" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.percent4}" />
			</c:when>
			<c:otherwise>
				<html:text property="percent4" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.section5}" />
			</c:when>
			<c:otherwise>
				<html:text property="section5" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.score5}" />
			</c:when>
			<c:otherwise>
				<html:text property="score5" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.percent5}" />
			</c:when>
			<c:otherwise>
				<html:text property="percent5" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.section6}" />
			</c:when>
			<c:otherwise>
				<html:text property="section6" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.score6}" />
			</c:when>
			<c:otherwise>
				<html:text property="score6" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.percent6}" />
			</c:when>
			<c:otherwise>
				<html:text property="percent6" size="8" maxlength="25" />
			</c:otherwise>
		</c:choose></td>
	</tr>
</table>

<h3><br />
<br />
Interpretation</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<!--  c:out value="${dataDef.interpretation}" / -->
		<textarea cols="40" rows="5" readonly="readonly">${dataDef.interpretation}</textarea>
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="interpretationCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="interpretation" id="elementInterpretation" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.interpretation}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementInterpretation',
				               counterEl: 'interpretationCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>

	</c:otherwise>
</c:choose>
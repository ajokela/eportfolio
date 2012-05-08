<!-- $Name:  $ -->
<!-- $Id: eduSensoryModalityInv.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Report your results of the Sensory Modality Inventory.</p>
<h3>Name of inventory</h3>
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
	</c:otherwise>
</c:choose>

<br />
<br />
<table width="100%" border="0" cellspacing="1" cellpadding="4"
	align="center"
	summary="This table is orgaized for you to input your academic plan.  It is categorized by class number, subject, course number, class title, and credits.">
	<tr valign="top" align="left">
		<th align="left" scope="col">
		<h3>Results</h3>
		</th>
		<th align="center" scope="col">&nbsp;</th>
	</tr>
	<tr valign="top" align="left">
		<td align="center">
		<p>Auditory</p>
		</td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.auditory}" />
			</c:when>
			<c:otherwise>
				<html:text property="auditory" size="5" maxlength="10" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center">
		<p>Visual</p>
		</td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.visual}" />
			</c:when>
			<c:otherwise>
				<html:text property="visual" size="5" maxlength="10" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td align="center">
		<p>Kinesthetic</p>
		</td>
		<td align="center"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.kinesthetic}" />
			</c:when>
			<c:otherwise>
				<html:text property="kinesthetic" size="5" maxlength="10" />
			</c:otherwise>
		</c:choose></td>
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
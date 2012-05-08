<!-- $Name:  $ -->
<!-- $Id: carExploreInventory.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Record the results of a Career Exploration Inventory.</p>
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
<table border="01" cellspacing="0" cellpadding="3" width="90%"
	align="center"
	summary="This table is organized by interest clusters, occupations, leisure and learning activities.  Using your inventory enter your first, second and third choice for each."
	style="font-size: .75em;">
	<tr valign="top">
		<th class="BodyStyleBold" scope="col" align="right" bgcolor="#DDDDDD">Interest
		clusters</th>
		<th class="BodyStyleBold" scope="col" align="right" bgcolor="#DDDDDD">Occupations</th>
		<th class="BodyStyleBold" scope="col" align="right" bgcolor="#DDDDDD">Leisure
		activities</th>
		<th class="BodyStyleBold" scope="col" align="right" bgcolor="#DDDDDD">Learning
		activities</th>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" scope="row" bgcolor="#DDDDDD" align="right">First
		choice&nbsp;&nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.firstOccupation}" />
			</c:when>
			<c:otherwise>
				<html:text property="firstOccupation" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.firstLeisure}" />
			</c:when>
			<c:otherwise>
				<html:text property="firstLeisure" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.firstLearning}" />
			</c:when>
			<c:otherwise>
				<html:text property="firstLearning" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" scope="row" bgcolor="#DDDDDD" align="right">Second
		choice&nbsp;&nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.secondOccupation}" />
			</c:when>
			<c:otherwise>
				<html:text property="secondOccupation" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.secondLeisure}" />
			</c:when>
			<c:otherwise>
				<html:text property="secondLeisure" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.secondLearning}" />
			</c:when>
			<c:otherwise>
				<html:text property="secondLearning" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
	</tr>
	<tr valign="top">
		<td class="BodyStyle" scope="row" bgcolor="#DDDDDD" align="right">Third
		choice&nbsp;&nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.thirdOccupation}" />
			</c:when>
			<c:otherwise>
				<html:text property="thirdOccupation" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.thirdLeisure}" />
			</c:when>
			<c:otherwise>
				<html:text property="thirdLeisure" size="20" maxlength="50" />
			</c:otherwise>
		</c:choose> &nbsp;</td>
		<td bgcolor="#EEEEEE" class="BodyStyle"><c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.thirdLearning}" />
			</c:when>
			<c:otherwise>
				<html:text property="thirdLearning" size="20" maxlength="50" />
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
		<c:out value="${dataDef.reaction}" />
	</c:when>
	<c:otherwise>
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="reactionCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="reaction" id="elementReaction" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.reaction}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementReaction',
				               counterEl: 'reactionCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	</c:otherwise>
</c:choose>
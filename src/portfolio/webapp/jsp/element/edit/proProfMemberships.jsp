<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: proProfMemberships.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<h3>Organization</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="60" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Description</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.description}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="description" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Role(s)</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.role}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="role" cols="40" rows="5"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

<h3>Member since</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="memberSince"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="memberSince"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:otherwise>
</c:choose>

<!-- Added Member until BK 3/5/12 -->
<h3><br />
Member until</h3>
<!-- How this works: if present member box is checked, user will not see the selection boxes for entering a member until date.
For some reason, only inline javascript would work, thus the onclick in the checkbox input. The "if" in the div is to show/hide the
div based on the value coming in, because the box has not yet been clicked. -->
<div id="endDate"
	<c:if test="${dataDef.presentMember == 1 }">
		style="display:none;"
	</c:if>
>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="memberUntil"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="memberUntil"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:otherwise>
</c:choose>
</div>

<!-- Added Present Member checkbox BK 3/5/12 -->
<p><c:choose> 
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.presentMember == 1}">
      		Present Member
      	</c:if>
	</c:when>
	<c:otherwise>
 		<input type="checkbox" name="presentMember" id="presentMember" value="1" <c:if test="${dataDef.presentMember == 1}"> checked="checked"</c:if> onclick="$('endDate').toggle(this.checked);"/>Present Member
   		<input type="hidden" name="presentMember" value="" /> <!-- This is needed to send null value since unchecked box will not return a value -->
   </c:otherwise>
</c:choose></p>

<h3>Additional comments</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.comments}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="comments" cols="40" rows="8"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

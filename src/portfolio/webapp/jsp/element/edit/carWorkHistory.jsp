<!-- $Name:  $ -->
<!-- $Id: carWorkHistory.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Record your record of employment.</p>
<br />
<h3>Position Title</h3>
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
<h3>Organization</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.institution}" />
	</c:when>
	<c:otherwise>
		<html:text property="institution" size="25" maxlength="60" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Supervisor</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.supervisor}" />
	</c:when>
	<c:otherwise>
		<html:text property="supervisor" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>
<br />
<br />

<h2>Address</h2>
<br />
<h3>Address line 1</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.address1}" />
	</c:when>
	<c:otherwise>
		<html:text property="address1" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Address line 2</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.address2}" />
	</c:when>
	<c:otherwise>
		<html:text property="address2" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>City</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.city}" />
	</c:when>
	<c:otherwise>
		<html:text property="city" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>State</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.state}" />
	</c:when>
	<c:otherwise>
		<html:text property="state" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Zip</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.zip}" />
	</c:when>
	<c:otherwise>
		<html:text property="zip" size="10" maxlength="10" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Country</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.country}" />
	</c:when>
	<c:otherwise>
		<html:text property="country" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Phone</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.telephone}" />
	</c:when>
	<c:otherwise>
		<html:text property="telephone" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Fax</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.fax}" />
	</c:when>
	<c:otherwise>
		<html:text property="fax" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h2>Dates of Employment</h2>
<br />
<h3>Start date</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="startDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="startDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>End date</h3>
<!-- Added div BK 3/15/12 -->
<div id="endDate"
	<c:if test="${dataDef.presentlyEmployed == 1 }">
		style="display:none;"
	</c:if>
>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="endDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="endDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>
</div>

<p><c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.presentlyEmployed == 1}">
      Presently Employed
      </c:if>
	</c:when>
	<c:otherwise>
<!-- Changes to checkbox BK 3/15/12 -->
		<input type="checkbox" name="presentlyEmployed" id="presentlyEmployed" value="1" <c:if test="${dataDef.presentlyEmployed == 1}"> checked="checked"</c:if> onclick="$('endDate').toggle(this.checked);"/>Present Member
   		<input type="hidden" name="presentlyEmployed" value="" /> <!-- This is needed to send null value since unchecked box will not return a value -->
   </c:otherwise>
</c:choose></p>

<h3>Description</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.description}" />
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="descriptionCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="description" id="elementDescription" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.description}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementDescription',
				               counterEl: 'descriptionCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>

	</c:otherwise>
</c:choose>
<h3><br />
<br />
Accomplishments</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.accomplishments}" />
	</c:when>
	<c:otherwise>
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="commentsCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="accomplishments" id="elementComments" cols="56" rows="5" autocomplete="off" class="plainText"></textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementComments',
				               counterEl: 'commentsCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	</c:otherwise>
</c:choose>
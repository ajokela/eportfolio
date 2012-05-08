<!-- $Name:  $ -->
<!-- $Id: perAdditionalPhoto.jsp,v 1.5 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Type of Photo</h3>
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
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="dateTaken"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Additional Information</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 50 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.additionalInformation}" />
	</c:when>
	<c:otherwise>
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="additionalInformationCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="additionalInformation" id="elementAdditionalInformation" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.additionalInformation}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementAdditionalInformation',
				               counterEl: 'additionalInformationCounter',
				               max: 256
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose>
<!-- $Name:  $ -->
<!-- $Id: eduCoCurricular.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Co-curricular activities are learning activity that takes place
outside the classroom.</p>
<h3>Activity name</h3>
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
<h3>From</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="fromDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="fromDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>To</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="toDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="toDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
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
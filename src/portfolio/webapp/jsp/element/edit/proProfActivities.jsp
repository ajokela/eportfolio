<!-- $Name:  $ -->
<!-- $Id: proProfActivities.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Type of activity</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="50" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Name of practice</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.name}" />
	</c:when>
	<c:otherwise>
		<html:text property="name" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>From</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="fromDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="fromDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>To</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="toDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="toDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.DAY%>" />
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Location</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.location}" />
	</c:when>
	<c:otherwise>
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="locationCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="location" id="elementLocation" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.location}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementLocation',
				               counterEl: 'locationCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
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
						<span id="commentsCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="description" id="elementComments" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.description}</textarea>
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
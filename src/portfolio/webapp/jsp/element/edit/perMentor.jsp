<!-- $Name:  $ -->
<!-- $Id: perMentor.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>A mentor is an individual who has provided guidance in an
educational or professional environment.</p>
<h3>First Name</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.firstName}" />
	</c:when>
	<c:otherwise>
		<html:text property="firstName" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>
<br />
<br />
<h3>Last Name</h3>
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
<h3>Title</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.title}" />
	</c:when>
	<c:otherwise>
		<html:text property="title" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>
<br />
<br />
<h3>Organization</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.organization}" />
	</c:when>
	<c:otherwise>
		<html:text property="organization" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<h2>Address</h2>
<br />
<br />
<h3>Address line 1</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.street1}" />
	</c:when>
	<c:otherwise>
		<html:text property="street1" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>
<br />
<br />
<h3>Address line 2</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.street2}" />
	</c:when>
	<c:otherwise>
		<html:text property="street2" size="25" maxlength="50" />
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
<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carProfSkills.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<p>A professional skill is a developed professional skill.</p>
<h3>Name of skill</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="50" maxlength="50" />
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
		<html:textarea property="description" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>
<h3>Experience</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.experience}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="experience" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>
<h3>Reflection</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.reflection}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="reflection" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

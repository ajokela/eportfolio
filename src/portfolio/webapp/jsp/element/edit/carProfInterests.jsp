<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carProfInterests.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<p>A professional interest is an area of interest or specialty
within your profession.</p>
<br />
<h3>Name of interest</h3>
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
Relevant activities</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.interestActivity}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="interestActivity" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>
<h3>Skills</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.interestSkills}" />
	</c:when>
	<c:otherwise>
		<html:textarea property="interestSkills" cols="40" rows="10"
			onkeyup="limitChar(this,4000)" />
	</c:otherwise>
</c:choose></p>

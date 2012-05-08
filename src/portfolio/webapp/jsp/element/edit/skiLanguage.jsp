<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: skiLanguage.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Language</h3>
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
<h3>Oral fluency level</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.levelorCode}" />
	</c:when>
	<c:otherwise>
		<html:select property="levelorCode" size="1">
			<html:option value="">- Select Level -</html:option>
			<html:option value="NO">Novice</html:option>
			<html:option value="IN">Intermediate</html:option>
			<html:option value="AD">Advanced</html:option>
			<html:option value="SU">Superior</html:option>
		</html:select>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Written fluency level</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.levelorCode}" />
	</c:when>
	<c:otherwise>
		<html:select property="levelwrCode" size="1">
			<html:option value="">- Select Level -</html:option>
			<html:option value="NO">Novice</html:option>
			<html:option value="IN">Intermediate</html:option>
			<html:option value="AD">Advanced</html:option>
			<html:option value="SU">Superior</html:option>
		</html:select>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Experience</h3>
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

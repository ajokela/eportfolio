<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carReference.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<p>A reference is an individual who will write a letter of
recommendation or provide a reference.</p>
<h3>Name</h3>
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
<h3>Position</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.position}" />
	</c:when>
	<c:otherwise>
		<html:text property="position" size="25" maxlength="50" />
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
		<html:text property="city" size="25" maxlength="25" />
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
		<c:out value="${dataDef.phone}" />
	</c:when>
	<c:otherwise>
		<html:text property="phone" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>E-mail address</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.email}" />
	</c:when>
	<c:otherwise>
		<html:text property="email" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Contact preferences</h3>
<p><c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.mailPref == 1}">
      Mail &nbsp;&nbsp;&nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
<!-- Fixed checkboxes BK 3/15/12 -->
	 	<input type="checkbox" name="mailPref" id="mailPref" value="1" <c:if test="${dataDef.mailPref == 1}"> checked="checked"</c:if> /> Mail &nbsp;
   		<input type="hidden" name="mailPref" value="" /> <!-- This is needed to send null value since unchecked box will not return a value -->
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.phonePref == 1}">
      Phone &nbsp;&nbsp;&nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="phonePref" id="phonePref" value="1" <c:if test="${dataDef.phonePref == 1}"> checked="checked"</c:if> /> Phone &nbsp;
   		<input type="hidden" name="phonePref" value="" /> <!-- This is needed to send null value since unchecked box will not return a value -->
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.emailPref == 1}">
      Email
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="emailPref" id="emailPref" value="1" <c:if test="${dataDef.emailPref == 1}"> checked="checked"</c:if> /> Email
   		<input type="hidden" name="emailPref" value="" /> <!-- This is needed to send null value since unchecked box will not return a value -->
   </c:otherwise>
</c:choose></p>

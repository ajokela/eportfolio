<!-- $Name:  $ -->
<!-- $Id: ProfMemberships.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>
<tags:elementField heading="Role(s)" wysiwyg="true">${element.role}</tags:elementField>
<tags:elementField heading="Member since">${element.memberSince}</tags:elementField>
<!-- Added Member Until and Present Member BK 3/5/12 -->
<c:if test="${not empty element.memberUntil or not empty element.presentMember}">
<tags:elementField heading="Member until">
	<c:if test="${empty element.presentMember}">${element.memberUntil}</c:if>
	<c:if test="${not empty element.presentMember}">(present member)</c:if>
</tags:elementField>
</c:if>
<tags:elementField heading="Additional comments" wysiwyg="true">${element.comments}</tags:elementField>

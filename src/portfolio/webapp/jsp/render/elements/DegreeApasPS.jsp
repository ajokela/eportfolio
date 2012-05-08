<!-- $Name:  $ -->
<!-- $Id: DegreeApasPS.jsp,v 1.5 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField>
	<a target="_blank"
		href="/viewApasReport.do?personId=${element.encryptedPersonId}&entryId=${element.entryId}">${element.entryName}</a>
</tags:elementField>

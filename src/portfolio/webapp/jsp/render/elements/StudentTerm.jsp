<!-- $Name:  $ -->
<!-- $Id: StudentTerm.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<tags:elementField>
	<a target="_blank"
		href="/viewStudentTerm.do?personId=${ospi:encode(element.encryptedPersonId)}&entryId=${element.entryId}">${element.entryName}</a>
</tags:elementField>

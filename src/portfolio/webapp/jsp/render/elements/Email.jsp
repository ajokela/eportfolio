<!-- $Name:  $ -->
<!-- $Id: Email.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<div>
	${element.elementName}: <a href="mailto:${element.email}">${element.email}</a> (${element.entryName})
</div>

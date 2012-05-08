<!-- $Name:  $ -->
<!-- $Id: Phone.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<div>
	${element.elementName}: <a href="tel:${element.phone}">${element.phone}</a> (${element.entryName})
</div>


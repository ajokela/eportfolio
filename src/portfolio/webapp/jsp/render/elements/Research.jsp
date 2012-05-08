<!-- $Name:  $ -->
<!-- $Id: Research.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Organization">${element.organization}</tags:elementField>
<tags:elementField heading="Supervisor">${element.supervisor}</tags:elementField>
<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>

<!-- $Name:  $ -->
<!-- $Id: Travel.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="From">${element.fromDate}</tags:elementField>
<tags:elementField heading="To">${element.toDate}</tags:elementField>
<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>
<tags:elementField heading="Reflection" wysiwyg="true">${element.reflection}</tags:elementField>

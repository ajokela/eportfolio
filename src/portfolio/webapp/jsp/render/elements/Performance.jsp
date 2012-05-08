<!-- $Name:  $ -->
<!-- $Id: Performance.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Type of Performance">${element.type}</tags:elementField>
<tags:elementField heading="Opening Date">${element.openingDate}</tags:elementField>
<tags:elementField heading="Location">${element.location}</tags:elementField>
<tags:elementField heading="Juried">${element.juried}</tags:elementField>
<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>

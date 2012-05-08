<!-- $Name:  $ -->
<!-- $Id: Exhibition.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Type of Exhibition">${element.type}</tags:elementField>
<tags:elementField heading="From">${element.fromDate}</tags:elementField>
<tags:elementField heading="To">${element.toDate}</tags:elementField>
<tags:elementField heading="Location">${element.location}</tags:elementField>
<tags:elementField heading="Juried">${element.juried}</tags:elementField>
<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>

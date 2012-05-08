<!-- $Name:  $ -->
<!-- $Id: ProfDevelopment.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Sponsor">${element.sponsor}</tags:elementField>
<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>
<tags:elementField heading="Certificate Earned">${element.certificate}</tags:elementField>
<tags:elementField heading="From Date">${element.fromDate}</tags:elementField>
<tags:elementField heading="To Date">${element.toDate}</tags:elementField>

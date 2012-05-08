<!-- $Name:  $ -->
<!-- $Id: Teaching.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="From">${element.fromDate}</tags:elementField>
<tags:elementField heading="To">${element.toDate}</tags:elementField>
<tags:elementField heading="Course/Subjects" wysiwyg="true">${element.courses}</tags:elementField>
<tags:elementField heading="Organization">${element.organization}</tags:elementField>
<tags:elementField heading="Address">${element.street1}</tags:elementField>
<tags:elementField heading="Address 2">${element.street2}</tags:elementField>
<tags:elementField heading="City">${element.city}</tags:elementField>
<tags:elementField heading="State">${element.state}</tags:elementField>
<tags:elementField heading="Zip">${element.zip}</tags:elementField>
<tags:elementField heading="Country">${element.country}</tags:elementField>
<tags:elementField heading="Additional Comments" wysiwyg="true">${element.comments}</tags:elementField>

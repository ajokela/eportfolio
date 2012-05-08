<!-- $Name:  $ -->
<!-- $Id: Service.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:elementField heading="Organization">${element.organization}</tags:elementField>
<tags:elementField heading="Supervisor">${element.supervisor}</tags:elementField>
<tags:elementField heading="Address">${element.street1}</tags:elementField>
<tags:elementField heading="Address 2">${element.street2}</tags:elementField>
<tags:elementField heading="City">${element.city}</tags:elementField>
<tags:elementField heading="State">${element.state}</tags:elementField>
<tags:elementField heading="Zip">${element.zip}</tags:elementField>
<tags:elementField heading="Country">${element.country}</tags:elementField>
<tags:elementField heading="Phone">${element.phone}</tags:elementField>
<tags:elementField heading="Email">${element.email}</tags:elementField>
<tags:elementField heading="Fax">${element.fax}</tags:elementField>
<tags:elementField heading="Start Date">${element.startDate}</tags:elementField>
<tags:elementField heading="End Date">${element.endDate}</tags:elementField>
<tags:elementField heading="Hours">${element.hours}</tags:elementField>
<tags:elementField heading="Description" wysiwyg="true">${element.description}</tags:elementField>
<tags:elementField heading="Comments" wysiwyg="true">${element.comments}</tags:elementField>

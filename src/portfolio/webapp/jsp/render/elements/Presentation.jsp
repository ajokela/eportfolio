<!-- $Name:  $ -->
<!-- $Id: Presentation.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Description">${element.description}</tags:elementField>
<tags:elementField heading="Presenters">${element.presenters}</tags:elementField>
<tags:elementField heading="Name of Event">${element.eventName}</tags:elementField>
<tags:elementField heading="Location">${element.location}</tags:elementField>
<tags:elementField heading="Presentation Date">${element.eventDate}</tags:elementField>

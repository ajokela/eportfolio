<!-- $Name:  $ -->
<!-- $Id: RegQueuePS.jsp,v 1.4 2011/03/24 19:03:26 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField>${element.entryName}</tags:elementField>
<div>
	<div>Acad Year: ${element.acadCareer}</div>
	<div>Institution: ${element.institution}</div>
	<div>Date/Time: ${element.apptStartDate} - ${element.apptStartTime}</div>
</div>
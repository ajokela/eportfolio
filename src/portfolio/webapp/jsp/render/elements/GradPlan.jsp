<!-- $Name:  $ -->
<!-- $Id: GradPlan.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<tags:elementField heading="">
	<a target="_blank"
		href="/graduationPlanner.jsp?entryId=${ospi:encode(element.encodedEntryId)}">${element.title}</a>
</tags:elementField>

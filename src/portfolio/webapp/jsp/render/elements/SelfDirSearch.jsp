<!-- $Name:  $ -->
<!-- $Id: SelfDirSearch.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Date Taken">${element.dateTaken}</tags:elementField>
<tags:elementField heading="RIASEC type scores">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="Realistic">${element.realistic}</tags:innerElementField>
		<tags:innerElementField heading="Investigative">${element.investigative}</tags:innerElementField>
		<tags:innerElementField heading="Artistic">${element.artistic}</tags:innerElementField>
		<tags:innerElementField heading="Social">${element.social}</tags:innerElementField>
		<tags:innerElementField heading="Enterprising">${element.enterprising}</tags:innerElementField>
		<tags:innerElementField heading="Conventional">${element.conventional}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Summary Code">${element.summaryCode}</tags:elementField>
<tags:elementField heading="Occupational Information" wysiwyg="true">${element.occuInfo}</tags:elementField>
<tags:elementField heading="Interpretation / Reaction" wysiwyg="true">${element.reaction}</tags:elementField>

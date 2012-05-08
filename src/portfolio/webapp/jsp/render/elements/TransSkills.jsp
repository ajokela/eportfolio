<!-- $Name:  $ -->
<!-- $Id: TransSkills.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Date Taken">${element.surveyDate}</tags:elementField>
<tags:elementField heading="Self Knowledge Score">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="Total">${element.selfKnowledgeTotal}</tags:innerElementField>
		<tags:innerElementField heading="Average">${element.selfKnowledgeAve}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Effective Communication Score">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="Total">${element.effectiveCommTotal}</tags:innerElementField>
		<tags:innerElementField heading="Average">${element.effectiveCommAve}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Process Control Score">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="Total">${element.processControlTotal}</tags:innerElementField>
		<tags:innerElementField heading="Average">${element.processControlAve}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Visioning Score">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="Total">${element.visioningTotal}</tags:innerElementField>
		<tags:innerElementField heading="Average">${element.visioningAve}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Interpretation" wysiwyg="true">${element.interpretation}</tags:elementField>

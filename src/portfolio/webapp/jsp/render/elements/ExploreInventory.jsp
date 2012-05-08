<!-- $Name:  $ -->
<!-- $Id: ExploreInventory.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%-- IMPORTANT: Do not put with the line above. Need to be separated to create newline char BK 03-20-12 --%>

<tags:elementField heading="Date Taken">${element.dateTaken}</tags:elementField>
<c:if test="${not empty element.firstOccupation or not empty element.secondOccupation or not empty element.thirdOccupation}">
	<tags:elementField heading="Occupations">
		<tags:innerElementFieldList>
			<tags:innerElementField heading="First Choice">${element.firstOccupation}</tags:innerElementField>
			<tags:innerElementField heading="Second Choice">${element.secondOccupation}</tags:innerElementField>
			<tags:innerElementField heading="Third Choice">${element.thirdOccupation}</tags:innerElementField>
		</tags:innerElementFieldList>
	</tags:elementField>
</c:if>
<c:if test="${not empty element.firstLeisure or not empty element.secondLeisure or not empty element.thirdLeisure}">
	<tags:elementField heading="Leisure Activities">
		<tags:innerElementFieldList>
			<tags:innerElementField heading="First Choice">${element.firstLeisure}</tags:innerElementField>
			<tags:innerElementField heading="Second Choice">${element.secondLeisure}</tags:innerElementField>
			<tags:innerElementField heading="Third Choice">${element.thirdLeisure}</tags:innerElementField>
		</tags:innerElementFieldList>
	</tags:elementField>
</c:if>
<c:if test="${not empty element.firstLearning or not empty element.secondLearning or not empty element.thirdLearning}">
	<tags:elementField heading="Learning Activities">
		<tags:innerElementFieldList>
			<tags:innerElementField heading="First Choice">${element.firstLearning}</tags:innerElementField>
			<tags:innerElementField heading="Second Choice">${element.secondLearning}</tags:innerElementField>
			<tags:innerElementField heading="Third Choice">${element.thirdLearning}</tags:innerElementField>
		</tags:innerElementFieldList>
	</tags:elementField>
</c:if>
<div style="clear: both;"></div>
<c:if test="${not empty element.reaction}">
	<tags:elementField heading="Interpretation/Reaction"><br />${fn:replace(element.reaction, newLine, "<br/>")}</tags:elementField>	
</c:if>
<%-- added not empty test for all BK 03-21-12 --%>

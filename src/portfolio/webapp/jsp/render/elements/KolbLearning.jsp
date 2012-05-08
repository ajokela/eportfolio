<!-- $Name:  $ -->
<!-- $Id: KolbLearning.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="newLine" value="
"/> <%--  IMPORTANT: Do not put with the line above. Need to be separated to create newline char--%>

<c:if test="${not empty element.dateTaken}">
<tags:elementField heading="Date Taken">${element.dateTaken}</tags:elementField>
</c:if>
<c:if test="${not empty element.concreteExperience or not empty element.reflectiveObservation or not empty element.abstractConceptualization or not empty element.activeExperimentation}">
	<tags:elementField heading="Learning Cycle Stages Scores">
		<tags:innerElementFieldList>
			<c:if test="${not empty element.concreteExperience}">
				<tags:innerElementField heading="Concrete Experience (CE)">${element.concreteExperience}</tags:innerElementField>
			</c:if>
			<c:if test="${not empty element.reflectiveObservation}">
				<tags:innerElementField heading="Reflective Observation (RO)">${element.reflectiveObservation}</tags:innerElementField>
			</c:if>
			<c:if test="${not empty element.abstractConceptualization}">
				<tags:innerElementField heading="Abstract Conceptualization (AC)">${element.abstractConceptualization}</tags:innerElementField>
			</c:if>
			<c:if test="${not empty element.activeExperimentation}">
				<tags:innerElementField heading="Active Experimentation (AE)">${element.activeExperimentation}</tags:innerElementField>
			</c:if>
		</tags:innerElementFieldList>
	</tags:elementField>
</c:if>
<c:if test="${not empty element.learningStyleType}">
	<tags:elementField heading="Learning Style Type">${element.learningStyleType}</tags:elementField>
</c:if>
<c:if test="${not empty element.interpretation}">
	<tags:elementField heading="Interpretation/Reaction"><br />${fn:replace(element.interpretation, newLine, "<br/>")}</tags:elementField>
</c:if>
<%--  reworked layout for all BK 03-21-12 --%>
<!-- $Name:  $ -->
<!-- $Id: assessmentHome.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="Portfolio : Assessment Designer">
	<!-- Displays an individual assessment model. -->
	<!-- set model name -->
	<h1><c:out value="${assessmentModel.name}" /></h1>
	<p><c:out value="${assessmentModel.description}" /></p>
	<!-- Show assessment details based upon the assessment type -->
	<c:choose>
		<c:when test="${assessmentModel.Type = 'basic'}">
    Display Basic Assessment
  </c:when>
		<c:when test="${assessmentModel.Type = 'outcome'}">
    Display Outcome Assessment
  </c:when>
		<c:when test="${assessmentModel.Type = 'holisticRubric'}">
    Display Holistic Rubric
  </c:when>
		<c:when test="${assessmentModel.Type = 'analyticRubric'}">
    Display Analytic Rubric
  </c:when>
		<c:otherwise>Invalid Assessment Type Message</c:otherwise>
	</c:choose>
Display Feedback Section

    select scoring model
  else if assessmentModel.type = outcomeAssessment
    select scoring model
    while not finished
      add/update objective
  else if assessmentModel.type = holisticRubric
    select scoring model
    add/update objective
    for each score
      add/update descriptor
      add/update sample
      add/update guidance
  else if assessmentModel.type = analyticRubric
    select scoring model
    while not finished
      add/update objective
      for each score
        add/update descriptor
        add/update sample
        add/update guidance
  -->
</tags:portfolioPage>

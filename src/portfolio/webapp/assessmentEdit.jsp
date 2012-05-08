<!-- $Name:  $ -->
<!-- $Id: assessmentEdit.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="Portfolio : ${assessmentModel.name} : Edit Assessment Model">
	<!-- Allows editing of an assessment model if no assessments have been made against it. -->
	<h1>Edit Assessment Model : ${assessmentModel.name}</h1>
	<form><input type="hidden" name="communityId"
		value="${assessmentModel.id}">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="name"
				value="${assessmentModel.name}" /></td>
		</tr>
		<tr>
			<td>Description</td>
			<td><textarea name="description">${assessmentModel.description}</textarea></td>
		</tr>
	</table>
	<br />
	<input type="submit" name="save" value="Save"></form>

	<!--  if assessmentModel.type = basicAssessment
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
  save assessment model
  cancel assessment model
  -->

</tags:portfolioPage>

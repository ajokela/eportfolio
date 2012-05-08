<!-- $Name:  $ -->
<!-- $Id: templateBuilderAssessmentModels.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Template Builder" id="assignAssess">
	<jsp:include page="templateBuilderSteps.jsp">
		<jsp:param name="step" value="assessment" />
	</jsp:include>
	<h2>Assessment: Assign Assessment Models</h2>

	<form action="templateBuilderAssessmentModels.do" method="post">
	<input type="hidden" name="method" value="save" /> <input
		type="hidden" name="templateId" value="${template.templateId}">
	<h3>Portfolio-Level Assessment<span>&nbsp;&nbsp;This is the
	global assessment model for the entire portfolio.</span></h3>
	<table class="assessTable" cellspacing="0">
		<tr>
			<th>Assessment model</th>
			<th class="rightmost">Anonymous assessment?</th>
		</tr>
		<tr>
			<td><select name="pAssessmentModel">
				<option value="">None</option>
				<c:if test="${not empty basicAssessmentModels}">
					<optgroup label="Basic">
						<c:forEach var="model" items="${basicAssessmentModels}">
							<c:set var="selected"
								value="${not empty template.assessmentModelAssignment 
	                      and template.assessmentModelAssignment.assessmentModel.id eq model.id}" />
							<option value="${model.id}" ${selected ? 'selected="selected"' : ''}>${model.name}</option>
						</c:forEach>
					</optgroup>
				</c:if>
				<c:if test="${not empty outcomeAssessmentModels}">
					<optgroup label="Outcome-based">
						<c:forEach var="model" items="${outcomeAssessmentModels}">
							<c:set var="selected"
								value="${not empty template.assessmentModelAssignment 
                        and template.assessmentModelAssignment.assessmentModel.id eq model.id}" />
							<option value="${model.id}" ${selected ? 'selected="selected"' : ''}>${model.name}</option>
						</c:forEach>
					</optgroup>
				</c:if>
				<c:if test="${not empty rubricAssessmentModels}">
					<optgroup label="Rubric-based">
						<c:forEach var="model" items="${rubricAssessmentModels}">
							<c:set var="selected"
								value="${not empty template.assessmentModelAssignment 
                        and template.assessmentModelAssignment.assessmentModel.id eq model.id}" />
							<option value="${model.id}" ${selected ? 'selected="selected"' : ''}>${model.name}</option>
						</c:forEach>
					</optgroup>
				</c:if>
			</select></td>
			<td align="center" class="assessCheck"><c:set var="checked"
				value="${not empty template.assessmentModelAssignment and template.assessmentModelAssignment.anonymous}" />
			<input name="pAssessmentAnonymous" type="checkbox" value="true"
				${checked ? 'checked="checked" ' : ''}/></td>
		</tr>
	</table>
	<hr />
	<h3>Element-Level Assessment<span>&nbsp;&nbsp;Assign
	assessment to individual items in a portfolio template.</span></h3>
	<c:forEach var="category" items="${categories}">
		<div class="level1Bar">${category.title}</div>
		<table class="assessTable" id="assessTableElements" cellspacing="0">
			<tr>
				<th>&nbsp;</th>
				<th>Assessment model</th>
				<th class="rightmost">Anonymous assessment?</th>
			</tr>
			<c:forEach var="subcategory" items="${category.subcategories}">
				<tr>
					<td class="assessSubCat">${subcategory.title}</td>
					<td>&nbsp;</td>
					<td class="assessCheck">&nbsp;</td>
				</tr>
				<c:forEach var="def" items="${subcategory.templateElementDefs}">
					<tr class="assessElementRow">
						<td>${def.wizardElementDefinition.title}</td>
						<td><select name="eAssessmentModel_${def.id}">
							<option value="">None</option>
							<c:if test="${not empty basicAssessmentModels}">
								<optgroup label="Basic">
									<c:forEach var="model" items="${basicAssessmentModels}">
										<c:set var="selected"
											value="${not empty def.assessmentModelAssignment 
			                        and def.assessmentModelAssignment.assessmentModel.id eq model.id}" />
										<option value="${model.id}" ${selected ? 'selected="selected"' : ''}>${model.name}</option>
									</c:forEach>
								</optgroup>
							</c:if>
							<c:if test="${not empty outcomeAssessmentModels}">
								<optgroup label="Outcome-based">
									<c:forEach var="model" items="${outcomeAssessmentModels}">
										<c:set var="selected"
											value="${not empty def.assessmentModelAssignment 
                              and def.assessmentModelAssignment.assessmentModel.id eq model.id}" />
										<option value="${model.id}" ${selected ? 'selected="selected"' : ''}>${model.name}</option>
									</c:forEach>
								</optgroup>
							</c:if>
							<c:if test="${not empty rubricAssessmentModels}">
								<optgroup label="Rubric-based">
									<c:forEach var="model" items="${rubricAssessmentModels}">
										<c:set var="selected"
											value="${not empty def.assessmentModelAssignment 
                              and def.assessmentModelAssignment.assessmentModel.id eq model.id}" />
										<option value="${model.id}" ${selected ? 'selected="selected"' : ''}>${model.name}</option>
									</c:forEach>
								</optgroup>
							</c:if>
						</select></td>
						<td align="center" class="assessCheck"><c:set var="checked"
							value="${not empty def.assessmentModelAssignment and def.assessmentModelAssignment.anonymous}" />
						<input name="eAssessmentAnonymous_${def.id}" value="true"
							type="checkbox" ${checked ? 'checked="checked" ' : ''} /></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
	</c:forEach>
	<fieldset class="submitSet"><br />
	<input type="submit" name="prev" value="Prev: Sections"> <input
		type="submit" name="next" value="Next: Publish"></fieldset>
	</form>
</tags:communityPage>

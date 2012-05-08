<!-- $Name:  $ -->
<!-- $Id: templateBuilderSections.jsp,v 1.4 2011/03/14 19:37:44 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Template Builder" id="templateBuilderSections"
	cssClass="collSections">
	
	<div>
		<!-- ERRORS GO HERE -->
		<c:forEach var="templateError" items="${templateErrors}">
			<div style="font-weight: bold; font-size: 16pt; color: red;">&bull;&nbsp;${templateError}</div>
		</c:forEach>
	</div>
	
	<jsp:include page="templateBuilderSteps.jsp">
		<jsp:param name="step" value="content" />
	</jsp:include>
	<h2>Template Sections</h2>
	<p>Templates are composed of top-level sections, subsections, and
	elements. To begin, create a top-level section with a name of your
	choice. Next, create a subsection. When the subsection is created, you
	will then be able to add elements to it.</p>
	<div class="grayform">
	<form action="templateBuilderSections.do" class="newFormAlt"
		method="post"><input type="hidden" name="method"
		value="createSection"> <input type="hidden" name="templateId"
		value="${template.templateId}"> <label for="name">Add
	a new top-level section:</label> <input type="text" name="name" id="name"
		value="" /><br />
	<label></label> <input type="submit" value="Create section" /></form>
	</div>
	<hr class="clearboth" />
	<c:forEach var="category" items="${categories}">
		<h3><a
			href="templateBuilderSections.do?method=sectionUp&categoryId=${category.id}"><img
			src="/images/iconUp.gif" /></a> <a
			href="templateBuilderSections.do?method=sectionDown&categoryId=${category.id}"><img
			src="/images/iconDown.gif" /></a> ${category.title} <a
			href="templateBuilderSections.do?method=deleteSection&categoryId=${category.id}">Delete
		this section</a></h3>
		<div class="newIndent1">
		<div class="grayform grayformNarrow">
		<form action="templateBuilderSections.do" class="newFormAlt"
			method="post"><input type="hidden" name="method"
			value="createSection"> <input type="hidden"
			name="parentCategoryId" value="${category.id}"> <input
			type="hidden" name="templateId" value="${template.templateId}">
		<label>Add a subsection:</label> <input type="text" name="name"
			value="" /> <label></label> <input type="submit"
			value="Create subsection" /></form>
		</div>
		<br />
		<c:forEach var="subcategory" items="${category.subcategories}">
			<h4><a
				href="templateBuilderSections.do?method=sectionUp&categoryId=${subcategory.id}"><img
				src="/images/iconUp.gif" /></a> <a
				href="templateBuilderSections.do?method=sectionDown&categoryId=${subcategory.id}"><img
				src="/images/iconDown.gif" /></a> <span class="subcatTitle">${subcategory.title}</span>
			<a href="templateBuilderElementGroup.do?categoryId=${subcategory.id}"><strong>Add
			subsection elements</strong></a> | <a
				href="templateBuilderSections.do?method=deleteSection&categoryId=${subcategory.id}">Delete
			this subsection</a></h4>
			<div class="newIndent1 templateBuilderElementsList">
			<ul>
				<c:forEach var="def" items="${subcategory.templateElementDefs}">
					<li><a
						href="templateBuilderSections.do?method=elementUp&templateElementId=${def.id}"><img
						src="/images/iconUp.gif" alt="Move up" /></a> <a
						href="templateBuilderSections.do?method=elementDown&templateElementId=${def.id}"><img
						src="/images/iconDown.gif" alt="Move down" /></a> <strong>${def.wizardElementDefinition.title}</strong>
					<a
						href="templateBuilderSections.do?method=deleteElement&templateElementId=${def.id}">Delete</a>
					<div class="newIndent1">
					(${def.wizardElementDefinition.elementDefinition.elementName}) <br />
					<c:if test="${not empty def.wizardElementDefinition.description}">
						<p>${def.wizardElementDefinition.description}</p>
					</c:if></div>
					</li>
				</c:forEach>
			</ul>
			</div>
		</c:forEach>
		<hr class="clearboth " />
		</div>
	</c:forEach>
	<br class="clearboth" />
	<input type="button" name="prev" value="Prev: Name and Description"
		onclick="window.location='templateBuilderName.do?templateId=${template.templateId}'" />
	<input type="button" name="next" value="Next: Assessment"
		onclick="window.location='templateBuilderAssessment.do?templateId=${template.templateId}'" />
</tags:communityPage>

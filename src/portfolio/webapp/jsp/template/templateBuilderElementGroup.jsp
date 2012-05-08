<!-- $Name:  $ -->
<!-- $Id: templateBuilderElementGroup.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Template Builder" id="templateBuilderElementGroup">
	<jsp:include page="templateBuilderSteps.jsp">
		<jsp:param name="step" value="content" />
	</jsp:include>
	<h2>Select Elements</h2>
	<p>First, use the dropdown to select a collection guide. Then, use
	the checkboxes the select the elements you wish to include from the
	collection guide.</p>
	<form id="templateForm" action="templateBuilderElementGroup.do"
		method="get"><input id="categoryIdInput" type="hidden"
		name="categoryId" value="${category.id}"> <input type="hidden"
		name="method" value="save"> <select id="guideIdSelect"
		name="guideId"
		onchange="window.location='templateBuilderElementGroup.do?guideId='+$F('guideIdSelect')+'&categoryId='+$F('categoryIdInput')">
		<option value="">Select collection guide</option>
		<c:forEach var="guide" items="${collectionGuides}">
			<c:choose>
				<c:when test="${param.guideId eq guide.id}">
					<option value="${guide.id}" selected="selected">${guide.title}</option>
				</c:when>
				<c:otherwise>
					<option value="${guide.id}">${guide.title}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select> <c:if test="${not empty selectedGuide}">
		<ul id="templateBuilderSelectElements">
			<c:forEach var="cat" items="${selectedGuide.categories}">
				<li class="selectElementsTop">${cat.title}
				<ul>
					<c:forEach var="subcat" items="${cat.categories}">
						<li>${subcat.title}
						<ul>
							<c:forEach var="def" items="${subcat.wizardElementDefinitions}">
								<li><input type="checkbox" name="guideElementId"
									value="${def.id}" id="${def.id}"> <label
									for="${def.id}">${def.title}</label></li>
							</c:forEach>
						</ul>
						</li>
					</c:forEach>
				</ul>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<fieldset class="submitSet"><br />
	<br />
	<input type="submit" name="next"
		value="Save &amp; return to template sections" /></fieldset>
	</form>
</tags:communityPage>

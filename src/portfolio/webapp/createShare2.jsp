<!-- $Name:  $ -->
<!-- $Id: createShare2.jsp,v 1.11 2011/02/24 21:49:16 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="share" pageTitle="Choose content">
	<div class="MainContent" id="createShare2"><jsp:include
		page="shareheader.jsp">
		<jsp:param name="new" value="${share.new}" />
		<jsp:param name="step" value="2" />
	</jsp:include>
	<h1>Step 2. Choose Content</h1>
	<div class="Instructions">
	<p>Check items you want to include in this Portfolio.</p>
	</div>
	<strong>Title: <c:out value="${share.shareName}" /></strong>
	<p>Description: <c:out value="${share.shareDesc}" /></p>

	<c:if test="${share.template.assessmentModelAssignment ne null}">

		<c:set var="assessmentModel"
			value="${share.template.assessmentModelAssignment.assessmentModel}" />
		<p><span class="assessableCallout"> Portfolio-Level
		Assessment Model: <strong>${assessmentModel.name}</strong>. <a
			href="#"
			onclick="new EPF.widget.modal.Modal({ajax:{url:'/assessmentModelWindow.do',options:{parameters:{assessmentModelId: ${assessmentModel.id}}}}, width:780, title: '${assessmentModel.name}'})">
		View assessment criteria (pop-up) </a> </span></p>
	</c:if> <html:form action="saveCreateShare.do" styleId="shareForm">
		<html:hidden property="page" value="2" />
		<html:hidden property="publicView" />
		<input type="hidden" name="shareId" id="shareId" value="${share.shareId}" />
		<input type="hidden" name="back" value="false" />
		<%@ include file="messages.jsp"%>
		<%@ include file="errorMsgs.jsp"%>

		<a href="#"
			onclick="$$('#shareForm input[type=checkbox]').each(function(e){e.checked = true;}); return false;">check
		all</a>
  |
  <a href="#"
			onclick="$$('#shareForm input[type=checkbox]').each(function(e){e.checked = false;}); return false;">uncheck
		all</a>
		<!-- New Step 2 -->
		<div class="content"><c:forEach items="${topLevelCats}"
			var="topLevelCat" varStatus="index">
			<div class="catHeader"
				onclick="expandCollapse('level1-<c:out value="${topLevelCat.id}"/>');">
			<c:out value="${topLevelCat.title}" /></div>
			<div class="catBody" id="level1-<c:out value="${topLevelCat.id}"/>">
			<c:forEach items="${topLevelCat.subcategories}" var="subcategory"
				varStatus="index">
				<div class="subcatHeader"
					onclick="expandCollapse('level2-<c:out value="${subcategory.id}"/>');">
				<c:out value="${subcategory.title}" /></div>
				<div class="subcatBody"
					id="level2-<c:out value="${subcategory.id}"/>"><c:forEach
					items="${subcategory.templateElementDefs}" var="element"
					varStatus="index">
					<div class="templateElementDefSection">
					<div class="nameSection"><c:if
						test="${element.assessmentModelAssignment ne null}">
						<span class="assessableCallout"> <c:set
							var="assessmentModel"
							value="${element.assessmentModelAssignment.assessmentModel}" /> <a
							href="#"
							onclick="new EPF.widget.modal.Modal({ajax:{url:'/assessmentModelWindow.do',options:{parameters:{assessmentModelId: ${assessmentModel.id}}}}, width:780, title: '${assessmentModel.name}'}); return false;">
						[assessable] </a> </span>
					</c:if> <c:out value="${element.elementTitle}" /></div>
					<div class="entriesSection"><c:choose>
						<c:when
							test="${element.instances == null || empty element.instances}">
							<span class="none">none available</span>
						</c:when>
						<c:otherwise>
							<div id="elType-<c:out value="${element.id}"/>">
							<ul class="elementEntryList">
								<c:forEach items="${element.instances}" var="entry"
									varStatus="index">
									<bean:define id="isChecked"
										value="${epf:contains(elementMap[element.id], entry.entryId)}" />
									<li class="elementEntry" id="elementEntry_${entry.entryKeyId}">
									<div class="entryTools"><input type="checkbox"
										name="mapping${element.id}" value="${entry.entryId}" 
										<c:if test="${isChecked == 'true'}">checked</c:if> /></div>
									<div class="entryTitle">${entry.entryName}</div>
									<div class="entryType"><img alt="source icon"
										src="${entry.elementDefinition.iconPath}" class="sourceImage" />
									${entry.elementDefinition.name}</div>
									<div class="entryDateModified">${epf:relativeFormat(entry.modifiedDate)}</div>
									</li>
									<!-- End Element Instance Loop -->
								</c:forEach>
							</ul>
							</div>
						</c:otherwise>
					</c:choose></div>
					</div>
					<!-- templateElementDefSection -->
				</c:forEach> <!--
</div>
--> <!-- End Third-Level Category Loop --></div>
				<!-- End Second-Level Category Loop -->
			</c:forEach></div>
			<!-- End Top-Level Category Loop -->
		</c:forEach></div>
		<!-- End New Step 2 -->

		<!-- Start Old Step 2 (remove after making changes) -->
		<!-- End Old Step 2 -->

		<div class="share" width="100%"><input type="button" class="btn"
			value="Back" onclick="goBack('createShare1');" /> <input
			type="button" class="btn" onclick="shareCancelConfirm();"
			value="Cancel" /> <input type="button" class="btn"
			value="Save &amp; Finish" name="save" onclick="if(alertEmptyOk()){saveFinish();}" /> <input
			type="submit" name="continue" class="btn" value="Save &amp; Continue" onclick="return alertEmptyOk();"/>
		</div>
		<input type="hidden" name="nextStep" value="createShare2a" />
	</html:form></div>
	
	<script language="JavaScript" type="text/javascript">
		function alertEmptyOk() {
			var isempty = true;
			$$('#shareForm input[type=checkbox]').each(function(e){
				if (e.checked) {
					isempty = false;
				}
			});
			if (isempty) {
				return confirm("Warning: This portfolio is currently empty. Click cancel to add items. Click OK to proceed anyway.");
			} else {
				return true;
			}
		}
	</script>
</tags:portfolioPage>
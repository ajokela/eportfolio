<!-- $Name:  $ -->
<!-- $Id: createShare2a.jsp,v 1.9 2011/02/24 21:49:16 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="share" pageTitle="Arrange Content">
	<div class="MainContent" id="sortElements"><jsp:include
		page="shareheader.jsp">
		<jsp:param name="new" value="${share.new}" />
		<jsp:param name="step" value="3" />
	</jsp:include>
	<h1>Step 3. Arrange Content</h1>
	<div class="Instructions">
	<p>Review and adjust the sequence of items within the Portfolio.</p>
	</div>
	<strong>Title: <c:out value="${share.shareName}" /></strong>
	<p>Description: <c:out value="${share.shareDesc}" /></p>
	<html:form action="saveSortedShare.do" styleId="shareForm">
		<input type="hidden" name="shareId" id="shareId" value="${share.shareId}" />
		<html:hidden property="page" value="2a" />
		<html:hidden property="publicView" />
		<input type="hidden" name="elementMapId" />
		<%@ include file="messages.jsp"%>
		<%@ include file="errorMsgs.jsp"%>
		<c:forEach items="${topLevelCats}" var="topLevelCat" varStatus="index">
			<c:if test="${topLevelCat.populated}">
				<div class="catHeader"><c:out value="${topLevelCat.title}" />
				</div>
				<div class="catBody"><c:forEach
					items="${topLevelCat.subcategories}" var="subcategory"
					varStatus="index">
					<c:if test="${not empty subcategory.shareEntries}">
						<div class="subcatHeader"><c:out
							value="${subcategory.title}" /></div>
						<div class="subcatBody">
						<ul class="elementEntryList" id="ul_${subcategory.id}">
							<c:forEach items="${subcategory.shareEntries}" var="shareEntry"
								varStatus="index">
								<li class="elementEntry"
									id="li_${shareEntry.elementId}${shareEntry.entryIndex}"><input
									name="${shareEntry.elementId}:${shareEntry.entryIndex}"
									type="hidden" size="3"
									value="<c:out value="${shareEntry.sortOrder}"/>"
									style="font-size: 9px;">
								<div class="entryTools"><img class="draggable"
									src="/images/draggable.png" /></div>
								<div class="entryTitle">${shareEntry.element.entryName}</div>
								<div class="entryType"><img alt="source icon"
									src="${shareEntry.element.elementDefinition.iconPath}"
									class="sourceImage" />
									<!-- Fixed 28/March/2011 - Alex Jokela -->
								${shareEntry.elementTitle}</div>
								
								<div class="entryDateModified">${epf:relativeFormat(shareEntry.element.modifiedDate)}</div>
								</li>
							</c:forEach>
						</ul>
						</div>
					</c:if>
				</c:forEach></div>
			</c:if>
		</c:forEach>
		<fieldset class="submitSet"><input type="button"
			class="btn" value="Back" onclick="goBack('createShare2');" /> <input
			type="button" class="btn" onclick="shareCancelConfirm();"
			value="Cancel" /> <input type="button" class="btn"
			value="Save &amp; Finish" name="save" onclick="saveFinish();" /> <input
			type="submit" name="continue" class="btn" value="Save &amp; Continue" />
		<input type="hidden" name="nextStep" value="createShareSelectStyle" />
		</fieldset>
	</html:form></div>
	<script type="text/javascript">
    $$('.elementEntryList').each(function(ul){
      Sortable.create(ul.id, {
        onUpdate: function(container) {
          ul.childElements().each(function(li, index){
            li.down('input[type=hidden]').value = index + 1;
          });
        }
      });
    });
	</script>
</tags:portfolioPage>


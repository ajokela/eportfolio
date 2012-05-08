<!-- $Name:  $ -->
<!-- $Id: quickShare1.jsp,v 1.10 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="share">
	<div class="MainContent">
	<h1>QUICKSHARE: Set Up Portfolio &amp; Arrange Content</h1>
	<%@ include file="/messages.jsp"%> <%@ include
		file="/errorMsgs.jsp"%> 
		
		<form action="/saveQuickShare.do" name="share" id="shareForm" method="POST" class="newForm">
		
		<input type="hidden" name="templateId" value="${share.templateId}" />
		<html:hidden property="page" value="1" />
		<div class="level1Bar">Enter a Title and Description</div>
		<fieldset>
		<ul>
			<li><label for="shareName">Title*:</label> <input type="text"
				name="shareName" id="shareName" class="inputsizer75" maxlength="50"
				size="50" autocomplete="off" />&nbsp;</li>
			<li>
			<div>
				<div>
					<div style="width: 75%; float: left; display: block;">
						<span>Description: (describe the purpose of this portfolio)</span>
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="shareDescCounter"></span>
					</div>
				</div>
				<div>
					<textarea name="shareDesc" id="shareDesc" cols="40" rows="5" class="inputsizer90" autocomplete="off"></textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'shareDesc',
				               counterEl: 'shareDescCounter',
				               max: 800
				           });
			    </script>
			    
		    </div>

			</li>
		</ul>
		</fieldset>
		<p>*Required</p>


		<!-- Arrange Content -->
		<div class="wizard"><!-- Begin Top-Level Category Loop (replace "catCode" with a unique code for each category, starting with a letter, even if the letter is constant) -->
		<c:forEach items="${topLevelCats}" var="topLevelCat" varStatus="index">
			<div class="level1Bar"><c:out value="${topLevelCat.title}" /></div>
			<div class="indent" id="level1-<c:out value="${topLevelCat.id}"/>">
			<!-- Begin Sub Category Loop (replace "cat2Code" with a unique code for each category, starting with a letter, even if the letter is constant) -->
			<c:forEach items="${topLevelCat.subcategories}" var="subcategory"
				varStatus="index">
				<div class="level2Bar"><c:out value="${subcategory.title}" />
				</div>
				<table class="elementInstances"
					summary="This table displays elements of your portfolio so that you can edit their arrangement">
					<colgroup span="5">
						<col class="cs3Order" />
						<col class="cs3Title" />
						<col class="cs3Attachments" />
					</colgroup>
					<thead>
						<tr>
							<td>Order</td>
							<td>Element Type/Title</td>
						</tr>
					</thead>
					<tbody>
						<!-- Begin Element Loop -->
						<c:set var="rowColorCount" value="1" />
						<c:forEach items="${subcategory.shareEntries}" var="shareEntry">
							<tr>
								<td
									<c:choose><c:when test="${rowColorCount % 2 == 0}">class="evenRow"</c:when><c:otherwise>class="oddRow"</c:otherwise></c:choose>>
								<c:set var="paramName"
									value="order${shareEntry.elementId}-${shareEntry.entryIndex}" />
								<c:set var="orderValue"
									value="${not empty param[paramName] ? param[paramName] : shareEntry.sortOrder}" />
								<input name="${paramName}" type="text" size="3"
									value="${orderValue}" style="font-size: 9px;"></td>
								<td
									<c:choose><c:when test="${rowColorCount % 2 == 0}">class="evenRow"</c:when><c:otherwise>class="oddRow"</c:otherwise></c:choose>>

								<c:if test="${shareEntry.element.elementType eq 'link'}">
                            	Link: 
                            </c:if> <c:if
									test="${shareEntry.element.elementType ne 'link'}">
									<c:out value="${shareEntry.elementName}" /> : 
                            </c:if> <c:out
									value="${shareEntry.element.entryName}" /></td>
							</tr>
							<!-- End share entry Loop -->
							<c:set var="rowColorCount" value="${rowColorCount + 1}" />
						</c:forEach>
					</tbody>
				</table>
				<!-- End Sub Category Loop -->
			</c:forEach></div>
			<!-- End Top-Level Category Loop -->
		</c:forEach></div>
		<!-- End Arrange Arrange Content -->
		<div align="center">
		<div class="share"><input type="button"
			name="org.apache.struts.taglib.html.CANCEL" value="Cancel"
			onclick="shareCancelConfirm();" /> <input type="submit" name="save"
			value="Create" /></div>
		</div>
	</form></div>
	<script type="text/javascript">
   $('shareForm').observe('submit', function(event){
     if ($F('shareName').empty()) {
       new dialog.Alert('You must enter a title.').show();
       event.stop();
     }
   });
</script>

</tags:portfolioPage>

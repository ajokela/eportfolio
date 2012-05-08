<!-- $Name:  $ -->
<!-- $Id: editElement.jsp,v 1.21 2011/02/18 19:52:42 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<%@ page import="org.portfolio.util.Configuration"%>

<div class="elementEdit yui-skin-sam">
<div class="entryType"><img src="${dataDef.elementDefinition.iconPath}" />${dataDef.elementDefinition.name}</div>
<form method="POST" action="/element/save/" class="editElementForm">
<div class="contents"><input type="hidden" name="nodeId" value="${dataDef.nodeId}" />
	<c:if test="${!dataDef.new}">
		<input type="hidden" name="entryId" value="${dataDef.entryId}" />
	</c:if>
	<jsp:include page="${dataDef.elementDefinition.elementType.jspPrefix}${dataDef.elementDefinition.elementType.editJsp}" />
	<div class="categoriesSection">
		<c:set var="category" value="${dataDef.elementDefinition.elementType.category}"/>
		<div class="hd">Category:</div><div class="bd"><c:out value="${fn:toLowerCase(category)}"/></div>
		<c:if test="${not empty keywords}">
			<div class="hd">Keywords:</div>
			<div class="bd">
				<c:forEach var="keyword" items="${keywords}" varStatus="status">
					${keyword}<c:if test="${!status.last}">, </c:if>
				</c:forEach>
			</div>
		</c:if>
	</div>
</div>
<div class="lastUpdated">Last updated: <span
	class="lastUpdatedDate">${epf:relativeFormat(dataDef.modifiedDate)}</span>
</div>
<div class="actions">
	<input type="submit" name="save" value="Save" class="saveButton" />
	<!-- input type="submit" name="saveContinue" value="Save & Continue" class="saveContinueButton" / -->
	<span class="saveContinueButton"></span>
	<a href="#" class="cancelLink">cancel</a>
</div>
</form>
</div>

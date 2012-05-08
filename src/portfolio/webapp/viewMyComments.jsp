<!-- $Name:  $ -->
<!-- $Id: viewMyComments.jsp,v 1.4 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="view"
	pageTitle="Portfolio : View Comments For Others">
	<div class="MainContent">
	<h1>My Comments for Others</h1>
	<div class="Instructions">
	<p>In this section you can view the comments you have made on your
	own and other users Portfolios, elements, and materials.</p>
	</div>
	<table class="sortable data rowstyle-r0" width="100%" border="0"
		cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<th class="sortable">Commented Item</th>
				<th class="sortable">Item Owner</th>
				<th class="sortable">My Comment</th>
				<th class="sortable-date favour-reverse">Date</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty commentList}">
				<tr>
					<td colspan="5">
					<p>You have not submitted any comments for others. This page
					shows all the comments you have made about other users' shares,
					elements, and materials.</p>
					</td>
				</tr>
			</c:if>
			<c:forEach var="comment" items="${commentList}" varStatus="status">
				<tr class="${status.index % 2 == 1 ? 'r0' : ''}">
					<td><c:if test="${comment.type == 1}">
		         Material:
             <c:set var="material"
							value="${materials[comment.commentId]}" />
						<a
							href="${material.type == 'file' ? 'editFile' : 'editUrl'}.do?entryId=${material.entryId}#comments${material.entryId}"><c:out
							value="${material.sampleName}" /></a>
					</c:if> <c:if test="${comment.type == 2}">
             Portfolio:
             <c:set var="share" value="${shares[comment.commentId]}" />
						<a target="_blank"
							href="/portfolio/${share.shareId}#comments${share.shareId}"><c:out
							value="${share.shareName}" /></a>
					</c:if> <c:if test="${comment.type == 3}">
             Element:
             <c:set var="element" value="${elements[comment.commentId]}" />
						<c:set var="elementDef" value="${elementDefs[element.entryId]}" />
						<a
							href="viewElement.do?action=edit&nodeId=${elementDef.elementId}&entryId=${element.entryId}#comments${element.entryId}"><c:out
							value="${element.entryName}" /></a>
					</c:if> <c:if test="${comment.type == 4}">
             Portfolio:
             <c:set var="share" value="${shares[comment.commentId]}" />
						<c:set var="shareEntry" value="${shareEntries[comment.commentId]}" />
						<a target="_blank"
							href="/portfolio/${share.shareId}#comments${shareEntry.id}"><c:out
							value="${share.shareName}" /></a>
					</c:if> <c:if test="${comment.type == 5}">
             Portfolio:
             <c:set var="share" value="${shares[comment.commentId]}" />
						<c:set var="assessment" value="${assessments[comment.commentId]}" />
						<a target="_blank"
							href="/portfolio/${share.shareId}#comments${assessment.assessmentId}"><c:out
							value="${share.shareName}" /></a>
					</c:if></td>
					<td><c:out value="${comment.owner.displayName}" /></td>
					<td><c:out value="${comment.commentText}" /></td>
					<td><fmt:formatDate value="${comment.commentDate}"
						pattern="M/d/yyyy" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</tags:portfolioPage>

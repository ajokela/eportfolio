<!-- $Name:  $ -->
<!-- $Id: ProfDevelopmentPS.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<c:choose>
	<c:when test="${empty element.recordsList}">
		<tags:elementField>
      There are no records on file.
    </tags:elementField>
	</c:when>
	<c:otherwise>
		<tags:elementField>
			<table class="basic">
				<tr>
					<td><b>Course Name</b></td>
					<td><b>Status</b></td>
					<td><b>Status Date</b></td>
				</tr>
				<c:forEach items="${element.recordsList}" var="record">
					<tr>
						<td><c:out value="${record.courseName}" /></td>
						<td><c:out value="${record.status}" /></td>
						<td><c:out value="${record.statusDate}" /></td>
					</tr>
				</c:forEach>
			</table>
		</tags:elementField>
	</c:otherwise>
</c:choose>

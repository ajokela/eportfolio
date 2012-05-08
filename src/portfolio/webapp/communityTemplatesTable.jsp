<!-- $Name:  $ -->
<!-- $Id: communityTemplatesTable.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:forEach var="template" items="${templates}" varStatus="status">
	<div class="templateChoice"><input id="t_${template.templateId}"
		type="radio" name="templateIdTemp" value="${template.templateId}"
		onclick="$('templateIdInput').value='${template.templateId}'" /> <label
		for="t_${template.templateId}"><strong><c:out
		value="${template.name}" /></strong> <c:out value="${template.description}" /></label>
	</div>
</c:forEach>

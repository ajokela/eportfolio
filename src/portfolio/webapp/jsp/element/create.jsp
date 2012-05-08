<!-- $Name:  $ -->
<!-- $Id: create.jsp,v 1.10 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="createNewElement">
<ul class="elementListing">
	<c:forEach var="elementDefinition" items="${defs}">
		<li><img alt="source icon" src="${elementDefinition.iconPath}" />
		<a href="#" id="link_${elementDefinition.elementId}">${elementDefinition.elementName}</a>
		</li>
	</c:forEach>
</ul>
</div>

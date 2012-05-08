<!-- $Name:  $ -->
<!-- $Id: listElementTypes.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : Element Types">
	<div class="MainContent" id="listElementTypes">
	<h1>Element Types</h1>
	<ul>
		<c:forEach var="elementType" items="${types}">
			<li>${elementType.name}</li>
		</c:forEach>
	</ul>
	</div>
	<!-- MainContent -->
</tags:portfolioPage>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<%@ page import="org.portfolio.util.Configuration"%>

<ul>

	<c:if test="${peoples_count > 0}">
		<c:forEach var="person" items="${peoples}"> 
		<li><span class="informal">${person.displayName} (</span>${person.username}<span class="informal">)</span></li>
		</c:forEach>
	</c:if>
	
	<c:if test="${peoples_count == 0}">
		<li><span class="informal">User not found</span></li>
	</c:if>
	
</ul>

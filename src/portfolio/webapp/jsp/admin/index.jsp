<!-- $Name:  $ -->
<!-- $Id: index.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="general" pageTitle="Portfolio : Administration">
	
	<div id="adminPage" style="padding: 2px;">
		
		<div>
			<div><b>Portfolio Admins:</b></div>
			<div>
			
				<c:forEach var="person" items="${admins}" varStatus="status">
					
					<c:if test="${status.last == false}">
					${person.displayName},		
					</c:if>
					
					<c:if test="${status.last == true}">
					${person.displayName}
					</c:if>
					
				</c:forEach>
			
			</div>
		</div>
	
		<div style="color: #f00; padding-bottom: 10px;">
			<c:if test="${userDisabled != null}">
				${userDisabled.displayName} (${userDisabled.username}) has been disabled.
			</c:if>
		</div>
		<div>
			<form action="/user/disable" method="post">
			
			<div>
			<input type="text" name="username" size="40" /><br />
			<span style="font-size: 10pt;">X.500 or Email Address (if guest)</span>
			</div>
			
			<div>
				<input type="checkbox" name="email_user" value="true" />&nbsp;Email user explaining their account has been disabled?
			</div>
			
			<input type="submit" value="Disable" />
			</form>
		</div>
	</div>
	
</tags:portfolioPage>

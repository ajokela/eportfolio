<!-- $Name:  $ -->
<!-- $Id: disable.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="general" pageTitle="ePortfolio : Administration">
	
	<div class="MainContent" id="adminPage">
		<div style="text-align: center;">
			<c:if test="${person != null}">
			Are you certain you want to disable the account for ${person.displayName} (${person.username})?<br /><br />
			
			<a href="/user/disable/${person.base64Username}/${emailUser}">Click Here to Disable ${person.username}</a>
			
			</c:if>
		</div>
	</div>
		
</tags:portfolioPage>

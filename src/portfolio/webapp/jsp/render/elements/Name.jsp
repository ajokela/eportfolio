<!-- $Name:  $ -->
<!-- $Id: Name.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<div>
	<span style="font-weight: bold;">${element.entryName}:&nbsp;</span>
	<c:if test="${element.title != null && element.title ne ''}">${element.title}</c:if>&nbsp;${element.firstName}&nbsp;${element.middleName}&nbsp;${element.lastName}
</div>
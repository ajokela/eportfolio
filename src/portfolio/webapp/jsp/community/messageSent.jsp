<!-- $Name:  $ -->
<!-- $Id: messageSent.jsp,v 1.1 2010/11/23 14:29:11 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" jsModules="button,dhtmlcalendar" pageTitle="Messages" id="communityMessgaes" returnTo="Community">
	<div class="MainContent communityHome yui-skin-sam" id="communityMessages" style="width: 100%;">

	Your message has been saved.  If you set a start/end date, your message will begin to run on the start date and will finish after the end date.
			
	</div>
</tags:communityPage>
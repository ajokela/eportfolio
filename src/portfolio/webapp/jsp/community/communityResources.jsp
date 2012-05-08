<!-- $Name:  $ -->
<!-- $Id: editLinks.jsp,v 1.7 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" pageTitle="Resources"
	id="editLinks" returnTo="editCommunity">

<div>

	<div style="display: block; float: left; width: 35%; padding-right: 4px;">
		
		<div style="display: none;">
			<a href="#" id="manageCollectionLink">Hide Available Files List</a>
		</div>
		
		<div id="resourcesList">
		
		</div>

	</div>
	
	<div style="display: block; float: left; width: 60%;">
		
		<div class="MainContent" id="manageCollection">
			<script>
		      EPF.use("communityCollection", "${applicationScope['org.portfolio.project.version']}", function() {
		        new EPF.controller.communityCollection.Main($('manageCollection'), '${community.id}');
		      });
		    </script>
		</div>
	</div>
	<div style="clear: both;"></div>	
</div>

</tags:communityPage>
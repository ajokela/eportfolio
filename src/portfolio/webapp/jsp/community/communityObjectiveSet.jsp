<!-- $Name:  $ -->
<!-- $Id: communityObjectiveSet.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<tags:communityPage community="${community}"
	pageTitle="View Objective Set" id="viewObjectiveSet"
	returnTo="communityHome" cssClass="yui-skin-sam">
	<h2 class="objectiveSetTitle"><c:out value="${objectiveSet.name}" /></h2>
	<p class="objectiveSetDescription"><c:out
		value="${objectiveSet.description}" /></p>
	<tags:objectiveList parentObjective="${objectiveSet}" level="1"
		editable="${false}" />
</tags:communityPage>

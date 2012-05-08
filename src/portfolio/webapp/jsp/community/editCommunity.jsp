<!-- $Name:  $ -->
<!-- $Id: editCommunity.jsp,v 1.5 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:communityPage community="${community}" pageTitle="Manage Community" id="editCommunity" returnTo="communityHome">
<div>
          <c:choose>
            <c:when test="${community.deleted}"><a href="/community.do?method=restore&communityId=${community.id}">Restore Community</a></c:when>
            <c:otherwise><a id="deleteCommunity" href="/community.do?method=delete&communityId=${community.id}">Delete Community</a></c:otherwise>
          </c:choose>


</div>

<div>
	<ul class="editList">
		<li>
			<a href="community.do?method=edit&communityId=${community.id}">edit</a>
			<span>Community Details</span>
		</li>
	    <li>
	      <a href="/communityRole.do?communityId=${community.id}">edit</a>
	      <span>Roles</span>
	    </li>
	    <li>
	    	<a href="/community/resources/${community.id}">edit</a>
	    	<span>Resources</span>
	    </li>
		<li>
			<a href="/communityLink.do?communityId=${community.id}">edit</a>
			<span>Links</span>
		</li>
		<li>
			<a href="/communityObjective.do?communityId=${community.id}">edit</a>
			<span>Objectives</span>
		</li>
	    <li>
	      <a href="/assessmentDirectory.do?communityId=${community.id}">edit</a>
	      <span>Assessment Models</span>
	    </li>
		<li>
			<a href="/editCollectionGuides.do?communityId=${community.id}">edit</a>
			<span>Collection Guides</span>
		</li>
		<li>
			<a href="/editTemplates.do?communityId=${community.id}">edit</a>
			<span>Portfolio Templates</span>
		</li>
		<li>
			<a href="/community/messages/manage/${community.id}">manage</a>
			<span>Community Messages</span>
		</li>
		<li>
			<a href="/community/interact/${community.id}">manage</a>
			<span>Community Interact</span>
		</li>
		<li>
			<a href="/community/copy/${community.id}">copy</a>
			<span>Copy this Community</span>
		</li>
		
	</ul>
</div>
  <script type="text/javascript">
    EPF.use("progressbar", "${applicationScope['org.portfolio.project.version']}", function() {
      $$('.progressBarWrapper').each(function(e){ 
        var pct = e.down('.percentText').innerHTML.slice(0,-1);
	      var pb = new YAHOO.widget.ProgressBar( {
	                value : parseInt(pct),
	                width : '60px',
	                height : '12px'
	            }).render(e.down('.progressBar'));
      });
      if ($('deleteCommunity')) {
        addLinkConfirm('deleteCommunity', 'Are you sure that you want to delete this community?');
      }
    });
  </script>
</tags:communityPage>

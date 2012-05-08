<!-- $Name:  $ -->
<!-- $Id: communityInfo.jsp,v 1.2 2010/11/23 14:27:17 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" pageTitle="ePortfolio : Community : ${community.name} Info" id="communityInfo" returnTo="Community">
	<div class="MainContent communityInfo yui-skin-sam" id="communityInfo">

		<div>
			
			<div>
		      <div style="float: left; vertical-align: top; width: 15%;">
		        <div style="font-weight: bold; display:inline;">Members:&nbsp;</div>
		        <div style="display:inline;">${numMembers}</div>
		      </div>
			</div>
				      
	        <c:if test="${not empty communityCoordinators}">
	        	<div>
			        <div style="float: left; vertical-align: top; width: 15%;">
				        <div style="font-weight: bold; display: inline;">Admins:&nbsp;</div> 
				        <div style="display:inline;"> 
				        <c:forEach var="person" items="${communityCoordinators}">
					       
					          <div style="display: block; vertical-align: top;">${person.displayName} <a href="mailto:${person.email}"><img src="/images/fugue/icon_shadowless/mail__arrow.png" /></a></div>
					        
				        </c:forEach>
				        </div>
			        </div>
				</div>
	        </c:if>
	        
	        <div style="float: left; vertical-align: top; width: 43.2%;">
	        	<div style="float: left; vertical-align: top;">
			        <div style="display: block; float: left; vertical-align: top; font-weight: bold;">Community Type:&nbsp;</div>
			        <div style="display:inline;"> ${community.privateCommunity ? 'Private' : 'Public'} Community</div>
			    </div>
			</div>

	        <div style="float: left; vertical-align: top; width: 15%;">
	        	<div style="float: left; vertical-align: top;">     
			        <div style="display: block; float: left; vertical-align: top; font-weight: bold;">Created:&nbsp;</div>
			        <div style="display:inline;"><fmt:formatDate value="${community.dateCreated}" pattern="MMMM d, yyyy"/></div>
				</div>
			</div>
				      
		</div>	
	</div>
</tags:communityPage>
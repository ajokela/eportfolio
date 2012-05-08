<!-- $Name:  $ -->
<!-- $Id: communityHome.jsp,v 1.30 2011/02/16 20:24:41 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:portfolioPage viewMode="enter" pageTitle="ePortfolio : Community : ${community.name}">
	<div class="MainContent communityHome yui-skin-sam" id="communityHome">

	<div id="manage" style="text-align: right;">
      
      <a href="/community/info/${community.id}">Community Info</a>
      
      <c:if test="${empty roles and not hasEditCommunityAccess}">
          | <a id="joinCommunity" href="/community.do?method=becomeMember&communityId=${community.id}">Add to My Communities</a>
      </c:if>
      
      <c:if test="${not empty roles and not hasEditCommunityAccess}">
          | <a id="joinCommunity" href="/community.do?method=removeMember&communityId=${community.id}">Remove from My Communities</a>
      </c:if>

      
      <c:if test="${hasEditCommunityAccess == true}">
      
          | <a href="/editCommunity.do?communityId=${community.id}">Manage Community</a>

      </c:if>

	  <c:if test="${isEvaluator == true or hasEditCommunityAccess == true}">
	  
          | <a href="/communityReports.do?communityId=${community.id}">View Reports</a>
          
      </c:if>

	  <c:if test="${isEvaluator != true and hasEditCommunityAccess != true}">
	  
          | <a href="/mycommunityreports/${community.id}">View My Reports</a>
          
      </c:if>

      
    </div>
	

	<div id="header">
		<div id="logo" style="display: none;"></div>
		<div id="title-desc">
	        <h1><c:out value="${community.name}" /></h1>
	        <c:if test="${not empty community.description}">
	          <span><c:out value="${community.description}" /></span><br /><br />
	        </c:if>
		</div>
	    		<br class="clearboth" />
	</div>

    <div id="left">

     	<div id="messages" class="section">
     		<h1>Messages</h1>
     		<div class="sectionBody">
     			<ul>
     				<li>
     				
     				<a href="/community/messages/${community.id}">Messages from community coordinator (${messageCnt} unread)</a>
     				
     				</li>
     				
     				<c:if test="${isEvaluator == true}">
     				<li>
     					<a href="/share/#unassessed/${community.id}">Portfolios to assess (${unassessed})</a>
     				</li>
     				</c:if>
     				
     				<li>
     					<a href="/share/#community-unread/${community.id}">Portfolios to view (${unread})</a>
     				</li>
     				
     				<!-- li>Comments</li -->
     			</ul>
     		</div>
     	</div>
     

	    <div id="objectives" class="section">
	      <h1>Objectives</h1>
        	<div class="sectionBody">
		      <c:choose>
		        <c:when test="${empty objectiveSets}">
		          <p class="sectionDescription">There are no Objectives.</p>
		        </c:when>
		        <c:otherwise>
		          <ul>
			          <c:forEach var="objectiveSet" items="${objectiveSets}">
			            <li>
			              <a href="/communityObjective.do?method=viewSet&objectiveSetId=${objectiveSet.id}">
                      <c:out value="${objectiveSet.name}" />
                    </a>
                    <p class="itemDescription">
                      <c:out value="${objectiveSet.description}" />
                    </p>
			            </li>
			          </c:forEach>
              </ul>
		        </c:otherwise>
		      </c:choose>
	      </div>
	    </div>


	    <div id="links" class="section">
	      <h1>Resources</h1>
        <div class="sectionBody">
		      <c:choose>
		        <c:when test="${empty links and empty communityResources}">
		          <p class="sectionDescription">There are no Resources.</p>
		        </c:when>
		        <c:otherwise>

					<div id="resourceCollection">
						<div style="width: 100%; text-align: center;">
						<img src="/images/indicator_big.gif" alt="" />
						</div>
						
						<script>
					      EPF.use("communityCollection", "${applicationScope['org.portfolio.project.version']}", function() {
					        new EPF.controller.communityCollection.Resources($('resourceCollection'), '${community.id}');
					      });
					    </script>
					</div>

		        </c:otherwise>
		      </c:choose>
	      </div>
	    </div>
	    
    </div>
    
     <div id="right">
     
     <div id="interact" class="section">
     	<h1>Interact</h1>
     	<div class="sectionBody">
     		<div>
				<c:forEach var="interact" items="${interacts}">
					<div style="padding: 2px;">
						<a href="${fn:escapeXml(interact.val)}" target="_blank">${interact.longType}</a>
					</div>
				</c:forEach>
     		</div>
     	</div>
     </div>
     
      <div id="collection" class="section">
        <h1>Collection</h1>
        <div class="sectionBody">
          <c:choose>
            <c:when test="${empty wizards}">
              <p class="sectionDescription">There are no Collection Guides</p>
            </c:when>
            <c:otherwise>
            	<div>
            		<div>
		                Your <a href="/collection/#community/${community.id}">community collection</a> contains ${totalNumElements} elements
					</div>
					<div style="clear: both; height: 20px;"></div>
					<div>
					<b>Collection Guides</b>       
		              	<ul>
		              
			                <c:forEach var="wizard" items="${wizards}" varStatus="status">
			                  <li>
			                    <a href="/guide/${wizard.id}"><c:out value="${wizard.title}" /></a> (${numElementsMap[wizard]})
			                  </li>
			                </c:forEach>
		              	
		              	</ul>
		        	</div>
		    	</div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div id="portfolios" class="section">
        <h1>Portfolios</h1>
        <div class="sectionBody">
          <c:choose>
            <c:when test="${empty templates}">
              <p class="sectionDescription">There are no Portfolios found.</p>
            </c:when>
            <c:otherwise>
            	
					<div>
				     	You have ${totPortfolios} <a href="/share/#community/${community.id}">community portfolios</a><br />
				     	<!--You have ${total} <a href="/share/#community/">community portfolios</a> -->
						<!--ul>
							<li><a href="/share/#advanced-search/type=my&communityId=${community.id}">Created by me</a> (${createdByMeCnt})</li>
							<li><a href="/share/#advanced-search/type=shared&communityId=${community.id}">Created by others</a> (${createdByOthersCnt})</li>
						</ul -->
					</div>
					<div style="clear: both; height: 20px;"></div>
					<div>
					  <b>Portfolio Templates</b>
		              
		              <ul>
		              
		                <c:forEach var="template" items="${templates}">
		                  <li>
		                    <a href="/share/#advanced-search/templateId=${template.id}">${template.name}</a> (${totalMap[template]})&nbsp;<a target="_blank" href="/createShare1.do?templateId=${template.id}&communityId=${community.id}">new</a>
							<div style="clear: both;"></div>
		                  	<div>

		                  		<div style="display: block; width: 25px; float: left;">&nbsp;</div>
		                  		<div style="display: block; float: left; width: 75%;"><a href="/share/#advanced-search/type=my&templateId=${template.id}">Created by me (${createdByMeMap[template]})</a></div>
		                  		<div style="clear: both;"></div>
		                  		
		                  		<div style="display: block; width: 25px; float: left;">&nbsp;</div>
		                  		<div style="display: block; float: left; width: 75%;"><a href="/share/#advanced-search/type=shared&templateId=${template.id}">Created by others (${createdByOthersMap[template]})</a></div>
		                  		<div style="clear: both;"></div>

		                  	</div>
		                  </li>
		                </c:forEach>
		                
		              </ul>
		        	</div>
				
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
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
        addLinkConfirm('deleteCommunity', 'Are you sure that you want to delete this community?')
      }
    });
  </script>
</tags:portfolioPage>

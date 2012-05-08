<!-- $Name:  $ -->
<!-- $Id: welcome.jsp,v 1.24 2011/03/24 19:03:26 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="general" pageTitle="ePortfolio : Welcome">

	<div class="MainContent" id="welcomePage" style="display: none">
	<div id="welcomeGreeting">
	<div id="thumb"><img id="thumbPic" src="/profilePicture"
		alt="Profile picture" /></div>
	<div id="welcomeGreetingText">
	<p id="welcomeGreetingMain">Welcome, ${Person.displayName}</p>
	<p id="welcomeGreetingSub">[ <a id="changePicLink" href="#">change
	profile picture</a> ]</p>
	</div>
	</div>
	<div id="lower">
	<div class="left">
	<div id="about" class="">
	<h2 class="">About Portfolio</h2>
	<p>Portfolio is your on-line
	account where you may manage information
	(elements) and uploaded files (materials). You have three options for managing your information in Portfolio:
	</p>
	<ul id="esv">
		<li><a class="esv enter" href="/collection/">Enter</a> elements,
		upload files, or join one or more communities and use their tools that
		provide guidance to enter infomation. All information entered by you
		is stored in your secure Portfolio collection.</li>
		<li><a class="esv share" href="/share/#my">Share</a> information
		in your Portfolio collection with others in publications,
		called portfolios. Each portfolio consists of one or more elements
		and/or files that is shared with one or more individuals. Community
		tools provide guidance for building portfolios.</li>
		<li><a class="esv view" href="/share/#shared">View</a> portfolios
		that others have shared with you. These portfolios may also be
		commented on, and in specific cases evaluated.</li>
	</ul>
	</div>
	<div id="gettingStarted">
	<h2>Getting Started</h2>
	<p>Select and join <a href="/community/46">ePortfolio Community</a>
	and use the tools for entering and sharing information.</p>
	</div>
	</div>
	<div id="myStuff">
	<h2 class="">My Stuff</h2>
	<div id="collectionSection" class="welcomeSection">
	<h3 class="heading">My Collection (${numElements})</h3>
	<div id="collectionBody" class="body">
	<div class="bodyInner">
	<ul>
		<li><a class="mainLink" href="/collection/">Elements
		(${numElements})</a></li>
	</ul>
	</div>
	</div>
	</div>
	<div id="portfoliosSection" class="welcomeSection">
	<h3 class="heading">My Portfolios (${numMyPortfolios +
	numSharedPortfolios + fn:length(adviseeList)})</h3>
	<div id="portfoliosBody" class="body">
	<div class="bodyInner">
	<ul>
		<li><a class="mainLink" href="/share/#my">Created by Me
		(${numMyPortfolios})</a> - <a target="_blank" href="/createShare1.do">new</a></li>
		<li><a class="mainLink" href="/share/#shared">Created by
		Others (${numSharedPortfolios})</a></li>

	</ul>
	</div>
	</div>
	</div>
	<div id="communitiesSection" class="welcomeSection">
	<h3 class="heading">My Communities (${fn:length(communities)})</h3>
	<div id="communitiesBody" class="body">
	<div class="bodyInner">
	<ul>
		<li><img id="myCommunitiesExpand" src="/images/toggle.png" /> <img
			id="myCommunitiesCollapse" src="/images/toggle_collapse.png"
			style="display: none" /> <a class="mainLink"
			href="/myCommunities.do">My Communities
		(${fn:length(communities)})</a></li>
		<c:forEach var="community" items="${Person.communities}">
			<li class="community" style="display: none"><a
				href="/community/${community.id}"><c:out
				value="${community.name}" /> </a></li>
		</c:forEach>
		<li><a class="mainLink" href="/community/directory">Community
		Directory</a></li>
	</ul>
	</div>
	</div>
	</div>
	</div>
	<br class="clearboth" />
	</div>
	</div>
	<!-- Main content ends -->
	<script type="text/javascript">
    EPF.use('welcome-css', "${applicationScope['org.portfolio.project.version']}", function() {
        $('welcomePage').show()
    });
    $('thumbPic', 'changePicLink').invoke('observe', 'click', function(event) {
      event.stop();
      
      new EPF.widget.modal.Modal({
          title: 'Change profile picture',
//          width: 300,
          ajax: {
              url: '/profilePicture/window'
          }
      });
    });
    document.observe('modal:closed', function(event) {
      $('thumbPic').src = $('thumbPic').src + '?t=' + (new Date()).getTime();
    });
	
    $('myCommunitiesExpand', 'myCommunitiesCollapse').invoke('observe', 'click', function(event) {
        $$('li.community').invoke('toggle');
        $('myCommunitiesExpand', 'myCommunitiesCollapse').invoke('toggle');
    });
</script>
</tags:portfolioPage>

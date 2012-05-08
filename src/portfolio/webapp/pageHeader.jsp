<!-- $Name:  $ -->
<!-- $Id: pageHeader.jsp,v 1.55 2011/03/24 19:03:26 ajokela Exp $ -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<%@ page import="org.portfolio.util.Configuration"%>

<c:set var="viewMode" value="${param.viewMode}" />
<c:if test="${empty viewMode}">
	<%-- just in case --%>
	<c:set var="viewMode" value="enter" />
</c:if>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>${param.pageTitle != null ? param.pageTitle :
'ePortfolio'}</title>

<!-- meta name="google-site-verification" content="Kie00h3X-ivroVk_VAULnaTYRzhoNI8vEiLBRCdsqMI" / -->

<link rel="shortcut icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/css/modules/core.css" />
<link rel="stylesheet" type="text/css" href="/css/prototip.css" />

<script type="text/javascript" src="/js/prototype.js"></script>
<script type="text/javascript" src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls"></script>
<script type="text/javascript" src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js"></script>

<script type="text/javascript" src="/js/EPF.js"></script>
<script type="text/javascript" src="/js/modules/core.js"></script>
<script type="text/javascript" src="/js/modules/core/Loader.js"></script>

<script type="text/javascript" src="/js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="/js/prototip.js"></script>


<!-- YUI JS files -->
<c:if test="${not empty param.jsModules}">
	<script type="text/javascript">
	
		function randomString(string_length) {
		    var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
		    
		    var randomstring = '';
		    
		    for (var i=0; i<string_length; i++) {
		        var rnum = Math.floor(Math.random() * chars.length);
		        randomstring += chars.substring(rnum,rnum+1);
		    }
		    
		    return randomstring;
		}
	
		EPF.use(["${fn:replace(param.jsModules,',','","')}"], randomString(32), function() {
		  document.fire('loader:success');
		});
    </script>
</c:if>

<!-- Prototype Window Class -->
<script type="text/javascript" src="/js/windows_js_1.3/javascripts/window.js"></script>

<script type="text/javascript" src="/js/base64.js"></script>

<link rel="stylesheet" href="/js/windows_js_1.3/themes/default.css" type="text/css" />
<link rel="stylesheet" href="/js/windows_js_1.3/themes/alphacube.css" type="text/css" />
<link rel="stylesheet" href="/css/extras.css" type="text/css" />

</head>
<body id="${viewMode}" class="yui-skin-sam">

<!--  -->

<div id="newHeader" class="${Person == null ? 'public' : ''}">
<div class="pageWidth">
<div id="titleBar">
<div id="titleRight"><c:choose>
	<c:when test="${Person != null}">
            Logged in as <span class="strong">${Person.displayName}</span> |
            <a href="/account">Account</a>&nbsp;|
            <c:if test="${Person != null && Person.admin}">
            <a href="/admin">Portfolio Administration</a>&nbsp;|
            </c:if>
             
            <a href="/logout.do">Logout</a>
	</c:when>
	<c:otherwise>
             You are not logged in | <a href="/">Login</a>
	</c:otherwise>
</c:choose></div>
<div id="titleLeft" style="text-align: left;"><c:if
	test="${Person == null}">
	<img src="/images/loginColorBlock.gif" id="eportfolioColors" />
</c:if> <a href="/" title="Go to homepage"><img id="eportfolioLogo"
	src="/images/epfLogo.gif" /></a></div>
</div>
<c:if test="${Person != null}">
	<div id="navBar" style="text-align: left;">
	<ul id="navMenu">
		<li id="navMenuEnter">
		<div id="enterMenuTitle" class="menuTitle">Enter <img
			src="/images/dwnArWonG.gif" alt="down" /></div>
		<ul id="enterMenu" class="navDropDown dropdown dropdown-portfolio">
			<li><a href="/collection/">Manage your collection</a></li>
			<li class="dir"><a href="/community/directory">Communities</a>
			<ul>
				<li><a href="/community/directory">Community Directory</a></li>
				<li class="dir"><span>My Communities</span>
				<ul>
					<c:forEach var="community" items="${Person.communities}">
						<li><a href="/community/${community.id}"><c:out
							value="${community.name}" /></a></li>
					</c:forEach>
				</ul>
				</li>
			</ul>
			</li>
		</ul>
		</li>
		<li id="navMenuShare">
		<div id="shareMenuTitle" class="menuTitle">Share <img
			src="/images/dwnArWonG.gif" alt="down" /></div>
		<ul id="shareMenu" class="navDropDown dropdown dropdown-portfolio">
		    <c:if test="${Person != null and not Person.guest }">
			<li><a target="_blank" href="/createShare1.do">Create a portfolio</a></li>
			</c:if>
			<li><a id="viewMyPortfoliosLink" href="/share/#my">Manage
			your portfolios</a></li>
			<li><a href="/viewNewComments.do">Read shared comments</a></li>
		</ul>
		</li>
		<li id="navMenuView">
		<div id="viewMenuTitle" class="menuTitle">View <img
			src="/images/dwnArWonG.gif" alt="down" /></div>
		<ul id="viewMenu" class="navDropDown dropdown dropdown-portfolio">
			<li><a id="viewSharedPortfoliosLink" href="/share/#shared">View
			shared portfolios</a></li>

			<li><a href="/public">Search for public portfolios</a></li>
			<li><a href="/viewMyComments.do">Read comments you left</a></li>
			<c:if test="${Person != null && Person.admin}">
				<li><a href="#" id="switchUser">Switch user</a> <script>
                     $('switchUser').observe('click', function(event) {
                       new dialog.Prompt({
                         messageText: 'Enter target username',
                         onOk: function(name) {
                           
                         	window.location = '/admin/switchUser/' + Base64.encode(name);
                           	
                         }
                       }).show();
                       event.stop();
                     });
                   </script></li>
			</c:if>
			<c:if test="${sessionScope.realPerson != null}">
				<li><a href="/admin/switchBack">Switch back to
				${sessionScope.realPerson.username}</a>
			</c:if>
		</ul>
		</li>
	</ul>
	<div id="searchOuter"><jsp:include page="searchBar.jsp" /></div>
	<br class="clearboth" />
	</div>
</c:if></div>
</div>
<c:if test="${not empty epfErrors}">
	<div class="pageWidth">
	<div id="pageMessages"
		style="background-color: #CC3333; font-size: 13pt; text-align: center;">
	<ul>
		<c:forEach var="error" items="${epfErrors}">
			<li>${error}</li>
		</c:forEach>
	</ul>
	<c:remove var="epfErrors" scope="session" /></div>
	</div>
</c:if>
<div class="pageWidth"><!-- Main content begins -->
<!-- $Name:  $ -->
<!-- $Id: login.jsp,v 1.7 2011/03/24 19:03:26 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="login" pageTitle="Welcome to ePortfolio!">
	
	<c:if test="${isIE == false}">
	<div id="loginPage" style="display: none">
	</c:if>
		
	<c:if test="${isIE == true}">
	<div id="loginPage">
	</c:if>
	
	<div id="logoSection"><img id="eportfolioColors"
		src="/images/loginColorBlock.gif"> <img
		src="/images/epfLogo.gif" id="eportfolioLogo"></div>
	<div id="aboutSection">
	<p id="indexBlurb">Portfolio</p>
	</div>
	<div id="loginSection">

	<div id="guestLoginSection" class="userTypeSection">
	<div class="content">
	<h2>Users</h2>
	<p></p>
		<div class="buttons">
		
		<c:if test="${isIE == false}">
		<a id="guestLoginLink" href="#" class="awesome">Login</a>
		</c:if>
		
		<c:if test="${isIE == true}">
		<a id="guestLoginLink" href="/glogin" class="awesome">Login</a>
		</c:if>
		
		
		</div>
	</div>
	</div>
	<div id="publicLoginSection" class="userTypeSection">
	<div class="content">
	<h2>Public</h2>
	<p>Anyone can search and view Portfolios that have been made public
	</p>
	<div class="buttons"><a href="/public" class="awesome">Public
	Search</a></div>
	</div>
	</div>
	</div>
	</div>
	
	<c:if test="${isIE == false}">
	<script type="text/javascript">
    EPF.use('login', "${applicationScope['org.portfolio.project.version']}", function() {
      new EPF.controller.login.Main();
      $('loginPage').show();
    });
  	</script>
  	</c:if>
  	
</tags:portfolioPage>

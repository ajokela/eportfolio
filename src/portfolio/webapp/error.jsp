<!-- $Name:  $ -->
<!-- $Id: error.jsp,v 1.7 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<c:set var="ex"
	value="${requestScope['org.apache.struts.action.EXCEPTION']}"></c:set>

<tags:portfolioPage viewMode="general"
	pageTitle="Portfolio : Error Page">
	<div id="errorPageContent" class="MainContent">
	<h1 id="heading">Sorry! An error occurred while processing your
	request.</h1>
	<p id="headingDesc">ePortfolio has encountered an error and can't
	continue with your request. Sorry for the inconvenience! While you're
	here, you might try one of the following:</p>
	<ul id="options">
		<li><a
			href="mailto:${applicationScope['org.portfolio.portfolio.contact.email']}?subject=Error: ${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}">Send
		us an email</a> and we'll take a look at this issue right away!</li>
		<li><a href="#"
			onclick="Effect.toggle('errorReport', 'slide', { queue: 'end' });return false;">View
		the error report</a> and get the details.</li>
	</ul>
	
	<div id="goToHomepage"><a href="/">Return to your ePortfolio homepage</a></div>
	<div id="goToPrevious"><a href="${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}">Return to previous page (NOTE: This may cause an error, again)</a></div>
	<div id="goToShare"><a href="/share/">Manage your Portfolios</a></div>
	<div id="goToCollection"><a href="/collection/">Manage your Collection</a></div>
	
	<div style="display: none;" id="errorReport">
	<div><c:if test="${ex ne null}">
         ${ex}<br />
		<ul>
			<c:forEach var="traceEl" items="${ex.stackTrace}">
				<li style="list-style-type: none">${traceEl}</li>
			</c:forEach>
		</ul>
	</c:if></div>
	</div>
	</div>
</tags:portfolioPage>

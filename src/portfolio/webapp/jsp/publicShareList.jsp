<!-- $Name:  $ -->
<!-- $Id: publicShareList.jsp,v 1.6 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="view"
	pageTitle="Portfolio : View Public Portfolios">
	<div class="MainContent">
	<h1>Search for Public Portfolios</h1>
	<br />
	<br />
	<form name="publicPortfolioForm" action="/public/search" method="get"
		id="publicPortfolioForm">
	<h2>Search Public Portfolios:</h2>
	<input type="text" name="searchQuery" id="searchQuery" size="64" /> 
	   
	   <input
		id="viewPublic" type="submit" value="Search" class="btn" />
	<br /><span style="font-size: 10pt;">Search using a person's
	first and last name or email address, Portfolio tags or
	titles, and more</span></form>

	<br />
	<br />
	<h1>Public Portfolios <c:if test="${not empty searchQuery}">
					with <i><c:out value="${searchQuery}" /></i>
		</c:if></h1>
		<div class="Instructions">
		<p>Listed below are public portfolios matching your query. Click
		one of the portfolio links to view it.</p>
		</div>
		<div class="share">
		<table class="data"
			summary="This table displays portfolios found for the search"
			style="width: 100%">
			<tr>
				<th>Name/Description</th>
				<th>Created</th>
				<th>Expires</th>
				<th>Link</th>
			</tr>
			<c:choose>
				<c:when test="${not empty shares}">
					<c:forEach var="share" items="${shares}" varStatus="status">
						<tr class="r${status.count % 2}">
							<!-- for each <td>  if rowColorCount%2==0, <td class="evenRow"> otherwise <td class="oddRow"> -->
							<td><strong><c:out value="${share.shareName}" /> </strong>
							<br />
							<c:out value="${share.shareDesc}" /> <br />
							<a target="_blank" href="/portfolio/${share.shareId}">http://portfolio.umn.edu/portfolio/${share.shareId}</a>
							</td>
							<td><fmt:formatDate value="${share.dateCreated}"
								pattern="d MMM yyyy" /></td>
							<td><fmt:formatDate value="${share.dateExpire}"
								pattern="d MMM yyyy" /></td>
							<td><a target="_blank" href="/portfolio/${share.shareId}">View</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
		</div>
	</div>
</tags:portfolioPage>








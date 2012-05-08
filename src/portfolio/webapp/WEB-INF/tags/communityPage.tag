<%@ tag body-content="scriptless"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="community" required="true"
	type="org.portfolio.model.community.Community"%>
<%@ attribute name="pageTitle" required="true"%>
<%@ attribute name="returnTo" required="false"%>
<%@ attribute name="cssClass" required="false"%>
<%@ attribute name="jsModules" required="false"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:portfolioPage viewMode="enter" jsModules="${jsModules}"
	pageTitle="${pageTitle}">
	<div class="MainContent community ${cssClass}" id="${id}">
	<h1 id="communityHeader"><a href="/community/${community.id}"><c:out
		value="${community.name}" /></a> <img alt="right"
		src="/images/rtArBonW_11.gif"> <span>${pageTitle}</span></h1>
	<jsp:doBody /> <c:if test="${not empty returnTo}">
		<div class="returnTo">Return to <c:choose>
			<c:when test="${returnTo eq 'editCommunity'}">
				<a href="/editCommunity.do?communityId=${community.id}">Manage
				Community</a>
			</c:when>
			<c:when test="${returnTo eq 'communityReports'}">
				<a href="/communityReports.do?communityId=${community.id}">Community
				Reports</a>
			</c:when>
			<c:otherwise>
				<a href="/community/${community.id}">Community Home</a>
			</c:otherwise>
		</c:choose></div>
	</c:if></div>
</tags:portfolioPage>

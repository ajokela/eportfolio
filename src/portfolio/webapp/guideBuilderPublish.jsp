<!-- $Name:  $ -->
<!-- $Id: guideBuilderPublish.jsp,v 1.6 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Collection Guide Builder" id="guideBuilderPublish">
	<h2>Save and Publish</h2>
	<p>Before your new collection guide is available, it must be
	published. You are encouraged to carefully check all the details of
	your collection guide before publishing it because once published it
	cannot be edited without compromising the data integrity (deleting an
	element will remove elements from the user collection guides).</p>
	<p>All progress on your collection guide has been saved so far. If
	you'd like to return at a later point to publish the guide, you will
	find it in the &quot;Unpublished&quot; section of your community's
	&quot;Collection Guides&quot; area.</p>
	<form action="guideBuilderPublish.do" method="post"><input
		type="hidden" name="method" value="togglePublish" /> <input
		type="hidden" name="guideId" value="${guide.id}" /> <c:choose>
		<c:when test="${guide.published}">
			<p><em>Your collection guide has been published.</em></p>
			<input type="submit" name="unpublish"
				value="Unpublish collection guide">
        or
        <a href="/community/${guide.communityId}">Return to
			community home</a>
		</c:when>
		<c:otherwise>
			<p><em>Your collection guide has not been published.</em></p>
			<input type="submit" name="publish" value="Publish collection guide">
        or
        <a href="community/${guide.communityId}">Save collection
			guide and publish later</a>
		</c:otherwise>
	</c:choose></form>
	<br />
	<br />
	<hr class="clearboth" />
	<br />
	<br />
	<input type="button" value="Prev: Sections and elements"
		onclick="window.location='guideBuilderSections.do?method=edit&guideId=${guide.id}'">
</tags:communityPage>

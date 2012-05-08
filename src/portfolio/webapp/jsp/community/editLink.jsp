<!-- $Name:  $ -->
<!-- $Id: editLink.jsp,v 1.6 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:portfolioPage viewMode="enter" pageTitle="Edit Link">
	<div class="MainContent community" id="editLink">
	<h1 id="communityHeader">${community.name} <img alt="right"
		src="/images/rtArBonW_11.gif"> <span>Edit Link</span></h1>
	<div id="editLinkDiv" class="commEditLinkForm">
	<div>
	<form action="/communityLink.do" class="newForm narrowForm"
		method="post"><input type="hidden" name="method" value="update" />
	<input type="hidden" name="entryKeyId" value="${link.entryKeyId}" />
	<fieldset>
	<ul>
		<li><label for="name">Name:</label> <input type="text"
			name="name" id="name" class="full" value="${link.entryName}" /></li>
		<li><label for="url">URL:</label> <input type="text" name="url"
			id="url" class="full" value="${link.url}" /></li>
		<li><label for="description">Description:</label> <textarea
			name="description" id="description" class="full">${link.description}</textarea></li>
	</ul>
	</fieldset>
	<fieldset class="submitset"><input type="submit"
		value="Save" />&nbsp;or <a
		href="/communityLink.do?communityId=${community.id}">cancel</a></fieldset>
	</form>
	</div>
	</div>
	<div class="returnTo">Return to <a
		href="/community/${community.id}">Community Home</a> &gt;&gt; <a
		href="/editCommunity.do?communityId=${community.id}">Edit
	Community</a> &gt;&gt; <a
		href="/communityLink.do?communityId=${community.id}">Edit Links</a></div>
	</div>
</tags:portfolioPage>

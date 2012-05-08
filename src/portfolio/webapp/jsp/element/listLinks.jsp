<!-- $Name:  $ -->
<!-- $Id: listLinks.jsp,v 1.12 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="listLinks">
<div class="header">
<p>Select one or more links to attach to your element.</p>
</div>
<div class="searchLinks">
<div class="wrapper"><label>Search</label> <input type="text" />
<img src="/images/fugue/icon_shadowless/cross_bw.png" /></div>
<div class="numLinks"></div>
</div>
<form class="attachLinksForm">
<div class="contents">
<ul>
	<c:forEach var="link" items="${links}" varStatus="status">
		<li class="${status.index % 2 == 0 ? 'even' : 'odd'} linkItem">
		<ul class="attachLinkDetails">
			<li class="attachLinkCheck"><input type="checkbox"
				name="attachment" value="${link.entryKey.id}"></li>
			<li class="attachLinkName"><c:out value="${link.entryName}" /></li>
			<li class="attachLinkUrl"><a href="${link.url}" target="_blank"><c:out
				value="${link.url}" /></a></li>
		</ul>
		</li>
	</c:forEach>
</ul>
</div>
<div class="actions"><input type="submit" value="Attach" /> <a
	href="#" class="attachLinksCancel">cancel</a></div>
</form>
</div>

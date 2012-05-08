<!-- $Name:  $ -->
<!-- $Id: shareheader.jsp,v 1.4 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<ul id="shareSteps">
	<li class="${param.step eq '1' ? 'current' : ''}"><c:choose>
		<c:when test="${param.new}">
			<div class="stepNoLink">1: Set Up Portfolio</div>
		</c:when>
		<c:otherwise>
			<a href="javascript:goBack('createShare1');">1: Set Up Portfolio</a>
		</c:otherwise>
	</c:choose></li>
	<li class="${param.step eq '2' ? 'current' : ''}"><c:choose>
		<c:when test="${param.new}">
			<div class="stepNoLink">2: Choose Content</div>
		</c:when>
		<c:otherwise>
			<a href="javascript:goBack('createShare2');">2: Choose Content</a>
		</c:otherwise>
	</c:choose></li>
	<li class="${param.step eq '3' ? 'current' : ''}"><c:choose>
		<c:when test="${param.new}">
			<div class="stepNoLink">3: Arrange Content</div>
		</c:when>
		<c:otherwise>
			<a href="javascript:goBack('createShare2a');">3: Arrange Content</a>
		</c:otherwise>
	</c:choose></li>
	<li class="${param.step eq '4' ? 'current' : ''}"><c:choose>
		<c:when test="${param.new}">
			<div class="stepNoLink">4: Choose Style</div>
		</c:when>
		<c:otherwise>
			<a href="javascript:goBack('createShareSelectStyle');">4: Choose
			Style</a>
		</c:otherwise>
	</c:choose></li>
	<li class="${param.step eq '5' ? 'current' : ''}"><c:choose>
		<c:when test="${param.new}">
			<div class="stepNoLink">5: Add Tags</div>
		</c:when>
		<c:otherwise>
			<a href="javascript:goBack('createShareAddTags');">5: Add Tags</a>
		</c:otherwise>
	</c:choose></li>
</ul>

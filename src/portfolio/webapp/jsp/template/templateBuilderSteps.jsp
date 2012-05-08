<!-- $Name:  $ -->
<!-- $Id: templateBuilderSteps.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<ul id="shareSteps">
	<li class="${param.step eq 'name' ? 'current' : ''}">
	<div class="stepNoLink">1: Name &amp; Description</div>
	</li>
	<li class="${param.step eq 'content' ? 'current' : ''}">
	<div class="stepNoLink">2: Content</div>
	</li>
	<!-- If the format is outcome or rubric, skip steps 3 - 5 -->
	<li class="${param.step eq 'assessment' ? 'current' : ''}">
	<div class="stepNoLink">3: Assessment</div>
	</li>
	<li class="${param.step eq 'publish' ? 'current' : ''}">
	<div class="stepNoLink">4: Publish</div>
	</li>
</ul>

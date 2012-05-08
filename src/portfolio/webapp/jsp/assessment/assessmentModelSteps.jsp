<!-- $Name:  $ -->
<!-- $Id: assessmentModelSteps.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<ul id="shareSteps">
	<li class="${param.step eq '1' ? 'current' : ''}">
	<div class="stepNoLink">1: Name &amp; Description</div>
	</li>
	<li class="${param.step eq '2' ? 'current' : ''}">
	<div class="stepNoLink">2: Scoring Model</div>
	</li>
	<!-- If the format is outcome or rubric, skip steps 3 - 5 -->
	<c:if test="${param.format ne 'basic'}">
		<li class="${param.step eq '3' ? 'current' : ''}">
		<div class="stepNoLink">3: Objectives</div>
		</li>
		<li class="${param.step eq '4' ? 'current' : ''}">
		<div class="stepNoLink">4: Order Objectives</div>
		</li>
		<!-- If the format is outcome, skip step 5 -->
		<c:if test="${param.format eq 'rubric'}">
			<li class="${param.step eq '5' ? 'current' : ''}">
			<div class="stepNoLink">5: Descriptors</div>
			</li>
		</c:if>
	</c:if>
</ul>

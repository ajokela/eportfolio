<!-- $Name:  $ -->
<!-- $Id: communityDetails.jsp,v 1.5 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:portfolioPage viewMode="enter" pageTitle="Community Details">
	<div class="MainContent">
	<h1 id="communityHeader">${empty community ? 'New Community' :
	community.name} <img alt="right" src="/images/rtArBonW_11.gif"> <span>Community
	Details</span></h1>
	<form method="post" action="community.do" class="newForm narrowForm"
		id="theForm"><input type="hidden" name="communityId"
		value="${community.id}"> <input type="hidden" name="method"
		value="save">
	<fieldset>
	<ul>
		<li><label for="name">Name:</label> <input type="text"
			name="name" id="name" class="full" value="${community.name}" /></li>

		<li><label for="description">Description:</label> <textarea
			name="description" id="description" rows="4" class="full">${community.description}</textarea>
		</li>

		<li><label for="program">Program:</label> <input type="text"
			name="program" id="program" class="full" value="${community.program}" />
		</li>

		<li><label for="campusCode">Campus:</label> <select
			name="campusCode" id="campusCode" class="full">
			<c:forEach var="campusEntry" items="${campusCodeMap}">
				<option value="${campusEntry.key}" ${not empty community and
					campusEntry.key eq community.campusCode ? 'selected="selected"' : ''}>
				${campusEntry.value}</option>
			</c:forEach>
		</select></li>
		<li><label>Type:</label> <select name="type">
			<c:forEach var="type" items="${types}">
				<option value="${type}" ${type eq
					community.type ? 'selected="selected"' : ''}>${type.label}</option>
			</c:forEach>
		</select></li>
		<li><label>Private community?</label>
		<div class="radioGroup"><input type="radio"
			name="privateCommunity" value="true" id="privateCommunityTrue"
			${community.privateCommunity ? 'checked="checked" ' : ''}/> <label
			for="privateCommunityTrue">Yes</label> <input type="radio"
			name="privateCommunity" value="false" id="privateCommunityFalse"
			${!community.privateCommunity ? 'checked="checked" ' : ''}/> <label
			for="privateCommunityFalse">No</label></div>
		</li>
	</ul>
	</fieldset>
	<fieldset class="submitSet"><input type="submit" name="save"
		value="Save"> &nbsp;or <c:choose>
		<c:when test="${empty community}">
			<a href="/community/directory">cancel</a>
		</c:when>
		<c:otherwise>
			<a href="/editCommunity.do?communityId=${community.id}">cancel</a>
		</c:otherwise>
	</c:choose></fieldset>
	</form>
	<script type="text/javascript">
		  $('theForm').observe('submit', function(event){
		    if ($F(this.description).length > 255) {
	        new dialog.Alert('Maximum length for Description exceeded. actual: ' + $F(this.description).length + ', ' + 'maximum: 255').show();
	        event.stop();
	        return;
		    }
		  });
		</script></div>
</tags:portfolioPage>

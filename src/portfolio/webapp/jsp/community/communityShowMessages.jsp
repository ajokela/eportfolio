<!-- $Name:  $ -->
<!-- $Id: communityShowMessages.jsp,v 1.2 2011/01/12 17:28:25 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" jsModules="button,dhtmlcalendar,menu" pageTitle="Community Messages" id="communityMessgaes" returnTo="Community">
	<div class="MainContent communityHome yui-skin-sam" id="communityInfo" style="width: 100%;">
                
		<div style="display: block; float: left; width: 10px; padding: 1px; margin: 1px 1px 1px 1px;">&nbsp;</div>
		<div style="display: block; float: left; width: 20%; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Coordinator</div>
		<div style="display: block; float: left; width: 30%; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Subject</div>
		<div style="display: block; float: left; width: 10%; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Date Sent</div>
		<div style="display: block; float: left; width: 10%; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Expires</div>
		
		<div style="clear: both;"></div>
		
		<c:forEach var="message" items="${messages}" varStatus="rowCounter">
			<c:choose>
				<c:when test="${rowCounter.count % 2 == 0}">
	            	<c:set var="rowStyle" scope="page" value="#dddddd"/>
	          	</c:when>
	          	<c:otherwise>
	            	<c:set var="rowStyle" scope="page" value="#ffffff"/>
	          	</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${message.read == true}">
					<c:set var="weight" scope="page" value="normal" />
				</c:when>
				<c:otherwise>
					<c:set var="weight" scope="page" value="bold" />
				</c:otherwise>
			</c:choose>
			          
			<div style="display: block; float: left; width: 10px; padding: 1px;">${rowCounter.count}</div>
			<div id="msg_coord_${message.id}" style="display: block; float: left; width: 20%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">${message.coordinator.displayName}</div>
			<div id="msg_subject_${message.id}" style="display: block; float: left; width: 30%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">
				<a href="#" id="message_${message.id}">${fn:escapeXml(message.subject)}</a>
			
				<script type="text/javascript">
				
	                $('message_${message.id}').observe('click', function(event) {
	                	
	                	var width =  640; // document.body.clientWidth * 0.80;
	                	var height = 480; // document.body.clientHeight * 0.80;
	                	
						var win = new Window({url: '/community/message/${community.id}/${message.id}', options: {method: 'get'}}, {className: "alphacube", title: '${fn:escapeXml(message.subject)}'});
						
						win.setSize(width, height);
						win.setDestroyOnClose();
						win.toFront();
						
						win.showCenter(true);
						
						$('msg_coord_${message.id}').setStyle({fontWeight: 'normal'});
						$('msg_subject_${message.id}').setStyle({fontWeight: 'normal'});
						$('msg_started_at_${message.id}').setStyle({fontWeight: 'normal'});
						$('msg_expires_${message.id}').setStyle({fontWeight: 'normal'});
						
	                });
                </script>
			
			
			</div>
			<div id="msg_started_at_${message.id}" style="display: block; float: left; width: 10%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};"><fmt:formatDate value="${message.startedAt}" pattern="MM/dd/yyyy"/></div>
			<div id="msg_expires_${message.id}" style="display: block; float: left; width: 10%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">
				
				<c:if test="${message.endedAt != null}">
					<fmt:formatDate value="${message.endedAt}" pattern="MM/dd/yyyy"/>
				</c:if>
				
				<c:if test="${message.endedAt == null}">
					Never
				</c:if>
				
			</div>
			
			<div style="clear: both;"></div>
			
		</c:forEach>
	</div>
</tags:communityPage>
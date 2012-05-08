<!-- $Name:  $ -->
<!-- $Id: UploadedMaterial.jsp,v 1.15 2011/01/11 21:36:18 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<div>
<c:choose>
	<c:when test="${element.image}">
		<c:set var="url" value="/photo/${element.entryKeyId}?portfolioId=${portfolio.shareId}" />
			
		<div style="display: block; padding: 10px;">
			
		<tags:elementField>
			<a href="${url}" target="_blank"><img src="${url}&width=120&height=120" alt="thumbnail" /></a>
		</tags:elementField>
		
		</div>
		
		<div style="clear: both;"></div>
		
		<div>
		<tags:elementField heading="Title">
			<a href="${url}" target="_blank">${element.entryName}</a>
		</tags:elementField>
		</div>
		
	</c:when>
	<c:otherwise>
		<c:set var="url" value="/file/${element.entryKey.id}?portfolioId=${portfolio.shareId}" />
			
		<div>
			
			<c:if test="${element.audio}">
				<tags:elementField>
					<embed
						src="https://www.google.com/reader/ui/3523697345-audio-player.swf"
						flashvars="audioUrl=${org.portfolio.portfolio.base.url}${url}"
						width="400" height="27"
						pluginspage="http://www.macromedia.com/go/getflashplayer"></embed>
				</tags:elementField>
			</c:if>
		
		</div>
		<div style="clear: both;"></div>
		<div>
		
			<tags:elementField heading="Title">
				<a href="${url}">${element.entryName}</a>
			</tags:elementField>
		
		</div>
		
	</c:otherwise>
</c:choose>
</div>
<div style="clear: both;"></div>
<div>
<tags:elementField heading="File Name">${element.shortFileName}</tags:elementField>
<tags:elementField heading="Description">${element.description}</tags:elementField>
<tags:elementField heading="Size">${epf:formatFileSize(element.size)}</tags:elementField>
</div>

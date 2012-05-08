<!-- $Name:  $ -->
<!-- $Id: listPhotos.jsp,v 1.10 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="listPhotos">
<div class="header">
<p>Select one or more photos to attach to your element.</p>
</div>
<div class="searchPhotos">
<div class="wrapper"><label>Search</label> <input type="text" />
<img src="/images/fugue/icon_shadowless/cross_bw.png" /></div>
<div class="numPhotos"></div>
</div>
<form class="attachPhotosForm">
<div class="contents">
<ul class="photosList">
	<c:forEach var="photo" items="${photos}">
		<li class="photoItem">
		<div class="thumbnailWrapper"><img
			src="/photo/${photo.entryKey.id}?maxWidth=75&maxHeight=75" /> <input
			type="checkbox" name="attachment" value="${photo.entryKey.id}">
		</div>
		<div class="photoName"><span><c:out
			value="${photo.entryName}" /></span></div>
		</li>
	</c:forEach>
</ul>
</div>
<div class="actions"><input type="submit" value="Attach" /> <a
	href="#" class="attachPhotosCancel">cancel</a></div>
</form>
</div>

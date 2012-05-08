<!-- $Name:  $ -->
<!-- $Id: listFiles.jsp,v 1.11 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="listFiles">
<div class="header">
<p>Select one or more files to attach to your element.</p>
</div>
<div class="searchFiles">
<div class="wrapper"><label>Search</label> <input type="text" />
<img src="/images/fugue/icon_shadowless/cross_bw.png" /></div>
<div class="numFiles"></div>
</div>
<form class="attachFilesForm">
<div class="contents">
<ul>
	<c:forEach var="file" items="${files}" varStatus="status">
		<li class="${status.index % 2 == 0 ? 'even' : 'odd'} fileItem">
		<ul class="attachFileDetails">
			<li class="attachFileCheck"><input type="checkbox"
				name="attachment" value="${file.entryKey.id}"></li>
			<li class="attachFileName"><c:out value="${file.entryName}" /></li>
			<li class="attachFileSize">${epf:formatFileSize(file.size)}</li>
		</ul>
		</li>
	</c:forEach>
</ul>
</div>
<div class="actions"><input type="submit" value="Attach" /> <a
	href="#" class="attachFilesCancel">cancel</a></div>
</form>
</div>

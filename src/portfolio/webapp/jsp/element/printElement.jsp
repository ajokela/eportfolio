<!-- $Name:  $ -->
<!-- $Id: printElement.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<%@ page import="org.portfolio.util.Configuration"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<link rel="shortcut icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/css/modules/core.css" />

<script type="text/javascript" src="/js/prototype.js"></script>
<script type="text/javascript"
	src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls"></script>
<script type="text/javascript"
	src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js"></script>
<script type="text/javascript" src="/js/EPF.js"></script>
<script type="text/javascript" src="/js/modules/core.js"></script>

<style type="text/css">
body {
	margin: 0;
	width: 600px;
}

.printElementList {
	margin: 0;
	padding: 0;
}

.printElementList>li {
	border-bottom: 3px solid #999999;
	list-style-type: none;
	margin-bottom: 5px;
	padding-bottom: 10px;
}
</style>
</head>
<body>

<div class="MainContent yui-skin-sam" id="viewElement"><c:if
	test="${param.action eq 'view' && param.viewType eq 'AP'}">
	<p style="margin-left: 15px">You are viewing the record of <strong><c:out
		value="${advisee.firstName}" /> <c:out value="${advisee.lastname}" /></strong></p>
</c:if>
<ul class="printElementList">
	<c:forEach var="entry" items="${entries}">
		<li>
		<div class="viewElement">
		<div class="elementPanel">
		<div class="hd">
		<div class="entryType"><img
			src="${entry.elementDefinition.iconPath}" />${entry.elementDefinition.name}
		</div>
		</div>
		<div class="bd">
		<div class="entryName">${fn:escapeXml(entry.entryName)}</div>
		<dl>
			<c:set var="element" value="${entry}" scope="request" />
			<jsp:include page="/jsp/render/elements/${entry.shortClassName}.jsp" />
		</dl>
		</div>
		</div>

		<c:if test="${not empty entry.tags}">
			<div class="tagsSection">
			<div class="hd">Tags</div>
			<div class="bd">
			<ul class="elementTagList">
				<c:forEach var="tag" items="${entry.tags}">
					<li><span>${tag}</span></li>
				</c:forEach>
			</ul>
			</div>
			</div>
		</c:if> <c:if test="${not empty entry.attachments}">
			<div class="attachmentsSection">
			<div class="hd">Attachments</div>
			<div class="bd">
			<ul class="thumbnails">
				<c:forEach var="photo" items="${entry.photoAttachments}">
					<li class="photo">
					<div class="thumbnailWrapper"><img alt="photo"
						src="/photo/${photo.entryKeyId}?maxWidth=100&maxHeight=100" /></div>
					<div class="name">${photo.entryName}</div>
					</li>
				</c:forEach>
			</ul>
			<ul class="details">
				<c:forEach var="file" items="${entry.fileAttachments}">
					<li class="file"><span class="entryKeyId"
						style="display: none">${file.entryKeyId}</span>

					<div class="name">${file.entryName}</div>
					<div class="path">${file.fileName}</div>
					</li>
				</c:forEach>
				<c:forEach var="link" items="${entry.linkAttachments}">
					<li class="link"><span class="entryKeyId"
						style="display: none">${link.entryKeyId}</span>

					<div class="name">${link.entryName}</div>
					<div class="path"><a href="${link.url}" target="_blank">${link.url}</a></div>
					</li>
				</c:forEach>
			</ul>
			</div>
			</div>
		</c:if></div>
		</li>
	</c:forEach>

</ul>
</div>
<script type="text/javascript">
  EPF.use("elementWindow-css", "${applicationScope['org.portfolio.project.version']}", function() {
    window.print();
  });
</script>
</body>
</html>

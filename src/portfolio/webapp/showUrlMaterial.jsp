<!-- $Name:  $ -->
<!-- $Id: showUrlMaterial.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ include file="taglibs.jsp"%>

<html>
<head>
<script language="JavaScript" src="js/portfolio.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="css/main.css" type="text/css" />
<link rel="stylesheet" href="css/enter.css" type="text/css" />
<title>Portfolio : Upload Materials - URL Information</title>
</head>
<body>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="53" background="/images/header_background_enter.gif"><img
			src="/images/IDs_enter.gif" width="354" height="53" alt=""></td>
	</tr>
</table>
<table>
	<tr>
		<td height="300" align="left" Class="MainContent">

		<h2>Upload Materials : <abbr
			title="Uniform Resource Locator: The address or location of a website">URL</abbr></h2>

		<a href="<c:out value="${materialForm.name}"/>" target="_blank"><img
			src="/images/fileUrl.gif" border="0"
			alt="Click to view the website in a new window.">
		<p>(Click image to view)</p>
		</a>

		<h3>URL:</h3>
		<p><c:out value="${materialForm.name}" /></p>

		<h3>Material Name:</h3>
		<p><c:out value="${materialForm.sampleName}" /></p>

		<h3>Description: (Limit 50 words)</h3>
		<p><c:out value="${materialForm.description}" /></p>

		<h3>Author:</h3>
		<p><c:out value="${materialForm.author}" /></p>

		<br />
		<br />
		<br />
		<a href="javascript:window.close()"><img
			src="/images/close_red.gif" width="65" height="19" alt="close window"></a>

		</td>
	</tr>
</table>
</body>
</html>

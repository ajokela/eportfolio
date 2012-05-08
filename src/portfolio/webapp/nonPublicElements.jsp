<!-- $Name:  $ -->
<!-- $Id: nonPublicElements.jsp,v 1.6 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ page import="org.portfolio.model.Person"%>

<html>
<head>
<title>Portfolio: Element Map</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="js/portfolio.js"></script>
<link rel="stylesheet" type="text/css" href="css/enter.css">
<link rel="stylesheet" type="text/css" href="css/share.css">
<style>
.text {
	font-size: .80em;
}

.PS,.UM,.self {
	font-weight: bold;
	color: a82f39;
}

.titleBG {
	color: #ffffff;
}
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="5" topmargin="5"
	marginwidth="5" marginheight="5" onLoad="self.focus();">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td height="53" background="/images/header_background_enter.gif"><img
			src="/images/IDs_view.gif" width="354" height="53" alt=""></td>
	</tr>
</table>
<br />
<table width="565" border="0" cellspacing="0" cellpadding="0"
	summary="This table contains a list of Non Public Portfolio Assets"
	class="MainContent">
	<tr>
		<td bgcolor="#33792c">
		<table border="0" cellpadding="0" cellspacing="0" width="570">
			<tr>
				<td
					style="font: bold 14px Arial, Helvetica, sans-serif; color: ffffff; padding: 3px 0px 3px 6px;">ELEMENTS
				NOT SHARABLE PUBLICLY</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr valign="top" align="center">
		<td colspan="2"><img src="/images/spacer.gif" width="1" height="8"
			border="0" alt="&nbsp;"></td>
	</tr>
	<tr>
		<td class="xfooter" style="padding-left: 30px;">
		<ul style="font-size: 87%;">
			<li>Grades</li>
			<li>Courses taken</li>
			<li>Schedule</li>
			<li>Test scores</li>
			<li>Advising records</li>
			<li>Educational services received</li>
			<li>Disciplinary actions</li>
			<li>Social Security Number</li>
			<li>Student ID Number</li>
		</ul>
		</td>
	</tr>

	<tr valign="top" align="center">
		<td><img src="/images/spacer.gif" width="1" height="8" border="0"
			alt="&nbsp;"></td>
	</tr>
	<tr valign="top">
		<td align="center"><!--<a href="javascript:window.close();"><img src="/images/close_red.gif" width="65" height="19" alt=""></a>-->
		<input type="button" class="btn" value="Close" name="close"
			onclick="window.close();" /></td>
	</tr>
</table>
</body>
</html>

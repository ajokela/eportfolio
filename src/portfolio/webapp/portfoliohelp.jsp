<!-- $Name:  $ -->
<!-- $Id: portfoliohelp.jsp,v 1.6 2010/11/23 20:34:57 ajokela Exp $ -->

<%@ page import="edu.umn.web.portfolio.dbobject.HelpDataDef"%>
<%@ page isErrorPage="true"%>


<%
  HelpDataDef helpDef = views.getHelpData(request, null);
%>


<html>
<head>
<title>Portfolio : Portfolio Help: Interest Areas</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="css/enter.css" type="text/css">
<script language="JavaScript" src="js/portfolio.js"></script>
</head>
<body bgcolor="#FFFFFF" marginheight="0" marginwidth="0" topmargin="0"
	leftmargin="0">
<table width="99%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td rowspan="2" background="img/bg.gif"><img
			src="img/page_leftTop3.gif" width="190" height="70" alt="&nbsp;"></td>
		<td align="left" background="img/bg.gif" height="54" width="100%">
		<font face="arial, helvetica, sans-serif" size="4" color="#CCD6E3">Help</font>
		</td>
	</tr>
	<tr>
		<td align="right" background="img/page_top2.gif" height="16"><img
			src="img/page_top2.gif" width="163" height="16" alt="&nbsp;"><img
			src="img/page_rightTop2.gif" width="13" height="16" alt="&nbsp;"></td>
	</tr>
</table>
<table width="99%" border="0" cellspacing="0" cellpadding="0">
	<tr valign="top">
		<td background="img/page_left.gif"><img src="img/page_left.gif"
			width="12" height="350" alt="&nbsp;"></td>
		<td bgcolor="#FFFFFF" align="left" width="100%"><img
			src="/images/spacer.gif" width="20" height="1" alt="&nbsp;"> <font
			face="arial, helvetica, sans-serif" size="4" color="#0099CC"><b><%= helpDef.getTITLE() %></b></font>
		<blockquote><font face="Verdana, Arial, sans-serif"
			size="2" color="#333333"> <%= helpDef.getHELP_TEXT() %> </font></blockquote>
		<hr noshade width="100%" size="1">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><span class="bodyLinks"><a
					href="portfoliohelp.jsp?page=<%= helpDef.getPREV_TOPIC() %>">PREVIOUS</a></span>
				</td>
				<td align="center"><span class="bodyLinks"><a
					href='javascript:window.close()'>CLOSE THIS WINDOW</a></span></td>
				<td align="right"><span class="bodyLinks"><a
					href="portfoliohelp.jsp?page=<%= helpDef.getNEXT_TOPIC() %>">NEXT</a></span>
				</td>
			</tr>
		</table>
		</td>
		<td background="img/page_right.gif"><img src="img/page_right.gif"
			width="13" height="300" alt="&nbsp;"></td>
	</tr>
	<tr>
		<td><img src="img/page_leftBottom.gif" width="12" height="8"
			alt="&nbsp;"></td>
		<td colspan="2" align="right" background="img/page_bottom2.gif"><img
			src="img/page_rightBottom.gif" width="13" height="8" alt="&nbsp;"></td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
</table>
<map name="helpBanner">
	<area shape="rect" coords="296,11,371,28" target="_self"
		alt="Help Topics" href="/">
	<area shape="rect" coords="398,11,486,28" alt="Close Window" href="#"
		onClick="window.close()">
</map>
</body>
</html>

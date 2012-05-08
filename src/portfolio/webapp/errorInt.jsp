<!-- $Name:  $ -->
<!-- $Id: errorInt.jsp,v 1.4 2010/11/23 20:34:57 ajokela Exp $ -->

<%@ page import="java.util.*"%>

<html>
<head>
<title>Portfolio : Error Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="css/enter.css" type="text/css">
<script language="JavaScript" src="js/portfolio.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="10"
	marginwidth="0" marginheight="10">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="1%"><img src="/images/spacer.gif" width="20" height="1"
			border="0" alt="&nbsp;"></td>
		<td width="98%">
		<table width="100%" border="0" cellpadding="5" cellspacing="0">
			<tr align="left">
				<td bgcolor="#3399FF">
				<table width="100%" border="0" cellspacing="0" cellpadding="1"
					align="center">
					<tr>
						<td width="100%" bgcolor="#FFFFFF"></td>
					</tr>
					<tr bgcolor="#99CCFF">
						<td class="ltBlue"><img src="/images/spacer.gif" width="1"
							height="3" border="0" alt="&nbsp;"></td>
					</tr>
					<tr>
						<td width="99%" bgcolor="#FFFFFF" valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr>
								<td width="98%" class="Error" style="white-space: nowrap;">
								You have reached this page as a result of the following:</td>
							</tr>
							<tr>
								<td width="98%" class="BodyStyle"><br />
								<blockquote>
								<%
                                            String exception = request.getParameter( "exception" );

											if ( exception != null && exception.length() > 0 )
												 out.println( "<img src=\"/images/error.gif\" width=\"28\" height=\"24\" alt=\" \" border=\"0\" align=\"absmiddle\"><span class=\"smError\">" + exception + "</span><br/><br/>" );


                                               String reason = request.getParameter( "reason" );
                                                  if ( ( reason != null ) && reason.equals ( "getfile" ) ) {
                                                     out.println("<span class=\"Label\">&middot;&nbsp;&nbsp;There may be a problem with the file you uploaded.</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;Please see <a href=\"#\" onClick=\"openMyWindow('uploadFileInfo.html')\"> Uploading tips</a> for more information.");
											 }
                                         %> <!-- here --> &nbsp;</blockquote>
								</td>
							</tr>
							<tr>
								<td width="98%" class="text">
								<hr width="100%" size="1" noshade="noshade">
								<blockquote>Please give detailed information when
								reporting any problems encountered while using
								Portfolio.&nbsp;&nbsp;Problems should be reported to your campus
								<a href="javascript:openTipWindow('faq.html#helpDesk')">help
								desk</a>.<br />
								<br />
								<span class="textBG">Be prepared to provide the following
								information:</span><br />
								&middot; what were the exact steps taken when the problem
								occurred<br />
								&middot; what (if any) error messages were received<br />
								&middot; what time the problem occurred<br />
								&middot; what web browser and version were you using<br />
								</blockquote>
								</td>
							</tr>
							<tr>
								<td width="98%"><br />
								<input type="button" name="cancel" class="btn" value="Close"
									onclick="window.close();" /></td>
							</tr>
							<tr>
								<td width="98%" class="Text">&nbsp;</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
		</td>
		<td width="1%"><img src="/images/spacer.gif" width="20" height="1"
			border="0" alt="&nbsp;"></td>
	</tr>
</table>
</body>
</html>

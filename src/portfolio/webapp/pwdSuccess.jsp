<!-- $Name:  $ -->
<!-- $Id: pwdSuccess.jsp,v 1.4 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ page
	import="java.lang.*,
                 org.portfolio.model.Person"%>
<%@ page import="java.util.*"%>



<%@ include file="pageHeader.jsp"%>
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
					<tr bgcolor="#FFFFFF">
						<td width="100%" colspan="4">
						<table width="100%" border="0" cellspacing="0" cellpadding="1">
							<tr>

								<td class="PageTitle" height="38">&nbsp;Portfolio Password
								Changed</td>
							</tr>
							<tr bgcolor="#99CCFF">
								<td class="ltBlue"><img src="/images/spacer.gif" width="1"
									height="3" border="0" alt="&nbsp;"></td>
							</tr>
							<tr>
								<td class="text"><br />
								<br />
								<span class="headTitle">You have successfully chosen a
								new Portfolio password.</span><br />
								<br />
								<a href="accountInfo.do"><br />
								Return to Account Information</a><br />
								&nbsp;</td>
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
<%@ include file="pageFooter.jsp"%>

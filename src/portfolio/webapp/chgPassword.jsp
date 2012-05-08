<!-- $Name:  $ -->
<!-- $Id: chgPassword.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="Portfolio : Change Password">
	<form method="POST" action="changePassword.do"><input
		type="hidden" name="action" value="save" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="1%"><img src="/images/spacer.gif" width="20"
				height="1" border="0" alt="&nbsp;"></td>
			<td width="98%">
			<table width="100%" border="0" cellpadding="5" cellspacing="0">
				<tr align="left">
					<td bgcolor="#3399FF">
					<table width="100%" border="0" cellspacing="0" cellpadding="1"
						align="center">
						<tr>
							<td colspan="4" class="PageTitle" bgcolor="#FFFFFF">&nbsp;Change
							Password</td>
						</tr>
						<tr bgcolor="#99CCFF">
							<td colspan="4" class="ltBlue"><img src="/images/spacer.gif"
								width="1" height="3" border="0" alt="&nbsp;"></td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td width="100%" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="text"><a name="skip"></a>
									<table width="100%" border="0" cellspacing="0" cellpadding="2">
										<tr valign="top">
											<td class="Instructions" colspan="2">Enter your old
											password then choose your new password.&nbsp;&nbsp;Click <span
												class="InstructionsBold">save</span> when you are done.</td>
										</tr>
										<tr valign="top">
											<td class="footer" colspan="2">Note: Your new password
											must be a minimum of 6 characters.</td>
										</tr>
										<tr>
											<td colspan="2"><%@include file="errorMsgs.jsp"%>
											</td>
										</tr>
										<tr>
											<td class="headTitleBG" style="white-space: nowrap;"
												colspan="2" align="left">Your password</td>
										</tr>
										<tr>
											<td class="TextLgBold" style="white-space: nowrap;"
												width="1%" align="right">Old password:&nbsp;&nbsp;</td>
											<td width="99%" class="BodyStyle" valign="top"><img
												src="/images/spacer.gif" width="1" height="1" border="0"
												alt="Required field"> <input type="password"
												name="password" size="8" maxlength="19" tabindex="1" /> <img
												src="/images/spacer.gif" width="10" height="1" border="0"
												alt="&nbsp;"> <img src="/images/required.gif"
												width="42" height="13" alt="&nbsp;" border="0"></td>
										</tr>
										<tr valign="top">
											<td class="TextLg" style="white-space: nowrap;" width="1%"
												align="right">Choose a<span class="TextLgBold">
											New password:</span>&nbsp;&nbsp;</td>
											<td width="99%" class="BodyStyle"><img
												src="/images/spacer.gif" width="1" height="1" border="0"
												alt="Required field"> <input type="password"
												name="newPass1" size="8" maxlength="16" tabindex="2" /> <img
												src="/images/spacer.gif" width="10" height="1" border="0"
												alt="&nbsp;"> <img src="/images/required.gif"
												width="42" height="13" alt="&nbsp;" border="0"></td>
										</tr>
										<tr>
											<td class="TextLg" style="white-space: nowrap;" width="1%"
												align="right">Confirm your <span class="TextLgBold">New
											password:</span>&nbsp;&nbsp;</td>
											<td width="99%" class="BodyStyle" valign="top"><img
												src="/images/spacer.gif" width="1" height="1" border="0"
												alt="Required field"> <input type="password"
												name="newPass2" size="8" maxlength="16" tabindex="3" /><img
												src="/images/spacer.gif" width="10" height="1" border="0"
												alt="&nbsp;"> <img src="/images/required.gif"
												width="42" height="13" alt="&nbsp;" border="0"></td>
										</tr>
										<tr>
											<td style="white-space: nowrap;" width="1%">&nbsp;</td>
											<td width="99%" class="BodyStyle" valign="top"><input
												type="submit" name="saveData" class="btn" value="Save" /> <input
												type="button" name="cancel" class="btn" value="Cancel"
												onclick="window.location.href='accountInfo.do';" /></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>

					</td>
				</tr>
			</table>
			</td>
			<td width="1%"><img src="/images/spacer.gif" width="20"
				height="1" border="0" alt="&nbsp;"></td>
		</tr>
	</table>
	<br />
	</form>
</tags:portfolioPage>



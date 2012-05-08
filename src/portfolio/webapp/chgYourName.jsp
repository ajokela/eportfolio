<!-- $Name:  $ -->
<!-- $Id: chgYourName.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<tags:portfolioPage viewMode="enter" pageTitle="Portfolio : Edit Name">
	<html:form method="POST" action="changeName.do">

		<input type="hidden" name="action" value="save" />
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
								<td width="100%" colspan="4" class="PageTitle" bgcolor="#FFFFFF">&nbsp;Edit
								Your Name</td>
							</tr>
							<tr bgcolor="#99CCFF">
								<td colspan="4" class="ltBlue"><img src="/images/spacer.gif"
									width="1" height="3" border="0" alt="&nbsp;"><a
									name="skip"></a></td>
							</tr>
							<tr bgcolor="#FFFFFF">
								<td width="100%" colspan="4">
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr align="left">
										<td class="Instructions" style="white-space: nowrap;"
											colspan="4">Enter your name in the spaces
										provided.&nbsp;&nbsp;Click <span class="InstructionsBold">save</span>
										when you are done.</td>
									</tr>
									<tr>
										<td colspan="4"><%@include file="errorMsgs.jsp"%>
										</td>
									</tr>
									<tr align="left">
										<td class="headTitleBG" style="white-space: nowrap;"
											colspan="4">Your name</td>
									</tr>
									<tr>
										<td class="TextLgBold" style="white-space: nowrap;" width="1%"
											align="right" valign="top">Current password:&nbsp;&nbsp;</td>
										<td width="59%" class="BodyStyle" style="white-space: nowrap;"
											valign="top"><img src="/images/spacer.gif" width="1"
											height="1" border="0" alt="Required field"><input
											type="password" name="password" size="8" maxlength="19"
											tabindex="1" /> <img src="/images/spacer.gif" width="10"
											height="1" border="0" alt="&nbsp;"> <img
											src="/images/required.gif" width="42" height="13" alt="&nbsp;"
											border="0"> <br />
										<span class="footer">&nbsp;&nbsp;(Your password is
										required to edit your name.)</span></td>
										<td class="Instructions" rowspan="5" bgcolor="#CCCCCC"
											valign="top"><span class="InstructionsBold">Why
										enter your name?</span>
										<hr width="100%" size="1" noshade>
										If you choose to enter your name in Portfolio the system will
										be able to actually display your name instead of your email
										address.</td>
									</tr>
									<tr>
										<td class="TextLgBold" style="white-space: nowrap;" width="1%"
											align="right">First name:&nbsp;&nbsp;</td>
										<td width="59%" class="BodyStyle" valign="top"><html:text
											property="firstName" size="25" maxlength="64" /></td>
									</tr>
									<tr>
										<td class="TextLgBold" style="white-space: nowrap;" width="1%"
											align="right">Middle name:&nbsp;&nbsp;</td>
										<td width="59%" class="BodyStyle" valign="top"><html:text
											property="middlename" size="25" maxlength="64" /></td>
									</tr>
									<tr>
										<td class="TextLgBold" style="white-space: nowrap;" width="1%"
											align="right">Last name:&nbsp;&nbsp;</td>
										<td width="59%" class="BodyStyle" valign="top"><html:text
											property="lastname" size="25" maxlength="64" /></td>
									</tr>
									<tr>
										<td class="TextLgBold" style="white-space: nowrap;" width="1%"
											align="right">&nbsp;</td>
										<td width="59%" class="BodyStyle" valign="top"><input
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
				<td width="1%"><img src="/images/spacer.gif" width="20"
					height="1" border="0" alt="&nbsp;"></td>
			</tr>
		</table>
	</html:form>
</tags:portfolioPage>

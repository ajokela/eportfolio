<!-- $Name:  $ -->
<!-- $Id: forgetPwd.jsp,v 1.7 2011/03/02 20:46:59 rkavajec Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="enter"
	pageTitle="Portfolio : Forget Your Password?">
	<div class="MainContent">
	<%@ include file="messages.jsp"%>
	<table width="300" border="0" cellspacing="0" cellpadding="3"
		class="guestLogin">
		<tr>
			<td>
			<form method="post" action="sendPassword.do" autocomplete="off">

			<h2>Guest Users: Forget your password?</h2>
			<p>Did you forget your Portfolio password? If so, complete this
			form and click <strong> send password.</strong> We will then send you
			an email message containing your password. <br>
			<br>
			<strong> Note:</strong> Users with x.500 IDs or Internet IDs from the
			University of Minnesota system cannot have their passwords emailed to
			them from this page. They will need to contact their campus Help Desk
			for assistance with forgotten passwords.</p>
			<h2>Request your Portfolio password</h2>
			<h3>Enter your email address 
				<input type="text" name="email" value="<c:out value="${param.email}"/>" size="25" maxlength="50" />
			</h3>
			<br>
			<input type="submit" name="Submit" class="btn" value="Send password" />
			<input type="button" name="cancel" class="btn" value="Cancel" onclick="window.location.href='/';" />
			</form>
			</td>
		</tr>
	</table>
	</div>
</tags:portfolioPage>

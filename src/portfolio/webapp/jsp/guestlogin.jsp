<!-- $Name:  $ -->
<!-- $Id: guestlogin.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="login" pageTitle="Welcome to ePortfolio!">
		<div id="loginSection">
			
			<form action="/basicGuestLogin" method="post">
			
				<div style="text-align: center;">
					<div>
					<b>Guest Login</b>
					</div>
					<div style="text-align: left;">
						<input type="text" name="username" id="guestLoginEmail" size="40" /><br />
						<span style="font-size: 10pt;">Email Address</span>
					</div>			
					
					<div style="text-align: left;">
						<input type="password" name="password" id="guestLoginPassword" size="40" /><br />
						<span style="font-size: 10pt;">Password</span>
					</div>
					
					<div style="text-align: left;">
						<input type="submit" name="Login" />
					</div>
				</div>
			
			</form>
		</div>
	
</tags:portfolioPage>

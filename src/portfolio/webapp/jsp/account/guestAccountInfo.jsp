<!-- $Name:  $ -->
<!-- $Id: guestAccountInfo.jsp,v 1.4 2011/02/17 19:59:18 rkavajec Exp $ -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="view"
	pageTitle="ePortfolio : Update Guest Account Information">
	<div class="MainContent">
	<form method="post" name="guestAccountForm" action="saveGuestAccountInfo.do">
	<input type="hidden" name="email"
		value="<c:out value="${sessionScope.Person.email}"/>" />
	<h2 align="left">Account Information</h2>
	<h3 align="left">First Name:</h3>
	<div align="left"><input name="firstName"
		value="<c:out value="${sessionScope.Person.firstName}"/>" /></div>
	<h3 align="left">Middle Initial:</h3>
	<div align="left"><input name="middlename"
		value="<c:out value="${sessionScope.Person.middlename}"/>" size="2" />
	</div>
	<h3 align="left">Last Name:</h3>
	<div align="left"><input name="lastname"
		value="<c:out value="${sessionScope.Person.lastname}"/>" /></div>
	<h3 align="left">Email address:</h3>
	<div align="left"><c:out value="${sessionScope.Person.email}" />
	</div>
	<h3 align="left">Password:</h3>
	<div align="left"><input type="password" name="password"
		value="<c:out value="${sessionScope.Person.password}"/>" /></div>
	<h3 align="left">Re-enter password:</h3>
	<div align="left"><input type="password" name="password2"
		value="<c:out value="${sessionScope.Person.password}"/>" /> <br />
	<br />
	<input type="submit" name="save" class="btn" value="Save Changes" /> <input
		type="submit" name="org.apache.struts.taglib.html.CANCEL" class="btn"
		value="Cancel" onclick="bCancel=true;" /></div>
	</form>
	</div>
	
	<div class="settingsGroup">
    <h3>Statistics</h3>
    <ul>
        <li><label>Storage space:</label>
        <div class="itemContent"><img
            src="storageChart.do?t=${ospi:currentTimeMillis()}"
            style="vertical-align: middle; margin-left: -13px; margin-top: -10px;" />
        </div>
        </li>
    </ul>
	
</tags:portfolioPage>




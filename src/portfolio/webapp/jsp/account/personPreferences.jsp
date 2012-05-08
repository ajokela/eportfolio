<!-- $Name:  $ -->
<!-- $Id: personPreferences.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : My ePortfolo Account">
	<div class="MainContent">
	<h1>My Portfolio Account</h1>
	<p class="Instructions">Your Portfolio account grants you access
	to storage space within the Portfolio application, as well as access
	to Portfolios shared by others who are members of the ePortfolio
	community.</p>
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
	</div>
	<div class="settingsGroup">
	<h3>Settings</h3>
	<ul>
		<li><label>Profile picture:</label>
		<div class="itemContent"><img src="/profilePicture"
			style="vertical-align: middle; width: 60px;" id="profilePic" /> <a
			href="#" id="changePic">change</a></div>
		</li>

	</ul>
	</div>
	</div>
	<script>
    $('changePic').observe('click', function(event){
      new Window({width:400,height:450}).setAjaxContent('/profilePicture/window', {}, true, true);
      document.observe('window:close', function(event) {
        Windows.closeAll();
        $('profilePic').src = $('profilePic').src + '?' + (new Date()).getTime();
      });
      event.stop();
    });
	</script>
</tags:portfolioPage>


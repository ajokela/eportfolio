<!-- $Name:  $ -->
<!-- $Id: apps.jsp,v 1.6 2011/03/14 19:37:44 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:portfolioPage viewMode="enter" pageTitle="ePortfolio Apps">
	<div class="MainContent" id="apps"><script type="text/javascript">EPF.use('services-css', "${applicationScope['org.portfolio.project.version']}", function() { $('apps').show() });</script>
	<h1>ePortfolio Apps</h1>
	<div>
	
	<!-- div style="overflow: hidden">
	<div style="display: block; float: left; padding: 10px;"><img
		src="/images/tinyPorticoScreenshot.png" width="200"
		alt="Portico Screenshot" /></div>

	<h2>Portico&trade;</h2>
	<p>Portico&trade; allows you to upload multiple files at a time to
	your ePortfolio. Using the desktop application, you may select files
	from your computer you'd like to add to ePortfolio. You don't even need
	to be logged on to start collecting files in the Portico&trade; to add
	to ePortfolio! To upload the files from Portico&trade;, you simply log
	in and send them to your ePortfolio. They are automatically added to
	your Materials in the "Portico&trade; Uploaded" folder where they can
	then be attached to elements, tagged, and moved into any other Material
	folder, just like any other ePortfolio material.</p>

	<p><a href="/services/portico">More Information on
	Portico&trade;</a></p>
	</div>
	-->
	
	<div style="overflow: hidden">

	<div style="display: block; float: left; padding: 10px;"><img
		src="/images/tinyiPhoneScreenshot.png" width="200"
		alt="iPhone App Screenshot" /></div>

	<h2>ePortfolio for iPhone &amp; iPod Touch</h2>
	<p>ePortfolio for iPhone &amp; iPod Touch is a simple way for you
	to add images or photos from your device's photo library to your
	ePortfolio. Simply enter your ePortfolio credentials, select one or
	more photo or image, and then send it to your ePortfolio.</p>

	<p><a href="http://z.umn.edu/eportfolioiphone">More Information
	on ePortfolio for iPhone &amp; iPod Touch</a></p>
	</div>

	</div>

	</div>
</tags:portfolioPage>

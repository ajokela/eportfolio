<!-- $Name:  $ -->
<!-- $Id: share.jsp,v 1.8 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:portfolioPage viewMode="share"
	pageTitle="ePortfolio : Manage your portfolios">
	<div class="MainContent" id="editPortfolios">
	<h1>Manage your portfolios</h1>
	<script>
      EPF.use("share", "${applicationScope['org.portfolio.project.version']}", function() {
        new EPF.controller.share.Main($('editPortfolios'));
      });
    </script></div>
</tags:portfolioPage>

<!-- $Name:  $ -->
<!-- $Id: collection.jsp,v 1.7 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : Manage your collection">
	<div class="MainContent" id="manageCollection">
	<h1>Manage your collection</h1>
	<p class="instructNew">Each discrete information record in your
	ePortfolio collection is considered an element.</p>
	<script>
      EPF.use("collection", "${applicationScope['org.portfolio.project.version']}", function() {
        new EPF.controller.collection.Main($('manageCollection'));
      });
    </script></div>
</tags:portfolioPage>

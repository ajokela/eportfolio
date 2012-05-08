<!-- $Name:  $ -->
<!-- $Id: wizardAdmin.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="Portfolio : Collection Guide Admin">
	<div class="MainContent">
	<h3>Collection Guide Admin Page</h3>
	<input class="btn" type="button"
		onclick="window.location.href='viewKeywordData.do'"
		value="Generate collection report" /> <br />
	<br />
	<form enctype="multipart/form-data" encoding="multipart/form-data"
		method="post" action="generateWizardSql.do"><input type="file"
		name="file" /> <input class="btn" type="submit"
		value="Generate SQL from spreadsheet" /></form>
	<br />
	<br />
	<input class="btn" type="button"
		onclick="new Ajax.Request('start52WizardMigrateKeywords.do')"
		value="Start 5.2 System Tag Migration" /> <br />
	<br />
	<input class="btn" type="button"
		onclick="new Ajax.Request('start52WizardMigrateEntries.do')"
		value="Start 5.2 Entries Migration" /> <br />
	</div>
</tags:portfolioPage>

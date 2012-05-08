<!-- $Name:  $ -->
<!-- $Id: uploadTest.jsp,v 1.5 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="File Upload Service Test">
	<div class="MainContent">
	<h1>File Upload Service Test</h1>
	<div></div>
	<form action="/services/upload/" enctype="multipart/form-data"
		method="post" class="newFormAlt"><label for="usernameInput">
	username </label> <input id="usernameInput" type="text" name="username" /> <label>
	password </label> <input id="passwordInput" type="password" name="password" />
	<label for="folderName"> folder </label> <input id="folderName"
		type="text" name="folder_name" /> <label for="entry_name">
	entry_name </label> <input id="entry_name" type="text" name="entry_name" /> <label
		for="description"> description </label> <textarea id="description"
		name="description" rows="" cols=""></textarea> <label for="author">
	author </label> <input id="author" type="text" name="author" /> <label
		for="file1Input"> File 1 </label> <input id="file1Input" type="file"
		name="file1" /> <a href="#" id="addFile" style="float: left;">[more
	files]</a>
	<fieldset class="submitSet" style="clear: both"><label></label>
	<input type="submit" name="save" value="Submit" /></fieldset>
	</form>
	</div>
	<script type="text/javascript">
    $('addFile').observe('click', function(event) {
        event.stop();
        var index = $$('input[type=file]').size() + 1;
        var labelEl = new Element('label', {
            'for' : 'file' + index + 'input'
        }).update('File ' + index);
        var inputEl = new Element('input', {
            'id' : 'file' + index + 'input',
            type : 'file',
            name : 'file' + index
        }).update('File ' + index);
        $('addFile').insert({'before' :labelEl});
        $('addFile').insert({'before' :inputEl});
    });
</script>
</tags:portfolioPage>

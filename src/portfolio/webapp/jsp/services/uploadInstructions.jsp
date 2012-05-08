<!-- $Name:  $ -->
<!-- $Id: uploadInstructions.jsp,v 1.9 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:portfolioPage viewMode="enter" pageTitle="File Upload Service">
	<div class="MainContent" id="uploadInstructions"><script
		type="text/javascript">EPF.use('services-css', "${applicationScope['org.portfolio.project.version']}", function() { $('uploadInstructions').show() });</script>
	<h1>File Upload Service</h1>
	<p>This service allow applications to post files to an ePortfolio
	user's account.</p>
	<h2>Uploading Files</h2>
	<p>You must send an HTTP POST request to:</p>
	<pre>${applicationScope['org.portfolio.portfolio.base.url']}/services/upload/</pre>
	<p>The form must use the <code>multipart/form-data</code> enctype.
	<a href="/services/upload/test">This page</a> can be used to test the
	service.</p>
	<h2>Form Parameters</h2>
	<p>All parameters are required unless otherwise noted. Anything not
	implemented yet is not required.</p>
	<dl>
		<dt>api_key (not implemented yet!)</dt>
		<dd>The API key for your application.</dd>
		<dt>username</dt>
		<dd>The username of the account holder.</dd>
		<dt>password (optional)</dt>
		<dd>The password of the account holder. Either the password or
		the umn_cookie_value must be included.</dd>
		<dt>umn_cookie_value (optional)</dt>
		<dd>The value of the umn auth cookie. This could be used instead
		of the password if the user has already logged in to your app. Either
		the password or the umn_cookie_value must be included.</dd>
		<dt>file</dt>
		<dd>The file(s) to upload. One or more files may be uploaded. The
		parameter name doesn't matter, but parameter names for multiple files
		<b>must be different</b>.</dd>
		<dt>folder_name (optional)</dt>
		<dd>The name of a folder to add the files to. If it doesn't
		exist, it is created.</dd>
		<dt>entry_name (optional)</dt>
		<dd>A name for the file. If multiple files are included, they
		will all get this name. Use separate requests if you want to give the
		files different names. If this is not included, the file's name will
		be used.</dd>
		<dt>description (optional)</dt>
		<dd>A description for the file. If multiple files are included,
		they will all get this description. Use separate requests if you want
		to give the files different descriptions.</dd>
		<dt>author (optional)</dt>
		<dd>An author for the file. If multiple files are included, they
		will all get this author. Use separate requests if you want to give
		the files different authors.</dd>
	</dl>
	<h2>Response</h2>
	If the upload is successful, the response will be in this format: <pre>&lt;?xml version="1.0" encoding="utf-8" ?&gt;
&lt;rsp stat="ok"&gt;
  &lt;files&gt;
  &lt;file entryKeyId="DSJDLALKJSKLDJLASKJDLJKASDKLJASKJDL" fileName="resume.docx" /&gt;
  &lt;file entryKeyId="LALKJSLDSJDJKAKLDJLASKJDSDKLJASKJDL" fileName="myphoto.jpg" /&gt;
  &lt;file entryKeyId="ADSJDLALKJSKDLJKLDJLDKLJASKJDLASKJS" fileName="mysong.mp3" /&gt;
  &lt;/files&gt;
&lt;/rsp&gt;</pre> If the upload fails, the repsonse will be in this format: <pre>&lt;?xml version="1.0" encoding="utf-8" ?&gt;
&lt;rsp stat="fail"&gt;
  &lt;err code="[error-code]" msg="[error-message]" /&gt;
&lt;/rsp&gt;</pre>
	<h2>Error Codes</h2>
	<dl>
		<dt>general.fail</dt>
		<dd>The upload failed for an unknown reason.</dd>
		<dt>apikey.invalid</dt>
		<dd>The given API key was not valid.</dd>
		<dt>login.invalid</dt>
		<dd>The login did not authenticate.</dd>
		<dt>file.size.empty</dt>
		<dd>One or more files was empty.</dd>
		<dt>file.size.exceedsMax</dt>
		<dd>One or more files exceeded the max upload size of 200 MB.</dd>
		<dt>file.size.exceedsFreeSpace</dt>
		<dd>The sum of the file sizes exceeded the user's free space
		remaining. Total space is 5 GB.</dd>
	</dl>
	</div>
</tags:portfolioPage>

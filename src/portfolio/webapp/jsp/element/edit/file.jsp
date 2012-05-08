<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: file.jsp,v 1.5 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="epf"%>

<h3>Name</h3>
<input type="text" name="entryName" value="${dataDef.entryName}" />
<br />
<br />
<h3>Description</h3>
<textarea name="description" class="plainText"
	onkeyup="limitChar(this,256)">${dataDef.description}</textarea>
<br />
<br />
<h3>Author</h3>
<input type="text" name="author" value="${dataDef.author}" />
<br />
<br />

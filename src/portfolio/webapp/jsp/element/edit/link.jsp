<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: link.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<h3>Name:</h3>
<input name="entryName" type="text" maxlength="256"
	value="${dataDef.entryName}" />
<span class="required">* Required</span>
<br />
<br />
<h3>URL:&nbsp;&nbsp;(e.g. http://portfolio.umn.edu)</h3>
<input name="url" type="text" maxlength="256"
	value="${fn:escapeXml(dataDef.url)}" />
<span class="required">* Required</span>
<br />
<br />
<h3>Description:</h3>
<textarea name="description" class="plainText">${dataDef.description}</textarea>
<br />
<br />
<h3>Author:</h3>
<input name="author" type="text" maxlength="64"
	value="${dataDef.author}" />

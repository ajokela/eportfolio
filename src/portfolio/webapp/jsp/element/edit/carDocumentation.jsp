<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carDocumentation.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<h3>Name of documentation</h3>
<html:text property="entryName" size="25" maxlength="60" />
<span class="required">* Required</span>

<br />
<br />
<h3>Text / Description</h3>
(Limit approximately 600 words)
<br />
<html:textarea property="text" cols="40" rows="15"
	onkeyup="limitChar(this,4000)" />

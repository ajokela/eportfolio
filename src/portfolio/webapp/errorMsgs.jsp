<!-- $Name:  $ -->
<!-- $Id: errorMsgs.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<logic:messagesPresent>
	<html:messages id="error" header="errors.header" footer="errors.footer">
		<li><bean:write name="error" filter="false" /></li>
	</html:messages>
</logic:messagesPresent>
<logic:messagesPresent>
	<html:messages id="uploadWarning" message="true"
		header="warn.upload.header" footer="warn.upload.footer">
		<h3><bean:write name="uploadWarning" filter="false" /></h3>
	</html:messages>
</logic:messagesPresent>

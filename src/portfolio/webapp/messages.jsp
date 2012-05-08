<!-- $Name:  $ -->
<!-- $Id: messages.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<logic:messagesPresent message="true">
	<br />
	<span class="systemMessages"> <html:messages id="message"
		message="true">
		<logic:notEmpty name="message">
			<bean:write name="message" />
			<br />
		</logic:notEmpty>
	</html:messages> </span>
	<br />
	<br />
</logic:messagesPresent>

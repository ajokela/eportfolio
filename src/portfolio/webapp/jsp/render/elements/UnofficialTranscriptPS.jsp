<!-- $Name:  $ -->
<!-- $Id: UnofficialTranscriptPS.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField>
	<a target="ospiTranscript"
		href="/viewUnofficialTranscript.do?institution=${element.institution}&studentID=${element.encodedEmplid}">${element.entryName}</a>
</tags:elementField>

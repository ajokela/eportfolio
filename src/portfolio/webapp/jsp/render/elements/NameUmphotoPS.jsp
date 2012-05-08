<!-- $Name:  $ -->
<!-- $Id: NameUmphotoPS.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField>
	<img border="0" height="120"
		src="UCardPicture?personid=${element.encryptedEmplId}" />
</tags:elementField>

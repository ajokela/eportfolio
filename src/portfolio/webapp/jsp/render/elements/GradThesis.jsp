<!-- $Name:  $ -->
<!-- $Id: GradThesis.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:elementField heading="Univeristy of Completion">${element.university}</tags:elementField>
<tags:elementField heading="Date of Defense">${element.defenseDate}</tags:elementField>
<tags:elementField heading="Introduction" wysiwyg="true">${element.introduction}</tags:elementField>
<tags:elementField heading="Abstract/Comments" wysiwyg="true">${element.commentary}</tags:elementField>

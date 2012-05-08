<!-- $Name:  $ -->
<!-- $Id: CollegeMajorPS.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:elementField heading="Term">${element.termDesc}</tags:elementField>
<tags:elementField heading="">
	<c:if test="${not empty element.effdt}">
    ${element.effdt} (Date of change)
  </c:if>
</tags:elementField>
<tags:elementField heading="Campus">${element.institutionDesc}</tags:elementField>
<tags:elementField heading="Career">${element.acadCareerDesc}</tags:elementField>
<tags:elementField heading="Program Action">${element.progActionDescr}</tags:elementField>
<tags:elementField heading="College">${element.progDescr}</tags:elementField>
<tags:elementField heading="Major/Minor">${element.planDesc} (${element.planTypeDesc})</tags:elementField>

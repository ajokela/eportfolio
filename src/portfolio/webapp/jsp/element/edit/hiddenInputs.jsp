<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: hiddenInputs.jsp,v 1.5 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%-- Encrypted UID - leave as it is: --%>
<logic:present parameter="uid">
	<input type="hidden" name="uid" value="<c:out value="${param.uid}"/>" />
</logic:present>
<logic:present parameter="entryId">
	<input type="hidden" name="entryId"
		value="<c:out value="${param.entryId}"/>" />
</logic:present>
<logic:present parameter="elementid">
	<input type="hidden" name="elementid"
		value="<c:out value="${param.elementid}"/>" />
</logic:present>
<logic:present parameter="category">
	<input type="hidden" name="category"
		value="<c:out value="${param.category}"/>" />
</logic:present>
<logic:present parameter="index">
	<input type="hidden" name="index"
		value="<c:out value="${param.index}"/>" />
</logic:present>
<logic:present parameter="nodeId">
	<input type="hidden" name="nodeId"
		value="<c:out value="${param.nodeId}"/>" />
</logic:present>
<logic:present parameter="wizardElementDefIdFromEW">
	<input type="hidden" name="wizardElementDefIdFromEW"
		value="<c:out value="${param.wizardElementDefIdFromEW}"/>" />
</logic:present>
<logic:present parameter="fieldTitle">
	<input type="hidden" name="fieldTitle"
		value="<c:out value="${param.fieldTitle}"/>" />
</logic:present>

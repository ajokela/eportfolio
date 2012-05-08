<%@ tag body-content="empty"%>
<%@ attribute name="entries" required="true" type="java.util.Collection"%>
<%@ attribute name="elementDefinition" required="true"
	type="org.portfolio.model.ElementDefinition"%>
<%@ attribute name="wizardElementId" required="false"%>
<%@ attribute name="showCheckBox" required="false"%>
<%@ attribute name="showDelete" required="false"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<table class="elementInstances">
	<!-- System Element Table -->
	<colgroup>
		<c:if test="${showCheckBox == 'true'}">
			<col class="elcheckbox" />
		</c:if>
		<col class="elActions" />
		<col class="elName" />
	</colgroup>
	<thead>
		<tr>
			<c:if test="${showCheckBox == 'true'}">
				<td>Select</td>
			</c:if>
			<td>Actions</td>
			<td>Name</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="instance" items="${entries}" varStatus="status4">
			<c:set var="rowClass"
				value="${status4.index % 2 eq 0 ? 'evenRow' : 'oddRow'}" />
			<tr>
				<c:if test="${showCheckBox == 'true'}">
					<td class="${rowClass}"><input name="elementCheck"
						type="checkbox"
						value="${elementDefinition.elementId}_${instance.entryId}"
						id="user_<c:out value="${elementDefinition.elementName}"/>_<c:out value="${instance.entryName}"/>" /></td>
				</c:if>
				<td class="${rowClass}">&middot;<a
					href="viewElement.do?action=edit&nodeId=${elementDefinition.elementId}&entryId=${instance.entryId}">View/Edit</a>
				<br />
				<c:if test="${pageScope.wizardElementId != null}">
            &middot;<a id="${elementDefinition.elementId}"
						href="javascript:deleteElementFromWizard('deleteWizardElementInstance.do?wizardElementId=${wizardElementId}&entryId=${instance.entryId}');">Remove</a>
					<br />
				</c:if> <c:if test="${showDelete == 'true'}">
            &middot;<a id="${elementDefinition.elementId}"
						href="javascript:deleteElement('deleteElement.do?nodeId=${elementDefinition.elementId}&entryId=${instance.entryId}#${elementDefinition.elementId}','<c:out value="${instance.entryName}"/>','<c:out value="${elementDefinition.elementName}"/>');">Delete</a>
					<br />
				</c:if></td>
				<td class="${rowClass}">${instance.entryName}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<!-- $Name:  $ -->
<!-- $Id: GradCommMember.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<tags:elementField heading="First Name">${element.firstName}</tags:elementField>
<!-- Fixed Type of Committee layout BK 3/19/12 -->
<c:if test="${not empty element.masters or not empty element.docPrelimOral or not empty element.docPrelimWritten or not empty element.docFinalOral}">
<tags:elementField heading="Type of Committee">
	<c:if test="${not empty element.masters}">Masters</c:if>
	<c:if test="${not empty element.docPrelimOral}">Doctoral Preliminary Oral</c:if>
	<c:if test="${not empty element.docPrelimWritten}">Doctoral Preliminary Written</c:if>
	<c:if test="${not empty element.docFinalOral}">Doctoral Final Oral</c:if>
</tags:elementField>
</c:if>
<!-- Fixed layout for Roles BK 3/19/12 -->
<c:if test="${not empty element.adviser or not empty element.majorFieldChair or not empty element.supportingFieldChair or not empty element.majorFieldMember or not empty element.supportingFieldMember or not empty element.majorFieldReviewer or not empty element.supportingFieldReviewer}">
<tags:elementField heading="Role(s)"><br />
	<c:if test="${not empty element.adviser}">Advisor<br/></c:if>
	<c:if test="${not empty element.majorFieldChair}">Major Field Chair<br /></c:if>
	<c:if test="${not empty element.supportingFieldChair}">Supporting Field Chair<br /></c:if>
	<c:if test="${not empty element.majorFieldMember}">Major Field Member<br /></c:if>
	<c:if test="${not empty element.supportingFieldMember}">Supporting Field Member<br /></c:if>
	<c:if test="${not empty element.majorFieldReviewer}">Major Field Reviewer<br /></c:if>
	<c:if test="${not empty element.supportingFieldReviewer}">Supporting Field Reviewer</c:if>
</tags:elementField>
</c:if>
<tags:elementField heading="Campus Address">${element.campusAddress}</tags:elementField>
<tags:elementField heading="Phone">${element.phone}</tags:elementField>
<tags:elementField heading="Email Address">${element.emailAddress}</tags:elementField>
<!-- 

grad_comm_member.setPersonId(rs.getString("PERSON_ID"));
grad_comm_member.setEntryId(rs.getBigDecimal("ENTRY_ID"));
grad_comm_member.setDateCreated(rs.getTimestamp("DATE_CREATED"));
grad_comm_member.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
grad_comm_member.setMasters(rs.getString("MASTERS"));
grad_comm_member.setDocPrelimWritten(rs.getString("DOC_PRELIM_WRITTEN"));
grad_comm_member.setDocPrelimOral(rs.getString("DOC_PRELIM_ORAL"));
grad_comm_member.setDocFinalOral(rs.getString("DOC_FINAL_ORAL"));
grad_comm_member.setFirstName(rs.getString("FIRST_NAME"));
grad_comm_member.setEntryName(rs.getString("ENTRY_NAME"));
grad_comm_member.setAdviser(rs.getString("ADVISER"));
grad_comm_member.setMajorFieldChair(rs.getString("MAJOR_FIELD_CHAIR"));
grad_comm_member.setMajorFieldMember(rs.getString("MAJOR_FIELD_MEMBER"));
grad_comm_member.setMajorFieldReviewer(rs.getString("MAJOR_FIELD_REVIEWER"));
grad_comm_member.setSupportingFieldChair(rs.getString("SUPPORTING_FIELD_CHAIR"));
grad_comm_member.setSupportingFieldMember(rs.getString("SUPPORTING_FIELD_MEMBER"));
grad_comm_member.setSupportingFieldReviewer(rs.getString("SUPPORTING_FIELD_REVIEWER"));
grad_comm_member.setCampusAddress(rs.getString("CAMPUS_ADDRESS"));
grad_comm_member.setPhone(rs.getString("PHONE"));
grad_comm_member.setEmailAddress(rs.getString("EMAIL_ADDRESS"));

 -->
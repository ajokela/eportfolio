<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduGradExamCommMember.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3><br />
<br />
Type of committee</h3>
<!-- Fixed checkboxes, really should be radio buttons, but too much work at this time BK 3/19/12 -->
<p>&nbsp;&nbsp;&nbsp;&nbsp;(select one of the following)<br />

<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.masters == 1}">
      Masters &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="masters" id="masters" value="1" <c:if test="${dataDef.masters == 1}"> checked="checked"</c:if> /> Masters &nbsp;
   		<input type="hidden" name="masters" value="" />
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
        <c:if test="${dataDef.docPrelimOral == 1}">
            Doctoral Preliminary Oral &nbsp;
        </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="docPrelimOral" id="docPrelimOral" value="1" <c:if test="${dataDef.docPrelimOral == 1}"> checked="checked"</c:if> /> Doctoral Preliminary Oral &nbsp;
   		<input type="hidden" name="docPrelimOral" value="" />
   </c:otherwise>
</c:choose> <br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.docPrelimWritten == 1}">
      Doctoral Preliminary Written &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="docPrelimWritten" id="docPrelimWritten" value="1" <c:if test="${dataDef.docPrelimWritten == 1}"> checked="checked"</c:if> /> Doctoral Preliminary Written &nbsp;
   		<input type="hidden" name="docPrelimWritten" value="" />
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.docFinalOral == 1}">
      Doctoral Final Oral &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="docFinalOral" id="docFinalOral" value="1" <c:if test="${dataDef.docFinalOral == 1}"> checked="checked"</c:if> /> Doctoral Final Oral &nbsp;
   		<input type="hidden" name="docFinalOral" value="" />
   </c:otherwise>
</c:choose></p>

<h3>First name</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.firstName}" />
	</c:when>
	<c:otherwise>
		<html:text property="firstName" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Last name</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="25" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
Role(s)</h3>
<!-- Fixed checkboxes BK 3/19/12 -->
<p>&nbsp;&nbsp;&nbsp;&nbsp;(select one or more)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.adviser == 1}">
      Advisor &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="adviser" id="adviser" value="1" <c:if test="${dataDef.adviser == 1}"> checked="checked"</c:if> /> Advisor &nbsp;
   		<input type="hidden" name="adviser" value="" /> <!-- This is needed to send null value since unchecked box will not return a value -->
   </c:otherwise>
</c:choose> <br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.majorFieldChair == 1}">
      Major Field Chair &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="majorFieldChair" id="majorFieldChair" value="1" <c:if test="${dataDef.majorFieldChair == 1}"> checked="checked"</c:if> /> Major Field Chair &nbsp;
   		<input type="hidden" name="majorFieldChair" value="" />	    
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.supportingFieldChair == 1}">
      Supporting Field Chair &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="supportingFieldChair" id="supportingFieldChair" value="1" <c:if test="${dataDef.supportingFieldChair == 1}"> checked="checked"</c:if> /> Supporting Field Chair &nbsp;
   		<input type="hidden" name="supportingFieldChair" value="" />	 
   </c:otherwise>
</c:choose> <br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.majorFieldMember == 1}">
      Major Field Member &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="majorFieldMember" id="majorFieldMember" value="1" <c:if test="${dataDef.majorFieldMember == 1}"> checked="checked"</c:if> /> Major Field Member &nbsp;
   		<input type="hidden" name="majorFieldMember" value="" />
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.supportingFieldMember == 1}">
      Supporting Field Member &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="supportingFieldMember" id="supportingFieldMember" value="1" <c:if test="${dataDef.supportingFieldMember == 1}"> checked="checked"</c:if> /> Supporting Field Member &nbsp;
   		<input type="hidden" name="supportingFieldMember" value="" />	    
   </c:otherwise>
</c:choose> <br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.majorFieldReviewer == 1}">
      Major Field Reviewer &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="majorFieldReviewer" id="majorFieldReviewer" value="1" <c:if test="${dataDef.majorFieldReviewer == 1}"> checked="checked"</c:if> /> Major Field Reviewer &nbsp;
   		<input type="hidden" name="majorFieldReviewer" value="" />	    
   </c:otherwise>
</c:choose> <c:choose>
	<c:when test="${param.action == 'view'}">
		<c:if test="${dataDef.supportingFieldReviewer == 1}">
      Supporting Field Reviewer &nbsp;
      </c:if>
	</c:when>
	<c:otherwise>
		<input type="checkbox" name="supportingFieldReviewer" id="supportingFieldReviewer" value="1" <c:if test="${dataDef.supportingFieldReviewer == 1}"> checked="checked"</c:if> /> Supporting Field Reviewer &nbsp;
   		<input type="hidden" name="supportingFieldReviewer" value="" />	    
   </c:otherwise>
</c:choose></p>

<br />
<h3>Campus address</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.campusAddress}" />
	</c:when>
	<c:otherwise>
		<html:text property="campusAddress" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Phone</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.phone}" />
	</c:when>
	<c:otherwise>
		<html:text property="phone" size="25" maxlength="25" />
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>E-mail address</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.emailAddress}" />
	</c:when>
	<c:otherwise>
		<html:text property="emailAddress" size="25" maxlength="50" />
	</c:otherwise>
</c:choose>

<script type="text/javascript"> 
    // masters, docPrelimOral, docPrelimWritten, docFinalOral

    var allCommitteeTypes = $$('input.committeeType')
    
    allCommitteeTypes.each(function(item){
    	item.observe('click', function(event){
    		var element = event.element();
    		
    		if(element.checked) {
    			allCommitteeTypes.each(function(other){
    				
    				if(other.id != element.id) {
    					other.setValue(false);	
    				}
    				
    			});
    		}
    		
    	});
    });
    
</script>

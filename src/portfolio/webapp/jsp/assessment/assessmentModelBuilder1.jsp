<!-- $Name:  $ -->
<!-- $Id: assessmentModelBuilder1.jsp,v 1.5 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : ${param.format} Assessment Builder">

	<div class="MainContent builderPage">
	<p class="superhead">ePortfolio Communities > ${param.format}
	Assessment Model Builder</p>

	<jsp:include page="assessmentModelSteps.jsp">
		<jsp:param name="new" value="${assessmentModel.new}" />
		<jsp:param name="step" value="1" />
		<jsp:param name="format" value="${param.format}" />
	</jsp:include>

	<h1>Step 1: Name and Description</h1>
	<SCRIPT language="Javascript">
            function validateForm(){
                if($('shareName').value=="")
                {
                    new dialog.Alert({messageText:'Please enter a name'}).show();
                    return false;
                }
                return true;
            }
        </SCRIPT> <html:form
		action="assessmentModelBuilder1.do?format=${param.format}"
		method="post" styleClass="newForm newFormNarrow"
		onsubmit="return validateForm();">
		<fieldset>
		<ul>
			<li><label for="shareName">Name*:</label> <html:text
				property="name" styleId="shareName" styleClass="inputsizer75"
				maxlength="50" size="50" />&nbsp; <input type="hidden"
				name="communityId" value="${assessmentModelForm.communityId}" /></li>
			<li><%--<!-- label for="shareDesc">Description:</label> <small>(describe the purpose of this assessment model)</small>--%>
			<%--<html:textarea property="description" styleId="shareDesc" cols="40" rows="3" styleClass="inputsizer90" onkeyup="limitChar(this,512)"/ -->--%>

			<div>
			<div style="width: 75%; float: left; display: block;"><span>Description:
			(describe the purpose of this assessment model)</span></div>
			<div
				style="width: 25%; float: right; display: block; vertical-align: bottom;">
			<span id="shareDescCounter"></span></div>
			</div>
			<div><html:textarea property="description" styleId="shareDesc"
				cols="40" rows="5" style="width: 350px;" /></div>
			<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'shareDesc',
				               counterEl: 'shareDescCounter',
				               max: 800
				           });
			            </script></li>
		</ul>
		</fieldset>
		<p>*Required</p>
		<input type="submit" class="btn" value="Next:Choosing scoring model" />
		<br />
		<br />
		<hr />
		<br />
		<a
			href="assessmentDirectory.do?communityId=${assessmentModelForm.communityId}">Cancel</a>
		<input id="step" type="hidden" name="step" value="step2" />
	</html:form></div>
</tags:portfolioPage>

<!-- $Name:  $ -->
<!-- $Id: assessmentModelBuilder5.jsp,v 1.5 2010/11/23 20:34:58 ajokela Exp $ -->
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
		<jsp:param name="step" value="5" />
		<jsp:param name="format" value="${param.format}" />
	</jsp:include>

	<h1>Step 5: Enter Performance Descriptors</h1>
	<%@ include file="/messages.jsp"%> <script
		language="javascript">
    var prevEditId;
    var editWindow;
    function edit(id){
        prevEditId = id;
        $$('textarea.editing').invoke('removeClassName', 'editing');
        $(id).addClassName('editing');
        $('editName').value =  $(id).value;
        $('editDescription').value = $('ext:'+id).value;
        $('editRowCol').update(id);
        showEdit();
    }
    
    function save(){
        $(prevEditId).value =  $('editName').value;
        $('ext:'+prevEditId).value =  $('editDescription').value;
        $(prevEditId).focus();
        hideEdit();
    }
    function showEdit(){
        editWindow = new Window({
          maximizable: false, 
          minimizable: false, 
          resizable: false, 
          destroyOnClose: true, 
          onClose: function() {$('editCell').hide()}
        });
        editWindow.setContent('editCell', true, true);
        editWindow.setSize(570,450);
        editWindow.showCenter(); 
        $('editName').focus();
    }
    
    function hideEdit(){
        $$('textarea.editing').invoke('removeClassName', 'editing');
        if (editWindow) editWindow.close();
    }
    
    function init(){
        hideEdit();
    }
    Event.observe(window, 'load', init);
</script> <html:form action="assessmentModelBuilder5.do?format=${param.format}"
		method="post" styleId="builderForm">
		<input type="hidden" name="communityId"
			value="${assessmentModelForm.communityId}" />
		<p>The final step is filling in the rubric grid: creating a
		descriptor for each criterion/performance level intersection. Click a
		cell in the grid below to make it editable.</p>
		<!-- Performance Descriptor creation -->
		<table class="rubric" border="0" cellspacing="0">
			<tr>
				<th></th>
				<!-- TODO: Build row based on scoringModel.valueSet -->
				<c:forEach var="col" items="${assessmentModelForm.values}">
					<th align="center"><c:out value="${col}" /></th>
				</c:forEach>
			</tr>
			<c:forEach var="objective"
				items="${assessmentModelForm.assessedObjectives}" varStatus="loop0">
				<tr>
					<td class="objNameCell" valign="top"><b><c:out
						value="${objective.name}" /></b></td>
					<c:forEach var="col" items="${assessmentModelForm.values}"
						varStatus="loop">
						<td><textarea name="performanceDescriptors_${loop0.count}_${loop.count}"
							id="${objective.displaySequence}:${col}" rows="4" cols="15"
							onclick="edit('${objective.displaySequence}:${col}')"
							readonly="true"><c:out
							value="${objective.performanceDescriptors[loop.index].name}"></c:out></textarea>
						<input type="hidden" name="extPerformanceDescriptors_${loop0.count}_${loop.count}"
							id="ext:${objective.displaySequence}:${col}"
							value="${objective.performanceDescriptors[loop.index].description}" />
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>

		<!-- Edit Rubric cell -->
		<div id="editCell" style="display: none;">
		<h3>Editing Cell
		<div id="editRowCol"></div>
		</h3>
		<label for="editName" style="display: block;"><strong>Cell
		contents</strong> (required)</label> <textarea id="editName" rows="4" cols="60"></textarea>
		<br />
		<label for="editDescription" style="display: block;"><strong>Extended
		cell contents</strong> (optional-will open in a pop-up during evaluation)</label> <textarea
			id="editDescription" rows="6" cols="60"></textarea><br />
		<em>You can use this space to provide an example of content that
		exemplifies<br />
		the criterion-performance intersection</em><br />
		<br />
		<input type="button" value="Save" onclick="save()" />&nbsp;or <a
			href="#" onclick="hideEdit();">Cancel</a></div>
		<!-- End Edit Rubric cell -->
		<!-- End Rubric creation -->
		<br />
		<br />
		<input type="submit" class="btn" value="Prev:Order Objectives"
			onclick="$('step').value='step4';return true;" />
		<input type="submit" class="btn" value="Save For Later"
			onclick="$('step').value='SaveForLater';return true;" />
		<input type="submit" class="btn" value="Finish"
			onclick="$('step').value='finish';return true;" />
		<br />
		<br />
		<hr />
		<br />
		<a
			href="assessmentDirectory.do?communityId=${assessmentModelForm.communityId}">Cancel</a>
		<input type="hidden" id="stepAction" name="stepAction"
			value="backFromStep5" />
		<input id="step" type="hidden" name="step" value="next" />
	</html:form></div>
</tags:portfolioPage>

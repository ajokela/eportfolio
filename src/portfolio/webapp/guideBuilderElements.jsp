<!-- $Name:  $ -->
<!-- $Id: guideBuilderElements.jsp,v 1.10 2011/03/25 21:08:50 rkavajec Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<tags:communityPage community="${community}"
	pageTitle="Collection Guide Builder" id="guideBuilderElements" cssClass="yui-skin-sam">
	<h2>Add/Edit Section Elements</h2>
	<p>You are editing the <strong>${category.title}</strong> section.
	<a href="guideBuilderSections.do?method=edit&guideId=${guide.id}">Finish
	and return to collection guide sections.</a></p>
	<div class="grayform narrowLabels">
	<form action="guideBuilderElements.do" method="post" id="theForm"
		class="newFormAlt"><input type="hidden" name="guideId="
		value="${guide.id}" /> <input type="hidden" name="method"
		value="save"> <input type="hidden" name="categoryId"
		value="${category.id}"> <input type="hidden"
		name="guideElementId"
		value="${empty selectedDefiniton ? '' : selectedDefiniton.id}">
	<c:choose>
		<c:when test="${empty selectedDefiniton}">
			<h3>Add a new element:</h3>
		</c:when>
		<c:otherwise>
			<h2>Edit element:</h2>
		</c:otherwise>
	</c:choose> <label>Name</label> <input type="text" name="name"
		value="${empty selectedDefiniton ? '' : selectedDefiniton.title}" />
	<br class="clearboth" />

	<label>Description</label>
	<div id="descriptionWrapper">
		<textarea rows="" cols="" name="description" id="description">${empty selectedDefiniton ? '' : selectedDefiniton.description}</textarea>
	</div>
	<br class="clearboth" />
	<br class="clearboth" />
	
	<label>Element Type</label> <select name="elementType">
		<option value="">Select an element type</option>
		<c:forEach var="source" items="${elementSources}">
			<optgroup label="${source.name}">
				<c:forEach var="elementDef" items="${source.elementDefinitions}">
					<c:choose>
						<c:when
							test="${not empty selectedDefiniton and selectedDefiniton.elementDefinition.id eq elementDef.id}">
							<option value="${elementDef.id}" selected="selected">${elementDef.name}</option>
						</c:when>
						<c:otherwise>

							<c:if test="${elementDef.id eq '01010108'}">

								<option value="${elementDef.id}">UCard Photo</option>

							</c:if>

							<c:if test="${elementDef.id ne '01010108'}">

								<option value="${elementDef.id}">${elementDef.name}</option>

							</c:if>

						</c:otherwise>
					</c:choose>
				</c:forEach>
			</optgroup>
		</c:forEach>
	</select><br class="clearboth" />

	<label>Required?</label>
	<div class="radiogroup"><c:choose>
		<c:when
			test="${not empty selectedDefiniton and selectedDefiniton.required}">
			<input type="radio" name="required" value="yes" id="req1"
				checked="checked" />
			<label for="req1">Yes</label>
			<input type="radio" name="required" value="no" id="req2" />
			<label for="req2">No</label>
		</c:when>
		<c:otherwise>
			<input type="radio" name="required" value="yes" id="req1" />
			<label for="req1">Yes</label>
			<input type="radio" name="required" value="no" id="req2"
				checked="checked" />
			<label for="req2">No</label>
		</c:otherwise>
	</c:choose></div>
	<br class="clearboth" />
	<label>Auto-import?</label>
	<div class="radiogroup"><c:choose>
		<c:when
			test="${not empty selectedDefiniton and selectedDefiniton.autoImport}">
			<input type="radio" name="autoImport" value="yes" checked="checked" />
			<label for="auto1">Yes</label>
			<input type="radio" name="autoImport" value="no" id="auto2" />
			<label for="auto2">No</label>
		</c:when>
		<c:otherwise>
			<input type="radio" name="autoImport" value="yes" id="auto1" />
			<label for="auto1">Yes</label>
			<input type="radio" name="autoImport" value="no" id="auto2"
				checked="checked" />
			<label for="auto2">No</label>
		</c:otherwise>
	</c:choose></div>
	<br class="clearboth" />
	<label>Keywords</label> <input id="systemTagsInput"
		type="text" name="systemTags"
		value="${empty selectedDefiniton ? '' : ospi:join(selectedDefiniton.keywords,', ')}" />
	<br class="clearboth" />
	<fieldset class="submitSet"><label></label> <input
		type="submit" name="save"
		value="${empty selectedDefiniton ? 'Add' : 'Update'} element" />
	&nbsp;or <a
		href="/guideBuilderSections.do?method=edit&guideId=${guide.id}">cancel</a>
	</fieldset>
	</form>
	</div>
	<br class="clearboth" />
	<ul class="sectionElements">
		<c:forEach var="def" items="${category.wizardElementDefinitions}">
			<li id="def_${def.id}" class="def"><a
				href="/guideBuilderElements.do?method=up&guideElementId=${def.id}&guideId=${guide.id}"><img
				src="/images/iconUp.gif" /></a> <a
				href="/guideBuilderElements.do?method=down&guideElementId=${def.id}&guideId=${guide.id}"><img
				src="/images/iconDown.gif" /></a> <strong>${def.title}</strong> <a
				href="/guideBuilderElements.do?method=edit&categoryId=${category.id}&guideElementId=${def.id}&guideId=${guide.id}">Edit</a>
			| <a class="delete"
				href="/guideBuilderElements.do?method=delete&guideElementId=${def.id}&guideId=${guide.id}">Delete</a>
			<div class="newIndent1">(${def.elementDefinition.elementName})
			<br />
			<c:if test="${not empty def.description}">
				<p>${def.description}</p>
			</c:if></div>
			</li>
		</c:forEach>
	</ul>
	<br />
	<p><a
		href="/guideBuilderSections.do?method=edit&guideId=${guide.id}">Finish
	and return to collection guide sections.</a></p>
	<script type="text/javascript">
    $('theForm').observe('submit', function(event){
      if ($F(this.name).blank()) {
        new dialog.Alert('You must enter a name for the element.').show();
        event.stop();
        return;
      }
      if ($F(this.elementType).blank()) {
        new dialog.Alert('You must enter a type for the element.').show();
        event.stop();
        return;
      }
    });
    
    new EPF.widget.InputTextInnerLabel('systemTagsInput', 'Separate tags with commas');
    
    $$('#guideBuilderElements .def').each(function(e){
      var id = e.id.split('_')[1];
      e.down('.delete').observe('click', function(event){
        event.stop();
          new Ajax.Request('/guide/usage/def/' + id, {
            onComplete: function(transport) {
              var resp = transport.responseJSON;
              var numUsers = resp.data.numUsers;
              if (numUsers > 0) {
                new dialog.Alert('This element is already being used by ' + numUsers + ' users and can\'t be deleted.');
              } else {
                new dialog.Confirm({
                  messageText: 'Are you sure you want to delete this element? Deleting this element will also delete it from community template(s).',
                  confirmUrl: '/guideBuilderElements.do?method=delete&categoryId=' + id + '&guideElementId=' + id + "&guideId=${guide.id}"
                });
              }
            }
          });
      });
    });
    
  </script>
  	<script>
	  new EPF.use(["editor"], "${applicationScope['org.portfolio.project.version']}", function(){
      var myEditor = new YAHOO.widget.Editor('description', {
        height: '125px',
        width: '100%',
        handleSubmit: true,
        toolbar: {
            buttons: [
                      { group: 'textstyle', 
                          buttons: [
                              { type: 'push', label: 'Bold CTRL + SHIFT + B', value: 'bold' },
                              { type: 'push', label: 'Italic CTRL + SHIFT + I', value: 'italic' },
                              { type: 'push', label: 'Underline CTRL + SHIFT + U', value: 'underline' }
                          ]
                      },
									    { type: 'separator' },
									    { group: 'parastyle', 
									        buttons: [
									        { type: 'select', label: 'Normal', value: 'heading', disabled: true,
									            menu: [
									                { text: 'Normal', value: 'none', checked: true },
									                { text: 'Header 1', value: 'h2' },
									                { text: 'Header 2', value: 'h3' },
									                { text: 'Header 3', value: 'h4' }
									            ]
									        }
									        ]
									    },
                      { type: 'separator' },
                      { group: 'indentlist', 
                          buttons: [
                              { type: 'push', label: 'Create an Unordered List', value: 'insertunorderedlist' },
                              { type: 'push', label: 'Create an Ordered List', value: 'insertorderedlist' }
                          ]
                      },
									    { type: 'separator' },
									    { group: 'insertitem', 
									        buttons: [
									            { type: 'push', label: 'HTML Link CTRL + SHIFT + L', value: 'createlink', disabled: true }
									        ]
									    }
									                      
                    ]
                }
    });
    myEditor.render();
  });
	</script>
</tags:communityPage>

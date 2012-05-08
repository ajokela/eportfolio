<!-- $Name:  $ -->
<!-- $Id: changeProfilePictureWindow.jsp,v 1.5 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="changePicture">
<form id="changePictureForm"><input type="hidden" name="method"
	value="save">
<ul>
	<li><input type="radio" name="pictureSource" id="anonymousSource"
		value="anonymous" ${empty
		Person.profilePictureKey ? 'checked="checked" ' : ''}/> <label
		for="anonymousSource">No picture</label>
	<div class="radioContent"><img src="/images/anonymous.gif" /></div>
	</li>
	<li><input type="radio" name="pictureSource" id="materialSource"
		value="material" ${not empty
		Person.profilePictureKey ? 'checked="checked" ' : ''}/> <label
		for="materialSource">Select an image from my photos</label>
	<p class="radioContent note">Image will be displayed as a 60px by
	60px square.</p>
	<div class="radioContent"><c:choose>
		<c:when test="${not empty Person.profilePictureKey}">
			<img src="/photo/${Person.profilePictureKey.id}?width=60&height=60"
				id="materialImage" />
		</c:when>
		<c:otherwise>
			<img src="" id="materialImage" style="display: none;" />
		</c:otherwise>
	</c:choose>
	<div id="selectImage"><select id="materialSelect"
		name="materialEntryId">
		<option>Select an image</option>
		<c:forEach var="image" items="${images}">
			<option value="${image.entryKey.id}" ${not empty
				Person.profilePictureKey and Person.profilePictureKey eq
				image.entryKey ? 'selected="selected"' : '' }>${fn:escapeXml(image.entryName)}</option>
		</c:forEach>
	</select></div>
	</div>
	</li>
</ul>
<script>
      $('materialSelect').observe('change', function(evt){
        $('materialImage').src = '/photo/' + evt.element().value + '?height=60&width=60';
        $('materialImage').show();
      });
    </script>
<fieldset class="submitSet"><input type="button" value="Save"
	id="saveButton" /> &nbsp;or <a href="#"
	onclick="EPF.widget.modal.Modal.closeAll();return false;">cancel</a></fieldset>
</form>
<script>
    $('saveButton').observe('click', function(event){
      new Ajax.Request('/profilePicture/save', {
        parameters: $('changePictureForm').serialize(),
        onComplete: function(transport){
          EPF.widget.modal.Modal.closeAll();
        }
      });
    });
    if (!$('materialSource').checked) {
      $('materialSelect').disable();
    }
    $$('input[type="radio"][name="pictureSource"]').invoke('observe', 'click',
      function(e){
        if(e.element().value == 'material'){
          $('materialSelect').enable();
        } else{
          $('materialSelect').disable();
        }
      }
    );
  </script></div>

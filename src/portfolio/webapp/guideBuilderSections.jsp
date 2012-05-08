<!-- $Name:  $ -->
<!-- $Id: guideBuilderSections.jsp,v 1.9 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Collection Guide Builder" id="guideBuilderSections"
	cssClass="collSections">
	<!-- 
		Class:  ${className}
		Method: ${methodName}
		guideFound: ${guideFound}
	 -->

	<h2>Collection Guide Sections</h2>
	<c:if test="${guideFound == true}">
		<p>Before selecting specific elements to include in the collection
		guide, create sections and subsections to contain the elements. Use
		the up and down arrows to reorder your sections.</p>
		<div class="grayform">
		<form action="/guideBuilderSections.do"
			class="createSection newFormAlt" method="post" id="createSectionForm">
		<input type="hidden" name="method" value="createSection"> <input
			type="hidden" name="guideId" value="${guide.id}"> <label
			for="name">Add a new top-level section:</label> <input type="text"
			name="name" id="name" value="" /><br />
		<label></label> <input type="submit" value="Create section" /></form>
		</div>
		<hr class="clearboth" />
		<c:forEach var="category" items="${guide.categories}">
			<h3 id="cat_${category.id}" class="cat"><a
				href="/guideBuilderSections.do?method=sectionUp&categoryId=${category.id}&guideId=${guide.id}"><img
				src="/images/iconUp.gif" /></a> <a
				href="/guideBuilderSections.do?method=sectionDown&categoryId=${category.id}&guideId=${guide.id}"><img
				src="/images/iconDown.gif" /></a> ${category.title} <a class="rename"
				href="#">Rename this section</a> | <a class="delete" href="#">Delete
			this section</a></h3>
			<div class="newIndent1">
			<div class="grayform grayformNarrow">
			<form action="/guideBuilderSections.do"
				class="createSection newFormAlt" method="post"
				id="createSectionForm_${category.id}"><input type="hidden"
				name="method" value="createSection"> <input type="hidden"
				name="parentCategoryId" value="${category.id}"> <input
				type="hidden" name="guideId" value="${guide.id}"> <label>Add
			a subsection:</label> <input type="text" name="name" value="" /> <label></label>
			<input type="submit" value="Create subsection" /></form>
			</div>
			<br />
			<c:forEach var="subcategory" items="${category.categories}">
				<h4 id="subcat_${subcategory.id}" class="subcat"><a
					href="/guideBuilderSections.do?method=sectionUp&categoryId=${subcategory.id}&guideId=${guide.id}"><img
					src="/images/iconUp.gif" /></a> <a
					href="/guideBuilderSections.do?method=sectionDown&categoryId=${subcategory.id}&guideId=${guide.id}"><img
					src="/images/iconDown.gif" /></a> ${subcategory.title} <a
					href="/guideBuilderElements.do?method=edit&categoryId=${subcategory.id}&guideId=${guide.id}">Add/edit
				subsection elements</a> | <a class="rename" href="#">Rename this
				section</a> | <a class="delete" href="#">Delete this section</a></h4>
				<div class="newIndent1">
				<ul>
					<c:forEach var="def"
						items="${subcategory.wizardElementDefinitions}">
						<li>${def.title}</li>
					</c:forEach>
				</ul>
				</div>
			</c:forEach>
			<hr class="clearboth " />
			</div>
		</c:forEach>
		<br class="clearboth" />
		<input id="prevButton" type="button" name="prev" value="Prev: Details" />
		<input id="nextButton" type="button" name="next"
			value="Next: Finish and Publish" />

		<script type="text/javascript">
	      $$('#guideBuilderSections .cat, #guideBuilderSections .subcat').each(function(cat){
	        var id = cat.id.split('_')[1];
	        cat.down('.rename').observe('click', function(event){
	          event.stop();
	          new dialog.Prompt( {
	            messageText : 'Enter new name for this section:',
	            onOk : function(name) {
	                location.href="/guideBuilderSections.do?method=renameSection&categoryId="+id+"&name="+name + "&guideId=${guide.id}";
	            }
	          });
	        });
	        cat.down('.delete').observe('click', function(event){
	          event.stop();
	          new Ajax.Request('/guide/usage/cat/' + id, {
	            onComplete: function(transport) {
	              var resp = transport.responseJSON;
	              var numUsers = resp.data.numUsers;
	              if (numUsers > 0) {
	                new dialog.Alert('This section is already being used by ' + numUsers + ' users and can\'t be deleted.');
	              } else {
	                window.location.href='/guideBuilderSections.do?method=deleteSection&categoryId='+id + "&guideId=${guide.id}";
	              }
	            }
	          });
	        });
	      });
	    
	      $$('form.createSection').invoke('observe', 'submit', function(event){
		        if ($F(this.name).blank()) {
		          event.stop();
		          new dialog.Alert('You must enter a name for the section.').show();
		        }
	      });
	    
	      $('prevButton').observe('click', function(event){
	        event.stop();
	        window.location='/guideBuilderDetails.do?method=edit&guideId=${guide.id}';
	      });
	      
	      $('nextButton').observe('click', function(event){
	        event.stop();
	        window.location='/guideBuilderPublish.do?guideId=${guide.id}';
	      });
	    </script>
	</c:if>

	<c:if test="${guideFound != true}">
		<div style="width: 100%; text-align: center;">The requested
		Guide could not be located.</div>
	</c:if>

</tags:communityPage>

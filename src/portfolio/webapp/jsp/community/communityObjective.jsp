<!-- $Name:  $ -->
<!-- $Id: communityObjective.jsp,v 1.3 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<tags:communityPage community="${community}" pageTitle="Objectives"
	id="commObj" returnTo="editCommunity" jsModules="button,menu"
	cssClass="yui-skin-sam">
	<div><input type="button" id="new" name="new" value="New"
		style="display: none;">
	<div id="newMenu" class="yuimenu newMenu" style="display: none;">
	<div class="bd">
	<ul class="first-of-type">
		<li class="yuimenuitem"><a id="newSetLink"
			class="yuimenuitemlabel" href="javascript:void(0)">Create
		objective set from scratch</a></li>
		<li class="yuimenuitem"><a class="yuimenuitemlabel"
			href="#importMenuItem"> Import objective set from another
		community </a>
		<div id="importMenuItem" class="yuimenu">
		<div class="bd">
		<ul>
			<c:forEach var="objectiveSetEntry" items="${objectiveSetMap}">
				<li class=""><a class="communityLabel yuimenuitemlabel"
					href="#${objectiveSetEntry.key.id}"> <c:out
					value="${objectiveSetEntry.key.name}" /> </a>
				<div id="${objectiveSetEntry.key.id}" class="yuimenu">
				<div class="bd">
				<ul>
					<c:forEach var="objectiveSet" items="${objectiveSetEntry.value}">
						<li><a class="yuimenuitemlabel"
							href="/communityObjective.do?method=importSet&destCommunityId=${community.id}&objectiveId=${objectiveSet.id}">
						<c:out value="${objectiveSet.name}" /> </a></li>
					</c:forEach>
				</ul>
				</div>
				</div>
				</li>
			</c:forEach>
		</ul>
		</div>
		</div>
		</li>
	</ul>
	</div>
	</div>
	</div>


	<div id="createSetDiv" style="display: none;" class="MainContent">
	<div class="commAddForm">
	<form action="communityObjective.do" class="newForm narrowForm">
	<input type="hidden" name="method" value="add" /> <input type="hidden"
		name="communityId" value="${community.id}" />
	<fieldset>
	<ul>
		<li><label for="name">Name:</label> <input type="text"
			name="name" id="name" class="full" /></li>
		<!-- <br class="clearboth" /> -->
		<li><label for="description">Description:</label> <textarea
			name="description" id="description" class="full"></textarea></li>
	</ul>
	</fieldset>
	<fieldset class="submitset"><input type="submit"
		value="Create" /></fieldset>
	</form>
	<br class="clearboth" />
	</div>
	</div>

	<c:forEach var="objectiveSet" items="${objectiveSets}">
		<div class="objSetDiv">
		<div><strong>${objectiveSet.name}</strong> <a
			href="communityObjective.do?method=delete&objectiveId=${objectiveSet.id}"
			id="delete_${objectiveSet.id}"><img
			src="/images/fugue/icon_shadowless/cross.png" alt="Delete objective" /></a>
		<a href="#"
			onclick="Effect.toggle('editObjectiveDiv_${objectiveSet.id}', 'slide', { queue: 'end' });return false;"><img
			src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit" /></a>
		</div>

		<div id="editObjectiveDiv_${objectiveSet.id}" style="display: none;">
		<div class="commAddForm">
		<form action="communityObjective.do" class="newForm narrowForm">
		<input type="hidden" name="method" value="edit" /> <input
			type="hidden" name="objectiveId" value="${objectiveSet.id}" />
		<fieldset>
		<ul>
			<li><label for="name">Name (required):</label> <input
				type="text" name="name" id="name" class="full"
				value="${objectiveSet.name}" /></li>
			<li><label for="description">Description (optional):</label> <textarea
				name="description" id="description" class="full">${objectiveSet.description}</textarea></li>
		</ul>
		</fieldset>
		<fieldset class="submitset"><input type="submit"
			value="Update" /></fieldset>
		</form>
		</div>
		</div>

		<script type="text/javascript">addLinkConfirm('delete_${objectiveSet.id}','Are you sure you want to delete this objective set?');</script>
		<tags:objectiveList parentObjective="${objectiveSet}" level="1"
			editable="${true}" /> <br class="clearboth" />
		</div>
	</c:forEach>
	<script type="text/javascript">
    document.observe('loader:success', function(event) { 
      new YAHOO.widget.Button('new', {type: 'menu', menu: 'newMenu' });
      $('newMenu').show();
    });
    
    $('newSetLink').observe('click', function(event){
      var contentWin = new Window({maximizable: false, minimizable: false, resizable: false, minWidth: 10, destroyOnClose: true});
      contentWin.setContent('createSetDiv', true, true);
      contentWin.showCenter(true);
      
	    Windows.addObserver({ 
	      onDestroy: function(eventName, win) { 
	        if (win == contentWin) { 
	          $('createSetDiv').hide();
	          Windows.removeObserver(this);
	        } 
	      }
	    }); 
    });
    
  </script>
</tags:communityPage>

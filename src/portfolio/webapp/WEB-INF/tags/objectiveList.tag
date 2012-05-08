<%@ tag body-content="empty"%>
<%@ attribute name="parentObjective" required="true"
	type="org.portfolio.model.assessment.Objective"%>
<%@ attribute name="editable" required="true" type="java.lang.Boolean"%>
<%@ attribute name="level" required="true"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<ul id="subs_${parentObjective.id}"
	class="objectiveList level${(level - 1) mod 8 + 1} ${editable ? 'editable hasRight' : ''}">
	<c:forEach var="objective" items="${parentObjective.subObjectives}">
		<li id="li_${objective.id}" class="objectiveItem">
		<div class="objectiveInfo"><c:if test="${editable}">
			<div class="right"><a
				href="communityObjective.do?method=up&objectiveId=${objective.id}"><img
				src="/images/fugue/icon_shadowless/arrow_090.png" alt="up"
				title="Move objective up" /></a> <a
				href="communityObjective.do?method=down&objectiveId=${objective.id}"><img
				src="/images/fugue/icon_shadowless/arrow_270.png" alt="down"
				title="Move objective down" /></a> <a href="#"
				onclick="Effect.toggle('editObjectiveDiv_${objective.id}', 'slide', { queue: 'end' });return false;"><img
				src="/images/fugue/icon_shadowless/document__pencil.png" alt="edit"
				title="Edit objective" /></a> <a
				href="communityObjective.do?method=delete&objectiveId=${objective.id}"
				id="delete_${objective.id}"><img
				src="/images/fugue/icon_shadowless/cross.png" alt="delete"
				title="Delete objective" /></a> <script type="text/javascript">addLinkConfirm('delete_${objective.id}','Are you sure you want to delete this objective?');</script>
			</div>
		</c:if> ${objective.name} <c:if test="${not empty objective.description}">
			<div id="descriptionDiv_${objective.id}" class="descObj">
			${objective.description}</div>
		</c:if></div>
		<c:if test="${editable}">
			<div id="editObjectiveDiv_${objective.id}" style="display: none;">
			<div class="commAddForm">
			<form action="communityObjective.do" class="newForm narrowForm">
			<input type="hidden" name="method" value="edit" /> <input
				type="hidden" name="objectiveId" value="${objective.id}" />
			<fieldset>
			<ul>
				<li><label for="name">Name (required):</label> <input
					type="text" name="name" id="name" class="full"
					value="${objective.name}" /></li>
				<li><label for="description">Description (optional):</label> <textarea
					name="description" id="description" class="full">${objective.description}</textarea></li>
			</ul>
			</fieldset>
			<fieldset class="submitset"><input type="submit"
				value="Update" /></fieldset>
			</form>
			</div>
			</div>
		</c:if> <tags:objectiveList parentObjective="${objective}"
			editable="${editable}" level="${level + 1}" /></li>
	</c:forEach>
	<c:if test="${editable}">
		<li class="addObj"><a href="#"
			onclick="Effect.toggle('createObjectiveDiv_${parentObjective.id}', 'slide', { queue: 'end' });return false;">add
		objective</a>
		<div id="createObjectiveDiv_${parentObjective.id}"
			style="display: none;">
		<div class="commAddForm">
		<form action="communityObjective.do" class="newForm narrowForm">
		<input type="hidden" name="method" value="add" /> <input
			type="hidden" name="communityId" value="${community.id}" /> <input
			type="hidden" name="parentId" value="${parentObjective.id}" />
		<fieldset>
		<ul>
			<li><label for="name">Name (required):</label> <input
				type="text" name="name" id="name" class="full" /></li>
			<li><label for="description">Description (optional):</label> <textarea
				name="description" id="description" class="full"></textarea></li>
		</ul>
		</fieldset>
		<fieldset class="submitset"><input type="submit"
			value="Create" /></fieldset>
		</form>
		</div>
		</div>
		<br class="clearboth" />
		</li>
	</c:if>
</ul>
<c:if test="${editable}">
	<script type="text/javascript">
      Sortable.create("subs_${parentObjective.id}", {
        handles:$$('#subs_${parentObjective.id} > li > div.objectiveInfo'), 
        only:'objectiveItem',
        onUpdate: function() {
    	    var ids = $$('#subs_${parentObjective.id} > li.objectiveItem').pluck('id').collect(function(n){return n.split('_')[1];});
    	    new Ajax.Request('/communityObjective.do', {
    	    	parameters: {
    	    	  parentId: '${parentObjective.id}',
    	    	  childIds: ids.toString(),
    	    	  method: 'reorder'
    	      }
        	});
        }  
      });
      $$('#subs_${parentObjective.id} > li > div.objectiveInfo')
        .invoke('observe', 'mousedown', function(evt){this.up().addClassName('moving');})
        .invoke('observe', 'mouseup', function(evt){this.up().removeClassName('moving');});
    </script>
</c:if>

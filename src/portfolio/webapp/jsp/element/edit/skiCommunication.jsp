<!-- $Name:  $ -->
<!-- $Id: skiCommunication.jsp,v 1.4 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<h3>Name of skills</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<h3><br />
<br />
Expository writing</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.expository}" />
	</c:when>
	<c:otherwise>

			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="expositoryCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="expository" id="elementExpository" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.expository}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementExpository',
				               counterEl: 'expositoryCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose></p>

<h3>Creative writing</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.creative}" />
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="creativeCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="creative" id="elementCreative" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.creative}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementCreative',
				               counterEl: 'creativeCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>

	</c:otherwise>
</c:choose></p>

<h3>Writing in your discipline</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.discipline}" />
	</c:when>
	<c:otherwise>

			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="disciplineCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="discipline" id="elementDiscipline" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.discipline}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementDiscipline',
				               counterEl: 'disciplineCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose></p>

<h3>One-on-One interaction</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.oneOnOne}" />
	</c:when>
	<c:otherwise>

			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="oneOnOneCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="oneOnOne" id="elementOneOnOne" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.oneOnOne}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementOneOnOne',
				               counterEl: 'oneOnOneCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>

	</c:otherwise>
</c:choose></p>

<h3>Small group communication</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.smallGroup}" />
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="smallGroupCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="smallGroup" id="elementSmallGroup" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.smallGroup}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementSmallGroup',
				               counterEl: 'smallGroupCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>

	</c:otherwise>
</c:choose></p>

<h3>Small group facilitation</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.facilitation}" />
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="facilitationCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="facilitation" id="elementFacilitation" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.facilitation}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementFacilitation',
				               counterEl: 'facilitationCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose></p>

<h3>Public speaking</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.publicSpeaking}" />
	</c:when>
	<c:otherwise>
	
		
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="publicSpeakingCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="publicSpeaking" id="elementPublicSpeaking" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.publicSpeaking}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementPublicSpeaking',
				               counterEl: 'publicSpeakingCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose></p>

<h3>Listening</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.listening}" />
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="listeningCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="listening" id="elementListening" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.listening}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementListening',
				               counterEl: 'listeningCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose></p>

<h3>Conflict resolution</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.conflictRes}" />
	</c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="conflictResCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="conflictRes" id="elementConflictRes" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.conflictRes}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementConflictRes',
				               counterEl: 'conflictResCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>
	
	</c:otherwise>
</c:choose>
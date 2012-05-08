<!-- $Name:  $ -->
<!-- $Id: createShareSelectStyle.jsp,v 1.8 2011/03/01 20:41:39 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<tags:portfolioPage viewMode="share" pageTitle="Choose a style">
	<div class="MainContent"><jsp:include page="shareheader.jsp">
		<jsp:param name="new" value="${share.new}" />
		<jsp:param name="step" value="4" />
	</jsp:include>
	<h1>Step 4. Choose a Style</h1>
	<div class="Instructions">
	<p>Select a style for the appearance of this Portfolio.</p>
	</div>
	<strong>Title: <c:out value="${share.shareName}" /></strong>
	<p>Description: <c:out value="${share.shareDesc}" /></p>
	<html:form action="saveCreateShare.do" styleId="shareForm">
		<input type="hidden" name="shareId"
			value="<c:out value="${share.shareId}"/>" />
		<html:hidden property="page" value="selectStyle" />
		<input type="hidden" name="shareId" id="shareId" value="${share.shareId}" />
		<html:hidden property="publicView" />
		<%@ include file="messages.jsp"%>
		<%@ include file="errorMsgs.jsp"%>

		<h2>Theme <span>determines layout and typography<span></h2>
		<ul class="radioSet" id="themeSelectionList">
			<li><input name="theme" type="radio" value="modern" id="modern"
				${share.theme eq 'modern' or empty share.theme ? 'checked="checked" ' : ''} />
			<label for="modern">Modern <span>Clean and simple
			layout with sans-serif font.</span></label></li>
			<li><input name="theme" type="radio" value="traditional"
				id="traditional" ${share.theme eq 'traditional' ? 'checked="checked" ' : ''} />
			<label for="traditional">Traditional <span>Elegant
			layout with serif font.</span></label></li>
			<li><input name="theme" type="radio" value="friendly"
				id="friendly" ${share.theme eq 'friendly' ? 'checked="checked" ' : ''} />
			<label for="friendly">Friendly <span>Rounded corners
			with sans-serif font.</span></label></li>
		</ul>
		<h2>Color scheme</h2>
		<ul class="radioSet" id="colorSelectionList">
			<li><input name="colorScheme" type="radio" value="mono"
				id="mono" ${share.colorScheme eq 'mono' or empty
				share.colorScheme ? 'checked="checked" ' : ''} /> <label for="mono">Mono
			<span>Black, white, and gray.</span></label></li>
			<li><input name="colorScheme" type="radio" value="winter"
				id="winter" ${share.colorScheme eq 'winter' ? 'checked="checked" ' : ''} />
			<label for="winter">Winter <span>Blues and grays.</span></label></li>
			<li><input name="colorScheme" type="radio" value="spring"
				id="spring" ${share.colorScheme eq 'spring' ? 'checked="checked" ' : ''} />
			<label for="spring">Spring <span>Greens.</span></label></li>
			<li><input name="colorScheme" type="radio" value="summer"
				id="summer" ${share.colorScheme eq 'summer' ? 'checked="checked" ' : ''} />
			<label for="summer">Summer <span>Reds, yellows, and
			oranges.</span></label></li>
			<li><input name="colorScheme" type="radio" value="autumn"
				id="autumn" ${share.colorScheme eq 'autumn' ? 'checked="checked" ' : ''} />
			<label for="autumn">Autumn <span>Brown, maroon, and
			olive.</span></label></li>
		</ul>
		<h2>Header image</h2>
		<ul class="radioSet">
			<li><c:choose>
				<c:when test="${empty share.headerImageType}">
					<input name="headerImageType" type="radio" value="" id="noimage"
						checked="checked" />
				</c:when>
				<c:otherwise>
					<input name="headerImageType" type="radio" value="" id="noimage" />
				</c:otherwise>
			</c:choose> <label for="noimage">None</label></li>
			<li><c:choose>
				<c:when test="${share.headerImageType eq 'stock'}">
					<input id="stockImageTypeInput" name="headerImageType" type="radio"
						value="stock" checked="checked" />
				</c:when>
				<c:otherwise>
					<input id="stockImageTypeInput" name="headerImageType" type="radio"
						value="stock" />
				</c:otherwise>
			</c:choose> <label for="stockImageTypeInput">Select from stock images</label><br />
			<select name="stockImage" id="stockImageSelect">
				<c:forEach var="stockImageEntry" items="${stockImages}"
					varStatus="status">
					<c:choose>
						<c:when test="${share.stockImage eq stockImageEntry.key}">
							<option value="${stockImageEntry.key}" selected="selected">${stockImageEntry.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${stockImageEntry.key}">${stockImageEntry.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select></li>
		</ul>
		<input type="button" name="Preview" onclick="preview();"
			value="Preview" class="btn" />
		<br />
		<br />
		<hr />
		<br />
		<div class="share"><input type="button" class="btn" value="Back"
			onclick="goBack('createShare2a');" /> <input type="button"
			class="btn" onclick="shareCancelConfirm();" value="Cancel" /> <input
			type="button" class="btn" value="Save &amp; Finish" name="save"
			onclick="saveFinish();" /> <input type="submit" class="btn"
			value="Save &amp; Continue" /></div>
		<input type="hidden" name="nextStep" value="createShareAddTags" />
	</html:form></div>
	<script language="javascript">
    if (!$('stockImageTypeInput').checked) {
      $('stockImageSelect').disable();
    }
    $$('input[type="radio"][name="headerImageType"]').invoke('observe', 'click',
      function(e){
        if(e.element().value == 'stock'){
          $('stockImageSelect').enable();
        } else{
          $('stockImageSelect').disable();
        }
      }
    );
  
    function preview(){
      var theForm = $('shareForm'); 
      var theme = $$('input:checked[type="radio"][name="theme"]').first().value;
      var colorScheme = $$('input:checked[type="radio"][name="colorScheme"]').first().value;
      var headerImageType = $$('input:checked[type="radio"][name="headerImageType"]').first().value;
      var stockImage = $F(theForm['stockImage']);
      
      var query = "preview=true";
      
      
      var hash = $H({
        shareId: ${share.shareId}, 
        theme: theme, 
        colorScheme: colorScheme,
        preview: 'true'
      });
      
      
      if (headerImageType == 'stock') {
        hash.set('stockImage',stockImage);
      }
      var url = '/portfolio/' + ${share.shareId} + '?' + hash.toQueryString();  
      var specs = 'width=800,height=500,screenX=350,screenY=150,left=350,top=150,toolbar=yes,location=no,status=yes,scrollbars=yes,resizable=yes';
      window.open(url, '_blank', specs);
    }
  </script>
</tags:portfolioPage>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<br/>
<c:set var="parentType" value="${entry.class.name eq 'org.portfolio.model.UploadedMaterial' ? 'Material' : 'Element'}"/>
<div class="elementTags">
  <div class="elementTagsHeader">Tags</div>
  <div class="elementTagsBody">No tags exist.</div>
</div>

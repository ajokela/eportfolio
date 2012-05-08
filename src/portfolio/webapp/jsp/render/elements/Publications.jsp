<!-- $Name:  $ -->
<!-- $Id: Publications.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div>
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Author(s):</div><div>${element.author}</div>
	<div style="clear: both;"></div>
	
	<div style="white-space: nowrap; width: 135px; font-weight: bold;">Description / Abstract:</div><div>${element.description}</div>
	<div style="clear: both;"></div>
	
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Year of publication:</div><div>${element.publDate}</div>
	<div style="clear: both;"></div>
	
	<c:if test="${element.editor ne ''}">
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Editors(s):</div><div>${element.editor}</div>
	<div style="clear: both;"></div>
	</c:if>
	
	<c:if test="${element.collTitle ne ''}">
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Title of collection:</div><div>${element.collTitle}</div>
	<div style="clear: both;"></div>
	</c:if>
	
	<c:if test="${element.collVol ne ''}">
	<div style="display: block; float: left; white-space: nowrap; width: 250px; font-weight: bold;">Volume and number of collection:</div><div>${element.collVol}</div>
	<div style="clear: both;"></div>
	</c:if>
	
	<c:if test="${element.collPage ne ''}">
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Page numbers:</div><div>${element.collPage}</div>
	<div style="clear: both;"></div>
	</c:if>
	
	<c:if test="${element.pubLoc ne ''}">
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Location of publisher:</div><div>${element.pubLoc}</div>
	<div style="clear: both;"></div>
	</c:if>
	
	<c:if test="${element.pubName ne ''}">
	<div style="display: block; float: left; white-space: nowrap; width: 145px; font-weight: bold;">Name of publisher:</div><div>${element.pubName}</div>
	<div style="clear: both;"></div>
	</c:if>
	
</div>

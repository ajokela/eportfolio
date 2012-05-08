<!-- $Name:  $ -->
<!-- $Id: uploadFile.jsp,v 1.12 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<%@ page import="org.portfolio.util.Configuration"%>

<div class="yui-skin-sam uploadFile">
<div class="uploadFileInstructions">Select a file</div>
<form method="post" class="uploadFileForm" enctype="multipart/form-data">
<input type="hidden" name="elementDefId"
	value="${dataDef.elementDefinition.id}">
<div class="uploadFileInput"><input type="file" name="file"
	class="fileSelectInput" /></div>
<div class="uploadFileButtons"><input type="button" value="Upload"
	class="uploadActionButton" /> <a href="#" class="uploadCancelLink">cancel</a>
</div>
<div class="uploadFileDetails" style="display: none;"><span
	class="uploadFileProgressName"></span> <span
	class="uploadFileProgressSize"></span></div>
<span class="uploadFileProgressBar"></span></form>
</div>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>${portfolio.owner.displayName} | ${portfolio.title}</title>
  <link rel="shortcut icon" href="/favicon.ico"/>

  <link rel="stylesheet" type="text/css" href="/css/modules/core.css"/>
  <link rel="stylesheet" href="/css/templates/main.css" type="text/css" media="all"/>

  <script type="text/javascript" src="/js/prototype.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/EPF.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/modules/core.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/tiny_mce/tiny_mce.js?version=${applicationScope['org.portfolio.project.version']}"></script>

</head>
<body class="yui-skin-sam">
<div id="toolbar" class="yui-skin-sam">
  <div id="toolbar-inner">
    <div class="brand">
      <a href="/" title="Go to homepage">
        <img src="/images/loginColorBlock.gif" class="colorBlock"/>
        <img class="logo" src="/images/epfLogo.gif"/>
      </a>
    </div>
    <div class="actionsWrapper">
      <div class="actions">
        <a href="/portfolio/${portfolio.id}" id="viewButton">View</a>
      </div>
    </div>
  </div>
</div>

<div id="wrapper">
  <div id="container">
    <div id="contentWrapper">
      <div id="content" style="width: 75%;">

        <!-- Start of Masthead -->
        <div id="masthead">
          <!-- Portfolio information -->
          <div id="port_title">
            <h1 id="portfolioTitle"><c:out value="${portfolio.title}"/></h1>
          </div>
          <div id="portfolioDescriptionSection">
            <p class="description">
              <c:out value="${portfolio.description}"/>
            </p>
            <span class="noDescription">Click to add a description</span>
          </div>
          <div id="portfolioImage"></div>
        </div>
        <!--end masthead-->

        <!-- Start of Content. -->
        <div id="toolBarODoom">
        	<div style="display: block; float: left; width: 150px; height: 16pt; font-weight: bold; font-size: 16pt; display: none;" id="statusODoom">
        		Saving...
        	</div>
        	<div style="display: block; float: right;">
        		<a href="#" id="savePortfolio">Save Portfolio</a>
        	</div>
        	<div style="clear: both;"></div>
        </div>
        <div id="contentItems">
        	<ul id="menu" style="padding-top: 6px; padding-bottom: 6px;">
        	
        	</ul>
        </div>
      </div>
    </div>
  </div>
</div>
<!--end wrapper-->

<script type="text/javascript">
  EPF.use("portfolioEdit", "${applicationScope['org.portfolio.project.version']}", function() {
    new EPF.controller.portfolioEdit.Main({
      portfolio: '${portfolioJson}'.evalJSON()
    });
  });
</script>

<jsp:include page="/googleTracker.jsp"/>
</body>
</html>


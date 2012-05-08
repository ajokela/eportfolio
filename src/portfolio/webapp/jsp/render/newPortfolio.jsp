<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Create a new Portfolio</title>
  <link rel="shortcut icon" href="/favicon.ico"/>

  <link rel="stylesheet" type="text/css" href="/css/modules/core.css?version=${applicationScope['org.portfolio.project.version']}"/>
  <link rel="stylesheet" href="/css/templates/main.css?version=${applicationScope['org.portfolio.project.version']}" type="text/css" media="all"/>

  <script type="text/javascript" src="/js/prototype.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls&version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/EPF.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/modules/core.js?version=${applicationScope['org.portfolio.project.version']}"></script>
  <script type="text/javascript" src="/js/tiny_mce/tiny_mce.js?version=${applicationScope['org.portfolio.project.version']}"></script>

</head>
<body class="yui-skin-sam createPortfolio">
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
      </div>
    </div>
  </div>
</div>
<div id="wrapper">
  <div id="container">
    <div id="contentWrapper">
      <div id="content" class="create">
        <h1>Create a new portfolio</h1>
        <ul class="startOptions">
          <li>
            <label>
              <input id="templateTypeCustom" type="radio" name="templateType" value="custom" checked="checked"/>
              <span>Start from scratch</span>
            </label>
          </li>
          <li>
            <label>
              <input id="templateTypeCommunity" type="radio" name="templateType" value="community"/>
              <span>Use a community template</span>
            </label>

            <div class="communitySelectSection">
              <select name="communityId" id="communitySelect">
                <option>Select a community</option>
                <c:forEach var="communityEntry" items="${communitiesByCampus}">
                  <optgroup label="${communityEntry.key}">
                    <c:forEach var="community" items="${communityEntry.value}">
                      <c:choose>
                        <c:when
                            test="${not empty communityId and community.id eq communityId}">
                          <option value="${community.id}" selected="selected">${community.name}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${community.id}">${community.name}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </optgroup>
                </c:forEach>
              </select>
              <img id="indicator" src="/images/indicator_arrows.gif" style="display: none">
              <div id="communityTemplates"></div>
            </div>
          </li>
        </ul>
        <div class="actions">
          <button class="create">Create</button>
          <a class="cancel" href="/share/">cancel</a>
        </div>
      </div>
    </div>
  </div>
</div>
<!--end wrapper-->

<script type="text/javascript">
  EPF.use("portfolioEdit", "${applicationScope['org.portfolio.project.version']}", function() {
    new EPF.controller.portfolioEdit.create.Main({

    });
  });
</script>

<jsp:include page="/googleTracker.jsp"/>
</body>
</html>


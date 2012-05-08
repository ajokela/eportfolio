<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${portfolioFound == true}">

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://opengraphprotocol.org/schema/" xmlns:fb="http://www.facebook.com/2008/fbml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>${portfolio.person.fullName} | ${portfolio.shareName}</title>
  <link rel="shortcut icon" href="/favicon.ico"/>

    <%-- these values can be overridden by params for preview purposes --%>
  <c:choose>
    <c:when test="${not empty param.theme}"><c:set var="theme" value="${param.theme}"/></c:when>
    <c:when test="${not empty portfolio.theme}"><c:set var="theme" value="${portfolio.theme}"/></c:when>
    <c:otherwise><c:set var="theme" value="modern"/></c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${not empty param.colorScheme}"><c:set var="colorScheme" value="${param.colorScheme}"/></c:when>
    <c:when test="${not empty portfolio.colorScheme}"><c:set var="colorScheme"
                                                             value="${portfolio.colorScheme}"/></c:when>
    <c:otherwise><c:set var="colorScheme" value="mono"/></c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${not empty param.stockImage}"><c:set var="stockImage" value="${param.stockImage}"/></c:when>
    <c:otherwise><c:set var="stockImage" value="${portfolio.stockImage}"/></c:otherwise>
  </c:choose>

  <link rel="stylesheet" href="/css/templates/main.css" type="text/css" media="all"/>
  <link rel="stylesheet" href="/css/templates/assess.css" type="text/css" media="all"/>
  <!--[if lte IE 7]>
  <link rel="stylesheet" href="/css/templates/main_ie.css" type="text/css" media="all"/><![endif]-->
  <link rel="stylesheet" href="/css/templates/main_print.css" type="text/css" media="print"/>
  <c:choose>
    <c:when test="${theme eq 'friendly'}">
      <link class="themeCss" rel="stylesheet" href="/css/templates/friendly.css" type="text/css" media="all"/>
      <!--[if lte IE 7]> <link class="themeCss" rel="stylesheet" href="/css/templates/friendly_ie.css" type="text/css" media="all" /><![endif]-->
      <link class="themeCss" rel="stylesheet" href="/css/templates/friendly_print.css" type="text/css" media="print"/>
    </c:when>
    <c:when test="${theme eq 'traditional'}">
      <link class="themeCss" rel="stylesheet" href="/css/templates/traditional.css" type="text/css" media="all"/>
      <!--[if lte IE 7]> <link class="themeCss" rel="stylesheet" href="/css/templates/traditional_ie.css" type="text/css" media="all" /><![endif]-->
      <link class="themeCss" rel="stylesheet" href="/css/templates/traditional_print.css" type="text/css"
            media="print"/>
    </c:when>
    <c:otherwise>
      <link class="themeCss" rel="stylesheet" href="/css/templates/modern.css" type="text/css" media="all"/>
      <!--[if lte IE 7]> <link class="themeCss" rel="stylesheet" href="/css/templates/modern_ie.css" type="text/css" media="all" /><![endif]-->
      <link class="themeCss" rel="stylesheet" href="/css/templates/modern_print.css" type="text/css" media="print"/>
    </c:otherwise>
  </c:choose>
  <c:if test="${colorScheme ne 'mono'}">
    <link class="colorSchemeCss" rel="stylesheet" href="/css/templates/${theme}_${colorScheme}.css" type="text/css"
          media="screen"/>
  </c:if>

  <script type="text/javascript" src="/js/prototype.js"></script>
  <script type="text/javascript" src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls"></script>
  <script type="text/javascript" src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js"></script>
  <script type="text/javascript" src="/js/EPF.js"></script>
  <script type="text/javascript" src="/js/modules/core.js"></script>

  <c:if test="${portfolio.publicView}">
    <meta property="og:title" content="${fn:escapeXml(portfolio.shareName)}"/>
    <meta property="og:url" content="https://portfolio.umn.edu/portfolio/${portfolio.shareId}"/>
    <meta property="og:description" content="${fn:escapeXml(portfolio.shareDesc)}"/>
    <meta property="og:site_name" content="University of Minnesota - ePortfolio"/>
  </c:if>

</head>
<body>
<jsp:include page="/googleTracker.jsp"/>  
<div id="toolbar" class="yui-skin-sam">
  <div id="toolbar-inner">
    <div class="brand">
      <a href="/" title="Go to homepage">
        <img src="/images/loginColorBlock.gif" class="colorBlock" alt="" />
        <img class="logo" src="/images/epfLogo.gif" alt="" />
      </a>
    </div>
    <div class="actionsWrapper">
      <div class="actions">
        <c:if test="${Person ne null && Person.personId eq portfolio.personId && !previewMode}">
          <!-- 
          <a href="#" id="tagsButton">Tags</a>
          <a href="#" id="themeButton">Theme</a>
          <a href="#" id="colorsButton">Colors</a>
          -->
          <a href="/createShare1.do?shareId=${portfolio.shareId}" id="editButton">Edit</a>
          <a href="#" id="sharingButton">Share</a>
        </c:if>
        <a href="#" id="closeButton">Close</a>
      </div>
    </div>
  </div>
</div>

<c:if test="${portfolio.publicView}">
  <div id="fb-root"></div>
  <script type="text/javascript">
    window.fbAsyncInit = function() {
      FB.init({appId: '146419598704590', status: true, cookie: true, xfbml: true});
    };
    (function() {
      var e = document.createElement('script');
      e.async = true;
      e.src = document.location.protocol + '//connect.facebook.net/en_US/all.js';
      document.getElementById('fb-root').appendChild(e);
    }());
  </script>
</c:if>

<div id="wrapper">
<div id="container">

<c:if test="${hasPrivateEntries && Person ne null && Person.personId eq portfolio.personId}">
  <div id="privateEntriesWarning" class="warning" style="display:${portfolio.publicView ? 'block': 'none' }">The UM
    records in your portfolio are private and will only be visible to selected viewers.
  </div>
</c:if>

<!-- Start of Masthead -->
<div id="masthead">
  <div id="metadata">
    <dl id="metadata_list">
      <dt>Shared by</dt>
      <dd>${portfolio.person.fullName}</dd>
      <dt>Email</dt>
      <dd><a href="mailto:${portfolio.person.email}">${portfolio.person.email}</a></dd>
      <dt>Created</dt>
      <dd class="last"><fmt:formatDate value="${portfolio.dateCreated}" pattern="MM.dd.yy"/></dd>
    </dl>
  </div>

  <div>
    <div style="width: 86%; float: left;">
        <c:if test="${portfolio.publicView}">
        <fb:like href="https://portfolio.umn.edu/portfolio/${portfolio.shareId}"></fb:like>
        </c:if>
        &nbsp;
    </div>
    <div style="width: 14%; float: left;">
        <img src="/qrcode/${portfolio.shareId}" alt="portfolio qrcode" width="96" height="96"/>
    </div>
    <div style="clear: both;"></div>
 </div>

  <!-- Summary of evaluator responses and assessed components for private portfolios -->
  <c:if test="${!portfolio.publicView}">
    <c:if
        test="${portfolio.template.assessable and portfolio.template.communityId != 46 and (viewerRole eq 'owner' or viewerRole eq 'evaluator' or viewerRole eq 'coordinator')}">
      <div class="sticky_note">
        <div class="sticky_note_inner">
          <p>This is an assessable portfolio. The following individuals are assigned as evaluators:</p>
          <!-- Modify to retrieve latest assessment for each evaluator in the list -->
          <c:choose>
            <c:when
                test="${empty evaluatorList}">

              <!-- Edit can only be done one Submission/Experiment at a time. Multiple samples can be edited simultaneously only if they belong to the same Submission/Experiment. So always select a Submission/Experiment from the drop-down list on top of the page if multi-sample edit is desired. If whole Submission/Experiment edit is desired, just choose a Submission/Experiment from the drop-down and select all the samples. Alternatively, you can go to Home page, select a Submission/Experiment and click Edit. -->

              <p class="indent">There are no viewers assigned to this portfolio who are designated as evaluators.</p>
            </c:when>
            <c:otherwise>
              <!-- Programmer Note: this will be replaced by more specific code once Assesment has been adjusted to store shareId. -->
              <ul>
                <c:choose>
                  <c:when test="${assessmentModelAssignment.anonymous}">
                    <li>There are anonymous evaluators assigned to this portfolio (${fn:length(evaluatorList)}).</li>
                  </c:when>
                  <c:otherwise>
                    <c:forEach var="evaluatorEntry" items="${evaluatorList}">
                      <li><b>${evaluatorEntry.displayName}</b>&nbsp; has been assigned as an evaluator.</li>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
              </ul>
            </c:otherwise>
          </c:choose>
          <p>The following items are assessable. Click a link below to jump to any assessments that have been
            recorded.</p>
          <ul>
            <c:if test="${not empty assessmentModelAssignment}">
              <li>The <a href="#portfolioAssessment">entire portfolio</a>.</li>
            </c:if>
            <c:forEach var="category" items="${categories}">
              <c:forEach var="subcategory" items="${category.subcategories}">
                <c:forEach var="shareEntry" items="${subcategory.shareEntries}" varStatus="count">
                  <!-- If portfolio element has assessments, show assessment content -->
                  <c:if test="${shareEntry.assessmentModelAssignment ne null}">
                    <li class="${status.last ? 'last' : ''}">
                      The <a href="#shareEntry${shareEntry.id}">${shareEntry.elementName}:
                        ${shareEntry.element.entryName}</a> element in the ${category.title} : ${subcategory.title}
                      section.
                    </li>
                  </c:if>
                </c:forEach> <!-- End of for each shareEntry -->
              </c:forEach> <!-- End of for each sub-category -->
            </c:forEach> <!-- End of for each category -->
          </ul>
          <p>This note, and any assessment scores below, are only visible to the portfolio owner and individuals
            assigned as evaluators.</p>
        </div>
        <!--end sticky_note_inner-->
      </div>
      <!--end sticky_note-->
    </c:if>
  </c:if>
  <!-- Portfolio information -->
  <div id="port_title">
    <h1><c:out value="${portfolio.shareName}"/></h1>
  </div>
  <!--end port_title-->
  <div id="port_info">
    <p>
      <!--Programmer note: This would be the optional user-uploaded image. We'd need to somehow enforce max width and height dimensions.-->
      <c:if test="${not empty stockImage}">
        <img src="/images/stock/${stockImage}" alt=""/>
      </c:if>
      <c:out value="${portfolio.shareDesc}"/>
    </p>
  </div>
  <!--end port_info-->
</div>
<!--end masthead-->

  <%--<!-- Start of Table of Contents -->--%>
  <%--<div id="toc">--%>
  <%--<h4>Jump to section:</h4>--%>
  <%--<ul>--%>
  <%--<c:forEach var="category" items="${categories}" varStatus="status">--%>
  <%--<li class="${status.last ? 'last' : ''}">--%>
  <%--<a href="#section${status.count}">${category.title}</a>--%>
  <%--</li>--%>
  <%--</c:forEach>--%>
  <%--<!--PROGRAMMER NOTE: Last li needs a class of "last"-->--%>
  <%--</ul>--%>
  <%--</div>--%>
  <%--<!--end toc-->--%>

<!-- Start of Content. -->
<div id="content">
  <c:forEach var="item" items="${portfolioMap.content}" varStatus="status">
    <c:choose>
      <c:when test="${item.type == 'heading1'}">
        <div class="section" id="section${status.count}">
          <!-- div style="font-weight: bold; display: block; font-size: 13pt; width: 100%; background-color: #ccc; color: #fff; padding-top: 10px; padding-bottom: 10px;">
          
          </div -->
          <h2><span>${item.title}</span></h2>
        </div>
      </c:when>
      <c:when test="${item.type == 'heading2'}">
        <div class="section_inner">
            
            ${item.title}
            
        </div>
      </c:when>
      <c:when test="${item.type == 'entry'}">
        
        <c:set var="entry" value="${item.value}"/>
        <c:set var="email" value="E-mail Address" scope="request" />
        <c:set var="primary" value="Primary" scope="request" />
        <c:set var="preferred" value="Preferred" scope="request" />
        <c:set var="phone" value="Phone Number" scope="request" />
                
         <div id="shareEntry${entry.entryKeyId}" style="font-size: 10pt; padding-left: 15px;">
            <c:if test="${entry.elementName ne email and entry.entryName ne primary and entry.entryName ne preferred and entry.elementName ne phone}">
                <div style="padding-top: 15px; padding-bottom: 15px; font-weight: bold;">
                    ${entry.entryName}
                    <c:if test="${entry.elementTypeName} ne ''">
                        <span style="font-weight: normal;">(${entry.elementTypeName})</span>
                    </c:if>
                </div>
            </c:if>
                                
            <div style="width: 100%; padding-bottom: 10px; margin-left: 20px;">
            ${entry.entryHtml}
            
            <c:if test="${not empty entry.linkAttachments || not empty entry.fileAttachments || not empty entry.photoAttachments}">

                    <div style="margin-left: 30px;">
                    
                        <c:if test="${not empty entry.linkAttachments}">
                            <div>
                            <h4>Related Links:&nbsp;</h4>
                                <c:forEach var="link" items="${entry.linkAttachments}" varStatus="linkStatus">
                                    <a href="${fn:escapeXml(link.url)}" target="_blank">${fn:escapeXml(link.entryName)}</a>${not linkStatus.last ? ',&nbsp;' : ''}
                                </c:forEach>
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty entry.fileAttachments or not empty entry.photoAttachments}">
                            <div style="padding-top: 10px;">Attachments:</div>
                        </c:if>
                        
                        <c:if test="${not empty entry.fileAttachments}">
                            <div>
                                <ul class="files_urls attachmentsList">
                                  <c:forEach var="file" items="${entry.fileAttachments}">
                                    <li>
                                      <a href="/file/${file.entryKeyId}?portfolioId=${portfolio.shareId}">${fn:escapeXml(file.fileName)}</a>
                                      <!--(<c:if test="${file.dateCreated != null}">Created: ${file.dateCreated} / </c:if>${file.formattedSize}) -->
                                    </li>
                                  </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty entry.photoAttachments}">
                            <div>
                                <ul class="images attachmentsList">
                                  <c:forEach var="image" items="${entry.photoAttachments}">
                                    <li>
                                      <a href="/photo/${image.entryKeyId}?portfolioId=${portfolio.shareId}&amp;maxWidth=800&amp;maxHeight=800"
                                         title="${fn:escapeXml(image.fileName)}" target="_blank">
                                        <img src="/photo/${image.entryKeyId}?portfolioId=${portfolio.shareId}&amp;width=75&amp;height=75" alt="" />
                                      </a>
                                      <a href="/photo/${image.entryKeyId}?portfolioId=${portfolio.shareId}&amp;maxWidth=800&amp;maxHeight=800"
                                         title="${fn:escapeXml(image.fileName)}"
                                         target="_blank" >${fn:escapeXml(image.fileName)}</a>
                                    </li>
                                  </c:forEach>
                                </ul>
                            </div>
                            <div style="clear: both;"></div>
                        </c:if>
                    
                    </div>

            </c:if>
          <!-- If element is not from PeopleSoft and the portfolio is not a public portfolio, then allow assessment and comment content -->
          <c:if test="${!portfolio.publicView}">

                 <!-- If portfolio element has assessments, show assessment content -->
                <c:if test="${entry.assessmentModelAssignment && (viewerRole eq 'owner' or viewerRole eq 'evaluator')}">
                  <dd class="attachments">
                    <h4>Assessments:&nbsp;</h4>

                    <div id="pawrapper_${entry.entryKeyId}"><img alt="loading..." src="/images/indicator_arrows.gif"/>
                    </div>
                    <script type="text/javascript">
                      new Ajax.Updater('pawrapper_${entry.entryKeyId}', '/assessmentSection.do', {
                        evalScripts: true,
                        parameters: {
                          shareEntryId: '${entry.shareEntryId}',
                          wrapperId: 'pawrapper_${entry.entryKeyId}',
                          showAssessments: false
                        }
                      });
                    </script>
                  </dd>
                </c:if>

                <!-- If portfolio element has comments, show comment content -->
                <!-- element.shortClassName: ${element.shortClassName} -->

                <c:if test="${not empty entry.shortClassName and entry.shortClassName ne 'Email' and entry.shortClassName ne 'Address' }">

                   <dd class="attachments">
                     <h4>Comments:&nbsp;</h4>
    
                     <div id="pcwrapper_${entry.entryKeyId}"><img alt="loading..." src="/images/indicator_arrows.gif"/></div>
                     <div style="clear: both;"></div>
                     
                     <script type="text/javascript">
                       new Ajax.Updater('pcwrapper_${entry.entryKeyId}', '/commentSection.do', {
                         evalScripts: true,
                         parameters: {
                           shareEntryId: '${entry.shareEntryId}',
                           wrapperId: 'pcwrapper_${entry.entryKeyId}',
                           showComments: false
                         }
                       });
                     </script>
                   </dd>
                
                </c:if>

          </c:if>
          
          </div>
          
        </div>
      </c:when>
    </c:choose>
  </c:forEach>

  <!-- If portfolio has assessments, show assessment content -->
  <c:if test="${!portfolio.publicView}">
    <c:if test="${!empty assessmentModelAssignment && (viewerRole eq 'owner' or viewerRole eq 'evaluator')}">
      <div id="portfolioAssessment">
        <h4>Portfolio Assessments:&nbsp;${viewerRole}</h4>

        <div id="pawrapper"><img alt="loading..." src="/images/indicator_arrows.gif"/></div>
        <script type="text/javascript">
          new Ajax.Updater('pawrapper', '/assessmentSection.do', {
            evalScripts: true,
            parameters: {
              shareId: '${portfolio.shareId}',
              wrapperId: 'pawrapper',
              showAssessments: false
            }
          });
        </script>
      </div>
    </c:if>

    <!-- If portfolio has comments, show comment content -->
    <div id="portfolioComment">
      <h4>Portfolio Comments:&nbsp;</h4>

      <div id="pcwrapper"><img alt="loading..." src="/images/indicator_arrows.gif"/></div>
      <script type="text/javascript">
        new Ajax.Updater('pcwrapper', '/commentSection.do', {
          evalScripts: true,
          parameters: {
            shareId: '${portfolio.shareId}',
            wrapperId: 'pcwrapper',
            showComments: false
          }
        });
      </script>
    </div>
  </c:if>
</div>
<!--end content-->
</div>
<!--end container-->
</div>
<!--end wrapper-->
<script type="text/javascript">
  EPF.use('viewPortfolio', "${applicationScope['org.portfolio.project.version']}", function() {
    window.a = new EPF.controller.viewPortfolio.Main('${portfolio.shareId}', {
      autOpenShare: ${showShare},
      theme: '${theme}',
      colorScheme: '${colorScheme}'
    });
  });
</script>

</body>
</html>

</c:if>

<c:if test="${portfolioFound == false}">
  <html>
  <head><title>Portfolio No Longer Available</title></head>
  <body>
  <div style="width: 100%; text-align: center;">The portfolio you were looking for is no longer available for your
    viewing.
  </div>
  </body>
  </html>
</c:if>

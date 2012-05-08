<!-- $Name:  $ -->
<!-- $Id: guide.jsp,v 1.13 2011/02/16 17:39:21 rkavajec Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : Collection : ${wizard.title}">
	<div class="MainContent" id="collectionGuide" style="display: none">
	<div class="communityHome"><a href="/community/${community.id}"><c:out
		value="${community.name}" /> </a></div>
	<div id="guideHeader">
	<h1><c:out value="${wizard.title}" /></h1>
	<ul class="linkbar">
		<li><a href="#" id="aboutLink">About</a></li>
	</ul>
	<dl class="status">
		<dt>Number of Elements:</dt>
		<dd id="statusNumElements"></dd>
		<dt>Sections Completed:</dt>
		<dd id="statusNumDefs"></dd>
	</dl>
	</div>
	<div id="navset" class="yui-navset">
	<ul class="yui-nav">
		<li class="selected"><a href="#enterContent"><em>Manage
		this collection</em> </a></li>
		<li><a href="#shareContent"><em>Share this collection</em> </a></li>
	</ul>
	<div class="yui-content">
	<div id="enterContent"></div>
	<div id="shareContent"><c:if test="${wizard.shareTip != null}">
		<p id="shareTip"><c:out value="${wizard.shareTip}" /></p>
	</c:if>
	<h3>Templates Available</h3>
	<ul>
		<c:forEach begin="0" items="${wizard.templates}" var="template"
			varStatus="index">
			<li><a target="_blank"
				href="/createShare1.do?templateId=${template.templateId}&communityId=${community.id}"><c:out
				value="${template.name}" /> </a></li>
		</c:forEach>
	</ul>
	<h3>Portfolios related to this collection</h3>
	<c:choose>
		<c:when test="${empty userData.portfolios}">
			<div class="NewShare">You have no portfolios related to this collection.</div>
		</c:when>
		<c:otherwise>
			<table class="data">
				<thead>
					<tr>
						<th class="left">Actions</th>
						<th>Name/Description</th>
						<th>Template Used</th>
						<th>Shared With</th>
						<th>Created/ <br />
						Last Updated</th>
						<th class="right">Expires</th>
					</tr>
				</thead>
				<tbody>
					<!-- Begin Related portfolios loop -->
					<c:forEach begin="0" items="${userData.portfolios}" var="share"
						varStatus="index">
						<c:set var="rowClass"
							value="index.count % 2 ? 'evenRow' : 'oddRow'" />
						<tr id="portfolio_row_${share.shareId}">
							<td class="left r${index.count % 2}"><a target="_blank"
								href="/portfolio/${share.shareId}">Preview</a> <br />
							<a target="_blank"
								href="/createShare1.do?shareId=<c:out value="${share.shareId}"/>">Edit</a>
							<br />
							
							<a href="#" class="delete_portfolio" id="delete_portfolio_${share.shareId}">Delete</a>
							
							</td>
							<td class="r${index.count % 2}"><c:out
								value="${share.shareName}" /> <br />
							<c:out value="${share.shareDesc}" /></td>
							<td class="r${index.count % 2}">${share.template.name}</td>
							<td class="r${index.count % 2}"><c:forEach
								items="${share.viewersObject}" var="viewer">
								<c:out value="${viewer.person.firstName}" />&nbsp;&nbsp;<c:out
									value="${viewer.person.lastname}" />
								<br />
							</c:forEach></td>
							<td class="r${index.count % 2}"><fmt:formatDate
								value="${share.dateCreated}" pattern="d MMM yyyy" /> <br />
							<fmt:formatDate value="${share.dateModified}"
								pattern="d MMM yyyy" /></td>
							<td class="right r${index.count % 2}"><c:choose>
								<c:when test="${empty share.dateExpire}">Never</c:when>
								<c:otherwise>
									<fmt:formatDate value="${share.dateExpire}"
										pattern="d MMM yyyy" />
								</c:otherwise>
							</c:choose></td>
						</tr>
					</c:forEach>
					<!-- End Related portfolios Loops -->
				</tbody>
			</table>
		</c:otherwise>
	</c:choose></div>
	</div>
	</div>
	</div>
	<script type="text/javascript">
    EPF.use("guide", "${applicationScope['org.portfolio.project.version']}", function() {
        new EPF.controller.guide.Main({guideId:${wizard.id}});
        $('collectionGuide').show();
    });
    
    $$('.delete_portfolio').each(function(link){
    	link.observe('click', function(event){
    		event.stop();
    		
    		var ele = $(Event.element(event));
    		
    		var pieces = ele.id.split(/_/);

            new dialog.Confirm( {
                messageText : "Are you sure you wish to remove this portfolio?",
                onConfirm : function() {

                	new Ajax.Request('/deletePortfolios.do?', {
                        parameters : {
                            ids : pieces[2]
                        },
                        onSuccess : function(transport) {
                            $('portfolio_row_' + pieces[2]).fade( {
                                afterFinish : function() {
                                	$('portfolio_row_' + pieces[2]).remove();
                                }
                            });
                        }
                    });
                	
                }
            }).show();
    		
    	});
    });
    
    </script>
</tags:portfolioPage>

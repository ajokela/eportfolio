<!-- $Name:  $ -->
<!-- $Id: guestView.jsp,v 1.6 2011/03/04 17:44:10 rkavajec Exp $ -->

<!-- 3-3-2011 this page is no longer used. Guests will be directed to their homepage instead. -->

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="view" pageTitle="Portfolio : View">
	<script language="javascript">
    function confirmGuestViewDelete(shareId) {
    var confirmDialog = new dialog.Confirm( {
          headerText : 'Confirm',
          confirmButtonText : 'Delete',
          cancelButtonText : 'Cancel',
          messageText : 'Are you sure you want to delete this view?',
          confirmUrl : 'deleteGuestView.do?shareId='+shareId
        }
      );
      confirmDialog.show();
    }      
  </script>
	<div class="MainContent">
	<h1>GUEST VIEW</h1>
	<table width="90%" border="0" cellspacing="3" cellpadding="0">
		<tr>
			<td colspan="5" class="Instructions">
			<p>Within <strong>Guest View</strong>, you may access folders
			that contain information that has been shared with you by others at
			the ${applicationScope['org.portfolio.institution.longName']}.</p>
			</td>
		</tr>
		<tr>
			<td class="ShareListHeader">
			<h5>TITLE&nbsp; <c:choose>
				<c:when test="${!empty param.sortOn}">
					<a href="guestView.do"><img src="/images/sort_down_blue.gif"
						alt="Sort by Title" width="12" height="12" /></a>
				</c:when>
				<c:otherwise>
					<img src="/images/sort_down_gray.gif" height="12" width="12"
						alt="&nbsp;" />
				</c:otherwise>
			</c:choose></h5>
			</td>
			<td class="ShareListHeader">
			<h5>DESCRIPTION</h5>
			</td>
			<td class="ShareListHeader">
			<h5>DATE&nbsp; <c:choose>
				<c:when test="${param.sortOn != 'dateCreated'}">
					<a href="guestView.do?sortOn=dateCreated"><img
						src="/images/sort_down_blue.gif" alt="Sort by Date" width="12"
						height="12" /></a>
				</c:when>
				<c:otherwise>
					<img src="/images/sort_down_gray.gif" height="12" width="12"
						alt="&nbsp;" />
				</c:otherwise>
			</c:choose></h5>
			</td>
			<td class="ShareListHeader">
			<h5>SHARED BY&nbsp; <c:choose>
				<c:when test="${param.sortOn != 'lastName'}">
					<a href="guestView.do?sortOn=lastName"><img
						src="/images/sort_down_blue.gif" alt="Sort by User" width="12"
						height="12" /></a>
				</c:when>
				<c:otherwise>
					<img src="/images/sort_down_gray.gif" height="12" width="12"
						alt="&nbsp;" />
				</c:otherwise>
			</c:choose></h5>
			</td>
			<td class="ShareListHeader">
			<h5>ACTIONS</h5>
			</td>
		</tr>

		<c:choose>
			<c:when test="${empty folders}">
				<tr>
					<td colspan="5">
					<p align="center">No folders (shared by others) exist.</p>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${folders}" var="folder" varStatus="index">
					<tr class="ElementSep">
						<td style="white-space: nowrap;" class="ListData">
						<h6><a target="_blank" href="/portfolio/${folder.shareId}">${folder.shareName}</a></h6>
						</td>
						<td class="ListData">
						<p><c:choose>
							<c:when test="${empty folder.shareDesc}">
                  This folder does not have a description.
                </c:when>
							<c:when test="${fn:length(folder.shareDesc) > 50}">
                  ${fn:substring(folder.shareDesc, 0, 50)}...
                </c:when>
							<c:otherwise>
                  ${folder.shareDesc}
                </c:otherwise>
						</c:choose></p>
						</td>
						<td class="ListData">
						<p>${folder.dateCreated}</p>
						</td>
						<td class="ListData">
						<p>${folder.person.displayName}</p>
						</td>
						<td class="ListData"><a
							href="javascript:confirmGuestViewDelete('${folder.shareId}')">
						<img src="/images/trashcan_blue.gif" alt="Delete this view"
							width="18" height="22" /> </a></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	</div>
</tags:portfolioPage>

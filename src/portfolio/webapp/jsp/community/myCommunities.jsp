<!-- $Name:  $ -->
<!-- $Id: myCommunities.jsp,v 1.4 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : My Communities" jsModules="datatable">
	<div class="MainContent yui-skin-sam" id="myCommunities">
	<h1>My Communities</h1>
	<div id="dirWrapper">
	<table id="dir" style="display: none">
		<thead>
			<tr>
				<th>Community</th>
				<th>Role</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="communityInfo" items="${myCommunities}">
				<tr>
					<td>
					<a href="/community/${communityInfo.community.id}">
					<c:out value="${communityInfo.community.name}" /></a></td>
					<td>${communityInfo.role.title}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
     
	<script type="text/javascript">
	EPF.use('datatable', "${applicationScope['org.portfolio.project.version']}", function() {
        var myColumnDefs = [
            {key:"community",label:"Community",sortable:true},
            {key:"role",label:"Role", sortable:true}
        ];

        var myDataSource = new YAHOO.util.DataSource(YAHOO.util.Dom.get("dir"));
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
        myDataSource.responseSchema = {
            fields: [{key:"community"},
                    {key:"role"}
            ]
        };
        new YAHOO.widget.DataTable("dirWrapper", myColumnDefs, myDataSource);
	});
    </script>
     
    </div>
</tags:portfolioPage>

<!-- $Name:  $ -->
<!-- $Id: communityDirectory.jsp,v 1.8 2010/11/23 20:34:58 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<tags:portfolioPage viewMode="enter"
	pageTitle="ePortfolio : Community Directory" jsModules="datatable">
	<div class="MainContent commDir yui-skin-sam">
	<h1>Community Directory</h1>

	<c:if test="${Person.admin}">
		<p><a href="/community.do?method=create">Establish a community</a></p>
	</c:if> <c:if test="${not empty yourCommunities}">
		<p><strong>Your Communities:</strong> <c:forEach var="community"
			items="${yourCommunities}" varStatus="status">
			<a href="/community/${community.id}"><c:out
				value="${community.name}" /><c:if test="${not status.last}">,</c:if></a>
		</c:forEach></p>
	</c:if>
	<h3>All Communities:</h3>
	<div id="dirWrapper">
	<table id="dir" style="display: none">
		<thead>
			<tr>
				<th>Type</th>
				<th>Campus</th>
				<th>Program</th>
				<th>Name</th>
				<th>Date Created</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="community" items="${allCommunities}">
				<tr>
					<td>${community.type.label}</td>
					<td>${community.campusName}</td>
					<td>${community.program}</td>
					<td><span style="display: none"><c:out
						value="${community.name}" /></span> <%-- for sorting --%> <span
						style="display: none"><a class="goLink"
						href="/community/${community.id}">go</a></span> <%-- datatable uses this --%>
					<c:if test="${community.privateCommunity}">
						<img src="/images/fugue/icon_shadowless/lock.png" />
					</c:if> <c:choose>
						<c:when test="${community.deleted}">
							<span class="deleted"><c:out value="${community.name}" /></span>
						</c:when>
						<c:otherwise>
							<a href="/community/${community.id}"><c:out
								value="${community.name}" /></a>
						</c:otherwise>
					</c:choose></td>
					<td><fmt:formatDate value="${community.dateCreated}"
						pattern="MM/dd/yyyy" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>

	<script type="text/javascript">
      EPF.use('datatable', "${applicationScope['org.portfolio.project.version']}", function() {
      var sortFunction = function(a, b, desc) {
				    // Deal with empty values
				    if(!YAHOO.lang.isValue(a)) {
				        return (!YAHOO.lang.isValue(b)) ? 0 : 1;
				    } else if(!YAHOO.lang.isValue(b)) {
				        return -1;
				    }
				 
				    // First compare by Column2
				    var comp = YAHOO.util.Sort.compare;
				    var compState = comp(a.getData("Column2"), b.getData("Column2"), desc);
				 
				    // If values are equal, then compare by Column1
				    return (compState !== 0) ? compState : comp(a.getData("Column1"), b.getData("Column1"), desc);
				};
				      
        var myColumnDefs = [
            {key:"type",label:"Type",sortable:true},
            {key:"campus",label:"Campus", sortable:true},
            {key:"program",label:"Program", sortable:true},
            {key:"name",label:"Name",sortable:true},
            {key:"dateCreated",label:"Date Created",formatter:"date",sortable:true}
        ];

        var myDataSource = new YAHOO.util.DataSource(YAHOO.util.Dom.get("dir"));
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
        myDataSource.responseSchema = {
            fields: [{key:"type"},
                    {key:"campus"},
                    {key:"program"},
                    {key:"name"},
                    {key:"dateCreated", parser:"date"}
            ]
        };
        var dataTable = new YAHOO.widget.DataTable("dirWrapper", myColumnDefs, myDataSource);
        
        dataTable.subscribe("rowMouseoverEvent", dataTable.onEventHighlightRow);
        dataTable.subscribe("rowMouseoutEvent", dataTable.onEventUnhighlightRow);
        dataTable.subscribe("rowClickEvent", function(evt, tr) {
          window.location = evt.target.down('.goLink').href;
        });

        
      });
    </script></div>
</tags:portfolioPage>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

      	<c:if test="${not empty communityResources}">
      		<div>
      			<span style="font-weight: bold;">File Resources</span>
      		</div>
      		
      		<div>
      			<ul>
      			<c:forEach var="resource" items="${communityResources}">
      				<li>
      					<div>
      						<div>
      							<a href="/file/${resource.encodedId}?portfolioId=" target="_blank">
      							
      							<c:if test="${param.expand eq 'true'}">
      							
      							${resource.name}
      							
      							</c:if>
      							
      							<c:if test="${param.expand eq 'false'}">
      							
      							${resource.shortName}
      							
      							</c:if>
      							
      							</a>
      						</div>
      						<div style="font-size: 8pt; margin-left: 5px;">
      							<div style="display: block; float: left; width: 150px;">
      								${resource.owner.displayName}
      							</div>
      							<div style="display: block; float: left;">
      								(${resource.humanReadableSize})
      							</div>
      							<div style="clear: both;"></div>
      						
      						</div>
      						<c:if test="${not empty resource.description}">
      						<div style="font-size: 10pt; margin-bottom: 5px; margin-left: 5px;">
      							${resource.description}
      						</div>
      						</c:if>
      					</div>
      				</li>
      			</c:forEach>
      			</ul>
      		</div>
      	</c:if>
      
      	<c:if test="${not empty links}">
	       	<div style="margin-top: 10px;">
	       		<span style="font-weight: bold;">Links</span>
	       	</div>
	       	<div>
		      <ul>
		        <c:forEach var="link" items="${links}">
		          <li>
		            <a target="_blank" href="${fn:escapeXml(link.url)}"><c:out value="${link.entryName}" /></a>
		            <c:if test="${not empty link.description}">
		              <p class="itemDescription"><c:out value="${link.description}" /></p>
		            </c:if>
		          </li>
		        </c:forEach>
		      </ul>
		    </div>
		
		</c:if>
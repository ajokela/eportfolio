<!-- $Name:  $ -->
<!-- $Id: StrongIntInventory.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<div>
	<div style="font-weight: bold; display: block; float: left; width: 85px;">Date Taken:</div>
	<div>${element.inventoryDate}</div>
	<div style="clear: both;"></div>
	
	<div><span style="font-weight: bold; padding-bottom: 5px;">Key:</span>&nbsp;
		<span style=" font-size: 8pt;">
			<span style="font-weight: bold;">Very High:&nbsp;</span>V,&nbsp;
			<span style="font-weight: bold;">High:&nbsp;</span>H,&nbsp;
			<span style="font-weight: bold;">Average:&nbsp;</span>A,&nbsp;
			<span style="font-weight: bold;">Little:&nbsp;</span>L,&nbsp;
			<span style="font-weight: bold;">Very Little:&nbsp;</span>VL&nbsp;
		</span>
	
	</div>
	
	<div style="padding-bottom: 10px;"><hr /></div>
	
	<div>
		<div style="font-weight: bold; width: 95px; float: left; display: block;">Realistic:</div>
		<div>${element.realistic}</div>
		<div style="clear: both;"></div>

		<div style="font-weight: bold; width: 95px; float: left; display: block;">Investigative:</div>
		<div>${element.investigative}</div>
		<div style="clear: both;"></div>

		<div style="font-weight: bold; width: 95px; float: left; display: block;">Artistic:</div>
		<div>${element.artistic}</div>
		<div style="clear: both;"></div>

		<div style="font-weight: bold; width: 95px; float: left; display: block;">Social:</div>
		<div>${element.social}</div>
		<div style="clear: both;"></div>

		<div style="font-weight: bold; width: 95px; float: left; display: block;">Enterprising:</div>
		<div>${element.enterprising}</div>
		<div style="clear: both;"></div>

		<div style="font-weight: bold; width: 95px; float: left; display: block;">Conventional:</div>
		<div>${element.conventional}</div>
		<div style="clear: both;"></div>
		
	</div>
	
	<div style="padding-bottom: 10px;"></div>
	
	<div style="display: block; width: 50%; float: left;">
		<div style="font-weight: bold; bottom-padding: 5px;">Basic Interest Scales</div>
		<c:set var="count" value="1" />
		
		<c:if test="${element.interest1 ne ''}">
		
			<div>${count}.&nbsp;
			${element.interest1}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.interest2 ne ''}">
		
			<div>${count}.&nbsp;
			${element.interest2}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.interest3 ne ''}">
		
			<div>${count}.&nbsp;
			${element.interest3}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.interest4 ne ''}">
		
			<div>${count}.&nbsp;
			${element.interest4}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.interest5 ne ''}">
		
			<div>${count}.&nbsp;
			${element.interest5}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

	</div>
	
	<div style="display: block; width: 50%; float: left;">
		<div style="font-weight: bold; bottom-padding: 5px;">Occupational Scales</div>
		<c:set var="count" value="1" />

		<c:if test="${element.occupation1 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation1}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>
	

		<c:if test="${element.occupation2 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation2}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>
		
		<c:if test="${element.occupation3 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation3}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.occupation4 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation4}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.occupation5 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation5}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>
		
		<c:if test="${element.occupation6 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation6}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.occupation7 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation7}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>
		
		
		<c:if test="${element.occupation8 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation8}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>
		
		<c:if test="${element.occupation9 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation9}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

		<c:if test="${element.occupation10 ne ''}">
		
			<div>${count}.&nbsp;
			${element.occupation10}
			</div>
			
			<c:set var="count" value="${count + 1}" />
	
		</c:if>

	</div>
	<div style="clear: both;"></div>
	
	
	<div style="padding-bottom: 10px;"></div>
	
	<div>
		<div style="font-weight: bold; bottom-padding: 5px;">Interpretation/Reaction</div>
		<div>
			${element.interpretation}
		</div>
		
	</div>
		
</div>

<!-- 
<tags:elementField heading="Date Taken">${element.inventoryDate}</tags:elementField>
<tags:elementField heading="Key">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="V">Very high</tags:innerElementField>
		<tags:innerElementField heading="H">High</tags:innerElementField>
		<tags:innerElementField heading="A">Average</tags:innerElementField>
		<tags:innerElementField heading="L">Little</tags:innerElementField>
		<tags:innerElementField heading="VL">Very little</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Realistic">${element.realistic}</tags:elementField>
<tags:elementField heading="Investigative">${element.investigative}</tags:elementField>
<tags:elementField heading="Artistic">${element.artistic}</tags:elementField>
<tags:elementField heading="Social">${element.social}</tags:elementField>
<tags:elementField heading="Enterprising">${element.enterprising}</tags:elementField>
<tags:elementField heading="Conventional">${element.conventional}</tags:elementField>
<tags:elementField heading="Occupational Scales">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="1.">${element.interest1}</tags:innerElementField>
		<tags:innerElementField heading="2.">${element.interest2}</tags:innerElementField>
		<tags:innerElementField heading="3.">${element.interest3}</tags:innerElementField>
		<tags:innerElementField heading="4.">${element.interest4}</tags:innerElementField>
		<tags:innerElementField heading="5.">${element.interest5}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Occupational Scales">
	<tags:innerElementFieldList>
		<tags:innerElementField heading="1.">${element.occupation1}</tags:innerElementField>
		<tags:innerElementField heading="2.">${element.occupation2}</tags:innerElementField>
		<tags:innerElementField heading="3.">${element.occupation3}</tags:innerElementField>
		<tags:innerElementField heading="4.">${element.occupation4}</tags:innerElementField>
		<tags:innerElementField heading="5.">${element.occupation5}</tags:innerElementField>
		<tags:innerElementField heading="6.">${element.occupation6}</tags:innerElementField>
		<tags:innerElementField heading="7.">${element.occupation7}</tags:innerElementField>
		<tags:innerElementField heading="8.">${element.occupation8}</tags:innerElementField>
		<tags:innerElementField heading="9.">${element.occupation9}</tags:innerElementField>
		<tags:innerElementField heading="10.">${element.occupation10}</tags:innerElementField>
	</tags:innerElementFieldList>
</tags:elementField>
<tags:elementField heading="Interpretation/Reaction" wysiwyg="true">${element.interpretation}</tags:elementField>

-->


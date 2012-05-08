<!-- $Name:  $ -->
<!-- $Id: copyCommunity.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" jsModules="button,dhtmlcalendar" pageTitle="Community : Copy" id="communityMessgaes" returnTo="Community">
	<div class="MainContent communityHome yui-skin-sam" id="communityInfo" style="width: 100%;">
       <SCRIPT language="Javascript">
            function validateForm(){
                if($('name').value=="")
                {
                    new dialog.Alert({messageText:'Please enter unique name for the new community'}).show();
                    return false;
                }
                return true;
            }
        </SCRIPT>
		
		<div style="text-align: center; width: 50%; float: left;">
			<div style="text-align: left;">
				<form action="/community/copy/post/${community.id}" method="post" onSubmit="return validateForm();">
				
				<div>
	
				<input type="text" name="name" id="name" size="40" /><br />
				<span style="font-size: 10pt;">New Community Name</span><br /><br />
				
				</div>
				
				<div style="clear: both;"></div>
				
				<div>
					
					<input type="checkbox" name="members" value="true" />&nbsp;Include Members from Original Community
					
				</div>
				
				<div style="clear: both;"></div>
								
				<input type="submit" name="submit" id="submit" value="Copy" />
				
				</form>
			</div>
		</div>

		<div style="text-align: center; width: 50%; float: right;">
			<c:forEach var="message" items="${messages}">
			
			</c:forEach>
		</div>

		<div style="clear:both;"></div>
			
	</div>
</tags:communityPage>
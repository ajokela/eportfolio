<!-- $Name:  $ -->

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<tags:portfolioPage viewMode="general" pageTitle="ePortfolio : Access Denied">

    <div class="MainContent" id="accessDeniedPage">
	    <div style="font-size: 18pt; font-weight: bold; text-align: center;">
            Access Denied
	    </div>
	    <div style="padding-top: 30px; text-align: center;">
            <p>
    	    The requested Portfolio could not be accessed.  This is mostly likely due to the Portfolio having not been shared with you.
	        </p
	    </div>
	    <div style="text-align: center;">
	        <p>
	         With the button below, you may message the Portfolio's owner requesting a share.<br /><br />
	        </p>
	        <form action="/requestAccess" method="post">
	           <input type="hidden" name="owner" value="${owner}" />
	           <input type="hidden" name="viewer" value="${viewer}" />
	           <input type="hidden" name="portfolio" value="${portfolio}" />
	           <input type="submit" name="submit" value="Send a share request to this Portfolio's owner" /> 
	        </form>
	        
	    </div>
    </div>
</tags:portfolioPage>
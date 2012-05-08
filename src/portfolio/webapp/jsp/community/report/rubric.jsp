<!-- $Name:  $ -->
<!-- $Id: rubric.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tags:communityPage community="${community}" pageTitle="Rubric Report" id="individualTemplateReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
    <div id="reportCriteria">
    
    <form action="/community/reports/rubric/${community.id}" method="get">
    
    <c:set var="dateFromString" value="None" />
    <c:set var="dateToString" value="None" />
    
    <c:if test="${dateFromString != null and dateFromString ne '-1'}">
        <c:set var="dateFromString" value="${dateFromString}" />
    </c:if>

    <c:if test="${dateToString != null and dateToString ne '-1'}">
        <c:set var="dateToString" value="${dateToString}" />
    </c:if>

    
    <dl>
        <dd style="margin-bottom: 5px;">
        <div>
        <div style="display: block; float: left;"><strong>From:</strong>
        <input type="text" id="dateFrom" name="dateFromString" value="${dateFromString}"
            style="width: 100px;" readonly="readonly" />&nbsp; <img id="trigger1"
            src="/images/calendar.jpg" style="cursor: pointer;" /> <a href="#"
            onclick="$('dateFrom').value='None';return false;">clear</a> <script
            type="text/javascript">
                    document.observe('loader:success', function() {
                        var today = new Date();
                        Calendar.setup({
                            inputField: 'dateFrom',
                            ifFormat: "%m/%d/%Y",
                            button: 'trigger1',
                            weekNumbers: false
                            
                          }
                        );
                    }); 
                </script></div>
        </div>
        <div>
        <div style="display: block; float: left;"><strong>To:</strong> <input
            type="text" id="dateTo" name="dateToString" value="${dateToString}"
            style="width: 100px" readonly="readonly" />&nbsp; <img id="trigger2"
            src="/images/calendar.jpg" style="cursor: pointer;" /> <a href="#"
            onclick="$('dateTo').value='None';return false;">clear</a> <script
            type="text/javascript">
                    document.observe('loader:success', function() {
                        var today = new Date();
                        Calendar.setup({
                            inputField: 'dateTo',
                            ifFormat: "%m/%d/%Y",
                            button: 'trigger2',
                            weekNumbers: false
                            
                          }
                        );
                    }); 
                </script></div>
        </div>

        <div style="clear: both;"></div>

        <div style="height: 25px;">
            &nbsp;
        </div>
        
        <div>
            <span style="font-size: 8pt;">Rubric:</span><br />
            <select name="rubric">
                
            <c:forEach var="rubric" items="${rubrics}">
                <option value="${rubric.id}" <c:if test="${rubric_id eq rubric.id}">selected="selected"</c:if>>${rubric.name}</option>
            </c:forEach>
            
            </select>
        </div>

        <div style="padding-top: 10px;">
            <span style="font-size: 8pt;">Template:</span><br />
            <select name="template">
                
            <c:forEach var="template" items="${templates}">
                <option value="${template.id}" <c:if test="${template_id eq template.id}">selected="selected"</c:if>>${template.name}</option>
            </c:forEach>
            
            </select>
        </div>
        </dd>

        <dt>&nbsp;</dt>


        <dd><input type="submit" name="report" value="Run" /></dd>
        
    </dl>
    </form>
    </div>
    <c:if test="${report != null}">
        
        <!-- /community/reports/rubric/export/{communityid}/{rubric}/{template}/{dateFromString}/{dateToString} 
        
            https://eportfolio-dev.oit.umn.edu:26443/community/reports/rubric/export/520/1000/1563/-1/-1
        
        -->
        
        <a href="#" id="exportExcel">Export to Excel...</a>
        
        <script type="text/javascript">
        
       var exportExcel = $("exportExcel");
       
       exportExcel.observe('click', function(event){
    	   
    	   var dateFrom = Base64.encode($('dateFrom').value);
    	   var dateTo   = Base64.encode($('dateTo').value);
    	   
    	   var url = "/community/reports/rubric/export/${community.id}/${rubric_id}/${template_id}/" + dateFrom.replace(/(\n|\r)+$/, '') + "/" + dateTo.replace(/(\n|\r)+$/, '');
    	   
    	   window.location = url;
    	   
       });
        
        </script>        
        
        <div>
            <table style="width: 100%; padding-bottom: 25px; border-bottom: 1px solid #eee;">
                <tr>
                    <td style="font-weight: bold; width: 30%; background-color: #eee;">
                    Portfolios Matching Rubric &amp; Template:
                    </td>
                    <td style="background-color: #eee;">
                        ${portfolios_count}
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold;">
                    Portfolios with Preliminary and/or Final:
                    </td>
                    <td>
                        ${portfolios_with_prelim_and_final_count}
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; background-color: #eee;">
                    Portfolios with Preliminary:
                    </td>
                    <td style="background-color: #eee;">
                        ${portfolios_with_prelim_count}
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold;">
                    Portfolios with Final:
                    </td>
                    <td>
                        ${portfolios_with_final_count}
                    </td>
                </tr>
                
                <tr>
                    <td style="font-weight: bold; background-color: #eee;">
                    Portfolios with Only Preliminary:
                    </td>
                    <td style="background-color: #eee;">
                        ${portfolios_with_only_prelim_count}
                    </td>
                </tr>
                
            </table>
        </div>
        
        <div>
        
        <table style="width: 100%;">
            <tr>
               <td colspan="2" style="background-color: #eee; border-right: 1px solid #ccc;">
                   <span style="font-weight: bold; font-size: 12pt;">Assessments Summary (Element Counts)</span>
               </td>
               
               <c:forEach var="scoringModelValue" items="${assessmentModel.scoringModel.scores}" varStatus="sStatus">
                   <td style="text-align: center; font-weight: bold; background-color: #eee; border-right: 1px solid #ccc;" colspan="2">
                       ${scoringModelValue}
                   </td>
               </c:forEach>
            </tr>
               
            <tr>
               <td colspan="2" style="border-right: 1px solid #ccc;"></td>
               <c:forEach var="scoringModelValue" items="${assessmentModel.scoringModel.scores}" varStatus="sStatus">
                   <td style="text-align: center; background-color: #eee; border-right: 1px solid #ccc;">
                       Prelim
                   </td>
                   <td style="text-align: center; background-color: #eee; border-right: 1px solid #ccc;">
                       Final
                   </td>
                   
               </c:forEach>
            </tr>

            <c:forEach var="objective" items="${objectives}" varStatus="objStatus">    
	            
	           <c:set var="color" value="#fff" />
	           
	           <c:set var="scores_p" value="${global_portfolio_pMap[objStatus.index]}" />
	           <c:set var="scores_f" value="${global_portfolio_fMap[objStatus.index]}" />
	           
               <c:if test="${objStatus.index % 2 != 0}">
                    <c:set var="color" value="#eee" />
               </c:if>

                <tr>
                    <td style="width: 20px; background-color: ${color};">&nbsp;</td>
                    <td style="background-color: ${color}; border-right: 1px solid #ccc; width: 33%;">
                        ${objective.name}
                    </td>
                    
                    <c:forEach var="scoringModelValue" items="${assessmentModel.scoringModel.scores}" varStatus="sStatus">
                        
                        <td style="text-align: center; background-color: ${color}; border-right: 1px solid #ccc;">
                            ${scores_p[sStatus.index]}
                        </td>
                        
                        <td style="text-align: center; background-color: ${color}; border-right: 1px solid #ccc;">
                            ${scores_f[sStatus.index]}
                        </td>
                        
                    </c:forEach>
                    
                </tr>
	           
	            
	        </c:forEach>
        </table>
        
        </div>
        <div style="height: 45px;">
        &nbsp;
        </div>
        <div>
        <table style="width: 100%;">
        <c:forEach var="item" items="${portfolioAssessmentsMap}" varStatus="pStatus">    
            <c:set var="portfolio" value="${item.key}" />
            <c:set var="assessmentsMap" value="${item.value}" />
            
	        <tr>
	           
	           <td colspan="2" style="background-color: #eee; border-right: 1px solid #ccc; width: 33%;">
	               <a href="/portfolio/${portfolio.shareId}"><span style="font-weight: bold; font-size: 11pt;">${portfolio.shareName}</span></a> (${portfolio.person.fullName})
	           </td>
	           
	           <c:forEach var="scoringModelValue" items="${assessmentModel.scoringModel.scores}" varStatus="sStatus">
	               <td style="text-align: center; font-weight: bold; background-color: #eee; border-right: 1px solid #ccc;" colspan="2">
	                   ${scoringModelValue}
	               </td>
	           </c:forEach>
	           
	        </tr>
	        
	        <tr>
	           <td colspan="2" style="border-right: 1px solid #ccc;"></td>
	           <c:forEach var="scoringModelValue" items="${assessmentModel.scoringModel.scores}" varStatus="sStatus">
	               <td style="text-align: center; background-color: #eee; border-right: 1px solid #ccc;">
	                   Prelim
	               </td>
	               <td style="text-align: center; background-color: #eee; border-right: 1px solid #ccc;">
                       Final
                   </td>
                   
               </c:forEach>
	        </tr>
	        
            <c:forEach var="objective" items="${objectives}" varStatus="objStatus">
				<c:set var="color" value="#fff" />
				<c:if test="${objStatus.index % 2 != 0}">
				    <c:set var="color" value="#eee" />
				</c:if>

                <tr>
                    <td style="width: 20px; background-color: ${color};">&nbsp;</td>
                    <td style="background-color: ${color}; border-right: 1px solid #ccc; width: 33%;">
                        ${objective.name}
                    </td>
                    
                    <c:forEach var="scoringModelValue" items="${assessmentModel.scoringModel.scores}" varStatus="sStatus">
                        
                        <td style="text-align: center; background-color: ${color}; border-right: 1px solid #ccc;">
                            ${assessmentsMap['prelim'][objStatus.index][sStatus.index]}
                        </td>
                        
                        <td style="text-align: center; background-color: ${color}; border-right: 1px solid #ccc;">
                           ${assessmentsMap['final'][objStatus.index][sStatus.index]}
                        </td>
                        
                    </c:forEach>
               
                </tr>
            </c:forEach>
            <tr>
                <td colspan="${(assessmentModelCount * 2) + 2}" style="border-top: 1px solid #ccc;">&nbsp;</td>
            </tr>

        </c:forEach>
        </table>
        </div>
    </c:if>

</tags:communityPage>



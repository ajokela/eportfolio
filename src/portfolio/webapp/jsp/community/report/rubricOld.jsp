<!-- $Name:  $ -->
<!-- $Id: rubric.jsp,v 1.2 2011/03/22 16:04:05 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<tags:communityPage community="${community}" pageTitle="Rubric Report" id="individualTemplateReport" jsModules="button,dhtmlcalendar" cssClass="yui-skin-sam report" returnTo="communityReports">
	<div id="reportCriteria">
	
	<form action="/community/reports/rubric/${community.id}" method="get">
	
	<dl>
		<dd style="margin-bottom: 5px;">
		<div>
		<div style="display: block; float: left;"><strong>From:</strong>
		<input type="text" id="dateFrom" name="dateFromString" value="None"
			style="width: 100px" readonly="readonly" />&nbsp; <img id="trigger1"
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
			type="text" id="dateTo" name="dateToString" value="None"
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
		</dd>

		<div style="clear: both;"></div>

		<div style="height: 25px;">
			&nbsp;
		</div>
		
		<div>
			<select name="rubric">
				
			<c:forEach var="rubric" items="${rubrics}">
				<option value="${rubric.id}" <c:if test="${rubric_id eq rubric.id}">selected="selected"</c:if>>${rubric.name}</option>
			</c:forEach>
			
			</select>
		</div>

        <div>
            <select name="template">
                
            <c:forEach var="template" items="${templates}">
                <option value="${template.id}" <c:if test="${template_id eq template.id}">selected="selected"</c:if>>${template.name}</option>
            </c:forEach>
            
            </select>
        </div>


		<dt>&nbsp;</dt>


		<dd><input type="submit" name="report" value="Run" /></dd>
		
	</dl>
	</form>
	</div>
	
	
	<c:if test="${report != null}">
		<div>

		<table class="rubric" border=0 cellspacing="0">
			<tr>
				<th colspan="${(assessmentModelCount * 2) + 1}"><span style="font-size: 12pt; font-weight: bold;">${assessmentModel.name}</span></th>
			</tr>
			<tr>
				<td colspan="${(assessmentModelCount * 2) + 1}">${assessmentModel.description}</td>
			</tr>
			
			<tr>
                <td colspan="${(assessmentModelCount * 2) + 1}">&nbsp;</td>
			</tr>
			
			<tr>
			    <td><b>Portfolios Assessed/Evaluated:</b></td>
			    <td>${portfolios_size}</td>
                <td colspan="${((assessmentModelCount * 2) + 1) - 2}">&nbsp;</td>
            </tr>
            
            <tr>
                <td><b>Elements in All Portfolios:</b></td>
                <td>${elementsCount}</td>
                <td colspan="${((assessmentModelCount * 2) + 1) - 2}">&nbsp;</td>
            </tr>

            <tr>
                <td><b>Assessments (Element-based):</b></td>
                <td>${assessmentsCount}</td>
                <td colspan="${((assessmentModelCount * 2) + 1) - 2}">&nbsp;</td>
            </tr>

            <tr>
                <td><b>Scores (Preliminary &amp; Final):</b></td>
                <td>${scoresCount}</td>
                <td colspan="${((assessmentModelCount * 2) + 1) - 2}">&nbsp;</td>
            </tr>
			
			<tr><td colspan="${((assessmentModelCount * 2) + 1)}">&nbsp;</td></tr>
			
			<tr>
                <td><b>Portfolios per Template:</b></td>
                <td colspan="${((assessmentModelCount * 2) + 1) - 1}">&nbsp;</td>
			</tr>
			
			<c:forEach var="tm" items="${templateCounts}" varStatus="tmStat">
            <tr>
                
                <td>${tm.key.name}</td>
                <td>${tm.value}</td>
                <td colspan="${((assessmentModelCount * 2) + 1) - 2}">&nbsp;</td>
                
            </tr>
			</c:forEach>
			
			<tr>
				<th>&nbsp;</th>
				<c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
					<th class="scoreNameCell" style="width: ${(100 / (assessmentModelCount)) / 2}%; text-align: center; font-weight: bold;" colspan="2"><c:out value="${col}" /></th>
				</c:forEach>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
					<td style="width: ${(100 / (assessmentModelCount)) / 4}%; text-align: center; background-color: #ccc; border-top: 1px solid #000; padding-bottom: 2px;">Pre</td>
					<td style="width: ${(100 / (assessmentModelCount)) / 4}%; text-align: center; background-color: #ccc; border-top: 1px solid #000; padding-bottom: 2px;">Fin</td>
				</c:forEach>

			</tr>
			
			<c:forEach var="objective" items="${assessmentModel.assessedObjectives}" varStatus="outerLoop">
				<c:set var="color" value="#fff" />
				<c:if test="${outerLoop.index % 2 != 0}">
					<c:set var="color" value="#ccc" />
				</c:if>
				
				<tr>
					<td style="background-color: ${color};"><c:out value="${objective.name}" /></td>
					<c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
						
						<td style="text-align: center; background-color: ${color}; ">
							${pMap[outerLoop.index][loop.index]} 
						</td>
						
						<td style="text-align: center; background-color: ${color}; border-right: 1px solid #000;">
							${fMap[outerLoop.index][loop.index]} 
						</td>
						
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<br /><br />
		<table style="width: 100%;">
		  <c:forEach var="item" items="${portfolioAssessments}" varStatus="status">
		  <!-- item.key = Portfolio; item.value = List<Assessment> -->
		  
		  <c:set var="portfolio" value="${item.key}" />
		  <c:set var="assessments" value="${items.value}" />
		  
		  <tr>
		      <td colspan="${((assessmentModelCount * 2) + 1)}" style="width: 100%;">&nbsp;</td>
		  </tr>
		  
		  <tr>
		     <td colspan="${((assessmentModelCount * 2) + 1)}" style="font-weight: bold;">${status.index + 1}. ${portfolio.person.fullName} (<i>Portfolio: ${portfolio.shareName}</i>)</td>
		  </tr>

          <tr>
             <td colspan="${((assessmentModelCount * 2) + 1)}">Template: ${portfolio.template.name}</td>
          </tr>
		  
		  <!-- ------------------------------------------------------- -->
		  
		    <tr>
                <th>&nbsp;</th>
                <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
                    <th class="scoreNameCell" style="width: ${(100 / (assessmentModelCount)) / 2}%; text-align: center; font-weight: bold;" colspan="2"><c:out value="${col}" /></th>
                </c:forEach>
            </tr>
            
            <tr>
                <td>&nbsp;</td>
                <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
                    <td style="width: ${(100 / (assessmentModelCount)) / 4}%; text-align: center; background-color: #ccc; border-top: 1px solid #000; padding-bottom: 2px;">Pre</td>
                    <td style="width: ${(100 / (assessmentModelCount)) / 4}%; text-align: center; background-color: #ccc; border-top: 1px solid #000; padding-bottom: 2px;">Fin</td>
                </c:forEach>

            </tr>
            
            <c:forEach var="objective" items="${assessmentModel.assessedObjectives}" varStatus="outerLoop">
                <c:set var="color" value="#fff" />
                <c:if test="${outerLoop.index % 2 != 0}">
                    <c:set var="color" value="#ccc" />
                </c:if>
                
                <tr>
                    <td style="background-color: ${color};"><c:out value="${objective.name}" /></td>
                    <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
                        
                        <td style="text-align: center; background-color: ${color}; ">
                            ${portfolioScorespMap[portfolio][outerLoop.index][loop.index]} 
                        </td>
                        
                        <td style="text-align: center; background-color: ${color}; border-right: 1px solid #000;">
                            ${portfolioScoresfMap[portfolio][outerLoop.index][loop.index]} 
                        </td>
                        
                    </c:forEach>
                </tr>
            </c:forEach>
		  
		  </c:forEach>
		</table>
		
		</div>
		
	</c:if>

</tags:communityPage>

                            /*
                            if(assessmentModelAssessedObjectivesScoresMap.get(am) == null) {
                                
                                Map<AssessedObjective, Map<String, Integer>>objectivesScoresMap        = new LinkedHashMap<AssessedObjective, Map<String, Integer>>();
                                Map<AssessedObjective, Map<String, Integer>>objectivesScoresMap_final  = new LinkedHashMap<AssessedObjective, Map<String, Integer>>();
                                Map<AssessedObjective, Map<String, Integer>>objectivesScoresMap_prelim = new LinkedHashMap<AssessedObjective, Map<String, Integer>>();
                                
                                List<AssessedObjective>objectives = am.getAssessedObjectives();
                                
                                for(Iterator<AssessedObjective>q = objectives.iterator(); q.hasNext();) {
                                    
                                    AssessedObjective ao = q.next();
                                    
                                    Map<String, Integer>scoresMap = new LinkedHashMap<String, Integer>();
                                    Map<String, Integer>scoresMap_final = new LinkedHashMap<String, Integer>();
                                    Map<String, Integer>scoresMap_prelim = new LinkedHashMap<String, Integer>();
                                    
                                    for(int k=0; k<am.getScoringModel().getScores().length; ++k) {
                                        scoresMap.put(am.getScoringModel().getScores()[k], 0);
                                        scoresMap_final.put(am.getScoringModel().getScores()[k], 0);
                                        scoresMap_prelim.put(am.getScoringModel().getScores()[k], 0);
                                    }
                                    
                                    objectivesScoresMap.put(ao, scoresMap);
                                    objectivesScoresMap_final.put(ao, scoresMap_final);
                                    objectivesScoresMap_prelim.put(ao, scoresMap_prelim);
                                }
                                
                                assessmentModelAssessedObjectivesScoresMap.put(am, objectivesScoresMap);
                                assessmentModelAssessedObjectivesScoresMap_final.put(am, objectivesScoresMap_final);
                                assessmentModelAssessedObjectivesScoresMap_prelim.put(am, objectivesScoresMap_prelim);
    
                            }
                            */
                            
                                                // portfolioAssessments.put(p, assessments);
                    
                    // assessmentsCount += assessments.size();
                    
                    /*
                    for(Iterator<AssessmentModel>x = assessmentModelAssessmentsMap.keySet().iterator(); x.hasNext();) {
                        AssessmentModel assessmentModel = x.next();
                        
                        List<Assessment>assessments = assessmentModelAssessmentsMap.get(assessmentModel);
                        
                        for(Iterator<Assessment> j = assessments.iterator(); j.hasNext();) {
                            Assessment assessment = j.next();
                            
                            String[] scores = assessment.getScores();
                            
                            List<AssessedObjective>assessedObjectives = assessmentModel.getAssessedObjectives();
                            
                            Map<AssessedObjective, Map<String, Integer>>assessedObjectivesMap = null;
                            
                            if(assessment.isFinal()) {
                                assessedObjectivesMap = assessmentModelAssessedObjectivesScoresMap_final.get(assessmentModel);
                            }
                            else {
                                assessedObjectivesMap = assessmentModelAssessedObjectivesScoresMap_prelim.get(assessmentModel);
                            }
                            
                            if(assessedObjectivesMap != null) {
                                for(Iterator<AssessedObjective>y = assessedObjectives.iterator(); y.hasNext();) {
                                    AssessedObjective objective = y.next();
                                    Map<String, Integer>matrixScores = assessedObjectivesMap.get(objective);
                                    
                                    for(int z=0; z<scores.length; ++z) {
                                        Integer scoreCount = matrixScores.get(scores[z]);
                                        scoreCount++;
                                        matrixScores.put(scores[z], scoreCount);
                                    }
                                    
                                    
                                    
                                }
                            }
                        }
                    }
                    */
                    
                    /*
                    Integer[][] portfolio_fMap = new Integer[objectives.size()][];
                    Integer[][] portfolio_pMap = new Integer[objectives.size()][];
                    
                    for(Integer z=0; z<objectives.size(); ++z) {
                        
                        portfolio_fMap[z] = new Integer[assessmentModel.getScoringModel().getScores().length];
                        portfolio_pMap[z] = new Integer[assessmentModel.getScoringModel().getScores().length];
                        
                        for(int y=0; y<assessmentModel.getScoringModel().getScores().length; ++y) {
                            portfolio_fMap[z][y] = 0;
                            portfolio_pMap[z][y] = 0;
                        }
                        
                    }
                    
                    for(Iterator<Assessment> j = assessments.iterator(); j.hasNext();) {
                        Assessment assessment = j.next();
                        
                        //assessment.
                        
                        String[] scores = assessment.getScores();
                        
                        scoresCount += scores.length;
                        
                        if(assessment.isFinal()) {
                            final_assessments.add(assessment);
                            
                            for(int x=0; x<scores.length; ++x) {
                                Integer pos = posMap.get(scores[x]);
                                
                                try {
                                    fMap[x][pos]++;
                                    portfolio_fMap[x][pos]++;
                                    logService.debug("------------ ==> pos '" + pos + "' - score[" + x + "] '" + scores[x] + "'");
                                }
                                catch (Exception e) {
                                    //logService.debug(e);
                                    logService.debug("ERR: portfolio[" + p.getShareId() + "]  ==> pos '" + pos + "' - score[" + x + "] '" + scores[x] + "'");
                                
                                }
                                
                            }
                            
                        }
                        else {
                            
                            prelim_assessments.add(assessment);
    
                            for(int x=0; x<scores.length; ++x) {
                                Integer pos = posMap.get(scores[x]);
                                
                                try {
                                    pMap[x][pos]++;
                                    portfolio_pMap[x][pos]++;
                                }
                                catch (Exception e) {
                                    //logService.debug(e);
                                    logService.debug("ERR:  ==> pos '" + pos + "' - score '" + scores[x] + "'");
                                }
                                
                            }
    
                        }
                        
                    }
                    
                    portfolioScorespMap.put(p, portfolio_pMap);
                    portfolioScoresfMap.put(p, portfolio_fMap);
                     */

             <c:forEach var="assessmentModelMap" items="${assessmentModelAssessedObjectivesScoresMap}" varStatus="status">
                <c:set var="color" value="#fff" />
                <c:set var="assessmentModel" value="${assessmentModelMap.key}" />
                <c:set var="assessedModelMap" value="${assessmentModelMap.value}" />
                <c:if test="${status.index % 2 != 0}">
                    <c:set var="color" value="#eee" />
                </c:if>
                
                <tr>
                    <td style="background-color: ${color}">
                        ${assessmentModel.name}
                    </td>
                </tr>
            </c:forEach>
                     
                     
        <div>
            <table style="width: 100%; border: 1px solid #ccc; margin-bottom: 20px;">
                <tr>
                    <td style="width: 40%; background-color: #eee;">
                        Portfolios Matching Template &amp; Rubric:
                    </td>
                    <td style="background-color: #eee;">
                        ${portfolios_count}
                    </td>
                </tr>
                
                <tr>
                    <td style="background-color: #fff;">
                        Portfolios with Assessments (excludes element-level):
                    </td>
                    <td style="background-color: #fff;">
                        ${portfolios_with_count}&nbsp:(Prelim: ${portfolios_with_prelim_count} / Final: ${portfolios_with_final_count})
                    </td>
                </tr>

                <tr>
                    <td style="background-color: #eee;">
                        Portfolios without Assessments (excludes element-level):
                    </td>
                    <td style="background-color: #eee;">
                        ${portfolios_without_count}
                    </td>
                </tr>
                
            </table>

            <table style="width: 100%;">
                <tr>
                    <th colspan="${(assessmentModelCount * 2) + 1}" style="font-weight: bold; font-size: 14pt; background-color: #eee;">Portfolio-level Assessments</th>
                </tr>
            <tr>
                <th>&nbsp;</th>
                <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
                    <th class="scoreNameCell" style="width: ${(100 / (assessmentModelCount)) / 2}%; text-align: center; font-weight: bold; background-color: #eee; border-top: 1px solid #ccc;" colspan="2"><c:out value="${col}" /></th>
                </c:forEach>
            </tr>

            <tr>
                <th>&nbsp;</th>
                <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
                    <th class="scoreNameCell" style="text-align: center; background-color: #eee; border-bottom: 1px solid #ccc;">Pre</th>
                    <th class="scoreNameCell" style="text-align: center; background-color: #eee; border-bottom: 1px solid #ccc; border-right: 1px solid #eee;">Fin</th>
                </c:forEach>
            </tr>
            
            <c:forEach var="objective" items="${objectives}" varStatus="status">
            <tr>
                <td>
                ${objective.name}
                </td>
                <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="inner">
                    <c:set var="color" value="#fff" />
                    <c:if test="${inner.index % 2 != 0}">
                        <c:set var="color" value="#eee" />
                    </c:if>
                        
                    <td style="text-align: center;">${portfolio_pMap[status.index][inner.index]} </td>
                    <td style="text-align: center; border-right: 1px solid #eee;">${portfolio_fMap[status.index][inner.index]} </td>
                    
                </c:forEach>
                
            </tr>
            </c:forEach>

            </table>
        </div>
        <div>&nbsp;</div>
        <div>
            <table>
                <c:forEach var="portfolio" items="${portfolios}" varStatus="status">
                <tr>
                    <td>${portfolio.person.fullName}</td>
                    <td colspan="${assessmentModelCount * 2}">&nbsp;</td>              
                </tr>
                <tr style="margin-bottom: 5px;">
                    <td>${portfolio.shareName}</td>
                    <c:forEach var="col" items="${assessmentModel.scoringModel.scores}" varStatus="loop">
                        <td class="scoreNameCell" style="width: ${(100 / (assessmentModelCount)) / 2}%; text-align: center; font-weight: bold; background-color: #eee; border-top: 1px solid #ccc;" colspan="2"><c:out value="${col}" /></td>
                    </c:forEach>
                    
                </tr>
                </c:forEach>
               
            </table>
        </div>        
                     
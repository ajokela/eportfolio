/* $Name:  $ */
/* $Id: AssessmentModelHomeImpl.java,v 1.28 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.PerformanceDescriptor;
import org.portfolio.model.assessment.ScoringModel;
import org.portfolio.model.community.Community;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("assessmentModelHome")
public class AssessmentModelHomeImpl implements AssessmentModelHome {

    @Autowired
    private ScoringModelHome scoringModelHome;
    @Autowired
    private ObjectiveHome objectiveHome;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<AssessmentModel> rowMapper = new RowMapper<AssessmentModel>() {
        public AssessmentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AssessmentModel assessmentModel = new AssessmentModel();
            final int assessmentModelId = rs.getInt("assessment_model_id");
            assessmentModel.setId(assessmentModelId);
            assessmentModel.setParentAssessmentModelId(rs.getInt("parent_assessment_model_id"));
            assessmentModel.setCommunityId(rs.getInt("community_id"));
            assessmentModel.setName(rs.getString("name"));
            assessmentModel.setDescription(rs.getString("description"));
            assessmentModel.setFormat(rs.getString("format"));
            assessmentModel.setReleaseStatus(rs.getString("release_status"));
            assessmentModel.setDateCreated(new Date(rs.getTimestamp("date_created").getTime()));
            ScoringModel scoringModel = scoringModelHome.find(rs.getInt("scoring_model_id"));
            assessmentModel.setScoringModel(scoringModel);

            if (scoringModel != null && scoringModel.isVisibleInAM(assessmentModel.getFormat())) {
                List<AssessedObjective> assessedObjectives = findAssessedObjectives(assessmentModelId);

                assessmentModel.setAssessedObjectives(assessedObjectives);

                HashMap<Integer, AssessedObjective> map = new HashMap<Integer, AssessedObjective>();
                for (AssessedObjective obj : assessedObjectives) {
                    map.put(obj.getId(), obj);
                }

                List<PerformanceDescriptor> perfDescriptors = findPerformanceDescriptors(assessmentModelId);

                // For each performance descriptor; put it in the appropriate
                // objective's peformance descriptor's list
                for (PerformanceDescriptor perf : perfDescriptors) {
                    AssessedObjective obj = map.get(perf.getObjectiveId());
                    if (obj != null) {
                        List<PerformanceDescriptor> perfList = obj.getPerformanceDescriptors();
                        if (perfList == null) {
                            perfList = new ArrayList<PerformanceDescriptor>();
                        }
                        perfList.add(perf);
                    }
                }
            }

            return assessmentModel;
        }

        private List<AssessedObjective> findAssessedObjectives(final int assessmentModelId) {
            String objSql = "select objs.*,asmtObjs.DISPLAY_SEQUENCE from at_objective objs, at_assessment_objectives asmtObjs " +
            		"where objs.objective_id = asmtObjs.assessment_objective_id and asmtObjs.assessment_model_id=? order by asmtobjs.display_sequence";
            List<AssessedObjective> assessedObjectives = simpleJdbcTemplate.query(
                    objSql,
                    new RowMapper<AssessedObjective>() {
                        public AssessedObjective mapRow(ResultSet rs, int rowNum) throws SQLException {
                            AssessedObjective objective = new AssessedObjective();
                            int objectiveId = rs.getInt("objective_id");
                            objective.setId(objectiveId);
                            objective.setAssessmentModelId(assessmentModelId);
                            int parentId = rs.getInt("parent_objective_id");
                            objective.setParentId(parentId == 0 ? null : parentId);
                            objective.setCommunityId(rs.getInt("community_id"));
                            objective.setName(rs.getString("name"));
                            objective.setDescription(rs.getString("description"));
                            objective.setOrder(rs.getInt("sort_order"));
                            objective.setDateCreated(rs.getDate("date_created"));
                            objective.setDateModified(rs.getDate("date_modified"));
                            objective.setDisplaySequence(rs.getInt("display_sequence"));
                            return objective;
                        }
                    },
                    assessmentModelId);
            return assessedObjectives;
        }

        private List<PerformanceDescriptor> findPerformanceDescriptors(final int assessmentModelId) {
            String perfSql = "select * from at_performance_descriptor where assessment_model_id=?";
            RowMapper<PerformanceDescriptor> performanceDescriptorRowMapper = new RowMapper<PerformanceDescriptor>() {
                public PerformanceDescriptor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PerformanceDescriptor pd = new PerformanceDescriptor(rs.getInt("ASSESSMENT_MODEL_ID"), rs.getInt("OBJECTIVE_ID"), rs
                            .getString("DESCRIPTION"), rs.getString("EXAMPLE"), rs.getString("SCORE_VALUE"));
                    return pd;
                }
            };
            return simpleJdbcTemplate.query(perfSql, performanceDescriptorRowMapper, assessmentModelId);
        }
    };

    /**
     * Delete the assessment Objectives Not in Assessment model When the user
     * de-selects the assessment Objectives and saves the assessment model
     * 
     * @param assessmentModelId
     * @param objectiveIds
     */
    public void deleteObjectivesNotInAM(Integer assessmentModelId, String[] objectiveIds) {
        if (objectiveIds.length < 1) {
            return;
        }
        StringBuffer sb = new StringBuffer(objectiveIds[0]);
        for (int i = 1; i < objectiveIds.length; i++) {
            sb.append(",").append(objectiveIds[i]);
        }
        String perfSql = "delete from at_performance_descriptor where assessment_model_id=? and assessment_objective_id not in (" + sb
                + ")";
        String objSql = "delete from at_assessment_objectives where assessment_model_id=? and assessment_objective_id not in (" + sb + ")";
        simpleJdbcTemplate.update(perfSql, assessmentModelId);
        simpleJdbcTemplate.update(objSql, assessmentModelId);
    }

    /**
     * Deletes Assessment Objectives and Performance Descriptors of Assessment
     * Model
     * 
     * @param assessmentModelId
     */
    public void deleteObjectivesInAM(Integer assessmentModelId) {
        String perfSql = "delete from at_performance_descriptor where assessment_model_id=?";
        String objSql  = "delete from at_assessment_objectives where assessment_model_id=?";
        simpleJdbcTemplate.update(perfSql, assessmentModelId);
        simpleJdbcTemplate.update(objSql, assessmentModelId);
    }
    
    public AssessmentModel copyAssessmentModel(AssessmentModel model, Community community, boolean copyAMObjectives) {
    	
    	if(model != null) {
    		
    		AssessmentModel newModel = new AssessmentModel();
    		newModel.setCommunityId(community.getId());
    		newModel.setDescription(model.getDescription());
    		newModel.setFormat(model.getFormat());
    		newModel.setParentAssessmentModelId(model.getParentAssesmentModelId());
    		newModel.setReleaseStatus(model.getReleaseStatus());
    		newModel.setScoringModel(model.getScoringModel());
    		newModel.setName(model.getName());
    		
    		if(copyAMObjectives) {
    			if(model.getAssessedObjectives() != null) {
    				List<AssessedObjective> aos = new ArrayList<AssessedObjective>();
    				Map<Integer, Integer>iDMapping = new LinkedHashMap<Integer, Integer>();
    				
    				for(Iterator<AssessedObjective> i = model.getAssessedObjectives().iterator(); i.hasNext();) {
    					AssessedObjective ao = i.next();
    					AssessedObjective newAO = new AssessedObjective();
    					
    					newAO.setAssessmentModelId(ao.getAssessmentModelId());
    					newAO.setCommunityId(community.getId());
    					newAO.setDescription(ao.getDescription());
    					newAO.setDisplaySequence(ao.getDisplaySequence());
    					newAO.setLevel(ao.getLevel());
    					newAO.setName(ao.getName());
    					newAO.setOrder(ao.getOrder());
    					newAO.setSelected(ao.isSelected());
    					
   
    					
    					List<PerformanceDescriptor> pds = new ArrayList<PerformanceDescriptor>();
    					
    					for(Iterator<PerformanceDescriptor> j = ao.getPerformanceDescriptors().iterator(); j.hasNext(); ) {
    						
    						PerformanceDescriptor pd = j.next();
    						PerformanceDescriptor newPD = new PerformanceDescriptor();
    						
    						newPD.setDescription(pd.getDescription());
    						newPD.setName(pd.getName());
    						newPD.setScoreValue(pd.getScoreValue());
    						
    						pds.add(newPD);
    						
    					}
    					
    					newAO.setPerformanceDescriptors(pds);

    					newAO.setParentId(ao.getParentId());
    					
    					objectiveHome.insert(newAO);
    					
    					iDMapping.put(ao.getId(), newAO.getId());
    					
    					aos.add(newAO);
    					
    				}
    				
    				for(Iterator<AssessedObjective> j = aos.iterator(); j.hasNext();) {
    					AssessedObjective ao = j.next();
    					
    					Integer newId = iDMapping.get(ao.getParentId());

    					if (newId != null) {
    						ao.setParentId(newId);
    					}
    					
    					objectiveHome.update(ao);
    				}
    				
    			}
    		}
    		
    		int id = insertAssessmentModel(newModel);
    		
    		newModel.setId(id);
    		
    		return newModel;
    		
    	}
    	
    	return null;
    }

    /**
     * Inserts into Assessment Model
     */
    @Transactional
    public int insertAssessmentModel(AssessmentModel model) {
        // insert into at_assessment_model
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
        
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	
        String amSql = "insert into at_assessment_model(ASSESSMENT_MODEL_ID,COMMUNITY_ID,NAME,DESCRIPTION,FORMAT,DATE_CREATED,DATE_MODIFIED,RELEASE_STATUS,PARENT_ASSESSMENT_MODEL_ID,SCORING_MODEL_ID) values(?,?,?,?,?," + sp + ", " + sp + ",?,?,?)";
        int assessmentModelId = sequenceGenerator.getNextSequenceNumber("ASSESSMENT_MODEL_ID");
        model.setId(assessmentModelId);
        simpleJdbcTemplate.update(
                amSql,
                new Integer(assessmentModelId),
                model.getCommunityId(),
                model.getName(),
                model.getDescription(),
                model.getFormat(),
                model.getReleaseStatus(),
                model.getParentAssesmentModelId() == null ? new Integer(assessmentModelId) : model.getParentAssesmentModelId(),
                model.getScoringModel().getId());
        
        
        
        if (!model.getFormat().equals("basic")) {
            insertAMObjectives(model);
        }
        
        return assessmentModelId;
    }

    /**
     * Updates Assessment Model
     * 
     * @param model
     */
    @Transactional
    public void updateAssessmentModel(AssessmentModel model) {
        Integer assessmentModelId = model.getId();
        // insert into at_assessment_model
        String amSql = "update at_assessment_model set COMMUNITY_ID=?,NAME=?,DESCRIPTION=?,FORMAT=?, RELEASE_STATUS=?,PARENT_ASSESSMENT_MODEL_ID=?,SCORING_MODEL_ID=? where ASSESSMENT_MODEL_ID=?";
        simpleJdbcTemplate.update(
                amSql,
                model.getCommunityId(),
                model.getName(),
                model.getDescription(),
                model.getFormat(),
                model.getReleaseStatus(),
                model.getParentAssesmentModelId() == null ? assessmentModelId : model.getParentAssesmentModelId(),
                model.getScoringModel().getId(),
                assessmentModelId); // TO DO : Update Date Modified
        // Delete the assessment Objectives

        deleteObjectivesInAM(assessmentModelId);

        // Insert Assessment Objectives and Performance Descriptors
        if (!model.getFormat().equals("basic")) {
            insertAMObjectives(model);
        }
    }

    /**
     * Deletes an Assessment Model by setting the release status to "retired".
     * These assessment models must be maintained for historical purposes but
     * are not availabel for assignment to Portoflio Templates.
     * 
     * @param model
     */
    @Transactional
    public void deleteAssessmentModel(AssessmentModel assessmentModel) {
        Integer assessmentModelId = assessmentModel.getId();
        // insert into at_assessment_model
        String amSql = "update at_assessment_model set RELEASE_STATUS=? where ASSESSMENT_MODEL_ID=?";
        simpleJdbcTemplate.update(amSql, "RETIRED", assessmentModelId); // TODO
        // :
        // use
        // constants

    }

    /**
     * Inserts Assessment Model Objectives and performance Descriptors
     * 
     * @param model
     */
    @Transactional
    public void insertAMObjectives(AssessmentModel model) {

        // insert into at_assessment_objectives
        Integer assessmentModelId = model.getId();
        List<AssessedObjective> objectives = model.getAssessedObjectives();
        
        if ( objectives != null ) {
	        
	        String amObjectivesSql = "insert into at_assessment_objectives(ASSESSMENT_MODEL_ID,ASSESSMENT_OBJECTIVE_ID,DISPLAY_SEQUENCE) values(?,?,?)";
	        String perfSql = "insert into at_performance_descriptor(ASSESSMENT_MODEL_ID,OBJECTIVE_ID,DESCRIPTION,SCORE_VALUE,SCORE_SEQUENCE,EXAMPLE) values(?,?,?,?,?,?)";
	
	        for (AssessedObjective obj : objectives) {
	            simpleJdbcTemplate.update(amObjectivesSql, assessmentModelId, obj.getId(), obj.getDisplaySequence());
	            if (model.getFormat().equals("rubric")) {
	                List<PerformanceDescriptor> perfDescriptors = obj.getPerformanceDescriptors();
	                if (!perfDescriptors.isEmpty()) {
	                    int index = 0;
	                    for (PerformanceDescriptor perf : perfDescriptors) {
	                        simpleJdbcTemplate.update(
	                                perfSql,
	                                assessmentModelId,
	                                obj.getId(),
	                                perf.getName(),
	                                perf.getScoreValue(),
	                                index++,
	                                perf.getDescription());
	                    }
	                }
	            }
	        }
        }
    }

    public List<AssessmentModel> findAssessmentModelsByCommuntyAndType(String communityIdParam, String assessmentModelFormat) {
        String sql = "select * from at_assessment_model where community_id=? and format=? and release_status <> 'RETIRED'";
        return simpleJdbcTemplate.query(sql, rowMapper, communityIdParam, assessmentModelFormat);
    }

    public AssessmentModel findAssessmentModelById(Integer assessmentModelId) {
        String sql = "select * from at_assessment_model where assessment_model_id=?";
        return simpleJdbcTemplate.queryForObject(sql, rowMapper, assessmentModelId);
    }

    /**
     * @see org.portfolio.dao.assessment.AssessmentModelHome#findAssessmentModelsByObjective(int)
     */
    public List<AssessmentModel> findAssessmentModelsByObjective(int objectiveId, String dateFrom, String dateTo) {
        String sql = "select am.* from at_assessment_model am, at_assessment_objectives ao where am.assessment_model_id=ao.assessment_model_id and ao.assessment_objective_id=?";
        
        /*
        int args = 0;
        
        if (dateFrom != null) {
        	sql += " AND date_modified >= to_date(?, 'MM/DD/YYYY')";
        	args = 1;
        }
        
        if (dateTo != null) {
        	sql += " AND date_modified <= to_date(?, 'MM/DD/YYYY')";
        	
        	if (args == 1) {
        		args = 5;
        	}
        	else {
        		args = 3;
        	}
        }
        
        // logService.error("[SQL] => " + sql);

        if (args == 5) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateFrom: " + dateFrom + " dateTo: " + dateTo);
        	return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateFrom, dateTo);
        }
        else if (args == 3) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateTo: " + dateTo);
        	return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateTo);
        }
        else if (args == 1) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateFrom: " + dateFrom);
        	return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateFrom);
        }
        */
        
        //logService.error("[PARAMS] => objectiveId: " + objectiveId);
        return simpleJdbcTemplate.query(sql, rowMapper, objectiveId);
    }
    
}

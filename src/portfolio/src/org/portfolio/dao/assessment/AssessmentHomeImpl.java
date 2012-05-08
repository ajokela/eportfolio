/* $Name:  $ */
/* $Id: AssessmentHomeImpl.java,v 1.22 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.PersonHome;
import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("assessmentHome")
public class AssessmentHomeImpl implements AssessmentHome {

    @Autowired
    private PersonHome personHome;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    final RowMapper<Assessment> rowMapper = new RowMapper<Assessment>() {
        public Assessment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Assessment assessment = new Assessment();
            int identifier = rs.getInt("assessment_id");
            assessment.setId(identifier);
            assessment.setAssessedItemId(rs.getInt("assessed_item_id"));
            assessment.setAssessedItemType(PortfolioItemType.valueOf(rs.getString("assessed_item_type")));
            assessment.setScoreAssignment(rs.getString("score_assignment"));
            assessment.setQuantifiedScore(rs.getString("quantified_score"));
            assessment.setAssessedDate(rs.getTimestamp("assessed_date"));
            assessment.setAssessmentType(rs.getString("assessment_type"));
            assessment.setEvaluator(personHome.findByPersonId(rs.getString("evaluator_id")));
            assessment.setShareId(rs.getString("share_id"));
            assessment.setOverallScore(rs.getString("overall_score"));
            assessment.setOverallQuantifiedScore(rs.getInt("overall_quantified_score"));
            return assessment;
        }
    };

    public void insert(Assessment assessment) {
        int id = sequenceGenerator.getNextSequenceNumber("ASSESSMENT_ID_SEQ");
        assessment.setId(new Integer(id));

        String sql = "insert into at_assessment (assessment_id, assessed_item_id, assessed_item_type, score_assignment, "
                + "quantified_score, evaluator_id, assessed_date, assessment_type, share_id, overall_score, overall_quantified_score)"
                + " values (?,?,?,?,?,?,?,?,?,?,?)";
        simpleJdbcTemplate.update(
                sql,
                assessment.getId(),
                assessment.getAssessedItemId(),
                assessment.getAssessedItemType().toString(),
                assessment.getScoreAssignment(),
                assessment.getQuantifiedScore(),
                assessment.getEvaluator().getPersonId(),
                new Timestamp(System.currentTimeMillis()),
                assessment.getAssessmentType(),
                assessment.getShareId(),
                assessment.getOverallScore(),
                assessment.getOverallQuantifiedScore());
    }

    public List<Assessment> findEvaluatorAssessments(String personId, int assessedItemId, PortfolioItemType portfolioItemType) {
        String sql = "select * from at_assessment where evaluator_id = ? and assessed_item_id=? and assessed_item_type=? order by assessed_date";
        return simpleJdbcTemplate.query(sql, rowMapper, personId, assessedItemId, portfolioItemType.toString());
    }
    
    public List<Assessment> findAssessmentsByShareIdAndType(String shareId, PortfolioItemType portfolioItemType, String dateFrom, String dateTo) {
    	String sql = "SELECT * FROM at_assessment WHERE share_id = ? AND assessed_item_type= ? ";
    	
        int args = 0;
        
        if (dateFrom != null) {
        	sql += " AND assessed_date >= to_date(?, 'MM/DD/YYYY')";
        	args = 1;
        }
        
        if (dateTo != null) {
        	sql += " AND assessed_date <= to_date(?, 'MM/DD/YYYY')";
        	
        	if (args == 1) {
        		args = 5;
        	}
        	else {
        		args = 3;
        	}
        }
        
        sql += " order by assessed_date";
        
        // logService.error("[SQL] => " + sql);

        if (args == 5) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateFrom: " + dateFrom + " dateTo: " + dateTo);
        	//return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateFrom, dateTo);
        	return simpleJdbcTemplate.query(sql, rowMapper, shareId, portfolioItemType.toString(), dateFrom, dateTo);
        }
        else if (args == 3) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateTo: " + dateTo);
        	//return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateTo);
        	return simpleJdbcTemplate.query(sql, rowMapper, shareId, portfolioItemType.toString(), dateTo);
        }
        else if (args == 1) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateFrom: " + dateFrom);
        	// return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateFrom);
        	return simpleJdbcTemplate.query(sql, rowMapper, shareId, portfolioItemType.toString(), dateFrom);
        }
        
        
        return simpleJdbcTemplate.query(sql, rowMapper, shareId, portfolioItemType.toString());
    }
    
    public List<Assessment> findAssessmentsByItemIdAndType(int assessedItemId, PortfolioItemType portfolioItemType, String dateFrom, String dateTo) {
    	
        String sql = "select * from at_assessment where assessed_item_id=? and assessed_item_type=?";
        
        int args = 0;
        
        if (dateFrom != null) {
        	sql += " AND assessed_date >= to_date(?, 'MM/DD/YYYY')";
        	args = 1;
        }
        
        if (dateTo != null) {
        	sql += " AND assessed_date <= to_date(?, 'MM/DD/YYYY')";
        	
        	if (args == 1) {
        		args = 5;
        	}
        	else {
        		args = 3;
        	}
        }
        
        sql += " order by assessed_date";
        
        // logService.error("[SQL] => " + sql);

        if (args == 5) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateFrom: " + dateFrom + " dateTo: " + dateTo);
        	//return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateFrom, dateTo);
        	return simpleJdbcTemplate.query(sql, rowMapper, assessedItemId, portfolioItemType.toString(), dateFrom, dateTo);
        }
        else if (args == 3) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateTo: " + dateTo);
        	//return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateTo);
        	return simpleJdbcTemplate.query(sql, rowMapper, assessedItemId, portfolioItemType.toString(), dateTo);
        }
        else if (args == 1) {
        	//logService.error("[PARAMS] => objectiveId: " + objectiveId + " dateFrom: " + dateFrom);
        	// return simpleJdbcTemplate.query(sql, rowMapper, objectiveId, dateFrom);
        	return simpleJdbcTemplate.query(sql, rowMapper, assessedItemId, portfolioItemType.toString(), dateFrom);
        }
        
        
        return simpleJdbcTemplate.query(sql, rowMapper, assessedItemId, portfolioItemType.toString());
    }

    public List<Assessment> findEvaluatorAssessments(String shareId, String personId) {
        String sql = "select * from at_assessment where evaluator_id = ? and share_id=? order by assessed_date";
        return simpleJdbcTemplate.query(sql, rowMapper, personId, shareId);
    }

    public Assessment findById(int assessmentId) {
        String sql = "select * from at_assessment where assessment_id=?";
        return simpleJdbcTemplate.queryForObject(sql, rowMapper, assessmentId);
    }
}

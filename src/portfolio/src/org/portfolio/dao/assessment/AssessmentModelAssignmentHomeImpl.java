/* $Name:  $ */
/* $Id: AssessmentModelAssignmentHomeImpl.java,v 1.15 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.community.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Tom Smith
 * 
 */
@Repository("assessmentModelAssignmentHome")
public class AssessmentModelAssignmentHomeImpl implements AssessmentModelAssignmentHome {

    @Autowired
  	private AssessmentModelHome assessmentModelHome;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }


	private final RowMapper<AssessmentModelAssignment> rowMapper = new RowMapper<AssessmentModelAssignment>() {
		public AssessmentModelAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
			AssessmentModelAssignment assessmentModelAssignment = new AssessmentModelAssignment();
			assessmentModelAssignment.setIdentifier(rs.getInt("id"));
            assessmentModelAssignment.setAssessmentModel(assessmentModelHome.findAssessmentModelById(rs.getInt("assessment_model_id")));
			assessmentModelAssignment.setAssessedItemId(rs.getInt("assessed_item_type_id"));
			assessmentModelAssignment.setPortfolioItemType(PortfolioItemType.valueOf(rs.getString("assessed_item_type")));
            assessmentModelAssignment.setCommitteeBased(rs.getBoolean("committee_based"));
			assessmentModelAssignment.setAnonymous(rs.getBoolean("is_anonymous"));
			return assessmentModelAssignment;
		}
	};

	public AssessmentModelAssignment copyAssessmentModelAssignment(AssessmentModelAssignment ama, Community community, boolean copyAMObjectives) {
		if(ama != null) {
			AssessmentModelAssignment newAma = new AssessmentModelAssignment();
			newAma.setAnonymous(ama.isAnonymous());
			newAma.setAssessmentModel(assessmentModelHome.copyAssessmentModel(ama.getAssessmentModel(), community, copyAMObjectives));
	
		}
		
		return null;
	}
	
	public int insert(AssessmentModelAssignment assessmentModelAssignment){
		int id = sequenceGenerator.getNextSequenceNumber("assessment_model_assign_id_seq");
		String sql = "insert into at_assessment_model_assnmnt (id, assessment_model_id, assessed_item_type_id, assessed_item_type, "
				+ "committee_based, is_anonymous) values (?,?,?,?,?,?)";
		simpleJdbcTemplate.update(
				sql,
				id,
				assessmentModelAssignment.getAssessmentModel().getId(),
				assessmentModelAssignment.getAssessedItemId(),
				assessmentModelAssignment.getPortfolioItemType().toString(),
				assessmentModelAssignment.isCommitteeBased(),
				assessmentModelAssignment.isAnonymous()? 1 : 0);
		assessmentModelAssignment.setIdentifier(id);
		
		return id;
    }

	public void delete(int assessmentModelAssignmentId){
		String sql = "delete from at_assessment_model_assnmnt where id=?";
		simpleJdbcTemplate.update(sql, assessmentModelAssignmentId);      
    }

	public void update(AssessmentModelAssignment assessmentModelAssignment){
		String sql = "update at_assessment_model_assnmnt set assessment_model_id=?, assessed_item_type_id=?, assessed_item_type=?, "
				+ "committee_based=?, is_anonymous=? where id=?";
		simpleJdbcTemplate.update(
				sql,
				assessmentModelAssignment.getAssessmentModel().getId(),
				assessmentModelAssignment.getAssessedItemId(),
				assessmentModelAssignment.getPortfolioItemType().toString(),
				assessmentModelAssignment.isCommitteeBased(),
				assessmentModelAssignment.isAnonymous() ? 1 : 0,
				assessmentModelAssignment.getIdentifier()
                );      
    }
	
	public List<AssessmentModelAssignment> findByAssessmentModel(AssessmentModel am, PortfolioItemType portfolioItemType) {
		String sql = "SELECT * FROM at_assessment_model_assnmnt WHERE ASSESSMENT_MODEL_ID = ? AND assessed_item_type=?";
		
		List<AssessmentModelAssignment> result = simpleJdbcTemplate.query(sql, rowMapper, am.getId(), portfolioItemType.toString());
		return result;
	}

	public AssessmentModelAssignment findByAssessedItemIdAndPortfolioItemType(int assessedItemId, PortfolioItemType assessedItemType) {
		String sql = "select * from at_assessment_model_assnmnt where assessed_item_type_id=? and assessed_item_type=?";
		List<AssessmentModelAssignment> result = simpleJdbcTemplate.query(sql, rowMapper, assessedItemId, assessedItemType.toString());
		return result.isEmpty() ? null : result.get(0);
    }
	
	public void deleteByItemIdAndItemType(String shareId, PortfolioItemType portfolioItemType) {
		String sql = "delete from at_assessment_model_assnmnt where assessed_item_type_id=? and assessed_item_type=?";
		simpleJdbcTemplate.update(sql, shareId, portfolioItemType.toString());   
	}
}

/* $Name:  $ */
/* $Id: TemplateHomeImpl.java,v 1.27 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.community.CommunityHome;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.Template;
import org.portfolio.model.community.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.*;

@Repository("templateHome")
public class TemplateHomeImpl implements TemplateHome {

    @Autowired
    private AssessmentModelAssignmentHome assessmentModelAssignmentHome;
    @Autowired
    private CommunityHome communityHome;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
    private final ParameterizedRowMapper<Template> rowMapper = new ParameterizedRowMapper<Template>() {
        public Template mapRow(ResultSet rs, int rowNum) throws SQLException {
            Template template = new Template();
            template.setId(rs.getString("template_id"));
            template.setName(rs.getString("name"));
            template.setDateCreated(rs.getDate("date_created"));
            template.setDateModified(rs.getDate("date_modified"));
            template.setPublished(rs.getBoolean("visible"));
            template.setDescription(rs.getString("description"));
            template.setCommunityId(rs.getInt("community_id"));
            template.setAssessable(rs.getBoolean("is_assessable"));
            template.setAssessmentModelAssignment(assessmentModelAssignmentHome.findByAssessedItemIdAndPortfolioItemType(Integer
                    .parseInt(template.getId()), PortfolioItemType.TEMPLATE));
            template.setDeleted("y".equals(rs.getString("is_deleted")));
            return template;
        }
    };

    @SuppressWarnings("deprecation")
	public List<Template> findPublishedTemplates() {
        String sqlQuery = "select * from template where visible=1 and is_deleted='n' order by name";
        return simpleJdbcTemplate.query(sqlQuery, rowMapper);
    }

    @SuppressWarnings("deprecation")
	public List<Template> findAllTemplates() {
        String sqlQuery = "select * from template where is_deleted='n' order by name";
        return simpleJdbcTemplate.query(sqlQuery, rowMapper);
    }

    @SuppressWarnings("deprecation")
	public Template findTemplateById(String id) {
        String sqlQuery = "select * from template where template_id=?";
        return simpleJdbcTemplate.queryForObject(sqlQuery, rowMapper, id);
    }

    @SuppressWarnings("deprecation")
	public Template findTemplateById(String id, String dateFrom, String dateTo) throws EmptyResultDataAccessException {
    	
    	try {
	    	String sqlQuery = "select * from template where template_id=?";
	        int args = 0;
	        
	        if (dateFrom != null) {
	        	sqlQuery += " AND date_modified >= to_date(?, 'MM/DD/YYYY')";
	        	args = 1;
	        }
	        
	        if (dateTo != null) {
	        	sqlQuery += " AND date_modified <= to_date(?, 'MM/DD/YYYY')";
	        	
	        	if (args == 1) {
	        		args = 5;
	        	}
	        	else {
	        		args = 3;
	        	}
	        }
	
	        if (args == 5) {
	        	return simpleJdbcTemplate.queryForObject(sqlQuery, rowMapper, id, dateFrom, dateTo);
	        }
	        else if (args == 3) {
	        	return simpleJdbcTemplate.queryForObject(sqlQuery, rowMapper, id, dateTo);
	        }
	        else if (args == 1) {
	        	return simpleJdbcTemplate.queryForObject(sqlQuery, rowMapper, id, dateFrom);
	        }
	        
	        return simpleJdbcTemplate.queryForObject(sqlQuery, rowMapper, id);
    	}
    	catch (EmptyResultDataAccessException e) {
    		
    	}
    	
        // return simpleJdbcTemplate.queryForObject(sqlQuery, rowMapper, id);
    	
    	return null;
    }
    
    public Template copyTemplate(Template template, Community community, boolean copyAMObjectives) {
    	if(template != null) {
    		
    		Template newTemplate = new Template();
    		
    		newTemplate.setAssessable(template.isAssessable());
    		newTemplate.setAssessmentModelAssignment(assessmentModelAssignmentHome.copyAssessmentModelAssignment(template.getAssessmentModelAssignment(), communityHome.findByCommunityId(template.getCommunityId()), copyAMObjectives));
    		
    		newTemplate.setCommunityId(community.getId());
    		newTemplate.setDeleted(template.isDeleted());
    		newTemplate.setPublished(template.isPublished());
    		newTemplate.setDescription(template.getDescription());
    		newTemplate.setName(template.getName());
    	
    		int id = insert(newTemplate);
    		
    		newTemplate.setId(Integer.toString(id));
    		
    		return newTemplate;
    		
    	}
    	
    	return null;
    }


    public int insert(Template template) {
        String sql = "insert into template (template_id, name, description, date_created, date_modified, visible, community_id, is_assessable, is_deleted) "
                + "values (?,?,?,?,?,?,?,?,?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int id = sequenceGenerator.getNextSequenceNumber("TEMPLATE_ID_SEQ");
        simpleJdbcTemplate.update(
                sql,
                id,
                template.getName(),
                template.getDescription(),
                now,
                now,
                template.isPublished() ? 1 : 0,
                template.getCommunityId(),
                template.isAssessable() ? 1 : 0,
                template.isDeleted() ? "y" : "n");
        // TODO whoops! mixing y/n strategies there
        template.setId("" + id);
        
        return id;
    }

    public void update(Template template) {
        String sql = "update template set name=?, description=?, date_modified=?, visible=?, community_id=?, is_assessable=?, is_deleted=? where template_id=?";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        simpleJdbcTemplate.update(
                sql,
                template.getName(),
                template.getDescription(),
                now,
                template.isPublished() ? 1 : 0,
                template.getCommunityId(),
                template.isAssessable() ? 1 : 0,
                template.isDeleted() ? "y" : "n",
                template.getId());
        // TODO whoops! mixing y/n strategies there
    }

    public void delete(String templateId) {
        String sql = "delete from template where template_id=?";
        simpleJdbcTemplate.update(sql, templateId);
    }

    @SuppressWarnings("deprecation")
	public List<Template> findByCommunityId(int communityId) {
        String sql = "select * from template where community_id=? and is_deleted='n'";
        return simpleJdbcTemplate.query(sql, rowMapper, communityId);
    }

    @SuppressWarnings("deprecation")
	public List<Template> findAssessableByCommunityId(String communityId) {
        String sql = "select * from template where community_id=? and is_assessable=1 and is_deleted='n'";
        return simpleJdbcTemplate.query(sql, rowMapper, communityId);
    }

    @SuppressWarnings("deprecation")
	public List<Template> findPublishedByCommunityId(int communityId) {
        String sqlQuery = "select * from template where community_id=? and visible=1 and is_deleted='n' order by name";
        return simpleJdbcTemplate.query(sqlQuery, rowMapper, communityId);
    }

    @SuppressWarnings("deprecation")
	public List<Template> findUnpublishedByCommunityId(int communityId) {
        String sqlQuery = "select * from template where community_id=? and visible=0 and is_deleted='n' order by name";
        return simpleJdbcTemplate.query(sqlQuery, rowMapper, communityId);
    }
}

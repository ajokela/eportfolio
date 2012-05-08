/* $Name:  $ */
/* $Id: TemplateElementMappingHomeImpl.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.wizard.WizardElementDefinitionHome;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * This class manages the life cycle for <code>TemplateElementForm</code>
 * objects.
 * 
 */
@Repository
public class TemplateElementMappingHomeImpl implements TemplateElementMappingHome {

    @Autowired private WizardElementDefinitionHome wizardElementDefinitionHome;
    @Autowired private AssessmentModelAssignmentHome assessmentModelAssignmentHome;
    @Autowired private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;
    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<TemplateElementMapping> rowMapper = new RowMapper<TemplateElementMapping>() {
        public TemplateElementMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateElementMapping element = new TemplateElementMapping();
            element.setId(rs.getString("mapping_id"));
            element.setTemplate_id(rs.getString("template_id"));
            element.setCategoryId(rs.getInt("category_id"));
            element.setSortOrder(rs.getInt("sort_order"));
            element.setAssessmentModelAssignment(assessmentModelAssignmentHome.findByAssessedItemIdAndPortfolioItemType(Integer
                    .parseInt(element.getId()), PortfolioItemType.TEMPLATE_ELEMENT));
            WizardElementDefinition wizardElementDefinition = wizardElementDefinitionHome.findById(rs.getInt("wizard_element_id"));
            element.setWizardElementDefinition(wizardElementDefinition);
            return element;
        }
    };

    public List<TemplateElementMapping> findByTemplateCategoryId(int categoryId) {
    	
    	// logService.debug("==> findByTemplateCategoryId(" + categoryId + ")");
    	
        String sql = "select * from template_elements_mapping where category_id = ? order by sort_order";
        return simpleJdbcTemplate.query(sql, rowMapper, categoryId);
    }

    public List<TemplateElementMapping> findByTemplateId(String templateId) {
        String sqlQuery = "select * from template_elements_mapping where template_id=? order by sort_order";
        return simpleJdbcTemplate.query(sqlQuery, rowMapper, templateId);
    }

    public TemplateElementMapping findById(String id) {
        String sql = "select * from template_elements_mapping where mapping_id = ?";
        return simpleJdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void delete(String id) {
    	
    	// try {
	    	
	    	String sql;
	    	
	    	sql = "DELETE FROM share_entry WHERE element_id = ?";
	    	
	    	simpleJdbcTemplate.update(sql, id);
	    	
	    	sql = "delete from template_elements_mapping where mapping_id = ?";
	    	
	        simpleJdbcTemplate.update(sql, id);
	    /* 
    	}
	    catch (Exception e) {
	    	
	    	logService.error(e);
	    
	    }
	    */
    }

    public TemplateElementMapping findByCategoryIdAndSortOrder(Integer categoryId, int i) {
        String sql = "select * from template_elements_mapping where category_id=? and sort_order=?";
        List<TemplateElementMapping> result = simpleJdbcTemplate.query(sql, rowMapper, categoryId, i);
        return result.isEmpty() ? null : result.get(0);
    }

    public void insert(TemplateElementMapping tem) {
        String sql = "insert into template_elements_mapping (mapping_id, template_id, category_id, wizard_element_id, sort_order) values (?,?,?,?,?)";
        int id = sequenceGenerator.getNextSequenceNumber("TE_ELEMENT_MAPPING_ID_SEQ");
        simpleJdbcTemplate.update(
                sql,
                "" + id,
                tem.getTemplate_id(),
                tem.getCategoryId(),
                tem.getWizardElementDefinition().getId(),
                tem.getSortOrder());
        tem.setId("" + id);
    }

    public void update(TemplateElementMapping tem) {
        String sql = "update template_elements_mapping set template_id=?, category_id=?, wizard_element_id=?, sort_order=? where mapping_id=?";
        simpleJdbcTemplate.update(
                sql,
                tem.getTemplate_id(),
                tem.getCategoryId(),
                tem.getWizardElementDefinition().getId(),
                tem.getSortOrder(),
                tem.getId());
    }

    public List<TemplateElementMapping> findAssessableByTemplateId(String templateId) {
        String sql = "select tem.* from template_elements_mapping tem, at_assessment_model_assnmnt ama"
                + " where tem.template_id=? and ama.assessed_item_type=? and ama.assessed_item_type_id=tem.mapping_id";
        return simpleJdbcTemplate.query(sql, rowMapper, templateId, PortfolioItemType.TEMPLATE_ELEMENT.toString());
    }

    public List<TemplateElementMapping> findByCollectionGuideElementId(int collectionGuideElementId) {
        String sql = "select * from template_elements_mapping where wizard_element_id=?";
        return simpleJdbcTemplate.query(sql, rowMapper, collectionGuideElementId);
    }
}

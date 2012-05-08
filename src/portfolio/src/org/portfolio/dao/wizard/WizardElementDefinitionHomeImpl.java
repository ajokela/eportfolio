/* $Name:  $ */
/* $Id: WizardElementDefinitionHomeImpl.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WizardElementDefinitionHomeImpl implements WizardElementDefinitionHome {

    @Autowired private WizardElementKeywordHome wizardElementKeywordHome;
    @Autowired private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    private LogService logService = new LogService(this.getClass());
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<WizardElementDefinition> rowMapper = new RowMapper<WizardElementDefinition>() {
        public WizardElementDefinition mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int identifier = resultSet.getInt("id");
            List<String> keywords = wizardElementKeywordHome.findKeywordsByWizardElementId(identifier);
            
            WizardElementDefinition wed = new WizardElementDefinition();
            wed.setAutoImport("y".equals(resultSet.getString("auto_import")));
            wed.setCategoryId(resultSet.getInt("category_id"));
            wed.setDescription(resultSet.getString("description"));
            wed.setElementDefinitionId(resultSet.getString("element_id"));
            wed.setId(identifier);
            wed.setKeywords(keywords);
            wed.setRequired("y".equals(resultSet.getString("is_required")));
            wed.setTitle(resultSet.getString("title"));
            wed.setWizardId(resultSet.getInt("wizard_id"));
            wed.setSortOrder(resultSet.getInt("sort_order"));
            return wed;
        }
    };
    
    public List<WizardElementDefinition> findByCategoryId(int categoryId) {
        String sql = "SELECT wcem.*, wc.wizard_id FROM WIZARD_ELEMENT_MAPPING wcem, wizard_category wc WHERE wcem.category_id = ? and wcem.category_id=wc.id ORDER BY wcem.sort_order,wcem.id";
        return simpleJdbcTemplate.query(sql, rowMapper, categoryId);
    }

    public WizardElementDefinition findById(int mapId) {
    	logService.debug("=> findById(" + (new Integer(mapId)).toString() + ")");
    	
        String sql = "SELECT wcem.*, wc.wizard_id FROM WIZARD_ELEMENT_MAPPING wcem , wizard_category wc WHERE wcem.id = ? and wcem.category_id=wc.id";
        
        List<WizardElementDefinition> list = simpleJdbcTemplate.query(sql, rowMapper, mapId);
        
        if(list.size() > 0) {
        	return list.get(0);
        }
        
        return null;
    }

    public void delete(int wizardElementDefinitionId) {
        String sql = "delete from WIZARD_ELEMENT_MAPPING where id=?";
        simpleJdbcTemplate.update(sql, wizardElementDefinitionId);
    }

    public void insert(WizardElementDefinition wizardElementDefinition) {
        String sql = "INSERT into WIZARD_ELEMENT_MAPPING (ID, CATEGORY_ID, ELEMENT_ID, TITLE, DESCRIPTION, IS_REQUIRED, "
                + "AUTO_IMPORT, SORT_ORDER) values (?,?,?,?,?,?,?,?)";
        int id = sequenceGenerator.getNextSequenceNumber("WIZARD_ELEMENT_MAPPING_ID_SEQ");
        simpleJdbcTemplate.update(
                sql,
                id,
                wizardElementDefinition.getCategoryId(),
                wizardElementDefinition.getElementDefintionId(),
                wizardElementDefinition.getTitle(),
                wizardElementDefinition.getDescription(),
                wizardElementDefinition.isRequired() ? "y" : "n",
                wizardElementDefinition.isAutoImport() ? "y" : "n",
                wizardElementDefinition.getSortOrder());
        wizardElementDefinition.setId(id);
    }

    public void update(WizardElementDefinition wizardElementDefinition) {
        String sql = "update WIZARD_ELEMENT_MAPPING set CATEGORY_ID=?, ELEMENT_ID=?, TITLE=?, DESCRIPTION=?, IS_REQUIRED=?, "
                + "AUTO_IMPORT=?, SORT_ORDER=? where id=?";
        simpleJdbcTemplate.update(
                sql,
                wizardElementDefinition.getCategoryId(),
                wizardElementDefinition.getElementDefintionId(),
                wizardElementDefinition.getTitle(),
                wizardElementDefinition.getDescription(),
                wizardElementDefinition.isRequired() ? "y" : "n",
                wizardElementDefinition.isAutoImport() ? "y" : "n",
                wizardElementDefinition.getSortOrder(),
                wizardElementDefinition.getId());
    }

    public WizardElementDefinition findByCategoryIdAndSortOrder(Integer categoryId, int i) {
        String sql = "select wcem.*, wc.wizard_id FROM WIZARD_ELEMENT_MAPPING wcem, wizard_category wc where wcem.category_id=? "
                + "and wcem.sort_order=? and wcem.category_id=wc.id";
        List<WizardElementDefinition> result = simpleJdbcTemplate.query(sql, rowMapper, categoryId, i);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<WizardElementDefinition> findByWizardId(int wizardId) {
        String sql = "SELECT wcem.*, wc.wizard_id FROM WIZARD_ELEMENT_MAPPING wcem, wizard_category wc "
                + "WHERE wc.wizard_id = ? and wcem.category_id=wc.id";
        return simpleJdbcTemplate.query(sql, rowMapper, wizardId);
    }

    @Override
    public List<WizardElementDefinition> findAutoImportByCommunityId(Integer communityId) {
        String sql = "select wem.*, wc.wizard_id from wizard_element_mapping wem, wizard_category wc, wizard w where wem.auto_import='y' "
            + "and wem.category_id=wc.id and wc.wizard_id=w.id and w.wizard_group_id=?";
        return simpleJdbcTemplate.query(sql, rowMapper, communityId);
    }
    
    public List<WizardElementDefinition> findByElementDefinitionId(String elementDefinitionId) {
        String sql = "SELECT * FROM WIZARD_ELEMENT_MAPPING wcem, wizard_category wc "
            + "where wcem.element_id=? and wcem.category_id=wc.id";
        return simpleJdbcTemplate.query(sql, rowMapper, elementDefinitionId);
    }
   
}

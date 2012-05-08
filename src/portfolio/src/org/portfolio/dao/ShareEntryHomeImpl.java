/* $Name:  $ */
/* $Id: ShareEntryHomeImpl.java,v 1.20 2011/03/22 16:04:05 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.model.EntryKey;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShareEntryHomeImpl implements ShareEntryHome {

    @Autowired private AssessmentModelAssignmentHome assessmentModelAssignmentHome;
    @Autowired private TemplateElementMappingHome templateElementMappingHome;
    @Autowired private SequenceGenerator sequenceGenerator;
    
    @SuppressWarnings("unused")
	private final LogService logService = new LogService(this.getClass());
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public void store(ShareEntry shareEntry) {
        if (shareEntry.getId() == null) {
            insert(shareEntry);
        } else {
            update(shareEntry);
        }
    }

    public void remove(ShareEntry shareEntry) {
    	
        // simpleJdbcTemplate.update("delete from share_entry where share_entry_id = ?", shareEntry.getId());
    	
    	simpleJdbcTemplate.update("UPDATE share_entry SET is_deleted = 't' where share_entry_id = ?", shareEntry.getId());
    }

    public List<ShareEntry> findByShareIdElementId(String shareId, String elementId) {
    	// logService.debug(" ==> findByShareIdElementId(" + shareId + ", " + elementId + ")");
    	
        String sql = "select * from share_entry where share_id = ? and element_id = ? AND is_deleted = 'f' order by sort_order";
        return simpleJdbcTemplate.query(sql, rowMapper, shareId, elementId);
    }

    private final RowMapper<ShareEntry> rowMapper = new RowMapper<ShareEntry>() {
        public ShareEntry mapRow(ResultSet rs, int arg1) throws SQLException {
            ShareEntry shareEntry = new ShareEntry();
            int shareEntryId = rs.getInt("share_entry_id");
            shareEntry.setId(shareEntryId);
            shareEntry.setShareId(rs.getString("share_id"));
            shareEntry.setEntryIndex(rs.getBigDecimal("entry_index"));
            shareEntry.setSortOrder(rs.getInt("sort_order"));
            shareEntry.setTemplateElementMapping(templateElementMappingHome.findById(rs.getString("element_id")));
            
            shareEntry.setIsDeleted(rs.getString("IS_DELETED").contentEquals("t") ? true : false);

            shareEntry.setIsHidden(rs.getString("IS_HIDDEN").contentEquals("N") ? false : true);
            
            AssessmentModelAssignment assessmentModelAssignment = assessmentModelAssignmentHome.findByAssessedItemIdAndPortfolioItemType(
                    shareEntryId,
                    PortfolioItemType.PORTFOLIO_ELEMENT);
            shareEntry.setAssessmentModelAssignment(assessmentModelAssignment);
            return shareEntry;
        }
    };
    
    public List<ShareEntry> findByShareId(String shareId) {
    	return _findByShareId(shareId, false);
    }

    public List<ShareEntry> findHiddenByShareId(String shareId) {
    	return _findByShareId(shareId, true);
    }
    
    private List<ShareEntry> _findByShareId(String shareId, boolean is_hidden) {
        String sql = "select * from share_entry where share_id = ? AND is_deleted = 'f' AND is_hidden = ? order by element_id";
        return simpleJdbcTemplate.query(sql, rowMapper, shareId, is_hidden ? "Y" : "N");
    }

    public List<ShareEntry> findByShareIdCategoryId(String shareId, int categoryId) {
        return simpleJdbcTemplate.query(
                "select * from share_entry where share_id = ? and category_id = ? AND is_deleted = 'f' AND is_hidden = 'N' order by sort_order",
                rowMapper,
                shareId,
                categoryId);
    }

    public ShareEntry findByShareEntryId(int shareEntryId) {
        String sql = "select * from share_entry where share_entry_id = ? AND is_deleted = 'f' AND is_hidden = 'N'";
        List<ShareEntry> result = simpleJdbcTemplate.query(sql, rowMapper, shareEntryId);
        return result.isEmpty() ? null : result.get(0);
    }

    protected void update(ShareEntry shareEntry) {
        String sql = "update share_entry set element_id = ?,share_id = ?,entry_index = ?"
                + ",category_id = ?,sort_order = ? where element_id = ? and share_id = ? and entry_index = ? AND is_deleted = ? AND is_hidden = ?";
        simpleJdbcTemplate.update(
                sql,
                shareEntry.getElementId(),
                shareEntry.getShareId(),
                shareEntry.getEntryIndex(),
                shareEntry.getCategoryId(),
                shareEntry.getSortOrder() <= 0 ? null : shareEntry.getSortOrder(),
                shareEntry.getElementId(),
                shareEntry.getShareId(),
                shareEntry.getEntryIndex(),
                shareEntry.isDeleted() ? "t" : "f",
                shareEntry.isIsHidden() ? "Y" : "N");
    }

    /**
     * Inserts a new instance of the data element. The store method needs to guarantee that the data object is of type ShareEntry.
     * 
     * @param shareEntry the instance to create.
     * @throws SQLException if there is a problem inserting.
     */
    protected void insert(ShareEntry shareEntry) {
        String sql = "insert into share_entry(share_entry_id,element_id,share_id,entry_index,category_id,sort_order,is_deleted, is_hidden) values (?,?,?,?,?,?,?,?)";
        int id = sequenceGenerator.getNextSequenceNumber("share_id_seq");
        
        while (findByShareEntryId(id) != null) {
        	id = sequenceGenerator.getNextSequenceNumber("share_id_seq");
        }
        
        int sortOrder = shareEntry.getSortOrder() <= 0 ? null : shareEntry.getSortOrder();
        simpleJdbcTemplate.update(
                sql,
                id,
                shareEntry.getElementId(),
                shareEntry.getShareId(),
                shareEntry.getEntryIndex(),
                shareEntry.getCategoryId(),
                sortOrder,
                shareEntry.isDeleted() ? "t" : "f",
                shareEntry.isIsHidden() ? "Y" : "N");
        
        shareEntry.setId(id);
    }

    public void deleteShareEntries(String shareId) {
        // simpleJdbcTemplate.update("delete from share_entry where share_id = ? ", shareId);
    	simpleJdbcTemplate.update("update share_entry SET is_deleted = 't' where share_id = ? ", shareId);
    }

    public List<ShareEntry> findByPersonIdAndAssessmentModelId(String personId, Integer assessmentModelId) {
        String sql = "select se.* from share_entry se, share_definition sd, at_assessment_model_assnmnt ama "
                + "where se.share_id=sd.share_id and sd.person_id=? AND se.is_deleted = 'f' AND se.is_hidden = 'N' AND sd.is_deleted = 'f' and se.share_entry_id = ama.assessed_item_type_id "
                + "and ama.assessed_item_type = ? and ama.assessment_model_id = ?";
        return simpleJdbcTemplate.query(sql, rowMapper, personId, PortfolioItemType.PORTFOLIO_ELEMENT.toString(), assessmentModelId);
    }

    public boolean entryExistsInPortfolio(EntryKey entryKey, String portfolioId) {
        String sql = "select count(*) from share_entry se, wizard_element_mapping wem, template_elements_mapping tem "
                + "where wem.element_id=? and wem.id=tem.wizard_element_id and tem.mapping_id=se.element_id and "
                + "se.entry_index=? and se.share_id=? AND se.is_deleted = 'f' AND se.is_hidden = 'N'";
        return simpleJdbcTemplate.queryForInt(sql, entryKey.getElementId(), entryKey.getEntryId(), portfolioId) > 0;
    }

    public void deleteForEntry(EntryKey entryKey) {
        simpleJdbcTemplate.update(
                // "delete from share_entry where element_id=? and entry_index=? and share_id in (select share_id from share_definition where person_id=?)",
        		"UPDATE share_entry SET is_deleted = 't' WHERE element_id=? and entry_index=? and share_id in (select share_id from share_definition where person_id=?)",
                entryKey.getElementId(),
                entryKey.getEntryId(),
                entryKey.getPersonId());
    }
}

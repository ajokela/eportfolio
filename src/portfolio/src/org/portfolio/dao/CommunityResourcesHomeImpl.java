package org.portfolio.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.bus.ElementManager;
import org.portfolio.bus.TagManager;
import org.portfolio.dao.community.CommunityHome;
import org.portfolio.model.CommunityResource;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.util.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityResourcesHomeImpl implements CommunityResourcesHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;
    
    @Autowired
    private SequenceGenerator sequenceGenerator;
    
    @Autowired private CommunityHome communityHome;
    @Autowired private ElementManager elementManager;
    @Autowired private PersonHome personHome;
    @Autowired private TagManager tagManager;
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
    private RowMapper<CommunityResource> rowMapper = new RowMapper<CommunityResource>() {
    	public CommunityResource mapRow(ResultSet rs, int arg1) throws SQLException {
    		CommunityResource cr = new CommunityResource();
    		
    		cr.setCommunityId(rs.getInt("COMMUNITY_ID"));
    		cr.setEntryId(rs.getInt("ENTRY_ID"));
    		cr.setId(rs.getInt("ID"));
    		cr.setPlace(rs.getInt("PLACE"));
    		cr.setResourceType(rs.getString("RESOURCE_TYPE"));
    		cr.setCreatedAt(rs.getTimestamp("CREATED_AT"));
    		cr.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
    		cr.setIsDeleted(rs.getString("IS_DELETED").contentEquals("T"));
    		cr.setPersonId(rs.getString("PERSON_ID"));
    		
    		cr.setTags(tagManager.getTagsByEntryId(BigDecimal.valueOf(cr.getEntryId())));
    		
    		Community community = communityHome.findByCommunityId(cr.getCommunityId());
    		
    		EntryKey ek = new EntryKey(community.getPerson().getPersonId(), cr.getResourceType(), new BigDecimal(cr.getEntryId()));
    		
    		cr.setEntryKey(ek);
    		
    		ElementDataObject elementInstance = elementManager.findElementInstance(ek.getElementId(), cr.getPersonId(), ek.getEntryId(), false, cr.getPersonId());
    		
    		if(elementInstance != null) {
	    		
	    		cr.setName(elementInstance.getFileName());
	    		cr.setDescription(elementInstance.getDescription());
	    		cr.setSize(elementInstance.getSize());
	    		cr.setHumanReadableSize(elementInstance.getFormattedSize());
	    		
	    		cr.setElementName(elementInstance.getEntryName());
    		}	
    		
    		Person owner = personHome.findByPersonId(cr.getPersonId());
    		
    		cr.setOwner(owner);
    		
    		return cr;
    		
    	}
    };
    
	public List<CommunityResource> findByCommunityId(int community_id) {
		
		String sql = "SELECT * FROM communities_resources WHERE community_id = ? AND is_deleted = 'F' ORDER BY PLACE";
		
		return simpleJdbcTemplate.query(sql, rowMapper, community_id);
	}
	
	public CommunityResource findByCommunityResourceId(int resource_id) {
		String sql = "SELECT * FROM communities_resources WHERE id = ?";
		
		List<CommunityResource> resources = simpleJdbcTemplate.query(sql, rowMapper, resource_id);
		
		if(resources != null && resources.size() > 0) {
			return resources.get(0);
		}
		
		return null;
	}
	
	public CommunityResource findByCommunityIdAndEntryId(int community_id, int entry_id) {
		String sql = "SELECT * FROM communities_resources WHERE community_id = ? AND entry_id = ?";

		List<CommunityResource> resources = simpleJdbcTemplate.query(sql, rowMapper, community_id, entry_id);
		
		if(resources != null && resources.size() > 0) {
			return resources.get(0);
		}
		
		return null;
		
	}
	
	public void add(CommunityResource cr) {
		
		String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
		CommunityResource existCr = findByCommunityIdAndEntryId(cr.getCommunityId(), cr.getEntryId());
		
		if(existCr == null) {
			
			String sp = "sysdate";
	    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
	    		sp += "()";
	    	}
			
			int id = sequenceGenerator.getNextSequenceNumber("COMMUNITIES_RESOURCES_SEQ");
			
			String sql = "INSERT INTO communities_resources (id, community_id, entry_id, place, resource_type, person_id, created_at, updated_at, is_deleted ) VALUES (?, ?, ?, ?, ?, ?, " + sp + ", " + sp + ", ?)";
		
			cr.setId(id);
			cr.setIsDeleted(false);
			
			simpleJdbcTemplate.update(sql, cr.getId(), cr.getCommunityId(), cr.getEntryId(), cr.getPlace(), cr.getResourceType(), cr.getPersonId(), "F" );
		} else {
			
			if(existCr.isDeleted()) {
				
				cr.setCommunityId(existCr.getCommunityId());
				cr.setCreatedAt(existCr.getCreatedAt());
				cr.setUpdatedAt(existCr.getUpdatedAt());
				cr.setDescription(existCr.getDescription());
				cr.setEncodedId(existCr.getEncodedId());
				cr.setEntryId(existCr.getEntryId());
				cr.setEntryKey(existCr.getEntryKey());
				cr.setId(existCr.getId());
				cr.setIsDeleted(false);
				cr.setName(existCr.getName());
				cr.setOwner(existCr.getOwner());
				cr.setPersonId(existCr.getPersonId());
				cr.setPlace(existCr.getPlace());
				cr.setResourceType(existCr.getResourceType());
				
				update(cr);
			
			}
		}
		
	}
	
	public void remove(CommunityResource cr) {
		
		cr.setIsDeleted(true);
		
		update(cr);
	}
	
	public void remove(int resource_id) {
		CommunityResource cr = findByCommunityResourceId(resource_id);
		
		if(cr != null) {
			remove(cr);
		}
		
	}
	
	public void update(CommunityResource cr) {
		
		String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
		
		String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
		
		String sql = "UPDATE communities_resources SET place = ?, entry_id = ?, community_id = ?, resource_type = ?, person_id = ?, updated_at = " + sp + ", is_deleted = ? WHERE id = ?";
		
		simpleJdbcTemplate.update(sql, cr.getPlace(), cr.getEntryId(), cr.getCommunityId(), cr.getResourceType(), cr.getPersonId(), cr.isDeleted() ? "T" : "F", cr.getId());
	}
	
}
/* $Name:  $ */
/* $Id: ViewerHomeImpl.java,v 1.23 2011/03/25 12:35:20 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioHistory;
import org.portfolio.model.Viewer;
import org.portfolio.model.PortfolioHistory.Action;
import org.portfolio.model.Viewer.ViewType;
import org.portfolio.model.community.CommunityRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("viewerHome")
public class ViewerHomeImpl implements ViewerHome {

    @Autowired
    private PersonHome personHome;
    
    @Autowired
    private PortfolioHome portfolioHome;
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<Viewer> rowMapper = new RowMapper<Viewer>() {
        public Viewer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Viewer viewer = new Viewer(portfolioHome);
            viewer.setShareId(rs.getString("share_id"));
            viewer.setEmailSentDate(rs.getTimestamp("email_sent_date"));
            viewer.setRead("read".equals(rs.getString("view_status")));
            viewer.setFlagged("flagged".equals(rs.getString("flag_status")));
            viewer.setPerson(personHome.findByPersonId(rs.getString("person_id")));
            viewer.setFolderId(rs.getBigDecimal("folder_id"));
            Timestamp timestamp = rs.getTimestamp("date_shared");
            
            viewer.setIsDeleted(rs.getString("IS_DELETED").contentEquals("t") ? true : false);
            
            if (timestamp != null) {
                viewer.setDateShared(new java.util.Date(timestamp.getTime()));
            }
            viewer.setViewType(ViewType.valueOf(rs.getString("view_type")));
            return viewer;
        }
    };

    public List<Viewer> findByPersonId(String personId) {
        return simpleJdbcTemplate.query("select * from viewer where person_id = ? AND is_deleted = 'f'", rowMapper, personId);
    }

    public List<Viewer> findByShareId(String shareId) {
    	
    	return simpleJdbcTemplate.query("select * from viewer where share_id = ? AND is_deleted = 'f'", rowMapper, shareId);
    	
    }

    public Viewer findInstance(String personId, String shareId) {
        List<Viewer> results = simpleJdbcTemplate.query(
                "select * from viewer where person_id = ? and share_id = ? AND is_deleted = 'f'",
                rowMapper,
                personId,
                shareId);
        return results.isEmpty() ? null : results.get(0);
    }

    public void remove(Viewer viewer) {
        deleteByPersonIdShareId(viewer.getPersonId(), viewer.getShareId());
    }

    public void update(Viewer viewer) {
        String sql = "update viewer set email_sent_date = ?,view_status = ?,flag_status =?,folder_id=?,view_type=?,is_deleted=? where person_id=? and share_id=?";
        Date date = viewer.getEmailSentDate() == null ? null : new Date(viewer.getEmailSentDate().getTime());
        String viewStatus = viewer.isRead() ? "read" : "notRead";
        String flagStatus = viewer.isFlagged() ? "flagged" : "notFlagged";
        simpleJdbcTemplate.update(
                sql,
                date,
                viewStatus,
                flagStatus,
                viewer.getFolderId(),
                viewer.getViewType().toString(),
                viewer.isDeleted() ? "t" : "f",
                viewer.getPersonId(),
                viewer.getShareId());
    }

    public void insert(Viewer viewer) {
    	
    	PortfolioHistory history = new PortfolioHistory(personHome);
    	Person vp = personHome.findByPersonId(viewer.getPersonId());
    	
    	String description = new String("Adding " + vp.getDisplayName() + " (" +  vp.getUsername() + ") to Portfolio[" + viewer.getShareId() + "] as " + (viewer.getViewType() == ViewType.OWNER ? "OWNER" : "VIEWER") );
    	history.setAction(Action.SHARE_ADD_USER);
    	history.setPersonId(viewer.getOwner().getPersonId());
    	history.setShareId(viewer.getShareId());
    	history.setDescription(description);
    	
    	portfolioHome.newPortfolioHistoryEntry(history);
    	
    	String sql;
    	
    	sql = "SELECT * FROM viewer WHERE person_id = ? AND share_id = ?";
    	
    	List<Viewer> viewers = simpleJdbcTemplate.query(sql, rowMapper, vp.getPersonId(), viewer.getShareId());
    	
    	if(viewers.size() > 0) {
        
    		sql = "UPDATE viewer SET email_sent_date=?,view_status=?,flag_status=?,date_shared=?,folder_id=?,view_type=?,is_deleted=? WHERE person_id=? AND share_id=?";
        
	        simpleJdbcTemplate.update(
	                sql,
	                viewer.getEmailSentDate(),
	                "notRead",
	                "notFlagged",
	                viewer.getDateShared(),
	                viewer.getFolderId(),
	                viewer.getViewType().toString(),
	                viewer.isDeleted() ? "t" : "f",
	    	        viewer.getPersonId(),
	    	        viewer.getShareId());
    		
    	}
    	else {
	    	
	        sql = "insert into viewer(person_id,share_id,email_sent_date,view_status,flag_status,date_shared,folder_id,view_type,is_deleted) "
	                + "values (?,?,?,?,?,?,?,?,?)";
	
	        simpleJdbcTemplate.update(
	                sql,
	                viewer.getPersonId(),
	                viewer.getShareId(),
	                viewer.getEmailSentDate(),
	                "notRead",
	                "notFlagged",
	                viewer.getDateShared(),
	                viewer.getFolderId(),
	                viewer.getViewType().toString(),
	                viewer.isDeleted() ? "t" : "f");
    	}        
    }

    public void deleteByPersonIdShareId(String personId, String shareId) {
    	
    	PortfolioHistory history = new PortfolioHistory(personHome);
    	Person vp = personHome.findByPersonId(personId);
    	
    	String description = new String("Removing " + vp.getDisplayName() + " (" +  vp.getUsername()  + ") from Portfolio[" + shareId + "]" );
    	history.setAction(Action.SHARE_DEL_USER);
    	history.setPersonId(vp.getPersonId());
    	history.setShareId(shareId);
    	history.setDescription(description);
    	
    	portfolioHome.newPortfolioHistoryEntry(history);

    	
        // simpleJdbcTemplate.update("delete from viewer where person_id = ? and share_id = ?", personId, shareId);
    	simpleJdbcTemplate.update("UPDATE viewer SET is_deleted = 't' where person_id = ? and share_id = ?", personId, shareId);
    	
    }

    public List<Viewer> findXMostRecentByPersonId(String userId, int x) {
        String sql = "select * from (select * from viewer where person_id=? AND is_deleted = 'f' AND date_shared is not null order by date_shared desc) LIMIT " + x;
        return simpleJdbcTemplate.query(sql, rowMapper, userId);
    }

    public void deleteByShareId(String shareId) {
    	
    	PortfolioHistory history = new PortfolioHistory(personHome);
    	Portfolio p = portfolioHome.findByShareId(shareId);
    
    	if(p != null) {
	    	
	    	String description = new String("Removing Portfolio[" + shareId + "]" );
	    	history.setAction(Action.SHARE_DELETE);
	    	history.setPersonId(p.getPerson().getPersonId());
	    	history.setShareId(shareId);
	    	history.setDescription(description);
	    	
	    	portfolioHome.newPortfolioHistoryEntry(history);
    	
    	}
    	
        // simpleJdbcTemplate.update("delete from viewer where share_id = ?", shareId);
    	simpleJdbcTemplate.update("UPDATE viewer SET is_deleted = 't' where share_id = ?", shareId); 
    	
    }

    public List<Viewer> findEvaluatorsByShareId(String shareId) {
        String sql = "select v.* from viewer v, cy_role_assignment ra, share_definition sd, template t "
                + "where v.share_id=? AND v.is_deleted = 'f' AND sd.is_deleted = 'f' and v.share_id=sd.share_id and sd.template_id=t.template_id and t.community_id=ra.community_id "
                + "and ra.role_type=? and ra.person_id=v.person_id";
        return simpleJdbcTemplate.query(sql, rowMapper, shareId, CommunityRoleType.EVALUATOR.getCode());
    }

    public List<Viewer> findEvaluatorsByShareEntryId(int shareEntryId) {
        String sql = "select v.* from viewer v, cy_role_assignment ra, share_definition sd, template t, share_entry se "
                + "where v.share_id=se.share_id AND v.is_deleted = 'f' and v.share_id=sd.share_id AND sd.is_deleted = 'f' and sd.template_id=t.template_id and t.community_id=ra.community_id "
                + "and ra.role_type=? and ra.person_id=v.person_id and se.share_entry_id=?";
        return simpleJdbcTemplate.query(sql, rowMapper, CommunityRoleType.EVALUATOR.getCode(), shareEntryId);
    }

    public boolean isViewableBy(String portfolioId, String personId) {
        return simpleJdbcTemplate.queryForInt(
                "select count(*) from share_definition sd where sd.share_id=? AND sd.is_deleted = 'f' and "
                        + "(sd.public_view=1 or exists (select 'x' from viewer v where v.share_id=sd.share_id and v.person_id=? AND is_deleted = 'f'))",
                portfolioId,
                personId) > 0;
    }
}

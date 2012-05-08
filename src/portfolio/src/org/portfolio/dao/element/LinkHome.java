/* $Name:  $ */
/* $Id: LinkHome.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.element;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.portfolio.dao.AbstractElementHome;
import org.portfolio.dao.DataAccessException;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Link;
import org.springframework.jdbc.core.RowMapper;

public class LinkHome extends AbstractElementHome {
    
	
	public LinkHome() {
		super();
	}
	
    private final RowMapper<Link> rowMapper = new RowMapper<Link>() {
        public Link mapRow(ResultSet rs, int arg1) throws SQLException {
            Link link = new Link();
            link.setPersonId(rs.getString("person_id"));
            link.setEntryId(rs.getBigDecimal("entry_id"));
            link.setEntryName(rs.getString("entry_name"));
            link.setUrl(rs.getString("url"));
            link.setDescription(rs.getString("description"));
            link.setAuthor(rs.getString("author"));
            link.setDateCreated(rs.getTimestamp("date_created"));
            link.setModifiedDate(rs.getTimestamp("date_modified"));            
            link.setPlace(rs.getInt("place"));
            return link;
        }
    };

    @Override
    public void store(ElementDataObject data) {
        Link link = (Link) data;

        if (link.isNew()) {
            insert(link);
        } else {
            update(link);
        }
    }

    private void insert(Link link) {
    	
        int id = sequenceGenerator.getNextSequenceNumber("ENTRYID");
        getSimpleJdbcTemplate().update(
                "INSERT INTO link (PERSON_ID, ENTRY_ID, ENTRY_NAME, URL, DESCRIPTION, AUTHOR, DATE_CREATED, DATE_MODIFIED, PLACE) values(?,?,?,?,?,?,?,?,?)",
                link.getPersonId(),
                id,
                link.getEntryName(),
                link.getUrl(),
                link.getDescription(),
                link.getAuthor(),
                link.getDateCreated(),
                link.getModifiedDate(),
        		link.getPlace());
        link.setEntryId(new BigDecimal(id));
    }

    private void update(Link link) {
    	//logService.debug ("LinkHome -> update");
        getSimpleJdbcTemplate().update(
        		"update link set person_id=?, entry_name=?, url=?, description=?, author=?, date_created=?, date_modified=?, place=? where entry_id=?",
                link.getPersonId(),
                link.getEntryName(),
                link.getUrl(),
                link.getDescription(),
                link.getAuthor(),
                link.getDateCreated(),
                link.getModifiedDate(),
                link.getPlace(),
                link.getEntryId());
    }

    @Override
    public void remove(ElementDataObject data) {
        Link link = (Link) data;
        getSimpleJdbcTemplate().update("delete from link where entry_id=?", link.getEntryId());
    }

    public List<? extends ElementDataObject> findByPersonId(String personId) {
        return getSimpleJdbcTemplate().query("select * from link where person_id=?", rowMapper, personId);
    }

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
    	
    	if (personId != null && entryId != null) {
    		
    		if ( getSimpleJdbcTemplate() == null) {
    			logService.debug("getSimpleJdbcTemplate() is null");
    		}
    		else {
	    		
    			if (rowMapper == null) {
    				logService.debug("rowMapper is null");
    			} else {
	    			
			        List<Link> result = getSimpleJdbcTemplate().query("select * from link where person_id=? and entry_id=?", rowMapper, personId, entryId);
			        return result.isEmpty() ? null : result.get(0);
    			}
    		}
    	}
    	else {

    		if (personId == null) 
    			logService.debug("personId is null");
    		
    		if (entryId == null) 
    			logService.debug("entryId is null");
    		
    	}
    	
    	return null;
    }
    
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("select COUNT(ENTRY_ID) from link where person_id=? and entry_id=?");
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
            	if(rs.getInt(1) != 0) {
            		return true;
            	}
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return false;
	}

}

/* $Name:  $ */
/* $Id: SkiDocumentationHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.element;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.SkiDocumentation;

public class SkiDocumentationHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        if (!(data instanceof SkiDocumentation)) {
        } else {
            SkiDocumentation ski_documentation = (SkiDocumentation) data;
            logService.debug("Is instance new?" + ski_documentation.isNew());
            if (ski_documentation.isNew()) {
                logService.debug("inserting new instance");
                insert(ski_documentation);
            } else {
                logService.debug("Updating instance.");
                update(ski_documentation);
            }
        }
    }

    public void remove(ElementDataObject data) {
        if (!(data instanceof SkiDocumentation)) {
        } else {
            SkiDocumentation ski_documentation = (SkiDocumentation) data;
            delete(ski_documentation);
        }
    }

    public List<SkiDocumentation> findByPersonId(String personId) {
        List<SkiDocumentation> elements = new ArrayList<SkiDocumentation>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                SkiDocumentation ski_documentation = new SkiDocumentation();
                ski_documentation.setPersonId(rs.getString("PERSON_ID"));
                ski_documentation.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                ski_documentation.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                ski_documentation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                ski_documentation.setEntryName(rs.getString("ENTRY_NAME"));
                ski_documentation.setText(rs.getString("TEXT"));
                // logService.debug("Adding instance " + ski_documentation + " to the elements list.");
                elements.add(ski_documentation);
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } catch (Exception ex) {
            logService.error("Exception in findByPersonId()", ex);
        } finally {
            close(conn, ps, rs);
        }

        return elements;
    }

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
        SkiDocumentation ski_documentation = new SkiDocumentation();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_SINGLE);
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
                ski_documentation.setPersonId(rs.getString("PERSON_ID"));
                ski_documentation.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                ski_documentation.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                ski_documentation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                ski_documentation.setEntryName(rs.getString("ENTRY_NAME"));
                ski_documentation.setText(rs.getString("TEXT"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return ski_documentation;
    }

    protected void update(SkiDocumentation ski_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, ski_documentation.getEntryName());
            ps.setString(2, ski_documentation.getText());
            ps.setString(3, ski_documentation.getPersonId());
            ps.setBigDecimal(4, ski_documentation.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            logService.error("Exception caught in update", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    protected void insert(SkiDocumentation ski_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, ski_documentation.getPersonId());
            ps.setInt(2, id);
            ski_documentation.setEntryId(String.valueOf(id));
            ps.setString(3, ski_documentation.getEntryName());
            ps.setString(4, ski_documentation.getText());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    protected void delete(SkiDocumentation ski_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, ski_documentation.getPersonId());
            ps.setBigDecimal(2, ski_documentation.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",TEXT FROM SKI_DOCUMENTATION WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",TEXT FROM SKI_DOCUMENTATION WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM SKI_DOCUMENTATION WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the ski_documentation table. */
    private final String INSERT_QUERY = "INSERT INTO SKI_DOCUMENTATION(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,TEXT) VALUES (?,?," + sysdate + "," + sysdate + ",?,?)";

    /** The query to INSERT a new row into the ski_documentation table. */
    private final String UPDATE_QUERY = "UPDATE SKI_DOCUMENTATION SET MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?,TEXT = ?"
            + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM SKI_DOCUMENTATION  WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_SINGLE_COUNT);
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

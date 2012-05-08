/* $Name:  $ */
/* $Id: TravelHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.element;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Travel;

public class TravelHome extends AbstractElementHome {
    public void store(ElementDataObject data) {
        if (!(data instanceof Travel)) {
        } else {
            Travel travel = (Travel) data;
            logService.debug("Is instance new?" + travel.isNew());
            if (travel.isNew()) {
                logService.debug("inserting new instance");
                insert(travel);
            } else {
                logService.debug("Updating instance.");
                update(travel);
            }
        }
    }

    public void remove(ElementDataObject data) {
        if (!(data instanceof Travel)) {
        } else {
            Travel travel = (Travel) data;
            delete(travel);
        }
    }

    public List<Travel> findByPersonId(String personId) {
        List<Travel> elements = new ArrayList<Travel>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Travel travel = new Travel();
                travel.setPersonId(rs.getString("PERSON_ID"));
                travel.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                travel.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                travel.setEntryName(rs.getString("ENTRY_NAME"));
                travel.setFromDate(rs.getTimestamp("FROM_DATE"));
                travel.setToDate(rs.getTimestamp("TO_DATE"));
                travel.setDescription(rs.getString("DESCRIPTION"));
                travel.setReflection(rs.getString("REFLECTION"));
                travel.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                // logService.debug("Adding instance " + travel + " to the elements list.");
                elements.add(travel);
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
        Travel travel = new Travel();
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
                travel.setPersonId(rs.getString("PERSON_ID"));
                travel.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                travel.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                travel.setEntryName(rs.getString("ENTRY_NAME"));
                travel.setFromDate(rs.getTimestamp("FROM_DATE"));
                travel.setToDate(rs.getTimestamp("TO_DATE"));
                travel.setDescription(rs.getString("DESCRIPTION"));
                travel.setReflection(rs.getString("REFLECTION"));
                travel.setDateCreated(rs.getTimestamp("DATE_CREATED"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return travel;
    }

    protected void update(Travel travel) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, travel.getEntryName());
            if (travel.getFromDate() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(travel.getFromDate().getTime()));
            if (travel.getToDate() == null)
                ps.setNull(3, Types.TIMESTAMP);
            else
                ps.setTimestamp(3, new java.sql.Timestamp(travel.getToDate().getTime()));
            ps.setString(4, travel.getDescription());
            ps.setString(5, travel.getReflection());
            ps.setString(6, travel.getPersonId());
            ps.setBigDecimal(7, travel.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            logService.error("Exception caught in update", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    protected void insert(Travel travel) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, travel.getPersonId());
            ps.setInt(2, id);
            travel.setEntryId(String.valueOf(id));
            ps.setString(3, travel.getEntryName());
            if (travel.getFromDate() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(travel.getFromDate().getTime()));
            if (travel.getToDate() == null)
                ps.setNull(5, Types.TIMESTAMP);
            else
                ps.setTimestamp(5, new java.sql.Timestamp(travel.getToDate().getTime()));
            ps.setString(6, travel.getDescription());
            ps.setString(7, travel.getReflection());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    protected void delete(Travel travel) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, travel.getPersonId());
            ps.setBigDecimal(2, travel.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,MODIFIED_DATE,ENTRY_NAME,FROM_DATE"
            + ",TO_DATE,DESCRIPTION,REFLECTION,DATE_CREATED FROM TRAVEL WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,MODIFIED_DATE,ENTRY_NAME,FROM_DATE"
            + ",TO_DATE,DESCRIPTION,REFLECTION,DATE_CREATED FROM TRAVEL WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM TRAVEL WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to INSERT a new row into the travel table. */
    private final String INSERT_QUERY = "INSERT INTO TRAVEL(PERSON_ID,ENTRY_ID,MODIFIED_DATE,ENTRY_NAME,FROM_DATE"
            + ",TO_DATE,DESCRIPTION,REFLECTION,DATE_CREATED) VALUES (?,?," + sysdate + ",?" + ",?,?,?,?," + sysdate + ")";

    /** The query to INSERT a new row into the travel table. */
    private final String UPDATE_QUERY = "UPDATE TRAVEL SET MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?,FROM_DATE = ?"
            + ",TO_DATE = ?,DESCRIPTION = ?,REFLECTION = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM TRAVEL  WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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

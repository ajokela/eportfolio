/* $Name:  $ */
/* $Id: DegRequirementHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
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
import org.portfolio.model.DegRequirement;
import org.portfolio.model.ElementDataObject;

public class DegRequirementHome extends AbstractElementHome {
    public void store(ElementDataObject data) {
        if (!(data instanceof DegRequirement)) {
        } else {
            DegRequirement deg_requirement = (DegRequirement) data;
            logService.debug("Is instance new?" + deg_requirement.isNew());
            if (deg_requirement.isNew()) {
                logService.debug("inserting new instance");
                insert(deg_requirement);
            } else {
                logService.debug("Updating instance.");
                update(deg_requirement);
            }
        }
    }

    public void remove(ElementDataObject data) {
        if (!(data instanceof DegRequirement)) {
        } else {
            DegRequirement deg_requirement = (DegRequirement) data;
            delete(deg_requirement);
        }
    }

    public List<DegRequirement> findByPersonId(String personId) {
        List<DegRequirement> elements = new ArrayList<DegRequirement>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                DegRequirement deg_requirement = new DegRequirement();
                deg_requirement.setPersonId(rs.getString("PERSON_ID"));
                deg_requirement.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                deg_requirement.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                deg_requirement.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                deg_requirement.setEntryName(rs.getString("ENTRY_NAME"));
                deg_requirement.setBulletinYear(rs.getString("BULLETIN_YEAR"));
                deg_requirement.setRequirements(rs.getString("REQUIREMENTS"));
                // logService.debug("Adding instance " + deg_requirement + " to the elements list.");
                elements.add(deg_requirement);
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
        DegRequirement deg_requirement = new DegRequirement();
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
                deg_requirement.setPersonId(rs.getString("PERSON_ID"));
                deg_requirement.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                deg_requirement.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                deg_requirement.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                deg_requirement.setEntryName(rs.getString("ENTRY_NAME"));
                deg_requirement.setBulletinYear(rs.getString("BULLETIN_YEAR"));
                deg_requirement.setRequirements(rs.getString("REQUIREMENTS"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return deg_requirement;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type DegRequirement.
     * 
     * @param deg_requirement the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(DegRequirement deg_requirement) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, deg_requirement.getEntryName());
            ps.setString(2, deg_requirement.getBulletinYear());
            ps.setString(3, deg_requirement.getRequirements());
            ps.setString(4, deg_requirement.getPersonId());
            ps.setBigDecimal(5, deg_requirement.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            logService.error("Exception caught in update", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /**
     * Inserts a new instance of the data element. The store method needs to
     * guarantee that the data object is of type DegRequirement.
     * 
     * @param deg_requirement the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(DegRequirement deg_requirement) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (deg_requirement.isRemote()) ? deg_requirement.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, deg_requirement.getPersonId());
            ps.setInt(2, id);
            deg_requirement.setEntryId(String.valueOf(id));
            ps.setString(3, deg_requirement.getEntryName());
            ps.setString(4, deg_requirement.getBulletinYear());
            ps.setString(5, deg_requirement.getRequirements());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /**
     * Deletes the instance of the data element. The store method needs to
     * guarantee that the data object is of type Name.
     * 
     * @param name the instance to delete. @ if there is a problem deleting.
     */
    protected void delete(DegRequirement deg_requirement) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, deg_requirement.getPersonId());
            ps.setBigDecimal(2, deg_requirement.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,BULLETIN_YEAR,REQUIREMENTS FROM DEG_REQUIREMENT WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,BULLETIN_YEAR,REQUIREMENTS FROM DEG_REQUIREMENT WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM DEG_REQUIREMENT WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the deg_requirement table. */
    private final String INSERT_QUERY = "INSERT INTO DEG_REQUIREMENT(PERSON_ID,ENTRY_ID,DATE_CREATED"
            + ",MODIFIED_DATE,ENTRY_NAME,BULLETIN_YEAR,REQUIREMENTS) VALUES (?,?," + sysdate + "," + sysdate + ",?,?,?)";

    /** The query to INSERT a new row into the deg_requirement table. */
    private final String UPDATE_QUERY = "UPDATE DEG_REQUIREMENT SET MODIFIED_DATE = " + sysdate
            + ",ENTRY_NAME = ?,BULLETIN_YEAR = ?,REQUIREMENTS = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM DEG_REQUIREMENT  WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

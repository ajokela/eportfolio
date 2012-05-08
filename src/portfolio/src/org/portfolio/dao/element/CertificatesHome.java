/* $Name:  $ */
/* $Id: CertificatesHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
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
import org.portfolio.model.Certificates;
import org.portfolio.model.ElementDataObject;

/**
 * Handles the persistence of the Certificates data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class CertificatesHome extends AbstractElementHome {

    /**
     * Persists the Certificates Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the Certificates object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof Certificates)) {
        } else {
            Certificates certificates = (Certificates) data;
            logService.debug("Is instance new?" + certificates.isNew());
            if (certificates.isNew()) {
                logService.debug("inserting new instance");
                insert(certificates);
            } else {
                logService.debug("Updating instance.");
                update(certificates);
            }
        }
    }

    /**
     * Deletes the instance of $table.destinationClassNameHome.
     * 
     * @param data the $table.destinationClassNameHome object to persist @ if a
     *            data type other than the one expected is found. @ if there are
     *            any problems persisting the data.
     */
    public void remove(ElementDataObject data) {
        if (!(data instanceof Certificates)) {
        } else {
            Certificates certificates = (Certificates) data;
            delete(certificates);
        }
    }

    /**
     * Method to find all instances of this particular data element for a
     * specified user. <i>Note: This will need to implement sort order as well,
     * at some time.</i>
     * 
     * @param personId of the user that we are finding elements for
     * @return a java.util.List of DataObjects of the type for this element. @
     *         if there are any problems reading from the database.
     */
    public List<Certificates> findByPersonId(String personId) {
        List<Certificates> elements = new ArrayList<Certificates>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Certificates certificates = new Certificates();
                certificates.setPersonId(rs.getString("PERSON_ID"));
                certificates.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                certificates.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                certificates.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                certificates.setEntryName(rs.getString("ENTRY_NAME"));
                certificates.setRcdDate(rs.getTimestamp("RCD_DATE"));
                certificates.setOrganization(rs.getString("ORGANIZATION"));
                certificates.setDescription(rs.getString("DESCRIPTION"));
                // logService.debug("Adding instance " + certificates + " to the elements list.");
                elements.add(certificates);
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

    /**
     * Method to fetch a single instance of a given element.
     * 
     * @param personId of the user
     * @param entryId of the element @ if this element can not be located @ if
     *            there are any fetch problems.
     */
    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
        Certificates certificates = new Certificates();
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
                certificates.setPersonId(rs.getString("PERSON_ID"));
                certificates.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                certificates.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                certificates.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                certificates.setEntryName(rs.getString("ENTRY_NAME"));
                certificates.setRcdDate(rs.getTimestamp("RCD_DATE"));
                certificates.setOrganization(rs.getString("ORGANIZATION"));
                certificates.setDescription(rs.getString("DESCRIPTION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return certificates;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Certificates.
     * 
     * @param certificates the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(Certificates certificates) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, certificates.getEntryName());
            if (certificates.getRcdDate() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(certificates.getRcdDate().getTime()));
            ps.setString(3, certificates.getOrganization());
            ps.setString(4, certificates.getDescription());
            ps.setString(5, certificates.getPersonId());
            ps.setBigDecimal(6, certificates.getEntryId());
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
     * guarantee that the data object is of type Certificates.
     * 
     * @param certificates the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(Certificates certificates) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (certificates.isRemote()) ? certificates.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, certificates.getPersonId());
            ps.setInt(2, id);
            certificates.setEntryId(String.valueOf(id));
            ps.setString(3, certificates.getEntryName());
            if (certificates.getRcdDate() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(certificates.getRcdDate().getTime()));
            ps.setString(5, certificates.getOrganization());
            ps.setString(6, certificates.getDescription());
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
    protected void delete(Certificates certificates) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, certificates.getPersonId());
            ps.setBigDecimal(2, certificates.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",RCD_DATE,ORGANIZATION,DESCRIPTION FROM CERTIFICATES WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",RCD_DATE,ORGANIZATION,DESCRIPTION FROM certificates WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM certificates WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the certificates table. */
    private final String INSERT_QUERY = "INSERT INTO CERTIFICATES(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,RCD_DATE,ORGANIZATION,DESCRIPTION) VALUES (?,?," + sysdate + "," + sysdate + ",?,?,?,?)";

    /** The query to INSERT a new row into the certificates table. */
    private final String UPDATE_QUERY = "UPDATE CERTIFICATES SET MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?,RCD_DATE = ?"
            + ",ORGANIZATION = ?,DESCRIPTION = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM CERTIFICATES WHERE PERSON_ID = ? AND ENTRY_ID = ?";

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

/* $Name:  $ */
/* $Id: RecordShareHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/**
 *
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/RecordShareHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.8 $
 * $Date: 2010/10/27 19:24:57 $
 *
 * =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 * 
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 * 
 * Copyrights:
 * 
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 * 
 * Portions Copyright (c) 2003 the r-smart group, inc.
 * 
 * Portions Copyright (c) 2003 The University of Delaware.
 * 
 * Acknowledgements
 * 
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 *
 */

package org.portfolio.dao.element;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.AbstractDataHome;
import org.portfolio.dao.DataAccessException;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.RecordShare;

/**
 * Handles the persistence of the RecordShare data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class RecordShareHome extends AbstractDataHome {
    /**
     * Persists the RecordShare Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the RecordShare object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        RecordShare record_share = (RecordShare) data;
        if (record_share.isNew()) {
            insert(record_share);
        } else {
            update(record_share);
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
        if (!(data instanceof RecordShare)) {
        } else {
            RecordShare record_share = (RecordShare) data;
            delete(record_share);
        }
    }

    /**
     * Method to find all instances of this particular data element for a
     * specified user. <i>Note: This will need to implement sort order as well,
     * at some time.</i>
     * 
     * @param recordId of the user that we are finding elements for
     * @return a java.util.List of DataObjects of the type for this element. @
     *         if there are any problems reading from the database.
     */
    public List<RecordShare> findByRecordId(String recordId) {
        List<RecordShare> elements = new ArrayList<RecordShare>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, recordId);
            rs = ps.executeQuery();

            while (rs.next()) {
                RecordShare record_share = new RecordShare();
                record_share.setElementId(rs.getString("ELEMENT_ID"));
                record_share.setShareId(rs.getString("SHARE_ID"));
                record_share.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                record_share.setRecordId(rs.getBigDecimal("RECORD_ID"));
                // logService.debug("Adding instance " + record_share + " to the elements list.");
                elements.add(record_share);
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
    public RecordShare findElementInstance(String personId, BigDecimal entryId) {
        RecordShare record_share = new RecordShare();
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
                record_share.setElementId(rs.getString("ELEMENT_ID"));
                record_share.setShareId(rs.getString("SHARE_ID"));
                record_share.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                record_share.setRecordId(rs.getBigDecimal("RECORD_ID"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return record_share;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type RecordShare.
     * 
     * @param record_share the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(RecordShare record_share) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, record_share.getElementId());
            ps.setString(2, record_share.getShareId());
            ps.setBigDecimal(3, record_share.getRecordId());
            ps.setBigDecimal(4, record_share.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            logService.error("Exception caught in update", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    /**
     * Inserts a new instance of the data element. The store method needs to
     * guarantee that the data object is of type RecordShare.
     * 
     * @param record_share the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(RecordShare record_share) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("record");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, record_share.getElementId());
            ps.setString(2, record_share.getShareId());
            ps.setInt(3, id);
            record_share.setEntryId(record_share.getEntryId());
            ps.setBigDecimal(4, new BigDecimal(id));
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    /**
     * Deletes the instance of the data element. The store method needs to
     * guarantee that the data object is of type Name.
     * 
     * @param name the instance to delete. @ if there is a problem deleting.
     */
    protected void delete(RecordShare record_share) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setBigDecimal(1, record_share.getRecordId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps);
        }
    }

    // --------------------------------------------------------------------------
    // Instance variables here.

    // --------------------------------------------------------------------------
    // Class Constants
    /** The query to select ALL relevant instances of NAME for a person. */
    private static final String SELECT_QUERY_ALL = "SELECT " + "ELEMENT_ID" + ",SHARE_ID" + ",ENTRY_ID" + ",RECORD_ID"
            + " FROM RECORD_SHARE WHERE RECORD_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private static final String SELECT_QUERY_SINGLE = "SELECT " + "ELEMENT_ID" + ",SHARE_ID" + ",ENTRY_ID" + ",RECORD_ID"
            + " FROM RECORD_SHARE WHERE RECORD_ID = ?";

    /** The query to INSERT a new row into the record_share table. */
    private static final String INSERT_QUERY = "INSERT INTO RECORD_SHARE" + "(ELEMENT_ID" + ",SHARE_ID" + ",ENTRY_ID" + ",RECORD_ID"
            + ") VALUES (" + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the record_share table. */
    private static final String UPDATE_QUERY = "UPDATE RECORD_SHARE SET " + "ELEMENT_ID = ?" + ",SHARE_ID = ?" + ",RECORD_ID = ?"
            + " WHERE RECORD_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private static final String DELETE_QUERY = "DELETE FROM RECORD_SHARE " + " WHERE RECORD_ID";
}

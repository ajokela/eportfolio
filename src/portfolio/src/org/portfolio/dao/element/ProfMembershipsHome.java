/* $Name:  $ */
/* $Id: ProfMembershipsHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/ProfMembershipsHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.9 $
 * $Date: 2010/10/27 19:24:57 $
 *
 * ============================================================================
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
 */

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
import org.portfolio.model.ProfMemberships;

/**
 * Handles the persistence of the ProfMemberships data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class ProfMembershipsHome extends AbstractElementHome {
    /**
     * Persists the ProfMemberships Object. Will perform an INSERT if it is a
     * new instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the ProfMemberships object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof ProfMemberships)) {
        } else {
            ProfMemberships prof_memberships = (ProfMemberships) data;
            logService.debug("Is instance new?" + prof_memberships.isNew());
            if (prof_memberships.isNew()) {
                logService.debug("inserting new instance");
                insert(prof_memberships);
            } else {
                logService.debug("Updating instance.");
                update(prof_memberships);
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
        if (!(data instanceof ProfMemberships)) {
        } else {
            ProfMemberships prof_memberships = (ProfMemberships) data;
            delete(prof_memberships);
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
    public List<ProfMemberships> findByPersonId(String personId) {
        List<ProfMemberships> elements = new ArrayList<ProfMemberships>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProfMemberships prof_memberships = new ProfMemberships();
                prof_memberships.setPersonId(rs.getString("PERSON_ID"));
                prof_memberships.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                prof_memberships.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                prof_memberships.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                prof_memberships.setEntryName(rs.getString("ENTRY_NAME"));
                prof_memberships.setDescription(rs.getString("DESCRIPTION"));
                prof_memberships.setRole(rs.getString("ROLE"));
                prof_memberships.setMemberSince(rs.getTimestamp("MEMBER_SINCE"));
                prof_memberships.setMemberUntil(rs.getTimestamp("MEMBER_UNTIL")); // BK 3/5/12
                prof_memberships.setPresentMember(rs.getString("PRESENT_MEMBER")); // BK 3/5/12
                prof_memberships.setComments(rs.getString("COMMENTS"));
                // logService.debug("Adding instance " + prof_memberships + " to the elements list.");
                elements.add(prof_memberships);
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
        ProfMemberships prof_memberships = new ProfMemberships();
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
                prof_memberships.setPersonId(rs.getString("PERSON_ID"));
                prof_memberships.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                prof_memberships.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                prof_memberships.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                prof_memberships.setEntryName(rs.getString("ENTRY_NAME"));
                prof_memberships.setDescription(rs.getString("DESCRIPTION"));
                prof_memberships.setRole(rs.getString("ROLE"));
                prof_memberships.setMemberSince(rs.getTimestamp("MEMBER_SINCE"));
                prof_memberships.setMemberUntil(rs.getTimestamp("MEMBER_UNTIL")); // BK 3/5/12
                prof_memberships.setPresentMember(rs.getString("PRESENT_MEMBER")); // BK 3/5/12
                prof_memberships.setComments(rs.getString("COMMENTS"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return prof_memberships;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type ProfMemberships.
     * 
     * @param prof_memberships the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(ProfMemberships prof_memberships) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, prof_memberships.getEntryName());
            ps.setString(2, prof_memberships.getDescription());
            ps.setString(3, prof_memberships.getRole());
            // bug 119 - need to check for null
            if (prof_memberships.getMemberSince() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(prof_memberships.getMemberSince().getTime()));
            // BK 3/5/12
            if (prof_memberships.getMemberUntil() == null)
                ps.setNull(5, Types.TIMESTAMP);
            else
                ps.setTimestamp(5, new java.sql.Timestamp(prof_memberships.getMemberUntil().getTime()));
            ps.setString(6, prof_memberships.getPresentMember()); // BK 3/5/12
            ps.setString(7, prof_memberships.getComments());
            ps.setString(8, prof_memberships.getPersonId());
            ps.setBigDecimal(9, prof_memberships.getEntryId());
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
     * guarantee that the data object is of type ProfMemberships.
     * 
     * @param prof_memberships the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(ProfMemberships prof_memberships) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, prof_memberships.getPersonId());
            ps.setInt(2, id);
            prof_memberships.setEntryId(String.valueOf(id));
            ps.setString(3, prof_memberships.getEntryName());
            ps.setString(4, prof_memberships.getDescription());
            ps.setString(5, prof_memberships.getRole());
            if (prof_memberships.getMemberSince() == null)
                ps.setNull(6, Types.TIMESTAMP);
            else
                ps.setTimestamp(6, new java.sql.Timestamp(prof_memberships.getMemberSince().getTime()));
            // Added member until BK 3/5/12
            if (prof_memberships.getMemberUntil() == null)
                ps.setNull(7, Types.TIMESTAMP);
            else
                ps.setTimestamp(7, new java.sql.Timestamp(prof_memberships.getMemberUntil().getTime()));
            ps.setString(8, prof_memberships.getPresentMember()); // BK 3/5/12
            ps.setString(9, prof_memberships.getComments());
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
     * @param prof_memberships the instance to delete. @ if there is a problem
     *            deleting.
     */
    protected void delete(ProfMemberships prof_memberships) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, prof_memberships.getPersonId());
            ps.setBigDecimal(2, prof_memberships.getEntryId());
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
    // Added MEMBER_UNTIL and PRESENT_MEMBER below. BK 3/5/12
    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",DESCRIPTION" + ",ROLE" + ",MEMBER_SINCE" + ",MEMBER_UNTIL" + ",PRESENT_MEMBER" + ",COMMENTS" + " FROM PROF_MEMBERSHIPS WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",DESCRIPTION" + ",ROLE" + ",MEMBER_SINCE" + ",MEMBER_UNTIL" + ",PRESENT_MEMBER" + ",COMMENTS" + " FROM PROF_MEMBERSHIPS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM PROF_MEMBERSHIPS WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the prof_memberships table. */
    private final String INSERT_QUERY = "INSERT INTO PROF_MEMBERSHIPS" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",DESCRIPTION" + ",ROLE" + ",MEMBER_SINCE" + ",MEMBER_UNTIL" + ",PRESENT_MEMBER" + ",COMMENTS" + ") VALUES (" + "?" + "," + "?" + "," + sysdate
            + "," + sysdate + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the prof_memberships table. */
    private final String UPDATE_QUERY = "UPDATE PROF_MEMBERSHIPS SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?"
            + ",DESCRIPTION = ?" + ",ROLE = ?" + ",MEMBER_SINCE = ?" + ",MEMBER_UNTIL = ?" + ",PRESENT_MEMBER = ?" + ",COMMENTS = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM PROF_MEMBERSHIPS " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

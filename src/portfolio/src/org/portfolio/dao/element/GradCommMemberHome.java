/* $Name:  $ */
/* $Id: GradCommMemberHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/GradCommMemberHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.8 $
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
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.GradCommMember;

/**
 * Handles the persistence of the GradCommMember data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class GradCommMemberHome extends AbstractElementHome {
    /**
     * Persists the GradCommMember Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the GradCommMember object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof GradCommMember)) {
        } else {
            GradCommMember grad_comm_member = (GradCommMember) data;
            logService.debug("Is instance new?" + grad_comm_member.isNew());
            if (grad_comm_member.isNew()) {
                logService.debug("inserting new instance");
                insert(grad_comm_member);
            } else {
                logService.debug("Updating instance.");
                update(grad_comm_member);
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
        if (!(data instanceof GradCommMember)) {
        } else {
            GradCommMember grad_comm_member = (GradCommMember) data;
            delete(grad_comm_member);
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
    public List<GradCommMember> findByPersonId(String personId) {
        List<GradCommMember> elements = new ArrayList<GradCommMember>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                GradCommMember grad_comm_member = new GradCommMember();
                grad_comm_member.setPersonId(rs.getString("PERSON_ID"));
                grad_comm_member.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                grad_comm_member.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                grad_comm_member.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                grad_comm_member.setMasters(rs.getString("MASTERS"));
                grad_comm_member.setDocPrelimWritten(rs.getString("DOC_PRELIM_WRITTEN"));
                grad_comm_member.setDocPrelimOral(rs.getString("DOC_PRELIM_ORAL"));
                grad_comm_member.setDocFinalOral(rs.getString("DOC_FINAL_ORAL"));
                grad_comm_member.setFirstName(rs.getString("FIRST_NAME"));
                grad_comm_member.setEntryName(rs.getString("ENTRY_NAME"));
                grad_comm_member.setAdviser(rs.getString("ADVISER"));
                grad_comm_member.setMajorFieldChair(rs.getString("MAJOR_FIELD_CHAIR"));
                grad_comm_member.setMajorFieldMember(rs.getString("MAJOR_FIELD_MEMBER"));
                grad_comm_member.setMajorFieldReviewer(rs.getString("MAJOR_FIELD_REVIEWER"));
                grad_comm_member.setSupportingFieldChair(rs.getString("SUPPORTING_FIELD_CHAIR"));
                grad_comm_member.setSupportingFieldMember(rs.getString("SUPPORTING_FIELD_MEMBER"));
                grad_comm_member.setSupportingFieldReviewer(rs.getString("SUPPORTING_FIELD_REVIEWER"));
                grad_comm_member.setCampusAddress(rs.getString("CAMPUS_ADDRESS"));
                grad_comm_member.setPhone(rs.getString("PHONE"));
                grad_comm_member.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
                // logService.debug("Adding instance " + grad_comm_member + " to the elements list.");
                elements.add(grad_comm_member);
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
        GradCommMember grad_comm_member = new GradCommMember();
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
                grad_comm_member.setPersonId(rs.getString("PERSON_ID"));
                grad_comm_member.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                grad_comm_member.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                grad_comm_member.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                grad_comm_member.setMasters(rs.getString("MASTERS"));
                grad_comm_member.setDocPrelimWritten(rs.getString("DOC_PRELIM_WRITTEN"));
                grad_comm_member.setDocPrelimOral(rs.getString("DOC_PRELIM_ORAL"));
                grad_comm_member.setDocFinalOral(rs.getString("DOC_FINAL_ORAL"));
                grad_comm_member.setFirstName(rs.getString("FIRST_NAME"));
                grad_comm_member.setEntryName(rs.getString("ENTRY_NAME"));
                grad_comm_member.setAdviser(rs.getString("ADVISER"));
                grad_comm_member.setMajorFieldChair(rs.getString("MAJOR_FIELD_CHAIR"));
                grad_comm_member.setMajorFieldMember(rs.getString("MAJOR_FIELD_MEMBER"));
                grad_comm_member.setMajorFieldReviewer(rs.getString("MAJOR_FIELD_REVIEWER"));
                grad_comm_member.setSupportingFieldChair(rs.getString("SUPPORTING_FIELD_CHAIR"));
                grad_comm_member.setSupportingFieldMember(rs.getString("SUPPORTING_FIELD_MEMBER"));
                grad_comm_member.setSupportingFieldReviewer(rs.getString("SUPPORTING_FIELD_REVIEWER"));
                grad_comm_member.setCampusAddress(rs.getString("CAMPUS_ADDRESS"));
                grad_comm_member.setPhone(rs.getString("PHONE"));
                grad_comm_member.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return grad_comm_member;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type GradCommMember.
     * 
     * @param grad_comm_member the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(GradCommMember grad_comm_member) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, grad_comm_member.getMasters());
            ps.setString(2, grad_comm_member.getDocPrelimWritten());
            ps.setString(3, grad_comm_member.getDocPrelimOral());
            ps.setString(4, grad_comm_member.getDocFinalOral());
            ps.setString(5, grad_comm_member.getFirstName());
            ps.setString(6, grad_comm_member.getEntryName());
            ps.setString(7, grad_comm_member.getAdviser());
            ps.setString(8, grad_comm_member.getMajorFieldChair());
            ps.setString(9, grad_comm_member.getMajorFieldMember());
            ps.setString(10, grad_comm_member.getMajorFieldReviewer());
            ps.setString(11, grad_comm_member.getSupportingFieldChair());
            ps.setString(12, grad_comm_member.getSupportingFieldMember());
            ps.setString(13, grad_comm_member.getSupportingFieldReviewer());
            ps.setString(14, grad_comm_member.getCampusAddress());
            ps.setString(15, grad_comm_member.getPhone());
            ps.setString(16, grad_comm_member.getEmailAddress());
            ps.setString(17, grad_comm_member.getPersonId());
            ps.setBigDecimal(18, grad_comm_member.getEntryId());
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
     * guarantee that the data object is of type GradCommMember.
     * 
     * @param grad_comm_member the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(GradCommMember grad_comm_member) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (grad_comm_member.isRemote()) ? grad_comm_member.getEntryId().intValue() : sequenceGenerator
                .getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, grad_comm_member.getPersonId());
            ps.setInt(2, id);
            grad_comm_member.setEntryId(String.valueOf(id));
            ps.setString(3, grad_comm_member.getMasters());
            ps.setString(4, grad_comm_member.getDocPrelimWritten());
            ps.setString(5, grad_comm_member.getDocPrelimOral());
            ps.setString(6, grad_comm_member.getDocFinalOral());
            ps.setString(7, grad_comm_member.getFirstName());
            ps.setString(8, grad_comm_member.getEntryName());
            ps.setString(9, grad_comm_member.getAdviser());
            ps.setString(10, grad_comm_member.getMajorFieldChair());
            ps.setString(11, grad_comm_member.getMajorFieldMember());
            ps.setString(12, grad_comm_member.getMajorFieldReviewer());
            ps.setString(13, grad_comm_member.getSupportingFieldChair());
            ps.setString(14, grad_comm_member.getSupportingFieldMember());
            ps.setString(15, grad_comm_member.getSupportingFieldReviewer());
            ps.setString(16, grad_comm_member.getCampusAddress());
            ps.setString(17, grad_comm_member.getPhone());
            ps.setString(18, grad_comm_member.getEmailAddress());
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
    protected void delete(GradCommMember grad_comm_member) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, grad_comm_member.getPersonId());
            ps.setBigDecimal(2, grad_comm_member.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    // --------------------------------------------------------------------------
    // Instance variables here.

    // --------------------------------------------------------------------------
    // Class Constants
    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",MASTERS"
            + ",DOC_PRELIM_WRITTEN" + ",DOC_PRELIM_ORAL" + ",DOC_FINAL_ORAL" + ",FIRST_NAME" + ",ENTRY_NAME" + ",ADVISER"
            + ",MAJOR_FIELD_CHAIR" + ",MAJOR_FIELD_MEMBER" + ",MAJOR_FIELD_REVIEWER" + ",SUPPORTING_FIELD_CHAIR"
            + ",SUPPORTING_FIELD_MEMBER" + ",SUPPORTING_FIELD_REVIEWER" + ",CAMPUS_ADDRESS" + ",PHONE" + ",EMAIL_ADDRESS"
            + " FROM GRAD_COMM_MEMBER WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",MASTERS"
            + ",DOC_PRELIM_WRITTEN" + ",DOC_PRELIM_ORAL" + ",DOC_FINAL_ORAL" + ",FIRST_NAME" + ",ENTRY_NAME" + ",ADVISER"
            + ",MAJOR_FIELD_CHAIR" + ",MAJOR_FIELD_MEMBER" + ",MAJOR_FIELD_REVIEWER" + ",SUPPORTING_FIELD_CHAIR"
            + ",SUPPORTING_FIELD_MEMBER" + ",SUPPORTING_FIELD_REVIEWER" + ",CAMPUS_ADDRESS" + ",PHONE" + ",EMAIL_ADDRESS"
            + " FROM GRAD_COMM_MEMBER WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM GRAD_COMM_MEMBER WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the grad_comm_member table. */
    private final String INSERT_QUERY = "INSERT INTO GRAD_COMM_MEMBER" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",MASTERS" + ",DOC_PRELIM_WRITTEN" + ",DOC_PRELIM_ORAL" + ",DOC_FINAL_ORAL" + ",FIRST_NAME" + ",ENTRY_NAME" + ",ADVISER"
            + ",MAJOR_FIELD_CHAIR" + ",MAJOR_FIELD_MEMBER" + ",MAJOR_FIELD_REVIEWER" + ",SUPPORTING_FIELD_CHAIR"
            + ",SUPPORTING_FIELD_MEMBER" + ",SUPPORTING_FIELD_REVIEWER" + ",CAMPUS_ADDRESS" + ",PHONE" + ",EMAIL_ADDRESS" + ") VALUES ("
            + "?" + "," + "?" + ","
            + sysdate
            + ","
            + sysdate
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + ","
            + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the grad_comm_member table. */
    private final String UPDATE_QUERY = "UPDATE GRAD_COMM_MEMBER SET " + "MODIFIED_DATE = " + sysdate + ",MASTERS = ?"
            + ",DOC_PRELIM_WRITTEN = ?" + ",DOC_PRELIM_ORAL = ?" + ",DOC_FINAL_ORAL = ?" + ",FIRST_NAME = ?" + ",ENTRY_NAME = ?"
            + ",ADVISER = ?" + ",MAJOR_FIELD_CHAIR = ?" + ",MAJOR_FIELD_MEMBER = ?" + ",MAJOR_FIELD_REVIEWER = ?"
            + ",SUPPORTING_FIELD_CHAIR = ?" + ",SUPPORTING_FIELD_MEMBER = ?" + ",SUPPORTING_FIELD_REVIEWER = ?" + ",CAMPUS_ADDRESS = ?"
            + ",PHONE = ?" + ",EMAIL_ADDRESS = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM GRAD_COMM_MEMBER " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

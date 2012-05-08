/* $Name:  $ */
/* $Id: GradThesisHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/GradThesisHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.GradThesis;

/**
 * Handles the persistence of the GradThesis data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class GradThesisHome extends AbstractElementHome {
    /**
     * Persists the GradThesis Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the GradThesis object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof GradThesis)) {
        } else {
            GradThesis grad_thesis = (GradThesis) data;
            logService.debug("Is instance new?" + grad_thesis.isNew());
            if (grad_thesis.isNew()) {
                logService.debug("inserting new instance");
                insert(grad_thesis);
            } else {
                logService.debug("Updating instance.");
                update(grad_thesis);
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
        if (!(data instanceof GradThesis)) {
        } else {
            GradThesis grad_thesis = (GradThesis) data;
            delete(grad_thesis);
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
    public List<GradThesis> findByPersonId(String personId) {
        List<GradThesis> elements = new ArrayList<GradThesis>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                GradThesis grad_thesis = new GradThesis();
                grad_thesis.setPersonId(rs.getString("PERSON_ID"));
                grad_thesis.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                grad_thesis.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                grad_thesis.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                grad_thesis.setEntryName(rs.getString("ENTRY_NAME"));
                grad_thesis.setUniversity(rs.getString("UNIVERSITY"));
                grad_thesis.setDefenseDate(rs.getTimestamp("DEFENSE_DATE"));
                grad_thesis.setIntroduction(rs.getString("INTRODUCTION"));
                grad_thesis.setCommentary(rs.getString("COMMENTARY"));
                // logService.debug("Adding instance " + grad_thesis + " to the elements list.");
                elements.add(grad_thesis);
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
        GradThesis grad_thesis = new GradThesis();
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
                grad_thesis.setPersonId(rs.getString("PERSON_ID"));
                grad_thesis.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                grad_thesis.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                grad_thesis.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                grad_thesis.setEntryName(rs.getString("ENTRY_NAME"));
                grad_thesis.setUniversity(rs.getString("UNIVERSITY"));
                grad_thesis.setDefenseDate(rs.getTimestamp("DEFENSE_DATE"));
                grad_thesis.setIntroduction(rs.getString("INTRODUCTION"));
                grad_thesis.setCommentary(rs.getString("COMMENTARY"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return grad_thesis;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type GradThesis.
     * 
     * @param grad_thesis the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(GradThesis grad_thesis) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, grad_thesis.getEntryName());
            ps.setString(2, grad_thesis.getUniversity());
            if (grad_thesis.getDefenseDate() == null)
                ps.setNull(3, Types.TIMESTAMP);
            else
                ps.setTimestamp(3, new java.sql.Timestamp(grad_thesis.getDefenseDate().getTime()));
            ps.setString(4, grad_thesis.getIntroduction());
            ps.setString(5, grad_thesis.getCommentary());
            ps.setString(6, grad_thesis.getPersonId());
            ps.setBigDecimal(7, grad_thesis.getEntryId());
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
     * guarantee that the data object is of type GradThesis.
     * 
     * @param grad_thesis the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(GradThesis grad_thesis) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (grad_thesis.isRemote()) ? grad_thesis.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, grad_thesis.getPersonId());
            ps.setInt(2, id);
            grad_thesis.setEntryId(String.valueOf(id));
            ps.setString(3, grad_thesis.getEntryName());
            ps.setString(4, grad_thesis.getUniversity());
            if (grad_thesis.getDefenseDate() == null)
                ps.setNull(5, Types.TIMESTAMP);
            else
                ps.setTimestamp(5, new java.sql.Timestamp(grad_thesis.getDefenseDate().getTime()));
            ps.setString(6, grad_thesis.getIntroduction());
            ps.setString(7, grad_thesis.getCommentary());
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
    protected void delete(GradThesis grad_thesis) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, grad_thesis.getPersonId());
            ps.setBigDecimal(2, grad_thesis.getEntryId());
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
    private final String SELECT_QUERY_ALL = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",UNIVERSITY" + ",DEFENSE_DATE" + ",INTRODUCTION" + ",COMMENTARY" + " FROM GRAD_THESIS WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",UNIVERSITY" + ",DEFENSE_DATE" + ",INTRODUCTION" + ",COMMENTARY" + " FROM GRAD_THESIS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM GRAD_THESIS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the grad_thesis table. */
    private final String INSERT_QUERY = "INSERT INTO GRAD_THESIS" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",UNIVERSITY" + ",DEFENSE_DATE" + ",INTRODUCTION" + ",COMMENTARY" + ") VALUES (" + "?" + "," + "?" + ","
            + sysdate + "," + sysdate + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the grad_thesis table. */
    private final String UPDATE_QUERY = "UPDATE GRAD_THESIS SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",UNIVERSITY = ?"
            + ",DEFENSE_DATE = ?" + ",INTRODUCTION = ?" + ",COMMENTARY = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM GRAD_THESIS " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

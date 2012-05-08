/* $Name:  $ */
/* $Id: WorkHistoryHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/WorkHistoryHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.WorkHistory;

/**
 * Handles the persistence of the WorkHistory data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class WorkHistoryHome extends AbstractElementHome {

    /**
     * Persists the WorkHistory Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the WorkHistory object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof WorkHistory)) {
        } else {
            WorkHistory work_history = (WorkHistory) data;
            logService.debug("Is instance new?" + work_history.isNew());
            if (work_history.isNew()) {
                logService.debug("inserting new instance");
                insert(work_history);
            } else {
                logService.debug("Updating instance.");
                update(work_history);
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
        if (!(data instanceof WorkHistory)) {
        } else {
            WorkHistory work_history = (WorkHistory) data;
            delete(work_history);
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
    public List<WorkHistory> findByPersonId(String personId) {
        List<WorkHistory> elements = new ArrayList<WorkHistory>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                WorkHistory work_history = new WorkHistory();
                work_history.setPersonId(rs.getString("PERSON_ID"));
                work_history.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                work_history.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                work_history.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                work_history.setEntryName(rs.getString("ENTRY_NAME"));
                work_history.setInstitution(rs.getString("INSTITUTION"));
                work_history.setSupervisor(rs.getString("SUPERVISOR"));
                work_history.setAddress1(rs.getString("ADDRESS1"));
                work_history.setAddress2(rs.getString("ADDRESS2"));
                work_history.setCity(rs.getString("CITY"));
                work_history.setState(rs.getString("STATE"));
                work_history.setZip(rs.getString("ZIP"));
                work_history.setCountry(rs.getString("COUNTRY"));
                work_history.setTelephone(rs.getString("TELEPHONE"));
                work_history.setFax(rs.getString("FAX"));
                work_history.setStartDate(rs.getTimestamp("START_DATE"));
                work_history.setEndDate(rs.getTimestamp("END_DATE"));
                work_history.setPresentlyEmployed(rs.getString("PRESENTLY_EMPLOYED"));
                work_history.setDescription(rs.getString("DESCRIPTION"));
                work_history.setAccomplishments(rs.getString("ACCOMPLISHMENTS"));
                // logService.debug("Adding instance " + work_history + " to the elements list.");
                elements.add(work_history);
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
        WorkHistory work_history = new WorkHistory();
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
                work_history.setPersonId(rs.getString("PERSON_ID"));
                work_history.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                work_history.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                work_history.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                work_history.setEntryName(rs.getString("ENTRY_NAME"));
                work_history.setInstitution(rs.getString("INSTITUTION"));
                work_history.setSupervisor(rs.getString("SUPERVISOR"));
                work_history.setAddress1(rs.getString("ADDRESS1"));
                work_history.setAddress2(rs.getString("ADDRESS2"));
                work_history.setCity(rs.getString("CITY"));
                work_history.setState(rs.getString("STATE"));
                work_history.setZip(rs.getString("ZIP"));
                work_history.setCountry(rs.getString("COUNTRY"));
                work_history.setTelephone(rs.getString("TELEPHONE"));
                work_history.setFax(rs.getString("FAX"));
                work_history.setStartDate(rs.getTimestamp("START_DATE"));
                work_history.setEndDate(rs.getTimestamp("END_DATE"));
                work_history.setPresentlyEmployed(rs.getString("PRESENTLY_EMPLOYED"));
                work_history.setDescription(rs.getString("DESCRIPTION"));
                work_history.setAccomplishments(rs.getString("ACCOMPLISHMENTS"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return work_history;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type WorkHistory.
     * 
     * @param work_history the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(WorkHistory work_history) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, work_history.getEntryName());
            ps.setString(2, work_history.getInstitution());
            ps.setString(3, work_history.getSupervisor());
            ps.setString(4, work_history.getAddress1());
            ps.setString(5, work_history.getAddress2());
            ps.setString(6, work_history.getCity());
            ps.setString(7, work_history.getState());
            ps.setString(8, work_history.getZip());
            ps.setString(9, work_history.getCountry());
            ps.setString(10, work_history.getTelephone());
            ps.setString(11, work_history.getFax());
            if (work_history.getStartDate() == null)
                ps.setNull(12, Types.TIMESTAMP);
            else
                ps.setTimestamp(12, new java.sql.Timestamp(work_history.getStartDate().getTime()));
            if (work_history.getEndDate() == null)
                ps.setNull(13, Types.TIMESTAMP);
            else
                ps.setTimestamp(13, new java.sql.Timestamp(work_history.getEndDate().getTime()));
            ps.setString(14, work_history.getPresentlyEmployed());
            ps.setString(15, work_history.getDescription());
            ps.setString(16, work_history.getAccomplishments());
            ps.setString(17, work_history.getPersonId());
            ps.setBigDecimal(18, work_history.getEntryId());
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
     * guarantee that the data object is of type WorkHistory.
     * 
     * @param work_history the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(WorkHistory work_history) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, work_history.getPersonId());
            ps.setInt(2, id);
            work_history.setEntryId(String.valueOf(id));
            ps.setString(3, work_history.getEntryName());
            ps.setString(4, work_history.getInstitution());
            ps.setString(5, work_history.getSupervisor());
            ps.setString(6, work_history.getAddress1());
            ps.setString(7, work_history.getAddress2());
            ps.setString(8, work_history.getCity());
            ps.setString(9, work_history.getState());
            ps.setString(10, work_history.getZip());
            ps.setString(11, work_history.getCountry());
            ps.setString(12, work_history.getTelephone());
            ps.setString(13, work_history.getFax());
            if (work_history.getStartDate() == null)
                ps.setNull(14, Types.TIMESTAMP);
            else
                ps.setTimestamp(14, new java.sql.Timestamp(work_history.getStartDate().getTime()));
            if (work_history.getEndDate() == null)
                ps.setNull(15, Types.TIMESTAMP);
            else
                ps.setTimestamp(15, new java.sql.Timestamp(work_history.getEndDate().getTime()));
            ps.setString(16, work_history.getPresentlyEmployed());
            ps.setString(17, work_history.getDescription());
            ps.setString(18, work_history.getAccomplishments());
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
    protected void delete(WorkHistory work_history) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, work_history.getPersonId());
            ps.setBigDecimal(2, work_history.getEntryId());
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
            + ",INSTITUTION" + ",SUPERVISOR" + ",ADDRESS1" + ",ADDRESS2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",TELEPHONE" + ",FAX"
            + ",START_DATE" + ",END_DATE" + ",PRESENTLY_EMPLOYED" + ",DESCRIPTION" + ",ACCOMPLISHMENTS"
            + " FROM WORK_HISTORY WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",INSTITUTION" + ",SUPERVISOR" + ",ADDRESS1" + ",ADDRESS2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",TELEPHONE" + ",FAX"
            + ",START_DATE" + ",END_DATE" + ",PRESENTLY_EMPLOYED" + ",DESCRIPTION" + ",ACCOMPLISHMENTS"
            + " FROM WORK_HISTORY WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM WORK_HISTORY WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to INSERT a new row into the work_history table. */
    private final String INSERT_QUERY = "INSERT INTO WORK_HISTORY" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",INSTITUTION" + ",SUPERVISOR" + ",ADDRESS1" + ",ADDRESS2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY"
            + ",TELEPHONE" + ",FAX" + ",START_DATE" + ",END_DATE" + ",PRESENTLY_EMPLOYED" + ",DESCRIPTION" + ",ACCOMPLISHMENTS"
            + ") VALUES (" + "?" + "," + "?" + ","
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

    /** The query to INSERT a new row into the work_history table. */
    private final String UPDATE_QUERY = "UPDATE WORK_HISTORY SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",INSTITUTION = ?"
            + ",SUPERVISOR = ?" + ",ADDRESS1 = ?" + ",ADDRESS2 = ?" + ",CITY = ?" + ",STATE = ?" + ",ZIP = ?" + ",COUNTRY = ?"
            + ",TELEPHONE = ?" + ",FAX = ?" + ",START_DATE = ?" + ",END_DATE = ?" + ",PRESENTLY_EMPLOYED = ?" + ",DESCRIPTION = ?"
            + ",ACCOMPLISHMENTS = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM WORK_HISTORY " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
	
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

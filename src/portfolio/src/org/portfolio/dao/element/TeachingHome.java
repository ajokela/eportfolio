/* $Name:  $ */
/* $Id: TeachingHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/TeachingHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.Teaching;

/**
 * Handles the persistence of the Teaching data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class TeachingHome extends AbstractElementHome {
    /**
     * Persists the Teaching Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the Teaching object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof Teaching)) {
        } else {
            Teaching teaching = (Teaching) data;
            logService.debug("Is instance new?" + teaching.isNew());
            if (teaching.isNew()) {
                logService.debug("inserting new instance");
                insert(teaching);
            } else {
                logService.debug("Updating instance.");
                update(teaching);
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
        if (!(data instanceof Teaching)) {
        } else {
            Teaching teaching = (Teaching) data;
            delete(teaching);
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
    public List<Teaching> findByPersonId(String personId) {
        List<Teaching> elements = new ArrayList<Teaching>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Teaching teaching = new Teaching();
                teaching.setPersonId(rs.getString("PERSON_ID"));
                teaching.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                teaching.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                teaching.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                teaching.setEntryName(rs.getString("ENTRY_NAME"));
                teaching.setCourses(rs.getString("COURSES"));
                teaching.setOrganization(rs.getString("ORGANIZATION"));
                teaching.setStreet1(rs.getString("STREET1"));
                teaching.setStreet2(rs.getString("STREET2"));
                teaching.setCity(rs.getString("CITY"));
                teaching.setState(rs.getString("STATE"));
                teaching.setZip(rs.getString("ZIP"));
                teaching.setCountry(rs.getString("COUNTRY"));
                teaching.setFromDate(rs.getTimestamp("FROM_DATE"));
                teaching.setToDate(rs.getTimestamp("TO_DATE"));
                teaching.setComments(rs.getString("COMMENTS"));
                // logService.debug("Adding instance " + teaching + " to the elements list.");
                elements.add(teaching);
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
        Teaching teaching = new Teaching();
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
                teaching.setPersonId(rs.getString("PERSON_ID"));
                teaching.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                teaching.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                teaching.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                teaching.setEntryName(rs.getString("ENTRY_NAME"));
                teaching.setCourses(rs.getString("COURSES"));
                teaching.setOrganization(rs.getString("ORGANIZATION"));
                teaching.setStreet1(rs.getString("STREET1"));
                teaching.setStreet2(rs.getString("STREET2"));
                teaching.setCity(rs.getString("CITY"));
                teaching.setState(rs.getString("STATE"));
                teaching.setZip(rs.getString("ZIP"));
                teaching.setCountry(rs.getString("COUNTRY"));
                teaching.setFromDate(rs.getTimestamp("FROM_DATE"));
                teaching.setToDate(rs.getTimestamp("TO_DATE"));
                teaching.setComments(rs.getString("COMMENTS"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return teaching;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Teaching.
     * 
     * @param teaching the instance to update. @ if there is a problem updating.
     */
    protected void update(Teaching teaching) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, teaching.getEntryName());
            ps.setString(2, teaching.getCourses());
            ps.setString(3, teaching.getOrganization());
            ps.setString(4, teaching.getStreet1());
            ps.setString(5, teaching.getStreet2());
            ps.setString(6, teaching.getCity());
            ps.setString(7, teaching.getState());
            ps.setString(8, teaching.getZip());
            ps.setString(9, teaching.getCountry());
            if (teaching.getFromDate() == null)
                ps.setNull(10, Types.TIMESTAMP);
            else
                ps.setTimestamp(10, new java.sql.Timestamp(teaching.getFromDate().getTime()));
            if (teaching.getToDate() == null)
                ps.setNull(11, Types.TIMESTAMP);
            else
                ps.setTimestamp(11, new java.sql.Timestamp(teaching.getToDate().getTime()));
            ps.setString(12, teaching.getComments());
            ps.setString(13, teaching.getPersonId());
            ps.setBigDecimal(14, teaching.getEntryId());
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
     * guarantee that the data object is of type Teaching.
     * 
     * @param teaching the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(Teaching teaching) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, teaching.getPersonId());
            ps.setInt(2, id);
            teaching.setEntryId(String.valueOf(id));
            ps.setString(3, teaching.getEntryName());
            ps.setString(4, teaching.getCourses());
            ps.setString(5, teaching.getOrganization());
            ps.setString(6, teaching.getStreet1());
            ps.setString(7, teaching.getStreet2());
            ps.setString(8, teaching.getCity());
            ps.setString(9, teaching.getState());
            ps.setString(10, teaching.getZip());
            ps.setString(11, teaching.getCountry());
            if (teaching.getFromDate() == null)
                ps.setNull(12, Types.TIMESTAMP);
            else
                ps.setTimestamp(12, new java.sql.Timestamp(teaching.getFromDate().getTime()));
            if (teaching.getToDate() == null)
                ps.setNull(13, Types.TIMESTAMP);
            else
                ps.setTimestamp(13, new java.sql.Timestamp(teaching.getToDate().getTime()));
            ps.setString(14, teaching.getComments());
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
    protected void delete(Teaching teaching) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, teaching.getPersonId());
            ps.setBigDecimal(2, teaching.getEntryId());
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
            + ",COURSES" + ",ORGANIZATION" + ",STREET1" + ",STREET2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",FROM_DATE" + ",TO_DATE"
            + ",COMMENTS" + " FROM TEACHING WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",COURSES" + ",ORGANIZATION" + ",STREET1" + ",STREET2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",FROM_DATE" + ",TO_DATE"
            + ",COMMENTS" + " FROM TEACHING WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM TEACHING WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the teaching table. */
    private final String INSERT_QUERY = "INSERT INTO TEACHING" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",COURSES" + ",ORGANIZATION" + ",STREET1" + ",STREET2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY"
            + ",FROM_DATE" + ",TO_DATE" + ",COMMENTS" + ") VALUES (" + "?" + "," + "?" + ","
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
            + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the teaching table. */
    private final String UPDATE_QUERY = "UPDATE TEACHING SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",COURSES = ?"
            + ",ORGANIZATION = ?" + ",STREET1 = ?" + ",STREET2 = ?" + ",CITY = ?" + ",STATE = ?" + ",ZIP = ?" + ",COUNTRY = ?"
            + ",FROM_DATE = ?" + ",TO_DATE = ?" + ",COMMENTS = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM TEACHING " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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

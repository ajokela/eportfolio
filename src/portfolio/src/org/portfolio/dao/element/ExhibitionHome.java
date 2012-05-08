/* $Name:  $ */
/* $Id: ExhibitionHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/ExhibitionHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.Exhibition;

/**
 * Handles the persistence of the Exhibition data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class ExhibitionHome extends AbstractElementHome {
    /**
     * Persists the Exhibition Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the Exhibition object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof Exhibition)) {
        } else {
            Exhibition exhibition = (Exhibition) data;
            logService.debug("Is instance new?" + exhibition.isNew());
            if (exhibition.isNew()) {
                logService.debug("inserting new instance");
                insert(exhibition);
            } else {
                logService.debug("Updating instance.");
                update(exhibition);
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
        if (!(data instanceof Exhibition)) {
        } else {
            Exhibition exhibition = (Exhibition) data;
            delete(exhibition);
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
    public List<Exhibition> findByPersonId(String personId) {
        List<Exhibition> elements = new ArrayList<Exhibition>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Exhibition exhibition = new Exhibition();
                exhibition.setPersonId(rs.getString("PERSON_ID"));
                exhibition.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                exhibition.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                exhibition.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                exhibition.setEntryName(rs.getString("ENTRY_NAME"));
                exhibition.setType(rs.getString("TYPE"));
                exhibition.setFromDate(rs.getTimestamp("FROM_DATE"));
                exhibition.setToDate(rs.getTimestamp("TO_DATE"));
                exhibition.setLocation(rs.getString("LOCATION"));
                exhibition.setJuried(rs.getString("JURIED"));
                exhibition.setDescription(rs.getString("DESCRIPTION"));
                // logService.debug("Adding instance " + exhibition + " to the elements list.");
                elements.add(exhibition);
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
        Exhibition exhibition = new Exhibition();
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
                exhibition.setPersonId(rs.getString("PERSON_ID"));
                exhibition.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                exhibition.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                exhibition.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                exhibition.setEntryName(rs.getString("ENTRY_NAME"));
                exhibition.setType(rs.getString("TYPE"));
                exhibition.setFromDate(rs.getTimestamp("FROM_DATE"));
                exhibition.setToDate(rs.getTimestamp("TO_DATE"));
                exhibition.setLocation(rs.getString("LOCATION"));
                exhibition.setJuried(rs.getString("JURIED"));
                exhibition.setDescription(rs.getString("DESCRIPTION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
        } finally {
            close(conn, ps, rs);
        }

        return exhibition;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Exhibition.
     * 
     * @param exhibition the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(Exhibition exhibition) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, exhibition.getEntryName());
            ps.setString(2, exhibition.getType());
            if (exhibition.getFromDate() == null)
                ps.setNull(3, Types.TIMESTAMP);
            else
                ps.setTimestamp(3, new java.sql.Timestamp(exhibition.getFromDate().getTime()));
            if (exhibition.getToDate() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(exhibition.getToDate().getTime()));
            ps.setString(5, exhibition.getLocation());
            ps.setString(6, exhibition.getJuried());
            ps.setString(7, exhibition.getDescription());
            ps.setString(8, exhibition.getPersonId());
            ps.setBigDecimal(9, exhibition.getEntryId());
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
     * guarantee that the data object is of type Exhibition.
     * 
     * @param exhibition the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(Exhibition exhibition) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (exhibition.isRemote()) ? exhibition.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, exhibition.getPersonId());
            ps.setInt(2, id);
            exhibition.setEntryId(String.valueOf(id));
            ps.setString(3, exhibition.getEntryName());
            ps.setString(4, exhibition.getType());
            if (exhibition.getFromDate() == null)
                ps.setNull(5, Types.TIMESTAMP);
            else
                ps.setTimestamp(5, new java.sql.Timestamp(exhibition.getFromDate().getTime()));
            if (exhibition.getToDate() == null)
                ps.setNull(6, Types.TIMESTAMP);
            else
                ps.setTimestamp(6, new java.sql.Timestamp(exhibition.getToDate().getTime()));
            ps.setString(7, exhibition.getLocation());
            ps.setString(8, exhibition.getJuried());
            ps.setString(9, exhibition.getDescription());
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
    protected void delete(Exhibition exhibition) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, exhibition.getPersonId());
            ps.setBigDecimal(2, exhibition.getEntryId());
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
    private final String SELECT_QUERY_ALL = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",TYPE" + ",FROM_DATE" + ",TO_DATE" + ",LOCATION" + ",JURIED" + ",DESCRIPTION" + " FROM EXHIBITION WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",TYPE" + ",FROM_DATE" + ",TO_DATE" + ",LOCATION" + ",JURIED" + ",DESCRIPTION"
            + " FROM EXHIBITION WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM EXHIBITION WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the exhibition table. */
    private final String INSERT_QUERY = "INSERT INTO EXHIBITION" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",TYPE" + ",FROM_DATE" + ",TO_DATE" + ",LOCATION" + ",JURIED" + ",DESCRIPTION" + ") VALUES (" + "?" + ","
            + "?" + "," + sysdate + "," + sysdate + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the exhibition table. */
    private final String UPDATE_QUERY = "UPDATE EXHIBITION SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",TYPE = ?"
            + ",FROM_DATE = ?" + ",TO_DATE = ?" + ",LOCATION = ?" + ",JURIED = ?" + ",DESCRIPTION = ?"
            + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM EXHIBITION " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

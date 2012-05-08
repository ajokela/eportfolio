/* $Name:  $ */
/* $Id: StrongIntInventoryHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/StrongIntInventoryHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.StrongIntInventory;

/**
 * Handles the persistence of the StrongIntInventory data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class StrongIntInventoryHome extends AbstractElementHome {
    /**
     * Persists the StrongIntInventory Object. Will perform an INSERT if it is a
     * new instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the StrongIntInventory object to persist @ if a data type
     *            other than the one expected is found. @ if there are any
     *            problems persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof StrongIntInventory)) {
        } else {
            StrongIntInventory strong_int_inventory = (StrongIntInventory) data;
            logService.debug("Is instance new?" + strong_int_inventory.isNew());
            if (strong_int_inventory.isNew()) {
                logService.debug("inserting new instance");
                insert(strong_int_inventory);
            } else {
                logService.debug("Updating instance.");
                update(strong_int_inventory);
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
        if (!(data instanceof StrongIntInventory)) {
        } else {
            StrongIntInventory strong_int_inventory = (StrongIntInventory) data;
            delete(strong_int_inventory);
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
    public List<StrongIntInventory> findByPersonId(String personId) {
        List<StrongIntInventory> elements = new ArrayList<StrongIntInventory>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                StrongIntInventory strong_int_inventory = new StrongIntInventory();
                strong_int_inventory.setPersonId(rs.getString("PERSON_ID"));
                strong_int_inventory.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                strong_int_inventory.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                strong_int_inventory.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                strong_int_inventory.setEntryName(rs.getString("ENTRY_NAME"));
                strong_int_inventory.setInventoryDate(rs.getTimestamp("INVENTORY_DATE"));
                strong_int_inventory.setRealistic(rs.getString("REALISTIC"));
                strong_int_inventory.setInvestigative(rs.getString("INVESTIGATIVE"));
                strong_int_inventory.setArtistic(rs.getString("ARTISTIC"));
                strong_int_inventory.setSocial(rs.getString("SOCIAL"));
                strong_int_inventory.setEnterprising(rs.getString("ENTERPRISING"));
                strong_int_inventory.setConventional(rs.getString("CONVENTIONAL"));
                strong_int_inventory.setInterest1(rs.getString("INTEREST_1"));
                strong_int_inventory.setInterest2(rs.getString("INTEREST_2"));
                strong_int_inventory.setInterest3(rs.getString("INTEREST_3"));
                strong_int_inventory.setInterest4(rs.getString("INTEREST_4"));
                strong_int_inventory.setInterest5(rs.getString("INTEREST_5"));
                strong_int_inventory.setOccupation1(rs.getString("OCCUPATION_1"));
                strong_int_inventory.setOccupation2(rs.getString("OCCUPATION_2"));
                strong_int_inventory.setOccupation3(rs.getString("OCCUPATION_3"));
                strong_int_inventory.setOccupation4(rs.getString("OCCUPATION_4"));
                strong_int_inventory.setOccupation5(rs.getString("OCCUPATION_5"));
                strong_int_inventory.setOccupation6(rs.getString("OCCUPATION_6"));
                strong_int_inventory.setOccupation7(rs.getString("OCCUPATION_7"));
                strong_int_inventory.setOccupation8(rs.getString("OCCUPATION_8"));
                strong_int_inventory.setOccupation9(rs.getString("OCCUPATION_9"));
                strong_int_inventory.setOccupation10(rs.getString("OCCUPATION_10"));
                strong_int_inventory.setInterpretation(rs.getString("INTERPRETATION"));
                // logService.debug("Adding instance " + strong_int_inventory + " to the elements list.");
                elements.add(strong_int_inventory);
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
        StrongIntInventory strong_int_inventory = new StrongIntInventory();
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
                strong_int_inventory.setPersonId(rs.getString("PERSON_ID"));
                strong_int_inventory.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                strong_int_inventory.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                strong_int_inventory.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                strong_int_inventory.setEntryName(rs.getString("ENTRY_NAME"));
                strong_int_inventory.setInventoryDate(rs.getTimestamp("INVENTORY_DATE"));
                strong_int_inventory.setRealistic(rs.getString("REALISTIC"));
                strong_int_inventory.setInvestigative(rs.getString("INVESTIGATIVE"));
                strong_int_inventory.setArtistic(rs.getString("ARTISTIC"));
                strong_int_inventory.setSocial(rs.getString("SOCIAL"));
                strong_int_inventory.setEnterprising(rs.getString("ENTERPRISING"));
                strong_int_inventory.setConventional(rs.getString("CONVENTIONAL"));
                strong_int_inventory.setInterest1(rs.getString("INTEREST_1"));
                strong_int_inventory.setInterest2(rs.getString("INTEREST_2"));
                strong_int_inventory.setInterest3(rs.getString("INTEREST_3"));
                strong_int_inventory.setInterest4(rs.getString("INTEREST_4"));
                strong_int_inventory.setInterest5(rs.getString("INTEREST_5"));
                strong_int_inventory.setOccupation1(rs.getString("OCCUPATION_1"));
                strong_int_inventory.setOccupation2(rs.getString("OCCUPATION_2"));
                strong_int_inventory.setOccupation3(rs.getString("OCCUPATION_3"));
                strong_int_inventory.setOccupation4(rs.getString("OCCUPATION_4"));
                strong_int_inventory.setOccupation5(rs.getString("OCCUPATION_5"));
                strong_int_inventory.setOccupation6(rs.getString("OCCUPATION_6"));
                strong_int_inventory.setOccupation7(rs.getString("OCCUPATION_7"));
                strong_int_inventory.setOccupation8(rs.getString("OCCUPATION_8"));
                strong_int_inventory.setOccupation9(rs.getString("OCCUPATION_9"));
                strong_int_inventory.setOccupation10(rs.getString("OCCUPATION_10"));
                strong_int_inventory.setInterpretation(rs.getString("INTERPRETATION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return strong_int_inventory;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type StrongIntInventory.
     * 
     * @param strong_int_inventory the instance to update. @ if there is a
     *            problem updating.
     */
    protected void update(StrongIntInventory strong_int_inventory) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, strong_int_inventory.getEntryName());
            if (strong_int_inventory.getInventoryDate() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(strong_int_inventory.getInventoryDate().getTime()));
            ps.setString(3, strong_int_inventory.getRealistic());
            ps.setString(4, strong_int_inventory.getInvestigative());
            ps.setString(5, strong_int_inventory.getArtistic());
            ps.setString(6, strong_int_inventory.getSocial());
            ps.setString(7, strong_int_inventory.getEnterprising());
            ps.setString(8, strong_int_inventory.getConventional());
            ps.setString(9, strong_int_inventory.getInterest1());
            ps.setString(10, strong_int_inventory.getInterest2());
            ps.setString(11, strong_int_inventory.getInterest3());
            ps.setString(12, strong_int_inventory.getInterest4());
            ps.setString(13, strong_int_inventory.getInterest5());
            ps.setString(14, strong_int_inventory.getOccupation1());
            ps.setString(15, strong_int_inventory.getOccupation2());
            ps.setString(16, strong_int_inventory.getOccupation3());
            ps.setString(17, strong_int_inventory.getOccupation4());
            ps.setString(18, strong_int_inventory.getOccupation5());
            ps.setString(19, strong_int_inventory.getOccupation6());
            ps.setString(20, strong_int_inventory.getOccupation7());
            ps.setString(21, strong_int_inventory.getOccupation8());
            ps.setString(22, strong_int_inventory.getOccupation9());
            ps.setString(23, strong_int_inventory.getOccupation10());
            ps.setString(24, strong_int_inventory.getInterpretation());
            ps.setString(25, strong_int_inventory.getPersonId());
            ps.setBigDecimal(26, strong_int_inventory.getEntryId());
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
     * guarantee that the data object is of type StrongIntInventory.
     * 
     * @param strong_int_inventory the instance to create. @ if there is a
     *            problem inserting.
     */
    protected void insert(StrongIntInventory strong_int_inventory) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, strong_int_inventory.getPersonId());
            ps.setInt(2, id);
            strong_int_inventory.setEntryId(String.valueOf(id));
            ps.setString(3, strong_int_inventory.getEntryName());
            if (strong_int_inventory.getInventoryDate() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(strong_int_inventory.getInventoryDate().getTime()));
            ps.setString(5, strong_int_inventory.getRealistic());
            ps.setString(6, strong_int_inventory.getInvestigative());
            ps.setString(7, strong_int_inventory.getArtistic());
            ps.setString(8, strong_int_inventory.getSocial());
            ps.setString(9, strong_int_inventory.getEnterprising());
            ps.setString(10, strong_int_inventory.getConventional());
            ps.setString(11, strong_int_inventory.getInterest1());
            ps.setString(12, strong_int_inventory.getInterest2());
            ps.setString(13, strong_int_inventory.getInterest3());
            ps.setString(14, strong_int_inventory.getInterest4());
            ps.setString(15, strong_int_inventory.getInterest5());
            ps.setString(16, strong_int_inventory.getOccupation1());
            ps.setString(17, strong_int_inventory.getOccupation2());
            ps.setString(18, strong_int_inventory.getOccupation3());
            ps.setString(19, strong_int_inventory.getOccupation4());
            ps.setString(20, strong_int_inventory.getOccupation5());
            ps.setString(21, strong_int_inventory.getOccupation6());
            ps.setString(22, strong_int_inventory.getOccupation7());
            ps.setString(23, strong_int_inventory.getOccupation8());
            ps.setString(24, strong_int_inventory.getOccupation9());
            ps.setString(25, strong_int_inventory.getOccupation10());
            ps.setString(26, strong_int_inventory.getInterpretation());
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
    protected void delete(StrongIntInventory strong_int_inventory) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, strong_int_inventory.getPersonId());
            ps.setBigDecimal(2, strong_int_inventory.getEntryId());
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
            + ",INVENTORY_DATE" + ",REALISTIC" + ",INVESTIGATIVE" + ",ARTISTIC" + ",SOCIAL" + ",ENTERPRISING" + ",CONVENTIONAL"
            + ",INTEREST_1" + ",INTEREST_2" + ",INTEREST_3" + ",INTEREST_4" + ",INTEREST_5" + ",OCCUPATION_1" + ",OCCUPATION_2"
            + ",OCCUPATION_3" + ",OCCUPATION_4" + ",OCCUPATION_5" + ",OCCUPATION_6" + ",OCCUPATION_7" + ",OCCUPATION_8" + ",OCCUPATION_9"
            + ",OCCUPATION_10" + ",INTERPRETATION" + " FROM STRONG_INT_INVENTORY WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",INVENTORY_DATE" + ",REALISTIC" + ",INVESTIGATIVE" + ",ARTISTIC" + ",SOCIAL" + ",ENTERPRISING" + ",CONVENTIONAL"
            + ",INTEREST_1" + ",INTEREST_2" + ",INTEREST_3" + ",INTEREST_4" + ",INTEREST_5" + ",OCCUPATION_1" + ",OCCUPATION_2"
            + ",OCCUPATION_3" + ",OCCUPATION_4" + ",OCCUPATION_5" + ",OCCUPATION_6" + ",OCCUPATION_7" + ",OCCUPATION_8" + ",OCCUPATION_9"
            + ",OCCUPATION_10" + ",INTERPRETATION" + " FROM STRONG_INT_INVENTORY WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM STRONG_INT_INVENTORY WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the strong_int_inventory table. */
    private final String INSERT_QUERY = "INSERT INTO STRONG_INT_INVENTORY" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED"
            + ",MODIFIED_DATE" + ",ENTRY_NAME" + ",INVENTORY_DATE" + ",REALISTIC" + ",INVESTIGATIVE" + ",ARTISTIC" + ",SOCIAL"
            + ",ENTERPRISING" + ",CONVENTIONAL" + ",INTEREST_1" + ",INTEREST_2" + ",INTEREST_3" + ",INTEREST_4" + ",INTEREST_5"
            + ",OCCUPATION_1" + ",OCCUPATION_2" + ",OCCUPATION_3" + ",OCCUPATION_4" + ",OCCUPATION_5" + ",OCCUPATION_6" + ",OCCUPATION_7"
            + ",OCCUPATION_8" + ",OCCUPATION_9" + ",OCCUPATION_10" + ",INTERPRETATION" + ") VALUES (" + "?" + "," + "?" + ","
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
            + "?"
            + ","
            + "?"
            + ","
            + "?"
            + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the strong_int_inventory table. */
    private final String UPDATE_QUERY = "UPDATE STRONG_INT_INVENTORY SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?"
            + ",INVENTORY_DATE = ?" + ",REALISTIC = ?" + ",INVESTIGATIVE = ?" + ",ARTISTIC = ?" + ",SOCIAL = ?" + ",ENTERPRISING = ?"
            + ",CONVENTIONAL = ?" + ",INTEREST_1 = ?" + ",INTEREST_2 = ?" + ",INTEREST_3 = ?" + ",INTEREST_4 = ?" + ",INTEREST_5 = ?"
            + ",OCCUPATION_1 = ?" + ",OCCUPATION_2 = ?" + ",OCCUPATION_3 = ?" + ",OCCUPATION_4 = ?" + ",OCCUPATION_5 = ?"
            + ",OCCUPATION_6 = ?" + ",OCCUPATION_7 = ?" + ",OCCUPATION_8 = ?" + ",OCCUPATION_9 = ?" + ",OCCUPATION_10 = ?"
            + ",INTERPRETATION = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM STRONG_INT_INVENTORY " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";


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

/* $Name:  $ */
/* $Id: ExploreInventoryHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/ExploreInventoryHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.ExploreInventory;

/**
 * Handles the persistence of the ExploreInventory data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class ExploreInventoryHome extends AbstractElementHome {
    /**
     * Persists the ExploreInventory Object. Will perform an INSERT if it is a
     * new instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the ExploreInventory object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof ExploreInventory)) {
        } else {
            ExploreInventory explore_inventory = (ExploreInventory) data;
            logService.debug("Is instance new?" + explore_inventory.isNew());
            if (explore_inventory.isNew()) {
                logService.debug("inserting new instance");
                insert(explore_inventory);
            } else {
                logService.debug("Updating instance.");
                update(explore_inventory);
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
        if (!(data instanceof ExploreInventory)) {
        } else {
            ExploreInventory explore_inventory = (ExploreInventory) data;
            delete(explore_inventory);
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
    public List<ExploreInventory> findByPersonId(String personId) {
        List<ExploreInventory> elements = new ArrayList<ExploreInventory>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ExploreInventory explore_inventory = new ExploreInventory();
                explore_inventory.setPersonId(rs.getString("PERSON_ID"));
                explore_inventory.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                explore_inventory.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                explore_inventory.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                explore_inventory.setEntryName(rs.getString("ENTRY_NAME"));
                explore_inventory.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                explore_inventory.setFirstOccupation(rs.getString("FIRST_OCCUPATION"));
                explore_inventory.setSecondOccupation(rs.getString("SECOND_OCCUPATION"));
                explore_inventory.setThirdOccupation(rs.getString("THIRD_OCCUPATION"));
                explore_inventory.setFirstLeisure(rs.getString("FIRST_LEISURE"));
                explore_inventory.setSecondLeisure(rs.getString("SECOND_LEISURE"));
                explore_inventory.setThirdLeisure(rs.getString("THIRD_LEISURE"));
                explore_inventory.setFirstLearning(rs.getString("FIRST_LEARNING"));
                explore_inventory.setSecondLearning(rs.getString("SECOND_LEARNING"));
                explore_inventory.setThirdLearning(rs.getString("THIRD_LEARNING"));
                explore_inventory.setReaction(rs.getString("REACTION"));
                // logService.debug("Adding instance " + explore_inventory + " to the elements list.");
                elements.add(explore_inventory);
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
        ExploreInventory explore_inventory = new ExploreInventory();
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
                explore_inventory.setPersonId(rs.getString("PERSON_ID"));
                explore_inventory.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                explore_inventory.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                explore_inventory.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                explore_inventory.setEntryName(rs.getString("ENTRY_NAME"));
                explore_inventory.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                explore_inventory.setFirstOccupation(rs.getString("FIRST_OCCUPATION"));
                explore_inventory.setSecondOccupation(rs.getString("SECOND_OCCUPATION"));
                explore_inventory.setThirdOccupation(rs.getString("THIRD_OCCUPATION"));
                explore_inventory.setFirstLeisure(rs.getString("FIRST_LEISURE"));
                explore_inventory.setSecondLeisure(rs.getString("SECOND_LEISURE"));
                explore_inventory.setThirdLeisure(rs.getString("THIRD_LEISURE"));
                explore_inventory.setFirstLearning(rs.getString("FIRST_LEARNING"));
                explore_inventory.setSecondLearning(rs.getString("SECOND_LEARNING"));
                explore_inventory.setThirdLearning(rs.getString("THIRD_LEARNING"));
                explore_inventory.setReaction(rs.getString("REACTION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return explore_inventory;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type ExploreInventory.
     * 
     * @param explore_inventory the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(ExploreInventory explore_inventory) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, explore_inventory.getEntryName());
            if (explore_inventory.getDateTaken() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(explore_inventory.getDateTaken().getTime()));
            ps.setString(3, explore_inventory.getFirstOccupation());
            ps.setString(4, explore_inventory.getSecondOccupation());
            ps.setString(5, explore_inventory.getThirdOccupation());
            ps.setString(6, explore_inventory.getFirstLeisure());
            ps.setString(7, explore_inventory.getSecondLeisure());
            ps.setString(8, explore_inventory.getThirdLeisure());
            ps.setString(9, explore_inventory.getFirstLearning());
            ps.setString(10, explore_inventory.getSecondLearning());
            ps.setString(11, explore_inventory.getThirdLearning());
            ps.setString(12, explore_inventory.getReaction());
            ps.setString(13, explore_inventory.getPersonId());
            ps.setBigDecimal(14, explore_inventory.getEntryId());
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
     * guarantee that the data object is of type ExploreInventory.
     * 
     * @param explore_inventory the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(ExploreInventory explore_inventory) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (explore_inventory.isRemote()) ? explore_inventory.getEntryId().intValue() : sequenceGenerator
                .getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, explore_inventory.getPersonId());
            ps.setInt(2, id);
            explore_inventory.setEntryId(String.valueOf(id));
            ps.setString(3, explore_inventory.getEntryName());
            if (explore_inventory.getDateTaken() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(explore_inventory.getDateTaken().getTime()));
            ps.setString(5, explore_inventory.getFirstOccupation());
            ps.setString(6, explore_inventory.getSecondOccupation());
            ps.setString(7, explore_inventory.getThirdOccupation());
            ps.setString(8, explore_inventory.getFirstLeisure());
            ps.setString(9, explore_inventory.getSecondLeisure());
            ps.setString(10, explore_inventory.getThirdLeisure());
            ps.setString(11, explore_inventory.getFirstLearning());
            ps.setString(12, explore_inventory.getSecondLearning());
            ps.setString(13, explore_inventory.getThirdLearning());
            ps.setString(14, explore_inventory.getReaction());
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
    protected void delete(ExploreInventory explore_inventory) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, explore_inventory.getPersonId());
            ps.setBigDecimal(2, explore_inventory.getEntryId());
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
            + ",DATE_TAKEN" + ",FIRST_OCCUPATION" + ",SECOND_OCCUPATION" + ",THIRD_OCCUPATION" + ",FIRST_LEISURE" + ",SECOND_LEISURE"
            + ",THIRD_LEISURE" + ",FIRST_LEARNING" + ",SECOND_LEARNING" + ",THIRD_LEARNING" + ",REACTION"
            + " FROM EXPLORE_INVENTORY WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",DATE_TAKEN" + ",FIRST_OCCUPATION" + ",SECOND_OCCUPATION" + ",THIRD_OCCUPATION" + ",FIRST_LEISURE" + ",SECOND_LEISURE"
            + ",THIRD_LEISURE" + ",FIRST_LEARNING" + ",SECOND_LEARNING" + ",THIRD_LEARNING" + ",REACTION"
            + " FROM EXPLORE_INVENTORY WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM EXPLORE_INVENTORY WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the explore_inventory table. */
    private final String INSERT_QUERY = "INSERT INTO EXPLORE_INVENTORY" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",DATE_TAKEN" + ",FIRST_OCCUPATION" + ",SECOND_OCCUPATION" + ",THIRD_OCCUPATION" + ",FIRST_LEISURE"
            + ",SECOND_LEISURE" + ",THIRD_LEISURE" + ",FIRST_LEARNING" + ",SECOND_LEARNING" + ",THIRD_LEARNING" + ",REACTION"
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
            + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the explore_inventory table. */
    private final String UPDATE_QUERY = "UPDATE EXPLORE_INVENTORY SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?"
            + ",DATE_TAKEN = ?" + ",FIRST_OCCUPATION = ?" + ",SECOND_OCCUPATION = ?" + ",THIRD_OCCUPATION = ?" + ",FIRST_LEISURE = ?"
            + ",SECOND_LEISURE = ?" + ",THIRD_LEISURE = ?" + ",FIRST_LEARNING = ?" + ",SECOND_LEARNING = ?" + ",THIRD_LEARNING = ?"
            + ",REACTION = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM EXPLORE_INVENTORY " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

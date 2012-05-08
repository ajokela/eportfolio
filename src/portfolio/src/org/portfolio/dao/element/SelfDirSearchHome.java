/* $Name:  $ */
/* $Id: SelfDirSearchHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/SelfDirSearchHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.SelfDirSearch;

/**
 * Handles the persistence of the SelfDirSearch data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class SelfDirSearchHome extends AbstractElementHome {
    /**
     * Persists the SelfDirSearch Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the SelfDirSearch object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof SelfDirSearch)) {
        } else {
            SelfDirSearch self_dir_search = (SelfDirSearch) data;
            logService.debug("Is instance new?" + self_dir_search.isNew());
            if (self_dir_search.isNew()) {
                logService.debug("inserting new instance");
                insert(self_dir_search);
            } else {
                logService.debug("Updating instance.");
                update(self_dir_search);
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
        if (!(data instanceof SelfDirSearch)) {
        } else {
            SelfDirSearch self_dir_search = (SelfDirSearch) data;
            delete(self_dir_search);
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
    public List<SelfDirSearch> findByPersonId(String personId) {
        List<SelfDirSearch> elements = new ArrayList<SelfDirSearch>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                SelfDirSearch self_dir_search = new SelfDirSearch();
                self_dir_search.setPersonId(rs.getString("PERSON_ID"));
                self_dir_search.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                self_dir_search.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                self_dir_search.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                self_dir_search.setEntryName(rs.getString("ENTRY_NAME"));
                self_dir_search.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                self_dir_search.setRealistic(rs.getString("REALISTIC"));
                self_dir_search.setInvestigative(rs.getString("INVESTIGATIVE"));
                self_dir_search.setArtistic(rs.getString("ARTISTIC"));
                self_dir_search.setSocial(rs.getString("SOCIAL"));
                self_dir_search.setEnterprising(rs.getString("ENTERPRISING"));
                self_dir_search.setConventional(rs.getString("CONVENTIONAL"));
                self_dir_search.setSummaryCode(rs.getString("SUMMARY_CODE"));
                self_dir_search.setOccuInfo(rs.getString("OCCU_INFO"));
                self_dir_search.setReaction(rs.getString("REACTION"));
                // logService.debug("Adding instance " + self_dir_search + " to the elements list.");
                elements.add(self_dir_search);
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
        SelfDirSearch self_dir_search = new SelfDirSearch();
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
                self_dir_search.setPersonId(rs.getString("PERSON_ID"));
                self_dir_search.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                self_dir_search.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                self_dir_search.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                self_dir_search.setEntryName(rs.getString("ENTRY_NAME"));
                self_dir_search.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                self_dir_search.setRealistic(rs.getString("REALISTIC"));
                self_dir_search.setInvestigative(rs.getString("INVESTIGATIVE"));
                self_dir_search.setArtistic(rs.getString("ARTISTIC"));
                self_dir_search.setSocial(rs.getString("SOCIAL"));
                self_dir_search.setEnterprising(rs.getString("ENTERPRISING"));
                self_dir_search.setConventional(rs.getString("CONVENTIONAL"));
                self_dir_search.setSummaryCode(rs.getString("SUMMARY_CODE"));
                self_dir_search.setOccuInfo(rs.getString("OCCU_INFO"));
                self_dir_search.setReaction(rs.getString("REACTION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return self_dir_search;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type SelfDirSearch.
     * 
     * @param self_dir_search the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(SelfDirSearch self_dir_search) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, self_dir_search.getEntryName());
            if (self_dir_search.getDateTaken() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(self_dir_search.getDateTaken().getTime()));
            ps.setString(3, self_dir_search.getRealistic());
            ps.setString(4, self_dir_search.getInvestigative());
            ps.setString(5, self_dir_search.getArtistic());
            ps.setString(6, self_dir_search.getSocial());
            ps.setString(7, self_dir_search.getEnterprising());
            ps.setString(8, self_dir_search.getConventional());
            ps.setString(9, self_dir_search.getSummaryCode());
            ps.setString(10, self_dir_search.getOccuInfo());
            ps.setString(11, self_dir_search.getReaction());
            ps.setString(12, self_dir_search.getPersonId());
            ps.setBigDecimal(13, self_dir_search.getEntryId());
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
     * guarantee that the data object is of type SelfDirSearch.
     * 
     * @param self_dir_search the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(SelfDirSearch self_dir_search) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, self_dir_search.getPersonId());
            ps.setInt(2, id);
            self_dir_search.setEntryId(String.valueOf(id));
            ps.setString(3, self_dir_search.getEntryName());
            if (self_dir_search.getDateTaken() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(self_dir_search.getDateTaken().getTime()));
            ps.setString(5, self_dir_search.getRealistic());
            ps.setString(6, self_dir_search.getInvestigative());
            ps.setString(7, self_dir_search.getArtistic());
            ps.setString(8, self_dir_search.getSocial());
            ps.setString(9, self_dir_search.getEnterprising());
            ps.setString(10, self_dir_search.getConventional());
            ps.setString(11, self_dir_search.getSummaryCode());
            ps.setString(12, self_dir_search.getOccuInfo());
            ps.setString(13, self_dir_search.getReaction());
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
    protected void delete(SelfDirSearch self_dir_search) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, self_dir_search.getPersonId());
            ps.setBigDecimal(2, self_dir_search.getEntryId());
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
            + ",DATE_TAKEN" + ",REALISTIC" + ",INVESTIGATIVE" + ",ARTISTIC" + ",SOCIAL" + ",ENTERPRISING" + ",CONVENTIONAL"
            + ",SUMMARY_CODE" + ",OCCU_INFO" + ",REACTION" + " FROM SELF_DIR_SEARCH WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",DATE_TAKEN" + ",REALISTIC" + ",INVESTIGATIVE" + ",ARTISTIC" + ",SOCIAL" + ",ENTERPRISING" + ",CONVENTIONAL"
            + ",SUMMARY_CODE" + ",OCCU_INFO" + ",REACTION" + " FROM SELF_DIR_SEARCH WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM SELF_DIR_SEARCH WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the self_dir_search table. */
    private final String INSERT_QUERY = "INSERT INTO SELF_DIR_SEARCH" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",DATE_TAKEN" + ",REALISTIC" + ",INVESTIGATIVE" + ",ARTISTIC" + ",SOCIAL" + ",ENTERPRISING" + ",CONVENTIONAL"
            + ",SUMMARY_CODE" + ",OCCU_INFO" + ",REACTION" + ") VALUES (" + "?" + "," + "?" + ","
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
            + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the self_dir_search table. */
    private final String UPDATE_QUERY = "UPDATE SELF_DIR_SEARCH SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?"
            + ",DATE_TAKEN = ?" + ",REALISTIC = ?" + ",INVESTIGATIVE = ?" + ",ARTISTIC = ?" + ",SOCIAL = ?" + ",ENTERPRISING = ?"
            + ",CONVENTIONAL = ?" + ",SUMMARY_CODE = ?" + ",OCCU_INFO = ?" + ",REACTION = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM SELF_DIR_SEARCH " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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

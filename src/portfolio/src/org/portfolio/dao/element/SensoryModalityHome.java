/* $Name:  $ */
/* $Id: SensoryModalityHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/SensoryModalityHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.SensoryModality;

/**
 * Handles the persistence of the SensoryModality data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class SensoryModalityHome extends AbstractElementHome {
    /**
     * Persists the SensoryModality Object. Will perform an INSERT if it is a
     * new instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the SensoryModality object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof SensoryModality)) {
        } else {
            SensoryModality sensory_modality = (SensoryModality) data;
            logService.debug("Is instance new?" + sensory_modality.isNew());
            if (sensory_modality.isNew()) {
                logService.debug("inserting new instance");
                insert(sensory_modality);
            } else {
                logService.debug("Updating instance.");
                update(sensory_modality);
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
        if (!(data instanceof SensoryModality)) {
        } else {
            SensoryModality sensory_modality = (SensoryModality) data;
            delete(sensory_modality);
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
    public List<SensoryModality> findByPersonId(String personId) {
        List<SensoryModality> elements = new ArrayList<SensoryModality>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                SensoryModality sensory_modality = new SensoryModality();
                sensory_modality.setPersonId(rs.getString("PERSON_ID"));
                sensory_modality.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                sensory_modality.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                sensory_modality.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                sensory_modality.setEntryName(rs.getString("ENTRY_NAME"));
                sensory_modality.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                sensory_modality.setAuditory(rs.getString("AUDITORY"));
                sensory_modality.setVisual(rs.getString("VISUAL"));
                sensory_modality.setKinesthetic(rs.getString("KINESTHETIC"));
                sensory_modality.setInterpretation(rs.getString("INTERPRETATION"));
                // logService.debug("Adding instance " + sensory_modality + " to the elements list.");
                elements.add(sensory_modality);
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
        SensoryModality sensory_modality = new SensoryModality();
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
                sensory_modality.setPersonId(rs.getString("PERSON_ID"));
                sensory_modality.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                sensory_modality.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                sensory_modality.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                sensory_modality.setEntryName(rs.getString("ENTRY_NAME"));
                sensory_modality.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                sensory_modality.setAuditory(rs.getString("AUDITORY"));
                sensory_modality.setVisual(rs.getString("VISUAL"));
                sensory_modality.setKinesthetic(rs.getString("KINESTHETIC"));
                sensory_modality.setInterpretation(rs.getString("INTERPRETATION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return sensory_modality;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type SensoryModality.
     * 
     * @param sensory_modality the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(SensoryModality sensory_modality) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, sensory_modality.getEntryName());
            if (sensory_modality.getDateTaken() != null) {
                ps.setTimestamp(2, new Timestamp(sensory_modality.getDateTaken().getTime()));
            } else {
                ps.setTimestamp(2, null); // No DateTaken set
            }
            ps.setString(3, sensory_modality.getAuditory());
            ps.setString(4, sensory_modality.getVisual());
            ps.setString(5, sensory_modality.getKinesthetic());
            ps.setString(6, sensory_modality.getInterpretation());
            ps.setString(7, sensory_modality.getPersonId());
            ps.setBigDecimal(8, sensory_modality.getEntryId());
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
     * guarantee that the data object is of type SensoryModality.
     * 
     * @param sensory_modality the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(SensoryModality sensory_modality) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, sensory_modality.getPersonId());
            ps.setInt(2, id);
            sensory_modality.setEntryId(String.valueOf(id));
            ps.setString(3, sensory_modality.getEntryName());
            if (sensory_modality.getDateTaken() != null) {
                ps.setTimestamp(4, new Timestamp(sensory_modality.getDateTaken().getTime()));
            } else {
                ps.setTimestamp(4, null); // No DateTaken set
            }
            ps.setString(5, sensory_modality.getAuditory());
            ps.setString(6, sensory_modality.getVisual());
            ps.setString(7, sensory_modality.getKinesthetic());
            ps.setString(8, sensory_modality.getInterpretation());
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
     * @param sensory_modality the instance to delete. @ if there is a problem
     *            deleting.
     */
    protected void delete(SensoryModality sensory_modality) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, sensory_modality.getPersonId());
            ps.setBigDecimal(2, sensory_modality.getEntryId());
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
            + ",DATE_TAKEN" + ",AUDITORY" + ",VISUAL" + ",KINESTHETIC" + ",INTERPRETATION" + " FROM SENSORY_MODALITY WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",DATE_TAKEN" + ",AUDITORY" + ",VISUAL" + ",KINESTHETIC" + ",INTERPRETATION"
            + " FROM SENSORY_MODALITY WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM SENSORY_MODALITY WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the sensory_modality table. */
    private final String INSERT_QUERY = "INSERT INTO SENSORY_MODALITY" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",DATE_TAKEN" + ",AUDITORY" + ",VISUAL" + ",KINESTHETIC" + ",INTERPRETATION" + ") VALUES (" + "?" + "," + "?"
            + "," + sysdate + "," + sysdate + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the sensory_modality table. */
    private final String UPDATE_QUERY = "UPDATE SENSORY_MODALITY SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?"
            + ",DATE_TAKEN = ?" + ",AUDITORY = ?" + ",VISUAL = ?" + ",KINESTHETIC = ?" + ",INTERPRETATION = ?"
            + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM SENSORY_MODALITY " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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

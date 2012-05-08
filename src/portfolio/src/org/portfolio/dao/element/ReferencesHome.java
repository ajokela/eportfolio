/* $Name:  $ */
/* $Id: ReferencesHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/ReferencesHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.References;

/**
 * Handles the persistence of the References data element. <br />
 * Note: The name of the table REFERENCES has been changed to CAR_REFERENCES so
 * that it does not collide with the mySQL reserved word.
 * 
 * @author John Bush
 * @author Jack Brown, University of Minnesota
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class ReferencesHome extends AbstractElementHome {
    /**
     * Persists the References Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the References object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof References)) {
        } else {
            References references = (References) data;
            logService.debug("Is instance new?" + references.isNew());
            if (references.isNew()) {
                logService.debug("inserting new instance");
                insert(references);
            } else {
                logService.debug("Updating instance.");
                update(references);
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
        if (!(data instanceof References)) {
        } else {
            References references = (References) data;
            delete(references);
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
    public List<References> findByPersonId(String personId) {
        List<References> elements = new ArrayList<References>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                References references = new References();
                references.setPersonId(rs.getString("PERSON_ID"));
                references.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                references.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                references.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                references.setEntryName(rs.getString("ENTRY_NAME"));
                references.setPosition(rs.getString("POSITION"));
                references.setOrganization(rs.getString("ORGANIZATION"));
                references.setAddress1(rs.getString("ADDRESS1"));
                references.setAddress2(rs.getString("ADDRESS2"));
                references.setCity(rs.getString("CITY"));
                references.setState(rs.getString("STATE"));
                references.setZip(rs.getString("ZIP"));
                references.setCountry(rs.getString("COUNTRY"));
                references.setPhone(rs.getString("PHONE"));
                references.setEmail(rs.getString("EMAIL"));
                references.setMailPref(rs.getString("MAIL_PREF"));
                references.setPhonePref(rs.getString("PHONE_PREF"));
                references.setEmailPref(rs.getString("EMAIL_PREF"));
                // logService.debug("Adding instance " + references + " to the elements list.");
                elements.add(references);
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
        References references = new References();
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
                references.setPersonId(rs.getString("PERSON_ID"));
                references.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                references.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                references.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                references.setEntryName(rs.getString("ENTRY_NAME"));
                references.setPosition(rs.getString("POSITION"));
                references.setOrganization(rs.getString("ORGANIZATION"));
                references.setAddress1(rs.getString("ADDRESS1"));
                references.setAddress2(rs.getString("ADDRESS2"));
                references.setCity(rs.getString("CITY"));
                references.setState(rs.getString("STATE"));
                references.setZip(rs.getString("ZIP"));
                references.setCountry(rs.getString("COUNTRY"));
                references.setPhone(rs.getString("PHONE"));
                references.setEmail(rs.getString("EMAIL"));
                references.setMailPref(rs.getString("MAIL_PREF"));
                references.setPhonePref(rs.getString("PHONE_PREF"));
                references.setEmailPref(rs.getString("EMAIL_PREF"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return references;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type References.
     * 
     * @param references the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(References references) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, references.getEntryName());
            ps.setString(2, references.getPosition());
            ps.setString(3, references.getOrganization());
            ps.setString(4, references.getAddress1());
            ps.setString(5, references.getAddress2());
            ps.setString(6, references.getCity());
            ps.setString(7, references.getState());
            ps.setString(8, references.getZip());
            ps.setString(9, references.getCountry());
            ps.setString(10, references.getPhone());
            ps.setString(11, references.getEmail());
            ps.setString(12, references.getMailPref());
            ps.setString(13, references.getPhonePref());
            ps.setString(14, references.getEmailPref());
            ps.setString(15, references.getPersonId());
            ps.setBigDecimal(16, references.getEntryId());
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
     * guarantee that the data object is of type References.
     * 
     * @param references the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(References references) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, references.getPersonId());
            ps.setInt(2, id);
            references.setEntryId(String.valueOf(id));
            ps.setString(3, references.getEntryName());
            ps.setString(4, references.getPosition());
            ps.setString(5, references.getOrganization());
            ps.setString(6, references.getAddress1());
            ps.setString(7, references.getAddress2());
            ps.setString(8, references.getCity());
            ps.setString(9, references.getState());
            ps.setString(10, references.getZip());
            ps.setString(11, references.getCountry());
            ps.setString(12, references.getPhone());
            ps.setString(13, references.getEmail());
            ps.setString(14, references.getMailPref());
            ps.setString(15, references.getPhonePref());
            ps.setString(16, references.getEmailPref());
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
     * @param references the instance to delete. @ if there is a problem
     *            deleting.
     */
    protected void delete(References references) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, references.getPersonId());
            ps.setBigDecimal(2, references.getEntryId());
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
            + ",POSITION" + ",ORGANIZATION" + ",ADDRESS1" + ",ADDRESS2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",PHONE" + ",EMAIL"
            + ",MAIL_PREF" + ",PHONE_PREF" + ",EMAIL_PREF" + " FROM CAR_REFERENCES WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",POSITION" + ",ORGANIZATION" + ",ADDRESS1" + ",ADDRESS2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",PHONE" + ",EMAIL"
            + ",MAIL_PREF" + ",PHONE_PREF" + ",EMAIL_PREF" + " FROM CAR_REFERENCES WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM CAR_REFERENCES WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the CAR_REFERENCES table. */
    private final String INSERT_QUERY = "INSERT INTO CAR_REFERENCES" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",POSITION" + ",ORGANIZATION" + ",ADDRESS1" + ",ADDRESS2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY"
            + ",PHONE" + ",EMAIL" + ",MAIL_PREF" + ",PHONE_PREF" + ",EMAIL_PREF" + ") VALUES (" + "?" + "," + "?" + ","
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
            + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the CAR_REFERENCES table. */
    private final String UPDATE_QUERY = "UPDATE CAR_REFERENCES SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",POSITION = ?"
            + ",ORGANIZATION = ?" + ",ADDRESS1 = ?" + ",ADDRESS2 = ?" + ",CITY = ?" + ",STATE = ?" + ",ZIP = ?" + ",COUNTRY = ?"
            + ",PHONE = ?" + ",EMAIL = ?" + ",MAIL_PREF = ?" + ",PHONE_PREF = ?" + ",EMAIL_PREF = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM CAR_REFERENCES " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
	
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

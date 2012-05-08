/* $Name:  $ */
/* $Id: LanguageHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/LanguageHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.Language;

/**
 * Handles the persistence of the Language data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class LanguageHome extends AbstractElementHome {
    /**
     * Persists the Language Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the Language object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof Language)) {
        } else {
            Language language = (Language) data;
            logService.debug("Is instance new?" + language.isNew());
            if (language.isNew()) {
                logService.debug("inserting new instance");
                insert(language);
            } else {
                logService.debug("Updating instance.");
                update(language);
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
        if (!(data instanceof Language)) {
        } else {
            Language language = (Language) data;
            delete(language);
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
    public List<Language> findByPersonId(String personId) {
        List<Language> elements = new ArrayList<Language>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Language language = new Language();
                language.setPersonId(rs.getString("PERSON_ID"));
                language.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                language.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                language.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                language.setEntryName(rs.getString("ENTRY_NAME"));
                language.setLevelorCode(rs.getString("LEVELOR_CODE"));
                language.setLevelwrCode(rs.getString("LEVELWR_CODE"));
                language.setExperience(rs.getString("EXPERIENCE"));
                // logService.debug("Adding instance " + language + " to the elements list.");
                elements.add(language);
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
        Language language = new Language();
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
                language.setPersonId(rs.getString("PERSON_ID"));
                language.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                language.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                language.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                language.setEntryName(rs.getString("ENTRY_NAME"));
                language.setLevelorCode(rs.getString("LEVELOR_CODE"));
                language.setLevelwrCode(rs.getString("LEVELWR_CODE"));
                language.setExperience(rs.getString("EXPERIENCE"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return language;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Language.
     * 
     * @param language the instance to update. @ if there is a problem updating.
     */
    protected void update(Language language) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, language.getEntryName());
            ps.setString(2, language.getLevelorCode());
            ps.setString(3, language.getLevelwrCode());
            ps.setString(4, language.getExperience());
            ps.setString(5, language.getPersonId());
            ps.setBigDecimal(6, language.getEntryId());
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
     * guarantee that the data object is of type Language.
     * 
     * @param language the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(Language language) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (language.isRemote()) ? language.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, language.getPersonId());
            ps.setInt(2, id);
            language.setEntryId(String.valueOf(id));
            ps.setString(3, language.getEntryName());
            ps.setString(4, language.getLevelorCode());
            ps.setString(5, language.getLevelwrCode());
            ps.setString(6, language.getExperience());
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
    protected void delete(Language language) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, language.getPersonId());
            ps.setBigDecimal(2, language.getEntryId());
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
            + ",LEVELOR_CODE" + ",LEVELWR_CODE" + ",EXPERIENCE" + " FROM LANGUAGE WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",LEVELOR_CODE" + ",LEVELWR_CODE" + ",EXPERIENCE" + " FROM LANGUAGE WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM LANGUAGE WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the language table. */
    private final String INSERT_QUERY = "INSERT INTO LANGUAGE" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",LEVELOR_CODE" + ",LEVELWR_CODE" + ",EXPERIENCE" + ") VALUES (" + "?" + "," + "?" + "," + sysdate + ","
            + sysdate + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the language table. */
    private final String UPDATE_QUERY = "UPDATE LANGUAGE SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",LEVELOR_CODE = ?"
            + ",LEVELWR_CODE = ?" + ",EXPERIENCE = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM LANGUAGE " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

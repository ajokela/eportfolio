/* $Name:  $ */
/* $Id: ComputerSkillsHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/ComputerSkillsHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.ComputerSkills;
import org.portfolio.model.ElementDataObject;

/**
 * Handles the persistence of the ComputerSkills data element. <br />
 * Note: The name of the DATABASE field has been changed to DATABASE_SKILLS so
 * that it does not collide with the mySQL reserved word.
 * 
 * @author John Bush
 * @author Jack Brown, University of Minnesota
 * @since 1.0
 * @version $Revision: 1.8 $
 */

public class ComputerSkillsHome extends AbstractElementHome {

    /**
     * Persists the ComputerSkills Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the ComputerSkills object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof ComputerSkills)) {
        } else {
            ComputerSkills computer_skills = (ComputerSkills) data;
            logService.debug("Is instance new?" + computer_skills.isNew());
            if (computer_skills.isNew()) {
                logService.debug("inserting new instance");
                insert(computer_skills);
            } else {
                logService.debug("Updating instance " + computer_skills);
                update(computer_skills);
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
        logService.debug("We have a data object with entryId=" + data.getEntryIdString());
        logService.debug("We want to delete it : " + data);
        if (!(data instanceof ComputerSkills)) {
        } else {
            ComputerSkills computer_skills = (ComputerSkills) data;
            logService.debug("Trying to delete " + computer_skills);
            delete(computer_skills);
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
    public List<ComputerSkills> findByPersonId(String personId) {
        List<ComputerSkills> elements = new ArrayList<ComputerSkills>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ComputerSkills computer_skills = new ComputerSkills();
                computer_skills.setPersonId(rs.getString("PERSON_ID"));
                computer_skills.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                computer_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                computer_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                computer_skills.setEntryName(rs.getString("ENTRY_NAME"));
                computer_skills.setGeneralOps(rs.getString("GENERAL_OPS"));
                computer_skills.setCommInt(rs.getString("COMM_INT"));
                computer_skills.setWordProc(rs.getString("WORD_PROC"));
                computer_skills.setSpreadsheet(rs.getString("SPREADSHEET"));
                computer_skills.setDatabase(rs.getString("DATABASE_SKILLS"));
                computer_skills.setGraphics(rs.getString("GRAPHICS"));
                computer_skills.setApplications(rs.getString("APPLICATIONS"));
                computer_skills.setLanguages(rs.getString("LANGUAGES"));
                computer_skills.setExperience(rs.getString("EXPERIENCE"));
                // logService.debug("Adding instance " + computer_skills + " to the elements list.");
                elements.add(computer_skills);
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
        ComputerSkills computer_skills = new ComputerSkills();
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
                computer_skills.setEntryId(entryId);
                computer_skills.setPersonId(rs.getString("PERSON_ID"));
                computer_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                computer_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                computer_skills.setEntryName(rs.getString("ENTRY_NAME"));
                computer_skills.setGeneralOps(rs.getString("GENERAL_OPS"));
                computer_skills.setCommInt(rs.getString("COMM_INT"));
                computer_skills.setWordProc(rs.getString("WORD_PROC"));
                computer_skills.setSpreadsheet(rs.getString("SPREADSHEET"));
                computer_skills.setDatabase(rs.getString("DATABASE_SKILLS"));
                computer_skills.setGraphics(rs.getString("GRAPHICS"));
                computer_skills.setApplications(rs.getString("APPLICATIONS"));
                computer_skills.setLanguages(rs.getString("LANGUAGES"));
                computer_skills.setExperience(rs.getString("EXPERIENCE"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return computer_skills;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type ComputerSkills.
     * 
     * @param computer_skills the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(ComputerSkills computer_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, computer_skills.getEntryName());
            ps.setString(2, computer_skills.getGeneralOps());
            ps.setString(3, computer_skills.getCommInt());
            ps.setString(4, computer_skills.getWordProc());
            ps.setString(5, computer_skills.getSpreadsheet());
            ps.setString(6, computer_skills.getDatabase());
            ps.setString(7, computer_skills.getGraphics());
            ps.setString(8, computer_skills.getApplications());
            ps.setString(9, computer_skills.getLanguages());
            ps.setString(10, computer_skills.getExperience());
            ps.setString(11, computer_skills.getPersonId());
            ps.setBigDecimal(12, computer_skills.getEntryId());
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
     * guarantee that the data object is of type ComputerSkills.
     * 
     * @param computer_skills the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(ComputerSkills computer_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (computer_skills.isRemote()) ? computer_skills.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, computer_skills.getPersonId());
            ps.setInt(2, id);
            computer_skills.setEntryId(String.valueOf(id));
            ps.setString(3, computer_skills.getEntryName());
            ps.setString(4, computer_skills.getGeneralOps());
            ps.setString(5, computer_skills.getCommInt());
            ps.setString(6, computer_skills.getWordProc());
            ps.setString(7, computer_skills.getSpreadsheet());
            ps.setString(8, computer_skills.getDatabase());
            ps.setString(9, computer_skills.getGraphics());
            ps.setString(10, computer_skills.getApplications());
            ps.setString(11, computer_skills.getLanguages());
            ps.setString(12, computer_skills.getExperience());
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
     * @param computer_skills the instance to delete. @ if there is a problem
     *            deleting.
     */
    protected void delete(ComputerSkills computer_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            logService.debug("DELETE_QUERY  : \n" + DELETE_QUERY);
            logService.debug("deleting with personId  = " + computer_skills.getPersonId() + " and entry_id = "
                    + computer_skills.getEntryId());
            ps.setString(1, computer_skills.getPersonId());
            ps.setBigDecimal(2, computer_skills.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",GENERAL_OPS,COMM_INT,WORD_PROC,SPREADSHEET,DATABASE_SKILLS,GRAPHICS,APPLICATIONS"
            + ",LANGUAGES,EXPERIENCE FROM COMPUTER_SKILLS WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",GENERAL_OPS,COMM_INT,WORD_PROC,SPREADSHEET,DATABASE_SKILLS,GRAPHICS,APPLICATIONS"
            + ",LANGUAGES,EXPERIENCE FROM COMPUTER_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM COMPUTER_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the computer_skills table. */
    private final String INSERT_QUERY = "INSERT INTO COMPUTER_SKILLS(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,GENERAL_OPS,COMM_INT,WORD_PROC,SPREADSHEET,DATABASE_SKILLS,GRAPHICS"
            + ",APPLICATIONS,LANGUAGES,EXPERIENCE) VALUES (?,?," + sysdate + "," + sysdate + ",?,?,?,?,?,?,?,?,?,?)";

    /** The query to INSERT a new row into the computer_skills table. */
    private final String UPDATE_QUERY = "UPDATE COMPUTER_SKILLS SET MODIFIED_DATE = " + sysdate
            + ",ENTRY_NAME = ?,GENERAL_OPS = ?,COMM_INT = ?,WORD_PROC = ?,SPREADSHEET = ?,DATABASE_SKILLS = ?"
            + ",GRAPHICS = ?,APPLICATIONS = ?,LANGUAGES = ?,EXPERIENCE = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM COMPUTER_SKILLS  WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

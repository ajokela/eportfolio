/* $Name:  $ */
/* $Id: AdditionalSkillsHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/AdditionalSkillsHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.AdditionalSkills;
import org.portfolio.model.ElementDataObject;

/**
 * Handles the persistence of the AdditionalSkills data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class AdditionalSkillsHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        AdditionalSkills additional_skills = (AdditionalSkills) data;
        if (additional_skills.isNew()) {
            insert(additional_skills);
        } else {
            update(additional_skills);
        }
    }

    public void remove(ElementDataObject data) {
        AdditionalSkills additional_skills = (AdditionalSkills) data;
        delete(additional_skills);
    }

    /**
     * Method to find all instances of this particular data element for a
     * specified user. <i>Note: This will need to implement sort order as well,
     * at some time.</i>
     * 
     * @param personId of the user that we are finding elements for
     * @return a java.util.List of DataObjects of the type for this element.
     */
    public List<AdditionalSkills> findByPersonId(String personId) {
        List<AdditionalSkills> elements = new ArrayList<AdditionalSkills>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                AdditionalSkills additional_skills = new AdditionalSkills();
                additional_skills.setPersonId(rs.getString("PERSON_ID"));
                additional_skills.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                additional_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                additional_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                additional_skills.setEntryName(rs.getString("ENTRY_NAME"));
                additional_skills.setDescription(rs.getString("DESCRIPTION"));
                additional_skills.setExperience(rs.getString("EXPERIENCE"));
                // logService.debug("Adding instance " + additional_skills + " to the elements list.");
                elements.add(additional_skills);
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
     * @param entryId of the element
     */
    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
        AdditionalSkills additional_skills = new AdditionalSkills();
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
                additional_skills.setPersonId(rs.getString("PERSON_ID"));
                additional_skills.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                additional_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                additional_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                additional_skills.setEntryName(rs.getString("ENTRY_NAME"));
                additional_skills.setDescription(rs.getString("DESCRIPTION"));
                additional_skills.setExperience(rs.getString("EXPERIENCE"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return additional_skills;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type AdditionalSkills.
     * 
     * @param additional_skills the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(AdditionalSkills additional_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, additional_skills.getEntryName());
            ps.setString(2, additional_skills.getDescription());
            ps.setString(3, additional_skills.getExperience());
            ps.setString(4, additional_skills.getPersonId());
            ps.setBigDecimal(5, additional_skills.getEntryId());
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
     * guarantee that the data object is of type AdditionalSkills.
     * 
     * @param additional_skills the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(AdditionalSkills additional_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (additional_skills.isRemote()) ? additional_skills.getEntryId().intValue() : sequenceGenerator
                .getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, additional_skills.getPersonId());
            ps.setInt(2, id);
            additional_skills.setEntryId(String.valueOf(id));
            ps.setString(3, additional_skills.getEntryName());
            ps.setString(4, additional_skills.getDescription());
            ps.setString(5, additional_skills.getExperience());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /**
     * Deletes the instance of the data element.
     * 
     * The store method needs to guarantee that the data object is of type
     * <code>AdditionalSkills</code>.
     * 
     * @param additionalSkills the instance to delete @ if there is a problem
     *            deleting
     */
    protected void delete(AdditionalSkills additionalSkills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, additionalSkills.getPersonId());
            ps.setBigDecimal(2, additionalSkills.getEntryId());
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
            + ",DESCRIPTION,EXPERIENCE FROM ADDITIONAL_SKILLS WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",DESCRIPTION,EXPERIENCE FROM  ADDITIONAL_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(PERSON_ID) FROM  ADDITIONAL_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the additional_skills table. */
    private final String INSERT_QUERY = "INSERT INTO  ADDITIONAL_SKILLS(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,DESCRIPTION,EXPERIENCE) VALUES (?,?," + sysdate + "," + sysdate + ",?,?,?)";

    /** The query to INSERT a new row into the additional_skills table. */
    private final String UPDATE_QUERY = "UPDATE  ADDITIONAL_SKILLS SET MODIFIED_DATE = " + sysdate
            + ",ENTRY_NAME = ?,DESCRIPTION = ?,EXPERIENCE = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM  ADDITIONAL_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";


	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
		// SELECT_QUERY_SINGLE_COUNT
		
        boolean additional_skills = false;
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
            		additional_skills = true;
            	}
            	
            } else {
            	additional_skills = false;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return additional_skills;

	}
}

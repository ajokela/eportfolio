/* $Name:  $ */
/* $Id: TransSkillsHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/TransSkillsHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.TransSkills;

/**
 * Handles the persistence of the TransSkills data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class TransSkillsHome extends AbstractElementHome {
    /**
     * Persists the TransSkills Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the TransSkills object to persist @ if a data type other than
     *            the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof TransSkills)) {
        } else {
            TransSkills trans_skills = (TransSkills) data;
            logService.debug("Is instance new?" + trans_skills.isNew());
            if (trans_skills.isNew()) {
                logService.debug("inserting new instance");
                insert(trans_skills);
            } else {
                logService.debug("Updating instance.");
                update(trans_skills);
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
        if (!(data instanceof TransSkills)) {
        } else {
            TransSkills trans_skills = (TransSkills) data;
            delete(trans_skills);
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
    public List<TransSkills> findByPersonId(String personId) {
        List<TransSkills> elements = new ArrayList<TransSkills>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                TransSkills trans_skills = new TransSkills();
                trans_skills.setPersonId(rs.getString("PERSON_ID"));
                trans_skills.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                trans_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                trans_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                trans_skills.setEntryName(rs.getString("ENTRY_NAME"));
                trans_skills.setSurveyDate(rs.getTimestamp("SURVEY_DATE"));
                trans_skills.setSelfKnowledgeTotal(rs.getString("SELF_KNOWLEDGE_TOTAL"));
                trans_skills.setSelfKnowledgeAve(rs.getString("SELF_KNOWLEDGE_AVE"));
                trans_skills.setEffectiveCommTotal(rs.getString("EFFECTIVE_COMM_TOTAL"));
                trans_skills.setEffectiveCommAve(rs.getString("EFFECTIVE_COMM_AVE"));
                trans_skills.setProcessControlTotal(rs.getString("PROCESS_CONTROL_TOTAL"));
                trans_skills.setProcessControlAve(rs.getString("PROCESS_CONTROL_AVE"));
                trans_skills.setVisioningTotal(rs.getString("VISIONING_TOTAL"));
                trans_skills.setVisioningAve(rs.getString("VISIONING_AVE"));
                trans_skills.setInterpretation(rs.getString("INTERPRETATION"));
                // logService.debug("Adding instance " + trans_skills + " to the elements list.");
                elements.add(trans_skills);
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
        TransSkills trans_skills = new TransSkills();
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
                trans_skills.setPersonId(rs.getString("PERSON_ID"));
                trans_skills.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                trans_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                trans_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                trans_skills.setEntryName(rs.getString("ENTRY_NAME"));
                trans_skills.setSurveyDate(rs.getTimestamp("SURVEY_DATE"));
                trans_skills.setSelfKnowledgeTotal(rs.getString("SELF_KNOWLEDGE_TOTAL"));
                trans_skills.setSelfKnowledgeAve(rs.getString("SELF_KNOWLEDGE_AVE"));
                trans_skills.setEffectiveCommTotal(rs.getString("EFFECTIVE_COMM_TOTAL"));
                trans_skills.setEffectiveCommAve(rs.getString("EFFECTIVE_COMM_AVE"));
                trans_skills.setProcessControlTotal(rs.getString("PROCESS_CONTROL_TOTAL"));
                trans_skills.setProcessControlAve(rs.getString("PROCESS_CONTROL_AVE"));
                trans_skills.setVisioningTotal(rs.getString("VISIONING_TOTAL"));
                trans_skills.setVisioningAve(rs.getString("VISIONING_AVE"));
                trans_skills.setInterpretation(rs.getString("INTERPRETATION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return trans_skills;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type TransSkills.
     * 
     * @param trans_skills the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(TransSkills trans_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, trans_skills.getEntryName());
            if (trans_skills.getSurveyDate() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(trans_skills.getSurveyDate().getTime()));
            ps.setString(3, trans_skills.getSelfKnowledgeTotal());
            ps.setString(4, trans_skills.getSelfKnowledgeAve());
            ps.setString(5, trans_skills.getEffectiveCommTotal());
            ps.setString(6, trans_skills.getEffectiveCommAve());
            ps.setString(7, trans_skills.getProcessControlTotal());
            ps.setString(8, trans_skills.getProcessControlAve());
            ps.setString(9, trans_skills.getVisioningTotal());
            ps.setString(10, trans_skills.getVisioningAve());
            ps.setString(11, trans_skills.getInterpretation());
            ps.setString(12, trans_skills.getPersonId());
            ps.setBigDecimal(13, trans_skills.getEntryId());
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
     * guarantee that the data object is of type TransSkills.
     * 
     * @param trans_skills the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(TransSkills trans_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, trans_skills.getPersonId());
            ps.setInt(2, id);
            trans_skills.setEntryId(String.valueOf(id));
            ps.setString(3, trans_skills.getEntryName());
            if (trans_skills.getSurveyDate() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(trans_skills.getSurveyDate().getTime()));
            ps.setString(5, trans_skills.getSelfKnowledgeTotal());
            ps.setString(6, trans_skills.getSelfKnowledgeAve());
            ps.setString(7, trans_skills.getEffectiveCommTotal());
            ps.setString(8, trans_skills.getEffectiveCommAve());
            ps.setString(9, trans_skills.getProcessControlTotal());
            ps.setString(10, trans_skills.getProcessControlAve());
            ps.setString(11, trans_skills.getVisioningTotal());
            ps.setString(12, trans_skills.getVisioningAve());
            ps.setString(13, trans_skills.getInterpretation());
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
    protected void delete(TransSkills trans_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, trans_skills.getPersonId());
            ps.setBigDecimal(2, trans_skills.getEntryId());
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
            + ",SURVEY_DATE" + ",SELF_KNOWLEDGE_TOTAL" + ",SELF_KNOWLEDGE_AVE" + ",EFFECTIVE_COMM_TOTAL" + ",EFFECTIVE_COMM_AVE"
            + ",PROCESS_CONTROL_TOTAL" + ",PROCESS_CONTROL_AVE" + ",VISIONING_TOTAL" + ",VISIONING_AVE" + ",INTERPRETATION"
            + " FROM TRANS_SKILLS WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",SURVEY_DATE" + ",SELF_KNOWLEDGE_TOTAL" + ",SELF_KNOWLEDGE_AVE" + ",EFFECTIVE_COMM_TOTAL" + ",EFFECTIVE_COMM_AVE"
            + ",PROCESS_CONTROL_TOTAL" + ",PROCESS_CONTROL_AVE" + ",VISIONING_TOTAL" + ",VISIONING_AVE" + ",INTERPRETATION"
            + " FROM TRANS_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM TRANS_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the trans_skills table. */
    private final String INSERT_QUERY = "INSERT INTO TRANS_SKILLS" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",SURVEY_DATE" + ",SELF_KNOWLEDGE_TOTAL" + ",SELF_KNOWLEDGE_AVE" + ",EFFECTIVE_COMM_TOTAL"
            + ",EFFECTIVE_COMM_AVE" + ",PROCESS_CONTROL_TOTAL" + ",PROCESS_CONTROL_AVE" + ",VISIONING_TOTAL" + ",VISIONING_AVE"
            + ",INTERPRETATION" + ") VALUES (" + "?" + "," + "?" + ","
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

    /** The query to INSERT a new row into the trans_skills table. */
    private final String UPDATE_QUERY = "UPDATE TRANS_SKILLS SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",SURVEY_DATE = ?"
            + ",SELF_KNOWLEDGE_TOTAL = ?" + ",SELF_KNOWLEDGE_AVE = ?" + ",EFFECTIVE_COMM_TOTAL = ?" + ",EFFECTIVE_COMM_AVE = ?"
            + ",PROCESS_CONTROL_TOTAL = ?" + ",PROCESS_CONTROL_AVE = ?" + ",VISIONING_TOTAL = ?" + ",VISIONING_AVE = ?"
            + ",INTERPRETATION = ?" + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM TRANS_SKILLS " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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

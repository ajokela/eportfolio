/* $Name:  $ */
/* $Id: InformationSkillsHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 *
 * @author	John Hemmerle, University of Minnesota Web Development
 * @since	0.1
 * @version	$Revision: 1.8 $

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
import org.portfolio.model.InformationSkills;

/**
 * Handles the persistence of the InformationSkills data element. <br />
 * 
 * @author John Hemmerle, University of Minnesota
 * @since 1.0
 * @version $Revision: 1.8 $
 */

public class InformationSkillsHome extends AbstractElementHome {

    /**
     * Persists the InformationSkills Object. Will perform an INSERT if it is a
     * new instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the InformationSkills object to persist @ if a data type
     *            other than the one expected is found. @ if there are any
     *            problems persisting the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof InformationSkills)) {
        } else {
            InformationSkills information_skills = (InformationSkills) data;
            logService.debug("Is instance new?" + information_skills.isNew());
            if (information_skills.isNew()) {
                logService.debug("inserting new instance");
                insert(information_skills);
            } else {
                logService.debug("Updating instance " + information_skills);
                update(information_skills);
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
        if (!(data instanceof InformationSkills)) {
        } else {
            InformationSkills information_skills = (InformationSkills) data;
            logService.debug("Trying to delete " + information_skills);
            delete(information_skills);
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
    public List<InformationSkills> findByPersonId(String personId) {
        List<InformationSkills> elements = new ArrayList<InformationSkills>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                InformationSkills information_skills = new InformationSkills();
                information_skills.setPersonId(rs.getString("PERSON_ID"));
                information_skills.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                information_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                information_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                information_skills.setEntryName(rs.getString("ENTRY_NAME"));
                information_skills.setRecognizeNeed(rs.getString("RECOGNIZE_NEED"));
                information_skills.setAccessInfo(rs.getString("ACCESS_INFO"));
                information_skills.setEvaluateInfo(rs.getString("EVALUATE_INFO"));
                information_skills.setUseEffectively(rs.getString("USE_EFFECTIVELY"));
                information_skills.setUseEthically(rs.getString("USE_ETHICALLY"));
                // logService.debug("Adding instance " + information_skills + " to the elements list.");
                elements.add(information_skills);
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
        InformationSkills information_skills = new InformationSkills();
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
                information_skills.setEntryId(entryId);
                information_skills.setPersonId(rs.getString("PERSON_ID"));
                information_skills.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                information_skills.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                information_skills.setEntryName(rs.getString("ENTRY_NAME"));
                information_skills.setRecognizeNeed(rs.getString("RECOGNIZE_NEED"));
                information_skills.setAccessInfo(rs.getString("ACCESS_INFO"));
                information_skills.setEvaluateInfo(rs.getString("EVALUATE_INFO"));
                information_skills.setUseEffectively(rs.getString("USE_EFFECTIVELY"));
                information_skills.setUseEthically(rs.getString("USE_ETHICALLY"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return information_skills;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type InformationSkills.
     * 
     * @param information_skills the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(InformationSkills information_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, information_skills.getEntryName());
            ps.setString(2, information_skills.getRecognizeNeed());
            ps.setString(3, information_skills.getAccessInfo());
            ps.setString(4, information_skills.getEvaluateInfo());
            ps.setString(5, information_skills.getUseEffectively());
            ps.setString(6, information_skills.getUseEthically());
            ps.setString(7, information_skills.getPersonId());
            ps.setBigDecimal(8, information_skills.getEntryId());
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
     * guarantee that the data object is of type InformationSkills.
     * 
     * @param information_skills the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(InformationSkills information_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (information_skills.isRemote()) ? information_skills.getEntryId().intValue() : sequenceGenerator
                .getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, information_skills.getPersonId());
            ps.setInt(2, id);
            information_skills.setEntryId(String.valueOf(id));
            ps.setString(3, information_skills.getEntryName());
            ps.setString(4, information_skills.getRecognizeNeed());
            ps.setString(5, information_skills.getAccessInfo());
            ps.setString(6, information_skills.getEvaluateInfo());
            ps.setString(7, information_skills.getUseEffectively());
            ps.setString(8, information_skills.getUseEthically());
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
     * @param information_skills the instance to delete. @ if there is a problem
     *            deleting.
     */
    protected void delete(InformationSkills information_skills) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            logService.debug("DELETE_QUERY  : \n" + DELETE_QUERY);
            logService.debug("deleting with personId  = " + information_skills.getPersonId() + " and ENTRY_ID = "
                    + information_skills.getEntryId());
            ps.setString(1, information_skills.getPersonId());
            ps.setBigDecimal(2, information_skills.getEntryId());
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
    /**
     * The query to select ALL relevant instances of INFORMATION_SKILLS for a
     * person.
     */
    private final String SELECT_QUERY_ALL = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",RECOGNIZE_NEED" + ",ACCESS_INFO" + ",EVALUATE_INFO" + ",USE_EFFECTIVELY" + ",USE_ETHICALLY"
            + " FROM INFORMATION_SKILLS WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",RECOGNIZE_NEED" + ",ACCESS_INFO" + ",EVALUATE_INFO" + ",USE_EFFECTIVELY" + ",USE_ETHICALLY"
            + " FROM INFORMATION_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM INFORMATION_SKILLS WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the information_skills table. */
    private final String INSERT_QUERY = "INSERT INTO INFORMATION_SKILLS" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",RECOGNIZE_NEED" + ",ACCESS_INFO" + ",EVALUATE_INFO" + ",USE_EFFECTIVELY" + ",USE_ETHICALLY" + ") VALUES ("
            + "?" + "," + "?" + "," + sysdate + "," + sysdate + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the information_skills table. */
    private final String UPDATE_QUERY = "UPDATE INFORMATION_SKILLS SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?"
            + ",RECOGNIZE_NEED = ?" + ",ACCESS_INFO = ?" + ",EVALUATE_INFO = ?" + ",USE_EFFECTIVELY = ?" + ",USE_ETHICALLY = ?"
            + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM INFORMATION_SKILLS " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

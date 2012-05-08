/* $Name:  $ */
/* $Id: AssessScoresHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/AssessScoresHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.AssessScores;
import org.portfolio.model.ElementDataObject;

/**
 * Handles the persistence of the AssessScores data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class AssessScoresHome extends AbstractElementHome {

    /**
     * Persists the AssessScores Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the AssessScores object to persist @ if a data type other
     *            than the one expected is found. @ if there are any problems
     *            persisting the data.
     */
    public void store(ElementDataObject data) {
        AssessScores assess_scores = (AssessScores) data;
        logService.debug("Is instance new?" + assess_scores.isNew());
        if (assess_scores.isNew()) {
            logService.debug("inserting new instance");
            insert(assess_scores);
        } else {
            logService.debug("Updating instance.");
            update(assess_scores);
        }
    }

    /**
     * Deletes the instance of $table.destinationClassNameHome.
     * 
     * @param data the $table.destinationClassNameHome object to persist
     */
    public void remove(ElementDataObject data) {
        if (!(data instanceof AssessScores)) {
        } else {
            AssessScores assess_scores = (AssessScores) data;
            delete(assess_scores);
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
    public List<AssessScores> findByPersonId(String personId) {
        List<AssessScores> elements = new ArrayList<AssessScores>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                AssessScores assess_scores = new AssessScores();
                assess_scores.setPersonId(rs.getString("PERSON_ID"));
                assess_scores.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                assess_scores.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                assess_scores.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                assess_scores.setEntryName(rs.getString("ENTRY_NAME"));
                assess_scores.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                assess_scores.setScore1(rs.getString("SCORE1"));
                assess_scores.setSection1(rs.getString("SECTION1"));
                assess_scores.setPercent1(rs.getString("PERCENT1"));
                assess_scores.setScore2(rs.getString("SCORE2"));
                assess_scores.setSection2(rs.getString("SECTION2"));
                assess_scores.setPercent2(rs.getString("PERCENT2"));
                assess_scores.setScore3(rs.getString("SCORE3"));
                assess_scores.setSection3(rs.getString("SECTION3"));
                assess_scores.setPercent3(rs.getString("PERCENT3"));
                assess_scores.setScore4(rs.getString("SCORE4"));
                assess_scores.setSection4(rs.getString("SECTION4"));
                assess_scores.setPercent4(rs.getString("PERCENT4"));
                assess_scores.setScore5(rs.getString("SCORE5"));
                assess_scores.setSection5(rs.getString("SECTION5"));
                assess_scores.setPercent5(rs.getString("PERCENT5"));
                assess_scores.setScore6(rs.getString("SCORE6"));
                assess_scores.setSection6(rs.getString("SECTION6"));
                assess_scores.setPercent6(rs.getString("PERCENT6"));
                assess_scores.setComposite(rs.getString("COMPOSITE"));
                assess_scores.setTotal(rs.getString("TOTAL"));
                assess_scores.setInterpretation(rs.getString("INTERPRETATION"));
                // logService.debug("Adding instance " + assess_scores + " to the elements list.");
                elements.add(assess_scores);
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
        AssessScores assess_scores = new AssessScores();
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
                assess_scores.setPersonId(rs.getString("PERSON_ID"));
                assess_scores.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                assess_scores.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                assess_scores.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                assess_scores.setEntryName(rs.getString("ENTRY_NAME"));
                assess_scores.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                assess_scores.setScore1(rs.getString("SCORE1"));
                assess_scores.setSection1(rs.getString("SECTION1"));
                assess_scores.setPercent1(rs.getString("PERCENT1"));
                assess_scores.setScore2(rs.getString("SCORE2"));
                assess_scores.setSection2(rs.getString("SECTION2"));
                assess_scores.setPercent2(rs.getString("PERCENT2"));
                assess_scores.setScore3(rs.getString("SCORE3"));
                assess_scores.setSection3(rs.getString("SECTION3"));
                assess_scores.setPercent3(rs.getString("PERCENT3"));
                assess_scores.setScore4(rs.getString("SCORE4"));
                assess_scores.setSection4(rs.getString("SECTION4"));
                assess_scores.setPercent4(rs.getString("PERCENT4"));
                assess_scores.setScore5(rs.getString("SCORE5"));
                assess_scores.setSection5(rs.getString("SECTION5"));
                assess_scores.setPercent5(rs.getString("PERCENT5"));
                assess_scores.setScore6(rs.getString("SCORE6"));
                assess_scores.setSection6(rs.getString("SECTION6"));
                assess_scores.setPercent6(rs.getString("PERCENT6"));
                assess_scores.setComposite(rs.getString("COMPOSITE"));
                assess_scores.setTotal(rs.getString("TOTAL"));
                assess_scores.setInterpretation(rs.getString("INTERPRETATION"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return assess_scores;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type AssessScores.
     * 
     * @param assess_scores the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(AssessScores assess_scores) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, assess_scores.getEntryName());
            ps.setTimestamp(2, new java.sql.Timestamp(assess_scores.getDateTaken().getTime()));
            ps.setString(3, assess_scores.getScore1());
            ps.setString(4, assess_scores.getSection1());
            ps.setString(5, assess_scores.getPercent1());
            ps.setString(6, assess_scores.getScore2());
            ps.setString(7, assess_scores.getSection2());
            ps.setString(8, assess_scores.getPercent2());
            ps.setString(9, assess_scores.getScore3());
            ps.setString(10, assess_scores.getSection3());
            ps.setString(11, assess_scores.getPercent3());
            ps.setString(12, assess_scores.getScore4());
            ps.setString(13, assess_scores.getSection4());
            ps.setString(14, assess_scores.getPercent4());
            ps.setString(15, assess_scores.getScore5());
            ps.setString(16, assess_scores.getSection5());
            ps.setString(17, assess_scores.getPercent5());
            ps.setString(18, assess_scores.getScore6());
            ps.setString(19, assess_scores.getSection6());
            ps.setString(20, assess_scores.getPercent6());
            ps.setString(21, assess_scores.getComposite());
            ps.setString(22, assess_scores.getTotal());
            ps.setString(23, assess_scores.getInterpretation());
            ps.setString(24, assess_scores.getPersonId());
            ps.setBigDecimal(25, assess_scores.getEntryId());
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
     * guarantee that the data object is of type AssessScores.
     * 
     * @param assess_scores the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(AssessScores assess_scores) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (assess_scores.isRemote()) ? assess_scores.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, assess_scores.getPersonId());
            ps.setInt(2, id);
            assess_scores.setEntryId(String.valueOf(id));
            ps.setString(3, assess_scores.getEntryName());
            if (assess_scores.getDateTaken() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(assess_scores.getDateTaken().getTime()));
            ps.setString(5, assess_scores.getScore1());
            ps.setString(6, assess_scores.getSection1());
            ps.setString(7, assess_scores.getPercent1());
            ps.setString(8, assess_scores.getScore2());
            ps.setString(9, assess_scores.getSection2());
            ps.setString(10, assess_scores.getPercent2());
            ps.setString(11, assess_scores.getScore3());
            ps.setString(12, assess_scores.getSection3());
            ps.setString(13, assess_scores.getPercent3());
            ps.setString(14, assess_scores.getScore4());
            ps.setString(15, assess_scores.getSection4());
            ps.setString(16, assess_scores.getPercent4());
            ps.setString(17, assess_scores.getScore5());
            ps.setString(18, assess_scores.getSection5());
            ps.setString(19, assess_scores.getPercent5());
            ps.setString(20, assess_scores.getScore6());
            ps.setString(21, assess_scores.getSection6());
            ps.setString(22, assess_scores.getPercent6());
            ps.setString(23, assess_scores.getComposite());
            ps.setString(24, assess_scores.getTotal());
            ps.setString(25, assess_scores.getInterpretation());
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
    protected void delete(AssessScores assess_scores) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, assess_scores.getPersonId());
            ps.setBigDecimal(2, assess_scores.getEntryId());
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
            + ",DATE_TAKEN,SCORE1,SECTION1,PERCENT1,SCORE2,SECTION2,PERCENT2,SCORE3,SECTION3"
            + ",PERCENT3,SCORE4,SECTION4,PERCENT4,SCORE5,SECTION5,PERCENT5,SCORE6,SECTION6"
            + ",PERCENT6,COMPOSITE,TOTAL,INTERPRETATION FROM ASSESS_SCORES WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",DATE_TAKEN,SCORE1,SECTION1,PERCENT1,SCORE2,SECTION2,PERCENT2,SCORE3,SECTION3"
            + ",PERCENT3,SCORE4,SECTION4,PERCENT4,SCORE5,SECTION5,PERCENT5,SCORE6,SECTION6"
            + ",PERCENT6,COMPOSITE,TOTAL,INTERPRETATION FROM ASSESS_SCORES WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM ASSESS_SCORES WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the assess_scores table. */
    private final String INSERT_QUERY = "INSERT INTO ASSESS_SCORES(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,DATE_TAKEN,SCORE1,SECTION1,PERCENT1,SCORE2,SECTION2,PERCENT2,SCORE3"
            + ",SECTION3,PERCENT3,SCORE4,SECTION4,PERCENT4,SCORE5,SECTION5,PERCENT5,SCORE6"
            + ",SECTION6,PERCENT6,COMPOSITE,TOTAL,INTERPRETATION) VALUES (?,?,"
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
            + ",?,?,?,?)";

    /** The query to INSERT a new row into the assess_scores table. */
    private final String UPDATE_QUERY = "UPDATE ASSESS_SCORES SET MODIFIED_DATE = " + sysdate
            + ",ENTRY_NAME = ?,DATE_TAKEN = ?,SCORE1 = ?,SECTION1 = ?,PERCENT1 = ?,SCORE2 = ?,SECTION2 = ?"
            + ",PERCENT2 = ?,SCORE3 = ?,SECTION3 = ?,PERCENT3 = ?,SCORE4 = ?,SECTION4 = ?,PERCENT4 = ?"
            + ",SCORE5 = ?,SECTION5 = ?,PERCENT5 = ?,SCORE6 = ?,SECTION6 = ?,PERCENT6 = ?,COMPOSITE = ?"
            + ",TOTAL = ?,INTERPRETATION = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM ASSESS_SCORES WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
		// SELECT_QUERY_SINGLE_COUNT
		
        boolean assess_scores = false;
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
            		assess_scores = true;
            	}
            } else {
            	assess_scores = false;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return assess_scores;
	}
}

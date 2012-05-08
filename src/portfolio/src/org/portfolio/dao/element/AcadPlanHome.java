/* $Name:  $ */
/* $Id: AcadPlanHome.java,v 1.10 2010/11/09 14:53:05 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/AcadPlanHome.java,v 1.10 2010/11/09 14:53:05 ajokela Exp $
 * $Revision: 1.10 $
 * $Date: 2010/11/09 14:53:05 $
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
import org.portfolio.model.AcadPlan;
import org.portfolio.model.ElementDataObject;

public class AcadPlanHome extends AbstractElementHome {
	
    public void store(ElementDataObject data) {
        AcadPlan acad_plan = (AcadPlan) data;
        if (acad_plan.isNew()) {
            insert(acad_plan);
        } else {
            update(acad_plan);
        }
    }

    public void remove(ElementDataObject data) {
        AcadPlan acad_plan = (AcadPlan) data;
        delete(acad_plan);
    }

    public List<AcadPlan> findByPersonId(String personId) {
        List<AcadPlan> elements = new ArrayList<AcadPlan>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                elements.add(createAcadPlan(rs));
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return elements;
    }

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {

    	AcadPlan acadPlan = null;
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
                acadPlan = createAcadPlan(rs);
            } else {
            	acadPlan = null;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }
        return acadPlan;
        
    }

    private AcadPlan createAcadPlan(ResultSet rs) throws SQLException {
        AcadPlan acad_plan = new AcadPlan();
        acad_plan.setPersonId(rs.getString("PERSON_ID"));
        acad_plan.setEntryId(rs.getBigDecimal("ENTRY_ID"));
        acad_plan.setDateCreated(rs.getDate("DATE_CREATED"));
        acad_plan.setModifiedDate(rs.getDate("MODIFIED_DATE"));
        acad_plan.setEntryName(rs.getString("ENTRY_NAME"));
        acad_plan.setClass1(rs.getString("CLASS1"));
        acad_plan.setSubject1(rs.getString("SUBJECT1"));
        acad_plan.setCourse1(rs.getString("COURSE1"));
        acad_plan.setTitle1(rs.getString("TITLE1"));
        acad_plan.setCredits1(rs.getString("CREDITS1"));
        acad_plan.setClass2(rs.getString("CLASS2"));
        acad_plan.setSubject2(rs.getString("SUBJECT2"));
        acad_plan.setCourse2(rs.getString("COURSE2"));
        acad_plan.setTitle2(rs.getString("TITLE2"));
        acad_plan.setCredits2(rs.getString("CREDITS2"));
        acad_plan.setClass3(rs.getString("CLASS3"));
        acad_plan.setSubject3(rs.getString("SUBJECT3"));
        acad_plan.setCourse3(rs.getString("COURSE3"));
        acad_plan.setTitle3(rs.getString("TITLE3"));
        acad_plan.setCredits3(rs.getString("CREDITS3"));
        acad_plan.setClass4(rs.getString("CLASS4"));
        acad_plan.setSubject4(rs.getString("SUBJECT4"));
        acad_plan.setCourse4(rs.getString("COURSE4"));
        acad_plan.setTitle4(rs.getString("TITLE4"));
        acad_plan.setCredits4(rs.getString("CREDITS4"));
        acad_plan.setClass5(rs.getString("CLASS5"));
        acad_plan.setSubject5(rs.getString("SUBJECT5"));
        acad_plan.setCourse5(rs.getString("COURSE5"));
        acad_plan.setTitle5(rs.getString("TITLE5"));
        acad_plan.setCredits5(rs.getString("CREDITS5"));
        acad_plan.setClass6(rs.getString("CLASS6"));
        acad_plan.setSubject6(rs.getString("SUBJECT6"));
        acad_plan.setCourse6(rs.getString("COURSE6"));
        acad_plan.setTitle6(rs.getString("TITLE6"));
        acad_plan.setCredits6(rs.getString("CREDITS6"));
        acad_plan.setClass7(rs.getString("CLASS7"));
        acad_plan.setSubject7(rs.getString("SUBJECT7"));
        acad_plan.setCourse7(rs.getString("COURSE7"));
        acad_plan.setTitle7(rs.getString("TITLE7"));
        acad_plan.setCredits7(rs.getString("CREDITS7"));
        acad_plan.setComments(rs.getString("COMMENTS"));
        return acad_plan;
    }

    protected void update(AcadPlan acad_plan) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            String sql = "UPDATE ACAD_PLAN SET MODIFIED_DATE = " + sysdate
                    + ",ENTRY_NAME = ?,CLASS1 = ?,SUBJECT1 = ?,COURSE1 = ?,TITLE1 = ?,CREDITS1 = ?,CLASS2 = ?"
                    + ",SUBJECT2 = ?,COURSE2 = ?,TITLE2 = ?,CREDITS2 = ?,CLASS3 = ?,SUBJECT3 = ?,COURSE3 = ?"
                    + ",TITLE3 = ?,CREDITS3 = ?,CLASS4 = ?,SUBJECT4 = ?,COURSE4 = ?,TITLE4 = ?,CREDITS4 = ?"
                    + ",CLASS5 = ?,SUBJECT5 = ?,COURSE5 = ?,TITLE5 = ?,CREDITS5 = ?,CLASS6 = ?,SUBJECT6 = ?"
                    + ",COURSE6 = ?,TITLE6 = ?,CREDITS6 = ?,CLASS7 = ?,SUBJECT7 = ?,COURSE7 = ?,TITLE7 = ?"
                    + ",CREDITS7 = ?,COMMENTS = ? WHERE PERSON_ID=? AND ENTRY_ID=?";
            logService.debug("sql: " + sql);
            ps = conn.prepareStatement(sql);
            ps.setString(1, acad_plan.getEntryName());
            ps.setString(2, acad_plan.getClass1());
            ps.setString(3, acad_plan.getSubject1());
            ps.setString(4, acad_plan.getCourse1());
            ps.setString(5, acad_plan.getTitle1());
            ps.setString(6, acad_plan.getCredits1());
            ps.setString(7, acad_plan.getClass2());
            ps.setString(8, acad_plan.getSubject2());
            ps.setString(9, acad_plan.getCourse2());
            ps.setString(10, acad_plan.getTitle2());
            ps.setString(11, acad_plan.getCredits2());
            ps.setString(12, acad_plan.getClass3());
            ps.setString(13, acad_plan.getSubject3());
            ps.setString(14, acad_plan.getCourse3());
            ps.setString(15, acad_plan.getTitle3());
            ps.setString(16, acad_plan.getCredits3());
            ps.setString(17, acad_plan.getClass4());
            ps.setString(18, acad_plan.getSubject4());
            ps.setString(19, acad_plan.getCourse4());
            ps.setString(20, acad_plan.getTitle4());
            ps.setString(21, acad_plan.getCredits4());
            ps.setString(22, acad_plan.getClass5());
            ps.setString(23, acad_plan.getSubject5());
            ps.setString(24, acad_plan.getCourse5());
            ps.setString(25, acad_plan.getTitle5());
            ps.setString(26, acad_plan.getCredits5());
            ps.setString(27, acad_plan.getClass6());
            ps.setString(28, acad_plan.getSubject6());
            ps.setString(29, acad_plan.getCourse6());
            ps.setString(30, acad_plan.getTitle6());
            ps.setString(31, acad_plan.getCredits6());
            ps.setString(32, acad_plan.getClass7());
            ps.setString(33, acad_plan.getSubject7());
            ps.setString(34, acad_plan.getCourse7());
            ps.setString(35, acad_plan.getTitle7());
            ps.setString(36, acad_plan.getCredits7());
            ps.setString(37, acad_plan.getComments());
            ps.setString(38, acad_plan.getPersonId());
            ps.setBigDecimal(39, acad_plan.getEntryId());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void insert(AcadPlan acad_plan) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, acad_plan.getPersonId());
            ps.setInt(2, id);
            acad_plan.setEntryId(String.valueOf(id));
            ps.setString(3, acad_plan.getEntryName());
            ps.setString(4, acad_plan.getClass1());
            ps.setString(5, acad_plan.getSubject1());
            ps.setString(6, acad_plan.getCourse1());
            ps.setString(7, acad_plan.getTitle1());
            ps.setString(8, acad_plan.getCredits1());
            ps.setString(9, acad_plan.getClass2());
            ps.setString(10, acad_plan.getSubject2());
            ps.setString(11, acad_plan.getCourse2());
            ps.setString(12, acad_plan.getTitle2());
            ps.setString(13, acad_plan.getCredits2());
            ps.setString(14, acad_plan.getClass3());
            ps.setString(15, acad_plan.getSubject3());
            ps.setString(16, acad_plan.getCourse3());
            ps.setString(17, acad_plan.getTitle3());
            ps.setString(18, acad_plan.getCredits3());
            ps.setString(19, acad_plan.getClass4());
            ps.setString(20, acad_plan.getSubject4());
            ps.setString(21, acad_plan.getCourse4());
            ps.setString(22, acad_plan.getTitle4());
            ps.setString(23, acad_plan.getCredits4());
            ps.setString(24, acad_plan.getClass5());
            ps.setString(25, acad_plan.getSubject5());
            ps.setString(26, acad_plan.getCourse5());
            ps.setString(27, acad_plan.getTitle5());
            ps.setString(28, acad_plan.getCredits5());
            ps.setString(29, acad_plan.getClass6());
            ps.setString(30, acad_plan.getSubject6());
            ps.setString(31, acad_plan.getCourse6());
            ps.setString(32, acad_plan.getTitle6());
            ps.setString(33, acad_plan.getCredits6());
            ps.setString(34, acad_plan.getClass7());
            ps.setString(35, acad_plan.getSubject7());
            ps.setString(36, acad_plan.getCourse7());
            ps.setString(37, acad_plan.getTitle7());
            ps.setString(38, acad_plan.getCredits7());
            ps.setString(39, acad_plan.getComments());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void delete(AcadPlan acadPlan) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, acadPlan.getPersonId());
            ps.setBigDecimal(2, acadPlan.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,CLASS1,SUBJECT1,COURSE1,TITLE1,CREDITS1,CLASS2,SUBJECT2,COURSE2"
            + ",TITLE2,CREDITS2,CLASS3,SUBJECT3,COURSE3,TITLE3,CREDITS3,CLASS4,SUBJECT4"
            + ",COURSE4,TITLE4,CREDITS4,CLASS5,SUBJECT5,COURSE5,TITLE5,CREDITS5,CLASS6"
            + ",SUBJECT6,COURSE6,TITLE6,CREDITS6,CLASS7,SUBJECT7,COURSE7,TITLE7,CREDITS7,COMMENTS FROM ACAD_PLAN WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,CLASS1,SUBJECT1,COURSE1,TITLE1,CREDITS1,CLASS2,SUBJECT2,COURSE2"
            + ",TITLE2,CREDITS2,CLASS3,SUBJECT3,COURSE3,TITLE3,CREDITS3,CLASS4,SUBJECT4"
            + ",COURSE4,TITLE4,CREDITS4,CLASS5,SUBJECT5,COURSE5,TITLE5,CREDITS5,CLASS6"
            + ",SUBJECT6,COURSE6,TITLE6,CREDITS6,CLASS7,SUBJECT7,COURSE7,TITLE7,CREDITS7"
            + ",COMMENTS FROM ACAD_PLAN WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM ACAD_PLAN WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the acad_plan table. */
    private final String INSERT_QUERY = "INSERT INTO ACAD_PLAN(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,CLASS1,SUBJECT1,COURSE1,TITLE1,CREDITS1,CLASS2,SUBJECT2,COURSE2"
            + ",TITLE2,CREDITS2,CLASS3,SUBJECT3,COURSE3,TITLE3,CREDITS3,CLASS4,SUBJECT4"
            + ",COURSE4,TITLE4,CREDITS4,CLASS5,SUBJECT5,COURSE5,TITLE5,CREDITS5,CLASS6"
            + ",SUBJECT6,COURSE6,TITLE6,CREDITS6,CLASS7,SUBJECT7,COURSE7,TITLE7,CREDITS7,COMMENTS) VALUES (?,?,"
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
            + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ",?,?)";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM ACAD_PLAN WHERE PERSON_ID = ? AND ENTRY_ID = ?";
	
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {

    	boolean acadPlan = false;
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
            		acadPlan = true;
            	}
            	
            } else {
            	acadPlan = false;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }
        return acadPlan;

		
	}
}

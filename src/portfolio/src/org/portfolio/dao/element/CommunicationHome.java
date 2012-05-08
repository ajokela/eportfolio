/* $Name:  $ */
/* $Id: CommunicationHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
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
import org.portfolio.model.Communication;
import org.portfolio.model.ElementDataObject;

public class CommunicationHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        if (!(data instanceof Communication)) {
        } else {
            Communication communication = (Communication) data;
            logService.debug("Is instance new?" + communication.isNew());
            if (communication.isNew()) {
                logService.debug("inserting new instance");
                insert(communication);
            } else {
                logService.debug("Updating instance.");
                update(communication);
            }
        }
    }

    public void remove(ElementDataObject data) {
        if (!(data instanceof Communication)) {
        } else {
            Communication communication = (Communication) data;
            delete(communication);
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
    public List<Communication> findByPersonId(String personId) {
        List<Communication> elements = new ArrayList<Communication>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Communication communication = new Communication();
                communication.setPersonId(rs.getString("PERSON_ID"));
                communication.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                communication.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                communication.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                communication.setEntryName(rs.getString("ENTRY_NAME"));
                communication.setExpository(rs.getString("EXPOSITORY"));
                communication.setCreative(rs.getString("CREATIVE"));
                communication.setDiscipline(rs.getString("DISCIPLINE"));
                communication.setOneOnOne(rs.getString("ONE_ON_ONE"));
                communication.setSmallGroup(rs.getString("SMALL_GROUP"));
                communication.setFacilitation(rs.getString("FACILITATION"));
                communication.setPublicSpeaking(rs.getString("PUBLIC_SPEAKING"));
                communication.setListening(rs.getString("LISTENING"));
                communication.setConflictRes(rs.getString("CONFLICT_RES"));
                // logService.debug("Adding instance " + communication + " to the elements list.");
                elements.add(communication);
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
        Communication communication = new Communication();
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
                communication.setPersonId(rs.getString("PERSON_ID"));
                communication.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                communication.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                communication.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                communication.setEntryName(rs.getString("ENTRY_NAME"));
                communication.setExpository(rs.getString("EXPOSITORY"));
                communication.setCreative(rs.getString("CREATIVE"));
                communication.setDiscipline(rs.getString("DISCIPLINE"));
                communication.setOneOnOne(rs.getString("ONE_ON_ONE"));
                communication.setSmallGroup(rs.getString("SMALL_GROUP"));
                communication.setFacilitation(rs.getString("FACILITATION"));
                communication.setPublicSpeaking(rs.getString("PUBLIC_SPEAKING"));
                communication.setListening(rs.getString("LISTENING"));
                communication.setConflictRes(rs.getString("CONFLICT_RES"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return communication;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Communication.
     * 
     * @param communication the instance to update. @ if there is a problem
     *            updating.
     */
    protected void update(Communication communication) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, communication.getEntryName());
            ps.setString(2, communication.getExpository());
            ps.setString(3, communication.getCreative());
            ps.setString(4, communication.getDiscipline());
            ps.setString(5, communication.getOneOnOne());
            ps.setString(6, communication.getSmallGroup());
            ps.setString(7, communication.getFacilitation());
            ps.setString(8, communication.getPublicSpeaking());
            ps.setString(9, communication.getListening());
            ps.setString(10, communication.getConflictRes());
            ps.setString(11, communication.getPersonId());
            ps.setBigDecimal(12, communication.getEntryId());
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
     * guarantee that the data object is of type Communication.
     * 
     * @param communication the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(Communication communication) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (communication.isRemote()) ? communication.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, communication.getPersonId());
            ps.setInt(2, id);
            communication.setEntryId(String.valueOf(id));
            ps.setString(3, communication.getEntryName());
            ps.setString(4, communication.getExpository());
            ps.setString(5, communication.getCreative());
            ps.setString(6, communication.getDiscipline());
            ps.setString(7, communication.getOneOnOne());
            ps.setString(8, communication.getSmallGroup());
            ps.setString(9, communication.getFacilitation());
            ps.setString(10, communication.getPublicSpeaking());
            ps.setString(11, communication.getListening());
            ps.setString(12, communication.getConflictRes());
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
    protected void delete(Communication communication) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, communication.getPersonId());
            ps.setBigDecimal(2, communication.getEntryId());
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
            + ",EXPOSITORY,CREATIVE,DISCIPLINE,ONE_ON_ONE,SMALL_GROUP,FACILITATION,PUBLIC_SPEAKING"
            + ",LISTENING,CONFLICT_RES FROM COMMUNICATION WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",EXPOSITORY,CREATIVE,DISCIPLINE,ONE_ON_ONE,SMALL_GROUP,FACILITATION,PUBLIC_SPEAKING"
            + ",LISTENING,CONFLICT_RES FROM COMMUNICATION WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM COMMUNICATION WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the communication table. */
    private final String INSERT_QUERY = "INSERT INTO COMMUNICATION(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,EXPOSITORY,CREATIVE,DISCIPLINE,ONE_ON_ONE,SMALL_GROUP,FACILITATION"
            + ",PUBLIC_SPEAKING,LISTENING,CONFLICT_RES) VALUES (?,?," + sysdate + "," + sysdate + ",?,?,?,?,?,?,?,?,?,?)";

    /** The query to INSERT a new row into the communication table. */
    private final String UPDATE_QUERY = "UPDATE COMMUNICATION SET MODIFIED_DATE = " + sysdate
            + ",ENTRY_NAME = ?,EXPOSITORY = ?,CREATIVE = ?,DISCIPLINE = ?,ONE_ON_ONE = ?,SMALL_GROUP = ?"
            + ",FACILITATION = ?,PUBLIC_SPEAKING = ?,LISTENING = ?,CONFLICT_RES = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM COMMUNICATION  WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

/* $Name:  $ */
/* $Id: AdditionalPhotoHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.model.AdditionalPhoto;
import org.portfolio.model.ElementDataObject;

/**
 * Handles the persistence of the InformationSkills data element. <br />
 * 
 * @author Raji Srinivasan, University of Minnesota
 * @since 1.0
 * @version $Revision: 1.8 $
 */

public class AdditionalPhotoHome extends AbstractElementHome {

    /**
     * Persists the AdditionalPhoto Object. Will perform an INSERT if it is a
     * new instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the AdditionalPhoto object to persist
     */
    public void store(ElementDataObject data) {
        AdditionalPhoto additional_photo = (AdditionalPhoto) data;
        logService.debug("Is instance new?" + additional_photo.isNew());
        if (additional_photo.isNew()) {
            logService.debug("inserting new instance");
            insert(additional_photo);
        } else {
            logService.debug("Updating instance " + additional_photo);
            update(additional_photo);
        }
    }

    /**
     * Deletes the instance of $table.destinationClassNameHome.
     * 
     * @param data the $table.destinationClassNameHome object to persist
     */
    public void remove(ElementDataObject data) {
        AdditionalPhoto additional_photo = (AdditionalPhoto) data;
        delete(additional_photo);
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
    public List<AdditionalPhoto> findByPersonId(String personId) {
        List<AdditionalPhoto> elements = new ArrayList<AdditionalPhoto>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                AdditionalPhoto additional_photo = new AdditionalPhoto();
                additional_photo.setPersonId(rs.getString("PERSON_ID"));
                additional_photo.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                additional_photo.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                additional_photo.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                additional_photo.setEntryName(rs.getString("ENTRY_NAME"));
                // additional_photo.setTypeOfPhoto(rs.getString("PHOTO_TYPE"));
                additional_photo.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                additional_photo.setAdditionalInformation(rs.getString("ADDITIONAL_INFO"));
                // logService.debug("Adding instance " + additional_photo + " to the elements list.");
                elements.add(additional_photo);
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
        AdditionalPhoto additional_photo = new AdditionalPhoto();
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
                additional_photo.setEntryId(entryId);
                additional_photo.setPersonId(rs.getString("PERSON_ID"));
                additional_photo.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                additional_photo.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                additional_photo.setEntryName(rs.getString("ENTRY_NAME"));
                // additional_photo.setTypeOfPhoto(rs.getString("PHOTO_TYPE"));
                additional_photo.setDateTaken(rs.getTimestamp("DATE_TAKEN"));
                additional_photo.setAdditionalInformation(rs.getString("ADDITIONAL_INFO"));
            } else {
            	additional_photo = null;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return additional_photo;
    }

    protected void update(AdditionalPhoto additional_photo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, additional_photo.getEntryName());
            if (additional_photo.getDateTaken() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new java.sql.Timestamp(additional_photo.getDateTaken().getTime()));
            ps.setString(3, additional_photo.getAdditionalInformation());
            ps.setString(4, additional_photo.getPersonId());
            ps.setBigDecimal(5, additional_photo.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void insert(AdditionalPhoto additional_photo) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (additional_photo.isRemote()) ? additional_photo.getEntryId().intValue() : sequenceGenerator
                .getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, additional_photo.getPersonId());
            ps.setInt(2, id);

            // Not sure why it's commented out - it's the reason for the
            // NullPointerException when inserting
            // Uncommented it out -ZL
            additional_photo.setEntryId(String.valueOf(id));

            ps.setString(3, additional_photo.getEntryName());
            logService.debug("date taken raji" + additional_photo.getDateTaken());
            if (additional_photo.getDateTaken() == null)
                ps.setNull(4, Types.TIMESTAMP);
            else
                ps.setTimestamp(4, new java.sql.Timestamp(additional_photo.getDateTaken().getTime()));
            ps.setString(5, additional_photo.getAdditionalInformation());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void delete(AdditionalPhoto additional_photo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            logService.debug("DELETE_QUERY  : \n" + DELETE_QUERY);
            logService.debug("deleting with personId  = " + additional_photo.getPersonId() + " and ENTRY_ID = "
                    + additional_photo.getEntryId());
            ps.setString(1, additional_photo.getPersonId());
            ps.setBigDecimal(2, additional_photo.getEntryId());
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
     * The query to select ALL relevant instances of additional_photo for a
     * person.
     */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",DATE_TAKEN,ADDITIONAL_INFO FROM additional_photo WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME,DATE_TAKEN"
            + ",ADDITIONAL_INFO FROM additional_photo WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(PERSON_ID) FROM additional_photo WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the additional_photo table. */
    private final String INSERT_QUERY = "INSERT INTO additional_photo(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,DATE_TAKEN,ADDITIONAL_INFO) VALUES (?,?," + sysdate + "," + sysdate + "," + "?,?,?)";

    /** The query to UPDATE a row in the additional_photo table. */
    private final String UPDATE_QUERY = "UPDATE additional_photo SET MODIFIED_DATE = " + sysdate
            + ",ENTRY_NAME = ?,DATE_TAKEN = ?,ADDITIONAL_INFO = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM additional_photo  WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
		
        boolean additional_photo = false;
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
            		additional_photo = true;
            	}
            } else {
            	additional_photo = false;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

		
		return additional_photo;
	}
}

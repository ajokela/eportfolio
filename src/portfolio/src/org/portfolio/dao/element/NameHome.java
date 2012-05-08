/* $Name:  $ */
/* $Id: NameHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/NameHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
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

import org.portfolio.dao.AbstractElementHome;
import org.portfolio.dao.DataAccessException;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Name;

/**
 * Handles the persistence of the Name data element. <br />
 * 
 * @author Jack Brown
 * @author John Bush
 * @since 1.0
 * @version $Revision: 1.8 $
 */

public class NameHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        Name name = (Name) data;
        if (name.isNew()) {
            insert(name);
        } else {
            update(name);
        }
    }

    public void remove(ElementDataObject data) {
        if (!(data instanceof Name)) {
        } else {
            Name name = (Name) data;
            delete(name);
        }
    }

    public List<Name> findByPersonId(String personId) {
        List<Name> elements = new ArrayList<Name>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Name name = new Name();
                name.setPersonId(rs.getString("PERSON_ID"));
                name.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                name.setEntryName(rs.getString("ENTRY_NAME"));
                name.setFirstName(rs.getString("FIRST_NAME"));
                name.setMiddleName(rs.getString("MIDDLE_NAME"));
                name.setLastName(rs.getString("LAST_NAME"));
                name.setTitle(rs.getString("TITLE"));
                name.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                name.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                // logService.debug("Adding instance " + name + " to the elements list.");
                elements.add(name);
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
        Name name = new Name();
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
                name.setPersonId(rs.getString("PERSON_ID"));
                name.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                name.setEntryName(rs.getString("ENTRY_NAME"));
                name.setFirstName(rs.getString("FIRST_NAME"));
                name.setMiddleName(rs.getString("MIDDLE_NAME"));
                name.setLastName(rs.getString("LAST_NAME"));
                name.setTitle(rs.getString("TITLE"));
                name.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                name.setDateCreated(rs.getTimestamp("DATE_CREATED"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return name;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Name.
     * 
     * @param name the instance to update. @ if there is a problem updating.
     */
    protected void update(Name name) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, name.getEntryName());
            ps.setString(2, name.getFirstName());
            ps.setString(3, name.getMiddleName());
            ps.setString(4, name.getLastName());
            ps.setString(5, name.getTitle());
            ps.setString(6, name.getPersonId());
            ps.setBigDecimal(7, name.getEntryId());
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
     * guarantee that the data object is of type Name.
     * 
     * @param name the instance to create. @ if there is a problem inserting.
     */
    protected void insert(Name name) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, name.getPersonId());
            ps.setInt(2, id);
            name.setEntryId(String.valueOf(id));
            ps.setString(3, name.getEntryName());
            ps.setString(4, name.getFirstName());
            ps.setString(5, name.getMiddleName());
            ps.setString(6, name.getLastName());
            ps.setString(7, name.getTitle());
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
    protected void delete(Name name) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            logService.debug("DELETE_QUERY  : \n" + DELETE_QUERY);
            logService.debug("deleting with personId  = " + name.getPersonId() + " and entry_id = " + name.getEntryId());
            ps.setString(1, name.getPersonId());
            ps.setBigDecimal(2, name.getEntryId());
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
    // public static final String TABLE_NAME = "NAME";

    // Class Constants
    /** The query to select ALL relevant instances of NAME for a person. */
    private final String SELECT_QUERY_ALL = "SELECT PERSON_ID,ENTRY_ID,ENTRY_NAME,FIRST_NAME,MIDDLE_NAME"
            + ",LAST_NAME,TITLE,MODIFIED_DATE,DATE_CREATED FROM NAME WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,ENTRY_NAME,FIRST_NAME,MIDDLE_NAME"
            + ",LAST_NAME,TITLE,MODIFIED_DATE,DATE_CREATED FROM NAME WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM NAME WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    
    /** The query to INSERT a new row into the name table. */
    private final String INSERT_QUERY = "INSERT INTO NAME(PERSON_ID,ENTRY_ID,ENTRY_NAME,FIRST_NAME,MIDDLE_NAME"
            + ",LAST_NAME,TITLE,MODIFIED_DATE,DATE_CREATED) VALUES (?,?,?,?," + "?,?,?," + sysdate + "," + sysdate + ")";

    /** The query to INSERT a new row into the name table. */
    private final String UPDATE_QUERY = "UPDATE NAME SET ENTRY_NAME = ?,FIRST_NAME = ?,MIDDLE_NAME = ?,LAST_NAME = ?"
            + ",TITLE = ?,MODIFIED_DATE = " + sysdate + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM NAME  WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
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

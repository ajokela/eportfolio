/* $Name:  $ */
/* $Id: ServiceHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/ServiceHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.model.Service;

/**
 * Handles the persistence of the Service data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.9 $
 */

public class ServiceHome extends AbstractElementHome {
    /**
     * Persists the Service Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the Service object to persist @ if a data type other than the
     *            one expected is found. @ if there are any problems persisting
     *            the data.
     */
    public void store(ElementDataObject data) {
        if (!(data instanceof Service)) {
        } else {
            Service service = (Service) data;
            logService.debug("Is instance new?" + service.isNew());
            if (service.isNew()) {
                logService.debug("inserting new instance");
                insert(service);
            } else {
                logService.debug("Updating instance.");
                update(service);
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
        if (!(data instanceof Service)) {
        } else {
            Service service = (Service) data;
            delete(service);
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
    public List<Service> findByPersonId(String personId) {
        List<Service> elements = new ArrayList<Service>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Service service = new Service();
                service.setPersonId(rs.getString("PERSON_ID"));
                service.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                service.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                service.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                service.setEntryName(rs.getString("ENTRY_NAME"));
                service.setOrganization(rs.getString("ORGANIZATION"));
                service.setSupervisor(rs.getString("SUPERVISOR"));
                service.setStreet1(rs.getString("STREET1"));
                service.setStreet2(rs.getString("STREET2"));
                service.setCity(rs.getString("CITY"));
                service.setState(rs.getString("STATE"));
                service.setZip(rs.getString("ZIP"));
                service.setCountry(rs.getString("COUNTRY"));
                service.setPhone(rs.getString("PHONE"));
                service.setEmail(rs.getString("EMAIL"));
                service.setFax(rs.getString("FAX"));
                service.setStartDate(rs.getTimestamp("START_DATE"));
                service.setEndDate(rs.getTimestamp("END_DATE"));
                service.setHours(rs.getString("HOURS"));
                service.setDescription(rs.getString("DESCRIPTION"));
                service.setComments(rs.getString("COMMENTS"));
                // logService.debug("Adding instance " + service + " to the elements list.");
                elements.add(service);
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
        Service service = new Service();
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
                service.setPersonId(rs.getString("PERSON_ID"));
                service.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                service.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                service.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                service.setEntryName(rs.getString("ENTRY_NAME"));
                service.setOrganization(rs.getString("ORGANIZATION"));
                service.setSupervisor(rs.getString("SUPERVISOR"));
                service.setStreet1(rs.getString("STREET1"));
                service.setStreet2(rs.getString("STREET2"));
                service.setCity(rs.getString("CITY"));
                service.setState(rs.getString("STATE"));
                service.setZip(rs.getString("ZIP"));
                service.setCountry(rs.getString("COUNTRY"));
                service.setPhone(rs.getString("PHONE"));
                service.setEmail(rs.getString("EMAIL"));
                service.setFax(rs.getString("FAX"));
                service.setStartDate(rs.getTimestamp("START_DATE"));
                service.setEndDate(rs.getTimestamp("END_DATE"));
                service.setHours(rs.getString("HOURS"));
                service.setDescription(rs.getString("DESCRIPTION"));
                service.setComments(rs.getString("COMMENTS"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return service;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type Service.
     * 
     * @param service the instance to update. @ if there is a problem updating.
     */
    protected void update(Service service) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, service.getEntryName());
            ps.setString(2, service.getOrganization());
            ps.setString(3, service.getSupervisor());
            ps.setString(4, service.getStreet1());
            ps.setString(5, service.getStreet2());
            ps.setString(6, service.getCity());
            ps.setString(7, service.getState());
            ps.setString(8, service.getZip());
            ps.setString(9, service.getCountry());
            ps.setString(10, service.getPhone());
            ps.setString(11, service.getEmail());
            ps.setString(12, service.getFax());
            if (service.getStartDate() == null)
                ps.setNull(13, Types.TIMESTAMP);
            else
                ps.setTimestamp(13, new java.sql.Timestamp(service.getStartDate().getTime()));
            if (service.getEndDate() == null)
                ps.setNull(14, Types.TIMESTAMP);
            else
                ps.setTimestamp(14, new java.sql.Timestamp(service.getEndDate().getTime()));
            ps.setString(15, service.getHours());
            ps.setString(16, service.getDescription());
            ps.setString(17, service.getComments());
            ps.setString(18, service.getPersonId());
            ps.setBigDecimal(19, service.getEntryId());
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
     * guarantee that the data object is of type Service.
     * 
     * @param service the instance to create. @ if there is a problem inserting.
     */
    protected void insert(Service service) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, service.getPersonId());
            ps.setInt(2, id);
            service.setEntryId(String.valueOf(id));
            ps.setString(3, service.getEntryName());
            ps.setString(4, service.getOrganization());
            ps.setString(5, service.getSupervisor());
            ps.setString(6, service.getStreet1());
            ps.setString(7, service.getStreet2());
            ps.setString(8, service.getCity());
            ps.setString(9, service.getState());
            ps.setString(10, service.getZip());
            ps.setString(11, service.getCountry());
            ps.setString(12, service.getPhone());
            ps.setString(13, service.getEmail());
            ps.setString(14, service.getFax());
            if (service.getStartDate() == null)
                ps.setNull(15, Types.TIMESTAMP);
            else
                ps.setTimestamp(15, new java.sql.Timestamp(service.getStartDate().getTime()));
            if (service.getEndDate() == null)
                ps.setNull(16, Types.TIMESTAMP);
            else
                ps.setTimestamp(16, new java.sql.Timestamp(service.getEndDate().getTime()));
            ps.setString(17, service.getHours());
            ps.setString(18, service.getDescription());
            ps.setString(19, service.getComments());
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
    protected void delete(Service service) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, service.getPersonId());
            ps.setBigDecimal(2, service.getEntryId());
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
            + ",ORGANIZATION" + ",SUPERVISOR" + ",STREET1" + ",STREET2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",PHONE" + ",EMAIL"
            + ",FAX" + ",START_DATE" + ",END_DATE" + ",HOURS" + ",DESCRIPTION" + ",COMMENTS" + " FROM SERVICE WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT " + "PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE" + ",ENTRY_NAME"
            + ",ORGANIZATION" + ",SUPERVISOR" + ",STREET1" + ",STREET2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY" + ",PHONE" + ",EMAIL"
            + ",FAX" + ",START_DATE" + ",END_DATE" + ",HOURS" + ",DESCRIPTION" + ",COMMENTS"
            + " FROM SERVICE WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM SERVICE WHERE PERSON_ID = ? AND ENTRY_ID = ?";
    
    /** The query to INSERT a new row into the service table. */
    private final String INSERT_QUERY = "INSERT INTO SERVICE" + "(PERSON_ID" + ",ENTRY_ID" + ",DATE_CREATED" + ",MODIFIED_DATE"
            + ",ENTRY_NAME" + ",ORGANIZATION" + ",SUPERVISOR" + ",STREET1" + ",STREET2" + ",CITY" + ",STATE" + ",ZIP" + ",COUNTRY"
            + ",PHONE" + ",EMAIL" + ",FAX" + ",START_DATE" + ",END_DATE" + ",HOURS" + ",DESCRIPTION" + ",COMMENTS" + ") VALUES (" + "?"
            + "," + "?" + ","
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
            + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + "," + "?" + ")";

    /** The query to INSERT a new row into the service table. */
    private final String UPDATE_QUERY = "UPDATE SERVICE SET " + "MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?" + ",ORGANIZATION = ?"
            + ",SUPERVISOR = ?" + ",STREET1 = ?" + ",STREET2 = ?" + ",CITY = ?" + ",STATE = ?" + ",ZIP = ?" + ",COUNTRY = ?" + ",PHONE = ?"
            + ",EMAIL = ?" + ",FAX = ?" + ",START_DATE = ?" + ",END_DATE = ?" + ",HOURS = ?" + ",DESCRIPTION = ?" + ",COMMENTS = ?"
            + " WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM SERVICE " + " WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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

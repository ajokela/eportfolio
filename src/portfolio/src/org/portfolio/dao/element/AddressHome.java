/* $Name:  $ */
/* $Id: AddressHome.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $ */
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
import org.portfolio.model.Address;
import org.portfolio.model.ElementDataObject;

public class AddressHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        Address address = (Address) data;
        if (address.isNew()) {
            insert(address);
        } else {
            update(address);
        }
    }

    public void remove(ElementDataObject data) {
        Address address = (Address) data;
        delete(address);
    }

    public List<Address> findByPersonId(String personId) {
        List<Address> elements = new ArrayList<Address>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT PERSON_ID,ENTRY_ID,ENTRY_NAME,STREET1,STREET2"
                    + ",CITY,STATE,ZIP,COUNTRY,MODIFIED_DATE,DATE_CREATED FROM ADDRESS WHERE PERSON_ID = ?");
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setPersonId(rs.getString("PERSON_ID"));
                address.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                address.setEntryName(rs.getString("ENTRY_NAME"));
                address.setStreet1(rs.getString("STREET1"));
                address.setStreet2(rs.getString("STREET2"));
                address.setCity(rs.getString("CITY"));
                address.setState(rs.getString("STATE"));
                address.setZip(rs.getString("ZIP"));
                address.setCountry(rs.getString("COUNTRY"));
                address.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                address.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                // logService.debug("Adding instance " + address + " to the elements list.");
                elements.add(address);
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return elements;
    }

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
        Address address = new Address();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT PERSON_ID,ENTRY_ID,ENTRY_NAME,STREET1,STREET2"
                    + ",CITY,STATE,ZIP,COUNTRY,MODIFIED_DATE,DATE_CREATED" + " FROM ADDRESS WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
                address.setPersonId(rs.getString("PERSON_ID"));
                address.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                address.setEntryName(rs.getString("ENTRY_NAME"));
                address.setStreet1(rs.getString("STREET1"));
                address.setStreet2(rs.getString("STREET2"));
                address.setCity(rs.getString("CITY"));
                address.setState(rs.getString("STATE"));
                address.setZip(rs.getString("ZIP"));
                address.setCountry(rs.getString("COUNTRY"));
                address.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                address.setDateCreated(rs.getTimestamp("DATE_CREATED"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return address;
    }

    protected void update(Address address) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("UPDATE ADDRESS SET ENTRY_NAME = ?,STREET1 = ?,STREET2 = ?,CITY = ?"
                    + ",STATE = ?,ZIP = ?,COUNTRY = ?,MODIFIED_DATE = " + sysdate + " WHERE PERSON_ID=? AND ENTRY_ID=?");
            ps.setString(1, address.getEntryName());
            ps.setString(2, address.getStreet1());
            ps.setString(3, address.getStreet2());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getState());
            ps.setString(6, address.getZip());
            ps.setString(7, address.getCountry());
            ps.setString(8, address.getPersonId());
            ps.setBigDecimal(9, address.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void insert(Address address) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {
            conn = getDataSource().getConnection();
            ps = conn
                    .prepareStatement("INSERT INTO ADDRESS(PERSON_ID,ENTRY_ID,ENTRY_NAME,STREET1,STREET2"
                            + ",CITY,STATE,ZIP,COUNTRY,MODIFIED_DATE,DATE_CREATED) VALUES (?,?,?" + ",?,?,?,?,?,?," + sysdate + ","
                            + sysdate + ")");
            ps.setString(1, address.getPersonId());
            ps.setInt(2, id);
            address.setEntryId(String.valueOf(id));
            ps.setString(3, address.getEntryName());
            ps.setString(4, address.getStreet1());
            ps.setString(5, address.getStreet2());
            ps.setString(6, address.getCity());
            ps.setString(7, address.getState());
            ps.setString(8, address.getZip());
            ps.setString(9, address.getCountry());
            
            ps.executeUpdate();
            
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void delete(Address address) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("DELETE FROM ADDRESS WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, address.getPersonId());
            ps.setBigDecimal(2, address.getEntryId());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }
	
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
        boolean address = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(ENTRY_ID) FROM ADDRESS WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
            	if(rs.getInt(1) != 0) {
            		address = true;
            	}
            } else {
            	address = false;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return address;
	}

}

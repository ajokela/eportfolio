/* $Name:  $ */
/* $Id: CarDocumentationHome.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/element/CarDocumentationHome.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.10 $
 * $Date: 2010/11/04 21:08:53 $
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
import org.portfolio.model.CarDocumentation;
import org.portfolio.model.ElementDataObject;

public class CarDocumentationHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        CarDocumentation car_documentation = (CarDocumentation) data;
        if (car_documentation.isNew()) {
            insert(car_documentation);
        } else {
            update(car_documentation);
        }
    }

    public void remove(ElementDataObject data) {
        CarDocumentation car_documentation = (CarDocumentation) data;
        delete(car_documentation);
    }

    public List<CarDocumentation> findByPersonId(String personId) {
        List<CarDocumentation> elements = new ArrayList<CarDocumentation>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
                    + ",ENTRY_NAME,TEXT FROM CAR_DOCUMENTATION WHERE PERSON_ID = ?");
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CarDocumentation car_documentation = new CarDocumentation();
                car_documentation.setPersonId(rs.getString("PERSON_ID"));
                car_documentation.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                car_documentation.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                car_documentation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                car_documentation.setEntryName(rs.getString("ENTRY_NAME"));
                car_documentation.setText(rs.getString("TEXT"));
                // logService.debug("Adding instance " + car_documentation + " to the elements list.");
                elements.add(car_documentation);
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } catch (Exception ex) {
            logService.error("Exception in findByPersonId()", ex);
        } finally {
            close(conn, ps, rs);
        }

        return elements;
    }

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
        CarDocumentation car_documentation = new CarDocumentation();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
                    + ",ENTRY_NAME,TEXT FROM CAR_DOCUMENTATION WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
                car_documentation.setPersonId(rs.getString("PERSON_ID"));
                car_documentation.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                car_documentation.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                car_documentation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                car_documentation.setEntryName(rs.getString("ENTRY_NAME"));
                car_documentation.setText(rs.getString("TEXT"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return car_documentation;
    }

    protected void update(CarDocumentation car_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("UPDATE CAR_DOCUMENTATION SET MODIFIED_DATE = " + sysdate
                    + ",ENTRY_NAME = ?,TEXT = ? WHERE PERSON_ID=? AND ENTRY_ID=?");
            ps.setString(1, car_documentation.getEntryName());
            ps.setString(2, car_documentation.getText());
            ps.setString(3, car_documentation.getPersonId());
            ps.setBigDecimal(4, car_documentation.getEntryId());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void insert(CarDocumentation car_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (car_documentation.isRemote()) ? car_documentation.getEntryId().intValue() : sequenceGenerator
                .getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("INSERT INTO CAR_DOCUMENTATION(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
                    + ",ENTRY_NAME,TEXT) VALUES (?,?," + sysdate + "," + sysdate + ",?,?)");
            ps.setString(1, car_documentation.getPersonId());
            ps.setInt(2, id);
            car_documentation.setEntryId(String.valueOf(id));
            ps.setString(3, car_documentation.getEntryName());
            ps.setString(4, car_documentation.getText());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void delete(CarDocumentation car_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("DELETE FROM CAR_DOCUMENTATION  WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, car_documentation.getPersonId());
            ps.setBigDecimal(2, car_documentation.getEntryId());
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

	
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT COUNT(ENTRY_ID) FROM CAR_DOCUMENTATION WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
                if(rs.getInt(1) != 0 ) {
                	return true;
                }
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return false;
	}
}

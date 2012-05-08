/* $Name:  $ */
/* $Id: EduDocumentationHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
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
import org.portfolio.model.EduDocumentation;
import org.portfolio.model.ElementDataObject;

public class EduDocumentationHome extends AbstractElementHome {

    public void store(ElementDataObject data) {
        if (!(data instanceof EduDocumentation)) {
        } else {
            EduDocumentation edu_documentation = (EduDocumentation) data;
            if (edu_documentation.isNew()) {
                insert(edu_documentation);
            } else {
                update(edu_documentation);
            }
        }
    }

    public void remove(ElementDataObject data) {
        if (!(data instanceof EduDocumentation)) {
        } else {
            EduDocumentation edu_documentation = (EduDocumentation) data;
            delete(edu_documentation);
        }
    }

    public List<EduDocumentation> findByPersonId(String personId) {
        List<EduDocumentation> elements = new ArrayList<EduDocumentation>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
                    + ",ENTRY_NAME,TEXT FROM EDU_DOCUMENTATION WHERE PERSON_ID = ?");
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                EduDocumentation edu_documentation = new EduDocumentation();
                edu_documentation.setPersonId(rs.getString("PERSON_ID"));
                edu_documentation.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                edu_documentation.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                edu_documentation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                edu_documentation.setEntryName(rs.getString("ENTRY_NAME"));
                edu_documentation.setText(rs.getString("TEXT"));
                // logService.debug("Adding instance " + edu_documentation + " to the elements list.");
                elements.add(edu_documentation);
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

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
        EduDocumentation edu_documentation = new EduDocumentation();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
                    + ",ENTRY_NAME,TEXT FROM EDU_DOCUMENTATION WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
                edu_documentation.setPersonId(rs.getString("PERSON_ID"));
                edu_documentation.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                edu_documentation.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                edu_documentation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                edu_documentation.setEntryName(rs.getString("ENTRY_NAME"));
                edu_documentation.setText(rs.getString("TEXT"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return edu_documentation;
    }

    protected void update(EduDocumentation edu_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("UPDATE EDU_DOCUMENTATION SET MODIFIED_DATE = " + sysdate
                    + ",ENTRY_NAME = ?,TEXT = ? WHERE PERSON_ID=? AND ENTRY_ID=?");
            ps.setString(1, edu_documentation.getEntryName());
            ps.setString(2, edu_documentation.getText());
            ps.setString(3, edu_documentation.getPersonId());
            ps.setBigDecimal(4, edu_documentation.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows updated.");
        } catch (SQLException sqle) {
            logService.error("Exception caught in update", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void insert(EduDocumentation edu_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = sequenceGenerator.getNextSequenceNumber("entry");
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("INSERT INTO EDU_DOCUMENTATION(PERSON_ID,ENTRY_ID,DATE_CREATED"
                    + ",MODIFIED_DATE,ENTRY_NAME,TEXT) VALUES (?,?," + sysdate + "," + sysdate + ",?,?)");
            ps.setString(1, edu_documentation.getPersonId());
            ps.setInt(2, id);
            edu_documentation.setEntryId(String.valueOf(id));
            ps.setString(3, edu_documentation.getEntryName());
            ps.setString(4, edu_documentation.getText());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows inserted.");
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, null);
        }
    }

    protected void delete(EduDocumentation edu_documentation) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("DELETE FROM EDU_DOCUMENTATION  WHERE PERSON_ID = ? AND ENTRY_ID = ?");
            ps.setString(1, edu_documentation.getPersonId());
            ps.setBigDecimal(2, edu_documentation.getEntryId());
            int result = ps.executeUpdate();
            logService.debug("There were " + result + " rows deleted.");
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
            ps = conn.prepareStatement("SELECT COUNT(ENTRY_ID) FROM EDU_DOCUMENTATION WHERE PERSON_ID = ? AND ENTRY_ID = ?");
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

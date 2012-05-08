/* $Name:  $ */
/* $Id: SequenceGeneratorImpl.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
/*
* $Header: /opt/UMN-src/portfolio/src/org/portfolio/util/SequenceGeneratorImpl.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $
* $Revision: 1.4 $
* $Date: 2010/10/27 19:24:57 $
*
 * =====================================================================
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
package org.portfolio.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.portfolio.dao.DataAccessException;
import org.portfolio.dao.SequenceGenerator;


/**
 * primary key generation pattern - sequence generation
 * based on EJB Design Patterns by Floyd Marinescu, pg. 106
 * only using singleton static class to access rather than ejbs
 *
 */
public class SequenceGeneratorImpl implements SequenceGenerator {

    private static LogService logService = new LogService(SequenceGeneratorImpl.class);
    
    // data members
    private Hashtable<String, Sequence> entries;         // cache of named sequences
    private int blockSize = 10;       // size of cache

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    /**
     * Inner class that encapulates a specific sequence.
     */
    private class Sequence {
        private int value = 1;

        /**
         * The current value of the sequence.
         * @return the current value
         */
        public int getValue() {
            return value;
        }
        /**
         * Set the current value of the sequence.
         * @param value the new value
         */
        public void setValue( int value ) {
            this.value = value;
        }

        public void increment () {
            this.value++;
        }
    }

    /**
     * default constructor - as this is a singleton, this MUST be private
     */
    private SequenceGeneratorImpl() {
        // let's initialize this here, not in the body.
        entries = new Hashtable<String, Sequence>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement("select * from LAST_SEQUENCE");
            rs = ps.executeQuery();
            while (rs.next()) {
                Sequence entry = new Sequence();
                String name = rs.getString(1);
                entry.setValue( rs.getInt(2) );
                entries.put(name, entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e){}
        }
    }


    /**
     * returns the next number in the named sequence from the cache in memory.
     * if the named sequence does not yet exist, it is created and set to start at 1.
     * if the named sequence does exist, but the value number in the cached sequence has already been
     * given out, then the next block in the named sequence is retrieved and cached and a number is
     * returned from this new cache.
     * <br/><br/>
     * @param name  name of the sequence.
     * <br/><br/>
     * @return next number in the named sequence
     */
    public synchronized int getNextSequenceNumber(String name) {
        int retVal = 0;
		try {
			if (entries.containsKey(name)) {
				retVal = getNextSequenceValue(name);
			} else {
				retVal = createSequence(name);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

        // System.out.println("SequenceGeneratorManager.getNextSequenceNumber("+name+")=>"
		// +retVal);

        logService.debug("SequenceGeneratorManager.getNextSequenceNumber(" + name + ")=>" + retVal);

        return retVal;
    }

    /**
     * updates the database to the next block
     * @param name  The name of the sequence
     * @param entry The entry
     * @throws SQLException
     */
    protected void refresh (String name, Sequence entry ) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // must first read in the value sequence, then refresh THAT value.
        // This is so the application can be deployed accross multiple servers.

        try {
            con = getConnection();
            ps = con.prepareStatement("Select LAST from LAST_SEQUENCE where NAME=?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            // should only be one row, assuming name is the primary key
            if ( rs.next() ) {
                int last = rs.getInt( "LAST" );
                logService.debug("The new value for " + name + " is " + last );
                entry.setValue( last );
            }
            if ( rs != null ) rs.close();
            if ( ps != null ) ps.close();

            int last = entry.getValue() + blockSize;
            logService.debug("Updating the database. New last is " + last );
            ps = con.prepareStatement("update LAST_SEQUENCE set LAST=? where NAME=?");
            // this now uses the NEW value value. protects concurrency.
            ps.setInt(1,  last );
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

    }

    /**
     * updates the sequence
     * @param name
     * @return
     * @throws SQLException
     */
    protected int getNextSequenceValue(String name) throws SQLException {

        Sequence entry = (Sequence) entries.get(name);
        int last = entry.getValue();
        logService.debug ("Testing value " + last );

        // was the value number in the sequence cache already given out?
        if ( last % blockSize == 0) {
            logService.debug("Refreshing from database.");
            refresh(name, entry );
            last = entry.getValue();
            logService.debug("New value is " + last + " after refreshing.");
        }

        entry.increment();

        return last;
    }

    /**
     * creates a new sequence row in the database
     * @param name  of the new sequence.
     * @return the value of the new sequence.
     * @throws SQLException
     */
    protected int createSequence(String name) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;


        try {
            con = getConnection();
            ps = con.prepareStatement("insert into LAST_SEQUENCE (NAME, LAST) values (?,?)");
            ps.setString(1, name);
            ps.setInt(2, blockSize );
            ps.execute();
            Sequence entry = new Sequence();
            int value = entry.getValue();
            entry.setValue( entry.getValue() + 1);
            entries.put(name, entry);
            return value;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    /**
     * determines whethere or not there is a row in the db for this name
     * @param name
     * @return
     * @throws SQLException
     */
    protected boolean findByName(String name) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean found = false;

        try {
            con = getConnection();
            ps = con.prepareStatement("select NAME from LAST_SEQUENCE where NAME=?)");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) return true;
            return found;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

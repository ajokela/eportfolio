/* $Name:  $ */
/* $Id: CarPlanHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
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
import org.portfolio.model.CarPlan;
import org.portfolio.model.ElementDataObject;

/**
 * Handles the persistence of the CarPlan data element. <br />
 * 
 * @author John Bush
 * @since 0.1
 * @version $Revision: 1.8 $
 */

public class CarPlanHome extends AbstractElementHome {

    /**
     * Persists the CarPlan Object. Will perform an INSERT if it is a new
     * instance, (i.e., there is no data created), else it will perform an
     * update.
     * 
     * @param data the CarPlan object to persist
     */
    public void store(ElementDataObject data) {
        CarPlan car_plan = (CarPlan) data;
        if (car_plan.isNew()) {
            insert(car_plan);
        } else {
            update(car_plan);
        }
    }

    /**
     * Deletes the instance of $table.destinationClassNameHome.
     * 
     * @param data the $table.destinationClassNameHome object to persist
     */
    public void remove(ElementDataObject data) {
        CarPlan car_plan = (CarPlan) data;
        delete(car_plan);
    }

    /**
     * Method to find all instances of this particular data element for a
     * specified user. <i>Note: This will need to implement sort order as well,
     * at some time.</i>
     * 
     * @param personId of the user that we are finding elements for
     * @return a java.util.List of DataObjects of the type for this element.
     */
    public List<CarPlan> findByPersonId(String personId) {
        List<CarPlan> elements = new ArrayList<CarPlan>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_ALL);
            ps.setString(1, personId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CarPlan car_plan = new CarPlan();
                car_plan.setPersonId(rs.getString("PERSON_ID"));
                car_plan.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                car_plan.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                car_plan.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                car_plan.setEntryName(rs.getString("ENTRY_NAME"));
                car_plan.setActionPlan(rs.getString("ACTION_PLAN"));
                car_plan.setTimeline(rs.getString("TIMELINE"));
                // logService.debug("Adding instance " + car_plan + " to the elements list.");
                elements.add(car_plan);
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
        CarPlan car_plan = new CarPlan();
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
                car_plan.setPersonId(rs.getString("PERSON_ID"));
                car_plan.setEntryId(rs.getBigDecimal("ENTRY_ID"));
                car_plan.setDateCreated(rs.getTimestamp("DATE_CREATED"));
                car_plan.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                car_plan.setEntryName(rs.getString("ENTRY_NAME"));
                car_plan.setActionPlan(rs.getString("ACTION_PLAN"));
                car_plan.setTimeline(rs.getString("TIMELINE"));
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return car_plan;
    }

    // --------------------------------------------------------------------------
    // helper methods
    /**
     * Updates the current instance of the data element. The store method needs
     * to guarantee that the data object is of type CarPlan.
     * 
     * @param car_plan the instance to update. @ if there is a problem updating.
     */
    protected void update(CarPlan car_plan) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(UPDATE_QUERY);
            logService.debug("Preparing update statement " + UPDATE_QUERY);
            ps.setString(1, car_plan.getEntryName());
            ps.setString(2, car_plan.getActionPlan());
            ps.setString(3, car_plan.getTimeline());
            ps.setString(4, car_plan.getPersonId());
            ps.setBigDecimal(5, car_plan.getEntryId());
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
     * guarantee that the data object is of type CarPlan.
     * 
     * @param car_plan the instance to create. @ if there is a problem
     *            inserting.
     */
    protected void insert(CarPlan car_plan) {
        Connection conn = null;
        PreparedStatement ps = null;
        int id = (car_plan.isRemote()) ? car_plan.getEntryId().intValue() : sequenceGenerator.getNextSequenceNumber("entry");
        try {

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(INSERT_QUERY);
            logService.debug("Preparing insert statement " + INSERT_QUERY);
            ps.setString(1, car_plan.getPersonId());
            ps.setInt(2, id);
            car_plan.setEntryId(String.valueOf(id));
            ps.setString(3, car_plan.getEntryName());
            ps.setString(4, car_plan.getActionPlan());
            ps.setString(5, car_plan.getTimeline());
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
    protected void delete(CarPlan car_plan) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, car_plan.getPersonId());
            ps.setBigDecimal(2, car_plan.getEntryId());
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
            + ",ACTION_PLAN,TIMELINE FROM CAR_PLAN WHERE PERSON_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE = "SELECT PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE,ENTRY_NAME"
            + ",ACTION_PLAN,TIMELINE FROM CAR_PLAN WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to select a single instance of NAME for a person. */
    private final String SELECT_QUERY_SINGLE_COUNT = "SELECT COUNT(ENTRY_ID) FROM CAR_PLAN WHERE PERSON_ID = ? AND ENTRY_ID = ?";

    /** The query to INSERT a new row into the car_plan table. */
    private final String INSERT_QUERY = "INSERT INTO CAR_PLAN(PERSON_ID,ENTRY_ID,DATE_CREATED,MODIFIED_DATE"
            + ",ENTRY_NAME,ACTION_PLAN,TIMELINE) VALUES (?,?," + sysdate + "," + sysdate + ",?" + ",?,?)";

    /** The query to INSERT a new row into the car_plan table. */
    private final String UPDATE_QUERY = "UPDATE CAR_PLAN SET MODIFIED_DATE = " + sysdate + ",ENTRY_NAME = ?,ACTION_PLAN = ?"
            + ",TIMELINE = ? WHERE PERSON_ID=? AND ENTRY_ID=?";

    /** The query to DELETE a row from the NAME table. */
    private final String DELETE_QUERY = "DELETE FROM CAR_PLAN WHERE PERSON_ID = ? AND ENTRY_ID = ?";

	
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
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }

        return false;
	}
}

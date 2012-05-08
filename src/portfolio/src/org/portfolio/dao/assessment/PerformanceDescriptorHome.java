/* $Name:  $ */
/* $Id: PerformanceDescriptorHome.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.portfolio.dao.AbstractDataHome;
import org.portfolio.model.assessment.PerformanceDescriptor;

/**
 * This class manages the lifecycle for <code>PerformanceDescriptor</code> objects.
 */
public class PerformanceDescriptorHome extends AbstractDataHome {

	@SuppressWarnings("unused")
	private static final String allPerformanceDescriptorsQuery = "SELECT * FROM performance_descriptor";
	
	private final Map<Integer, PerformanceDescriptor> cache=null;

	public PerformanceDescriptorHome() {
//		cache = createCache();
	}

//	private Map<Integer, PerformanceDescriptor> createCache() {
//		RowMapper<PerformanceDescriptor> rowMapper = new RowMapper<PerformanceDescriptor>() {
//			public PerformanceDescriptor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//				PerformanceDescriptor performanceDescriptor = new PerformanceDescriptor( 
//						resultSet.getInt( "id" ), 
//						resultSet.getString( "name" ), 
//						resultSet.getString( "description" ) );
//				return performanceDescriptor;
//			}
//		};
//		List<PerformanceDescriptor> performanceDescriptors = queryList( rowMapper, allPerformanceDescriptorsQuery, new Object[0] );
//		Map<Integer, PerformanceDescriptor> map = new HashMap<Integer, PerformanceDescriptor>();
//		for (PerformanceDescriptor performanceDescriptor : performanceDescriptors) {
//			map.put( performanceDescriptor.getIdentifier(), performanceDescriptor );
//		}
//		return map;
//	}
//	}

	public PerformanceDescriptor find(int performanceDescriptorID) {
		return cache.get( performanceDescriptorID );
	}

	/**
	 * Returns an <code>PerformanceDescriptor</code> object given a <code>String</code> representing the
	 * performance descriptor ID, or <code>null</code> if no such performance descriptor could be found.
	 */
	public PerformanceDescriptor find(String performanceDescriptorIDString) {
		return find( Integer.parseInt( performanceDescriptorIDString ) );
	}

	/**
	 * Returns a <code>List</code> of all <code>PerformanceDescriptor</code> objects.
	 */
	public List<PerformanceDescriptor> find() {
		List<PerformanceDescriptor> performanceDescriptorList = new ArrayList<PerformanceDescriptor>( cache.values() );
		Collections.sort( performanceDescriptorList );
		return performanceDescriptorList;
	}
        
}

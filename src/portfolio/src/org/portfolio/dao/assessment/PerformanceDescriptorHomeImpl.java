/* $Name:  $ */
/* $Id: PerformanceDescriptorHomeImpl.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import org.portfolio.dao.AbstractDataHome;
import org.portfolio.model.assessment.PerformanceDescriptor;

/**
 * This class manages the lifecycle for <code>PerformanceDescriptor</code> objects.
 */
@SuppressWarnings("unused")
public class PerformanceDescriptorHomeImpl extends AbstractDataHome {

/**	private final ParameterizedRowMapper<PerformanceDescriptor> rowMapper = new ParameterizedRowMapper<PerformanceDescriptor>() {
		public PerformanceDescriptor mapRow(ResultSet rs, int rowNum) throws SQLException {
			PerformanceDescriptor performanceDescriptor = new PerformanceDescriptor();
			Integer performanceDescriptorId = rs.getInt("performance_descriptor_id");
			performanceDescriptor.setIdentifier(performanceDescriptorId);
			performanceDescriptor.setPerformanceDescriptor(rs.getInt("performance_descriptor_id"));
			performanceDescriptor.setAssessmentModelId(rs.getInt("assessment_model_id"));
			performanceDescriptor.setObjectiveId(rs.getInt("objective_id"));
			performanceDescriptor.setDescription(rs.getString("description"));
			performanceDescriptor.setScoreValue(rs.getInt("score_value"));
			performanceDescriptor.setScoreSequence(rs.getInt("score_sequence"));
			performanceDescriptor.setExample(rs.getString("example"));
			return performanceDescriptor;
		}
	};

	public PerformanceDescriptor find(Integer performanceDescriptorID) {
		return cache.get( performanceDescriptorID );
	}

	/**
	 * Returns an <code>PerformanceDescriptor</code> object given a <code>String</code> representing the
	 * performance descriptor ID, or <code>null</code> if no such performance descriptor could be found.
	 *
	public PerformanceDescriptor find(String performanceDescriptorIDString) {
		return find( Integer.parseInt( performanceDescriptorIDString ) );
	}

	/**
	 * Returns a <code>List</code> of all <code>PerformanceDescriptor</code> objects.
	 *
	public List<PerformanceDescriptor> find() {
		List<PerformanceDescriptor> performanceDescriptorList = new ArrayList<PerformanceDescriptor>( cache.values() );
		Collections.sort( performanceDescriptorList );
		return performanceDescriptorList;
	}
    
    */
}

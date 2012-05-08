/* $Name:  $ */

package org.portfolio.dao;

import java.util.HashMap;

import javax.sql.DataSource;

import org.portfolio.util.LogService;
import org.portfolio.util.RequiredInjection;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * This class manages sequences based on Oracle sequences.
 */
public class MySQLSequenceGenerator implements SequenceGenerator {

	private LogService logService = new LogService(this.getClass());
    private final String sequenceQuery = "SELECT auto_increment from information_schema.tables WHERE table_schema = DATABASE() AND LOWER(table_name) = LOWER(?) LIMIT 1";
    private HashMap<String, String> aliases;
    private SimpleJdbcTemplate jdbcTemplate;

	public int getNextSequenceNumber(String name) {
		// Columns should have AUTO_INCREMENT set 
		
		String sequenceName = aliases.get(name);
		
		if(sequenceName == null) {
			sequenceName = name;
		}
		
		logService.debug("==> Sequence: " + sequenceName);
		
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		
		for(int i=0; i<stack.length; ++i) {
			
			StackTraceElement t = stack[i];
			logService.debug("Line " + t.getLineNumber() + ": " + t.getClassName() + " - Method: " + t.getMethodName() );
			
		}
		
		logService.debug("----------------------------------------------------\n\n\n");
		
		int ret = 1;
		
		try {
			ret = jdbcTemplate.queryForInt(sequenceQuery, sequenceName);
		}
		catch(Exception e) {
			logService.debug(e);
		}
		
		if(sequenceName.contentEquals("ENTRY_TABLE")) {
			jdbcTemplate.update("INSERT INTO ENTRY_TABLE (ID) VALUES (0)");
		}
		else if(sequenceName.contentEquals("PERSON_ID_TABLE")) {
			jdbcTemplate.update("INSERT INTO PERSON_ID_TABLE (ID) VALUES (0)");
		}
		else if(sequenceName.contentEquals("COMMUNITY_ID_TABLE")) {
			jdbcTemplate.update("INSERT INTO COMMUNITY_ID_TABLE (ID) VALUES (0)");
		}
		else if(sequenceName.contentEquals("TEMPLATE_ELEMENTS_MAPPING_TABLE")) {
			jdbcTemplate.update("INSERT INTO TEMPLATE_ELEMENTS_MAPPING_TABLE (ID) VALUES (0)");
		}
		else if(sequenceName.contentEquals("TEMPLATE_HEADING_CATEGORY_TABLE")) {
			jdbcTemplate.update("INSERT INTO TEMPLATE_HEADING_CATEGORY_TABLE (ID) VALUES (0)");
		}
		else if(sequenceName.contentEquals("WIZARD_CATEGORY_TABLE")) {
			jdbcTemplate.update("INSERT INTO WIZARD_CATEGORY_TABLE (ID) VALUES (0)");
		}
		
		return ret;
	}

    @RequiredInjection
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @RequiredInjection
    public void setAliases(HashMap<String, String> aliases) {
        this.aliases = aliases;
    }
}
/* $Name:  $ */
/* $Id: OracleSequenceGeneratorUM.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.util.HashMap;

import javax.sql.DataSource;

import org.portfolio.util.RequiredInjection;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * This class manages sequences based on Oracle sequences.
 */
public class OracleSequenceGeneratorUM implements SequenceGenerator {

    private final String sequenceQuery = "select %s.nextval from dual";
    private HashMap<String, String> aliases;
    private SimpleJdbcTemplate jdbcTemplate;

    public int getNextSequenceNumber(String name) {
        String sequenceName = aliases.get(name);
        if (sequenceName == null) {
            sequenceName = name;
        }
        String query = String.format(sequenceQuery, sequenceName);
        return jdbcTemplate.queryForInt(query);
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

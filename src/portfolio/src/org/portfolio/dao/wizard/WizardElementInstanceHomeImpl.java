/* $Name:  $ */
/* $Id: WizardElementInstanceHomeImpl.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.math.BigDecimal;
import javax.sql.DataSource;

import org.portfolio.model.EntryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WizardElementInstanceHomeImpl implements WizardElementInstanceHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void insert(int wizardElementId, BigDecimal entryId, String personId) {
        simpleJdbcTemplate.update(
                "insert into wizard_element_instance (wizard_element_id, entry_id, person_id) values (?,?,?)",
                wizardElementId,
                entryId,
                personId);
    }

    @Override
    public void delete(int wizardElementId, BigDecimal entryId, String personId) {
        simpleJdbcTemplate.update(
                "delete from wizard_element_instance where wizard_element_id=? and entry_id=? and person_id=?",
                wizardElementId,
                entryId,
                personId);
    }

    @Override
    public boolean existsFor(int wizardElementId, BigDecimal entryId, String personId) {
        return simpleJdbcTemplate.queryForInt(
                "select count(*) from wizard_element_instance where wizard_element_id=? and entry_id=? and person_id=?",
                wizardElementId,
                entryId,
                personId) > 0;
    }

    @Override
    public void deleteForEntry(EntryKey entryKey) {
        simpleJdbcTemplate.update(
                "delete from wizard_element_instance where wizard_element_id in "
                        + "(select id from wizard_element_mapping where element_id=?) and entry_id=? and person_id=?",
                entryKey.getElementId(),
                entryKey.getEntryId(),
                entryKey.getPersonId());
    }

    @Override
    public int getPersonCountForElement(int wizardElementId) {
        return simpleJdbcTemplate.queryForInt(
                "select count(distinct person_id) from wizard_element_instance where wizard_element_id=?",
                wizardElementId);
    }

    @Override
    public int getPersonCountForCategory(int catId) {
        return simpleJdbcTemplate.queryForInt("select count(distinct wei.person_id) "
                + "from wizard_element_instance wei, wizard_element_mapping wem, wizard_category wc "
                + "where wei.wizard_element_id = wem.id and wem.category_id=wc.id and (wc.id=? or wc.parent_category_id=?)", catId, catId);
    }

    @Override
    public int getPersonCountForGuide(int guideId) {
        return simpleJdbcTemplate.queryForInt("select count(distinct wei.person_id) "
                + "from wizard_element_instance wei, wizard_element_mapping wem, wizard_category wc "
                + "where wei.wizard_element_id = wem.id and wem.category_id=wc.id and wc.wizard_id=?", guideId);
    }

    @Override
    public void deleteForElement(int wizardElementId) {
        simpleJdbcTemplate.update("delete from wizard_element_instance where wizard_element_id=?", wizardElementId);
    }
}

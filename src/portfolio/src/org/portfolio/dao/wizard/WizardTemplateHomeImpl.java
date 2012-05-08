/* $Name:  $ */
/* $Id: WizardTemplateHomeImpl.java,v 1.16 2010/10/27 19:24:57 ajokela Exp $ */
/**
 * $Header $
 * $Revision $
 * $Date $
 * 
 */
package org.portfolio.dao.wizard;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * This class provides methods to find the templates associated with a wizard.
 */
/**
 * @author Matt Sheehan
 * 
 *         Revised: 12/22/2009 Alex C. Jokela Bug: EPF-540 List<Template> getTemplates(BigDecimal wizardId)
 */
@Repository
public class WizardTemplateHomeImpl implements WizardTemplateHome {

    @Autowired
    private TemplateHome templateHome;

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public List<Template> getTemplates(int wizardId) {
        return getTemplates(new BigDecimal(wizardId));
    }

    private List<Template> getTemplates(BigDecimal wizardId) {
        String sql = "SELECT DISTINCT tem_map.template_id FROM TEMPLATE_ELEMENTS_MAPPING tem_map, "
                + "WIZARD_ELEMENT_MAPPING wcem, WIZARD_CATEGORY wc, TEMPLATE tmplt WHERE "
                + "tmplt.template_id=tem_map.template_id AND tem_map.wizard_element_id=wcem.id " + "AND tmplt.is_deleted = 'n' "
                + "AND tmplt.visible = 1 " + "AND wcem.category_id=wc.id and wc.wizard_id=?";
        RowMapper<Template> rowMapper = new RowMapper<Template>() {
            public Template mapRow(ResultSet rs, int rowNum) throws SQLException {
                return templateHome.findTemplateById(rs.getString("TEMPLATE_ID"));
            }
        };
        return simpleJdbcTemplate.query(sql, rowMapper, wizardId);
    }
}

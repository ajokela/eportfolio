/* $Name:  $ */
/* $Id: TemplateCategoryHomeImpl.java,v 1.15 2011/02/16 20:24:41 ajokela Exp $ */
package org.portfolio.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.TemplateCategory;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedSingleColumnRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("templateCategoryHome")
public class TemplateCategoryHomeImpl implements TemplateCategoryHome {

    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;
    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

	private static final RowMapper<TemplateCategory> rowMapper = new RowMapper<TemplateCategory>() {
		public TemplateCategory mapRow(ResultSet resultSet, int arg1) throws SQLException {
			TemplateCategory templateCategory = new TemplateCategory();
			templateCategory.setId(resultSet.getInt("id"));
			templateCategory.setTitle(resultSet.getString("title"));
			templateCategory.setSortOrder(resultSet.getInt("sort_order"));
			templateCategory.setTemplateId(resultSet.getString("template_id"));
			int parentCategoryId = resultSet.getInt("parent_category_id");
			if (parentCategoryId != 0) {
				templateCategory.setParentCategoryId(parentCategoryId);
			}
			return templateCategory;
		}
	};

	public List<TemplateCategory> find(String templateId) {
		String sql = "SELECT * FROM template_heading_category WHERE template_id = ? AND parent_category_id is null ORDER BY sort_order";
		
		return simpleJdbcTemplate.query(sql, rowMapper, templateId);
	}

	public List<TemplateCategory> findChildren(int parentCategoryId) {
		String sql = "SELECT * FROM template_heading_category WHERE parent_category_id = ? ORDER BY sort_order";
		return simpleJdbcTemplate.query(sql, rowMapper, parentCategoryId);
	}

	@SuppressWarnings("deprecation")
	public int findNumberOfChildrenByCategoryId(Integer parentCategoryId) {
		String sql = "select count(*) c from template_heading_category where parent_category_id = ?";
		return simpleJdbcTemplate.queryForObject(sql, new ParameterizedSingleColumnRowMapper<Long>(), parentCategoryId)
				.intValue();
	}

	@SuppressWarnings("deprecation")
	public int findNumberOfTopLevelCategoriesByTemplateId(String templateId) {
		String sql = "select count(*) c from template_heading_category where template_id=? and parent_category_id is null";
		return simpleJdbcTemplate.queryForObject(sql, new ParameterizedSingleColumnRowMapper<Long>(), templateId).intValue();
	}

	public void insert(TemplateCategory category) {
		String sql = "insert into template_heading_category (id, template_id, parent_category_id, title, sort_order) values (?,?,?,?,?)";
		int id = sequenceGenerator.getNextSequenceNumber("TEMPLATE_CATEGORY_ID_SEQ");
		simpleJdbcTemplate.update(
				sql,
				id,
				category.getTemplateId(),
				category.getParentCategoryId(),
				category.getTitle(),
				category.getSortOrder());
		category.setId(id);

	}

	public void update(TemplateCategory category) {
		String sql = "update template_heading_category set template_id=?, parent_category_id=?, title=?, sort_order=? where id=?";
		simpleJdbcTemplate.update(
				sql,
				category.getTemplateId(),
				category.getParentCategoryId(),
				category.getTitle(),
				category.getSortOrder(),
				category.getId());
	}

	public void delete(Integer categoryId) {

		String templateSQL = "delete from template_heading_category where id=?";
		String elementsSQL = "delete from template_elements_mapping where category_id =?";
		
		simpleJdbcTemplate.update(elementsSQL, categoryId);
		
		simpleJdbcTemplate.update(templateSQL, categoryId);
	}

	public TemplateCategory findById(int categoryId) {
		String sql = "select * from template_heading_category where id = ?";
		return simpleJdbcTemplate.queryForObject(sql, rowMapper, categoryId);
	}

	public TemplateCategory findByParentIdAndSortOrder(Integer parentCategoryId, int order) {
		String sql = "select * from template_heading_category where parent_category_id=? and sort_order=?";
		List<TemplateCategory> result = simpleJdbcTemplate.query(sql, rowMapper, parentCategoryId, order);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<TemplateCategory> findTopLevelCategories(String templateId) {
		String sql = "select * from template_heading_category where template_id = ? and parent_category_id is null "
				+ "order by sort_order, id";
		return simpleJdbcTemplate.query(sql, rowMapper, templateId);
	}

	public TemplateCategory findTopLevelCategoryByTemplateIdAndSortOrder(String templateId, int order) {
		String sql = "select * from template_heading_category where template_id=? and parent_category_id is null and sort_order=?";
		List<TemplateCategory> result = simpleJdbcTemplate.query(sql, rowMapper, templateId, order);
		return result.isEmpty() ? null : result.get(0);
	}
}

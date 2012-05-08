/* $Name:  $ */
/* $Id: CategoryHomeImpl.java,v 1.11 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.wizard.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedSingleColumnRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryHomeImpl implements CategoryHome {

    @Autowired private WizardElementDefinitionHome wizardElementDefinitionHome;
    @Autowired private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

	private final RowMapper<Category> rowMapper = new RowMapper<Category>() {
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			int categoryId = rs.getInt("id");
			Category category = new Category();
			category.setId(categoryId);
			category.setCategories(findChildrenByCategoryId(categoryId));
			category.setTitle(rs.getString("title"));
			category.setWizardElementDefinitions(wizardElementDefinitionHome.findByCategoryId(categoryId));
			category.setWizardId(rs.getInt("wizard_id"));
			int parentCategoryId = rs.getInt("parent_category_id");
			if (parentCategoryId != 0) {
				category.setParentCategoryId(parentCategoryId);				
			}
			category.setSortOrder(rs.getInt("sort_order"));
			return category;
		}
	};

	public List<Category> findTopLevelCategories(int wizardId) {
		String sql = "SELECT * FROM wizard_category WHERE wizard_id = ? AND (parent_category_id = 0 or parent_category_id is null) ORDER BY sort_order, id";
		return simpleJdbcTemplate.query(sql, rowMapper, wizardId);
	}

	public List<Category> findChildrenByCategoryId(int categoryId) {
		String sql = "SELECT * FROM wizard_category WHERE parent_category_id = ? ORDER BY sort_order, id";
		return simpleJdbcTemplate.query(sql, rowMapper, categoryId);
	}

	public Category findById(int categoryId) {
		try {
			String sql = "SELECT * FROM wizard_category WHERE id = ?";
			return simpleJdbcTemplate.queryForObject(sql, rowMapper, categoryId);
		}
		catch (Exception e) {
			return null;
		}
	}

	public void update(Category category) {
		String sql = "update wizard_category set WIZARD_ID=?, PARENT_CATEGORY_ID=?, TITLE=?, SORT_ORDER=? where id=?";
		simpleJdbcTemplate.update(
				sql,
				category.getWizardId(),
				category.getParentCategoryId(),
				category.getTitle(),
				category.getSortOrder(),
				category.getId());
	}

	public void delete(int categoryId) {
		String sql = "delete from wizard_category where id=?";
		simpleJdbcTemplate.update(sql, categoryId);
	}

	public void insert(Category category) {
		String sql = "insert into wizard_category (ID, WIZARD_ID, PARENT_CATEGORY_ID, TITLE, SORT_ORDER) values (?,?,?,?,?)";
		int id = sequenceGenerator.getNextSequenceNumber("WIZARD_CATEGORY_ID_SEQ");
		
		boolean found = false;
		
		while(!found) {
			int res = simpleJdbcTemplate.queryForInt("SELECT COUNT(id) FROM wizard_category WHERE id = ?", id);
			
			if(res > 0) {
				++id;
			}
			else {
				found = true;
			}
			
		}
		
		simpleJdbcTemplate.update(
				sql,
				id,
				category.getWizardId(),
				category.getParentCategoryId(),
				category.getTitle(),
				category.getSortOrder());
		category.setId(id);
	}

	@SuppressWarnings("deprecation")
	public int findNumberOfChildrenByCategoryId(int categoryId) {
		String sql = "select count(*) c from wizard_category where parent_category_id = ?";
		return simpleJdbcTemplate.queryForObject(sql, new ParameterizedSingleColumnRowMapper<Long>(), categoryId).intValue();
	}

	@SuppressWarnings("deprecation")
	public int findNumberOfTopLevelCategoriesByWizardId(int wizardId) {
		String sql = "select count(*) c from wizard_category where wizard_id=? and (parent_category_id = 0 or parent_category_id is null)";
		return simpleJdbcTemplate.queryForObject(sql, new ParameterizedSingleColumnRowMapper<Long>(), wizardId).intValue();
	}

	public Category findTopLevelCategoryByWizardIdAndSortOrder(int wizardId, int order) {
		String sql = "select * from wizard_category where wizard_id=? and (parent_category_id = 0 or parent_category_id is null) and SORT_ORDER=?";
		List<Category> result = simpleJdbcTemplate.query(sql, rowMapper, wizardId, order);
		return result.isEmpty() ? null : result.get(0);
	}

	public Category findByParentIdAndSortOrder(int parentCategoryId, int order) {
		String sql = "select * from wizard_category where PARENT_CATEGORY_ID=? and SORT_ORDER=?";
		List<Category> result = simpleJdbcTemplate.query(sql, rowMapper, parentCategoryId, order);
		return result.isEmpty() ? null : result.get(0);
	}
}

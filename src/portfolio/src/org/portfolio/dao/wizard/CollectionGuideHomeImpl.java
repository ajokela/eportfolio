/* $Name:  $ */
/* $Id: CollectionGuideHomeImpl.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.wizard.CollectionGuide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Matt Sheehan
 * 
 */
@Repository("collectionGuideHome")
public class CollectionGuideHomeImpl implements CollectionGuideHome {

    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<CollectionGuide> rowMapper = new RowMapper<CollectionGuide>() {
        public CollectionGuide mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int wizardId = resultSet.getInt("id");
            CollectionGuide guide = new CollectionGuide();
            guide.setId(wizardId);
            guide.setCommunityId(resultSet.getInt("wizard_group_id"));
            guide.setTitle(resultSet.getString("title"));
            guide.setTip(resultSet.getString("tip"));
            guide.setShareTip(resultSet.getString("share_tip"));
            guide.setDateCreated(resultSet.getDate("date_created"));
            guide.setPublished("y".equals(resultSet.getString("is_published")));
            guide.setDeleted("y".equals(resultSet.getString("is_deleted")));
            guide.setDescription(resultSet.getString("description"));
            return guide;
        }
    };

    public List<CollectionGuide> findByGroupId(int groupId) {
        return simpleJdbcTemplate.query("select * from wizard where wizard_group_id = ? and is_deleted='n' order by id", rowMapper, groupId);
    }

    public CollectionGuide find(int wizardId) {
        return simpleJdbcTemplate.queryForObject("select * from wizard where id = ? and is_deleted='n'", rowMapper, wizardId);
    }

    public void insert(CollectionGuide wizard) {
        int id = sequenceGenerator.getNextSequenceNumber("WIZARD_ID_SEQ");
        String sql = "insert into wizard (id, wizard_group_id, title, description, "
                + "tip, share_tip, date_created, is_published, is_deleted) values (?,?,?,?,?,?,?,?,?)";
        simpleJdbcTemplate
                .update(
                        sql,
                        id,
                        wizard.getCommunityId(),
                        wizard.getTitle(),
                        wizard.getDescription(),
                        wizard.getTip(),
                        wizard.getShareTip(),
                        new Timestamp(System.currentTimeMillis()),
                        wizard.isPublished() ? "y" : "n",
                        wizard.isDeleted() ? "y" : "n");
        wizard.setId(id);
    }

    public void update(CollectionGuide collectionGuide) {
        String sql = "update wizard set wizard_group_id=?, title=?, description=?, tip=?, share_tip=?, date_created=?, is_published=?, is_deleted=? where id=?";
        
        simpleJdbcTemplate.update(
                sql,
                collectionGuide.getCommunityId(),
                collectionGuide.getTitle(),
                collectionGuide.getDescription(),
                collectionGuide.getTip(),
                collectionGuide.getShareTip(),
                new Timestamp(System.currentTimeMillis()),
                collectionGuide.isPublished() ? "y" : "n",
                collectionGuide.isDeleted() ? "y" : "n",
                collectionGuide.getId());
    }

    public List<CollectionGuide> findAll() {
        return simpleJdbcTemplate.query("select * from wizard where is_deleted='n'", rowMapper);
    }
}

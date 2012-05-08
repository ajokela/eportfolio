/* $Name:  $ */
/* $Id: AdviserCommentHomeImpl.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.AdviserComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdviserCommentHomeImpl implements AdviserCommentHome {

    @Autowired private PersonHome personHome;
    @Autowired private SequenceGenerator sequenceGenerator;
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<AdviserComment> rm = new RowMapper<AdviserComment>() {
        public AdviserComment mapRow(ResultSet rs, int arg1) throws SQLException {
            AdviserComment c = new AdviserComment();
            c.setId(rs.getInt("id"));
            c.setText(rs.getString("text"));
            c.setDateCreated(rs.getTimestamp("date_created"));
            c.setDateUpdated(rs.getTimestamp("date_updated"));
            c.setAdviser(personHome.findByPersonId(rs.getString("adviser")));
            c.setAdvisee(personHome.findByPersonId(rs.getString("advisee")));
            return c;
        }
    };

    public void delete(int id) {
        simpleJdbcTemplate.update("delete from adviser_comment where id=?", id);

    }

    public List<AdviserComment> findByAdvisee(String adviseeId) {
        return simpleJdbcTemplate.query("select * from adviser_comment where advisee=?", rm, adviseeId);
    }

    public AdviserComment findById(int id) {
        List<AdviserComment> result = simpleJdbcTemplate.query("select * from adviser_comment where id=?", rm, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void insert(AdviserComment adviserComment) {
        int id = sequenceGenerator.getNextSequenceNumber("adviser_comment_id");
        adviserComment.setId(id);
        Date now = new Date();
        adviserComment.setDateCreated(now);
        adviserComment.setDateUpdated(now);
        simpleJdbcTemplate.update(
                "insert into adviser_comment (id, adviser, advisee, text, date_created, date_updated) values (?,?,?,?,?,?)",
                adviserComment.getId(),
                adviserComment.getAdviser().getPersonId(),
                adviserComment.getAdvisee().getPersonId(),
                adviserComment.getText(),
                adviserComment.getDateCreated(),
                adviserComment.getDateUpdated());
    }

    public void update(AdviserComment adviserComment) {
        adviserComment.setDateUpdated(new Date());
        simpleJdbcTemplate.update(
                "update adviser_comment set adviser=?, advisee=?, text=?, date_updated=? where id=?",
                adviserComment.getAdviser().getPersonId(),
                adviserComment.getAdvisee().getPersonId(),
                adviserComment.getText(),
                adviserComment.getDateUpdated(),
                adviserComment.getId());
    }
}

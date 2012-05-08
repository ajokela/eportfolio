/* $Name:  $ */
/* $Id: CommentsHomeImpl.java,v 1.12 2011/02/03 20:43:01 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("commentsHome")
public class CommentsHomeImpl implements CommentsHome {

    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<Comment> rowMapper = new RowMapper<Comment>() {
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comments = new Comment();
            comments.setCreator(rs.getString("CREATOR"));
            comments.setCommentId(rs.getLong("COMMENT_ID"));
            comments.setElementClassName(rs.getString("ELEMENT_CLASS_NAME"));
            comments.setEntryId(rs.getBigDecimal("ENTRY_ID"));
            comments.setCommentDate(rs.getTimestamp("COMMENT_DATE"));
            comments.setCommentTitle(rs.getString("COMMENT_TITLE"));
            comments.setCommentText(rs.getString("COMMENT_TEXT"));
            comments.setVisibility(new Integer(rs.getInt("VISIBILITY")));
            comments.setOwner(rs.getString("OWNER"));
            return comments;
        }
    };

    public void remove(Comment data) {
        simpleJdbcTemplate.update("delete from comments where comment_id = ?", data.getCommentId());
    }

    public List<Comment> findXMostRecentCommentsRecievedByUser(String userId, int x) {
        String sql = "select * from (select * from comments where owner=? and owner != creator order by comment_date desc) where rownum <=?";
        return simpleJdbcTemplate.query(sql, rowMapper, userId, x);
    }

    public List<Comment> findXMostRecentCommentsSentByUser(String userId, int x) {
        String sql = "select * from (select * from comments where creator=? order by comment_date desc) where rownum <=?";
        return simpleJdbcTemplate.query(sql, rowMapper, userId, x);
    }

    public Comment findByCommentId(String commentId) {
        String sql = "select creator,comment_id,element_class_name,entry_id"
                + ",comment_date,comment_title,comment_text,visibility,owner from comments where comment_id = ?";
        return simpleJdbcTemplate.queryForObject(sql, rowMapper, commentId);
    }

    public List<Comment> findAllByElement(String personId, BigDecimal entryId, String className, boolean isElementOwner) {
        String sql = String.format("select *  from comments where entry_id = ? and element_class_name = ? "
                + "and (creator = ? or visibility in (%s) ) order by comment_date", isElementOwner ? "2,3" : "3");
        return simpleJdbcTemplate.query(sql, rowMapper, entryId, className, personId);
    }

    public List<Comment> findAllByOwner(String ownerId) {
        String sql = "select creator,comment_id,element_class_name,entry_id"
                + ",comment_date,comment_title,comment_text,visibility,owner from comments where owner = ? and "
                + " ( visibility in(3, 2)  or creator = owner )  order by comment_date desc";
        return simpleJdbcTemplate.query(sql, rowMapper, ownerId);
    }

    public List<Comment> findByPersonId(String personId) {
        String sql = "select creator,comment_id,element_class_name,entry_id" + ",comment_date,comment_title,comment_text,visibility,owner"
                + " from comments where creator = ? order by comment_date desc";
        return simpleJdbcTemplate.query(sql, rowMapper, personId);
    }

    public void update(Comment comments) {
        String sql = "update comments set element_class_name = ?,comment_date = ?"
                + ",comment_title = ?,comment_text = ?,visibility = ?,owner = ? where comment_id=?";
        simpleJdbcTemplate.update(
                sql,
                comments.getElementClassName(),
                new java.sql.Timestamp(comments.getCommentDate().getTime()),
                comments.getCommentTitle(),
                comments.getCommentText(),
                comments.getVisibility().intValue(),
                comments.getOwner().getPersonId(),
                comments.getCommentId());
    }

    public void insert(Comment comments) {
        String sql = "insert into comments(creator,comment_id,element_class_name,entry_id"
                + ",comment_date,comment_title,comment_text,visibility,owner) values (?,?,?,?,?,?,?,?,?)";
        int id = sequenceGenerator.getNextSequenceNumber("COMMENTID");
        simpleJdbcTemplate.update(
                sql,
                comments.getCreator().getPersonId(),
                id,
                comments.getElementClassName(),
                comments.getEntryId(),
                new Date(),
                comments.getCommentTitle(),
                comments.getCommentText(),
                comments.getVisibility().intValue(),
                comments.getOwner().getPersonId());
        
        
        
    }

    public void deleteAll(Comment comments) {
        simpleJdbcTemplate.update("delete from comments where entry_id = ? and element_class_name = ? ", comments.getEntryId(), comments
                .getElementClassName());
    }
}

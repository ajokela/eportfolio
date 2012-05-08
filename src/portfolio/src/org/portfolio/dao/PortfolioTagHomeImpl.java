/* $Name:  $ */
/* $Id: PortfolioTagHomeImpl.java,v 1.13 2010/11/23 14:27:17 ajokela Exp $ */
package org.portfolio.dao;

/**
 * @author Vijay Rajagopal
 */
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.PortfolioTag;
import org.portfolio.util.ArrayUtil;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("portfolioTagHome")
public class PortfolioTagHomeImpl implements PortfolioTagHome {

    private static final LogService logService = new LogService(PortfolioTagHomeImpl.class);
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<PortfolioTag> tagMapper = new RowMapper<PortfolioTag>() {
        public PortfolioTag mapRow(ResultSet rs, int rowNum) throws SQLException {
            PortfolioTag portfolioTag = new PortfolioTag(rs.getString("PERSON_ID"), rs.getBigDecimal("SHARE_ID"), rs.getString("TAG"), rs
                    .getDate("CREATED_DATE"));
            return portfolioTag;
        }
    };

    public List<PortfolioTag> findByPersonId(String personId) {
        return simpleJdbcTemplate.query(
                "select * from PORTFOLIO_TAGS where PERSON_ID = ? ORDER by SHARE_ID,lower(TAG)",
                tagMapper,
                personId);
    }

    public List<PortfolioTag> findByPersonPortfolio(String personId, BigDecimal shareId) {
        return simpleJdbcTemplate.query(
                "select * from PORTFOLIO_TAGS where PERSON_ID = ?  and SHARE_ID=? ORDER by SHARE_ID,lower(TAG)",
                tagMapper,
                personId,
                shareId);
    }

    public List<PortfolioTag> findAllTagsForPortfolios(String[] shareIds) {
        if (shareIds.length < 1) {
            return new ArrayList<PortfolioTag>();
        }
        String shareIdsString = ArrayUtil.join(shareIds, ",");
        String sql = "select * from portfolio_tags where share_id in (" + shareIdsString + ") order by share_id,lower(tag)";

        // logService.debug("tagQuery: " + sql);
        return simpleJdbcTemplate.query(sql, tagMapper);
    }

    public PortfolioTag getTag(String personId, BigDecimal shareId, String tagText) {
        List<PortfolioTag> result = simpleJdbcTemplate.query(
                "select * from portfolio_tags where person_id = ?  and share_id=? and tag=?",
                tagMapper,
                personId,
                shareId,
                tagText);
        return result.isEmpty() ? null : result.get(0);
    }

    public void deletePortfolioTag(PortfolioTag tag) {
        simpleJdbcTemplate.update(
                "delete portfolio_tags where person_id=? and  share_id=? and tag=?",
                tag.getPersonId(),
                tag.getShareId(),
                tag.getTag());
    }

    public void insertPortfolioTag(PortfolioTag tag) {
        simpleJdbcTemplate.update(
                "insert into portfolio_tags(person_id, share_id, tag, created_date) " + "values (?,?,?,?)",
                tag.getPersonId(),
                tag.getShareId(),
                tag.getTag(),
                new Timestamp(System.currentTimeMillis()));
    }

    public List<PortfolioTag> findPubTagsForPublicPortfolios() {
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
        String sql = "select tags.* from portfolio_tags tags, share_definition sharedef where sharedef.share_id = tags.share_id and "
                + "tags.person_id = sharedef.person_id and sharedef.public_view=1 and sharedef.date_expire > " + sp;
        return simpleJdbcTemplate.query(sql, tagMapper);
    }

    public List<PortfolioTag> querySharedPortfolioTags(String personId) {
        return simpleJdbcTemplate.query(
                "select tags.* from portfolio_tags tags, viewer viewer where tags.share_id= viewer.share_id and viewer.person_id = ?",
                tagMapper,
                personId);
    }

    public List<PortfolioTag> queryMyPortfolioTags(String personId, String[] portfolioTags) {
        if (portfolioTags.length < 1) {
            return new ArrayList<PortfolioTag>();
        }
        String portfolioTagsString = ArrayUtil.join(portfolioTags, ",");
        String sql = "select * from portfolio_tags where person_id = ? and tag in (" + portfolioTagsString + ")";
        return simpleJdbcTemplate.query(sql, tagMapper, personId);
    }
}

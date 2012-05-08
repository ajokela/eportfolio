/* $Name:  $ */
/* $Id: PortfolioHomeImpl.java,v 1.32 2011/03/25 13:10:32 ajokela Exp $ */
package org.portfolio.dao;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.portfolio.model.BarePortfolio;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioHistory;
import org.portfolio.model.PortfolioHistory.Action;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.Template;
import org.portfolio.model.Viewer;
import org.portfolio.model.Viewer.ViewType;
import org.portfolio.util.ArrayUtil;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository("portfolioHome")
public class PortfolioHomeImpl implements PortfolioHome {
	
    private final LogService logService = new LogService(this.getClass());
	
    @Autowired
    private SequenceGenerator sequenceGenerator;
    
    @Autowired
    private PersonHome personHome;
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
    private RowMapper<PortfolioHistory> portfolioHistoryMapper = new RowMapper<PortfolioHistory>() {
    	public PortfolioHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		PortfolioHistory history = new PortfolioHistory(personHome);
    		
    		history.setAction(rs.getString("ACTION"));
    		history.setDescription(rs.getString("DESCRIPTION"));
    		history.setId(rs.getInt("ID"));
    		history.setPersonId(rs.getString("PERSON_ID"));
    		history.setStamp(rs.getTimestamp("STAMP"));
    		history.setShareId(rs.getString("SHARE_ID"));
    		history.setContent(rs.getString("CONTENT"));
    		
    		return history;
    	}
    };

    private RowMapper<Portfolio> shareRowMapper = new RowMapper<Portfolio>() {
        public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
        	
            Portfolio portfolio = new Portfolio();
            String shareId = rs.getString("share_id");
            portfolio.setShareId(shareId);
            portfolio.setPersonId(rs.getString("person_id"));
            portfolio.setShareName(rs.getString("share_name"));
            portfolio.setShareDesc(rs.getString("share_desc"));
            portfolio.setDateCreated(new java.util.Date(rs.getTimestamp("date_created").getTime()));
            portfolio.setDateModified(rs.getTimestamp("date_modified"));
            portfolio.setDateExpire(rs.getTimestamp("date_expire"));
            portfolio.setTemplateId(rs.getString("template_id"));
            portfolio.setPublicView(rs.getBoolean("public_view"));
            portfolio.setQuickShare(rs.getBoolean("quick_share"));
            portfolio.setTheme(rs.getString("theme"));
            portfolio.setStockImage(rs.getString("stock_image"));
            portfolio.setHeaderImageType(rs.getString("header_image_type"));
            portfolio.setColorScheme(rs.getString("color_scheme"));
            portfolio.setContents(rs.getString("contents"));
            
            Person person = portfolio.getPerson();
            
            portfolio.setIsEnabled(person.isEnabled());
            
            String is_deleted = rs.getString("IS_DELETED");
            
            if(is_deleted == null) {
            	portfolio.setIsDeleted(false);
            }
            else {
            	portfolio.setIsDeleted(is_deleted.contentEquals("t") ? true : false);
            }
            
            portfolio.setHistory(findPortfolioHistoryByShareId(shareId));
            
            return portfolio;
        }
    };
    
    private RowMapper<BarePortfolio> bareShareRowMapper = new RowMapper<BarePortfolio>() {
    	public BarePortfolio mapRow(ResultSet rs, int rowNum) {
    		BarePortfolio b_p = new BarePortfolio();
    		
    		try {
    			String author = "";
    			
				if(rs.getString("LASTNAME") != null) {
					author += rs.getString("LASTNAME");
				}
				
				if(rs.getString("FIRST_NAME") != null) {
					author += rs.getString("FIRST_NAME");
				}
				
				if(rs.getString("MIDDLENAME") != null) {
					author += rs.getString("MIDDLENAME");
				}
    			
    			author = author.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    			
    			b_p.setAuthor(author);

    		}
    		catch(SQLException e) {
    			
    		}
    		
    		try {
    			b_p.setId(rs.getBigDecimal("SHARE_ID").toBigInteger());
    		}
    		catch(SQLException e) {
    			
    		}

    		try {
    			b_p.setTitle(rs.getString("SHARE_NAME"));
    		}
    		catch(SQLException e) {
    			
    		}
    		
    		try {
    			b_p.setUpdatedAt(rs.getTimestamp("DATE_MODIFIED"));
    		}
    		catch(SQLException e) {
    			
    		}
    		
    		return b_p;
    	}
    };
    
    public List<PortfolioHistory> findPortfolioHistoryByShareId(String shareId) {
    	return simpleJdbcTemplate.query("SELECT * FROM share_history WHERE share_id = ? AND description IS NOT NULL ORDER BY stamp", portfolioHistoryMapper, shareId);
    }
       
    public void newPortfolioHistoryEntry(PortfolioHistory history) {
    	
        Date now = new Date();
        
        String sql = "INSERT INTO share_history (ID, PERSON_ID, SHARE_ID, ACTION, STAMP, DESCRIPTION) VALUES (?,?,?,?,?,?)";
        int id = sequenceGenerator.getNextSequenceNumber("SHARE_HISTORY_SEQ");	
        
        simpleJdbcTemplate.update(sql, id, history.getPersonId(), history.getShareId(), history.getActionString(), now, history.getDescription());
        
    }
    
    public Portfolio findByShareId(String shareId) {
    	try {
    		return simpleJdbcTemplate.queryForObject("select * from share_definition where share_id = ? AND is_deleted = 'f'", shareRowMapper, shareId);
    	}
    	catch (Exception e) {
    		// logService.debug(e);
    	}
    	return null;
    }

    public List<Portfolio> findByOwnerAndTemplateId(String personId, String templateId) {
        return simpleJdbcTemplate.query(
                "select * from share_definition where person_id = ? and template_id = ? AND is_deleted = 'f'",
                shareRowMapper,
                personId,
                templateId);
    }
    
    public List<Portfolio> findByOwner(Person person) {
        return simpleJdbcTemplate.query(
                "select * from share_definition where person_id = ? AND is_deleted = 'f'",
                shareRowMapper,
                person.getPersonId());
    }
    
    public int findCountByTemplateId(String templateId) {
    	
    	String sql = "SELECT COUNT(*) FROM share_definition where template_id = ? AND is_deleted = 'f'";
    	
    	return simpleJdbcTemplate.queryForInt(sql, templateId);
    }

    public List<Portfolio> findByOwnerAndTemplateId(String personId, String templateId, String dateFrom, String dateTo) {
        
    	String sql = "select * from share_definition where person_id = ? and template_id = ? AND is_deleted = 'f'";
        int args = 0;
        
        if (dateFrom != null) {
        	sql += " AND date_modified >= to_date(?, 'MM/DD/YYYY')";
        	args = 1;
        }
        
        if (dateTo != null) {
        	sql += " AND date_modified <= to_date(?, 'MM/DD/YYYY')";
        	
        	if (args == 1) {
        		args = 5;
        	}
        	else {
        		args = 3;
        	}
        }

        if (args == 5) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, personId, templateId, dateFrom, dateTo);
        }
        else if (args == 3) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, personId, templateId, dateTo);
        }
        else if (args == 1) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, personId, templateId, dateFrom);
        }
        
        return simpleJdbcTemplate.query(sql, shareRowMapper, personId, templateId);
   	
    }
    
    public List<Portfolio> findByViewer(String personId) {
        return simpleJdbcTemplate.query(
                "select sd.* from share_definition sd, viewer v where sd.share_id=v.share_id and v.person_id=? AND sd.is_deleted = 'f'",
                shareRowMapper,
                personId);
    }

    public List<Portfolio> findByViewerAndViewType(String personId, ViewType viewType) {
        return simpleJdbcTemplate.query(
                "select sd.* from share_definition sd, viewer v where sd.share_id=v.share_id and v.person_id=? and v.view_type=? AND sd.is_deleted = 'f'",
                shareRowMapper,
                personId,
                viewType.toString());
    }

    public List<Portfolio> findPublicByTag(String tag) {
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
        String sql = "select shares.* from share_definition shares, portfolio_tags tags where shares.public_view = 1 "
                + "and shares.date_expire > " + sp + " and tags.share_id = shares.share_id and tags.tag=?  AND shares.is_deleted = 'f'" + "order by share_name";
        return simpleJdbcTemplate.query(sql, shareRowMapper, tag);
    }

    public List<Portfolio> findPublicByOwner(String personId) {
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
        String sql = "select * from share_definition where person_id = ? and public_view = 1 and (date_expire > " + sp + " OR date_expire IS NULL) AND is_deleted = 'f' order by share_name";
        return simpleJdbcTemplate.query(sql, shareRowMapper, personId);
    }
    
    public List<Portfolio> findAllPublic() {
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
    	String sql = "select * from share_definition where public_view = 1 and (date_expire > " + sp + " OR date_expire IS NULL) AND is_deleted = 'f' order by share_name";
    	return simpleJdbcTemplate.query(sql, shareRowMapper);
    }

    public List<Portfolio> findPublicByNameOrDesc(String query) {
    	
    	/* remove leading whitespace */
    	query = query.replaceAll("^\\s+", "");
    	
    	/* remove trailing whitespace */
    	query = query.replaceAll("\\s+$", "");
    	
    	/* replace multiple whitespaces between words with single blank */
    	query = query.replaceAll("\\b\\s{2,}\\b", " ");
    	
    	query = query.replace(" ", "%");
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
    	String sql = "SELECT * FROM share_definition WHERE public_view = 1 and (date_expire > " + sp + " OR date_expire IS NULL) AND is_deleted = 'f' AND (LOWER(share_name) LIKE '%' || LOWER(?) || '%' OR LOWER(share_desc) LIKE '%' || LOWER(?) || '%')";
    
    	return simpleJdbcTemplate.query(sql, shareRowMapper, query, query);
    }
    
    public List<Portfolio> findXMostRecentSharesCreatedByUser(String userId, int x) {
        String sql = "select * from (select * from share_definition where person_id=? AND is_deleted = 'f' order by date_created desc) LIMIT " + x;
        return simpleJdbcTemplate.query(sql, shareRowMapper, userId);
    }

    public List<Portfolio> findByOwnerAndAssessmentModelId(String personId, Integer assessmentModelId) {

    	String sql = "select * from share_definition sd, at_assessment_model_assnmnt ama "
                + "where sd.person_id = ? AND sd.is_deleted = 'f' and sd.share_id = ama.assessed_item_type_id and ama.assessed_item_type = ? and ama.assessment_model_id = ?";
        return simpleJdbcTemplate.query(sql, shareRowMapper, personId, PortfolioItemType.PORTFOLIO.toString(), assessmentModelId);

    }
    
    public List<Portfolio> findByAssessmentModelId(Integer assessmentModelId) {
    	return findByAssessmentModelId(assessmentModelId, null, null);
    }
    
    public List<Portfolio> findByAssessmentModelAndTypeId(Integer assessment_model_id, PortfolioItemType portfolioItemType, String dateFrom, String dateTo) {
    	String sql = "SELECT sd.* FROM share_definition sd, at_assessment at WHERE at.ASSESSED_ITEM_ID IN (SELECT ama.assessed_item_type_id FROM at_assessment_model_assnmnt ama WHERE ama.assessed_item_type = ? AND ama.assessment_model_id = ?) AND sd.share_id = at.share_id AND sd.is_deleted = 'f'";
    	
        int args = 0;
        
        if (dateFrom != null) {
        	sql += " AND at.assessed_date >= to_date(?, 'MM/DD/YYYY')";
        	args = 1;
        }
        
        if (dateTo != null) {
        	sql += " AND at.assessed_date <= to_date(?, 'MM/DD/YYYY')";
        	
        	if (args == 1) {
        		args = 5;
        	}
        	else {
        		args = 3;
        	}
        }

        // logService.debug("==> " + sql);
        
        if (args == 5) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, portfolioItemType.toString(), assessment_model_id, dateFrom, dateTo);
        }
        else if (args == 3) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, portfolioItemType.toString(), assessment_model_id, dateTo);
        }
        else if (args == 1) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, portfolioItemType.toString(), assessment_model_id, dateFrom);
        }
        	
    	return simpleJdbcTemplate.query(sql, shareRowMapper, portfolioItemType.toString(), assessment_model_id);
   	
    }

    public List<Portfolio> findByAssessmentModelId(Integer assessmentModelId, String dateFrom, String dateTo) {
        
    	String sql = "SELECT * FROM share_definition sd, at_assessment_model_assnmnt ama WHERE sd.share_id = ama.assessed_item_type_id AND sd.is_deleted = 'f' and ama.assessed_item_type = ? and ama.assessment_model_id = ?";
        
        int args = 0;
        
        if (dateFrom != null) {
        	sql += " AND sd.date_modified >= to_date(?, 'MM/DD/YYYY')";
        	args = 1;
        }
        
        if (dateTo != null) {
        	sql += " AND sd.date_modified <= to_date(?, 'MM/DD/YYYY')";
        	
        	if (args == 1) {
        		args = 5;
        	}
        	else {
        		args = 3;
        	}
        }

        logService.debug("==> " + sql);
        
        if (args == 5) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, PortfolioItemType.PORTFOLIO.toString(), assessmentModelId, dateFrom, dateTo);
        }
        else if (args == 3) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, PortfolioItemType.PORTFOLIO.toString(), assessmentModelId, dateTo);
        }
        else if (args == 1) {
        	return simpleJdbcTemplate.query(sql, shareRowMapper, PortfolioItemType.PORTFOLIO.toString(), assessmentModelId, dateFrom);
        }
        
    	
    	return simpleJdbcTemplate.query(sql, shareRowMapper, PortfolioItemType.PORTFOLIO.toString(), assessmentModelId);

    }

    
    public void update(Portfolio shareDefinition) {
        
    	Portfolio osd = findByShareId(shareDefinition.getShareId());
    	PortfolioHistory history = new PortfolioHistory(personHome);
    	
    	String description = "";
    	history.setAction(Action.SHARE_UPDATE);
    	history.setPersonId(shareDefinition.getPersonId());
    	history.setShareId(shareDefinition.getShareId());
    	
    	if (shareDefinition.getShareName() != null && shareDefinition.getShareName().compareTo(osd.getShareName()) != 0) {
    		
    		description = "shareName: [old] '" + osd.getShareName()  + "' | [new] '" + shareDefinition.getShareName() + "'";
    		
    		
    	}
    	
    	if(shareDefinition.getContentChangeCount() > 0) {
    		description += " | Contents Changed";
    		String content = "content: '" + shareDefinition.getContents() + "'";
    		history.setContent(content);
    	}
    	
    	history.setDescription(description);

    	newPortfolioHistoryEntry(history);
    	
    	String sql = "UPDATE share_definition set share_name = ?,person_id = ?,share_desc = ?,date_modified = ?,date_expire = ?,template_id = ?,public_view = ?,theme=?,stock_image=?,header_image_type=?,color_scheme=?,is_deleted=?,contents=? where share_id=?";
        
    	description = new String("Committing Updates");
    	history.setDescription(description);
    	
    	newPortfolioHistoryEntry(history);
    	
        simpleJdbcTemplate.update(
                sql,
                shareDefinition.getShareName(),
                shareDefinition.getPersonId(),
                shareDefinition.getShareDesc(),
                new Date(),
                shareDefinition.getDateExpire(),
                shareDefinition.getTemplateId(),
                shareDefinition.isPublicView() ? 1 : 0,
                shareDefinition.getTheme(),
                shareDefinition.getStockImage(),
                shareDefinition.getHeaderImageType(),
                shareDefinition.getColorScheme(),
                shareDefinition.isDeleted() ? "t" : "f",
                shareDefinition.getContents(),
                shareDefinition.getShareId());
    }

    public void insert(Portfolio shareDefinition) {
        Date now = new Date();
        shareDefinition.setDateCreated(now);
        shareDefinition.setDateModified(now);
        
    	PortfolioHistory history = new PortfolioHistory(personHome);

        String sql = "insert into share_definition (share_id,person_id,share_name,share_desc,"
                + "date_created,date_modified,date_expire,template_id,public_view,"
                + "quick_share,theme,stock_image,header_image_type,color_scheme,is_deleted,contents) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        int id = sequenceGenerator.getNextSequenceNumber("SHAREID");
        simpleJdbcTemplate.update(
                sql,
                id,
                shareDefinition.getPersonId(),
                shareDefinition.getShareName(),
                shareDefinition.getShareDesc(),
                shareDefinition.getDateCreated(),
                shareDefinition.getDateModified(),
                shareDefinition.getDateExpire(),
                shareDefinition.getTemplateId(),
                shareDefinition.isPublicView() ? 1 : 0,
                shareDefinition.isQuickShare() ? 1 : 0,
                shareDefinition.getTheme(),
                shareDefinition.getStockImage(),
                shareDefinition.getHeaderImageType(),
                shareDefinition.getColorScheme(),
                shareDefinition.isDeleted() ? "t" : "f",
                shareDefinition.getContents());

        shareDefinition.setShareId("" + id);
        
    	String description = new String("New Portfolio - '" + shareDefinition.getShareName() + "'");
    	history.setAction(Action.SHARE_CREATE);
    	history.setPersonId(shareDefinition.getPersonId());
    	history.setShareId(shareDefinition.getShareId());
    	history.setDescription(description);
    	
    	newPortfolioHistoryEntry(history);

    }

    public void delete(String shareId) {
        // simpleJdbcTemplate.update("delete from share_definition  where share_id = ?", shareId);
    	
    	PortfolioHistory history = new PortfolioHistory(personHome);
    	
    	Portfolio shareDefinition = findByShareId(shareId);
    	
    	String description = new String("Deleting Portfolio - '" + shareDefinition.getShareName() + "'");
    	history.setAction(Action.SHARE_DELETE);
    	history.setPersonId(shareDefinition.getPersonId());
    	history.setShareId(shareDefinition.getShareId());
    	history.setDescription(description);
    	
    	newPortfolioHistoryEntry(history);

    	simpleJdbcTemplate.update("UPDATE share_definition SET is_deleted = 't' where share_id = ?", shareId);
    }
    
    private SqlStringWithArgs buildSearchCriteria(PortfolioSearchCriteria criteria) {
    	
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
        List<Object> args = new ArrayList<Object>();
        
        StringBuilder sb = new StringBuilder("select ");
        
        if(criteria.getReturnType() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_OBJECTS) {
        	sb.append(" sd.* ");
        } else if (criteria.getReturnType() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_SIZE) {
        	sb.append(" COUNT(sd.share_id) AS CNT ");
        }
        else if (criteria.getReturnType() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_BARE) {
        	sb.append( " sd.SHARE_ID, sd.SHARE_NAME, sd.DATE_MODIFIED, sd.PERSON_ID, p.FIRST_NAME, p.MIDDLENAME, p.LASTNAME ");
        }
        
        sb.append(" from share_definition sd, viewer v, template t, person p ");
        
        sb.append("where v.share_id=sd.share_id AND sd.is_deleted = 'f' AND v.is_deleted = 'f' ");
        sb.append("and t.template_id=sd.template_id ");
        sb.append("and p.person_id=sd.person_id ");
        sb.append("and (v.view_type = 'OWNER' or sd.date_expire is null or sd.date_expire > " + sp + ") ");
        sb.append("and v.person_id=? ");
        
        args.add(criteria.getPersonId());

        if (criteria.getShareName() != null) {
            sb.append("and lower(sd.share_name) like (?) ");
            args.add("%" + criteria.getShareName().toLowerCase() + "%");
            
            sb.append("and lower(sd.share_desc) like (?) ");
            args.add("%" + criteria.getShareName().toLowerCase() + "%");
            
            sb.append("lower(p.first_name) || ' ' || lower(p.lastname) LIKE ('%' || ? || '%') ");
            args.add(criteria.getShareName().toLowerCase().replaceAll(" ", "%"));
            
            sb.append("lower(p.lastname) || ' ' || lower(p.first_name) LIKE ('%' || ? || '%') ");
            args.add(criteria.getShareName().toLowerCase().replaceAll(" ", "%"));
            
        }

        if (criteria.getCommunityId() != null) {
            sb.append("and t.community_id=? ");
            args.add(criteria.getCommunityId());
        }

        if (criteria.getTemplateId() != null) {
            sb.append("and t.template_id=? ");
            args.add(criteria.getTemplateId());
        }

        if (criteria.getFolderId() != null) {
            sb.append("and v.folder_id=? ");
            args.add(criteria.getFolderId());
        }

        if (criteria.getFiled() != null) {
            if (criteria.getFiled()) {
                sb.append("and v.folder_id is not null ");
            } else {
                sb.append("and v.folder_id is null ");
            }
        }

        if (criteria.getFlagged() != null) {
            sb.append("and v.flag_status=? ");
            args.add(criteria.getFlagged() ? "flagged" : "notFlagged");
        }

        if (criteria.getRead() != null) {
            sb.append("and v.view_status=? ");
            args.add(criteria.getRead() ? "read" : "notRead");
        }

        if (criteria.isSharedPortfoliosOnly()) {
            sb.append("and (v.view_type=? OR sd.public_view=1) ");
            args.add(Viewer.ViewType.VIEWER.toString());
        }
        
        if(criteria.getTemplates() != null) {
        	
        	String qmarks = "";
        	
        	List<Template> templates = new ArrayList<Template>();
        	
        	templates.addAll(criteria.getTemplates());
        	
        	for(int i=0; i<(templates.size() - 1); ++i) {
        		qmarks += "?, ";
        	}
        	
        	qmarks += "?";
        	
        	sb.append("and t.is_deleted = 'n' AND t.template_id IN (");
        	sb.append(qmarks);
        	sb.append(")");
        	
        	for(int i=0; i<templates.size(); ++i) {
        		args.add(templates.get(i).getId());
        	}
        	
        }

        if (criteria.isMyPortfoliosOnly()) {
            sb.append("and v.view_type=? ");
            args.add(Viewer.ViewType.OWNER.toString());
        }

        if (criteria.getTags() != null) {
            String format = "and exists (select person_id from portfolio_tags pt where sd.share_id=pt.share_id and lower(pt.tag) in (%s)) ";
            String[] qs = new String[criteria.getTags().size()];
            Arrays.fill(qs, "?");
            String sql = String.format(format, ArrayUtil.join(qs, ","));
            sb.append(sql);

            List<String> lowercaseTags = new ArrayList<String>();
            for (String tag : criteria.getTags()) {
                lowercaseTags.add(tag.toLowerCase());
            }
            args.addAll(lowercaseTags);
        }

        if (criteria.getQuery() != null) {
            String[] tokens = criteria.getQuery().split("\\s");
            String regExArg = ".*" + ArrayUtil.join(tokens, ".*|.*") + ".*";
            sb.append("and (");
            sb.append("exists (select person_id from portfolio_tags pt where sd.share_id=pt.share_id and regexp_like(pt.tag, ?, 'i')) ");
            sb.append("or regexp_like(sd.share_name, ?, 'i') ");
            sb.append("or regexp_like(sd.share_desc, ?, 'i') ");
            sb.append("or regexp_like(p.username, ?, 'i') ");
            sb.append("or regexp_like(p.first_name, ?, 'i') ");
            sb.append("or regexp_like(p.lastname, ?, 'i') ");
            
            sb.append(") ");
            args.add(regExArg);
            args.add(regExArg);
            args.add(regExArg);
            args.add(regExArg);
            args.add(regExArg);
            args.add(regExArg);
        }
        
        SqlStringWithArgs sswa = new SqlStringWithArgs(sb.toString(), args);

    	return sswa;
    }
    
    private class SqlStringWithArgs {
    	private String sql;
    	private List<Object> args;
    	
    	public SqlStringWithArgs(String sql, List<Object> args) {
    		this.sql = sql;
    		this.args = args;
    	}
    	
    	public String getSQL() {
    		return sql;
    	}
    	
    	public List<Object> getArgs() {
    		return args;
    	}
    }

    public List<Portfolio> findBySearchCriteria(PortfolioSearchCriteria criteria) {

    	SqlStringWithArgs sql_query = buildSearchCriteria(criteria);
        
        return simpleJdbcTemplate.query(sql_query.getSQL(), shareRowMapper, sql_query.getArgs().toArray());

    }
    
    public int findSizeBySearchCriteria(PortfolioSearchCriteria criteria) {
    	criteria.setReturnType(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_SIZE);
    	
    	SqlStringWithArgs sql_query = buildSearchCriteria(criteria);
    	
    	//logService.debug("===> [SIZE] " + sql_query.getSQL());
    	
    	int value = simpleJdbcTemplate.queryForInt(sql_query.getSQL(), sql_query.getArgs().toArray());
    	
    	return value;
    }

    public boolean isPublic(String portfolioId) {
        return simpleJdbcTemplate.queryForInt(
                "select count(*) from share_definition where share_id = ? AND sd.is_deleted = 'f' and public_view=1",
                portfolioId) > 0;
    }

	@Override
	public List<BarePortfolio> findBarePortfoliosBySearchCriteria(PortfolioSearchCriteria criteria) {
		
		criteria.setReturnType(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_BARE);
		SqlStringWithArgs sql_query = buildSearchCriteria(criteria);
        
		//logService.debug("===> [BARE] " + sql_query.getSQL());
		
        return simpleJdbcTemplate.query(sql_query.getSQL(), bareShareRowMapper, sql_query.getArgs().toArray());
	}

	@Override
	public List<Portfolio> findByBarePortfolioList(List<BarePortfolio> b_p) {
		
		if(b_p != null && b_p.size() > 0) {
			
			String sql = "SELECT sd.* FROM share_definition sd WHERE sd.is_deleted = 'f' AND share_id IN (SHAREIDS)";
			
			final String q_mark = "?  ";
			
			String q_marks = StringUtils.repeat(q_mark, b_p.size()).replaceAll(" +$", "").replaceAll("  ", ", ");
			
			sql = sql.replace("SHAREIDS", q_marks);
			
			List<BigDecimal> ids = new ArrayList<BigDecimal>();
			BarePortfolio p[] = b_p.toArray(new BarePortfolio[0]);
			
			for(int i=0; i<b_p.size(); ++i) {
				ids.add(new BigDecimal(p[i].getId()));
			}
			
			return simpleJdbcTemplate.query(sql, shareRowMapper, ids.toArray());
		}
		
		return null;
	}

}

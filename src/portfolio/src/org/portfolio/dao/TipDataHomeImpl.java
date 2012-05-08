/* $Name:  $ */
/* $Id: TipDataHomeImpl.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.portfolio.model.TipData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("tipDataHome")
public class TipDataHomeImpl implements TipDataHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
	/** holds all tip data keyed by page name */
	private Map<String, List<TipData>> cache;

	@PostConstruct
	public void init() {
		cache = createCacheMap();
	}

	private final RowMapper<TipData> rowMapper = new RowMapper<TipData>() {
		public TipData mapRow(ResultSet rs, int arg1) throws SQLException {
			TipData tipData = new TipData();
			tipData.setPageName(rs.getString("page_name"));
			tipData.setSeqNumber(rs.getString("seq_number"));
			tipData.setTipText(rs.getString("tip_text"));
			tipData.setTitle(rs.getString("title"));
			return tipData;
		}
	};

	private Map<String, List<TipData>> createCacheMap() {
		List<TipData> result = simpleJdbcTemplate.query(
				"select page_name, seq_number, tip_text, title from tip_data order by page_name, seq_number",
				rowMapper);
		Map<String, List<TipData>> cacheMap = new HashMap<String, List<TipData>>();
		for (TipData tipData : result) {
			List<TipData> tipDataByPage = cacheMap.get(tipData.getPageName());
			if (tipDataByPage == null) {
				tipDataByPage = new ArrayList<TipData>();
				cacheMap.put(tipData.getPageName(), tipDataByPage);
			}
			tipDataByPage.add(tipData);
		}
		return cacheMap;
	}

	public TipData findRandomByPageName(String pageName) {
	    List<TipData> tips = cache.get(pageName);
		if (tips == null) {
			return null;
		}

		int index = 0;
		if (tips.size() > 0) {
			index = (int) (Math.random() * tips.size());
		}
		return tips.get(index);
	}
}

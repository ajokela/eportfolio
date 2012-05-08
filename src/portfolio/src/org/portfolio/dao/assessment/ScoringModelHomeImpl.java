/* $Name:  $ */
/* $Id: ScoringModelHomeImpl.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.portfolio.model.assessment.ScoringModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("scoringModelHome")
public class ScoringModelHomeImpl implements ScoringModelHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private Map<Integer, ScoringModel> cache;

    @PostConstruct
    public void init() {
        cache = createCache();
    }

    private Map<Integer, ScoringModel> createCache() {
        RowMapper<ScoringModel> rowMapper = new RowMapper<ScoringModel>() {
            public ScoringModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ScoringModel scoreModel = new ScoringModel(resultSet.getInt("id"), resultSet.getString("name"), resultSet
                        .getString("description"), resultSet.getString("data_type"), resultSet.getString("value_type"), resultSet
                        .getString("value_set"), resultSet.getString("quantified_set"));
                return scoreModel;
            }
        };
        List<ScoringModel> scoreModels = simpleJdbcTemplate.query("SELECT * FROM at_scoring_model", rowMapper);
        Map<Integer, ScoringModel> map = new HashMap<Integer, ScoringModel>();
        for (ScoringModel scoreModel : scoreModels) {
            map.put(scoreModel.getId(), scoreModel);
        }
        return map;
    }

    public ScoringModel find(int scoreModelID) {
        return cache.get(scoreModelID);
    }

    public ScoringModel find(String scoringModelIDString) {
        return find(Integer.parseInt(scoringModelIDString));
    }

    public List<ScoringModel> find() {
        List<ScoringModel> scoringModelList = new ArrayList<ScoringModel>(cache.values());
        Collections.sort(scoringModelList);
        return scoringModelList;
    }

    public void insert(
            String scoringModelId,
            String name,
            String description,
            String dataType,
            String valueType,
            String valueSet,
            String quantifiedSet) {
        String sql = "insert into at_scoring_model (scoring_model_id,name,description,data_type,value_type,value_set,quantified_set)"
                + " values (?,?,?,?,?,?,?)";
        simpleJdbcTemplate.update(sql, scoringModelId, name, description, new Timestamp(System.currentTimeMillis()));
    }

    public void delete(String scoringModelId) {
        String sql = "delete from at_scoring_model where scoring_model_id=?";
        simpleJdbcTemplate.update(sql, scoringModelId);
    }
}

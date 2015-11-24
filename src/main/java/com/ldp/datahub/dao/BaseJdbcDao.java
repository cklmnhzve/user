package com.ldp.datahub.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Title :
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2014
 * <p/>
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    15.9.26    bob        Created</pre>
 * <p/>
 *
 * @author bob
 */
public class BaseJdbcDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public int save(String sql,Object[] param){
    	jdbcTemplate.update(sql,param);
    	String idSql = "SELECT LAST_INSERT_ID() AS ID";
    	return jdbcTemplate.queryForObject(idSql,Integer.class);
    }
}

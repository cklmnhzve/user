package com.ldp.datahub.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ldp.datahub.dao.BaseJdbcDao;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.entity.Quota;

@Repository
public class QuotaDaoImpl extends BaseJdbcDao implements QuotaDao {
	private static boolean createdTable = false;
	
	@Override
	public Quota getQuota(int userId,String quotaName) {
		if(!createdTable&&!isExistTable()){
			creatTable();
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM DH_USER_QUOTA WHERE USER_ID=? AND QUOTA_NAME=?");
		List<Quota> qutas =  getJdbcTemplate().query(sql.toString(), new Object[]{userId,quotaName}, BeanPropertyRowMapper.newInstance(Quota.class));
		if(qutas.isEmpty()){
			return null;
		}
		return qutas.get(0);
	}
	@Override
	public void saveQuota(Quota quota) {
		if(!createdTable&&!isExistTable()){
			creatTable();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DH_USER_QUOTA (USER_ID,QUOTA_NAME,QUOTA_VALUE,USE_VALUE,UNIT,OP_TIME,OP_USER) ");
		sql.append("VALUES (?,?,?,?,?,?,?)");
		Object[] param = new Object[]
				{quota.getUserId(),quota.getQuotaName(),quota.getQuotaValue(),quota.getUseValue(),quota.getUnit(),quota.getOpTime(),quota.getOpUser()};
		getJdbcTemplate().update(sql.toString(), param);
		
	}
	@Override
	public void updateQuota(int value,int opUser,int user,String quotaName) {
		if(!createdTable&&!isExistTable()){
			creatTable();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE DH_USER_QUOTA ");
		sql.append("SET QUOTA_VALUE=?,OP_TIME=?,OP_USER=? ");
		sql.append("WHERE USER_ID=? AND QUOTA_NAME=? ");
		
		Object[] param = new Object[]{value,new Timestamp(System.currentTimeMillis()),opUser,user,quotaName};
		
		getJdbcTemplate().update(sql.toString(), param);
		
	}
	@Override
	public void updateQuotaUse(int add, int user, String quotaName) {
		if(!createdTable&&!isExistTable()){
			creatTable();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE DH_USER_QUOTA ");
		sql.append("SET USE_VALUE=USE_VALUE+? ");
		sql.append("WHERE USER_ID=? AND QUOTA_NAME=? ");
		
		Object[] param = new Object[]{add,user,quotaName};
		
		getJdbcTemplate().update(sql.toString(), param);
		
		
	}
	
	private void creatTable(){
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE DH_USER_QUOTA");
		sql.append("(");
		sql.append("USER_ID	VARCHAR(32) NOT NULL COMMENT '用户ID',");
		sql.append("QUOTA_NAME	VARCHAR(32) NOT NULL COMMENT '配额名称',");
		sql.append("QUOTA_VALUE	INT COMMENT '配额数量',");
		sql.append("USE_VALUE	FLOAT COMMENT '使用数量',");
		sql.append("UNIT	VARCHAR(32) COMMENT '单位',");
		sql.append("OP_TIME	TIMESTAMP,");
		sql.append("OP_USER	INT,");
		sql.append("PRIMARY KEY (USER_ID, QUOTA_NAME)");
		sql.append(");");
		getJdbcTemplate().execute(sql.toString());
		createdTable=true;
	}
	
	private boolean isExistTable(){
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT USER_ID FROM DH_USER_QUOTA LIMIT 1");
			getJdbcTemplate().queryForObject(sql.toString(),Integer.class);
		} catch (Exception e) {
			String msg = e.getMessage();
			if(msg.contains("Table 'datahub.DH_USER_QUOTA' doesn't exist")){
				return false;
			}
		}
		createdTable = true;
		return true;
	}
	@Override
	public void delete(int userId, String quotaName) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM DH_USER_QUOTA WHERE USER_ID=? AND QUOTA_NAME=?");
		getJdbcTemplate().update(sql.toString(),new Object[]{userId,quotaName});
		
	}

}

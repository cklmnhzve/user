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
	
	@Override
	public Quota getQuota(int userId,String quotaName) {
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
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DH_USER_QUOTA (USER_ID,QUOTA_NAME,QUOTA_VALUE,USE_VALUE,UNIT,OP_TIME,OP_USER) ");
		sql.append("VALUES (?,?,?,?,?,?,?)");
		Object[] param = new Object[]
				{quota.getUserId(),quota.getQuotaName(),quota.getQuotaValue(),quota.getUseValue(),quota.getUnit(),quota.getOpTime(),quota.getOpUser()};
		getJdbcTemplate().update(sql.toString(), param);
	}
	@Override
	public void updateQuota(int value,int opUser,int user,String quotaName) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE DH_USER_QUOTA ");
		sql.append("SET QUOTA_VALUE=?,OP_TIME=?,OP_USER=? ");
		sql.append("WHERE USER_ID=? AND QUOTA_NAME=? ");
		
		Object[] param = new Object[]{value,new Timestamp(System.currentTimeMillis()),opUser,user,quotaName};
		
		getJdbcTemplate().update(sql.toString(), param);
	}
	@Override
	public void updateQuotaUse(int add, int user, String quotaName) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE DH_USER_QUOTA ");
		sql.append("SET USE_VALUE=USE_VALUE+? ");
		sql.append("WHERE USER_ID=? AND QUOTA_NAME=? ");
		
		Object[] param = new Object[]{add,user,quotaName};
		
		getJdbcTemplate().update(sql.toString(), param);
	}
	

}

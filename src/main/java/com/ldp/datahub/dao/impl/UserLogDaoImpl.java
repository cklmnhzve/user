package com.ldp.datahub.dao.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.ldp.datahub.dao.BaseJdbcDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.entity.UserLog;

@Repository
public class UserLogDaoImpl extends BaseJdbcDao implements UserLogDao {

	private static boolean createdTable = false;
	@Override
	public void save(UserLog log) {
		checkAndCreateTable();
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DH_USER_LOG (OP_TABLE,OP_TYPE,CHANGE_USER,CHANGE_INFO,OP_TIME,OP_USER) ");
		sql.append("VALUES(?,?,?,?,?,?)");
		
		Object[] param = new Object[]{
				log.getOpTable(),log.getOpType(),log.getChangeUser(),log.getChangeInfo(),
				new Timestamp(System.currentTimeMillis()),log.getOpUser()
		};
		save(sql.toString(), param);
	}
	
	
	private void checkAndCreateTable(){
		if(createdTable){
			return;
		}
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT ID FROM DH_USER_LOG LIMIT 1");
			getJdbcTemplate().queryForObject(sql.toString(),Long.class);
		} catch (Exception e) {
			String msg = e.getMessage();
			if(msg.contains("Table")&&msg.contains("doesn't exist")){
				creatTable();
			}
		}
		createdTable = true;
	}
	
	private void creatTable(){
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE DH_USER_LOG");
		sql.append("(");
		sql.append("ID                   BIGINT NOT NULL,");
		sql.append("OP_TABLE             VARCHAR(32) COMMENT '被操作的表',");
		sql.append("OP_TYPE              INT COMMENT '操作类别,1： 新增；2 ：修改；3：删除',");
		sql.append("CHANGE_USER          INT COMMENT '被修改的用户',");
		sql.append("CHANGE_INFO          VARCHAR(500) COMMENT '修改信息',");
		sql.append("OP_TIME              TIMESTAMP COMMENT '操作时间',");
		sql.append("OP_USER              INT COMMENT '操作人',");
		sql.append("PRIMARY KEY (ID)");
		sql.append(");");
		getJdbcTemplate().execute(sql.toString());
		
		String alterSql = "ALTER TABLE DH_USER_LOG MODIFY ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;";
		getJdbcTemplate().execute(alterSql);
	}


	@Override
	public void delete(int changeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM DH_USER_LOG WHERE CHANGE_USER=?");
		getJdbcTemplate().update(sql.toString(),new Object[]{changeId});
		
	}

}

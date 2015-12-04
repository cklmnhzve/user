package com.ldp.datahub.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.Constant.PayWay;
import com.ldp.datahub.common.Constant.QutaName;
import com.ldp.datahub.common.Constant.Status;
import com.ldp.datahub.dao.BaseJdbcDao;
import com.ldp.datahub.dao.VipDao;
import com.ldp.datahub.entity.Vip;

@Repository
public class VipDaoImpl extends BaseJdbcDao implements VipDao {
	
	private static boolean createdTable = false;
	public static String TABLENAME = "DH_VIP";

	@Override
	public List<Vip> getVipQuota(int type) {
		checkAndCreateTable();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM DH_VIP WHERE USER_TYPE=?");
		return getJdbcTemplate().query(sql.toString(),new Object[]{type}, BeanPropertyRowMapper.newInstance(Vip.class));
	}

	
	private void createTable(){
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE DH_VIP");
		sql.append("(");
		sql.append("USER_TYPE	INT NOT NULL,");
		sql.append("NAME	VARCHAR(32) NOT NULL COMMENT '配额名称',");
		sql.append("VALUE	INT NOT NULL COMMENT '配额数量',");
		sql.append("UNIT	VARCHAR(32) COMMENT '单位',");
		sql.append("STATUS	INT,");
		sql.append("OP_TIME	TIMESTAMP,");
		sql.append("OP_USER	INT,");
		sql.append("PRIMARY KEY (USER_TYPE, NAME)");
		sql.append(");");
		getJdbcTemplate().execute(sql.toString());
	}
	
	private void checkAndCreateTable(){
		if(createdTable){
			return;
		}
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT USER_TYPE FROM DH_VIP LIMIT 1");
			getJdbcTemplate().queryForObject(sql.toString(),Integer.class);
		} catch (Exception e) {
			String msg = e.getMessage();
			if(msg.contains("Table")&&msg.contains("doesn't exist")){
				createTable();
				initData();
			}
		}
		createdTable = true;
	}
	@Override
	public void insert(Vip vip){
		checkAndCreateTable();
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DH_VIP (USER_TYPE,NAME,VALUE,UNIT,STATUS,OP_TIME,OP_USER)");
		sql.append(" VALUES(?,?,?,?,?,?,?)");
		Object[] param = new Object[]
				{vip.getUserType(),vip.getName(),vip.getValue(),vip.getUnit(),vip.getStatus(),
						new Timestamp(System.currentTimeMillis()),vip.getOpUser()};
		getJdbcTemplate().update(sql.toString(), param);
	}
	
	private void initData(){
		Vip quota1 = new Vip(QutaName.REPO_PUBLIC, 1, 0, "", Status.EFFECT, -1);
		Vip quota2 = new Vip(QutaName.REPO_PRIVATE, 1, 0, "", Status.EFFECT, -1);
		Vip quota3 = new Vip(QutaName.DEPOSIT, 1, 0, "M", Status.EFFECT, -1);
		Vip quota4 = new Vip(QutaName.PULL_NUM, 1, 500, "", Status.EFFECT, -1);
		Vip quota5 = new Vip(QutaName.PAY_WAY, 1, PayWay.BEFORE, "", Status.EFFECT, -1);
		Vip quota6 = new Vip(QutaName.FEE, 1, 0, "", Status.EFFECT, -1);
		insert(quota1);
		insert(quota2);
		insert(quota3);
		insert(quota4);
		insert(quota5);
		insert(quota6);
		
		//管理员 无限使用资源
		quota1 = new Vip(Constant.QutaName.REPO_PUBLIC, 2, -1, "", Constant.Status.EFFECT, -1);
		quota2 = new Vip(Constant.QutaName.REPO_PRIVATE, 2, -1, "", Constant.Status.EFFECT, -1);
		quota3 = new Vip(Constant.QutaName.DEPOSIT, 2, -1, "M", Constant.Status.EFFECT, -1);
		quota4 = new Vip(Constant.QutaName.PULL_NUM, 2, -1, "", Constant.Status.EFFECT, -1);
		quota5 = new Vip(QutaName.PAY_WAY, 2, PayWay.FREE, "", Status.EFFECT, -1);
		quota6 = new Vip(QutaName.FEE, 2, 0, "", Status.EFFECT, -1);
		insert(quota1);
		insert(quota2);
		insert(quota3);
		insert(quota4);
		insert(quota5);
		insert(quota6);
		
		quota1 = new Vip(Constant.QutaName.REPO_PUBLIC, 3, 10, "", Constant.Status.EFFECT, -1);
		quota2 = new Vip(Constant.QutaName.REPO_PRIVATE, 3, 0, "", Constant.Status.EFFECT, -1);
		quota3 = new Vip(Constant.QutaName.DEPOSIT, 3, 50, "M", Constant.Status.EFFECT, -1);
		quota4 = new Vip(Constant.QutaName.PULL_NUM, 3, 1000, "", Constant.Status.EFFECT, -1);
		quota5 = new Vip(QutaName.PAY_WAY, 3, PayWay.BEFORE, "", Status.EFFECT, -1);
		quota6 = new Vip(QutaName.FEE, 3, 0, "", Status.EFFECT, -1);
		insert(quota1);
		insert(quota2);
		insert(quota3);
		insert(quota4);
		insert(quota5);
		insert(quota6);
		
		quota1 = new Vip(Constant.QutaName.REPO_PUBLIC, 4, 20, "", Constant.Status.EFFECT, -1);
		quota2 = new Vip(Constant.QutaName.REPO_PRIVATE, 4, 2, "", Constant.Status.EFFECT, -1);
		quota3 = new Vip(Constant.QutaName.DEPOSIT, 4, 200, "M", Constant.Status.EFFECT, -1);
		quota4 = new Vip(Constant.QutaName.PULL_NUM, 4, 2000, "", Constant.Status.EFFECT, -1);
		quota5 = new Vip(QutaName.PAY_WAY, 4, PayWay.BEFORE, "", Status.EFFECT, -1);
		quota6 = new Vip(QutaName.FEE, 4, 10000, "", Status.EFFECT, -1);
		insert(quota1);
		insert(quota2);
		insert(quota3);
		insert(quota4);
		insert(quota5);
		insert(quota6);
		
		quota1 = new Vip(Constant.QutaName.REPO_PUBLIC, 5, 50, "", Constant.Status.EFFECT, -1);
		quota2 = new Vip(Constant.QutaName.REPO_PRIVATE, 5, 20, "", Constant.Status.EFFECT, -1);
		quota3 = new Vip(Constant.QutaName.DEPOSIT, 5, 2048, "M", Constant.Status.EFFECT, -1);
		quota4 = new Vip(Constant.QutaName.PULL_NUM, 5, 5000, "", Constant.Status.EFFECT, -1);
		quota5 = new Vip(QutaName.PAY_WAY, 5, PayWay.AFTER, "", Status.EFFECT, -1);
		quota6 = new Vip(QutaName.FEE, 5, 20000, "", Status.EFFECT, -1);
		insert(quota1);
		insert(quota2);
		insert(quota3);
		insert(quota4);
		insert(quota5);
		insert(quota6);
	}


	@Override
	public void delete(int type) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM DH_VIP WHERE USER_TYPE=?");
		getJdbcTemplate().update(sql.toString(),new Object[]{type});
		
	}

}

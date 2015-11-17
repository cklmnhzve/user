package com.ldp.datahub.dao.impl;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ldp.datahub.dao.BaseJdbcDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;

@Repository
public class UserDaoImpl extends BaseJdbcDao implements UserDao
{

	

	public int userLogng(User user)
	{
		return 1;
	}

	@Override
	public User getUser(String loginName) 
	{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM DH_USER WHERE LOGIN_NAME=?");
		List<User> users =  getJdbcTemplate().query(sql.toString(), new Object[]{loginName}, BeanPropertyRowMapper.newInstance(User.class));
		
		 if(users.isEmpty()){
			 return null;
		 }
		 
		 return users.get(0);
//		 return(User) getJdbcTemplate().queryForObject(sql.toString(), new Object[]{loginName}, BeanPropertyRowMapper.newInstance(User.class));
		
	}
	
	@Override
	public void insertUser(User user) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DH_USER (LOGIN_NAME,LOGIN_PASSWD,USER_STATUS,USER_TYPE,NICK_NAME,OP_TIME) VALUES(?,?,?,?,?,?)");
		Object[] param = new Object[]{user.getLoginName(),user.getLoginPasswd(),user.getUserStatus(),user.getUserType(),user.getNickName(),user.getOpTime()};
		getJdbcTemplate().update(sql.toString(), param);
		
	}
	
	public boolean isExist(String loginName){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LOGIN_NAME FROM DH_USER WHERE LOGIN_NAME=?");
		return getJdbcTemplate().query(sql.toString(), new Object[]{loginName}, BeanPropertyRowMapper.newInstance(String.class)).size()>0;
	}
	
	/**
	 * 根据用户名修改用户
	 */
	@Override
	public int updateUser(User user)
	{
//		int t = session.update("user.updateUser", user);
		return 0;
	}

	/**
	 * 根据用户名删除用户
	 */
	@Override
	public int deleteUser(User user)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 用户登录
	 */
	@Override
	public int userLogin(User user)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	

}

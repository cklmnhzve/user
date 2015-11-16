package com.ldp.datahub.dao.impl;


import javax.activation.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ldp.datahub.dao.BaseJdbcDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;

@Repository
public class UserDaoImpl extends BaseJdbcDao implements UserDao
{
//	@Autowired
//	protected SqlSessionTemplate session;
//	@Autowired
//	protected DataSource dataSource;

	

	public int userLogng(User user)
	{
		return 1;
	}

	@Override
	public User getUser(String loginName) 
	{
//		 User user = new User();
//		 user.setLoginName(userName);
//		 session.getConfiguration().getEnvironment().getDataSource();
//		 
//		 user = session.selectOne("user.searchUser", user);
//		 return user;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM DH_USER WHERE LOGIN_NAME=?");
		return(User) getJdbcTemplate().queryForObject(sql.toString(), new Object[]{loginName}, BeanPropertyRowMapper.newInstance(User.class));
		
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

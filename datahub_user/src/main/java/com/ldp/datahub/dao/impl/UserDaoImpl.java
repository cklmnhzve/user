package com.ldp.datahub.dao.impl;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;

@Repository
public class UserDaoImpl implements UserDao
{
	@Autowired
	protected SqlSessionTemplate session;


	public int userLogng(User user)
	{
		return 1;
	}

	@Override
	public User getUser(String userName) 
	{
		 User user = new User();
		 user.setLoginName(userName);
		 
		 user = session.selectOne("user.searchUser", user);
		 return user;
		
	}

	/**
	 * 根据用户名修改用户
	 */
	@Override
	public int updateUser(User user)
	{
		int t = session.update("user.updateUser", user);
		return t;
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

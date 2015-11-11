package com.ldp.datahub.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

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

	@Autowired
	private DataSource dataSource;

	public int userLogng(User user)
	{
		return 1;
	}

	@Override
	public User getOneUser(User user) 
	{


		 user = session.selectOne("user.getAlluser", user);
		/*
		 * Connection con = session.getConnection(); System.out.println(con);
		 * System
		 * .out.println("session +++++++++++++++++++++++++++++++++++++++++++++"
		 * );
		 */
		 System.out.println("user:  " + user);
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

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

	public int userLoing(User user)
	{
		return 1;
	}

	@Override
	public User getOneUser(User user) throws SQLException
	{
		user = new User();
		user.setLoginName("lxy");
		System.out.println("dao 成 =====================");
		Connection c = dataSource.getConnection();
		System.out.println(c);

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

}

package com.ldp.datahub.dao.impl;

import java.sql.Connection;

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
	
	public int userLoing(User user)
	{
		return 1;
	}
	
	@Override
	public User getOneUser(User user)
	{
		//return  session.selectOne("user.getAlluser", user);
		Connection con = session.getConnection();
		System.out.println(con);
		return null;
	}
	
}

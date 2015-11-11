package com.ldp.datahub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;
	
	
	public User getUser(String userName) 
	{
		return userDao.getUser(userName);
	}


	/**
	 * 根据用户名修改用户
	 */
	@Override
	public int updateUser(User user)
	{
		int t = userDao.updateUser(user);
		return t;
	}


	@Override
	public int deleteUser(User user)
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int userLogin(User user)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}

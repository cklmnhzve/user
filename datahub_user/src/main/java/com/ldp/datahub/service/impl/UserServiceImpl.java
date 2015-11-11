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
	
	
	public User getOneUser(User user)
	{
		return userDao.getOneUser(user);
	}
}

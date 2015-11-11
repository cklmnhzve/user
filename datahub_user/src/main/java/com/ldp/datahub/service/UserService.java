package com.ldp.datahub.service;

import java.sql.SQLException;

import com.ldp.datahub.entity.User;

public interface UserService
{
	public User getOneUser(User user) throws SQLException;
}

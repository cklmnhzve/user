package com.ldp.datahub.dao;

import java.sql.SQLException;

import com.ldp.datahub.entity.User;

public interface UserDao
{

	User getOneUser(User user) throws SQLException;

}

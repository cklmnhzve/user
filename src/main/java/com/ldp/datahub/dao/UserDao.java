package com.ldp.datahub.dao;


import com.ldp.datahub.entity.User;

public interface UserDao
{

	/**
	 * 根据用户名查询用户
	 * @param user
	 * @return
	 */
	public User getUser(String userName);
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insertUser(User user);
	
	public boolean isExist(String loginName);
	
	/**
	 * 根据用户名修改用户
	 */
	public int updateUser(User user);
	
	/**
	 * 根据用户名删除用户
	 */
	public int deleteUser(User user);
	
	/**
	 * 用户登录
	 */
	public int userLogin(User user);

}

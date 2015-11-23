package com.ldp.datahub.dao;

import com.ldp.datahub.entity.User;

public interface UserDao
{

	/**
	 * 根据用户名查询用户
	 * @param user
	 * @return
	 */
	public User getUser(String loginName);
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insertUser(User user);
	
	public boolean isExist(String loginName);
	
	public void updateStatus(String loginName,int status,int oldStatus);
	
	public void updatePwd(String loginName,String pwd);
	
	/**
	 * 根据用户名修改用户
	 */
	public void updateUser(User user);
	
	void delete(int id);
	
	void delete(String loginName);

	String getPwd(String loginName);

}

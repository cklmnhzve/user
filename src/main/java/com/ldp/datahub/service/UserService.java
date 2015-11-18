package com.ldp.datahub.service;

import com.ldp.datahub.entity.User;
import com.ldp.datahub.vo.UserVo;

public interface UserService
{
	/**
	 * 根据用户名查询用户
	 * @param user
	 * @return
	 */
	public UserVo getUser(String loginName);
	
	public String creatUser(String loginName,String pwd);
	
	public boolean updatePwd(String loginName,String oldPwd,String newPwd);
	
	
	public int getUserType(String loginName);
	
	/**
	 * 根据用户名修改用户
	 */
	public void updateUser(User user);
	
	/**
	 * 根据用户名删除用户
	 */
	public void deleteUser(String loginName);

	public void activeUser(String loginName);
	
}

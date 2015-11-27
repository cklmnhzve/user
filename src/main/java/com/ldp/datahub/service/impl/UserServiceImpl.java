package com.ldp.datahub.service.impl;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.service.QuotaService;
import com.ldp.datahub.service.UserService;
import com.ldp.datahub.vo.UserVo;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuotaService quotaService;
	
	@Override
	public UserVo getUser(String loginName) 
	{
		User user=  userDao.getUser(loginName);
		if(user==null){
			return null;
		}
		UserVo vo = new UserVo();
		vo.setNickName(user.getNickName());
		vo.setComment(user.getSummary());
		vo.setUserName(user.getUserName());
		vo.setUserType(user.getUserType());
		vo.setUserStatus(user.getUserStatus());
		return vo;
	}
	
	@Override
	public void activeUser(String loginName){
		userDao.updateStatus(loginName, Constant.userStatus.ACTIVE,Constant.userStatus.NO_ACTIVE);
	}
	

	@Override
	@Transactional
	public String creatUser(String loginName, String pwd) {
		
		if(userDao.isExist(loginName)){
			return Constant.exist_user;
		}
		User user = new User();
		user.setLoginName(loginName);
		user.setNickName(loginName.substring(0,loginName.indexOf("@")));
		user.setLoginPasswd(pwd);
		user.setUserStatus(Constant.userStatus.NO_ACTIVE);
		user.setUserType(Constant.UserType.REGIST);
		user.setOpTime(new Timestamp(System.currentTimeMillis()));

		int userId = userDao.insertUser(user);
		
		//增加quota信息
		quotaService.saveRepo(userId, -1, 0, 0);
		quotaService.saveQuota(userId, -1, 0, Constant.QutaName.DEPOSIT, "M");
		quotaService.saveQuota(userId, -1, 500, Constant.QutaName.PULL_NUM, "");
		
		return "";
	}


	/**
	 * 根据用户名修改用户
	 */
	@Override
	public void updateUser(User user)
	{
		userDao.updateUser(user);
	}


	@Override
	public void deleteUser(String loginName,int status)
	{
		userDao.updateStatus(loginName, Constant.userStatus.DESTROY,status);
	}

	@Override
	public boolean updatePwd(String loginName, String oldPwd, String newPwd) {
		String pwd =userDao.getPwd(loginName);
		if(pwd!=null&&StringUtils.isNotEmpty(newPwd)&&pwd.equals(oldPwd)){
			userDao.updatePwd(loginName, newPwd);
			return true;
		}
		return false;
	}

	@Override
	public int getUserType(String loginName) {
		return userDao.getUser(loginName).getUserType();
	}

	@Override
	public int getUserId(String loginName) {
		
		return userDao.getUserId(loginName);
	}


	


}

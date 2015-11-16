package com.ldp.datahub.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.util.CodecUtil;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.service.UserService;
import com.ldp.datahub.vo.UserVo;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;
	
	
	public UserVo getUser(String loginName) 
	{
		User user=  userDao.getUser(loginName);
		UserVo vo = new UserVo();
		vo.setNickName(user.getNickName());
		vo.setComment(user.getSummary());
		vo.setUserName(user.getUserName());
		vo.setUserType(user.getUserType());
		
		return vo;
	}
	
	

	@Override
	public String creatUser(String loginName, String pwd) {
		
		if(userDao.isExist(loginName)){
			return Constant.exist_user;
		}
		User user = new User();
		user.setLoginName(loginName);
		user.setNickName(loginName.substring(loginName.indexOf("@")));
		user.setLoginPasswd(CodecUtil.MD5(pwd));
		user.setUserStatus(Constant.userStatus.NO_ACTIVE);
		user.setUserType(Constant.userType.common);
		user.setOpTime(new Timestamp(System.currentTimeMillis()));

		userDao.insertUser(user);
		return "";
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


	


}

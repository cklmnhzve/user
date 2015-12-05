package com.ldp.datahub.service.impl;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.dao.impl.UserDaoImpl;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.entity.UserLog;
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
	@Autowired
	private UserLogDao userLogDao;
	@Autowired
	private QuotaDao quotaDao;
	
	@Override
	public UserVo getUser(String loginName) 
	{
		User user=  userDao.getUser(loginName);
		if(user==null){
			return null;
		}
		UserVo vo = new UserVo();
		vo.setUserId(user.getUserId());
		vo.setNickName(user.getNickName());
		vo.setComment(user.getSummary());
		vo.setUserName(user.getUserName());
		vo.setUserType(user.getUserType());
		vo.setUserStatus(user.getUserStatus());
		return vo;
	}
	
	@Override
	public void activeUser(String loginName,int opUser){
		userDao.updateStatus(loginName, Constant.userStatus.ACTIVE);
		
		UserLog ulog = new UserLog();
		ulog.setChangeUser(getUserId(loginName));
		ulog.setChangeInfo("userStatus:"+Constant.userStatus.ACTIVE);
		ulog.setOpTable(UserDaoImpl.tableName);
		ulog.setOpType(Constant.OpType.UPDATE);
		ulog.setOpUser(opUser);
		userLogDao.save(ulog);
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
	public void updateUser(User user,int opUser)
	{
		userDao.updateUser(user);
		
		UserLog ulog = new UserLog();
		ulog.setChangeUser(getUserId(user.getLoginName()));
		
		ulog.setChangeInfo(findChangeInfo(user));
		
		ulog.setOpTable(UserDaoImpl.tableName);
		ulog.setOpType(Constant.OpType.UPDATE);
		ulog.setOpUser(opUser);
		userLogDao.save(ulog);
	}
	
	private String findChangeInfo(User user){
		StringBuilder info = new StringBuilder();
		if(StringUtils.isNotEmpty(user.getLoginPasswd())){
			info.append("loginPasswd:"+user.getLoginPasswd()+";");
		}
		if(StringUtils.isNotEmpty(user.getNickName())){
			info.append("nickName:"+user.getNickName()+";");
		}
		if(StringUtils.isNotEmpty(user.getSummary())){
			info.append("summary:"+user.getSummary()+";");
		}
		if(StringUtils.isNotEmpty(user.getUserName())){
			info.append("userName:"+user.getUserName()+";");
		}if(user.getUserStatus()>0){
			info.append("userStatus:"+user.getUserStatus()+";");
		}if(user.getUserType()>0){
			info.append("userType:"+user.getUserType()+";");
		}
		
		return info.toString();
	}


	@Override
	public void deleteUser(String loginName,int opUser)
	{
		UserLog ulog = new UserLog();
		ulog.setChangeUser(getUserId(loginName));
		ulog.setChangeInfo("删除");
		ulog.setOpTable(UserDaoImpl.tableName);
		ulog.setOpType(Constant.OpType.DELETE);
		ulog.setOpUser(opUser);
		userLogDao.save(ulog);
		
		userDao.updateStatus(loginName, Constant.userStatus.DESTROY);
		
		if(loginName.equals(Constant.testUser)){
			//测试用户，删除所有测试信息
			int testID =userDao.getUserId(loginName);
			userDao.delete(testID);
			
			quotaDao.delete(testID);
			userLogDao.delete(testID);
		}
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

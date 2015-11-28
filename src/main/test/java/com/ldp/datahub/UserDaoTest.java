package com.ldp.datahub;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;



@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("classpath:/applicationContext.xml") 

public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;		private int testID = -1;
	
	private SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void testGetAndInsert(){
		
		User user = makeUser();
		userDao.insertUser(user);
		
		try {
			User actual = userDao.getUser(user.getLoginName());
			Assert.assertEquals(user.getLoginPasswd(), actual.getLoginPasswd());  
			Assert.assertEquals(user.getLoginName(),actual.getLoginName());  
			Assert.assertEquals(user.getNickName(),actual.getNickName());  
			Assert.assertEquals(df.format(user.getOpTime()), df.format(actual.getOpTime())); 
			Assert.assertEquals(user.getSummary(), actual.getSummary()); 
			Assert.assertEquals(user.getUserName(), actual.getUserName()); 
			Assert.assertEquals(user.getUserStatus(), actual.getUserStatus()); 
			Assert.assertEquals(user.getUserType(), actual.getUserType()); 
			
		}finally {
			userDao.delete(testID);
		}
		
	}	
	@Test
	public void testUpdatePwd(){
		User user = makeUser();
		userDao.insertUser(user);
		
		try {
			String pwd = "abcdef";
			userDao.updatePwd(user.getLoginName(), pwd);
			User actual = userDao.getUser(user.getLoginName());
			Assert.assertEquals(pwd, actual.getLoginPasswd());
		} finally {
			userDao.delete(testID);
		}
	}
		@Test
	public void testUpdateStatus(){
		User user = makeUser();
		userDao.insertUser(user);
		try {			//普通状态修改
			int newStatus = Constant.userStatus.AUTHORIZE;
			userDao.updateStatus(user.getLoginName(), newStatus);
			User actual = userDao.getUser(user.getLoginName());			Assert.assertEquals(newStatus, actual.getUserStatus());			//修改成注销状态			int oldStatus = newStatus;			newStatus = Constant.userStatus.DESTROY;			userDao.updateStatus(user.getLoginName(), newStatus);			actual = userDao.getUser(user.getLoginName());			Assert.assertNull(actual);
		} finally {
			userDao.delete(testID);
		}
	}
		@Test	public void testDelete(){		User user = makeUser();		userDao.insertUser(user);		try {			userDao.delete(user.getLoginName());			User actual = userDao.getUser(user.getLoginName());			Assert.assertNull(actual);		} finally {			userDao.delete(testID);		}	}
	
	private User makeUser(){
		User user = new User();
		user.setLoginName("test@test.com");
		user.setLoginPasswd("abc");
		user.setNickName("liuliu");
		user.setOpTime(new Timestamp(System.currentTimeMillis()));
		user.setUserName("测试账号");
		user.setUserStatus(1);
		user.setUserType(1);
		user.setSummary("ceshi");		user.setUserId(testID);
		return user;
	}

}

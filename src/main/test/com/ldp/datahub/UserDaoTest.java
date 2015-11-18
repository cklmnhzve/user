package com.ldp.datahub;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.entity.User;



@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("classpath:/applicationContext.xml") 

public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	private SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void testGetAndInsert(){
		
		User user = makeUser();
		userDao.insertUser(user);
		
		int id = 0;
		
		try {
			User actual = userDao.getUser(user.getLoginName());
			id = actual.getUserId();
			Assert.assertEquals(user.getLoginPasswd(), actual.getLoginPasswd());  
			Assert.assertEquals(user.getLoginName(),actual.getLoginName());  
			Assert.assertEquals(user.getNickName(),actual.getNickName());  
			Assert.assertEquals(df.format(user.getOpTime()), df.format(actual.getOpTime())); 
			Assert.assertEquals(user.getSummary(), actual.getSummary()); 
			Assert.assertEquals(user.getUserName(), actual.getUserName()); 
			Assert.assertEquals(user.getUserStatus(), actual.getUserStatus()); 
			Assert.assertEquals(user.getUserType(), actual.getUserType()); 
			
			
		} catch (Exception e) {
			
		}finally {
			userDao.delete(id);
		}
		
	}
	
	
	private User makeUser(){
		User user = new User();
		user.setLoginName("liuliu@126.com");
		user.setLoginPasswd("abc");
		user.setNickName("liuliu");
		user.setOpTime(new Timestamp(System.currentTimeMillis()));
		user.setUserName("测试账号");
		user.setUserStatus(1);
		user.setUserType(1);
		user.setSummary("ceshi");
		return user;
	}

}

package com.ldp.datahub;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ldp.datahub.action.UserAction;
import com.ldp.datahub.common.Constant;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.entity.User;

import net.sf.json.JSONObject;

@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/springMVC-servlet.xml", "file:src/test/resources/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserActionTest {
	
	@Autowired  
    private UserAction userAction; 
	@Autowired
	private UserDao userDao;
	private MockMvc mockMvc; 
	@Autowired
	private QuotaDao quotaDao;
	@Autowired
	private UserLogDao userLogDao;
	
	private static String loginName="test@asiainfo.com";
	private static String adminUser = "datahubtest@asiainfo.com";
	
	private static String pwd = "abcdefg";
	
	private static int userId;
	
	private static int adminId;

	@Before
	public void setUp() throws Exception {
		// 绑定需要测试的Controller到MockMvcshang
		mockMvc = MockMvcBuilders.standaloneSetup(userAction).build();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users/"+loginName).param("passwd", pwd))
				.andReturn();
		
		userId= userDao.getUserId(loginName);
		
		String pwd="1111111";
		User user  = new User();
		user.setLoginName(adminUser);
		user.setLoginPasswd(pwd);
		user.setUserStatus(2);
		user.setUserType(2);
		adminId = userDao.insertUser(user);
	}
	
	@After
	public void endTest(){
		quotaDao.delete(userId);
		userLogDao.delete(userId);
		userDao.delete(userId);
		
		userDao.delete(adminId);
	}
	
	@Test
	public void testPost() throws Exception{
		
		String loginName="test1@asiainfo.com";
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post("/users/"+loginName).param("passwd", pwd))
				.andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		 result =mockMvc.perform(MockMvcRequestBuilders.post("/users/"+loginName).param("passwd", pwd))
					.andReturn();
		 rs =result.getResponse().getContentAsString();
		 json = JSONObject.fromObject(rs);
		 Assert.assertEquals(Constant.exist_user_code,json.get("code"));
		 
		 int id =userDao.getUserId(loginName);
		 quotaDao.delete(id);
		 userLogDao.delete(id);
		 userDao.delete(loginName);
	}
	
	@Test  
    public void testGet() throws Exception {  
		
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.get("/users/"+loginName))
	            .andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		
		JSONObject data = json.getJSONObject("data");
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		Assert.assertEquals("test",data.getString("nickName"));
		Assert.assertEquals(1,data.getInt("userType"));
		Assert.assertEquals(1,data.getInt("userStatus"));
    }  
	@Test  
	public void testUpdateStatus() throws Exception{
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName+"/active"))
	            .andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_login_code,json.get("code"));
		
		result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName+"/active").header("user", loginName))
	            .andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_auth_code,json.get("code"));
		
		result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName+"/active").header("user", adminUser))
	            .andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		Assert.assertEquals(Constant.userStatus.ACTIVE,userDao.getUser(loginName).getUserStatus());
	}
	
	@Test
	public void testUpdatePwd() throws Exception{
		String newpwd="1234";
		String jsonRequset = "{\"passwd\":\""+newpwd+"\",\"oldpwd\":\"1234\"}";
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName+"/pwd")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes()))
	            .andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.wrong_pwd_code,json.get("code"));
		
		jsonRequset = "{\"passwd\":\""+newpwd+"\",\"oldpwd\":\""+pwd+"\"}";
		result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName+"/pwd")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes()))
	            .andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		Assert.assertEquals(newpwd,userDao.getPwd(loginName));
	}
	
	@Test
	public void testUpdateUser() throws Exception{
		String jsonRequset = "{\"userstatus\":\"1\",\"username\":\"FOO\",\"comments\":\"测试用户\",\"passwd\":\"lalala\"}";
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes()))
	            .andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_login_code,json.get("code"));
		
	   result =mockMvc.perform(MockMvcRequestBuilders.put("/users/"+loginName)
			   .header("user", adminUser)
			   .characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes()))
	            .andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		User user =userDao.getUser(loginName);
		Assert.assertEquals("FOO",user.getUserName());
	}
	
	@Test
	public void testDelete() throws Exception{
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+loginName)
				.header("user", loginName)
				.contentType(MediaType.APPLICATION_JSON))
	            .andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_auth_code,json.get("code"));
		
	   result =mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+loginName)
			   .header("user", adminUser)
			   .contentType(MediaType.APPLICATION_JSON))
	            .andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		Assert.assertEquals(Constant.userStatus.DESTROY,userDao.getStatus(loginName));
	}
	

}

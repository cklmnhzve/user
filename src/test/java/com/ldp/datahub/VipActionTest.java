package com.ldp.datahub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ldp.datahub.action.VipAction;
import com.ldp.datahub.common.Constant;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.entity.User;

import net.sf.json.JSONObject;

@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/springMVC-servlet.xml", "file:src/test/resources/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VipActionTest {

	@Autowired
	private UserDao userDao;
	@Autowired
	private QuotaDao quotaDao;
	@Autowired
	private UserLogDao userLogDao;
	@Autowired
	private VipAction vipAction;
	
	private MockMvc mockMvc; 
	private static String loginName="test@asiainfo.com";
	private static int userId;
	private static String adminUser = "datahubtest@asiainfo.com";
	private static int adminId;
	@Before
	public void setUp() throws Exception {
		// 绑定需要测试的Controller到MockMvcshang
		mockMvc = MockMvcBuilders.standaloneSetup(vipAction).build();
		
		User user = new User();
		user.setLoginName(loginName);
		user.setLoginPasswd("asdfg");
		userId = userDao.insertUser(user);
		
		String pwd="1111111";
		User user1  = new User();
		user1.setLoginName(adminUser);
		user1.setLoginPasswd(pwd);
		user1.setUserStatus(2);
		user1.setUserType(2);
		adminId = userDao.insertUser(user1);
	}
	
	@After
	public void endTest(){
		quotaDao.delete(userId);
		userLogDao.delete(userId);
		userDao.delete(userId);
		
		userDao.delete(adminId);
	}
	
	@Test
	public void testVip() throws Exception{
		String jsonRequset = "{\"userType\":\"3\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/vip/"+loginName)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_login_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.put("/vip/"+loginName)
				.header("user", adminUser)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/vip/"+loginName)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		JSONObject data = json.getJSONObject("data");
		Assert.assertEquals("50M",data.getString("deposit"));
		Assert.assertEquals(0,data.getInt("fee"));
		Assert.assertEquals(1,data.getInt("payWay"));
		Assert.assertEquals(1000,data.getInt("pullNum"));
		Assert.assertEquals(0,data.getInt("repoPri"));
		Assert.assertEquals(10,data.getInt("repoPub"));
		Assert.assertEquals(3,data.getInt("userType"));
		
		jsonRequset = "{\"userType\":\"4\"}";
		result = mockMvc.perform(MockMvcRequestBuilders.put("/vip/"+loginName)
				.header("user", adminUser)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/vip/"+loginName)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		data = json.getJSONObject("data");
		Assert.assertEquals("200M",data.getString("deposit"));
		Assert.assertEquals(10000,data.getInt("fee"));
		Assert.assertEquals(1,data.getInt("payWay"));
		Assert.assertEquals(2000,data.getInt("pullNum"));
		Assert.assertEquals(2,data.getInt("repoPri"));
		Assert.assertEquals(20,data.getInt("repoPub"));
		Assert.assertEquals(4,data.getInt("userType"));
	}
	
	public static void main(String[] args) {
		List<User> user = new ArrayList<User>();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		user.add(new User());
		user.add(new User());
		jsonMap.put("data", user);
		String json = JSONObject.fromObject(jsonMap).toString();
		System.err.println(json);
	}
}

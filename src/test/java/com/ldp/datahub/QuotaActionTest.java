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

import com.ldp.datahub.action.QuotaAction;
import com.ldp.datahub.common.Constant;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.entity.User;

import net.sf.json.JSONObject;

@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/springMVC-servlet.xml", "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class QuotaActionTest {
	
	@Autowired
	private QuotaAction quotaAction;
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuotaDao quotaDao;
	@Autowired
	private UserLogDao userLogDao;
	
	private MockMvc mockMvc; 
	private static String loginName="test@asiainfo.com";
	private static int userId;
	@Before
	public void setUp() throws Exception {
		// 绑定需要测试的Controller到MockMvcshang
		mockMvc = MockMvcBuilders.standaloneSetup(quotaAction).build();
		
		User user = new User();
		user.setLoginName(loginName);
		user.setLoginPasswd("asdfg");
		userId = userDao.insertUser(user);
		
	}
	
	@After
	public void endTest(){
		quotaDao.delete(userId);
		userLogDao.delete(userId);
		userDao.delete(userId);
	}
	
	@Test
	public void testDeposit() throws Exception{
		//测试添加depo
		String jsonRequset = "{ \"quota\":\"20\",\"unit\":\"M\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/deposit")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_login_code,json.get("code"));
		
		String admin="datahub@asiainfo.com";
		result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/deposit")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/deposit")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		JSONObject data = json.getJSONObject("data");
		Assert.assertEquals("20M",data.getString("quota"));
		Assert.assertEquals("0M",data.getString("use"));
		
		//测试修改depo
		jsonRequset = "{\"quota\":\"300\"}";
		result = mockMvc.perform(MockMvcRequestBuilders.put("/quota/"+loginName+"/deposit")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		//查询depo
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/deposit")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		data = json.getJSONObject("data");
		Assert.assertEquals("300M",data.getString("quota"));
		Assert.assertEquals("0M",data.getString("use"));
		
	}
	
	@Test
	public void testRepo() throws Exception{
		//测试添加repo
		String jsonRequset = "{\"private\":\"20\",\"public\":\"50\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/repository")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_login_code,json.get("code"));
		
		String admin="datahub@asiainfo.com";
		result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/repository")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/repository")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		JSONObject data = json.getJSONObject("data");
		Assert.assertEquals(50,data.getInt("quotaPublic"));
		Assert.assertEquals(20,data.getInt("quotaPrivate"));
		Assert.assertEquals(0,data.getInt("usePublic"));
		Assert.assertEquals(0,data.getInt("usePrivate"));
		
		//测试修改repo配额
		jsonRequset = "{\"private\":\"200\",\"public\":\"100\"}";
		result = mockMvc.perform(MockMvcRequestBuilders.put("/quota/"+loginName+"/repository")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/repository")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		data = json.getJSONObject("data");
		Assert.assertEquals(100,data.getInt("quotaPublic"));
		Assert.assertEquals(200,data.getInt("quotaPrivate"));
		Assert.assertEquals(0,data.getInt("usePublic"));
		Assert.assertEquals(0,data.getInt("usePrivate"));
		
		//测试修改使用量
		jsonRequset = "{\"private\":\"1\",\"public\":\"1\"}";
		result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/repository/use")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		//测试获取repo
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/repository")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		data = json.getJSONObject("data");
		Assert.assertEquals(1,data.getInt("usePublic"));
		Assert.assertEquals(1,data.getInt("usePrivate"));
	}
	
	@Test
	public void testPull() throws Exception{
		//测试添加Pull量
		String jsonRequset = "{\"quota\":\"2000\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/pullnum")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.no_login_code,json.get("code"));
		
		String admin="datahub@asiainfo.com";
		result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/pullnum")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/pullnum")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		JSONObject data = json.getJSONObject("data");
		Assert.assertEquals(2000,data.getInt("quota"));
		Assert.assertEquals(0,data.getInt("use"));
		
		//测试修改Pull量
		jsonRequset = "{\"quota\":\"4000\"}";
		result = mockMvc.perform(MockMvcRequestBuilders.put("/quota/"+loginName+"/pullnum")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/pullnum")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		data = json.getJSONObject("data");
		Assert.assertEquals(4000,data.getInt("quota"));
		Assert.assertEquals(0,data.getInt("use"));
		
		//测试修改使用量
		jsonRequset = "{\"use\":\"1\"}";
		result = mockMvc.perform(MockMvcRequestBuilders.post("/quota/"+loginName+"/pullnum/use")
				.header("user", admin)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		
		//测试获取Pull量
		result = mockMvc.perform(MockMvcRequestBuilders.get("/quota/"+loginName+"/pullnum")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequset.getBytes())).andReturn();
		rs =result.getResponse().getContentAsString();
		json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
		data = json.getJSONObject("data");
		Assert.assertEquals(4000,data.getInt("quota"));
		Assert.assertEquals(1,data.getInt("use"));
	}

}

package com.ldp.datahub;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ldp.datahub.action.UserAction;
import com.ldp.datahub.common.Constant;

import net.sf.json.JSONObject;

@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/springMVC-servlet.xml", "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserActionTest {
	
	@Autowired  
    private UserAction userAction; 
	private MockMvc mockMvc; 

	@Before
	public void setUp() throws Exception {
		// 绑定需要测试的Controller到MockMvcshang
		mockMvc = MockMvcBuilders.standaloneSetup(userAction).build();
	}
	
	@Test  
    public void testGet() throws Exception {  
		
		MvcResult result =mockMvc.perform(MockMvcRequestBuilders.get("/users/liuxy10@asiainfo.com"))
	            .andReturn();
		String rs =result.getResponse().getContentAsString();
		JSONObject json = JSONObject.fromObject(rs);
		Assert.assertEquals(Constant.sucess_code,json.get("code"));
    }  

}

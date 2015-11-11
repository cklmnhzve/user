package com.ldp.datahub.action;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.service.UserService;
import com.ldp.datahub.util.LogUtil;

/**
 * 表情 控制层类
 * 
 * @author: 罗振苏
 * @Date: 2014/10/23
 */

@Controller
public class UserAction
{
	private static Logger log = Logger.getLogger(UserAction.class);

	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response)
	{
	    //blogManager.removeById(id);
		System.out.println("id:  " + id);
		return null;
	}

	/**
	 * 用户登录
	 * 
	 * @throws SQLException
	 */
	// @RequestMapping(value = "/users/auth", method = RequestMethod.GET)
//	@ResponseBody
//	public void userLogin() throws SQLException
//	{
//
//		System.out.println("进入用户登录 ! ");
//		User user = new User();
//		User uu = userService.getOneUser(user);
//
//		System.out.println(uu);
//
//	}

	/**
	 * 根据用户名查询用户
	 * @throws IOException 
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	@ResponseBody
	public void getUser(@PathVariable String username,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		User user = userService.getUser(username);
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(user.toString());
			writer.write(json);

		}
		catch (IOException e) {
			LogUtil.loggerException(e);
		}
		finally {
			writer.close();
		}
		
		
		System.out.println("hello ldp user ! ");

	}

	/**
	 * 创建用户
	 */
	@RequestMapping(value = "/users/:username", method = RequestMethod.POST)
	@ResponseBody
	public void addtUser()
	{
		System.out.println("hello ldp user ! ");
	}

	/**
	 * 修改用户,put 方式
	 * @throws IOException 
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	@ResponseBody
	public void updateUser(@PathVariable String username,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		System.out.println("进入修改用户方法！   ");
		
		System.out.println("username:  " + username);
		
		User user = new User();
		user.setLoginName("1234@13.com");
		user.setNickName("myBatis");
		user.setSellLevel(99);
		int ss = userService.updateUser(user);
		String mess = null;
		if(ss == 1)
		{
			mess = "修改成功！";
		}
		if(ss <= 0)
		{
			mess = "修改用户失败！";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(mess);
			writer.write(json);

		}
		catch (IOException e) {
			LogUtil.loggerException(e);
		}
		finally {
			writer.close();
		}

	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/users/:username", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteUser()
	{
		System.out.println("hello ldp user ! ");
	}

}

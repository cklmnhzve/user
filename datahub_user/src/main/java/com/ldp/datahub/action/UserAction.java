package com.ldp.datahub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldp.datahub.entity.User;
import com.ldp.datahub.service.UserService;

/**
 * 表情 控制层类
 * 
 * @author: 罗振苏
 * @Date: 2014/10/23
 */

@Controller
public class UserAction
{
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/users/auth", method = RequestMethod.GET)
	@ResponseBody
	public void userLogin()
	{
		
		System.out.println("进入用户登录 ! ");
		User user = new User();
		User uu = userService.getOneUser(user);
		
		
		System.out.println(uu);
		
	}
	
	
	/**
	 * 根据用户名查询用户
	 */
	@RequestMapping(value = "/users/:username", method = RequestMethod.GET)
	@ResponseBody
	public void getUser()
	{
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
	 * 修改用户
	 */
	@RequestMapping(value = "/users/:username", method = RequestMethod.PUT)
	@ResponseBody
	public void updateUser()
	{
		System.out.println("hello ldp user ! ");
	}
	
	/**
	 *  删除用户
	 */
	@RequestMapping(value = "/users/:username", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteUser()
	{
		System.out.println("hello ldp user ! ");
	}
	
}

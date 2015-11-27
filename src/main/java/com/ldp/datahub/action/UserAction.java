package com.ldp.datahub.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.service.UserService;
import com.ldp.datahub.vo.UserVo;

import net.sf.json.JSONObject;

/**
 * 用户控制层类
 * 
 * @author: 刘雪莹
 * @Date: 2014/10/23
 */

@Controller
public class UserAction extends BaseAction
{
	private static Log log = LogFactory.getLog(UserAction.class);

	@Autowired
	private UserService userService;
	

	/**
	 * 根据用户名查询用户
	 * @throws IOException 
	 */
	@RequestMapping(value = "/users/{loginName:.*}", method = RequestMethod.GET)
	public  void getUser(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String json =null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			UserVo user = userService.getUser(loginName);
			log.info(me+" getUser:"+loginName);
			
			if(user!=null){
				jsonMap.put(Constant.result_code, Constant.sucess_code);
				jsonMap.put(Constant.result_msg, Constant.sucess);
				jsonMap.put(Constant.result_data, user);
				if(loginName.equals(me)){
					
				}
				
			}else{
				log.error(loginName+" 不存在");
				jsonMap.put(Constant.result_code, Constant.no_user_code);
				jsonMap.put(Constant.result_msg, loginName+" "+Constant.no_user);
				
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally{
			json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}

	/**
	 * 创建用户
	 */
	@RequestMapping(value = "/users/{loginName:.*}", method = RequestMethod.POST)
	public void addUser(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String pwd = request.getParameter("passwd");
			
			if(StringUtils.isNotEmpty(pwd)){
				String msg = userService.creatUser(loginName, pwd);
				if(StringUtils.isNotEmpty(msg)){
					jsonMap.put(Constant.result_code, Constant.exist_user_code);
					jsonMap.put(Constant.result_msg, Constant.exist_user);
				}else{
					jsonMap.put(Constant.result_code, Constant.sucess_code);
					jsonMap.put(Constant.result_msg, Constant.sucess);
				}
			}else{
				jsonMap.put(Constant.result_code, Constant.param_err_code);
				jsonMap.put(Constant.result_msg, Constant.param_err);
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}
		finally{
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
		
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/active", method = RequestMethod.PUT)
	public void activeUser(@PathVariable String loginName,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			userService.activeUser(loginName);
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}
		finally{
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	
	@RequestMapping(value = "/users/{loginName:.*}/pwd", method = RequestMethod.PUT)
	public void updatePwd(@PathVariable String loginName, @RequestBody String body,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			JSONObject requestJson = JSONObject.fromObject(body);
			String oldpwd="";
			String pwd="";
			try {
				oldpwd = (String)requestJson.get("oldpwd");
				pwd = (String)requestJson.get("passwd");
			} catch (Exception e) {
				jsonMap.put(Constant.result_code, Constant.param_err_code);
				jsonMap.put(Constant.result_msg, Constant.param_err);
				return;
			}
			
			boolean update = userService.updatePwd(loginName, oldpwd, pwd);
			if(update){
				jsonMap.put(Constant.result_code, Constant.sucess_code);
				jsonMap.put(Constant.result_msg, Constant.sucess);
			}else{
				jsonMap.put(Constant.result_code, Constant.wrong_pwd_code);
				jsonMap.put(Constant.result_msg, Constant.wrong_pwd);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}
		finally{
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}

	/**
	 * 修改用户,put 方式
	 * @throws IOException 
	 */
	@RequestMapping(value = "/users/{loginName:.*}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateUser(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		JSONObject requestJson = JSONObject.fromObject(body);
		try {
			String me = request.getHeader("USER");
			if(StringUtils.isEmpty(me)){
				log.error("请登录后再修改");
				jsonMap.put(Constant.result_code, Constant.no_login_code);
				jsonMap.put(Constant.result_msg, Constant.no_login);
			}else{
				int type=userService.getUserType(me);
				User user =  new User();
				user.setLoginName(loginName);
				user.setOpTime(new Timestamp(System.currentTimeMillis()));
				
				//普通
				Object nickname = requestJson.get("nickname");
				Object comments = requestJson.get("comments");
				Object passwd = requestJson.get("passwd");
				
				if(nickname!=null&&StringUtils.isNotEmpty(nickname.toString())){
					user.setNickName(nickname.toString());
				}
				if(comments!=null&&StringUtils.isNotEmpty(comments.toString())){
					user.setSummary(comments.toString());
				}
				if(passwd!=null&&StringUtils.isNotEmpty(passwd.toString())){
					user.setLoginPasswd(passwd.toString());
				}
				if(type==Constant.UserType.ADMIN){
					//管理员
					Object types = requestJson.get("usertype");
					Object status = requestJson.get("userstatus");
					Object username = requestJson.get("username");
					Object destoryed =  requestJson.get("destoryed");
					
					if(types!=null&&StringUtils.isNotEmpty(types.toString())){
						user.setUserType(Integer.parseInt(types.toString()));
					}
					if(status!=null&&StringUtils.isNotEmpty(status.toString())){
						user.setUserStatus(Integer.parseInt(status.toString()));
					}
					if(username!=null&&StringUtils.isNotEmpty(username.toString())){
						user.setUserName(username.toString());
					}
					
					if(destoryed!=null&&StringUtils.isNotEmpty(destoryed.toString())){
						if(user.getUserStatus()>0 && Integer.parseInt(destoryed.toString())==Constant.userStatus.DESTROY){
							UserVo uu = userService.getUser(loginName);
							if(uu!=null){
								log.info(me+" 修改用户："+loginName+",失败，已存在激活的用户");
								jsonMap.put(Constant.result_code, Constant.exist_user_code);
								jsonMap.put(Constant.result_msg, Constant.exist_user);
								return;
							}else{
								user.setDestoryed(true);
							}
						}
					}
				}else if(!me.equals(loginName)){
					log.info(me+" 修改用户："+loginName+",没有权限");
					jsonMap.put(Constant.result_code, Constant.no_auth_code);
					jsonMap.put(Constant.result_msg, Constant.no_auth);
					return;
				}
					log.info(me+" 修改用户："+loginName);
					userService.updateUser(user);
					jsonMap.put(Constant.result_code, Constant.sucess_code);
					jsonMap.put(Constant.result_msg, Constant.sucess);
				
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
		

	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/users/{loginName:.*}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteUser(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		JSONObject requestJson = JSONObject.fromObject(body);
		try {
			String me = request.getHeader("USER");
			if(StringUtils.isEmpty(me)){
				log.error("请登录后再操作");
				jsonMap.put(Constant.result_code, Constant.no_login_code);
				jsonMap.put(Constant.result_msg, Constant.no_login);
			}else{
				if(userService.getUserType(me)!=Constant.UserType.ADMIN){
					log.error(me+" 权限不够");
					jsonMap.put(Constant.result_code, Constant.no_auth_code);
					jsonMap.put(Constant.result_msg, Constant.no_auth);
				}else{
					log.info(me+" 删除用户："+loginName);
					String status =requestJson.getString("status");
					userService.deleteUser(loginName,Integer.parseInt(status));
					jsonMap.put(Constant.result_code, Constant.sucess_code);
					jsonMap.put(Constant.result_msg, Constant.sucess);
				}
				
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}
		finally{
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
	

}

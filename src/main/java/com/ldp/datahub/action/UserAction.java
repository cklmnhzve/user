package com.ldp.datahub.action;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.util.CodecUtil;
import com.ldp.datahub.service.UserService;
import com.ldp.datahub.vo.UserVo;

import net.sf.json.JSONObject;

/**
 * 表情 控制层类
 * 
 * @author: 罗振苏
 * @Date: 2014/10/23
 */

@Controller
@RequestMapping(value="/users")
public class UserAction extends BaseAction
{
//	private static Logger log = Logger.getLogger(UserAction.class);
	private static Log log = LogFactory.getLog(UserAction.class);

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
	 * 根据用户名查询用户
	 * @throws IOException 
	 */
	@RequestMapping(value = "/{loginName:.*}", method = RequestMethod.GET)
//	@ResponseBody
	public  void getUser(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String json =null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("user");
			UserVo user = userService.getUser(loginName);
			log.info(me+" getUser:"+loginName);
			
			if(user!=null){
				jsonMap.put(Constant.result_code, 0);
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
//		return json;
		
	}

	/**
	 * 创建用户
	 */
	@RequestMapping(value = "/{loginName:.*}", method = RequestMethod.POST)
	public void addtUser(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response)
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			String pwd = request.getParameter("passwd");
			
			if(StringUtils.isEmpty(pwd)){
				log.error("pwd is null");
				jsonMap.put(Constant.result_code, Constant.pwd_null_code);
				jsonMap.put(Constant.result_msg, Constant.pwd_null);
			}else{
				String msg = userService.creatUser(loginName, pwd);
				if(StringUtils.isNotEmpty(msg)){
					jsonMap.put(Constant.result_code, Constant.exist_user_code);
					jsonMap.put(Constant.result_msg, Constant.exist_user);
				}else{
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
	
	@RequestMapping(value = "/{loginName:.*}/status", method = RequestMethod.PUT)
	public void activeUser(@PathVariable String loginName, HttpServletResponse response){
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

	/**
	 * 修改用户,put 方式
	 * @throws IOException 
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateUser(@PathVariable String username,HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String name = CodecUtil.basic64Decode(username);
		log.info("update user:"+name);
		request.getParameter("");
		
//		System.out.println("进入修改用户方法！   ");
//		
//		System.out.println("username:  " + username);
//		
//		User user = new User();
//		user.setLoginName("1234@13.com");
//		user.setNickName("myBatis");
//		int ss = userService.updateUser(user);
//		String mess = null;
//		if(ss == 1)
//		{
//			mess = "修改成功！";
//		}
//		if(ss <= 0)
//		{
//			mess = "修改用户失败！";
//		}
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//		Writer writer = null;
//		try {
//			writer = response.getWriter();
//			ObjectMapper mapper = new ObjectMapper();
//			String json = mapper.writeValueAsString(mess);
//			writer.write(json);
//
//		}
//		catch (IOException e) {
//			LogUtil.loggerException(e);
//		}
//		finally {
//			writer.close();
//		}

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

package com.ldp.datahub.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.entity.VipVo;
import com.ldp.datahub.service.UserService;
import com.ldp.datahub.service.VipService;
import com.ldp.datahub.vo.UserVo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 会员控制层类
 * 
 * @author: 刘雪莹
 * @Date: 2014/10/23
 */

@Controller
public class VipAction extends BaseAction
{
	private static Log log = LogFactory.getLog(VipAction.class);

	@Autowired
	private VipService vipService;
	@Autowired
	private UserService userService;
	

	/**
	 * 根据用户名查询会员信息
	 * @throws IOException 
	 */
	@RequestMapping(value = "/vip/{loginName:.*}", method = RequestMethod.GET)
	public  void getVip(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String me = request.getHeader("USER");
			
			UserVo user = userService.getUser(loginName);
			if(user==null){
				log.info("用户不存在:"+loginName);
				jsonMap.put(Constant.result_code, Constant.no_user_code);
				jsonMap.put(Constant.result_msg, Constant.no_user);
				return ;
			}
			int userType = user.getUserType();
			
			VipVo vo = vipService.getUserVipQuota(userType);
			log.info(me+" getVipInfo:"+loginName);
			if(vo!=null){
				jsonMap.put(Constant.result_code, Constant.sucess_code);
				jsonMap.put(Constant.result_msg, Constant.sucess);
				jsonMap.put(Constant.result_data, vo);
			}else{
				log.error(loginName+" 会员信息 不存在");
				jsonMap.put(Constant.result_code, Constant.no_info_code);
				jsonMap.put(Constant.result_msg, Constant.no_info);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally{
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}

	@RequestMapping(value = "/vip/{loginName:.*}", method = RequestMethod.PUT)
	public void updateLevel(@PathVariable String loginName,@RequestBody String body,HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String me = request.getHeader("USER");
		
		try {
			
			boolean admin=isAdmin(me, jsonMap);
			if(!admin){
				return;
			}
			
			JSONObject requestJson = JSONObject.fromObject(body);
			String userType = requestJson.getString("userType");
			
			int meID = userService.getUserId(me);
			vipService.updateUserType(loginName, Integer.parseInt(userType), meID);
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
		}catch(JSONException je){
			log.error("修改<"+loginName+">会员级别失败，请求参数错误");
			jsonMap.put(Constant.result_code, Constant.param_err_code);
			jsonMap.put(Constant.result_msg, Constant.param_err);
		}
		catch (Exception e) {
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

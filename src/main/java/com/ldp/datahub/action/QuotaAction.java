package com.ldp.datahub.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ldp.datahub.common.Constant;

import net.sf.json.JSONObject;
@Controller
public class QuotaAction extends BaseAction{

	private static Log log = LogFactory.getLog(UserAction.class);
	
	@RequestMapping(value = "users/{loginName:.*}/repository", method = RequestMethod.GET)
	public void getRepo(@PathVariable String loginName,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			jsonMap.put(Constant.result_code, Constant.sucess_code);
			jsonMap.put(Constant.result_msg, Constant.sucess);
			jsonMap.put("quotaPublic", 100);
			jsonMap.put("usePublic", 10);
			
			jsonMap.put("quotaPrivate",3);
			jsonMap.put("usePrivate", 1);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonMap.put(Constant.result_code, Constant.fail_code);
			jsonMap.put(Constant.result_msg, Constant.exception);
		}finally {
			String json = JSONObject.fromObject(jsonMap).toString();
			sendJson(response, json);
		}
	}
}

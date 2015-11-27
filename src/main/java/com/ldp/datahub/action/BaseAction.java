package com.ldp.datahub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.service.UserService;

public class BaseAction {
	
	private static Log log = LogFactory.getLog(BaseAction.class);
	
	@Autowired
	private UserService userService;

	 public void sendJson(HttpServletResponse response, String text){
    	 PrintWriter write = null;  
    	 response.setContentType("text/json;charset=UTF-8");  
    	 response.setHeader("Pragma", "No-cache");  
    	 response.setHeader("Cache-Control", "no-cache");  
//    	 response.setDateHeader("Expires", 0);  
    	 try {  
    		 write = response.getWriter();  
    		 write.write(text);  
    		 write.flush();  
    	 } catch (IOException e) {  
    		 
    	 } finally {  
    		 if (write != null)  
    			 write.close();  
    		 write = null;  
    	 }  
    }
	 
	 /**
		 * 判断是否为管理员权限
		 * @param me
		 * @param jsonMap
		 * @return
		 */
		public boolean isAdmin(String me,Map<String, Object> jsonMap){
			if(StringUtils.isEmpty(me)){
				log.error("请登录后再修改");
				jsonMap.put(Constant.result_code, Constant.no_login_code);
				jsonMap.put(Constant.result_msg, Constant.no_login);
				return false;
			}else{
				if(userService.getUserType(me)!=Constant.UserType.ADMIN){
					log.error(me+" 权限不够");
					jsonMap.put(Constant.result_code, Constant.no_auth_code);
					jsonMap.put(Constant.result_msg, Constant.no_auth);
					return false;
				}
			}
			return true;
		}
		
		public boolean isAdminOrI(String me,String loginName,Map<String, Object> jsonMap){
			if(StringUtils.isEmpty(me)){
				log.error("请登录后再修改");
				jsonMap.put(Constant.result_code, Constant.no_login_code);
				jsonMap.put(Constant.result_msg, Constant.no_login);
				return false;
			}else{
				if(userService.getUserType(me)!=Constant.UserType.ADMIN && !me.equals(loginName)){
					log.error(me+" 权限不够");
					jsonMap.put(Constant.result_code, Constant.no_auth_code);
					jsonMap.put(Constant.result_msg, Constant.no_auth);
					return false;
				}
			}
			return true;
		}
}

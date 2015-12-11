package com.ldp.datahub.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.Constant.RechargeType;
import com.ldp.datahub.exception.LinkServerException;

import net.sf.json.JSONObject;

public class RequestUtil {
	private static Log log = LogFactory.getLog(RequestUtil.class);
	private static String host = "";
	private static Properties properties = new Properties();
	private static String adminUser = "";
	
	static{
		InputStream inStream = RequestUtil.class.getClassLoader()
				.getResourceAsStream("." + File.separator + "config.properties");
		try {
			properties.load(inStream);
			String server = System.getenv("API_SERVER");
			if(StringUtils.isEmpty(server)){
				host=properties.getProperty("SERVER_URL");
			}else{
				host = "http://"+server+":"+System.getenv("API_PORT");
			}
			adminUser=properties.getProperty("adminUser");
		} catch (IOException e) {
			log.error("Error loading configuration file", e);
		}
		
		
	}
	
	private static String getToken(){
		try {
			URL url = new URL(host+"/permission/mob");
			//打开restful链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 提交模式
			conn.setRequestMethod("GET");//POST GET PUT DELETE
			conn.setRequestProperty("Authorization", adminUser);
			//设置访问提交模式，表单提交
			conn.setRequestProperty("Content-Type","text/json;charset=UTF-8");
			conn.setConnectTimeout(1000);//连接超时 单位毫秒
			conn.setReadTimeout(1000);//读取超时 单位毫秒
			conn.setDoOutput(false);// 是否输入参数

			InputStream inStream=conn.getInputStream();
			byte[] bypes = new byte[2048];
			inStream.read(bypes, 0, inStream.available());
			String content = new String(bypes, "utf-8");
			JSONObject js= JSONObject.fromObject(content);
			return js.getString("token");
		} catch (Exception e) {
			log.error("get token from ngiux error", e);
			return null;
		}
		
	}
	
	public static boolean recharge(String userName,int amount) throws LinkServerException {
		 try {
			   String token = "";
			   String user = "datahub@asiainfo.com";
			    if(host.endsWith("8888")){
			    	token=getToken();
			    }
			    
			    URL url = new URL(host+"/bill/"+userName+"/recharge");
				//打开restful链接
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				          
				// 提交模式
				conn.setRequestMethod("PUT");//POST GET PUT DELETE
				if(StringUtils.isEmpty(token)){
					conn.setRequestProperty("user", user);
				}else{
					conn.setRequestProperty("Authorization", token);
				}
				
				//设置访问提交模式，表单提交
				conn.setRequestProperty("Content-Type","text/json;charset=UTF-8");
				conn.setConnectTimeout(1000);//连接超时 单位毫秒
				conn.setReadTimeout(1000);//读取超时 单位毫秒
				conn.setDoOutput(true);// 是否输入参数
				
				JSONObject js = new JSONObject();
				js.put("order_id", "vip_"+System.currentTimeMillis());
				js.put("amount", amount);
				js.put("type", RechargeType.vipcost);
				js.put("channel", "");
				conn.getOutputStream().write(js.toString().getBytes());

				InputStream inStream=conn.getInputStream();
				byte[] bypes = new byte[2048];
				inStream.read(bypes, 0, inStream.available());
				String content = new String(bypes, "utf-8");
				JSONObject result= JSONObject.fromObject(content);
				
				if(result.getInt(Constant.result_code)==Constant.sucess_code){
					return true;
				}else{
					return false;
				}
		} catch (Exception e) {
			log.error("requset recharge error-->userName:"+userName, e);
			throw new LinkServerException("requset recharge error:"+host);
		}
	 }
}

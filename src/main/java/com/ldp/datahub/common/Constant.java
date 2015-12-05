package com.ldp.datahub.common;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ldp.datahub.action.BaseAction;

public class Constant {
	private static Log log = LogFactory.getLog(Constant.class);
	
	public static final int sucess_code=0;
    public static final String sucess = "ok";
    
    public static final int fail_code=1001;
    public static final String exception = "unknown error";
    
    public static final String result_code="code";
    public static final String result_msg = "msg";
    public static final String result_data="data";
    
    public static final int param_err_code=1007;
    public static final String param_err="invalid parameters";
    
    public static final int no_info_code = 1009;
    public static final String no_info = "not found";
    
    
    public static final int no_user_code=6002;
    public static final String no_user = "user not registered yet";
    
    public static final int exist_user_code=8002;
    public static final String exist_user = "user exist";
    
    public static final int wrong_pwd_code=8004;
    public static final String wrong_pwd = "pwd wrong";
    
    public static final int no_login_code=8005;
    public static final String no_login="no login";
    
    public static final int no_auth_code=1005;
    public static final String no_auth="auth failed";
    
    public static final int no_quota_code=8001;
    public static final String no_quota = "no quota info";
    
    public static final int exist_quota_code=8006;
    public static final String exist_quota = "quota exist";
    
    private static Properties properties = new Properties();
   	public static String testUser="";
   	 static{
   		 try {
   			 InputStream inStream = BaseAction.class.getClassLoader()
   						.getResourceAsStream("." + File.separator + "config.properties");
   			 properties.load(inStream);
   			testUser = properties.getProperty("testUser");
   		} catch (Exception e) {
   			log.error("加载配置文件错误");
   		}
   	 }
   	 
    
    public static class userStatus{
    	public static int NO_ACTIVE=1; //未激活账户
		public static int ACTIVE= 2;//激活账户
		public static int AUTHENTICATE = 3; //认证用户
		public static int AUTHORIZE = 4 ;//授权用户
		public static int FREEZE = 5; //冻结账户
		public static int DESTROY= 7;//帐号销毁
		
    }
    
    public static class QutaName{
    	public static String REPO_PUBLIC = "repo_public";//共有repo
    	public static String REPO_PRIVATE = "repo_private";//私有repo
    	
    	public static String PULL_NUM = "pull_num"; //下载
    	public static String DEPOSIT = "deposit"; //托管
    	
    	public static String PAY_WAY ="pay_way";//付费方式
    	public static String FEE="fee";//年费
    }
    
    public static class PayWay{
    	public static int FREE = 0;//免费
    	public static int BEFORE=1;//预付费
    	public static int AFTER =2;//后付费
    }
    
    public static class Status{
    	public static int TO_AUDIT=1;//待审核
    	public static int EFFECT = 2;//生效
    	public static int FAILED = 3;//失效
    	public static int DELETE = 4;//删除
    }
    
    public static class UserType{
    	public static int REGIST=1;
    	public static int ADMIN=2;
    	public static int VIP1=3;
    	public static int VIP2 = 4;
    	public static int VIP3 = 5;
    }
    
    public static class OpType{
    	public static int ADD = 1;
    	public static int UPDATE = 2;
    	public static int DELETE = 3;
    }
  
}

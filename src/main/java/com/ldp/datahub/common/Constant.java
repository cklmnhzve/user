package com.ldp.datahub.common;


public class Constant {
    public static final String sucess = "ok";
    public static final int sucess_code=0;
    public static final String exception = "unknown error";
    public static final int fail_code=1001;
    
    public static final String result_code="code";
    public static final String result_msg = "msg";
    public static final String result_data="data";
    
    public static final String no_user = "user not registered yet";
    public static final int no_user_code=6002;
    public static final String exist_user = "user exist";
    public static final int exist_user_code=8002;
    public static final String pwd_null = "pwd is null";
    public static final int pwd_null_code=8003;
    public static final String wrong_pwd = "pwd wrong";
    public static final int wrong_pwd_code=8004;
    public static final String no_login="no login";
    public static final int no_login_code=8005;
    public static final String no_auth="auth failed";
    public static final int no_auth_code=1005;
    public static final String no_quota = "no quota info";
    public static final int no_quota_code=8001;
    public static final String exist_quota = "quota exist";
    public static final int exist_quota_code=8006;
    
    public static class userType{
    	public static int common =1; //普通用户
    	public static int admin =2; //管理员
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
    }
}

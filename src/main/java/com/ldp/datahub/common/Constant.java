package com.ldp.datahub.common;


public class Constant {
    public static final String sucess = "ok";
    public static final int sucess_code=0;
    public static final String exception = "服务器繁忙，请稍后再试";
    public static final int fail_code=7000;
    
    public static final String result_code="code";
    public static final String result_msg = "msg";
    public static final String result_data="data";
    
    public static final String no_user = "用户不存在";
    public static final int no_user_code=7001;
    public static final String exist_user = "用户已存在";
    public static final int exist_user_code=7002;
    public static final String pwd_null = "密码错误";
    public static final int pwd_null_code=7003;
    public static final String wrong_pwd = "原始密码错误";
    public static final int wrong_pwd_code=7004;
    public static final String no_login="请先登录";
    public static final int no_login_code=7005;
    public static final String no_auth="权限不够";
    public static final int no_auth_code=7006;
    
    public static class userType{
    	public static int common =1; //普通用户
    	public static int admin =2; //管理员
    }
    
    public static class userStatus{
    	public static int NO_ACTIVE=1; //未激活账户
		public static int ACTIVE= 2;//激活账户
		public static int AUTHENTICATE = 3; //认证用户
		public static int AUTHORIZE = 4 ;//授权用户
		public static int DESTROY= 7;//帐号销毁
		public static int FREEZE = 8; //冻结账户
    }
}

package com.ldp.datahub.common;


public class Constant {
    public static final String sucess = "ok";
    public static final int sucess_code=0;
    public static final String exception = "服务器繁忙，请稍后再试";
    public static final int fail_code=-1;
    
    public static final String result_code="code";
    public static final String result_msg = "msg";
    public static final String result_data="data";
    
    public static final String no_user = "用户不存在";
    public static final String exist_user = "用户已存在";
    public static final String pwd_null = "密码错误";
    
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

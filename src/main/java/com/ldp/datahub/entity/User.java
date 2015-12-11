package com.ldp.datahub.entity;

import java.sql.Timestamp;

public class User
{
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 用户状态
	 */
	private int userStatus;
	private int userType;
	private String userName;
	private String loginName;
	private String nickName;
	private String loginPasswd;
	private String summary;
	
	private Timestamp opTime;
	private Timestamp invalidTime;
	
	public Timestamp getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLoginPasswd() {
		return loginPasswd;
	}
	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}



}

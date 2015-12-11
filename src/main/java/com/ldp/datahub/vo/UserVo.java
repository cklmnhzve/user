package com.ldp.datahub.vo;

public class UserVo {
	
	private int userId;
	private String nickName;
	private String userName;
	private String comment;
	private int userType;
	private int userStatus;
	private String registTime;
	private String invalidTime;
	
	public int getUserId() {
		return userId;
	}
	
	public String getRegistTime() {
		return registTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	
}

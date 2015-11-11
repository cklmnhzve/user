package com.ldp.datahub.entity;

public class User
{
	/**
	 * 用户id
	 */
	private long userId;
	
	/**
	 * 用户状态
	 */
	private int userStatus;
	private int userType;
	private int arrangType;
	private String loginName;
<<<<<<< HEAD
	private String nickName;
=======
	private String nickeName;
>>>>>>> f3b413cd28d5d6c1eca06b2445b99f098ce90e55
	private String loginPasswd;
	private int sellLevel;



	

	public long getUserId()
	{
		return userId;
	}





	public void setUserId(long userId)
	{
		this.userId = userId;
	}





	public int getUserStatus()
	{
		return userStatus;
	}





	public void setUserStatus(int userStatus)
	{
		this.userStatus = userStatus;
	}





	public int getUserType()
	{
		return userType;
	}





	public void setUserType(int userType)
	{
		this.userType = userType;
	}





	public int getArrangType()
	{
		return arrangType;
	}





	public void setArrangType(int arrangType)
	{
		this.arrangType = arrangType;
	}





	public String getLoginName()
	{
		return loginName;
	}





	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}


<<<<<<< HEAD



	public String getNickName()
	{
		return nickName;
	}





	public void setNickName(String nickName)
	{
		this.nickName = nickName;
=======
	public String getNickeName() {
		return nickeName;
	}

	public void setNickeName(String nickeName) {
		this.nickeName = nickeName;
>>>>>>> f3b413cd28d5d6c1eca06b2445b99f098ce90e55
	}





	public String getLoginPasswd()
	{
		return loginPasswd;
	}





	public void setLoginPasswd(String loginPasswd)
	{
		this.loginPasswd = loginPasswd;
	}





	public int getSellLevel()
	{
		return sellLevel;
	}





	public void setSellLevel(int sellLevel)
	{
		this.sellLevel = sellLevel;
	}





	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", userStatus=" + userStatus + ", userType=" + userType + ", arrangType="
<<<<<<< HEAD
				+ arrangType + ", loginName=" + loginName + ", nickName=" + nickName + ", loginPasswd=" + loginPasswd
=======
				+ arrangType + ", loginName=" + loginName + ", nickeName=" + nickeName + ", loginPasswd=" + loginPasswd
>>>>>>> f3b413cd28d5d6c1eca06b2445b99f098ce90e55
				+ ", sellLevel=" + sellLevel + "]";
	}







	
	

}

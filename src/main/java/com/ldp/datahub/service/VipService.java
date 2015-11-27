package com.ldp.datahub.service;

import com.ldp.datahub.entity.VipVo;

public interface VipService
{
	public VipVo getUserVipQuota(int type);
	
	public void updateUserType(String loginName, int userType,int opUser);
}

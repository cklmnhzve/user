package com.ldp.datahub.service;

import com.ldp.datahub.entity.VipVo;
import com.ldp.datahub.exception.LinkServerException;

public interface VipService
{
	public VipVo getUserVipQuota(int type);
	/**
	 * 更改用户的vip级别
	 * @param loginName
	 * @param userType
	 * @param validity
	 * @param opUser
	 * @return 如果年费扣款余额不足，返回false
	 * @throws LinkServerException 
	 */
	public boolean updateUserType(String loginName, int userType,int validity,int opUser) throws LinkServerException;
}

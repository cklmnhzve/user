package com.ldp.datahub.dao;

import com.ldp.datahub.entity.Quota;

public interface QuotaDao {
	/**
	 * 获取用户quota信息
	 * @param userId
	 * @return
	 */
	public Quota getQuota(int userId,String quotaName);
	
	public void saveQuota(Quota quota);
	
	public void updateQuota(int value,int opUser,int user,String quotaName);
	
	public void updateQuotaUse(int add,int user,String quotaName );

}

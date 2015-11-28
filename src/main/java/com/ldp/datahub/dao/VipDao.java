package com.ldp.datahub.dao;

import java.util.List;

import com.ldp.datahub.entity.Vip;

public interface VipDao {
	
	/**
	 * 根据用户级别获取VIP等级的相关配额信息
	 * @param type
	 * @return
	 */
	public List<Vip> getVipQuota(int type);

	void insert(Vip vip);
	
	void delete(int type);

}

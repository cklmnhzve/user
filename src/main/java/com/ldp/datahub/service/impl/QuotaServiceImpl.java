package com.ldp.datahub.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.Constant.QutaName;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.entity.Quota;
import com.ldp.datahub.entity.QuotaVo;
import com.ldp.datahub.entity.RepoVo;
import com.ldp.datahub.service.QuotaService;

@Service
public class QuotaServiceImpl implements QuotaService {
	
	@Autowired
	private QuotaDao quotaDao;

	@Override
	public RepoVo getRepos(int userId) {
		RepoVo vo = new RepoVo();
		QuotaVo qvo = getQuota(userId, Constant.QutaName.REPO_PUBLIC);
		if(qvo!=null){
			vo.setQuotaPublic(qvo.getQuota());
			vo.setUsePublic(qvo.getUse());
		}
		
		QuotaVo qvo1 = getQuota(userId, Constant.QutaName.REPO_PRIVATE); 
		if(qvo1!=null){
			vo.setQuotaPrivate(qvo1.getQuota());
			vo.setUsePrivate(qvo1.getUse());
		}
		
		if(qvo==null||qvo1==null){
			return null;
		}
		return vo;
	}
	
	@Override
	public QuotaVo getQuota(int userId,String qutaName){
		Quota quota = quotaDao.getQuota(userId, qutaName);
		if(quota==null){
			return null;
		}
		QuotaVo vo = new QuotaVo();
		vo.setQuota(quota.getQuotaValue()+quota.getUnit());
		vo.setUse(quota.getUseValue()+quota.getUnit());
		return vo;
	}

	@Override
	@Transactional
	public boolean saveRepo(int userId, int opUser, int privateRepo, int publicRepo) {
		Quota q = quotaDao.getQuota(userId, QutaName.REPO_PUBLIC);
		if(q!=null){
			return false;
		}
		Quota quota = new Quota();
		quota.setOpTime(new Timestamp(System.currentTimeMillis()));
		quota.setOpUser(opUser);
		quota.setQuotaName(Constant.QutaName.REPO_PUBLIC);
		quota.setQuotaValue(publicRepo);
		quota.setUnit("");
		quota.setUserId(userId);
		quota.setUseValue(0);
		quotaDao.saveQuota(quota);
		
		quota.setQuotaName(Constant.QutaName.REPO_PRIVATE);
		quota.setQuotaValue(privateRepo);
		quotaDao.saveQuota(quota);
		return true;
	}

	@Override
	@Transactional
	public void updateRepo(int userId, int opUser, Integer privateRepo, Integer publicRepo) {
		if(privateRepo!=null){
			quotaDao.updateQuota(privateRepo, opUser, userId, Constant.QutaName.REPO_PRIVATE);
		}
		
		if(publicRepo!=null){
			quotaDao.updateQuota(publicRepo, opUser, userId, Constant.QutaName.REPO_PUBLIC);
		}
	}

	@Override
	@Transactional
	public void updateRepoUse(int userId, Integer privateRepo, Integer publicRepo) {
		if(privateRepo!=null){
			quotaDao.updateQuotaUse(privateRepo, userId, Constant.QutaName.REPO_PRIVATE);
		}
		if(publicRepo!=null){
			quotaDao.updateQuotaUse(publicRepo, userId, Constant.QutaName.REPO_PUBLIC);
		}
	}

	@Override
	public boolean saveQuota(int userId, int opUser, int value, String quotaName,String unit) {
		Quota q = quotaDao.getQuota(userId, quotaName);
		if(q!=null){
			return false;
		}
		Quota quota = new Quota();
		quota.setOpTime(new Timestamp(System.currentTimeMillis()));
		quota.setOpUser(opUser);
		quota.setQuotaName(quotaName);
		quota.setQuotaValue(value);
		quota.setUnit(unit);
		quota.setUserId(userId);
		quota.setUseValue(0);
		quotaDao.saveQuota(quota);
		
		return true;
	}

	@Override
	public void updateQuota(int userId, int opUser, int value, String quotaName) {
		quotaDao.updateQuota(value, opUser, userId,quotaName);
	}

	@Override
	public void updateQuotaUse(int userId, int value, String quotaName) {
		quotaDao.updateQuotaUse(value, userId, quotaName);
	}

}

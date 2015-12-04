package com.ldp.datahub.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.Constant.OpType;
import com.ldp.datahub.common.Constant.QutaName;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.dao.VipDao;
import com.ldp.datahub.dao.impl.QuotaDaoImpl;
import com.ldp.datahub.entity.Quota;
import com.ldp.datahub.entity.QuotaVo;
import com.ldp.datahub.entity.RepoVo;
import com.ldp.datahub.entity.UserLog;
import com.ldp.datahub.entity.Vip;
import com.ldp.datahub.service.QuotaService;

@Service
public class QuotaServiceImpl implements QuotaService {
	
	@Autowired
	private QuotaDao quotaDao;
	@Autowired
	private UserLogDao userLogDao;
	@Autowired
	private VipDao vipDao;

	@Override
	public RepoVo getRepos(int userId,int userType) {
		RepoVo vo = new RepoVo();
		QuotaVo qvo = getQuota(userId, Constant.QutaName.REPO_PUBLIC,userType);
		vo.setQuotaPublic(qvo.getQuota());
		vo.setUsePublic(qvo.getUse());
		
		qvo = getQuota(userId, Constant.QutaName.REPO_PUBLIC,userType);
		vo.setQuotaPublic(qvo.getQuota());
		vo.setUsePublic(qvo.getUse());
		
		QuotaVo qvo1 = getQuota(userId, Constant.QutaName.REPO_PRIVATE,userType); 
		vo.setQuotaPrivate(qvo1.getQuota());
		vo.setUsePrivate(qvo1.getUse());
		
		return vo;
	}
	
	
	@Override
	public QuotaVo getQuota(int userId,String qutaName,int userType){
		Quota quota = quotaDao.getQuota(userId, qutaName);
		if(quota==null){
			List<Vip> vips = vipDao.getVipQuota(userType);
			for(Vip vip:vips){
				Quota q = new Quota();
				q.setOpUser(-1);
				q.setQuotaName(vip.getName());
				q.setUnit(vip.getUnit());
				q.setQuotaValue(vip.getValue());
				q.setUserId(userId);
				q.setUseValue(0);
				quotaDao.saveQuota(q);
			}
			quota = quotaDao.getQuota(userId, qutaName);
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
		
		UserLog ulog = new UserLog();
		ulog.setOpUser(opUser);
		ulog.setChangeInfo("添加repo");
		ulog.setOpTable(QuotaDaoImpl.TABLENAME);
		ulog.setOpType(OpType.ADD);
		ulog.setChangeUser(userId);
		userLogDao.save(ulog);
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
		
		UserLog ulog = new UserLog();
		ulog.setOpUser(opUser);
		ulog.setChangeInfo("privateRepo:"+privateRepo+";publicRepo"+publicRepo);
		ulog.setOpTable(QuotaDaoImpl.TABLENAME);
		ulog.setOpType(OpType.UPDATE);
		ulog.setChangeUser(userId);
		userLogDao.save(ulog);
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
		
		UserLog ulog = new UserLog();
		ulog.setOpUser(opUser);
		ulog.setChangeInfo("添加 "+quotaName);
		ulog.setOpTable(QuotaDaoImpl.TABLENAME);
		ulog.setOpType(OpType.ADD);
		ulog.setChangeUser(userId);
		userLogDao.save(ulog);
		
		return true;
	}

	@Override
	public void updateQuota(int userId, int opUser, int value, String quotaName) {
		quotaDao.updateQuota(value, opUser, userId,quotaName);
		
		UserLog ulog = new UserLog();
		ulog.setOpUser(opUser);
		ulog.setChangeInfo(quotaName+":"+value);
		ulog.setOpTable(QuotaDaoImpl.TABLENAME);
		ulog.setOpType(OpType.UPDATE);
		ulog.setChangeUser(userId);
		userLogDao.save(ulog);
	}

	@Override
	public void updateQuotaUse(int userId, int value, String quotaName) {
		quotaDao.updateQuotaUse(value, userId, quotaName);
	}

}

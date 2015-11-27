package com.ldp.datahub.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldp.datahub.common.Constant;
import com.ldp.datahub.common.Constant.QutaName;
import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.dao.UserDao;
import com.ldp.datahub.dao.UserLogDao;
import com.ldp.datahub.dao.VipDao;
import com.ldp.datahub.dao.impl.VipDaoImpl;
import com.ldp.datahub.entity.Quota;
import com.ldp.datahub.entity.User;
import com.ldp.datahub.entity.UserLog;
import com.ldp.datahub.entity.Vip;
import com.ldp.datahub.entity.VipVo;
import com.ldp.datahub.service.VipService;

@Service
public class VipServiceImpl implements VipService
{

	@Autowired
	private VipDao vipDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuotaDao quotaDao;
	
	@Autowired
	private UserLogDao userLogDao;
	
	@Override
	public VipVo getUserVipQuota(int userType){
		try {
			List<Vip> infos = vipDao.getVipQuota(userType);
			VipVo vo = new VipVo();
			for(Vip vip:infos){
				String quotaName=vip.getName();
				if(quotaName.equals(QutaName.REPO_PRIVATE)){
					vo.setRepoPri(vip.getValue());
				}else if(quotaName.equals(QutaName.REPO_PUBLIC)){
					vo.setRepoPub(vip.getValue());
				}else if(quotaName.equals(QutaName.DEPOSIT)){
					vo.setDeposit(vip.getValue()+vip.getUnit());
				}else if(quotaName.equals(QutaName.PULL_NUM)){
					vo.setPullNum(vip.getValue());
				}
			}
			return vo;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateUserType(String loginName, int userType,int opUser) {
		User oldUser = userDao.getUser(loginName);
		if(userType==oldUser.getUserType()){
			return;
		}
		//更新用户表
		User user = new User();
		user.setLoginName(loginName);
		user.setUserType(userType);
		userDao.updateUser(user);
		
		int oldLevel = oldUser.getUserType();
		List<Vip> oldVip = vipDao.getVipQuota(oldLevel);
		
		List<Vip> newVips = vipDao.getVipQuota(userType);
		Map<String,Vip> vipmap = toMap(newVips);
		
		int userId = oldUser.getUserId();
		for(Vip old :oldVip){
			String name = old.getName();
			Vip newV = vipmap.get(name);
			if(QutaName.FEE.equals(name)||QutaName.PAY_WAY.equals(name)){
				continue;
			}
			Quota quota = quotaDao.getQuota(userId, name);
			if(quota==null){
				Quota q = new Quota();
				q.setOpUser(opUser);
				q.setQuotaName(name);
				q.setUnit(newV.getUnit());
				q.setQuotaValue(newV.getValue());
				q.setUserId(userId);
				q.setUseValue(0);
				quotaDao.saveQuota(q);
			}else{
				int oldValue = old.getValue();
				int vipValue = newV.getValue();
				//如果以前是admin，转成会员级别
				if(oldValue==-1){
					oldValue=0;
				}
				int newValue=0;
				//如果是转成会员，资源无限使用
				if(vipValue==-1){
					newValue=-1;
				}else{
					//其他会员级别转换，使用差值控制资源使用配额
					int add = vipValue-oldValue;
					int num = quotaDao.getQuota(userId, name).getQuotaValue();
					newValue = num+add;
				}
				quotaDao.updateQuota(newValue, opUser, userId, name);
				updateLog(userId, opUser, name, newValue);
			}
		}
	}
	
	private void updateLog(int userId,int opUser,String quotaName,int value){
		UserLog ulog = new UserLog();
		ulog.setChangeUser(userId);
		ulog.setChangeInfo(quotaName+":"+value);
		ulog.setOpTable(VipDaoImpl.TABLENAME);
		ulog.setOpType(Constant.OpType.UPDATE);
		ulog.setOpUser(opUser);
		userLogDao.save(ulog);
	}
	
	private Map<String,Vip> toMap(List<Vip> vips){
		Map<String,Vip> map = new HashMap<String, Vip>();
		for(Vip vip:vips){
			map.put(vip.getName(), vip);
		}
		return map;
	}
	
	

}

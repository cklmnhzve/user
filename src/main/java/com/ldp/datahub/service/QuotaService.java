package com.ldp.datahub.service;

import java.text.ParseException;

import com.ldp.datahub.entity.QuotaVo;
import com.ldp.datahub.entity.RepoVo;

public interface QuotaService {

	public RepoVo getRepos(int userId,int usertype) throws ParseException;
	
	public boolean saveRepo(int userId,int opUser,int privateRepo,int publicRepo);
	
	public void updateRepo(int userId,int opUser,Integer privateRepo,Integer publicRepo);
	
	public void updateRepoUse(int userId,Integer privateRepo,Integer publicRepo);

	public QuotaVo getQuota(int userId, String qutaName,int userType) throws ParseException;
	
	public boolean saveQuota(int userId, int opUser, int value, String quotaName,String unit);
	
	public void updateQuota(int userId,int opUser,int value,String quotaName);
	
	public void updateQuotaUse(int userId,int value,String quotaName) throws ParseException;
	
}

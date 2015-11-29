package com.ldp.datahub.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldp.datahub.dao.QuotaDao;
import com.ldp.datahub.entity.Quota;

@RunWith(SpringJUnit4ClassRunner.class)  
//@ContextConfiguration("classpath:/applicationContext.xml") 
@ContextConfiguration("file:src/main/resources/applicationContext.xml") 
public class QuotaDaoTest {
	
	@Autowired
	private QuotaDao quotaDao;
	private SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void testGetAndInsert(){
		Quota target = makeQuota();
		quotaDao.saveQuota(target);
		try {
			Quota actual = quotaDao.getQuota(target.getUserId(), target.getQuotaName());
			Assert.assertEquals(df.format(target.getOpTime()), df.format(actual.getOpTime()));
			Assert.assertEquals(target.getQuotaName(), actual.getQuotaName());
			Assert.assertEquals(target.getUnit(), actual.getUnit());
			Assert.assertEquals(target.getOpUser(), actual.getOpUser());
			Assert.assertEquals(target.getQuotaValue(), actual.getQuotaValue());
			Assert.assertEquals(target.getUserId(), actual.getUserId());
			Assert.assertEquals(target.getUseValue(), actual.getUseValue());
			
		} finally {
			quotaDao.delete(target.getUserId(), target.getQuotaName());
		}
	}
	
	@Test
	public void testUpdateQuota(){
		Quota target = makeQuota();
		quotaDao.saveQuota(target);
		try {
			int value=1000;
			int opUser=19;
			
			quotaDao.updateQuota(value, opUser, target.getUserId(), target.getQuotaName());
			Quota actual = quotaDao.getQuota(target.getUserId(), target.getQuotaName());
			Assert.assertEquals(value, actual.getQuotaValue());
			Assert.assertEquals(opUser, actual.getOpUser());
			
		}finally {
			quotaDao.delete(target.getUserId(), target.getQuotaName());
		}
	}
	
	@Test
	public void testUpdateUse(){
		Quota target = makeQuota();
		quotaDao.saveQuota(target);
		try {
			int add=10;
			quotaDao.updateQuotaUse(add, target.getUserId(), target.getQuotaName());
			Quota actual = quotaDao.getQuota(target.getUserId(), target.getQuotaName());
			Assert.assertEquals(target.getUseValue()+add, actual.getUseValue());
			
		}finally {
			quotaDao.delete(target.getUserId(), target.getQuotaName());
		}
	}
	
	private Quota makeQuota(){
		Quota quota = new Quota();
		quota.setOpTime(new Timestamp(System.currentTimeMillis()));
		quota.setOpUser(-1);
		quota.setQuotaName("testQuota");
		quota.setQuotaValue(100);
		quota.setUnit("M");
		quota.setUserId(-2);
		quota.setUseValue(30);
		return quota;
	}

}

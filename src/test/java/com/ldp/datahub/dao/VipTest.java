package com.ldp.datahub.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldp.datahub.dao.VipDao;
import com.ldp.datahub.entity.Vip;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("file:src/main/resources/applicationContext.xml") 
public class VipTest {
	
	@Autowired
	private VipDao vipDao;
	
	@Test
	public void testGetAndInsert(){
		Vip vip = makeVip();
		vipDao.insert(vip);
		try {
			List<Vip> vips =vipDao.getVipQuota(vip.getUserType());
			Vip actual = vips.get(0);
			Assert.assertEquals(vip.getName(), actual.getName());
			Assert.assertEquals(vip.getUnit(), actual.getUnit());
			Assert.assertEquals(vip.getOpUser(), actual.getOpUser());
			Assert.assertEquals(vip.getStatus(), actual.getStatus());
			Assert.assertEquals(vip.getValue(), actual.getValue());
			
		} finally {
			vipDao.delete(vip.getUserType());
		}
	}
	
	
	private Vip makeVip(){
		Vip vip = new Vip();
		vip.setName("test");
		vip.setOpUser(-1);
		vip.setStatus(1);
		vip.setUnit("M");
		vip.setUserType(-1);
		vip.setValue(10);
		return vip;
	}

}

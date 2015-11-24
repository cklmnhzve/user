package com.ldp.datahub.entity;

import java.sql.Timestamp;

public class Quota {
	private String quotaName;
	private int quotaValue;
	private int useValue;
	private String unit;
	private int userId;
	private Timestamp opTime;
	private int opUser;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	public int getOpUser() {
		return opUser;
	}
	public void setOpUser(int opUser) {
		this.opUser = opUser;
	}
	public String getQuotaName() {
		return quotaName;
	}
	public void setQuotaName(String quotaName) {
		this.quotaName = quotaName;
	}
	public int getQuotaValue() {
		return quotaValue;
	}
	public void setQuotaValue(int quotaValue) {
		this.quotaValue = quotaValue;
	}
	public int getUseValue() {
		return useValue;
	}
	public void setUseValue(int useValue) {
		this.useValue = useValue;
	} 
	
	
}

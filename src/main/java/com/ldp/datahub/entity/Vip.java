package com.ldp.datahub.entity;

import java.sql.Timestamp;

public class Vip {

	private String name;
	private int userType;
	private int value;
	private String unit;
	private int status;
	private Timestamp opTime;
	private int opUser;
	
	
	public Vip(String quotaName,int userType,int quotaValue,String unit,int status,int opUser ){
		this.name=quotaName;
		this.userType=userType;
		this.value = quotaValue;
		this.unit=unit;
		this.status=status;
		this.opUser=opUser;
	}
	public Vip(){
		
	}
	
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}

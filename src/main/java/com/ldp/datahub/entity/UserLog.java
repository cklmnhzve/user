package com.ldp.datahub.entity;

import java.sql.Timestamp;

public class UserLog {
	
	private long id;
	private String opTable;
	private int opType;
	private int changeUser;
	private String changeInfo;
	private Timestamp opTime;
	private int opUser;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOpTable() {
		return opTable;
	}
	public void setOpTable(String opTable) {
		this.opTable = opTable;
	}
	public int getOpType() {
		return opType;
	}
	public void setOpType(int opType) {
		this.opType = opType;
	}
	public int getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(int changeUser) {
		this.changeUser = changeUser;
	}
	public String getChangeInfo() {
		return changeInfo;
	}
	public void setChangeInfo(String changeInfo) {
		this.changeInfo = changeInfo;
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
	
	

}

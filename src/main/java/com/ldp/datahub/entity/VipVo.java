package com.ldp.datahub.entity;

public class VipVo {
	private int userType; //会员级别
	private int repoPub; //共有repo资源量
	private int repoPri; //私有repo资源量
	private int pullNum;//免费数据Pull量
	private int payWay;//pull付费方式 (1:预付费，2后付费)
	private String deposit;//托管配额
	private int fee;//年费
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getRepoPub() {
		return repoPub;
	}
	public void setRepoPub(int repoPub) {
		this.repoPub = repoPub;
	}
	public int getRepoPri() {
		return repoPri;
	}
	public void setRepoPri(int repoPri) {
		this.repoPri = repoPri;
	}
	public int getPullNum() {
		return pullNum;
	}
	public void setPullNum(int pullNum) {
		this.pullNum = pullNum;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}

}

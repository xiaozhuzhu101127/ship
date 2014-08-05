package com.topjet.shipper.model;

import java.io.Serializable;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-7-9 上午9:01:49  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@SuppressWarnings("serial")
public class PhoneInfo implements Serializable {
	private boolean verified;
	private String number, companyName, location, name;
	private int credit, complaint;

	public PhoneInfo(String number, boolean verified, String companyName, String location, String name, int credit,
			int complaint) {
		super();
		this.number = number;
		this.verified = verified;
		this.companyName = companyName;
		this.location = location;
		this.name = name;
		this.credit = credit;
		this.complaint = complaint;
	}

	/**
	 * 空白信息
	 * @param number
	 * @param location
	 */
	public PhoneInfo(String number, String location){
		this.number = number;
		this.location = location;
		this.name = "陌生人";
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getComplaint() {
		return complaint;
	}

	public void setComplaint(int complaint) {
		this.complaint = complaint;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

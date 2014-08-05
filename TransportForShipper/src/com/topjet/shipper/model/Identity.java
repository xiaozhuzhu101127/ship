package com.topjet.shipper.model;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-30 下午2:55:34  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public enum Identity {
	Driver("司机", "UT_TW"), 
	GoodsSourceOwner("货主", "UT_RGL"), 
	InfomationMinistry("信息部", "UT_GW"), 
	LogisticCompany("物流公司", "UT_CO"), 
	Other("其他", "UT_OTH");
	private String description;
	private String code;

	private Identity(String description, String code) {
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCode(){
		return code;
	}
	
	public static Identity  getIdentity(String name){
		  if(Driver.getCode().equals(name)){
			  return Driver;
		  }else if (GoodsSourceOwner.getCode().equals(name)){
			  return GoodsSourceOwner;
		  }else if (InfomationMinistry.getCode().equals(name)){
			  return InfomationMinistry;
		  }else if (LogisticCompany.getCode().equals(name)){
			  return LogisticCompany;
		  }else {
			  return Other;
			  
		  }
	
	}
}

package com.topjet.shipper.model;

public enum Violate {
	WT("查询中","违章信息正在查询中，请耐心等待。", "WZSTS_WT"), 
	RESULT("有违章结果","有违章结果", "WZSTS_RESULT"), 
	NORESULT("暂无违章","您的车辆无违章记录，请继续遵守交通法规。", "WZSTS_NORESULT"), 
	ERROR("图片不清","图片过于模糊，系统无法完成查询，请重新拍照查询。", "WZSTS_ERROR");

	private String description;
	private String description2;
	private String code;

	private Violate(String description,String description2, String code) {
		this.description = description;
		this.description2=description2;
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCode(){
		return code;
	}
	
	
	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public static Violate  getViolate(String name){
		  if(ERROR.getCode().equals(name)){
			  return ERROR;
		  }else if (RESULT.getCode().equals(name)){
			  return RESULT;
		  }else if (NORESULT.getCode().equals(name)){
			  return NORESULT;
		  }else {
			  return WT;
			  
		  }
	
	}
	

}


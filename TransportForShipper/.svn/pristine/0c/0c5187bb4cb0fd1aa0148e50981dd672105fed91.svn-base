package com.topjet.shipper.net;

import java.util.Map;

import com.google.gson.Gson;

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-29 下午2:52:19  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class JSONRequest {
	private String className;
	private String cmd;
	private Map<String, Object> params;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public JSONRequest(String className, String cmd, Map params) {
		super();
		this.className = className;
		this.cmd = cmd;
		this.params = params;
	}

	public JSONRequest addParam(String key, String value){
		this.params.put(key, value);
		return this;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	
	//把实体类，序列化为json格式的
	/**
	 * [{"name":"name0","age":0},{"name":"name1","age":5},{"name":"name2","age":10},{"name":"name3","age":15},{"name":"name4","age":20},{"name":"name5","age":25},{"name":"name6","age":30},{"name":"name7","age":35},{"name":"name8","age":40},{"name":"name9","age":45}]
	 */
	@Override
	public String toString(){
		return new Gson().toJson(this);
	}
}

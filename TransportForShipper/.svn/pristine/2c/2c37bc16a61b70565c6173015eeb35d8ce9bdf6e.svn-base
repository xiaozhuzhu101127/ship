package com.topjet.shipper.net;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topjet.shipper.util.AppConifg;
import com.topjet.shipper.util.DefaultConst;
/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-25 下午4:43:14  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class HttpHelp {
	private static HttpHelp instance;
	
	private   HttpHelp(){		
	}
	public static HttpHelp getInstance(){		
		if (null == instance)  instance = new HttpHelp();
		return instance;
	}
	
	public String sendGetRequest(Map<String, ?> params) {	
		  return sendGetRequest(AppConifg.SERVER_ADDRESS,params,  DefaultConst.DEFAULT_CONN_TIMEOUT);	  
	 
	}
	
	

	public String sendGetRequest(String url,Map<String, ?> params,  int timeout) {
		return sendGetRequest(url,params, timeout, false);
	}
	
	public byte[] sendPostRequest(Map<String, ?> params) {	
		  return sendPostRequest(params,  DefaultConst.DEFAULT_CONN_TIMEOUT);	  
	 
	}

	public byte[] sendPostRequest(Map<String, ?> params,  int timeout) {
		return sendPostRequest(params, timeout, false);
	}
	
	public byte[] sendPostRequest(Map<String, ?> params, int timeout, boolean isForeign){
		byte[] requestData = null;
		if (params != null && params.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (java.util.Map.Entry<String, ?> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value != null && !value.equals("")) {
					if(sb.length() > 0){
						sb.append("&");
					}
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(value);
				}
			}
			try {
				requestData = sb.toString().getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
			
			}
		}
		return requestData;
	}
	
	public String sendGetRequest(String url,Map<String, ?> params, int timeout, boolean isForeign){
		int i = 0;
		StringBuffer sb = new StringBuffer(url);
		if (params != null && params.size() > 0) {
			for (java.util.Map.Entry<String, ?> entry : params.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();
				if (key != null && !key.equals("") && value != null && !value.equals("")) {
					if (i == 0) {
						sb.append("?");
					} else {
						sb.append("&");
					}
					sb.append(key);
					sb.append("=");
					sb.append(value);
					i++;
				}
			}
		}
		return sb.toString();
	}
	
	public JSONObject createJOSNObject(String json) {
		try {
			if (json != null && !"".equals(json)) {
				return new JSONObject(json);
			}
		} catch (JSONException e) {
		}
		return null;
	}

	public JSONObject getJSONObjectFromJSON(JSONObject json, String key, JSONObject defaultValue) {
		try {
			if (json != null && json.has(key)) {
				return json.getJSONObject(key);
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public JSONObject getJSONObjectFromJSONArray(JSONArray jsonArray, int index, JSONObject defaultValue) {
		try {
			if (index < jsonArray.length()) {
				return jsonArray.getJSONObject(index);
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public JSONArray getJSONArrayFromJSON(JSONObject json, String key, JSONArray defaultValue) {
		try {
			if (json != null && json.has(key)) {
				return json.getJSONArray(key);
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public String getStringFromJSON(JSONObject json, String key, String defaultValue) {
		try {
			String iTemp;
			if (json != null && json.has(key)) {
				iTemp = json.getString(key);
				if (iTemp == null) {
					return defaultValue;
				} else {
					if (iTemp.equals("null")) {
						return defaultValue;
					} else {
						return iTemp;
					}
				}
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public String getStringFromJSONArray(JSONArray jsonArray, int index, String defaultValue) {
		try {
			if (index < jsonArray.length()) {
				return jsonArray.getString(index);
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public boolean getBooleanFromJSON(JSONObject json, String key, boolean defaultValue) {
		try {
			boolean iTemp;
			if (json != null && json.has(key)) {
				iTemp = json.getBoolean(key);
				return iTemp;
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public int getIntFromJSON(JSONObject json, String key, int defaultValue) {
		try {
			if (json != null && json.has(key)) {
				return json.getInt(key);
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}

	public long getLongFromJSON(JSONObject json, String key, Long defaultValue) {
		try {
			if (json != null && json.has(key)) {
				return json.getLong(key);
			}
		} catch (JSONException e) {
		}
		return defaultValue;
	}
}

package com.topjet.shipper.adapter;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.audividi.imageloading.core.DisplayImageOptions;
import com.topjet.shipper.R;

import android.app.Activity;
import android.widget.BaseAdapter;

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-28 上午10:54:20  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public abstract class LocalPhoneBookListAdapter
extends BaseAdapter{
	protected Activity context;
	protected LinkedList<JSONObject> data;

	protected boolean loading = false;
	protected DisplayImageOptions options;
	public LocalPhoneBookListAdapter(Activity context) {
		this.data = new LinkedList<JSONObject>();
		this.context = context;
		options = new DisplayImageOptions.Builder()
		// 默认图片
		.showStubImage(R.drawable.twitter_photo)
		.showImageForEmptyUri(R.drawable.twitter_photo)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	}

	public void setData(JSONArray data){
		if(null == data){
			return;
		}
		if(null != this.data){
			this.data.clear();
		}
		for(int i = 0;i<data.length();i++){
			this.data.add(data.optJSONObject(i));
		}
	}

	public List<JSONObject> getData(){
		return data;
	}

	public void prependData(JSONArray data){
		if(null == data){
			return;
		}
		for(int i = data.length() - 1;i>=0;i--){
			this.data.addFirst(data.optJSONObject(i));
		}
	}

	public void appendData(JSONArray data){
		if(null == data){
			return;
		}
		for(int i = 0;i < data.length();i++){
			this.data.add(data.optJSONObject(i));
		}
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < 0 || position >= data.size()) {
			return null;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}

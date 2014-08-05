package com.topjet.shipper.adapter;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public abstract class CommonJsonAdapter extends BaseAdapter{
	protected Activity context;
	protected LinkedList<JSONObject> data;

	protected boolean loading = false;

	public CommonJsonAdapter(Activity context) {
		this.data = new LinkedList<JSONObject>();
		this.context = context;
	
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


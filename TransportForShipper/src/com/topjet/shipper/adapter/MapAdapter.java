package com.topjet.shipper.adapter;

import java.util.Map;

import com.topjet.shipper.util.Receivable;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-7-11 上午9:33:47  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class MapAdapter 
extends BaseAdapter implements OnClickListener {

	private Map<String, String> data;
	private Context context;
	private String[] keys;

	private int requestCode;
	
	public MapAdapter(Context context){
		this.context = context;
	}
	
	public MapAdapter(Context context, int requestCode){
		this.context = context;
		this.requestCode = requestCode;
	}
	public MapAdapter(Context context, Map<String, String> data){
		this.context = context;
		setData(data);
	}
	public MapAdapter(Context context, int requestCode, Map<String, String> data){
		this.context = context;
		this.requestCode = requestCode;
		setData(data);
	}

	public void setData(Map<String, String> data){
		this.data = data;
		keys = data.keySet().toArray(new String[0]);
	}

	@Override
	public int getCount() {
		return keys.length;
	}

	@Override
	public Object getItem(int position) {
		if(position < keys.length){
			return keys[position];
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new Button(this.context);
			//convertView.setBackgroundResource(R.drawable.btn_chose);
		}
		convertView.setOnClickListener(this);
		String code = keys[position];
		((Button) convertView).setText(data.get(code));
		convertView.setTag(code);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		String code = v.getTag().toString();
		String name = data.get(code);
		if(Receivable.class.isAssignableFrom(this.context.getClass())){
			((Receivable)this.context).receive(this.requestCode, code, name);
		}
	}
}

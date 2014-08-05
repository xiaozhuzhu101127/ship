package com.topjet.shipper.adapter;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.MyApplication;
import com.topjet.shipper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-16 下午4:20:21  
 * Description:今日货源
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsTodayAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private LinkedList<JSONObject> data;
	private boolean loading = false;
	
	public GoodsTodayAdapter(Context context){
		this.data = new LinkedList<JSONObject>();
		this.context = context;
		inflater = LayoutInflater.from(context);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView numView;
		TextView countView;
		TextView timeView;
		TextView departView;
		TextView targetView;
		TextView infoView;
		TextView nameView;
		TextView priceView;
		TextView nobjView;
		TextView tradedView;
		Datawrapper datawrapper;
		if(null == convertView){
			convertView = inflater.inflate(context.getResources().getLayout(R.layout.item_goods_today), null);
			numView = (TextView) convertView.findViewById(R.id.item_today_goods_num);
			countView = (TextView) convertView.findViewById(R.id.item_today_goods_count);
			timeView = (TextView) convertView.findViewById(R.id.item_today_goods_time);
			departView = (TextView) convertView.findViewById(R.id.item_today_goods_depart);
			targetView = (TextView) convertView.findViewById(R.id.item_today_goods_target);
			nameView = (TextView) convertView.findViewById(R.id.item_today_goods_bjname);
			priceView = (TextView) convertView.findViewById(R.id.item_today_goods_price);
			nobjView = (TextView) convertView.findViewById(R.id.item_today_goods_nobj);
			tradedView = (TextView) convertView.findViewById(R.id.item_today_goods_traded);
			infoView = (TextView) convertView.findViewById(R.id.item_today_goods_gsinfo);
			datawrapper = new Datawrapper(numView, countView, timeView, departView, targetView, infoView, nameView, priceView, nobjView, tradedView);
			convertView.setTag(datawrapper);
		}else{
			datawrapper = (Datawrapper) convertView.getTag();
			numView = datawrapper.numView;
			countView = datawrapper.countView;
			timeView = datawrapper.timeView;
			departView = datawrapper.departView;
			targetView = datawrapper.targetView;
			infoView = datawrapper.infoView;
			nameView = datawrapper.nameView;
			priceView = datawrapper.priceView;
			nobjView = datawrapper.nobjView;
			tradedView = datawrapper.tradedView;
		}
		JSONObject g = (JSONObject) getItem(position);
		if(!Common.isEmpty(g.optString("CALLUSRREALNAME"))){
			nobjView.setVisibility(View.GONE);
			//姓名
			nameView.setText(g.optString("CALLUSRREALNAME"));
			//报价
			priceView.setText("【"+g.optString("CALLPRICE")+"元】");
			nameView.setVisibility(View.VISIBLE);
			priceView.setVisibility(View.VISIBLE);
			
		}
		//时间
		timeView.setText(g.optString("CREATE_TIME"));
		//出发地
		String ct_depart = Common.splitBX(g.optString("depart"));
		String depart = Dict.getLocation(ct_depart);
		
		if(Common.isEmpty(depart)){
			depart = MyApplication.getInstance().getLocation();
		}
		departView.setText(depart);
		//目的地
		String ct_target = Common.splitBX(g.optString("target"));
		targetView.setText(Dict.getLocation(ct_target));
		// 货源信息、需车信息
		StringBuilder info = new StringBuilder();
		info.append(Dict.getGsType(g.optString("GSType")));
		if(!Common.isEmpty(g.optString("GSSCALE")) || !Common.isEmpty(g.optString("GSUNIT")))
			info.append(Common.weightOrVolume(g.optString("GSSCALE"), g.optString("GSUNIT")));
		info.append(" 需");
		if(!Common.isEmpty(g.optString("TKLen")))
			info.append(Dict.getTruckLength(g.optString("TKLen"))).append(" ");
		if(!Common.isEmpty(g.optString("TKType")))
			info.append(Dict.getTruckType(g.optString("TKType")));
		if(Common.isEmpty(g.optString("TKLen")) && Common.isEmpty(g.optString("TKType"))){
			info.append("车1辆");
		}else {
			info.append("1辆");
		}
		infoView.setText(info);
		//货源状态
		switch (Dict.getGsState(g.optString("GSSTS"))) {
			//成交
			case 1:
				countView.setVisibility(View.GONE);
				tradedView.setVisibility(View.VISIBLE);
				numView.setText("订单号:"+g.optString("ORDERNUM"));
				numView.setVisibility(View.VISIBLE);
				break;
			default:
				//报价人数
				countView.setText("报价:"+g.optLong("COUNTCALLPRICE")+"人");
				break;
		}
		return convertView;
	}
	
	private class Datawrapper{
		private TextView numView;
		private TextView countView;
		private TextView timeView;
		private TextView departView;
		private TextView targetView;
		private TextView infoView;
		private TextView nameView;
		private TextView priceView;
		private TextView nobjView;
		private TextView tradedView;
		public Datawrapper(TextView numView, TextView countView, TextView timeView, TextView departView,
				TextView targetView, TextView infoView, TextView nameView, TextView priceView,
				TextView nobjView, TextView tradedView) {
			super();
			this.numView = numView;
			this.countView = countView;
			this.timeView = timeView;
			this.departView = departView;
			this.targetView = targetView;
			this.infoView = infoView;
			this.nameView = nameView;
			this.priceView = priceView;
			this.nobjView = nobjView;
			this.tradedView = tradedView;
		}
		
	}
}

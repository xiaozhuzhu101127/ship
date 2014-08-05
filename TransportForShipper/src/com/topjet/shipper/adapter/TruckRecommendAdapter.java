package com.topjet.shipper.adapter;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.topjet.shipper.activity.TruckRecommendListActivity;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.R;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

 
/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-26 下午2:21:28  
 * Description:车源推荐
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class TruckRecommendAdapter extends BaseAdapter implements OnClickListener{
	
	private Context context;
	private LayoutInflater inflater;
	private LinkedList<JSONObject> data;
	private boolean loading = false;
	
	public TruckRecommendAdapter(Context context){
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
		TextView noView;
		TextView infoView;
		TextView nameView;
		TextView countView;
		TextView locationView;
		TextView targetView;
		LinearLayout calllLayout;
		LinearLayout queryLayout;
		Datawrapper datawrapper;
		if(null == convertView){
			convertView = inflater.inflate(context.getResources().getLayout(R.layout.item_truck_recommend), null);
			noView = (TextView) convertView.findViewById(R.id.item_truck_no);
			infoView = (TextView) convertView.findViewById(R.id.item_tk_info);
			targetView = (TextView) convertView.findViewById(R.id.item_tk_target);
			nameView = (TextView) convertView.findViewById(R.id.item_tk_name);
			countView = (TextView) convertView.findViewById(R.id.item_tk_count);
			locationView = (TextView) convertView.findViewById(R.id.item_tk_location);
			calllLayout = (LinearLayout) convertView.findViewById(R.id.item_goods_detail_teldriver);
			queryLayout = (LinearLayout) convertView.findViewById(R.id.item_goods_detail_cxcx);
			datawrapper = new Datawrapper(noView, infoView, targetView, nameView, countView, locationView, calllLayout, queryLayout);
			convertView.setTag(datawrapper);
		}else{
			datawrapper = (Datawrapper) convertView.getTag();
			noView = datawrapper.noView;
			targetView = datawrapper.targetView;
			nameView = datawrapper.nameView;
			countView = datawrapper.countView;
			locationView = datawrapper.locationView;
			infoView = datawrapper.infoView;
			calllLayout = datawrapper.calllLayout;
			queryLayout = datawrapper.queryLayout;
		}
		JSONObject g = (JSONObject) getItem(position);
		//车牌
		if(!Common.isEmpty(g.optString("TKPLATENO"))){
			noView.setText(g.optString("TKPLATENO"));
		}else {
			noView.setText("暂无号牌");
		}
		// 货源信息、需车信息
		StringBuilder info = new StringBuilder();
		if(!Common.isEmpty(g.optString("DCT_TT")))
			info.append(Dict.getTruckType(g.optString("DCT_TT"))).append("<font color='#999999'>/</font>");
		if(!Common.isEmpty(g.optString("DCT_TKLEN")))
			info.append(Dict.getTruckLength(g.optString("DCT_TKLEN"))).append("<font color='#999999'>/</font>");
		if(0 != g.optLong("TKLOAD"))
			info.append(g.optLong("TKLOAD")).append("吨");
		infoView.setText(Html.fromHtml(info.toString()));
		//期望流向
		StringBuilder target = new StringBuilder();
		if(!Common.isEmpty(g.optString("TKTARGET1")) && !Common.isEmpty(Dict.getCityName(Common.splitBX(g.optString("TKTARGET1")))))
			target.append(Dict.getCityName(Common.splitBX(g.optString("TKTARGET1")))).append("<font color='#999999'>/</font>");
		if(!Common.isEmpty(g.optString("TKTARGET2")) && !Common.isEmpty(Dict.getCityName(Common.splitBX(g.optString("TKTARGET2")))))
			target.append(Dict.getCityName(Common.splitBX(g.optString("TKTARGET2")))).append("<font color='#999999'>/</font>");
		if(!Common.isEmpty(g.optString("TKTARGET3")) && !Common.isEmpty(Dict.getCityName(Common.splitBX(g.optString("TKTARGET3")))))
			target.append(Dict.getCityName(Common.splitBX(g.optString("TKTARGET3"))));
		targetView.setText(Html.fromHtml(target.toString()));
		//司机姓名
		nameView.setText(g.optString("REALNAME"));
		//成交数
		countView.setText("历史成交"+g.optLong("HISTORYSUBMIT")+"笔");
		//最后位置和状态
		StringBuffer sbf = new StringBuffer();
		sbf.append(g.optString("POSITION")).append(" ").append("<font color='#669900'>");
		
		if(g.optString("TKSTS").equals("TKSTS_FREE")){
			sbf.append("【空车中】");
		}else {
			sbf.append("【满载】");
		}
		sbf.append("</font>");
		locationView.setText(Html.fromHtml(sbf.toString()));
		calllLayout.setTag(g.optString("MOBILE"));
		calllLayout.setOnClickListener(this);
		queryLayout.setTag(g.optString("MOBILE"));
		queryLayout.setOnClickListener(this);
		return convertView;
	}
	
	private class Datawrapper{
		private TextView noView;
		private TextView infoView;
		private TextView targetView;
		private TextView nameView;
		private TextView countView;
		private TextView locationView;
		private LinearLayout calllLayout;
		private LinearLayout queryLayout;
		public Datawrapper(TextView noView, TextView infoView, TextView targetView, TextView nameView,
				TextView countView, TextView locationView, LinearLayout calllLayout,
				LinearLayout queryLayout) {
			super();
			this.noView = noView;
			this.infoView = infoView;
			this.targetView = targetView;
			this.nameView = nameView;
			this.countView = countView;
			this.locationView = locationView;
			this.calllLayout = calllLayout;
			this.queryLayout = queryLayout;
		}
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_goods_detail_teldriver:
			((TruckRecommendListActivity) context).callDriver((String)v.getTag());
			break;
		case R.id.item_goods_detail_cxcx:
			((TruckRecommendListActivity) context).creditQuery((String)v.getTag());
			break;	
		}
		
	}
}

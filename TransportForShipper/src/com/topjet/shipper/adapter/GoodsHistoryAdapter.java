package com.topjet.shipper.adapter;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.topjet.shipper.activity.GoodsHistoryActivity;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.R;

import android.content.Context;
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
 * Create at:	2014-5-27 上午10:17:07  
 * Description:历史货源
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsHistoryAdapter extends BaseAdapter implements OnClickListener{
	
	private Context context;
	private LayoutInflater inflater;
	private LinkedList<JSONObject> data;
	private boolean loading = false;
	private String GSSTS;
	public GoodsHistoryAdapter(Context context,String GSSTS){
		this.data = new LinkedList<JSONObject>();
		this.context = context;
		this.GSSTS = GSSTS;
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
	public void setNewData(LinkedList<JSONObject> data){
		this.data = data;
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
		TextView priceView;
		TextView lowView;
		TextView infoView;
		TextView nameView;
		TextView statusView;
		TextView bjnameView;
		LinearLayout copyLayout;
		LinearLayout delLayout;
		LinearLayout driverLayout;
		Datawrapper datawrapper;
		if(null == convertView){
			convertView = inflater.inflate(context.getResources().getLayout(R.layout.item_goods_history), null);
			numView = (TextView) convertView.findViewById(R.id.item_history_goods_num);
			countView = (TextView) convertView.findViewById(R.id.item_history_goods_count);
			timeView = (TextView) convertView.findViewById(R.id.item_history_goods_time);
			departView = (TextView) convertView.findViewById(R.id.item_history_goods_depart);
			targetView = (TextView) convertView.findViewById(R.id.item_history_goods_target);
			infoView = (TextView) convertView.findViewById(R.id.item_history_goods_gsinfo);
			priceView = (TextView) convertView.findViewById(R.id.item_history_goods_price);
			lowView = (TextView)convertView.findViewById(R.id.item_history_goods_low);
			nameView = (TextView) convertView.findViewById(R.id.item_goods_history_bjname);
			statusView = (TextView) convertView.findViewById(R.id.item_history_goods_status);
			bjnameView = (TextView) convertView.findViewById(R.id.item_history_goods_bjname);
			copyLayout = (LinearLayout) convertView.findViewById(R.id.item_goods_history_copy);
			driverLayout  = (LinearLayout) convertView.findViewById(R.id.item_goods_history_driver);
			delLayout  = (LinearLayout) convertView.findViewById(R.id.item_goods_history_del);
			datawrapper = new Datawrapper(numView, countView, timeView, departView, targetView, infoView, lowView, priceView, nameView,statusView,bjnameView,copyLayout, delLayout, driverLayout);
			convertView.setTag(datawrapper);
		}else{
			datawrapper = (Datawrapper) convertView.getTag();
			numView = datawrapper.numView;
			countView = datawrapper.countView;
			timeView = datawrapper.timeView;
			departView = datawrapper.departView;
			targetView = datawrapper.targetView;
			infoView = datawrapper.infoView;
			priceView = datawrapper.priceView;
			lowView = datawrapper.lowView;
			nameView = datawrapper.nameView;
			statusView = datawrapper.statusView;
			bjnameView = datawrapper.bjnameView;
			copyLayout = datawrapper.copyLayout;
			delLayout = datawrapper.delLayout;
			driverLayout = datawrapper.driverLayout;
		}
		JSONObject g = (JSONObject) getItem(position);
		//时间
		timeView.setText(g.optString("CREATE_TIME"));
		//出发地
		String ct_depart = Common.splitBX(g.optString("depart"));
		departView.setText(Dict.getLocation(ct_depart));
		//目的地
		String ct_target = Common.splitBX(g.optString("target"));
		targetView.setText(Dict.getLocation(ct_target));
		// 货源信息、需车信息
		StringBuilder info = new StringBuilder();
		if(!Common.isEmpty(g.optString("GSType")))
			info.append(Dict.getGsType(g.optString("GSType"))).append(" ");
		if(!(Common.isEmpty(g.optString("GSSCALE")) || Common.isEmpty(g.optString("GSUNIT"))))
			info.append(Common.weightOrVolume(g.optString("GSSCALE"), g.optString("GSUNIT"))).append(" ");
		info.append("需");
		if(!Common.isEmpty(g.optString("TKTYPE"))){
			info.append(Dict.getTruckType(g.optString("TKTYPE"))).append("一辆");
		}else {
			info.append("车一辆");
		}
		infoView.setText(info);
		
		driverLayout.setTag(g.optString("CALLUSERPHONENUMBER"));
		driverLayout.setOnClickListener(this);
		copyLayout.setTag(g.optLong("GSID"));
		copyLayout.setOnClickListener(this);
		delLayout.setTag(new Object[]{position,g.optLong("GSID")});
		delLayout.setOnClickListener(this);
		//是否成交
		switch (Integer.parseInt(GSSTS)) {
			//成交
			case 1:
				//订单号
				numView.setText("订单号:"+g.optString("ORDERNUM"));
				//姓名
				nameView.setText(g.optString("CALLUSRREALNAME"));
				//成交数
				countView.setText(g.optLong("HISTORYSUBMIT")+"笔");
				//报价
				priceView.setText(g.optString("CALLPRICE")+"元");
				break;
			case 0:
				lowView.setVisibility(View.GONE);
				priceView.setVisibility(View.GONE);
				delLayout.setVisibility(View.VISIBLE);
				driverLayout.setVisibility(View.GONE);
				numView.setVisibility(View.GONE);
				countView.setVisibility(View.VISIBLE);
				//报价人数
				countView.setText("报价:"+g.optLong("COUNTCALLPRICE")+"人");
				statusView.setVisibility(View.VISIBLE);
				break;
		}
		//货源状态
		switch (Dict.getGsState(g.optString("GSSTS"))) {
			//交易中
			case 0:
				if(!"0".equals(g.optString("CALLPRICE")) && !Common.isEmpty(g.optString("CALLPRICE"))){
					priceView.setVisibility(View.VISIBLE);
					lowView.setVisibility(View.VISIBLE);
					bjnameView.setVisibility(View.VISIBLE);
					bjnameView.setText(g.optString("CALLUSRREALNAME"));
					lowView.setText("最低报价");
					priceView.setText(g.optString("CALLPRICE")+"元");
					statusView.setVisibility(View.GONE);
				}else {
					statusView.setText(" 暂无报价");
				}
				break;
			//交易中
			case 2:
				if(!"0".equals(g.optString("CALLPRICE")) && !Common.isEmpty(g.optString("CALLPRICE"))){
					priceView.setVisibility(View.VISIBLE);
					lowView.setVisibility(View.VISIBLE);
					bjnameView.setVisibility(View.VISIBLE);
					bjnameView.setText(g.optString("CALLUSRREALNAME"));
					lowView.setText("最低报价");
					priceView.setText(g.optString("CALLPRICE")+"元");
					statusView.setVisibility(View.GONE);
				}else {
					statusView.setText(" 暂无报价");
				}
				break;
			//撤销
			case 3:
				statusView.setText(" 您撤销了本单");
				break;
			//TODO:貌似现在没有这个状态
			//过期
			case 4:
				statusView.setText(" 时间过去了");
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
		private TextView lowView;
		private TextView priceView;
		private TextView nameView;
		private TextView statusView;
		private TextView bjnameView;
		private LinearLayout copyLayout;
		private LinearLayout delLayout;
		private LinearLayout driverLayout;
		public Datawrapper(TextView numView, TextView countView, TextView timeView, TextView departView,
				TextView targetView, TextView infoView, TextView lowView, TextView priceView, TextView nameView,
				TextView statusView,TextView bjnameView,LinearLayout copyLayout, LinearLayout delLayout, LinearLayout driverLayout) {
			super();
			this.numView = numView;
			this.countView = countView;
			this.timeView = timeView;
			this.departView = departView;
			this.targetView = targetView;
			this.infoView = infoView;
			this.lowView = lowView;
			this.priceView = priceView;
			this.nameView = nameView;
			this.statusView = statusView;
			this.bjnameView = bjnameView;
			this.copyLayout = copyLayout;
			this.delLayout = delLayout;
			this.driverLayout = driverLayout;
		}
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//删除
		case R.id.item_goods_history_del:
			Object[] ob = (Object[]) v.getTag();
			data.remove(Integer.parseInt(ob[0].toString()));
			((GoodsHistoryActivity) context).deleteGoods(data,(Long)ob[1]);
			break;
		//重发
		case R.id.item_goods_history_copy:
			((GoodsHistoryActivity) context).republishGoods(v.getTag());
			break;
		//诚信查询
		case R.id.item_goods_history_driver:
			((GoodsHistoryActivity) context).docriet((String)v.getTag());
			break;
		}
	}

}

package com.topjet.shipper.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topjet.shipper.R;
import com.topjet.shipper.activity.GoodsDetailActivtiy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

 
/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-19 下午3:39:30  
 * Description:货源详情adapter
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsDetailAdapter extends BaseAdapter implements OnClickListener{
	
	private Context context;
	private LayoutInflater inflater;
	private JSONArray data;
	
	public GoodsDetailAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	public void setData(JSONArray data){
		this.data = data;
	}
	@Override
	public int getCount() {
		return data.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return data.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button numView;
		TextView nameView;
		TextView priceView;
		TextView timeView;
		LinearLayout tradeLayout;
		LinearLayout cxcxLayout;
		LinearLayout telLayout;
		Datawrapper datawrapper;
		if(null == convertView){
			convertView = inflater.inflate(context.getResources().getLayout(R.layout.item_goods_detail), null);
			numView = (Button) convertView.findViewById(R.id.item_goods_detail_num);
			nameView = (TextView) convertView.findViewById(R.id.item_goods_detail_name);
			priceView = (TextView) convertView.findViewById(R.id.item_goods_detail_price);
			timeView = (TextView) convertView.findViewById(R.id.item_goods_detail_time);
			tradeLayout = (LinearLayout) convertView.findViewById(R.id.item_goods_detail_trade);
			cxcxLayout = (LinearLayout) convertView.findViewById(R.id.item_goods_detail_cxcx);
			telLayout = (LinearLayout) convertView.findViewById(R.id.item_goods_detail_teldriver);
			datawrapper = new Datawrapper(numView, nameView, priceView, timeView, tradeLayout, cxcxLayout, telLayout);
			convertView.setTag(datawrapper);
		}else{
			datawrapper = (Datawrapper) convertView.getTag();
			numView = datawrapper.numView;
			timeView = datawrapper.timeView;
			nameView = datawrapper.nameView;
			priceView = datawrapper.priceView;
			tradeLayout = datawrapper.tradeLayout;
			cxcxLayout = datawrapper.cxcxLayout;
			telLayout = datawrapper.telLayout;
		}
		JSONObject price = (JSONObject) getItem(position);
		//第几名
		if(0 != position){
			numView.setBackgroundResource(R.drawable.numbg);
			numView.setTextColor(R.color.color_orange);
		}
		numView.setText(position+1+"");
		//姓名
		nameView.setText(price.optString("USRREALNAME"));
		//价格
		priceView.setText(price.optString("PRICE")+"元");
		//时间
		timeView.setText(price.optString("LEFTTIME"));
		tradeLayout.setTag(price.optLong("PRICEID"));
		tradeLayout.setOnClickListener(this);
		cxcxLayout.setTag(price.optString("MOBILE"));
		cxcxLayout.setOnClickListener(this);
		telLayout.setTag(price.optString("MOBILE"));
		telLayout.setOnClickListener(this);
		return convertView;
	}
	
	private class Datawrapper{
		Button numView;
		TextView nameView;
		TextView priceView;
		TextView timeView;
		LinearLayout tradeLayout;
		LinearLayout cxcxLayout;
		LinearLayout telLayout;
		public Datawrapper(Button numView, TextView nameView, TextView priceView, TextView timeView,
				LinearLayout tradeLayout, LinearLayout cxcxLayout, LinearLayout telLayout) {
			super();
			this.numView = numView;
			this.nameView = nameView;
			this.priceView = priceView;
			this.timeView = timeView;
			this.tradeLayout = tradeLayout;
			this.cxcxLayout = cxcxLayout;
			this.telLayout = telLayout;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		//成交
		case R.id.item_goods_detail_trade:
			((GoodsDetailActivtiy) context).trade(v.getTag());
			break;
		//诚信查询
		case R.id.item_goods_detail_cxcx:
			((GoodsDetailActivtiy) context).docriet((String)v.getTag());
			break;
		//打电话
		case R.id.item_goods_detail_teldriver:
			((GoodsDetailActivtiy) context).callDriver((String)v.getTag());
			break;
		}
	}
}

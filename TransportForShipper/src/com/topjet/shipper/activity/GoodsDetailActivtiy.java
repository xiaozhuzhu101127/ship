package com.topjet.shipper.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.topjet.shipper.activity.CreditResultActivity;
import com.topjet.shipper.adapter.GoodsDetailAdapter;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.DefaultConst;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
 
/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-15 下午3:29:28  
 * Description:货源详情
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsDetailActivtiy extends BaseActivity{
	
	private LinearLayout infoLayout;
	
	private LinearLayout qhhlLayout;
	
	private TextView bjcountView;
	
	private TextView timeView;
	
	private TextView departView;
	
	private TextView targetView;
	
	private TextView gs1View;
	
	private TextView gs2View;
	
	private TextView transportPriceView;
	
	private TextView infoPriceView;
	
	private TextView qqhView;
	
	private ListView listView;
	
	private GoodsDetailAdapter detailAdapter;
	
	private JSONObject goods;
	
	private JSONArray prices;
	
	private ImageView nodriver;
	
	private long gsid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_detail);
		super.onCreate(savedInstanceState);	
		initView();
		initData();	 
	}
	
	public void initData() {
		initProgress("加载中……");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("GsId", getIntent().getExtras().get("GsId"));
		params.put("ISGSOWNER", 1);
		mLoadData.goodsDetail(baseHandler, DefaultConst.CMD_GOODS_DETAIL, params);
		
	}

	private void initView() {
		common_title.setText("货源详细");
		footer_center.setText(DefaultConst.CANCLE);
		common_main.setVisibility(View.VISIBLE);
		footer_update.setVisibility(View.VISIBLE);
		footer_refresh.setVisibility(View.VISIBLE);
		common_recommand.setVisibility(View.VISIBLE);
		infoLayout = (LinearLayout) findViewById(R.id.goods_detail_info);
		bjcountView = (TextView) findViewById(R.id.goods_detail_count);
		timeView = (TextView) findViewById(R.id.goods_detail_time);
		nodriver = (ImageView) findViewById(R.id.goods_detail_no);
		departView = (TextView) findViewById(R.id.goods_detail_depart);
		targetView = (TextView) findViewById(R.id.goods_detail_target);
		gs1View = (TextView) findViewById(R.id.goods_detail_gsinfo);
		gs2View = (TextView) findViewById(R.id.goods_detail_desc);
		transportPriceView = (TextView) findViewById(R.id.goods_detail_transprotprice);
		infoPriceView = (TextView) findViewById(R.id.goods_detail_infoprice);
		qhhlLayout = (LinearLayout) findViewById(R.id.goods_detail_qhhcount);
		qqhView = (TextView) findViewById(R.id.goods_detail_qhhcount_);
		listView = (ListView) findViewById(R.id.goods_detail_listview);
		detailAdapter = new GoodsDetailAdapter(this);
	}
	
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		if(msg.what == DefaultConst.CMD_CONFIRM_DEAL){
			showToast("确认成交!");
			Intent intent = new Intent(this, GoodsSuccessActivity.class);
			intent.putExtra("GsId", gsid);
			startActivity(intent);
			return;
		}
		if(msg.what == DefaultConst.CMD_GOODS_CANCLE){
			return;
		}
		JSONObject data = (JSONObject) msg.obj;
		goods = data.optJSONObject("goodsDetail");
		prices = goods.optJSONArray("PRICES");
		//报价人数
		bjcountView.setText("报价:"+prices.length()+"人");
		//时间
		timeView.setText(goods.optString("GSISSUEDATE"));
		// 出发地
		String ct_depart = Common.splitBX(goods.optString("CT_DEPART"));
		if(Common.isEmpty(Dict.getLocation(ct_depart))){
			departView.setText(app.getLocation());
		}else {
			departView.setText(Dict.getLocation(ct_depart));
		}
		// 目的地
		String ct_target = Common.splitBX(goods.optString("CT_TARGET"));
		targetView.setText(Dict.getLocation(ct_target));
		// 货源信息、需车信息
		StringBuilder info = new StringBuilder();
		info.append(Dict.getGsType(goods.optString("GSTYPE")));
		if(!Common.isEmpty(goods.optString("GSSCALE")) || !Common.isEmpty(goods.optString("GSUNIT")))
			info.append(Common.weightOrVolume(goods.optString("GSSCALE"), goods.optString("GSUNIT")));
		info.append(" 需");
		if(!Common.isEmpty(goods.optString("TKLEN")))
			info.append(Dict.getTruckLength(goods.optString("TKLEN"))).append(" ");
		if(!Common.isEmpty(goods.optString("TKTYPE")))
			info.append(Dict.getTruckType(goods.optString("TKTYPE")));
		if(Common.isEmpty(goods.optString("TKLEN")) && Common.isEmpty(goods.optString("TKTYPE"))){
			info.append("车1辆");
		}else {
			info.append("1辆");
		}
		if(!Common.isEmpty(goods.optString("DCT_PAYWAY")))
			info.append(" ").append(Dict.getPayWay(goods.optString("DCT_PAYWAY")));
		if(!Common.isEmpty(goods.optString("GSLOADDATE")))
			info.append(" ").append(goods.optString("GSLOADDATE")).append("装货");
		if(!Common.isEmpty(goods.optString("DCT_GSLOAD")))
			info.append(" ").append(Dict.getGsLoad(goods.optString("DCT_GSLOAD")));
		gs1View.setText(info);
		//装货地点
		if(!Common.isEmpty(goods.optString("GSDESC"))){
			gs2View.setText(goods.optString("GSDESC"));
		}else {
			gs2View.setText("");
		}
		//可接受运费
		if(!Common.isEmpty(goods.optString("TRANSPORTPRICE")))
			transportPriceView.setText("可接受运费:"+goods.optString("TRANSPORTPRICE")+"元");
		//信息费
		if(!Common.isEmpty(goods.optString("INFOPRICE")))
			infoPriceView.setText("信息费:"+goods.optString("INFOPRICE")+"元");
		infoLayout.setVisibility(View.VISIBLE);
		//几个人在抢
		if(prices.length() == 0){
			nodriver.setVisibility(View.VISIBLE);
		}else {
			nodriver.setVisibility(View.GONE);
			qqhView.setText(prices.length()+"");
			detailAdapter.setData(prices);
			listView.setAdapter(detailAdapter);
			qhhlLayout.setVisibility(View.VISIBLE);
		}
		gsid = goods.optLong("GSID");
	}
	//确认成交
	public void trade(final Object priceid){
		Dialog dialog = new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("确认成交")
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("USRID", shareHelper.getUserId());
						params.put("PRICEID", priceid);
						params.put("GSID", gsid);
						mLoadData.confirmDeal(baseHandler, DefaultConst.CMD_CONFIRM_DEAL, params);
					}
				}).setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
				
		dialog.show();
		
		
	}
	//诚信查询
	public void docriet(String mobile){
		Intent intent = new Intent(this, CreditResultActivity.class);
		intent.putExtra("number", mobile);
		startActivity(intent);
	}
	//打电话
	public void callDriver(String mobile){
		startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + mobile)));	
	}
	public void doFooterCenter(){
		Dialog dialog = new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("撤消本单").setMessage("一旦撤消本单货源，则所有报价均无效。请确认只在在线下找到合适车源时再撤消。")
				.setPositiveButton("撤消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						cancleGoods();
						showToast("撤消成功！");
						startActivity(new Intent(GoodsDetailActivtiy.this, GoodsTodayListActivity.class));
					}
				}).setNegativeButton("返回", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
				
		dialog.show();	
		
	}
	
	public void updateGoods(){
		Dialog dialog = new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("修改").setMessage("一旦执行修改，则本单货源将自动撤消，请确认。")
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//1 先撤销
						cancleGoods();
						//2 再发布
						Intent intent = new Intent(context, GoodsPublishActivity.class);
						intent.putExtra("goods", goods.toString());
						intent.putExtra("update", true);
						startActivity(intent);
						
					}
				}).setNegativeButton("返回", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
				
		dialog.show();	

	}
	private void cancleGoods(){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("USRID", shareHelper.getUserId());
		params.put("GSID", gsid);
		//status 0撤销 1删除
		params.put("Status", "0");
		mLoadData.cancleGoods(baseHandler, DefaultConst.CMD_GOODS_CANCLE, params);
	}
}


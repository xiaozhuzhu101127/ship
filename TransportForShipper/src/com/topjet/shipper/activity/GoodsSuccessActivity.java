package com.topjet.shipper.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topjet.shipper.util.ImageTask;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-20 上午10:09:54  
 * Description:成交货源
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsSuccessActivity extends BaseActivity{
	
	private JSONObject goods;
	
	private JSONObject driver;
	
	private TextView countView;
	
	private TextView timeView;
	
	private TextView departView;
	
	private TextView targetView;
	
	private TextView infoView;
	
	private TextView loadView;
	
	private TextView priceView;
	
	private TextView infopriceView;
	
	private ImageView photoView;
	
	private TextView nameView;
	
	private TextView yslaccountView;
	
	private TextView succountView;
	
	private RatingBar ratingBar;
	
	private TextView pjcountView;
	
	private TextView companyView;
	
	private TextView mobileView;
	
	private LinearLayout gsLayout;
	
	private RelativeLayout drvierLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_success);
		super.onCreate(savedInstanceState);	
		initView();
		initData();	 
	}
	
	public void initData() {
		initProgress("加载中……");
		long GsId = getIntent().getExtras().getLong("GsId");
		mLoadData.getSubmitGoodsInfoDetail(baseHandler, DefaultConst.CMD_GOODS_SUBMIT, GsId);
	}

	private void initView() {
		common_title.setText("货源详细");
		common_recommand.setVisibility(View.VISIBLE);
		footer_location.setVisibility(View.VISIBLE);
		footer_ts.setVisibility(View.VISIBLE);
		footer_center.setText(DefaultConst.TODAYGOODS);
		countView = (TextView) findViewById(R.id.goods_success_count);
		timeView = (TextView) findViewById(R.id.goods_success_time);
		departView = (TextView) findViewById(R.id.goods_success_depart);
		targetView = (TextView) findViewById(R.id.goods_success_target);
		infoView = (TextView) findViewById(R.id.goods_success_gsinfo);
		loadView = (TextView) findViewById(R.id.goods_success_load);
		priceView = (TextView) findViewById(R.id.goods_success_transprotprice);
		infopriceView = (TextView) findViewById(R.id.goods_success_infoprice);
		photoView = (ImageView) findViewById(R.id.goods_success_photo);
		yslaccountView = (TextView) findViewById(R.id.goods_success_yslcount);
		nameView = (TextView) findViewById(R.id.goods_success_name);
		succountView = (TextView) findViewById(R.id.goods_success_succount);
		ratingBar = (RatingBar) findViewById(R.id.goods_success_ratingbar);
		ratingBar.setClickable(false);
		pjcountView = (TextView) findViewById(R.id.goods_success_pjcount);
		companyView = (TextView) findViewById(R.id.goods_success_company);
		mobileView = (TextView) findViewById(R.id.goods_success_mobile);
		gsLayout = (LinearLayout) findViewById(R.id.goods_success_info);
		drvierLayout = (RelativeLayout) findViewById(R.id.goods_success_driver);
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		JSONObject data = (JSONObject) msg.obj;
		goods = data.optJSONObject("goodsDetail");
		driver = goods.optJSONObject("CARRIER");
		//报价人数
		countView.setText("订单号:"+goods.optLong("GSORDERNO"));
		//时间
		timeView.setText(goods.optString("GSISSUEDATE"));
		// 出发地
		String ct_depart = Common.splitBX(goods.optString("CT_DEPART"));
		departView.setText(Dict.getLocation(ct_depart));
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
			info.append(" ").append(goods.optString("GSLOADDATE")).append("装货，");
		if(!Common.isEmpty(goods.optString("DCT_GSLOAD")))
			info.append(" ").append(Dict.getGsLoad(goods.optString("DCT_GSLOAD")));
		
		infoView.setText(info);
		//装货地点
		if(!Common.isEmpty(goods.optString("GSDESC"))){
			loadView.setText(goods.optString("GSDESC"));
		}else {
			loadView.setVisibility(View.GONE);
		}
		//成交价
		priceView.setText("成交价:"+goods.optString("SUBMITPRICE")+"元");
		//信息费
		if(!Common.isEmpty(goods.optString("GSDESC"))){
			infopriceView.setText("信息费:"+goods.optLong("TRANSPORTPRICE")+"元");
		}else {
			infopriceView.setText("信息费:面议");
		}
		//照片
		new ImageTask(photoView).execute(driver.optString("PHOTOURL"));
		//姓名
		nameView.setText(goods.optString("CALLUSRREALNAME"));
		//诚信值
		yslaccountView.setText("诚信值:"+driver.optLong("YSLACCOUNT"));
		//历史成交数
		succountView.setText("历史成交:"+driver.optLong("HISTORYSUBMIT")+"笔");
		//好评度
		ratingBar.setRating(driver.optLong("CARRYPOINT"));
		//评价数
		pjcountView.setText(driver.optLong("CARRYCOUNT")+"人评价");
		//公司名称
		companyView.setText("公司:"+driver.optString("CARRIERCOMPANY"));
		//手机号码
		mobileView.setText(driver.optString("CARRIERMOBILE"));
		gsLayout.setVisibility(View.VISIBLE);
		drvierLayout.setVisibility(View.VISIBLE);
	}
	public void doFooterCenter() {
		startActivity(new Intent(this, GoodsTodayListActivity.class));
	}
	//投诉
	public void tousu() {
		Intent intent = new Intent(this, CreditTouShuActivity.class);
		intent.putExtra("mobile", driver.optString("CARRIERMOBILE"));
		startActivity(intent);
	}
	//定位
	public void locationDriver() {
		Intent intent = new Intent(this, GoodsLocationDriverActivity.class);
		intent.putExtra("USRID", shareHelper.getUserId());
		intent.putExtra("TKMOBILE", driver.optString("CARRIERMOBILE"));
		intent.putExtra("gsid", goods.optLong("GSID"));
		intent.putExtra("PHOTOURL", driver.optString("PHOTOURL"));
		intent.putExtra("HISTORYSUBMIT", driver.optLong("HISTORYSUBMIT"));
		startActivity(intent);
	}
	public void click(View v){
		startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + driver.optString("CARRIERMOBILE"))));	
	}
}

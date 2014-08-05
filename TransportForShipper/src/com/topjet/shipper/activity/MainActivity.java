package com.topjet.shipper.activity;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.model.UserInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.ImageTask;

 
/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-13 上午10:23:12  
 * Description:
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class MainActivity extends BaseActivity {
	
	private TextView todayGoods;
	
	private TextView thhygoods;
	
	private TextView aroundTs;
	
	private TextView yslaccount;
	
	private TextView userText;
	
	private TextView company;
	
	private RatingBar ratingBar;
	
	private TextView pjcount;
	
	private TextView jifen;
	
	private RelativeLayout main_info;
	
	private ImageView pictureImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);	
		initView();
		fetchData();
		//app.setAlarmLocation();
		//app.registerInCallPhoneListener();
	}
	private void fetchData(){
		initProgress("加载中……");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USRID", shareHelper.getUserId());
		params.put("cityCode", app.getLocationCode());
		mLoadData.getStatInfo(baseHandler, DefaultConst.CMD_DP_GET_STATEINFO, params);
	}
	private void initView() {
		todayGoods = (TextView) findViewById(R.id.todayGoods);
		thhygoods = (TextView) findViewById(R.id.thhy_goods);
		aroundTs = (TextView) findViewById(R.id.aroundTs);
		yslaccount = (TextView) findViewById(R.id.main_yslaccount);
		userText = (TextView) findViewById(R.id.main_user);
		company = (TextView) findViewById(R.id.main_company);
		ratingBar = (RatingBar) findViewById(R.id.main_ratingbar);
		ratingBar.setClickable(false);
		pjcount = (TextView) findViewById(R.id.main_pjcount);
		jifen = (TextView) findViewById(R.id.main_jifen);
		main_info = (RelativeLayout) findViewById(R.id.main_info);
		pictureImageView = (ImageView) findViewById(R.id.main_pic);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	    app.unRegisterInCallPhoneListener();
	}

	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		//今日货源
		case R.id.today_goods:
			intent = new Intent(MainActivity.this, GoodsTodayListActivity.class);
			startActivity(intent);
			break;
		//发布货源
		case R.id.publish_goods:
			intent = new Intent(MainActivity.this, GoodsPublishActivity.class);
			startActivity(intent);
			break;
		//网上货源
		case R.id.tx_goods:
			intent = new Intent(MainActivity.this, GoodsPeerListActivity.class);
			//网上货源
			intent.putExtra("isMain",true);
			intent.putExtra("CITYCODE", app.getLocationCode());
			startActivity(intent);
			break;
		//车源推荐
		case R.id.aroundtk:
			intent = new Intent(this, TruckRecommendListActivity.class);
			intent.putExtra("Depart", app.getLocationCode());
			intent.putExtra("city", app.getLocation());
			//周边空车
			intent.putExtra("around", true);
			startActivity(intent);
			break;
		//历史货源
		case R.id.history_goods:
			intent = new Intent(MainActivity.this, GoodsHistoryActivity.class);
			startActivity(intent);
			break;
		//诚信查询
		case R.id.cxcx:
			intent = new Intent(MainActivity.this, CreditQueryActivity.class);
			startActivity(intent);
			break;
		//会员福利
		case R.id.hyfl:
			intent = new Intent(MainActivity.this, MemberWealActivity.class);
			startActivity(intent);
			break;	
		//更多应用
		case R.id.moreyy:
			intent = new Intent(MainActivity.this, MoreActivity.class);
			startActivity(intent);
			break;
		//我的560
		case R.id.main_my560:
			intent = new Intent(MainActivity.this, MyDetailActivity.class);
			startActivity(intent);
			break;
		}
	}

 
	@Override
	public void onBackPressed() {
		//返回laucher
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
	}

	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		JSONObject result = (JSONObject) msg.obj;
		JSONObject GsStatInfo = result.optJSONObject("GsStatInfo");
		if(null == GsStatInfo){
			showToast("暂无数据");
			return;
		}
		todayGoods.setText(GsStatInfo.optLong("countOwnerGs")+"条新信息");
		thhygoods.setText(GsStatInfo.optLong("totalGs")+"条新信息");
		aroundTs.setText("全国共"+GsStatInfo.optLong("aroundTs")+"辆空车");
		
		//照片
		if(!Common.isEmpty(app.getUserInfo().photo)){
//			new ImageTask(pictureImageView).execute(app.getUserInfo().photo);
			byte b[]=android.util.Base64.decode(app.getUserInfo().photo,Base64.DEFAULT);
			Bitmap bm = BitmapFactory.decodeByteArray(b, 0,b.length);
			pictureImageView.setImageBitmap(bm);
		}
		yslaccount.setText(GsStatInfo.optLong("yslAccount")+"");
		ratingBar.setRating(GsStatInfo.optLong("shipPoint"));
		pjcount.setText(GsStatInfo.optLong("dpTotal")+"个评价");
		StringBuffer sbf = new StringBuffer();
		String service = app.getUserInfo().service;
		if(!Common.isEmpty(service) && null != Dict.getUserLevel(service))
			sbf.append(Dict.getUserLevel(service)).append("/");
		sbf.append(GsStatInfo.optString("point")).append("积分");
		jifen.setText(sbf);
		UserInfo userInfo = app.getUserInfo();
		userText.setText(userInfo.realName);
		if(Common.isEmpty(userInfo.companyName)){
			company.setVisibility(View.GONE);
		}else {
			company.setText(userInfo.companyName);
		}
		main_info.setVisibility(View.VISIBLE);

	}
}

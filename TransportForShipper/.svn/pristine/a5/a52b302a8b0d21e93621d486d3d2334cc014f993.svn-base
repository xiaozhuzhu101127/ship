package com.topjet.shipper.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.ImageTask;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.DefaultConst;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-20 上午10:09:54  
 * Description:定位司机
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsLocationDriverActivity extends BaseActivity{
	
	private JSONObject currentGPS;
	
	private JSONObject driver;
	
	private ImageView photoView;
	
	private TextView nameView;
	
	private TextView yslaccountView;
	
	private TextView succountView;
	
	private RatingBar ratingBar;
	
	private TextView pjcountView;
	
	private TextView companyView;
	
	private TextView mobileView;
	
	private RelativeLayout drvierLayout;
	//百度地图
	private BMapManager mBMapMan = null;  
	
	private MapView mMapView = null; 
	
	private Bundle extra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mBMapMan=new BMapManager(getApplication());  
		mBMapMan.init(null);
		setContentView(R.layout.activity_goods_location_driver);
		super.onCreate(savedInstanceState);	
		extra = getIntent().getExtras();
		initView();
		initData();	 
		initbaidu();
		
	}
	
	private void initbaidu() {
		
		//1 初始化注意：请在试用setContentView前初始化BMapManager对象，否则会报错  
		mMapView=(MapView)findViewById(R.id.bmapsView);  
		mMapView.setBuiltInZoomControls(true);  
		//设置启用内置的缩放控件  
		MapController mMapController=mMapView.getController();  
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
		mMapController.setZoom(12);//设置地图zoom级别  
	}

	public void initData() {
		initProgress("加载中……");
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != extra){
			params.put("USRID", extra.getLong("USRID"));
			params.put("TKMOBILE", extra.getString("TKMOBILE"));
			params.put("gsid", extra.getLong("gsid"));
		}
		mLoadData.getCurrentGPS(baseHandler, DefaultConst.CMD_GOODS_SUBMIT, params);
	}

	private void initView() {
		common_recommand.setVisibility(View.VISIBLE);
		common_title.setText("货源详细");
		photoView = (ImageView) findViewById(R.id.goods_success_photo);
		nameView = (TextView) findViewById(R.id.goods_success_yslcount);
		yslaccountView = (TextView) findViewById(R.id.goods_success_name);
		succountView = (TextView) findViewById(R.id.goods_success_succount);
		ratingBar = (RatingBar) findViewById(R.id.goods_success_ratingbar);
		ratingBar.setClickable(false);
		pjcountView = (TextView) findViewById(R.id.goods_success_pjcount);
		companyView = (TextView) findViewById(R.id.goods_success_company);
		mobileView = (TextView) findViewById(R.id.goods_success_mobile);
		drvierLayout = (RelativeLayout) findViewById(R.id.goods_success_driver);
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		JSONObject data = (JSONObject) msg.obj;
		currentGPS = data.optJSONObject("currentGPS");
		if(null != extra){
			//手机号码
			mobileView.setText(extra.getString("TKMOBILE"));
			//历史成交数
			succountView.setText("历史成交:"+extra.getLong("HISTORYSUBMIT")+"笔");
			//照片
			if(!Common.isEmpty(extra.getString("PHOTOURL")))
				new ImageTask(photoView).execute(extra.getString("PHOTOURL"));
		}
		//姓名
		nameView.setText(currentGPS.optString("realName"));
		//诚信值
		yslaccountView.setText("诚信值:"+currentGPS.optLong("yslAccount"));
		
		//好评度
		ratingBar.setRating(currentGPS.optLong("CARRYPOINT"));
		//评价数
		pjcountView.setText(currentGPS.optLong("BCARRYCOUNT")+"人评价");
		//公司名称
		companyView.setText("公司:"+currentGPS.optString("companyName"));
		drvierLayout.setVisibility(View.VISIBLE);
		if(currentGPS.optDouble("LAT")==0 && currentGPS.optDouble("LNG")==0){
			showToast("暂无位置信息……");
			return;
		}
		showMap();
	}
	public void showMap(){
		//2添加位置
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);  
		LocationData locData = new LocationData();  
		//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）  
		locData.latitude = currentGPS.optDouble("LAT");  
		locData.longitude = currentGPS.optDouble("LNG");
//		locData.latitude = 39.945;  
//		locData.longitude = 116.404;  
		locData.direction = 2.0f;  
		myLocationOverlay.setData(locData);  
		//指定图片.默认是蓝色的点
		Drawable mark= getResources().getDrawable(R.drawable.ztb);  
//		myLocationOverlay.setMarker(mark);
		mMapView.getOverlays().add(myLocationOverlay);  
		mMapView.refresh();  
		mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude*1e6),  
		(int)(locData.longitude* 1e6)));
	}
	public void click(View v){
		startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + extra.getString("TKMOBILE"))));	
	}
    @Override  
    protected void onDestroy(){  
            mMapView.destroy();  
            if(mBMapMan!=null){  
                    mBMapMan.destroy();  
                    mBMapMan=null;  
            }  
            super.onDestroy();  
    }  
    @Override  
    protected void onPause(){  
            mMapView.onPause();  
            if(mBMapMan!=null){  
                   mBMapMan.stop();  
            }  
            super.onPause();  
    }  
    @Override  
    protected void onResume(){  
            mMapView.onResume();  
            if(mBMapMan!=null){  
                    mBMapMan.start();  
            }  
           super.onResume();  
    }  
}

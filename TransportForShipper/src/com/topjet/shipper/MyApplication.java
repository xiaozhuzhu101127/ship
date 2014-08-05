package com.topjet.shipper;


import com.audividi.imageloading.cache.naming.Md5FilesNameGenerator;
import com.audividi.imageloading.core.ImageLoader;
import com.audividi.imageloading.core.ImageLoaderConfiguration;

import com.topjet.shipper.listener.AlarmReceiver;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.broadcast.BootupReceiver;
import com.topjet.shipper.core.LoadData;
import com.topjet.shipper.db.Database;
import com.topjet.shipper.listener.InCallPhoneStateListener;
import com.topjet.shipper.listener.PollingService;
import com.topjet.shipper.location.LocationManager;
import com.topjet.shipper.model.GoodsParameter;
import com.topjet.shipper.model.UserInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.CrashHandler;
import com.topjet.shipper.util.ScreenManager;
import com.topjet.shipper.util.ShareHelper;
import com.topjet.shipper.util.TransportLog;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MyApplication extends Application {

	static MyApplication app;

	private LoadData mLoadData;
	private ShareHelper mShareHelper;
	private Database db;
	private LocationManager mLocationManager;
	private PendingIntent apkUpdatesender, locationSender;
	private AlarmManager apkAlarm, locationAlarm;
	private ScreenManager mScreenManager;
	private InCallPhoneStateListener inCallPhoneListener;

	private String addressDetail;
	private long time;
	private double longitude, longitudeLast;
	private double latitude, latitudeLast;
	private String location;
	private String province;
	private String locationCode;
	private String sessionId;
	private UserInfo userInfo;
	private GoodsParameter goodsParameter;
	private long memberID = -1;

	// private boolean isApkUpdate = false;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//暂时这么用，解决应用被强制重启，userInfo实例丢失问题
		app = this;
		userInfo = new UserInfo();
		CrashHandler.getInstance().init(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2).memoryCacheSize(1500000)
				// 1.5 Mb
				.denyCacheImageMultipleSizesInMemory().discCacheFileCount(200)
				.discCacheFileNameGenerator(new Md5FilesNameGenerator()).enableLogging().build();
		ImageLoader.getInstance().init(config);
		mShareHelper = ShareHelper.getInstance(getApplicationContext());
		mLoadData = LoadData.getInstance();
		//防止应用挂掉，经纬度数据丢失
		gainLngAndLat();
		mScreenManager = ScreenManager.getScreenManager();
		try {
			db = Database.getInstance(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TransportLog.e("TransportApplication", "====================数据库创建报错:" + e.toString());
		}
//		registerInCallPhoneListener();
		if(Common.noInstallDriver()){
			//来电弹屏
			
			Intent intent = new Intent(this ,BootupReceiver.class); 
			sendBroadcast(intent);
		}
	      
		startService(new Intent(this, PollingService.class));
//		startService(new Intent(this, GoodsService.class));
		//消息提醒和位置上传，消息提醒关闭掉。
		setAlarmLocation();
	}
	// public void setApkUp(boolean isApkUpdate){
	// this.isApkUpdate = isApkUpdate;
	// }
	//
	// public boolean getApkUp(){
	// return this.isApkUpdate;
	// }

	public void registerInCallPhoneListener() {
		// 侦听来电状态
		TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if (inCallPhoneListener != null) {
			telMgr.listen(inCallPhoneListener, PhoneStateListener.LISTEN_NONE);
			inCallPhoneListener = null;
		}
		//实例化实用getApplicationContext
		inCallPhoneListener = new InCallPhoneStateListener(this);
		
		telMgr.listen(inCallPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void unRegisterInCallPhoneListener() {
		TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telMgr.listen(inCallPhoneListener, PhoneStateListener.LISTEN_NONE);
	}

	public ScreenManager getScreenManager() {
		return mScreenManager;
	}

	/*
	 * private void setAlarmApkUpdate() { // TODO Auto-generated method stub
	 * Intent intent =new Intent(this, AlarmReceiver.class);
	 * intent.setAction(DefaultConst.ALARM_APKUPDATE_ACTION);
	 * 
	 * apkUpdatesender=PendingIntent .getBroadcast(this, 0, intent, 0); long
	 * spinTime= 3 * 24 * 60 * 60 * 1000; //开始时间 long
	 * firstime=SystemClock.elapsedRealtime();
	 * apkAlarm=(AlarmManager)getSystemService(ALARM_SERVICE);
	 * apkAlarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP ,
	 * firstime,spinTime, apkUpdatesender); }
	 */

	public void cancelAlarm() {
		if (null != mLocationManager) {
			mLocationManager.destroy();
			mLocationManager = null;
		}
		if (null != apkAlarm) {
			if (null != apkUpdatesender)
				apkAlarm.cancel(apkUpdatesender);
		}
		if (null != locationSender) {
			if (null != locationSender)
				locationAlarm.cancel(locationSender);
		}
	}
	public void setAlarmLocation() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.setAction(DefaultConst.ALARM_LOCATION_ACTION);

	//	locationSender = PendingIntent.getBroadcast(this, 0, intent, 0);
		  //修改
		locationSender = PendingIntent.getBroadcast(this,0, intent, 0);
		// 开始时间
		long firstime = SystemClock.elapsedRealtime();
		
		locationAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		locationAlarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 5 * 60 * 1000, locationSender);
		
		// locationAlarm
		// .setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime,
		// 5 * 60 * 1000, locationSender);
	}
	public void startRequestLocation() {
		if (null != mLocationManager) {
			mLocationManager.destroy();
			mLocationManager = null;
		}
		//当有多个主线程的时候，则this就不起作用
		mLocationManager = new LocationManager(getApplicationContext());
		mLocationManager.requestLocation();
	}

	public Database getDb() {
		return db;
	}

	public ShareHelper getShareHelper() {
		return mShareHelper;
	}

	public LoadData getLoadData() {
		return this.mLoadData;
	}

	public static MyApplication getInstance() {
		return app;
	}

	@Override
	public void onTerminate() {
		//关闭来电弹屏
		unRegisterInCallPhoneListener();
		//关闭位置上传
		cancelAlarm();
		if (null != mLoadData)
			mLoadData.stop();
		if (null != db)
			db.closeDB();
		//关闭版本更新
		stopService(new Intent(this, PollingService.class));
		//自杀
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onTerminate();
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * 详细位置信息
	 * 
	 * @param address
	 */
	public void setAddressDetail(String... address) {
		addressDetail = Common.join(address, ",");
	}

	/**
	 * 详细位置信息
	 * 
	 * @return
	 */
	public String getAddressDetail() {
		return addressDetail;
	}

	/**
	 * 定位时间
	 * 
	 * @param time
	 */
	public void setLocationTime(long time) {
		this.time = time;

	}

	/**
	 * 定位时间
	 * 
	 * @return
	 */
	public long getLocationTime() {
		return time;
	}

	/**
	 * 坐标
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 坐标
	 * 
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 坐标
	 * 
	 * @param longitude
	 */
	public void setLastLongitude(double longitude) {
		longitudeLast = longitude;
	}

	/**
	 * 上次坐标
	 * 
	 * @return
	 */
	public double getLastLongitude() {
		return longitudeLast;
	}

	/**
	 * 上次坐标
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;

	}

	/**
	 * 坐标
	 * 
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 上次坐标
	 * 
	 * @param latitude
	 */
	public void setLastLatitude(double latitude) {
		latitudeLast = latitude;
	}

	/**
	 * 上次坐标
	 * 
	 * @return
	 */
	public double getLastLatitude() {
		return latitudeLast;
	}

	/**
	 * 当前位置（市级）
	 * 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 当前位置（市级）
	 * 
	 * @return
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * 当前位置（省级）
	 * 
	 * @param location
	 */
	public void setLocationProvince(String province) {
		this.province = province;
	}

	/**
	 * 当前位置（省级）
	 * 
	 * @return
	 */
	public String getLocationProvince() {
		return province;
	}
	/**
	 * 当前位置代码（市级）
	 * 
	 * @param location
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * 当前位置代码（市级）
	 * 
	 * @return
	 */
	public String getLocationCode() {
		return locationCode;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
		mShareHelper.setSession(sessionId);
	}

	public String getSessionId() {
		if (Common.isEmpty(sessionId))
			sessionId = mShareHelper.getSession();
		return sessionId;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
		mShareHelper.setUserId(memberID);
	}

	public long getMemberID() {
		if (memberID == -1)
			memberID = mShareHelper.getUserId();
		return memberID;
	}
	
	
	
	public void setLngAndLat(double longitude,double latitude) {
		mShareHelper.putString("longitude", String.valueOf(longitude));
		mShareHelper.putString("latitude", String.valueOf(latitude));		
	}

	public void gainLngAndLat() {
		this.longitude =Double.parseDouble(mShareHelper.getString("longitude", "0.0"));
		this.latitude =Double.parseDouble(mShareHelper.getString("latitude", "0.0"));
	}

	public GoodsParameter getGoodsParameter() {
		return goodsParameter;
	}

	public void setGoodsParameter(GoodsParameter goodsParameter) {
		this.goodsParameter = goodsParameter;
	}


}

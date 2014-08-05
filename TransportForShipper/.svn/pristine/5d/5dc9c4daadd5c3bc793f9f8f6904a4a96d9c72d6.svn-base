package com.topjet.shipper.listener;



import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.apkupdate.UpgradeManager;

import android.app.Service;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


 

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-25 上午10:08:51  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class PollingService extends Service {
 
	Thread mPollingThread;
	Handler mPollingHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch(msg.what){
				case CMD_APK_UPDATE:
					UpgradeManager.getInstance(BaseActivity.context).checkVersion(false);
					break;
				}
		}
	};
	final static long spinTime=  3 * 24 * 60 * 60 * 1000;
	final static int CMD_APK_UPDATE = 0x100;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {	 
		super.onCreate();	 
		mPollingThread = new Thread() {
			@Override
			public void run() {
				mPollingHandler.postDelayed(this, spinTime);
				mPollingHandler.sendEmptyMessage(CMD_APK_UPDATE);			
			}
		};		
		mPollingThread.start();
	}

	@Override
	public void onDestroy() {
		if (null != mPollingThread) mPollingThread.interrupt();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	 

	 
}

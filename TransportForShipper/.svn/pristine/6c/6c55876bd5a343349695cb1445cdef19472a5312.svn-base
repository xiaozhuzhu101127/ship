package com.topjet.shipper.listener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallPhoneService extends Service {

	private InCallPhoneStateListener inCallPhoneListener;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate() {	 
		super.onCreate();	 
		TelephonyManager telManager= (TelephonyManager) this

	              .getSystemService(this.getApplicationContext().TELEPHONY_SERVICE);

	       // 注册一个监听器对电话状态进行监听
		inCallPhoneListener =new InCallPhoneStateListener(this.getApplicationContext());
	       telManager.listen(inCallPhoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telMgr.listen(inCallPhoneListener, PhoneStateListener.LISTEN_NONE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	

}

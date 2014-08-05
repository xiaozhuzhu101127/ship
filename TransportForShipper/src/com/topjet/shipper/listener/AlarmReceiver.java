package com.topjet.shipper.listener;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.topjet.shipper.MyApplication;
import com.topjet.shipper.model.MessageInfo;
import com.topjet.shipper.notification.NotificationHelper;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.DisplayUtil;
import com.topjet.shipper.util.TransportLog;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-8 下午3:34:23  
 * Description:消息广播。接收消息
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class AlarmReceiver extends BroadcastReceiver {
	Context context;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			//消息提醒
//			case DefaultConst.CMD_PUSH_MESSAGE:
//				doMessage(msg);
//				break;
			//位置上传
			case 1:
				MyApplication.getInstance().startRequestLocation();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				doLocation();
				TransportLog.i(AlarmReceiver.class.getName(), "====ALARM_LOCATION_ACTION===");
			}
			
		}

		private void doMessage(Message msg) {
			// TODO Auto-generated method stub
			JSONArray array = ((JSONObject) msg.obj).optJSONArray("messages");
			if (array.length() == 0) {
				return;
			}
			ArrayList<MessageInfo> msgInfos = new ArrayList<MessageInfo>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject g = array.optJSONObject(i);
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.photo = g.optString("photoaddr");
				messageInfo.name = g.optString("REALNAME");
				messageInfo.credit = g.optString("YSLACCOUNT");
				JSONObject t = g.optJSONObject("CREATE_TIME");
				messageInfo.info = new StringBuilder().append(Dict.getCityName(g.optString("CT_REGOIN"), 2))
						.append(" ").append(DisplayUtil.renderDate(new Date(t.optLong("time")), "M-dd HH:mm"))
						.toString();
				messageInfo.content = g.optString("MSG");
				messageInfo.mobile = g.optString("MOBILE");
				msgInfos.add(messageInfo);
			}
			MyApplication.getInstance().getDb().addMessage(msgInfos);
			NotificationHelper.getInstance(context).showNotification();
		}
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		if (intent.getAction().equals(DefaultConst.ALARM_LOCATION_ACTION)) {
			//因为CMSS得关系，先做一次判断，判断是否能正常上网
//			if(!openUrl()){
//				return;
//			}
			//android 4.0之后做了特殊处理，不能在主线程中访问网络
			new Thread(){  
	            @Override  
	            public void run() {  
	                // TODO Auto-generated method stub  
	                super.run();  
	                 if(openUrl()){
	                	Message message= mHandler.obtainMessage(1);
	                	 mHandler.sendMessage(message);
	                 };
	                
	                
	               
	            }  
	        }.start();
	        
			
		} /*else if (intent.getAction().equals(DefaultConst.ALARM_APKUPDATE_ACTION)) {
			doApkUpdate();
			TransportLog.i(AlarmReceiver.class.getName(), "====ALARM_APKUPDATE_ACTION===");
		}*/
	}

	/*
	 * private void doApkUpdate() { // TODO Auto-generated method stub if
	 * (MyApplication.getInstance().getApkUp()) return;
	 * TransportApplication.getInstance().setApkUp(true);
	 * UpgradeManager.getInstance(BaseActivity.context).checkVersion(false);
	 * MyApplication.getInstance().ch }
	 */

	private void doLocation() {
		// TODO Auto-generated method stub
		MyApplication.getInstance().getLoadData().pushMessage(mHandler, DefaultConst.CMD_PUSH_MESSAGE);
	}
	
	
	public static boolean openUrl() {
		  String myString="";
		  try {
		   URL url = new URL("http://www.566560.com/d/test.htm");
		   URLConnection urlCon = url.openConnection();
		   urlCon.setConnectTimeout(1500);
		   InputStream is = urlCon.getInputStream();
		   BufferedInputStream bis = new BufferedInputStream(is);
		   // 用ByteArrayBuffer缓存
		   ByteArrayBuffer baf = new ByteArrayBuffer(50);
		   int current = 0;
		   while ((current = bis.read()) != -1) {
		    baf.append((byte) current);
		   }
		   
		   myString = new String(baf.toByteArray(), "UTF-8");
		   bis.close();
		   is.close();
		  } catch (Exception e) {
		   Log.e("AlarmReceiver", e.getMessage());
		   e.printStackTrace();
		   
		   return false;
		  } if(myString.indexOf("200")>-1){////////这句是判断是否真正的连接到了百度上，因为如果连接了  CMCC 是需要用户名和密码才能正常上网的，
			   return true;
		  }else{
		   return false;
		  }
		}

}

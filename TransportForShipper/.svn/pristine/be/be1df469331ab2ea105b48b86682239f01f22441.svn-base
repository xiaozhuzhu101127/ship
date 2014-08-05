package com.topjet.shipper.notification;

import com.topjet.shipper.R;
import com.topjet.shipper.activity.MessageActivity;
 
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-8 下午3:33:45  
 * Description:消息通知
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class NotificationHelper {
	private Context mContext;	
	private static NotificationHelper instance;
	
	public static NotificationHelper getInstance(Context context){
		if (instance == null) {
			synchronized (NotificationHelper.class) {
				if (instance == null) {
					instance = new NotificationHelper(context);
				}
			}
		}	
		return instance;		
	}

	public NotificationHelper(Context context) {
		mContext = context;
	}
	
	public void showNotification() {
	    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.drawable.setting_about_ysx);
        builder.setContentTitle("新的消息");
        builder.setTicker("新的消息");
        builder.setContentText("您有新的消息！");
        builder.setSound(soundUri);
        //0不需要图标
//        builder.addAction(R.drawable.ysx, "查看", pIntent)
//		.addAction(0, "Remind", pIntent);
        final Intent notificationIntent = new Intent(mContext, MessageActivity.class);
        final PendingIntent pi = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
        builder.setContentIntent(pi);
        final Notification notification = builder.build();
        // notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        // notification.flags |= Notification.FLAG_NO_CLEAR;
        // notification.flags |= Notification.FLAG_ONGOING_EVENT;
        //查看后消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

	public void cancelNotification(int notificationId) {

		if (Context.NOTIFICATION_SERVICE != null) {
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager nMgr = (NotificationManager) mContext.getSystemService(ns);
			nMgr.cancel(notificationId);
		}
	}

}

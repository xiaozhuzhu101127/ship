package com.topjet.shipper.broadcast;


import com.topjet.shipper.listener.CallPhoneService;
import com.topjet.shipper.listener.PollingService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// better delay some time.
		try {
			
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Intent i = new Intent(context, MainActivity.class);
//		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(i);
//		context.startService(new Intent(context, PollingService.class));
		context.startService(new Intent(context, CallPhoneService.class));
	}

}

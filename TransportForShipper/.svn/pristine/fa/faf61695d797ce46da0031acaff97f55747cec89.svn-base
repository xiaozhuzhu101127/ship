package com.topjet.shipper.location;



import com.topjet.shipper.MyApplication;
import com.topjet.shipper.util.Common;

import android.content.Context;


 

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-6 上午9:35:42  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class LocationManager {
	private BaiduMapLocationProvider mLocationProvider;

	public LocationManager(Context context){
		mLocationProvider = new BaiduMapLocationProvider(context);		
	}
  
	public void requestLocation(){
		if(Common.checkNet(MyApplication.getInstance().getApplicationContext())){
			mLocationProvider.start();
			mLocationProvider.requestLocation();
			mLocationProvider.stop();
		}
	}

	public void destroy(){		
		mLocationProvider.destroy();	
	}

	 
}

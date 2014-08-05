package com.topjet.shipper.listener;

import java.util.Date;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.goods.GoodsManager;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.ShareHelper;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-27 下午3:09:00  
 * Description:轮询是否成交
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsService extends Service{
	
	private GoodsManager goodsManager;
	private boolean callPrice;
	private boolean checkDeal;
	private boolean isLogin;
	private Thread checkThread;
	private ShareHelper shareHelper;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			 	case DefaultConst.CMD_GOODS_CHECK:
					//没有轮询过成交信息并且报过价才轮询
					if(!checkDeal && callPrice && isLogin){
						goodsManager = GoodsManager.getIntance(BaseActivity.context);
						goodsManager.checkGoods();
					}
			 	break;
			}
		}
		
	};
	
	@Override
	public void onCreate() {
		//这样只有一个goodsManager实例，contenxt则一直是loginActivity,不能正确判断是否在成交详情页
//		if(null == goodsManager)
//			goodsManager = GoodsManager.getIntance(BaseActivity.context);
		if(null == shareHelper)
			shareHelper = ShareHelper.getInstance(BaseActivity.context);
		checkThread = new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				//初始化
				if(new Date().getHours() >= 23){
					shareHelper.setCheckDeal(false);
					shareHelper.setCallPrice(false);
				}
				checkDeal = shareHelper.isCheckDeal();
				callPrice = shareHelper.isCallPrice();
				isLogin = shareHelper.isLogin();
				handler.postDelayed(this, DefaultConst.GOODS_CHECK_TIME);
				handler.sendEmptyMessage(DefaultConst.CMD_GOODS_CHECK);
			}
		});
		
		checkThread.start();
	}


	
	@Override
	public void onDestroy() {
		if(null != checkThread || checkThread.isAlive())
			checkThread.interrupt();
		super.onDestroy();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}

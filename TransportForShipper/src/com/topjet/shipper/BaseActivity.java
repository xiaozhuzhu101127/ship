package com.topjet.shipper;
 
import java.util.HashSet;
import java.util.Set;

import com.topjet.shipper.activity.ShuizaiChaWoActivity;
import com.topjet.shipper.activity.RecommendActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.activity.CreditCorrectActivity;
import com.topjet.shipper.activity.GoodsHistoryActivity;
import com.topjet.shipper.activity.GoodsPeerListActivity;
import com.topjet.shipper.activity.GoodsTodayListActivity;
import com.topjet.shipper.activity.MainActivity;
import com.topjet.shipper.core.LoadData;
import com.topjet.shipper.db.Database;
import com.topjet.shipper.util.CloseAble;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.DialogUtil;
import com.topjet.shipper.util.MyDialog;
import com.topjet.shipper.util.ScreenManager;
import com.topjet.shipper.util.ShareHelper;
import com.topjet.shipper.util.TransportLog;
 
 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity  extends Activity implements CloseAble{
	protected   ShareHelper shareHelper;
	protected   Database db;
	protected   InputMethodManager imm;  
	protected   MyApplication app;	
	protected   ProgressDialog mDialog;
	protected 	boolean  isDestroy = false;
	//head
	protected 	TextView common_title;
	protected   TextView title_place;
	protected   Button title_goods;
	protected   Button common_back;
	protected   Button common_main;
	protected   Button common_recommand;
	//footer
	protected   TextView footer_refresh;
	protected   TextView footer_history;
	protected   TextView footer_center;
	protected   TextView footer_update;
	protected   TextView footer_location;
	protected   TextView footer_ts;
	protected   TextView footer_delete;
	protected   TextView footer_cancle;
	protected   TextView footer_goods_today;
	protected   TextView footer_goods_history;
	protected   TextView footer_szcw;
	protected   TextView footer_bc;
	protected   TextView footer_delmsg;
	
	protected 	Button btn_other_function;
	protected   ScreenManager mScreenManager;
	public static Context context;
	protected Toast mToast;
	
	/**
	 * 弹出框的触发表单
	 */
	protected TextView picking;
	/**
	 * 正在显示的弹框
	 */
	protected MyDialog showing;
	
	/**
	 * 是否title的切换地区
	 */
	protected boolean isTitle = false;
	
	private static Set<Activity> COLSEABLE = new HashSet<Activity>();
	
	@SuppressLint("HandlerLeak")
	protected   Handler baseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			hindProgress();
			switch (msg.what) {		
			case DefaultConst.CMD_LOGIN:	
			case DefaultConst.CMD_APK_UPDATE:	
			case DefaultConst.CMD_LOGIN_OUT:	
			case DefaultConst.CMD_MOBILE_RESET_PWD:				
			case DefaultConst.CMD_TRUCK_STATUS:
			case DefaultConst.CMD_UP_RECOMMEND:
			case DefaultConst.CMD_REPORT_CONTACT_INFO_ERR:
			case DefaultConst.CMD_GET_LOCATION_REQ:
			case DefaultConst.CMD_UPDATE_LOCATION_REQ:
			case DefaultConst.CMD_UP_PHONELIST:
			case DefaultConst.CMD_GET_MOBILE_MEMBER_INFO_BY_ID:
			case DefaultConst.CMD_UP_MOBILE_MEMBER_INFO:	
			case DefaultConst.CMD_DP_INFO_QUERY_RESULT:
			case DefaultConst.CMD_DP_INFO_ADD_RESULT:
			case DefaultConst.CMD_DP_DETAIL_LIST_RESULT:
			case DefaultConst.CMD_QHH_FETCHDATA:
			case DefaultConst.CMD_GOODS_DETAIL:
			case DefaultConst.CMD_GOODS_DEAL_DETAIL:
			case DefaultConst.CMD_GOODS_COUNTDOWNTIME:
			case DefaultConst.CMD_GOODS_HISTORY:
			case DefaultConst.CMD_GOODS_DEAL:
			case DefaultConst.CMD_DP_GET_CHA_KAN_RESULT:
			case DefaultConst.CMD_DP_GET_STATEINFO:
			case DefaultConst.CMD_GOODS_CURRENTINFO:
			case DefaultConst.CMD_CONFIRM_DEAL:
			case DefaultConst.CMD_GOODS_CANCLE:
			case DefaultConst.CMD_GOODS_PEER:
			case DefaultConst.CMD_GOODS_SUBMIT:
			case DefaultConst.CMD_GOODS_PUBLISH:
			case DefaultConst.CMD_GET_TRUCKLIST:
			case DefaultConst.CMD_GET_TRUCKINFO:
			case DefaultConst.CMD_GET_CURRENT_GPS:
			case DefaultConst.CMD_UPDATE_PASSWORD:
			case DefaultConst.CMD_PUSH_MESSAGE:
				ioHandler(msg);			
				break;
			case DefaultConst.CMD_ERROR_NET_ADD_REQUEST:			 
			case DefaultConst.CMD_ERROR_NET_REQUEST_ERROR:				 
			case DefaultConst.CMD_ERROR_NET_RETURN_FAILED:	
				doError(msg);
				break;
			case DefaultConst.CMD_CMSS_NET_ADD_REQUEST:
				doCmssRequest(msg);
				break;
			}
		}
	};
	protected LoadData mLoadData;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		super.onCreate(savedInstanceState); 
		COLSEABLE.add(this);
		app  = MyApplication.getInstance();
		mLoadData = app.getLoadData(); 
		db = app.getDb();
		shareHelper = app.getShareHelper() ;		
		mScreenManager = app.getScreenManager();
		mScreenManager.pushActivity(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		context = this;
		//公用的menu头
		setMenu();
	}
	protected void setMenu(){
		//head
		common_title = (TextView) findViewById(R.id.common_title);
		title_place = (TextView) findViewById(R.id.title_place);
		title_goods = (Button) findViewById(R.id.title_goods);
		common_back =  (Button) findViewById(R.id.common_back);
		common_main =  (Button) findViewById(R.id.common_main);
		common_recommand = (Button) findViewById(R.id.common_recommand);
		//footer
		footer_refresh = (TextView) findViewById(R.id.footer_refresh);
		footer_history = (TextView) findViewById(R.id.footer_history);
		footer_center = (TextView) findViewById(R.id.footer_center);
		footer_update = (TextView) findViewById(R.id.footer_update);
		footer_location = (TextView) findViewById(R.id.footer_location);
		footer_ts = (TextView) findViewById(R.id.footer_ts);
		footer_cancle = (TextView) findViewById(R.id.footer_cancle);
		footer_delete = (TextView) findViewById(R.id.footer_delete);
		footer_goods_today = (TextView) findViewById(R.id.footer_goods_today);
		footer_goods_history = (TextView) findViewById(R.id.footer_goods_history);
		footer_szcw = (TextView) findViewById(R.id.footer_goods_today);
		footer_bc = (TextView) findViewById(R.id.footer_goods_history);
		footer_delmsg = (TextView) findViewById(R.id.footer_delmsg);
	}
	public void commonClick(View v){
		switch (v.getId()) {
		//刷新
		case R.id.footer_refresh:
			initData();
			break;
		//历史货源
		case R.id.footer_history:
			startActivity(new Intent(context, GoodsHistoryActivity.class));	
			break;
		//发布货源
		case R.id.footer_center:
			doFooterCenter();
			break;
		//返回
		case R.id.common_back:
			finish();
			break;
		//切换地区
		case R.id.title_place:
			picking = title_place;
			isTitle = true;
			popProvincePicker();
			break;
		//导航
		case R.id.common_main:
			startActivity(new Intent(context, MainActivity.class));
			break;
		//修改
		case R.id.footer_update:
			updateGoods();
			break;
		//推荐
		case R.id.common_recommand:
			startActivity(new Intent(context, RecommendActivity.class));
			break;
		//定位
		case R.id.footer_location:
			locationDriver();
			break;
		//投诉
		case R.id.footer_ts:
			tousu();
			break;
		//清空
		case R.id.footer_delete:
			goodsDelete();
			break;
		//取消
		case R.id.footer_cancle:
			finish();
			break;
		//今日货源
		case R.id.footer_goods_today:
			startActivity(new Intent(context, GoodsTodayListActivity.class));
			break;
			//历史货源
		case R.id.footer_goods_history:
			startActivity(new Intent(context, GoodsHistoryActivity.class));
			break;
		//谁在查我
		case R.id.footer_szcw:
			startActivity( new Intent(context,ShuizaiChaWoActivity.class));
		//报错
		case R.id.footer_bc:
			Intent itx = new Intent(context, CreditCorrectActivity.class);
			itx.putExtra("number", shareHelper.getUsername());
			startActivity(itx);
			break;
		//网上货源
		case R.id.title_goods:
			Intent gIntent = new Intent(context, GoodsPeerListActivity.class);
			gIntent.putExtra("CITYCODE", app.getLocationCode());
			gIntent.putExtra("isMain", true);
			startActivity(gIntent);
			break;
		//删除所有消息
		case R.id.footer_delmsg:
			doDelAll();
			break;
		}
	}
	private void doDelAll() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("删除全部消息记录").setMessage("确定删除?(永久删除)")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.clearMessageList();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}
	//删除货物
	protected void goodsDelete() {
		
	}
	//投诉
	protected void tousu() {
		
	}
	//定位
	protected void locationDriver() {
		
	}
	//修改货源
	protected void updateGoods() {
		
	}
	//底部栏操作
	protected void doFooterCenter() {
		
	}
	//初始化数据（刷新用）
	protected  void initData(){
		
	}
	//公用弹出选择组件
	protected void popProvincePicker(){
		showing = DialogUtil.showProvinceDialog(this);
	}
	protected void popGsTypePicker(){
		showing = DialogUtil.showGoodsTypeDialog(this);
	}
	protected void popGoodsDwPicker(){
		showing = DialogUtil.showGoodsDwDialog(this);
	}
	
	protected void popTkTypePicker(){
		showing = DialogUtil.showTruckTypeDialog(this);
	}
	protected void popTkLengPicker(){
		showing = DialogUtil.showTruckLengthDialog(this);
	}
	protected void popGsLoadWayPicker(){
		showing = DialogUtil.showGsLoadWayDialog(this);
	}
	protected void popPayWayPicker(){
		showing = DialogUtil.showPayWayDialog(this);
	}
	

	public void hideKeyboard(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public   void showKeyboard(View view) {
		imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
	}
	
	@Override   
	public void onBackPressed() {   
	       super.onBackPressed();   
	     
	  }  
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mScreenManager)   mScreenManager.popActivity(this);
	    TransportLog.i("popActivity", "执行退出==");
		if (!isDestroy){
	    	return;	    
	    }
		app.setSessionId("");
		app.cancelAlarm();
		if (null != mLoadData) mLoadData.stop();
		if (null != db) db.closeDB();
		shareHelper.setLogin(false);
		//貌似只能关闭当前页面
//	    android.os.Process.killProcess(android.os.Process.myPid());
		//使用任务管理器关闭自己的应用
//		ActivityManager activityMgr= (ActivityManager) context.getSystemService(ACTIVITY_SERVICE );
//		activityMgr.restartPackage(getPackageName()); 
		//或者像抢好货一样，关闭所有activity（一个set）
		closeAll();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void finish() {
		super.finish(); 
	}
	
	protected void initProgress(int res) {
		// TODO Auto-generated method stub
		if (null ==mDialog ){
			mDialog = new ProgressDialog(this);
			mDialog.setMessage(	getString(res));
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(true);
		}
		mDialog.show();
	}
	/**
	 * 公用组件：进度条
	 */
	protected void initProgress(String  msg) {
		// TODO Auto-generated method stub	 
		if (null ==mDialog ){
			mDialog = new ProgressDialog(this);
			mDialog.setMessage(	msg);
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(true);
		}
		mDialog.show();
	}
	/**
	 * 公用组件：关闭进度条
	 */
	protected void hindProgress(){
		if (null !=mDialog )	mDialog.dismiss();		
	}
	
	/**
	 * 抽象方法，给各个activity实现，去做自己的操作
	 * @param msg
	 */
	protected abstract void ioHandler(Message msg) ; 
	/**
	 * //处理异常
	 * @param msg 
	 */
	protected  void doError(Message msg){
		String info = (String)msg.obj;
		if(Common.isEmpty(info))
			return;
		//普通异常
		showToast(info);
	}; 

	protected  void doCmssRequest(Message msg){
		Uri uri = Uri.parse((String)msg.obj);
		Intent it = new Intent();
		it.setAction(Intent.ACTION_VIEW);
		it.setData(uri);
		context.startActivity(it);

	}; 
	
	/**
	 * 公用组件： Toast
	 * @param message
	 */
	public void showToast(final String message){
		//runOnUiThread是activity的方法，内部是handler。如果当前线程不是ui线程则加入队列，是的话，则会立即执行
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(null == mToast){
					mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
//					mToast.setGravity(Gravity.CENTER, 0, 0);
//					mToast.setView(mToast.getView());
				}
				mToast.setText(message);
				mToast.show();
			}
		});
	}
	
	/**
	 * 跳转到诚信详情页
	 * @param number
	 */
	protected void gotoCredit(String number){
//		Intent intent = new Intent(this, CreditResultActivity.class);
//		intent.putExtra("number", number);
//		startActivity(intent);
	}
	
	protected void gotoMain(){
		mScreenManager.popAllActivityExceptOne(MainActivity.class); 
	}
	/**
	 * 执行退出操作
	 */
	protected void quit(){
		//退出登录
//		logout();
		//关闭所有activity
//		closeAll();
		MyApplication.getInstance().onTerminate();
	}
	private void closeAll(){
		for(Activity close : COLSEABLE){
			((CloseAble) close).close();
		}
		COLSEABLE.clear();
	}
	@Override
	public void close() {
		finish();
	}
	public static void showTradeDialog(final Long gsid, String info){
//		if(context.getClass().getName().equals(GoodsMyDetailActivtiy.class.getName())){
//			//已经在成交订单页面，不再弹窗
//			return;
//		}
//		Dialog dialog = new Dialog(context);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(R.layout.dialog_trade);
//		TextView infoView = (TextView) dialog.findViewById(R.id.goods_trade_info);
//		infoView.setText(info);
//		Button button = (Button) dialog.findViewById(R.id.goods_trade);
//		button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				ShareHelper.getInstance(context).setCheckDeal(true);
//				Intent intent = new Intent(context, GoodsMyDetailActivtiy.class);
//				intent.putExtra("GSID", gsid);
//				context.startActivity(intent);
//				((BaseActivity) context).finish();
//			}
//		});
//		dialog.setOnCancelListener(new OnCancelListener() {
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				dialog.cancel();
//			}
//		});
//		dialog.show();
	}
	
}

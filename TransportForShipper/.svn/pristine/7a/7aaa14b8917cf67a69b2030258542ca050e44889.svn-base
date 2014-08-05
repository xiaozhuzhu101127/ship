package com.topjet.shipper.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.topjet.shipper.R;
import com.topjet.shipper.MyApplication;
import com.topjet.shipper.model.PhoneInfo;
import com.topjet.shipper.util.DisplayUtil;
/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-4 下午4:54:01  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class CallingInfoDialog 
extends Dialog {

	/**
	 * 鼠标触摸开始位置
	 */
	private float mTouchStartX = 0;
	/**
	 * 鼠标触摸结束位置
	 */
	private float mTouchStartY = 0;

	public static int TOOL_BAR_HIGH = 0;
	/**
	 * 要显示在窗口最前面的对象
	 */
	private View mView = null;
	private Context mContext;

	public CallingInfoDialog(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public CallingInfoDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mView = inflater.inflate(R.layout.dialog_calling, null);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 
		WindowManager.LayoutParams wmap = getWindow().getAttributes();
		wmap.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL 
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		wmap.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT 
				| WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
		wmap.gravity = Gravity.LEFT 
				| Gravity.TOP;
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		this.setContentView(mView);
		getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, 173);
		mView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				onTouchEvent(event, view);
				return true;
			}
		});
	}

	/**
	 * 要显示在窗口最前面的方法
	 * 
	 * @param context
	 *            调用对象Context getApplicationContext()
	 * @param window
	 *            调用对象 Window getWindow()
	 * @param floatingViewObj
	 *            要显示的浮动对象 View
	 */
	public void show() {
		// 以屏幕左上角为原点，设置x、y初始值?
		WindowManager.LayoutParams wmlp = getWindow().getAttributes();
		wmlp.gravity = Gravity.TOP | Gravity.LEFT;
		wmlp.x = (int) MyApplication.getInstance().getShareHelper().getCallingDialogX(0); // x position
		wmlp.y = (int) MyApplication.getInstance().getShareHelper().getCallingDialogY(200); // y position
		super.show();
	}

	/**
	 * 跟谁滑动移动
	 * 
	 * @param event
	 *            事件对象
	 * @param view
	 *            弹出对象实例（View）
	 * @return
	 */
	public boolean onTouchEvent(MotionEvent event, View view) {
		// 获取相对屏幕的坐标，即以屏幕左上角为原点
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchStartX = event.getX();
			mTouchStartY = event.getY() + 30;
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			float newX = event.getRawX();
			float newY = event.getRawY();
			float x = newX - mTouchStartX;
			float y = newY - mTouchStartY;
			Log.d(mTouchStartX + " " + mTouchStartY, x + " " + y);
			updateViewPosition(x, y);
			break;

		}
		return true;
	}

	/**
	 * 更新弹出窗口位置
	 */
	private void updateViewPosition(float x, float y) {
		WindowManager.LayoutParams wmlp = getWindow().getAttributes();
		// 更新浮动窗口位置参数
		wmlp.x = (int) x;
		wmlp.y = (int) y;
		getWindow().setAttributes(wmlp);
		MyApplication.getInstance().getShareHelper().setCallingDialogX(wmlp.x);
		MyApplication.getInstance().getShareHelper().setCallingDialogY(wmlp.y);
	}

	public void renderCallingInfo(PhoneInfo info) {
		//认证状态图标
		if(info.isVerified()){
			mView.findViewById(R.id.calling_info_verified).setVisibility(View.VISIBLE);
			mView.findViewById(R.id.calling_info_unverified).setVisibility(View.GONE);
		}else{
			mView.findViewById(R.id.calling_info_verified).setVisibility(View.GONE);
			mView.findViewById(R.id.calling_info_unverified).setVisibility(View.VISIBLE);
		}
		//公司名称
		DisplayUtil.hideOnBlank((TextView) mView.findViewById(R.id.calling_info_company), info.getCompanyName());
		//归属地、姓名
		((TextView) mView.findViewById(R.id.calling_info_location_name)).setText(info.getLocation() + " " + info.getName());
		((TextView) mView.findViewById(R.id.calling_info_credit_complaint)).setText("诚信" + info.getCredit() + ", 被诉" + info.getComplaint() + "次");
	}
}

package com.topjet.shipper.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.topjet.shipper.activity.AboutActivity;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;

 

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-5 下午4:48:16  
 * Description:更多应用
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class MoreActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_more);
		super.onCreate(savedInstanceState);
		common_title.setText("更多应用");
		common_recommand.setVisibility(View.VISIBLE);
		super.onCreate(savedInstanceState);	
	}

	public void click(View v) {
		switch (v.getId()) {
		//关于软件
		case R.id.about:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		//常见问题
		case R.id.wt:
			startActivity(new Intent(this, FaqActivity.class));
			break;
		//推荐给朋友
		case R.id.tj:
			startActivity(new Intent(this, RecommendActivity.class));
			break;
		//完善资料
		case R.id.zl:
			startActivity(new Intent(this, CompleteCategoryActivity.class));
			break;
		//营销代表专用
		case R.id.zy:
			startActivity(new Intent(this, SalesmanActivity.class));
			break;
		//账户余额
		case R.id.my560:
			startActivity(new Intent(this, BalanceActivity.class));
			break;
		//修改密码
		case R.id.updatepsd:
			startActivity(new Intent(this, PasswordActivity.class));
			break;
		//修改密码
		case R.id.contactus:
			startActivity(new Intent(this, ContactUsActivity.class));
			break;
		//退出当前账号
		case R.id.btn_setting_logout:
			logout();
			break;
		}
	}
	 
	private void logout() {
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("退出").setMessage("确定退出?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//不经过服务端了，服务端有问题。
//						doQuit();
						isDestroy = true;
						finish();
					}

				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}
	
	private void doQuit() {
		// TODO Auto-generated method stub
	 
		this.mLoadData.loginOut(baseHandler, 
				DefaultConst.CMD_LOGIN_OUT, 
				app.getMemberID()+"");

	}
	@Override
	protected void ioHandler(Message msg) {
		if (msg.what ==DefaultConst.CMD_LOGIN_OUT ){
			isDestroy = true;
			finish();
		}
	}

	@Override
	protected void doError(Message msg) {
		if (msg.what ==DefaultConst.CMD_LOGIN_OUT ){
			isDestroy = true;
			finish();
		}
	}
	
}

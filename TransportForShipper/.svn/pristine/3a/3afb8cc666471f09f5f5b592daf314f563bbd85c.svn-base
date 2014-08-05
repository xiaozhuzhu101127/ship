package com.topjet.shipper.activity;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message; 
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;


import com.topjet.shipper.R;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.location.LocationManager;
import com.topjet.shipper.model.Identity;
import com.topjet.shipper.model.UserInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.PhoneValidator;
import com.topjet.shipper.widget.CountDownButton;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-12 下午2:33:29  
 * Description:登录
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class LoginActivity 
extends BaseActivity 
implements OnClickListener {
	private final static int USER_LICENCE_CONFIRM = 0;
 
	private CountDownButton getPasswordView;
	private EditText accountView;
	private EditText passwordView;
 
	private String mUsername;
	private String mPassword; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);	
		initView();
		initData();	 
	}

	public void initData() {
		if (!Common.checkNet(this)) {
			setNetWorkStatus();
			return;
		}
		LocationManager mLocationManager = new LocationManager(this);
		mLocationManager.requestLocation();
		//TODO:要想登录就检测版本 可以不注释掉
//		UpgradeManager.getInstance(this).checkVersion(false);
	}

	private void initView() {
		
		getPasswordView = (CountDownButton)findViewById(R.id.get_password);
		accountView = (EditText) findViewById(R.id.login_account);
		passwordView = (EditText) findViewById(R.id.login_password);
		
		String account = shareHelper.getUsername();
		String password = shareHelper.getPassword();
		if (!account.equals("") && !password.equals("")) {
			accountView.setText(account);
			passwordView.setText(password);
		}
		//取手机号码，取到了就不能填
		String mobile=Common.getMobile();
		
		if(!Common.isEmpty(mobile)){
			//去掉-86
			while(!mobile.startsWith("1")&&mobile.length()>0){
				mobile=mobile.substring(1);
			}
			if(mobile.length()==11){
				accountView.setText(mobile);
				accountView.setEnabled(false);
				passwordView.requestFocus();
				account=mobile;
			}
		}
		
		if ("".equals(account)) {
			// 空的时候将本机号码填上
			account = Common.getMobile();
			if (null != account) {
				if (account.startsWith("+86")) {
					account = account.substring(3);
				}
				accountView.setText(account);
			}
		}
	}

	private void goMain() {
	
		mScreenManager.popActivity(this);
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void doLogin() {
		mUsername = accountView.getText().toString().trim();
		if (Common.isEmpty(mUsername)) {
			Common.showToast("用户名不能为空！", this);
			accountView.requestFocus();
			return;
		}
		mPassword = passwordView.getText().toString().trim();
		if (Common.isEmpty(mPassword)) {
			Common.showToast("密码不能为空！", this);		 
			passwordView.requestFocus();
			return;
		}
		initProgress("正在提交数据，请稍后");
		hideKeyboard(passwordView);
		shareHelper.setSession("");
		mLoadData.login(baseHandler, DefaultConst.CMD_LOGIN, mUsername, mPassword);
	}

	private void doGetPassword() {
		String account = accountView.getText().toString().trim();
		if ("".equals(account)) {
			Common.showToast("请先在\"联系号码\"处输入手机号码!",this);
			return;
		}
		if (!PhoneValidator.validateMobile(account)) {
			Common.showToast("手机号码格式不正确!",this);
			return;
		}
		showLicence();
	}

	/**
	 *  发送网络请求，取回密码
	 */
	private void _doGetPassword() {
		String account = accountView.getText().toString().trim();
		mLoadData.mobileResetPwd(baseHandler, DefaultConst.CMD_MOBILE_RESET_PWD, account);
		getPasswordView.countDown();	 
	}

	private void showLicence() {
		Intent intent = new Intent(LoginActivity.this, UserLicenceActivity.class);
		startActivityForResult(intent, USER_LICENCE_CONFIRM);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_submit:
			doLogin();
			break;
		case R.id.get_password:
			doGetPassword();	
			break;
		case R.id.kefu4000:
			startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + DefaultConst.KEFU)));	
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 不弹出OptionsMenu
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		// 用户协议
		if (requestCode == USER_LICENCE_CONFIRM) {
			boolean result = data.getBooleanExtra("result", false);
			if (result) {
				_doGetPassword();
			}
			return;
		}
	}

	private void setNetWorkStatus() {
		Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络").setMessage("是否对网络进行设置？");
		AlertDialog dialog = b.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent i = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//				if (Build.VERSION.SDK_INT < 16) {
//					i.setClassName("com.android.phone", "com.android.phone.Settings");
//				}
				// Start activity and catch potential exception
				try {
					startActivity(i);
				} catch (ActivityNotFoundException e) {
					Common.showToast("找不到网络设置界面，请手动打开网络设置！",LoginActivity.this);
				}
				//如果是于有网和无网的情况下，需要做异常处理 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).setNeutralButton("否", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
				finish();
			}
		}).create();
		dialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					finish();
				}
				return true;
			}
		});
		dialog.show();
	}

	@Override
	protected void ioHandler(Message msg) {
		switch(msg.what){
		case DefaultConst.CMD_MOBILE_RESET_PWD:	Common.showToast("发送成功, 密码会以短信形式发送给您!",this);break;
		case DefaultConst.CMD_LOGIN:doLoginSucc(msg);break; 
		}
	}

 

	private void doLoginSucc(Message msg) {
		shareHelper.setUsername(mUsername);
//		if (remembermeView.isChecked()) {		
//			shareHelper.setPassword(mPassword);			
//		} else {		 
//			shareHelper.setPassword("");
//		}
		JSONObject data = (JSONObject) msg.obj;
		UserInfo userInfo = new UserInfo();
		app.setMemberID(data.optLong("userID")); 
		shareHelper.setUserId(data.optLong("userID"));
		userInfo.mobile = mUsername;
		userInfo.realName = data.optString("realName"); 
		userInfo.companyName =  data.optString("companyName"); 
		userInfo.address = data.optString("busiAddress");
		userInfo.service = data.optString("service");
		userInfo.photo = data.optString("idCardPhoto");
		app.setUserInfo(userInfo);		
		Identity identity = Identity.getIdentity(data.optString("userType"));
		if(identity!=null){
			shareHelper.setUserType(identity.getDescription());
		}


	
		JSONObject trustInfo = (JSONObject)data.optJSONObject("trustInfo");
			if(trustInfo!=null){
				shareHelper.setYslAccount(trustInfo.optLong("credit"));
				shareHelper.setLocalTrustLevel(trustInfo.optLong("localTrustLevel"));
			}
	
		shareHelper.setRealName(data.optString("realName"));
		shareHelper.setLocale(data.optString("place"));		 
		shareHelper.setLocaleName(data.optString("placeName"));
		shareHelper.setRecommendMobile(data.optString("recommendMobile"));
		shareHelper.setUploaded(data.optInt("isrecommend") == 1);
		shareHelper.setBase64Photo(data.optString("idCardPhoto"));
		shareHelper.setLogin(true);
		shareHelper.setDct_ut(data.optString("userType"));
		shareHelper.setUsername(mUsername);
		shareHelper.setPassword(mPassword);
		if("1".equals(data.optString("isCert"))){
			shareHelper.setCert(true);
		}else {
			shareHelper.setCert(false);
		}
		goMain();
	}
 
	@Override
	protected void doError(Message msg) {
		Common.showToast((String)msg.obj,this);
	}

	/*private void doLoginFailed(Message msg) {
		Common.showToast((String)msg.obj,this);
	}

	private void doMobileResetPwdErr(Message msg) {
		getPasswordView.reset();
		String err = (String)msg.obj;
		if (Common.isEmpty(err)) Common.showToast("发送失败",this);		
		else Common.showToast("发送失败：" + err,this);	 

	}*/

}

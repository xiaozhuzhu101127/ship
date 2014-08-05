package com.topjet.shipper.listener;

import org.json.JSONObject;
import com.topjet.shipper.R;
import com.topjet.shipper.MyApplication;
import com.topjet.shipper.dialog.CallingInfoDialog;
import com.topjet.shipper.model.PhoneInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.NetUtils;
import com.topjet.shipper.util.PhoneValidator;
import com.topjet.shipper.util.TransportLog;
import com.topjet.shipper.util.NetUtils.NetType;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-6-14 下午4:08:05  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class InCallPhoneStateListener extends PhoneStateListener {
	/**
	 * 来电结束
	 */
	private boolean canceled = false;
	private String inComing = "";
	// private boolean isFailed = false;

	private CallingInfoDialog mDialog;

	public InCallPhoneStateListener(final Context context) {
		super();
		mDialog = new CallingInfoDialog(context, R.style.Theme_TransparentNoTitleBar);
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			Log.d("DEBUG", "IDLE " + incomingNumber);
			canceled = true;
			mDialog.hide();
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Log.d("DEBUG", "OFFHOOK " + incomingNumber);
			canceled = true;
			mDialog.hide();
			// if (isFailed){
			// queryCallingInfo(incomingNumber);
			// isFailed = !isFailed;
			// }
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			inComing = incomingNumber;
			// 如果软件开启了，但是状态丢失了，也应该可以获取相关的信息，需要检查为什么用户的状态会丢失
			if (null != MyApplication.getInstance()) {
				TransportLog.i("InCallPhoneStateListener", "MyApplication instance is exist");
				if (null != MyApplication.getInstance().getUserInfo()) {
					TransportLog.i("InCallPhoneStateListener", "MyApplication userInfo is exist");
//					if (Common.isEmpty(MyApplication.getInstance().getUserInfo().mobile)){
//						TransportLog.i("wei kong ......................................,","wei kong");
//						return;
//					}
					
				} else {
					TransportLog.i("InCallPhoneStateListener", "MyApplication userInfo is not exist");
					
				}
			} else {
				TransportLog.i("InCallPhoneStateListener", "MyApplication instance is not exist");
				return;
			}

			TransportLog.i("InCallPhoneStateListener", "RINGING " + incomingNumber);
			canceled = false;
			queryCallingInfo(incomingNumber);
		  //结束之后，需要进行重新注册
			
			break;
		}
	}

	private void showCallingDialog(PhoneInfo info) {
		if (!canceled) {
			mDialog.renderCallingInfo(info);
			mDialog.show();
		}
	}

	private void queryCallingInfo(String incomingNumber) {
		if (!PhoneValidator.validatePhoneOrMobile(incomingNumber) && !PhoneValidator.validPhone400(incomingNumber)) {
			// 不符合条件的号码不查
			return;
		}
		/*
		 * if (!isFailed){
		 * 
		 * try { PhoneInfo info = Database.getInstance(TransportApplication
		 * .getInstance()
		 * .getApplicationContext()).queryPhoneInfo(incomingNumber); if (null !=
		 * info){ showCallingDialog(info); return; } } catch (Exception e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * }
		 */
		if (NetType.NET_2G.equals(NetUtils.getNetType()))
			return;

		MyApplication.getInstance().getLoadData().callPhoneInfoByPhoneNumber(new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case DefaultConst.CMD_CALL_PHONE_INFO:
					doCallPhoneInfo(msg);
					break;
				case DefaultConst.CMD_ERROR_NET_ADD_REQUEST:
				case DefaultConst.CMD_ERROR_NET_REQUEST_ERROR:
				case DefaultConst.CMD_ERROR_NET_RETURN_FAILED:
					// isFailed = true;
					TransportLog.i("InCallPhoneStateListener", "===============msg->error");

					break;
				}
			}

		}, DefaultConst.CMD_CALL_PHONE_INFO, incomingNumber);

	}

	private void doCallPhoneInfo(Message msg) {
		// TODO Auto-generated method stub
		TransportLog.i("InCallPhoneStateListener", "msg-> " + (JSONObject) msg.obj);

		if (!canceled) {
			PhoneInfo info = readPhoneInfo((JSONObject) msg.obj, inComing);
			// 是处理正常来电提醒
			showCallingDialog(info);
			// 查询过的记录保存到本地
			/*
			 * try { Database.getInstance(TransportApplication .getInstance()
			 * .getApplicationContext()) .saveOrUpdatePhoneInfo(info);
			 * TransportLog.i("InCallPhoneStateListener",
			 * "input database OK==========");
			 * 
			 * } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}
	}

	private PhoneInfo readPhoneInfo(JSONObject data, String number) {
		JSONObject contactInfo = data.optJSONObject("ContactInfo");
		String location = contactInfo.optString("placeName");
		boolean verified = contactInfo.optInt("isCert", 0) == 1;
		String companyName = contactInfo.optString("companyName");
		String name = contactInfo.optString("realName");
		JSONObject trustInfo = contactInfo.optJSONObject("trustInfo");
		int credit = trustInfo.optInt("credit");
		int complaint = trustInfo.optInt("complaintNumber");
		return new PhoneInfo(number, verified, companyName, location, name, credit, complaint);
	}

}

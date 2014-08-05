package com.topjet.shipper.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.topjet.shipper.activity.CompleteActivity;
import com.topjet.shipper.activity.MainActivity;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.dialog.CommonDialog;
import com.topjet.shipper.model.Identity;
import com.topjet.shipper.model.UserInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.FileUtils;
import com.topjet.shipper.util.IdentityCardValidator;
import com.topjet.shipper.util.PlateNoValidator;
import com.topjet.shipper.util.Receivable;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-30 下午3:01:36  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

@SuppressLint("NewApi")
public class CompleteActivity extends BaseActivity implements OnClickListener, Receivable {

	private static final int TYPE_PROVINCE = 0;
	private static final int TYPE_CITY = 1;
	private static final int TYPE_TRUCK_TYPE = 2;
	private static final int TYPE_TRUCK_LENGTH = 3;

	CommonDialog mCityDialog;
	CommonDialog mProvinceDialog;
	CommonDialog mTruckTypeDialog;
	CommonDialog mTruckLengthDialog;

	EditText mIdcView;
	EditText mNameView;
	EditText mCompanyView;
	EditText mAddressView;
	Spinner mPlateRegionView;
	Spinner mPlateLetterView;
	EditText mPlateOrderView;
	TextView mTruckTypeView;
	TextView mTruckLengthView;

	TextView mTarget1View;
	TextView mTarget2View;
	TextView mTarget3View;
	TextView mTarget4View;

	Identity mIdentity = Identity.Driver;

	TextView mDialogFor;
	LinearLayout complete_for_driver;
	RelativeLayout searchViolate;
	
	ImageView violatePicture;
	Bitmap map;
	

	private Dialog mDialog;
	
	private TextView szcwView;
	
	private TextView bcView;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_complete);
		super.onCreate(savedInstanceState);

		common_title.setText("完善资料");
		common_main.setVisibility(View.VISIBLE);
		Bundle extras = savedInstanceState;
		if (savedInstanceState == null) {
			extras = getIntent().getExtras();
		}
		if (null != extras) {
			mIdentity = (Identity) extras.get("identity");
		}
		((TextView) findViewById(R.id.complete_identity)).setText(mIdentity.getDescription());

		complete_for_driver = (LinearLayout) findViewById(R.id.complete_for_driver);

		mIdcView = (EditText) findViewById(R.id.complete_idc);
		mNameView = (EditText) findViewById(R.id.complete_name);
		mCompanyView = (EditText) findViewById(R.id.complete_company);
		mAddressView = (EditText) findViewById(R.id.complete_address);
		violatePicture = (ImageView) findViewById(R.id.violatePicture);
		searchViolate = (RelativeLayout) findViewById(R.id.searchViolate);
		szcwView = (TextView) findViewById(R.id.complete_szcw);
		bcView = (TextView) findViewById(R.id.complete_bc);
		if(!shareHelper.isCert()){
			szcwView.setVisibility(View.INVISIBLE);
			bcView.setVisibility(View.INVISIBLE);
			mIdcView.setEnabled(true);
			mIdcView.setBackground(null);
			mNameView.setEnabled(true);
			mNameView.setBackground(null);
			mCompanyView.setEnabled(true);
			mCompanyView.setBackground(null);
			mAddressView.setEnabled(true);
			mAddressView.setBackground(null);
		}
		// 车主显示专用表单
		if (mIdentity.equals(Identity.Driver)) {
			findViewById(R.id.complete_for_driver).setVisibility(View.VISIBLE);
			mPlateRegionView = (Spinner) findViewById(R.id.complete_plate_region_select);
			mPlateLetterView = (Spinner) findViewById(R.id.complete_plate_letter_select);
			mPlateOrderView = (EditText) findViewById(R.id.complete_plate_order);
			mTruckTypeView = (TextView) findViewById(R.id.complete_truck_type);
			mTruckLengthView = (TextView) findViewById(R.id.complete_truck_length);
			mTarget1View = (TextView) findViewById(R.id.complete_target1);
			mTarget2View = (TextView) findViewById(R.id.complete_target2);
			mTarget3View = (TextView) findViewById(R.id.complete_target3);
			mTarget4View = (TextView) findViewById(R.id.complete_target4);
			mProvinceDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_PROVINCE,
					Dict.getProvinceDict());
			mProvinceDialog.setTvName("请选择省份");
			mCityDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_CITY);
			mCityDialog.setTvName("请选择城市");
			mTruckTypeDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_TRUCK_TYPE,
					Dict.getTruckTypeDict());
			mTruckTypeDialog.setTvName("请选择类型");
			mTruckLengthDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_TRUCK_LENGTH,
					Dict.getTruckLengthDict());
		} else
			complete_for_driver.setVisibility(View.GONE);

		fetchData();
	}

	private void renderData(JSONObject jsonData) {
		JSONObject data = null;
		try {
			data = jsonData.getJSONObject("MemberInfo");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		// getUserInfo可能为空，原因未知，待继续改进
		if (null != app.getUserInfo()) {
			app.getUserInfo().cardNo = data.optString("idno");
			app.getUserInfo().realName = data.optString("realName");
			app.getUserInfo().companyName = data.optString("companyName");
			app.getUserInfo().plate1 = data.optString("plate1");
			app.getUserInfo().plate2 = data.optString("plate2");
			app.getUserInfo().plate3 = data.optString("plate3");
			app.getUserInfo().dctTT = data.optString("dct_tt");
			app.getUserInfo().dctUt = data.optString("dct_tklen");
			app.getUserInfo().tkTarget1 = data.optString("tkTarget1");
			app.getUserInfo().tkTarget2 = data.optString("tkTarget2");
			app.getUserInfo().tkTarget3 = data.optString("tkTarget3");
			app.getUserInfo().tkTarget4 = data.optString("tkTarget4");
			app.getUserInfo().address=data.optString("address");
			mAddressView.setText(app.getUserInfo().address);
			mIdcView.setText(app.getUserInfo().cardNo);
			mNameView.setText(app.getUserInfo().realName);
			mCompanyView.setText(app.getUserInfo().companyName);
		}
		if (mIdentity.equals(Identity.Driver)) {
			String plateRegion = data.optString("plate1");
			if (!Common.isEmpty(plateRegion)) {
				SpinnerAdapter plateRegionAdapter = mPlateRegionView.getAdapter();
				for (int i = 0; i < plateRegionAdapter.getCount(); i++) {
					if (plateRegion.equals(plateRegionAdapter.getItem(i))) {
						mPlateRegionView.setSelection(i);
					}
				}
			}
			String plateLetter = data.optString("plate2");
			if (!Common.isEmpty(plateLetter)) {
				SpinnerAdapter plateLetterAdapter = mPlateLetterView.getAdapter();
				for (int i = 0; i < plateLetterAdapter.getCount(); i++) {
					if (plateLetter.equals(plateLetterAdapter.getItem(i))) {
						mPlateLetterView.setSelection(i);
					}
				}
			}
			String plateOrder = data.optString("plate3");
			if (!Common.isEmpty(plateOrder)) {
				mPlateOrderView.setText(plateOrder);
			}
			String truckType = data.optString("dct_tt");
			if (!Common.isEmpty(truckType)) {
				mTruckTypeView.setTag(truckType);
				mTruckTypeView.setText(Dict.getTruckTypeDict().get(truckType));
			}
			String truckLength = data.optString("dct_tklen");
			if (!Common.isEmpty(truckLength)) {
				mTruckLengthView.setTag(truckLength);
				mTruckLengthView.setText(Dict.getTruckLengthDict().get(truckLength));
			}
			String target1 = data.optString("tkTarget1");
			String target2 = data.optString("tkTarget2");
			String target3 = data.optString("tkTarget3");
			String target4 = data.optString("tkTarget4");
			if (!Common.isEmpty(target1)) {
				mTarget1View.setTag(target1);
				mTarget1View.setText(Dict.getCityName(target1));
			}
			if (!Common.isEmpty(target2)) {
				mTarget2View.setTag(target2);
				mTarget2View.setText(Dict.getCityName(target2));
			}
			if (!Common.isEmpty(target3)) {
				mTarget3View.setTag(target3);
				mTarget3View.setText(Dict.getCityName(target3));
			}
			if (!Common.isEmpty(target4)) {
				mTarget4View.setTag(target4);
				mTarget4View.setText(Dict.getCityName(target4));
			}
		}
	}

	private void fetchData() {

		this.initProgress("加载中......");
		this.mLoadData.getMobileMemberInfoById(baseHandler, DefaultConst.CMD_GET_MOBILE_MEMBER_INFO_BY_ID);
	}

	private void submit() {
		String idc = mIdcView.getText().toString().trim();

		if (!Common.isEmpty(idc) && !IdentityCardValidator.validateCard(idc)) {
			Common.showToast("身份证号码格式不正确！", this);
			mIdcView.requestFocus();
			return;
		}
		String name = mNameView.getText().toString().trim();
		if (name.length() > 20) {
			Common.showToast("名字过长！", this);
			mNameView.requestFocus();
			return;
		}
		String company = mCompanyView.getText().toString().trim();
		if (company.length() > 50) {
			Common.showToast("公司名称过长！", this);
			mCompanyView.requestFocus();
			return;
		}
		String address = mAddressView.getText().toString().trim();
		if (address.length() > 50) {
			Common.showToast("经营地址过长！", this);
			mAddressView.requestFocus();
			return;
		}
//		if(null == violatePicture.getDrawable()){
//			showToast("请上传行驶证照片！");
//			return;
//		}

		String plateRegion = null;
		String plateLetter = null;
		String plateOrder = null;
		String truckType = null;
		String truckLength = null;
		String target1 = null;
		String target2 = null;
		String target3 = null;
		String target4 = null;
		String drivePicture = null;
		if (mIdentity.equals(Identity.Driver)) {
			plateRegion = mPlateRegionView.getSelectedItem().toString();
			plateLetter = mPlateLetterView.getSelectedItem().toString();
			plateOrder = mPlateOrderView.getText().toString().trim();
			if (!Common.isEmpty(plateOrder) && !PlateNoValidator.validateOrder(plateOrder)) {
				Common.showToast("车牌号码格式不正确！", this);
				mPlateOrderView.requestFocus();
				return;
			}
			truckType = (String) mTruckTypeView.getTag();
			truckLength = (String) mTruckLengthView.getTag();
			target1 = (String) mTarget1View.getTag();
			target2 = (String) mTarget2View.getTag();
			target3 = (String) mTarget3View.getTag();
			target4 = (String) mTarget4View.getTag();
			if(null != map)
				drivePicture =Base64.encodeToString(FileUtils.Bitmap2Bytes(map),Base64.DEFAULT);
		}

		this.initProgress("正在提交数据，请稍后");
		UserInfo userInfo = new UserInfo();
		userInfo.cardNo = idc;
		userInfo.realName = name;
		userInfo.dctUt = mIdentity.getCode();
		userInfo.companyName = company;
		userInfo.address=address;
		if(null != drivePicture){
			userInfo.drivePicture = drivePicture;
			shareHelper.setisDriverPic(true);
		}
		if (mIdentity.equals(Identity.Driver)) {
			userInfo.plate1 = plateRegion;
			userInfo.plate2 = plateLetter;
			userInfo.plate3 = plateOrder;

			userInfo.dctTT = truckType;
			userInfo.dctTklen = truckLength;
			userInfo.tkTarget1 = target1;
			userInfo.tkTarget2 = target2;
			userInfo.tkTarget3 = target3;
			userInfo.tkTarget4 = target4;
		}
		this.mLoadData.updateMobileMemberInfo(baseHandler, DefaultConst.CMD_UP_MOBILE_MEMBER_INFO, userInfo);
	}

	@Override
	public void receive(int type, Object... datas) {
		switch (type) {
		case TYPE_CITY:
			mCityDialog.dismiss();
			if (Dict.BLANK.equals(datas[0])) {
				return;
			}
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			break;
		case TYPE_PROVINCE:
			mProvinceDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			// 省份不限
			if (Dict.BLANK.equals(datas[0])) {
				return;
			}
			mCityDialog.setData(Dict.getCityDict((String) datas[0]));
			mCityDialog.notifyDataSetChanged();
			mCityDialog.showDialog();
			break;
		case TYPE_TRUCK_TYPE:
			mTruckTypeDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			break;
		case TYPE_TRUCK_LENGTH:
			mTruckLengthDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			break;

		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.for_target1:
			mDialogFor = mTarget1View;
			mProvinceDialog.showDialog();
			break;
		case R.id.for_target2:
			mDialogFor = mTarget2View;
			mProvinceDialog.showDialog();
			break;
		case R.id.for_target3:
			mDialogFor = mTarget3View;
			mProvinceDialog.showDialog();
			break;
		case R.id.for_target4:
			mDialogFor = mTarget4View;
			mProvinceDialog.showDialog();
			break;
		case R.id.rl_complete_truck_type:
			mDialogFor = mTruckTypeView;
			mTruckTypeDialog.showDialog();
			break;
		case R.id.rl_complete_truck_length:
			mDialogFor = mTruckLengthView;
			mTruckLengthDialog.showDialog();
			break;
		case R.id.complete_submit:
			submit();
			break;
		//拍照
		case R.id.searchViolate:			
			String state = Environment.getExternalStorageState();  
			if (state.equals(Environment.MEDIA_MOUNTED)) {  
			    String saveDir = FileUtils.violateImagePath.toString();
			    File dir = new File(saveDir);  
			    if (!dir.exists()) {  
			        dir.mkdir();  
			    }  
			   File file = new File(saveDir, "violate.jpg");  
			    file.delete();  
			    if (!file.exists()) {  
			        try {  
			            file.createNewFile();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			            Toast.makeText(this,  
			                    "没有SDCARD",  
			                    Toast.LENGTH_LONG).show();  
			            return;  
			        }  
			    }  
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));  
			    startActivityForResult(intent, 1);  
			}else{
				 showToast("没有SDCARD");
			}
			break;
		//报错
		case R.id.complete_bc:
			Intent itx = new Intent(context, CreditCorrectActivity.class);
			itx.putExtra("number", shareHelper.getUsername());
			startActivity(itx);
			break;	
		//谁在查我
		case R.id.complete_szcw:
			startActivity( new Intent(context,ShuizaiChaWoActivity.class));
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();
			finish();
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();			
			Intent it = new Intent(CompleteActivity.this, MainActivity.class);		
			startActivity(it);
			break;	
		}
	}
	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == DefaultConst.CMD_GET_MOBILE_MEMBER_INFO_BY_ID) {
			renderData((JSONObject) msg.obj);
		} else if (msg.what == DefaultConst.CMD_UP_MOBILE_MEMBER_INFO) {
			//Common.showToast("保存成功！", this);
			if (null == mDialog) {
				mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.dialog_score_shop);
				TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);
				view.setText("资料已经更新！");
				TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
				text1.setText("确定");
				text1.setOnClickListener(this);
				TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
				text2.setText("返回");
				mDialog.findViewById(R.id.score_shop_dialog_cancel).setOnClickListener(this);
				mDialog.findViewById(R.id.score_shop_dialog_dial).setOnClickListener(this);
			}
			mDialog.show();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1){	
			//隐藏拍照文字
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					searchViolate.setVisibility(View.GONE);
				}
			});
			//显示图片
			map = FileUtils.getViolatePicture(this, FileUtils.violateImage);
			violatePicture.setImageBitmap(map);
		}
	}
}

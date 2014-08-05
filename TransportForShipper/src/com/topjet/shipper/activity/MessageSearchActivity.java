package com.topjet.shipper.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.dialog.CommonDialog;
import com.topjet.shipper.model.Identity;
import com.topjet.shipper.model.UserInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.IdentityCardValidator;
import com.topjet.shipper.util.PlateNoValidator;
import com.topjet.shipper.util.Receivable;

public class MessageSearchActivity extends BaseActivity implements OnClickListener,Receivable{
	
	
	private static final int TYPE_PROVINCE = 0;
	private static final int TYPE_CITY = 1;
	private static final int TYPE_TRUCK_TYPE = 2;
	private static final int TYPE_TRUCK_LENGTH = 3;
	
	private TextView tv_titleView;
	
	CommonDialog mCityDialog;
	CommonDialog mProvinceDialog;
	CommonDialog mTruckTypeDialog;
	CommonDialog mTruckLengthDialog;
	
	
	TextView mTruckTypeView;
	TextView mTruckLengthView;

	TextView mTarget1View;
	
	TextView mDialogFor;
	
	private String tk_length="";
	private String tk_type="";
	private String tk_city="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_search);
		
		mTruckTypeView = (TextView) findViewById(R.id.complete_truck_type);
		mTruckLengthView = (TextView) findViewById(R.id.complete_truck_length);
		mTarget1View = (TextView) findViewById(R.id.complete_target1);
		
		common_title.setText("搜索");
		
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
		mTruckLengthDialog.setTvName("请选择车长");
	}
	
	
	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.for_target1:
			mDialogFor = mTarget1View;
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
		}
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
			if(!"BX".equals(datas[0])){
				tk_city = (String) datas[1];
			}
			break;
		case TYPE_PROVINCE:
			mProvinceDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			// 省份不限
			if (Dict.BLANK.equals(datas[0])) {				
				return;
			}
			if(!"BX".equals(datas[0])){
				tk_city = (String) datas[1];
			}
			mCityDialog.setData(Dict.getCityDict((String) datas[0]));
			mCityDialog.notifyDataSetChanged();
			mCityDialog.showDialog();
			break;
		case TYPE_TRUCK_TYPE:
			mTruckTypeDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			tk_type=(String) datas[1];
			break;
		case TYPE_TRUCK_LENGTH:
			mTruckLengthDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			tk_length=(String) datas[1];
			break;

		}
	}
	
	
	
	
	private void submit() {
		Intent it = new Intent(this, MessageActivity.class);
		it.putExtra("tk_length",tk_length);
		it.putExtra("tk_type", tk_type);
		it.putExtra("tk_city", tk_city);
		startActivity(it);
		
	}
	

}

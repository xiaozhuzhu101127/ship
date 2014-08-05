package com.topjet.shipper.activity;
 
 
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.apkupdate.UpgradeManager;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-6 上午11:00:05  
 * Description:通讯录筛查
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class FilterActivity extends BaseActivity { 
	boolean mAuto = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_filter);
		super.onCreate(savedInstanceState);
		
		common_title.setText("通讯录筛查");
		common_main.setVisibility(View.VISIBLE);
		Bundle bundle = getIntent().getExtras(); 
		if (bundle != null) mAuto = bundle.getBoolean("auto");
 
 
		//从营销代表专用页面过来，自动筛查
		if(mAuto){
			filter();
		}
	}

	private void filter() {
		initProgress("正在进行通讯录筛查，请稍后......"); 
		
		this.mLoadData.upPhoneList(baseHandler, 
				DefaultConst.CMD_UP_PHONELIST);
	}

	

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.filter_btn:filter();break;
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		JSONObject json=(JSONObject)msg.obj;
		String content="";
		if(json!=null)
			content=json.optString("msg");
		//如果有http地址，那么说明是要更新的
		if(content!=null&&content.indexOf("http")>=0){
			UpgradeManager.getInstance(this).contactVersion(json);
			return ;
		}
		switch(msg.what){
		
		case DefaultConst.CMD_UP_PHONELIST:doUpPhoneList(content);break;
		}
	}

	private void doUpPhoneList(String msg) {
		
		
		// TODO Auto-generated method stub
		shareHelper.setUploaded(true);
		new AlertDialog
		.Builder(FilterActivity.this).
		setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("筛查成功")
		.setMessage(msg)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//营销代表点确定后直接返回
				if(mAuto){
					FilterActivity.this.finish();
				}
			}
		}).show();
 
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
 
		if (!Common.isEmpty((String) msg.obj)) {
			Common.showToast("通讯录筛查失败：" + (String) msg.obj, this);
		} else {
			Common.showToast("通讯录筛查失败，请检查网络是否通畅", this);
		}		 
	}

}

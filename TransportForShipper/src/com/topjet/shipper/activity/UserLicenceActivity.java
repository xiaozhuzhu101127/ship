package com.topjet.shipper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-10 下午3:51:41  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class UserLicenceActivity 
extends BaseActivity 
implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_user_licence);
		common_title = (TextView)this.findViewById(R.id.common_title);
		common_title.setText("获取密码");
		super.onCreate(savedInstanceState); 
	}

	@Override
	public void onClick(View v) {
		boolean agree = false;
		switch (v.getId()) {
		case R.id.licence_agree:
			agree = true;
			break;
		case R.id.licence_disagree:
			agree = false;
			break;
		}
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", agree);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

}

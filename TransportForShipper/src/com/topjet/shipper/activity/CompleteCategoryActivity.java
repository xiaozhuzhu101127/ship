package com.topjet.shipper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.model.Identity;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-13 上午10:07:25  
 * Description:完善资料选择身份
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class CompleteCategoryActivity 
extends BaseActivity 
implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_complete_category);
		super.onCreate(savedInstanceState);
		common_title.setText("完善资料");
		common_main.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v) {
		Identity identity = null;
		switch (v.getId()) {
		case R.id.rl_driver:
			identity = Identity.Driver;
			Intent intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.rl_owner:
			identity = Identity.GoodsSourceOwner;
			intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.rl_info:
			identity = Identity.InfomationMinistry;
			intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.rl_company:
			identity = Identity.LogisticCompany;
			intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		}

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

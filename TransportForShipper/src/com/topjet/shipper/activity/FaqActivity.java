package com.topjet.shipper.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-6 上午9:15:20  
 * Description:关于软件
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class FaqActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_faq);
		super.onCreate(savedInstanceState);
		common_title.setText("常见问题");
		common_recommand.setVisibility(View.VISIBLE);
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

package com.topjet.shipper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-30 上午9:53:23  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about);
		super.onCreate(savedInstanceState);
		common_title.setText("关于软件");
		common_recommand.setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.version)).setText("For Android V" + Common.getVersionName()+" build2014");
	}

	@Override
	protected void ioHandler(Message msg) {
		
	}

	@Override
	protected void doError(Message msg) {
		
	}
	public void click(View v){
		startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(DefaultConst.DRIVER)));
	}
}

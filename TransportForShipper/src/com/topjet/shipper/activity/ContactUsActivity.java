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
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-7-15 上午10:53:46  
 * Description:
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class ContactUsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_contactus);
		super.onCreate(savedInstanceState);
		common_title.setText("联系我们");
		common_recommand.setVisibility(View.VISIBLE);
	}

	@Override
	protected void ioHandler(Message msg) {
		
	}

	@Override
	protected void doError(Message msg) {
		
	}
	public void click(View v){
		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+DefaultConst.KEFU)));
	}
}

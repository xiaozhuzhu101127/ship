package com.topjet.shipper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
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
 * Create at:	2014-5-30 下午5:16:58  
 * Description:投诉
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class CreditTouShuActivity extends BaseActivity{

	private EditText contentView;
	private String number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_toushu_correct);
		super.onCreate(savedInstanceState);
		
		common_main.setVisibility(View.VISIBLE);
		common_title.setText("我要投诉");
		
		number = getIntent().getExtras().getString("number");
		contentView = (EditText) findViewById(R.id.correct_content);
	}

	private void correct() {
		ArrayList<String> types = new ArrayList<String>(4);

		types.add("MISSTS_OTHER");

		String content = contentView.getText().toString();
		if ("".equals(content)) {
			Common.showToast("请填写投诉信息！", this);
			return;
		}

		this.initProgress("正在提交数据，请稍后......");
		this.mLoadData.reportContactInfoError(baseHandler,
				DefaultConst.CMD_REPORT_CONTACT_INFO_ERR, number, types,
				content);
	}

	@Override
	protected void ioHandler(Message msg) {
		if (DefaultConst.CMD_REPORT_CONTACT_INFO_ERR == msg.what)
			Common.showToast("提交成功", this);
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub

	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.ts_telts:
			startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + DefaultConst.KEFU)));
			break;
		case R.id.ts_cancle:
			finish();
			break;
		case R.id.ts_center:
			correct();
			break;
		}
	}
}

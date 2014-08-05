package com.topjet.shipper.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

 
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
 
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;



/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-5 下午3:05:38  
 * Description:报错
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class CreditCorrectActivity extends BaseActivity {

	private CheckBox nameView;
	private CheckBox addressView;
	private CheckBox typeView;
	
	private EditText contentView;
	private String number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_credit_correct);
		super.onCreate(savedInstanceState);
	 
		number = getIntent().getExtras().getString("number");
		common_title.setText("我要报错");
		common_main.setVisibility(View.VISIBLE);
		footer_cancle.setVisibility(View.VISIBLE);
		footer_delete.setVisibility(View.VISIBLE);
		footer_center.setText("提交报错");
		
		nameView = (CheckBox)findViewById(R.id.correct_name);
		addressView = (CheckBox)findViewById(R.id.correct_address);
		typeView = (CheckBox)findViewById(R.id.correct_type);
		contentView = (EditText)findViewById(R.id.correct_content);
		
	//	ohthView = (CheckBox)findViewById(R.id.correct_other);
	}

	private void correct(){
		ArrayList<String> types = new ArrayList<String>(4);
		if(nameView.isChecked()){
			types.add("name");
		}
		if(addressView.isChecked()){
			types.add("address");
		}
		if(typeView.isChecked()){
			types.add("MISSTS_UC");
		}
//		if(ohthView.isChecked()){
//			types.add("MISSTS_OTHER");
//		}
		if(types.size() == 0){
			Common.showToast("至少选择一个类型！",this);
			return;
		}
		String content = contentView.getText().toString();
		if("".equals(content)){
			Common.showToast("请填写备注信息！",this);
			return;
		}	 
		
		this.initProgress("正在提交数据，请稍后......");
		this.mLoadData.reportContactInfoError(baseHandler, 
				DefaultConst.CMD_REPORT_CONTACT_INFO_ERR,
				number,
				types,
				content);
	}


	@Override
	protected void ioHandler(Message msg) {
		if (DefaultConst.CMD_REPORT_CONTACT_INFO_ERR == msg.what)
			Common.showToast("提交成功",this);
	}

	@Override
	protected void doError(Message msg) {
		
	}
	public void doFooterCenter(){
		correct();
	}
	public void goodsDelete() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				nameView.setChecked(false);
				typeView.setChecked(false);
				addressView.setChecked(false);
				contentView.setText("");
			}
		});
	}
}

package com.topjet.shipper.activity;



import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.AppConifg;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
 


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-13 下午1:05:40  
 * Description:
 *
 * 修改历史:账户余额
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class BalanceActivity extends BaseActivity implements OnClickListener{
	TextView mBalanceMoneyView;
	TextView mBalanceScoreView; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_balance);
		super.onCreate(savedInstanceState);
		mBalanceMoneyView = (TextView) findViewById(R.id.balance_money);
		mBalanceScoreView = (TextView) findViewById(R.id.balance_score);
		common_title.setText("账户余额");

		fetchData();
	}

	private void renderData(JSONObject data){
		String money = data.optString("accountRemainStr", "0");
		int score = data.optInt("activityNumber", 0);
		mBalanceMoneyView.setText(money);
		mBalanceScoreView.setText(score + "");
	}

	private void fetchData(){ 
		
		this.initProgress("正在获取数据，请稍后......");
		this.mLoadData.getMemberAccountState(baseHandler, DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE);
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + DefaultConst.KEFU)));
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE == msg.what ){			
			renderData(((JSONObject)msg.obj).optJSONObject("accountState"));
		}
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		Common.showToast("获取数据失败!", this);
	}
}

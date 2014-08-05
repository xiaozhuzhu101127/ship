package com.topjet.shipper.activity;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.adapter.UserTypeTongJiAdapter;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.DisplayUtil;

public class ShuizaiChaWoActivity extends BaseActivity {
	
	private TextView  zongping= null;
	
	private UserTypeTongJiAdapter mListAdapter;
	private ListView memberList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_shui_zai_cha_wo);
		super.onCreate(savedInstanceState);
		
		common_title.setText("谁在查我");
		common_main.setVisibility(View.VISIBLE);
		
		zongping=(TextView)super.findViewById(R.id.zongping);
		mListAdapter = new UserTypeTongJiAdapter(this);
		memberList = (ListView) findViewById(R.id.memberList);
		memberList.setAdapter(mListAdapter);
		fetchData();
	
	
	}
	
	private void fetchData() {

		this.initProgress("加载中......");
		this.mLoadData.getChaKan(baseHandler, DefaultConst.CMD_DP_GET_CHA_KAN_RESULT);
	}


	@Override
	protected void ioHandler(Message msg) {
		if (msg.what == DefaultConst.CMD_DP_GET_CHA_KAN_RESULT) {
			JSONObject data =(JSONObject) msg.obj;
			renderData(data.optJSONArray("mTypeStat"));
			//android:text="至2014年1月2日，共计有来自24个省的1234个会员对您发起34322次查询"
		
			String text="至【<font color='#009900'>"+DisplayUtil.renderDate(new Date(), "yyyy年MM月dd日")+"</font>】<br>共计有来自【<font color='#FF6600'>"
					+data.optInt("provinceTotal")+"个</font>】省的【<font color='#FF6600'>"
					+data.optInt("memberNumber")+"个</font>】会员<br>对您发起【<font color='#FF6600'>"
					+data.optInt("queryNumber")+"次</font>】查询";
			zongping.setText(Html.fromHtml(text));
			
		}
		

	}
	
	private void renderData(JSONArray data) {
		
			
			mListAdapter.setData(data);
			mListAdapter.notifyDataSetChanged();
	}

}

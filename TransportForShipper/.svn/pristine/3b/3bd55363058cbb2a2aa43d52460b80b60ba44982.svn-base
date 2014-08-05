package com.topjet.shipper.activity;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.adapter.MessageListAdapter;
import com.topjet.shipper.model.MessageInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.DisplayUtil;

/**
 * 消息推送页
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:22:04  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MessageActivity extends BaseActivity implements OnClickListener {

	ListView mListView;
	private MessageListAdapter mListAdapter;

	Button btn_setting_logout;

	private String tk_length = null;

	private String tk_type = null;

	private String tk_city = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_category);
		super.onCreate(savedInstanceState);

		common_title.setText("信息中心");
		footer_delmsg.setVisibility(View.VISIBLE);
		mListAdapter = new MessageListAdapter(this);
		mListView = (ListView) findViewById(R.id.listview_setting_menu);
		mListView.setAdapter(mListAdapter);
		// 取参数
		tk_length = this.getIntent().getStringExtra("tk_length");
		tk_type = this.getIntent().getStringExtra("tk_type");
		tk_city = this.getIntent().getStringExtra("tk_city");
		if("不限".equals(tk_length)){
			tk_length="";
		}
		if("不限".equals(tk_type)){
			tk_type="";
		}
		ArrayList<MessageInfo> list = db.getMessageInfos(tk_length, tk_type,
				tk_city);
		if (list != null && list.size() > 0) {
			mListAdapter.setList(list);
		} else {
			mListAdapter.setList(new ArrayList<MessageInfo>());
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mobile = ((TextView) view
						.findViewById(R.id.item_msg_mobile)).getText()
						.toString();
				if (!Common.isEmpty(mobile)) {
					gotoCredit(mobile);
				}

			}
		});

		btn_setting_logout = (Button) this
				.findViewById(R.id.btn_setting_logout);
		btn_setting_logout.setVisibility(View.VISIBLE);
		btn_setting_logout.setText("货源搜索");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mLoadData.pushMessage(baseHandler, DefaultConst.CMD_PUSH_MESSAGE);
	}

	@Override
	public void onClick(View v) {
		 if (R.id.btn_setting_logout == v.getId()) 
			startActivity(new Intent(this, MessageSearchActivity.class));
		
	}

	

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_PUSH_MESSAGE == msg.what) {
			doInputMsgToDB(msg);
			mListAdapter.setList(db.getMessageInfos(tk_length, tk_type, tk_city));
		}
	}

	private void doInputMsgToDB(Message msg) {
		// TODO Auto-generated method stub
		JSONArray array = ((JSONObject) msg.obj).optJSONArray("messages");
		if (array.length() == 0) {
			return;
		}
		ArrayList<MessageInfo> msgInfos = new ArrayList<MessageInfo>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject g = array.optJSONObject(i);
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.photo = g.optString("photoaddr");
			messageInfo.name = g.optString("REALNAME");
			messageInfo.credit = g.optString("YSLACCOUNT");
			JSONObject t = g.optJSONObject("CREATE_TIME");
			messageInfo.info = new StringBuilder()
					.append(Dict.getCityName(g.optString("CT_REGOIN"), 2))
					.append(" ")
					.append(DisplayUtil.renderDate(new Date(t.optLong("time")),
							"M-dd HH:mm")).toString();
			messageInfo.content = g.optString("MSG");
			messageInfo.mobile = g.optString("MOBILE");
			msgInfos.add(messageInfo);
		}
		db.addMessage(msgInfos);

	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub

	}

}

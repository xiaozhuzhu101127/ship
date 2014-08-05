package com.topjet.shipper.activity;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Spinner;

import com.topjet.shipper.activity.DianPingAddActivity;
import com.topjet.shipper.activity.DianPingDetailResultActivity;
import com.topjet.shipper.util.DC;
import com.topjet.shipper.R;
import com.topjet.shipper.adapter.PingJiaDetailListAdapter;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-30 下午2:27:37  
 * Description:查看评价
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class DianPingDetailResultActivity extends BaseListViewActivity
		implements OnClickListener, OnScrollListener {

	private Long USRID = null;
	
	private String DCT_DPUC = null;
	
	private Integer type= 0;

	private PingJiaDetailListAdapter mListAdapter;

	protected ListView mListView;
	
	private boolean append = false;

	private String[] level={"全部评价","1☆评价","2☆评价","3☆评价","4☆评价","5☆评价"};
	
	private List<String> levelList=new ArrayList<String>();
	
	//1 表示 DianPingDetailResultActivity，2表示DianPingAddActivity
	private String typeActivity;
	
	private Dialog mDialog;
	
	private boolean isFirst = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_dianping_detail_result);
		super.onCreate(savedInstanceState);
		
		for(int i=0;i<level.length;i++){
			levelList.add(level[i]);
		}
		Spinner spinner = (Spinner) findViewById(R.id.dp_result_spinner);
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.item_spinner,levelList);
		spinner.setAdapter(adapter);
		//默认选中第一个
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				showToast(levelList.get(position));
				if(isFirst){
					isFirst = !isFirst;
					return;
				}
				type = position;
				append = false;
				isMore = false;
				fetchData(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		USRID = getIntent().getExtras().getLong("USRID");
		type = getIntent().getExtras().getInt("type");
		DCT_DPUC= getIntent().getExtras().getString("DCT_DPUC");
		mListAdapter = new PingJiaDetailListAdapter(this);
		mListView = (ListView) findViewById(R.id.listView);
		fetchData(append);
		initFootView(this);
		mListView.addFooterView(footerView);
		mListView.setAdapter(mListAdapter);
		mListView.setOnScrollListener(this);
		
	}

	private void fetchData(boolean append) {

		mLoadData.getDpDetailList(baseHandler,
				DefaultConst.CMD_DP_DETAIL_LIST_RESULT, USRID,type,DCT_DPUC,
				append ? mListAdapter.getCount() : 0);
	}

	private void renderData(JSONArray data) {
		if(data.length() == 0){
			//不是点击更多时候，假如没数据，应该显示0条数据。
			if(!isMore){
				showToast("暂无数据");
				ll_more.setVisibility(View.GONE);
				if(!isFirst){
					mListAdapter.setData(data);
					mListAdapter.notifyDataSetChanged();
				}
				return;
			}
			changeFootViewNoData();
			return;
		}
		if(append){
			mListAdapter.appendData(data);
		}else{
			mListAdapter.setData(data);
		}
		mListAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		//添加数据
		if (!append) append = !append;
	}

	@Override
	protected void ioHandler(Message msg) {
		if (msg.what == DefaultConst.CMD_DP_DETAIL_LIST_RESULT) {
			mListAdapter.setLoading(false);
			renderData(((JSONObject) msg.obj).optJSONArray("details"));
		}

	}

	@Override
	protected void doError(Message msg) {
		Common.showToast((String) msg.obj, this);

	}

	@Override
	protected void showData() {
		// TODO Auto-generated method stub
		fetchData(append);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dp_result_back:
			finish();
			break;
		//添加评价
		case R.id.pjbtn:
			if (null == mDialog) {
				mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.dialog_score_shop);
				
				TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
				text1.setText("我是承运方");
				text1.setOnClickListener(this);
				TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
				text2.setText("我是发货方");
				text2.setOnClickListener(this);
				TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);				
				view.setText("请选择您的身份");
			}
			typeActivity="2";
			mDialog.show();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();			
			Intent it = null;
			if("1".equals(typeActivity)){
				it=new Intent(this,DianPingDetailResultActivity.class);
			}else{
				it=new Intent(this,DianPingAddActivity.class);
			}
			//这里要反着来，点发货方，DCT_DPUC 为承运方
			it.putExtra("DCT_DPUC", DC.DPUC_CARRY);
			it.putExtra("USRID", USRID);		
			startActivity(it);
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();			
			Intent itt =null;
			if("1".equals(typeActivity)){
				itt=new Intent(this,DianPingDetailResultActivity.class);
			}else{
				itt=new Intent(this,DianPingAddActivity.class);
			}
					
			itt.putExtra("DCT_DPUC", DC.DPUC_SHIP);
			itt.putExtra("USRID", USRID);		
			startActivity(itt);
			break;
		case R.id.ll_more:
			//获取更多数据
			doMoreLoadData();
			break;
			
		}
	
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

		}

		// 已经停止滚动
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 滚动到底部
			int viewCount = view.getCount() - 1;
			int viewLastPostion = view.getLastVisiblePosition();
			if (viewLastPostion == (viewCount)) {
				if (viewLastPostion != getLastVisiblePosition) {
					doMoreLoadData();
					getLastVisiblePosition = viewLastPostion;
				}
			} else if (view.getFirstVisiblePosition() == 0) {

			}
			getLastVisiblePosition = 0;
		}
	}

}

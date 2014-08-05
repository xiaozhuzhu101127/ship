package com.topjet.shipper.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.topjet.shipper.activity.CreditResultActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.adapter.TruckRecommendAdapter;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.DialogUtil;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.Receivable;
import com.topjet.shipper.widget.RefreshableView;
import com.topjet.shipper.widget.RefreshableView.PullToRefreshListener;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-19 上午9:18:06  
 * Description:车源推荐列表
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class TruckRecommendListActivity extends BaseListViewActivity implements OnClickListener,OnScrollListener,Receivable{
	
	private ListView truckListView;
	
	private TruckRecommendAdapter truckAdapter;
	
	private RefreshableView refreshableView;
	
	private boolean append = false;
	
	private Bundle extras;
	
	//地区
	private String depart;
	
	private Map<String, Object> params;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_truck_recommend_list);
		super.onCreate(savedInstanceState);
		initView();
		initData();	 
	}
	
	public void initData() {
			initProgress("加载中……");
			append = false;
			isMore = false;
			fetchData(false);
	}

	private void initView() {
		initFootView(this);
		truckAdapter = new TruckRecommendAdapter(this);
		common_title.setText("车源推荐");
		extras  = getIntent().getExtras();
		//地区
		if(null != extras && !Common.isEmpty(extras.getString("city"))){
			title_place.setText(extras.getString("city"));
		}else {
			title_place.setText(app.getLocation());
		}
		
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		truckListView = (ListView) findViewById(R.id.truck_listView);
		truckListView.addFooterView(footerView);
		truckListView.setAdapter(truckAdapter);
		truckListView.setOnScrollListener(this);
		truckListView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//不需要详情
//				showTruckDetail();
			}
		});
		footer_center.setText("车源搜索");
		footer_goods_history.setVisibility(View.VISIBLE);
		footer_refresh.setVisibility(View.VISIBLE);
		title_place.setVisibility(View.VISIBLE);
		//下拉更新
		// 不同界面在注册下拉刷新监听器时一定要传入不同的id。这里是1
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {  
            @Override  
            public void onRefresh() { 
            	//不添加数据
            	append = false;
            	fetchData(false);
            	//休眠1秒.不然刷新太快，会出现重复数据
            	try {
            		Thread.sleep(1000);
				} catch (Exception e) {
				}
                refreshableView.finishRefreshing();  
            }  
        }, 1); 
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ll_more){
			//获取更多数据
			doMoreLoadData();
		}
	}
	
	@Override
	protected void showData() {
		fetchData(append);
	}

	private void fetchData(boolean append) {
		params = new HashMap<String, Object>();
		if(append){
			params.put("offset", truckAdapter.getCount());
		}else {
			params.put("offset", 0);
		}
		if(null == extras)
			return;
		//1 查询周边空车
		if(extras.getBoolean("around")){
			params.put("CITYCODE",extras.getString("Depart"));
			params.put("MOBILE", shareHelper.getUsername());
			//车源搜索
			mLoadData.getTrucksInfoList(baseHandler, DefaultConst.CMD_GET_TRUCKINFO, params);
			return;
		}
		//2自己设定条件查询车源
		//默认是当前城市
		depart = app.getLocationCode();
		if(!Common.isEmpty(extras.getString("citycode")))
			depart = extras.getString("citycode");
		params.put("CITYCODE",depart);
		params.put("USRID", shareHelper.getUserId());
		if(!Common.isEmpty(extras.getString("TKTARGET")))
			params.put("TKTARGET",extras.getString("TKTARGET"));
		if(!Common.isEmpty(extras.getString("DCT_TT")))
			params.put("DCT_TT",extras.getString("DCT_TT"));
		if(!Common.isEmpty(extras.getString("DCT_TKLEN")))
			params.put("DCT_TKLEN",extras.getString("DCT_TKLEN"));
		//车源搜索
		mLoadData.getTrucksList(baseHandler, DefaultConst.CMD_GET_TRUCKLIST, params);
	}
	private void renderData(JSONArray data){
		if(data.length() == 0){
			//不是点击更多时候，假如没数据，应该显示0条数据。
			if(!isMore){
				
			}
			changeFootViewNoData();
			return;
		}
		if(append){
			truckAdapter.appendData(data);
		}else{
			truckAdapter.setData(data);
		}
		truckAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		//添加数据
		if (!append) append = !append;
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		JSONObject jObject = (JSONObject) msg.obj;
		JSONArray data = null;
		try { 
			data = jObject.getJSONArray("trucksSourceInfo");
			renderData(data);
		} catch (JSONException e) {
			showToast("暂无数据");
		}
		truckAdapter.setLoading(false);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		// TODO：已经停止滚动（
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 滚动到底部
			int viewCount = view.getCount() - 1;
			int viewLastPostion = view.getLastVisiblePosition();
			if (viewLastPostion == (viewCount)) {
				if (viewLastPostion != getLastVisiblePosition) {
					doMoreLoadData();
				}
			} 

		}
	}
	
	@Override
	public void receive(int type, Object... datas) {
		if(showing != null){
			showing.dismiss();
		}
		//切换地区，查询周边空车
		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
			//城市选择不限的时候显示省份
			//不能查询，必须是城市
//			autoSerach((String)picking.getTag(),(String)picking.getText());
			showToast("出发地必须是地级市，请重新选择。");
			return;
		}
		picking.setText((String)datas[1]);
		picking.setTag(datas[0]);
		//是省份的时候，继续展示城市
		if(DialogUtil.isProvinceDialog(showing) && !Dict.BLANK.equals((String)datas[0])){
			//是直辖市,直接查询车辆
			if(Dict.isMunicipalCity(picking.getTag().toString())){
				String depart = (String)picking.getTag()+"_"+(String)picking.getTag();
				autoSerach(depart,(String)picking.getText());
				return;
			}
			showing = DialogUtil.showCityDialog(this, (String)datas[0]);
			return;
		}
		autoSerach((String)picking.getTag(),(String)picking.getText());
	}
	//切换地区查询周边空车
	private  void autoSerach(String depart,String city) {
		Intent intent = new Intent(this, TruckRecommendListActivity.class);
		intent.putExtra("Depart", depart);
		intent.putExtra("city", city);
		//周边空车
		intent.putExtra("around", true);
		startActivity(intent);
	}
	//诚信查询
	public void creditQuery(String mobile){
		Intent intent = new Intent(this, CreditResultActivity.class);
		intent.putExtra("number", mobile);
		startActivity(intent);
	}
	//呼叫发货人
	public void callDriver(String mobile){
		startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + mobile)));	
	}

	@Override
	protected void doFooterCenter() {
		startActivity(new Intent(this, TruckSearchActivity.class));
	}
	
}

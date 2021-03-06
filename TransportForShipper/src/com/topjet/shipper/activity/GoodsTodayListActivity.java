package com.topjet.shipper.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.topjet.shipper.widget.RefreshableView;
import com.topjet.shipper.widget.RefreshableView.PullToRefreshListener;
import com.topjet.shipper.R;
import com.topjet.shipper.adapter.GoodsTodayAdapter;
import com.topjet.shipper.util.DefaultConst;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-8 下午2:25:00  
 * Description:今日货源
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsTodayListActivity extends BaseListViewActivity implements OnClickListener,OnScrollListener{
	
	private ListView goodsListView;
	
	private ImageView noImageView;
	
	private GoodsTodayAdapter goodsAdapter;
	
	private RefreshableView refreshableView;
	
	private boolean append = false;
	
	private Map<String, Object> params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_today_list);
		super.onCreate(savedInstanceState);	
		goodsAdapter = new GoodsTodayAdapter(this);
		initView();
		initData();	 
	}
	
	public void initData() {
		initProgress("加载中……");
		//刷新全部的话，设置为true即可
		append = false;
		isMore = false;
		fetchData(false);
	}

	private void initView() {
		initFootView(this);
		params = new HashMap<String, Object>();
		params.put("USRID", shareHelper.getUserId());
		common_title.setText("今日货源");
		common_main.setVisibility(View.VISIBLE);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		goodsListView = (ListView) findViewById(R.id.goods_listView);
		goodsListView.addFooterView(footerView);
		goodsListView.setAdapter(goodsAdapter);
		goodsListView.setOnScrollListener(this);
		goodsListView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JSONObject g = (JSONObject) goodsAdapter.getItem(position);
				showGoodsDetail(g.optLong("GSID"),g.optString("GSSTS"));
			}
		});
		noImageView = (ImageView) findViewById(R.id.goods_no);
		footer_center.setText(DefaultConst.PUBLISH);
		footer_history.setVisibility(View.VISIBLE);
		footer_refresh.setVisibility(View.VISIBLE);
//		title_place.setVisibility(View.VISIBLE);
		//下拉更新
		// 不同界面在注册下拉刷新监听器时一定要传入不同的id。这里是0
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
        }, 0); 
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
		if(append){
			params.put("offset", goodsAdapter.getCount());
		}else {
			params.put("offset", 0);
		}
		mLoadData.getCurrentGoodsInfo(baseHandler, DefaultConst.CMD_GOODS_CURRENTINFO, params);
	}
	private void renderData(JSONArray data){
		if(data.length() == 0){
			//不是点击更多时候，假如没数据，应该显示0条数据。
			if(!isMore){
				noImageView.setVisibility(View.VISIBLE);
				ll_more.setVisibility(View.GONE);
				return;
			}
			changeFootViewNoData();
			return;
		}
		if(append){
			goodsAdapter.appendData(data);
		}else{
			goodsAdapter.setData(data);
		}
		goodsAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		//添加数据
		if (!append) append = !append;
	}
	private void showGoodsDetail(long gsId,String GSSTS) {
		
		Intent intent ;
		if(GSSTS.equals("GSSTS_SUBMIT")){
			intent = new Intent(this, GoodsSuccessActivity.class);
		}else {
			intent = new Intent(this, GoodsDetailActivtiy.class);
		}
		intent.putExtra("GsId", gsId);
		startActivity(intent);
	}
	@Override
	protected void ioHandler(Message msg) {
		JSONObject jObject = (JSONObject) msg.obj;
		JSONArray data = null;
		try {
			data = jObject.getJSONArray("goodsInfo");
			renderData(data);
		} catch (JSONException e) {
			showToast("暂无数据");
		}
		goodsAdapter.setLoading(false);
	}
	public void doFooterCenter(){
		startActivity(new Intent(context, GoodsPublishActivity.class));
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
//	@Override
//	public void receive(int type, Object... datas) {
//		if(showing != null){
//			showing.dismiss();
//		}
//		//切换地区，查询同行货源
//		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
//			//城市选择不限的时候显示省份
//			autoSerach((String)picking.getTag(),(String)picking.getText());
//			return;
//		}
//		picking.setText((String)datas[1]);
//		picking.setTag(datas[0]);
//		//是省份的时候，继续展示城市
//		if(DialogUtil.isProvinceDialog(showing) && !Dict.BLANK.equals((String)datas[0])){
//			showing = DialogUtil.showCityDialog(this, (String)datas[0]);
//			return;
//		}
//		autoSerach((String)picking.getTag(),(String)picking.getText());
//	}
//	//查询同行货源
//	private  void autoSerach(String depart,String city) {
//		Intent intent = new Intent(this, GoodsPeerListActivity.class);
//		intent.putExtra("CITYCODE", depart);
//		intent.putExtra("city", city);
//		startActivity(intent);
//	}
}

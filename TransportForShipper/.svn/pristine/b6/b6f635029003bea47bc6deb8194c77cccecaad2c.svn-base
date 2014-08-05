package com.topjet.shipper.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;

public abstract class BaseListViewActivity 
extends BaseActivity {
	protected View footerView;
	protected LinearLayout ll_more;
	private ImageView imgMore;
	private TextView onLoadMore;
	protected ListView mListView; 
	protected int getLastVisiblePosition = 0;
	
	protected boolean isMore = false; 
	private boolean isNoData = false;
	protected void initFootView(Context context) {		
		LayoutInflater inflater = LayoutInflater.from(context);
		footerView = inflater.inflate(R.layout.scrolllistview_footer_view, null);
		ll_more = (LinearLayout) footerView.findViewById(R.id.ll_more);
		imgMore = (ImageView) footerView.findViewById(R.id.mediawall_img_more);
		onLoadMore = (TextView) footerView
				.findViewById(R.id.mediawall_onload_more);
		
	}

	protected void doMoreLoadData() {
		// TODO Auto-generated method stub	 
		if (!isMore){
			isMore = !isMore;
 
			if (!Common.checkNet(this))	{
				changeFootViewNoNet();
				return;
			}
			ChangeFootViewYes();
			if (isNoData) {
				changeFootViewNoData();
				return;
			}
			showData();
		}					
	}

	protected abstract void showData();
	
	private void ChangeFootViewYes(){
		onLoadMore.setText("加载中...");
		imgMore.setVisibility(View.VISIBLE);
		Animation hyperspaceJumpAnimation = AnimationUtils
				.loadAnimation(this, R.anim.anim_sroll_loading);
		imgMore.startAnimation(hyperspaceJumpAnimation);
	}
	
	protected void changeFootViewNoData(){
		onLoadMore.setText("无更多的数据");
		imgMore.clearAnimation();
		imgMore.setVisibility(View.GONE);		 
	}
	
	private void changeFootViewNoNet(){	 
		onLoadMore.setText("无法连接网络");				
		imgMore.clearAnimation();
		imgMore.setVisibility(View.GONE);
	}
	
	protected void  onLoadMoreOver(){	
		isMore = false;
		onLoadMore.setText("点击查看更多");
		imgMore.clearAnimation();
		imgMore.setVisibility(View.GONE);
	}
	
	
	protected void clearImgMore(){	 
		onLoadMore.setText("");
		imgMore.clearAnimation();
		imgMore.setVisibility(View.GONE);
	}
	
}

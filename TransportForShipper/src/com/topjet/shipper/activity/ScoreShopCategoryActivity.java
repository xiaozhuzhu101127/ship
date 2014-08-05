package com.topjet.shipper.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.AppConifg;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-5 下午4:12:13  
 * Description:积分商城
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class ScoreShopCategoryActivity extends BaseActivity  {

	WebView mWebView;
	String mNumber;
	ProgressDialog mProgressDialog;
	Button btn_other_function;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_scoreshop_category);
		super.onCreate(savedInstanceState);	 
		
		common_title.setText("积分商城");
		common_recommand.setVisibility(View.VISIBLE);
		//WebView可以加载网页
		//myWebView.loadUrl(“http://www.google.com“);
		mWebView = (WebView) findViewById(R.id.credit_webview);
		//在WebView中使用JavaScript
		//可以通过getSettings()获得WebSettings
		WebSettings websettings = mWebView.getSettings();
		websettings.setSupportZoom(true);
		websettings.setBuiltInZoomControls(true);
		websettings.setJavaScriptEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//不加上，会显示白边
		
		mProgressDialog = ProgressDialog.show(this, "", "正在加载，请稍候...", true);
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("tel:")) {
					//网页链接拨电话
					if(url.endsWith("4000566560")){
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
						startActivity(intent);
					}
				} else if (url.startsWith("http:") || url.startsWith("https:")) {
					view.loadUrl(url);
				}
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			}
		});
		String url=AppConifg.WEB_JIFEN_MALL+ "usrid="+shareHelper.getUserId();
		mWebView.loadUrl(url);
	}

	public void click(View v) {
		switch(v.getId()){	
		case R.id.jfrule:
			startActivity(new Intent(this,ScoreRuleActivity.class));
			break;
		}	 
	}

	@Override
	public void onBackPressed() {
		mWebView.stopLoading();
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		super.onBackPressed();
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}
}

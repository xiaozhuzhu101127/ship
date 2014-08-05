package com.topjet.shipper.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.MyApplication;
import com.topjet.shipper.R;
import com.topjet.shipper.util.AppConifg;
import com.topjet.shipper.util.TransportLog;

public class MemberWealSearchActivity extends BaseActivity {

	WebView mWebView;
	ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_member_search_result);
		super.onCreate(savedInstanceState);	 
		
		common_title.setText("每日抽奖");
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
		TransportLog.i("URL", AppConifg.WEB_ADDRESS );
		String url=AppConifg.WEB_CHOUJIANG+ "usrid="+ +MyApplication.getInstance().getShareHelper().getUserId()
				+"&mobile="+MyApplication.getInstance().getShareHelper().getUsername();
		mWebView.loadUrl(url);
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

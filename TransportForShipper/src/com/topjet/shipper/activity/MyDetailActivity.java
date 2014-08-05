package com.topjet.shipper.activity;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.model.Identity;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DC;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.DisplayUtil;
import com.topjet.shipper.util.ImageTask;
 
 
/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-5 上午9:53:47  
 * Description:我的560
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class MyDetailActivity extends BaseActivity implements OnClickListener{
	/**
	 * 需要heard右侧拥有资料有误按钮
	 */
	
	String phone;
	ProgressDialog mProgressDialog;
	Button btn_other_function;
	private ImageView  pictureImageView ;
	
	private TextView  realNameText ;
	private TextView companyNameText ;
	private TextView yslAccountText ;
	
	private RatingBar  fahuoxingjiBar ;
	
	private RatingBar chengyunxingjiBar ;
	
	private TextView fahuoxingjiCountText ;
	
	private TextView chengyunxingjiCountText ;
	
	private TextView mobileText ;
	private TextView telText ;
	private TextView paimingText ;
	private TextView usrTypeText ;
	private TextView memberLevelText ;
	private TextView registerTimeText ;
	private TextView companylinesText ;
	private TextView companyAddressText;
	private TextView commentText ;
	private TextView levelView;
	private TextView certView;
	private TextView jfView;
	private LinearLayout info1Layout;
	private LinearLayout info2Layout;
	private ImageView telvView1;
	private ImageView telvView2;
	private String mobile;
	
	private String tel;
	
	private Dialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_detail);
		super.onCreate(savedInstanceState);	 
		
		common_title.setText("我的560");
		common_main.setVisibility(View.VISIBLE);
		
		phone = shareHelper.getUsername(); 
		commentText = (TextView)findViewById(R.id.comment);
		levelView = (TextView)findViewById(R.id.credit_level);
		certView = (TextView)findViewById(R.id.credit_cert);
		jfView = (TextView)findViewById(R.id.credit_jf);
		info1Layout = (LinearLayout)findViewById(R.id.credit_info_1);
		info2Layout = (LinearLayout)findViewById(R.id.credit_info_2);
		
		pictureImageView=(ImageView)findViewById(R.id.picture);
		realNameText = (TextView)findViewById(R.id.realName);
		companyNameText = (TextView)findViewById(R.id.companyName);
		yslAccountText = (TextView)findViewById(R.id.yslAccount);  
		fahuoxingjiBar = (RatingBar)findViewById(R.id.fahuoxingji);  
		chengyunxingjiBar = (RatingBar)findViewById(R.id.chengyunxingji);  
		
		fahuoxingjiCountText = (TextView)findViewById(R.id.fahuoxingjicount);  
		chengyunxingjiCountText= (TextView)findViewById(R.id.chengyunxingjicount);  
		
		mobileText= (TextView)findViewById(R.id.mobile);  
		telText= (TextView)findViewById(R.id.tel);  
		paimingText= (TextView)findViewById(R.id.paiming);  
		usrTypeText= (TextView)findViewById(R.id.userType);  
		memberLevelText= (TextView)findViewById(R.id.memberLevel); 
		registerTimeText= (TextView)findViewById(R.id.registerTime); 
		companylinesText= (TextView)findViewById(R.id.companylines); 
		companyAddressText= (TextView)findViewById(R.id.companyAddress); 
		telvView1 = (ImageView) findViewById(R.id.teltb1);
		telvView2 = (ImageView) findViewById(R.id.teltb2);
		fetchData();
		
	}
	
	private void fetchData() {

		this.initProgress("加载中......");
		this.mLoadData.getDpInfo(baseHandler, DefaultConst.CMD_DP_INFO_QUERY_RESULT,phone);
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		renderData((JSONObject)msg.obj);
	}

	@Override
	protected void doError(Message msg) {
		
	}
	
	private void renderData(JSONObject jsonData) {
		JSONObject data = null;
		try {
			data = jsonData.getJSONObject("member");
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		//照片
		if(!Common.isEmpty(data.optString("photoAddr")))
			new ImageTask(pictureImageView).execute(data.optString("photoAddr"));
		//姓名
		realNameText.setText(data.optString("REALNAME"));
		//诚信值
		yslAccountText.setText(Html.fromHtml("诚信值:<font color='#CC6600'>"+data.optString("YSLACCOUNT")+"</font>"));
		//公司名称
		companyNameText.setText(data.optString("COMPANYNAME"));
		//发货星级展示	
		fahuoxingjiBar.setRating( data.optInt("SHIPPOINT"));
		//发货次数
		fahuoxingjiCountText.setText(data.optInt("BSHIPCOUNT")+"个评价");
		//承运星级
		chengyunxingjiBar.setRating(data.optInt("CARRYPOINT"));
		
		//承运次数
		chengyunxingjiCountText.setText(data.optInt("BCARRYCOUNT")+"个评价");
		//会员等级
		if(!Common.isEmpty(data.optString("serviceName")))
			levelView.setText(data.optString("serviceName"));
		//是否认证
		if(!Common.isEmpty(data.optString("DCT_UA")) && "UCERT_PS".equals(data.optString("DCT_UA")))
			certView.setText("已认证");
		//积分
		jfView.setText(data.optInt("YSLACCOUNT")+"积分");
		//提示
		commentText.setText(data.optString("comment"));
		 mobile=data.optString("MOBILE1");
		 tel=data.optString("TEL1");
		 if(!Common.isEmpty(mobile)){
			 telvView1.setVisibility(View.VISIBLE);
			 mobileText.setText(mobile);		
		 }
		 if(!Common.isEmpty(tel)){
			 telvView2.setVisibility(View.VISIBLE);
			 telText.setText(tel);		
		 }
		//当地排名
		String cityName=data.optString("cityName");
		if(!Common.isEmpty(cityName)){
			paimingText.setText(cityName+"第"+data.optInt("localTrustLevel")+"名");	
		}
		//会员类型
		usrTypeText.setText(Identity.getIdentity(data.optString("DCT_UT")).getDescription());
		//会员级别
		memberLevelText.setText(data.optString("serviceName")); 
		//注册时间
		JSONObject t = data.optJSONObject("CREATE_TIME");
		if(!(t==null||t.length()==0)){			
			registerTimeText.setText(DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd hh:mm:ss"));

		}
		companylinesText.setText(data.optString("BUSILINES"));
		companyAddressText.setText(data.optString("BUSIADDRESS"));
		info1Layout.setVisibility(View.VISIBLE);
		info2Layout.setVisibility(View.VISIBLE);
	}
	@Override
	public void onClick(View v) {
		Intent it = new Intent(this,DianPingDetailResultActivity.class);
		switch(v.getId()){
		case R.id.mypj:
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
			mDialog.show();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();			
			//这里要反着来，点发货方，DCT_DPUC 为承运方
			it.putExtra("DCT_DPUC", DC.DPUC_CARRY);
			it.putExtra("USRID", shareHelper.getUserId());		
			startActivity(it);
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();			
			
			it.putExtra("DCT_DPUC", DC.DPUC_SHIP);
			it.putExtra("USRID", shareHelper.getUserId());		
			startActivity(it);
			break;
		case R.id.my_szcw:
			startActivity( new Intent(this,ShuizaiChaWoActivity.class));
			break;		
		case R.id.my_complete:
			startActivity(new Intent(this, CompleteCategoryActivity.class));
			break;	
		}
	}	
}

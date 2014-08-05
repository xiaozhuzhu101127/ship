package com.topjet.shipper.activity;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.audividi.imageloading.core.DisplayImageOptions;
import com.audividi.imageloading.core.ImageLoader;
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
 * Create at:	2014-5-29 上午8:49:25  
 * Description:
 *
 * 修改历史:诚信查询结果
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class CreditResultActivity 
extends BaseActivity 
implements OnClickListener{
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
	private TextView usridText ;
	private TextView commentText ;
	private TextView levelView;
	private TextView certView;
	private LinearLayout viewpjLayout;
	private LinearLayout info1Layout;
	private ImageView telvView1;
	private ImageView telvView2;
	
	private Long USRID;
	private String phoneStr;
	
	private String mobile;
	
	private String tel;
	
	protected DisplayImageOptions options=new DisplayImageOptions.Builder()
	// 默认图片
	.showStubImage(R.drawable.idc_photo)
	.showImageForEmptyUri(R.drawable.idc_photo)
	.cacheInMemory()
	.cacheOnDisc()
	.build();
	

	

	//1 表示 DianPingDetailResultActivity，2表示DianPingAddActivity
	private String typeActivity;
	
	private Dialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_credit_result);
		super.onCreate(savedInstanceState);	 
		
		common_title.setText("诚信查询结果");
		common_main.setVisibility(View.VISIBLE);
		
		
		phone = getIntent().getExtras().getString("number"); 
		commentText = (TextView)findViewById(R.id.comment);
		levelView = (TextView)findViewById(R.id.credit_level);
		certView = (TextView)findViewById(R.id.credit_cert);
		viewpjLayout = (LinearLayout)findViewById(R.id.credit_viewpj);
		info1Layout = (LinearLayout)findViewById(R.id.credit_info_1);
		
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

//	private void correct(){
//		Intent intent = new Intent(CreditResultActivity.this, CreditCorrectActivity.class);
//		intent.putExtra("number", mNumber);
//		startActivity(intent);
//	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.queryDianPing:
		case R.id.credit_viewpj:
			
//			Intent ittt = new Intent(CreditResultActivity.this,DianPingDetailResultActivity.class);		
//			ittt.putExtra("USRID", USRID);
//			ittt.putExtra("type", 0);
//			startActivity(ittt);
//			break;
			
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
			typeActivity="1";
			mDialog.show();
			break;
		
		case R.id.addDianPing:
			//菜单样式修改
//		        TextView  text =(TextView) v.findViewById(R.id.addDianPingText);
//		        v.setBackgroundResource(R.drawable.dianping);
//		        Resources resource = (Resources) getBaseContext().getResources();  
//		        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.addDianPingTextColor);  
//		        if (csl != null) {  
//		            text.setTextColor(csl);  
//		            text.setShadowLayer(2, 1, 1, R.color.addDianPingTextColorShadow);
//		        }  
//		       
			
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
				it=new Intent(CreditResultActivity.this,DianPingDetailResultActivity.class);
			}else{
				it=new Intent(CreditResultActivity.this,DianPingAddActivity.class);
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
				itt=new Intent(CreditResultActivity.this,DianPingDetailResultActivity.class);
			}else{
				itt=new Intent(CreditResultActivity.this,DianPingAddActivity.class);
			}
					
			itt.putExtra("DCT_DPUC", DC.DPUC_SHIP);
			itt.putExtra("USRID", USRID);		
			startActivity(itt);
			break;
		case R.id.toushu:
			Intent intent = null;
			intent=new Intent(CreditResultActivity.this, CreditTouShuActivity.class);
			intent.putExtra("number", phoneStr);
			startActivity(intent);
			break;
		case R.id.baocuo:
			Intent itx = new Intent(CreditResultActivity.this, CreditCorrectActivity.class);
			itx.putExtra("number", phoneStr);
			startActivity(itx);
			break;
		case R.id.mobileLayout:
			if(!Common.isEmpty(mobile))
				 startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + mobile)));
		  break;
		case R.id.telLayout:
			if(!Common.isEmpty(tel))
				 startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + tel)));
			break;
		}	 
	}
	

	

	

	@Override
	protected void ioHandler(Message msg) {
		   
	
		if (msg.what == DefaultConst.CMD_DP_INFO_QUERY_RESULT) {
			JSONObject data = ((JSONObject) msg.obj).optJSONObject("member");
			if(null == data || 0==data.optLong("USRID")){
				showToast("未查询到此号码相关诚信信息！");
				this.finish();
				return;
			}
			renderData(data);
		}
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	private void renderData(JSONObject data) {
		//base转图片
//		String picture = data.optString("idCardPhoto");
//		if(!Common.isEmpty(picture)){
//			pictureImageView.setImageBitmap(ImageUtils.base642BigMap(picture));
//		}
		//照片
		//旧的方法由问题，不再使用
//		String url = DisplayUtil.toAbsoluteUrl(data.optString("photoAddr"));
//		if(!Common.isEmpty(url)){
//			ImageLoader.getInstance().displayImage(
//					url, 
//					pictureImageView,
//					options);
//		}
		if(!Common.isEmpty(data.optString("photoAddr")))
			new ImageTask(pictureImageView).execute(data.optString("photoAddr"));
		//姓名
		if(!Common.isEmpty(data.optString("REALNAME")))
			realNameText.setText(data.optString("REALNAME"));
		//诚信值
		if(0 != data.optLong("YSLACCOUNT"))
			yslAccountText.setText(Html.fromHtml("诚信值:<font color='#CC6600'>"+data.optString("YSLACCOUNT")+"</font>"));
		//公司名称
		if(!Common.isEmpty(data.optString("COMPANYNAME")))
			companyNameText.setText(data.optString("COMPANYNAME"));
		//发货星级展示	
		if(0 != data.optInt("SHIPPOINT"))
			fahuoxingjiBar.setRating( data.optInt("SHIPPOINT"));
		//发货次数
		if(0 != data.optInt("BSHIPCOUNT"))
			fahuoxingjiCountText.setText(data.optInt("BSHIPCOUNT")+"个评价");
		//承运星级
		if(0 != data.optInt("CARRYPOINT"))
			chengyunxingjiBar.setRating(data.optInt("CARRYPOINT"));
		
		//承运次数
		if(0 != data.optInt("BCARRYCOUNT"))
			chengyunxingjiCountText.setText(data.optInt("BCARRYCOUNT")+"个评价");
		//提示
		commentText.setText(data.optString("comment"));
		//会员等级
		if(!Common.isEmpty(data.optString("serviceName")))
			levelView.setText(data.optString("serviceName"));
		//是否认证
		if(!Common.isEmpty(data.optString("DCT_UA")) && "UCERT_PS".equals(data.optString("DCT_UA")))
			certView.setText("已认证");
		//公司
		companyNameText.setText(data.optString("COMPANYNAME"));
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
		if(!Common.isEmpty(mobile)){
			phoneStr=mobile;
		}else{
			phoneStr=tel;
		}
		//当地排名
		String cityName=data.optString("cityName");
		if(!Common.isEmpty(cityName)){
			paimingText.setText(cityName+"第"+data.optInt("localTrustLevel")+"名");	
		}
		//注册时间
		JSONObject t = data.optJSONObject("CREATE_TIME");
		if(!(t==null||t.length()==0)){			
			registerTimeText.setText(DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd hh:mm:ss"));

		}
		
		companylinesText.setText(data.optString("BUSILINES"));
		companyAddressText.setText(data.optString("BUSIADDRESS"));
		USRID = data.optLong("USRID");
		info1Layout.setVisibility(View.VISIBLE);
		
	}
}

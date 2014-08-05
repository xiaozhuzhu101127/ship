package com.topjet.shipper.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DC;
import com.topjet.shipper.util.DefaultConst;

public class DianPingAddActivity extends BaseActivity 
implements OnRatingBarChangeListener,OnFocusChangeListener{
	
	private String DCT_DPUC = null;
	
	private Long USRID = null;
	
	private TextView  firstTextView = null;
	private RatingBar zonghefen = null;
	private TextView  secondTextView = null;
	
	private RatingBar  taidulianghao = null;
	private RatingBar  yujiaheli = null;
	private RatingBar  huowuwanhao = null;
	private RatingBar  fahuojishi = null;
	private EditText  pingjiaContent   = null;
	
	private int first1 ;
	private int first2 ;
	private int first3 ;
	private int first4 ;
	private int 	total;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_dianpin_add);
		super.onCreate(savedInstanceState);	 
		
		common_title.setText("添加评价");
		common_main.setVisibility(View.VISIBLE);
		footer_cancle.setVisibility(View.VISIBLE);
		footer_delete.setVisibility(View.VISIBLE);
		footer_center.setText("添加评价");
		
		DCT_DPUC = getIntent().getExtras().getString(DC.DCT_DPUC); 
		USRID=getIntent().getExtras().getLong("USRID");
		firstTextView=(TextView)super.findViewById(R.id.firstPingjia);
		secondTextView=(TextView)super.findViewById(R.id.secondPingjia);
		if(DC.DPUC_CARRY.equals(DCT_DPUC)){
			firstTextView.setText("送货及时");
			secondTextView.setText("货物完好");
		}else{
			firstTextView.setText("如实描述");
			secondTextView.setText("结款及时");
		}
		taidulianghao  =(RatingBar)super.findViewById(R.id.taidulianghao);
		yujiaheli  =(RatingBar)super.findViewById(R.id.yujiaheli);
		huowuwanhao  =(RatingBar)super.findViewById(R.id.huowuwanhao);
		fahuojishi  =(RatingBar)super.findViewById(R.id.fahuojishi);
		zonghefen=(RatingBar)super.findViewById(R.id.zonghefen);
		
		taidulianghao.setOnRatingBarChangeListener(this);
		yujiaheli.setOnRatingBarChangeListener(this);
		huowuwanhao.setOnRatingBarChangeListener(this);
		fahuojishi.setOnRatingBarChangeListener(this);
		pingjiaContent=(EditText)super.findViewById(R.id.pingjiaContent);
		pingjiaContent.setOnFocusChangeListener(this);
		pingjiaContent.clearFocus();
		 first4 =Math.round(taidulianghao.getRating());
		 first3 =Math.round(yujiaheli.getRating());
		 first2 =Math.round(huowuwanhao.getRating());
		 first1 =Math.round(fahuojishi.getRating());
	     total=Math.round((first1+first2+first3+first4)/4);
		
	}
	
	
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		first4 =Math.round(taidulianghao.getRating());
		first3 =Math.round(yujiaheli.getRating());
		first2 =Math.round(huowuwanhao.getRating());
		first1 =Math.round(fahuojishi.getRating());
	     total=Math.round((first1+first2+first3+first4)/4);
		 zonghefen.setRating(total);
		
		
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText  editText =(EditText)v;
		String msg=editText.getText().toString();
		if(hasFocus){
			
			if("请输入评价内容".equals(msg)){
				editText.setText("");
			}
		}else{
			if(Common.isEmpty(msg)){
				editText.setText("请输入评价内容");
			}
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);  
	        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);    
		}
		
	}
	@Override
	protected void ioHandler(Message msg) {
		showToast("评价成功！");
		finish();
	}
	
	public void doFooterCenter(){
		submit();
	}

	private void submit() {
		String DPCONTENT=pingjiaContent.getText().toString();
		if("请输入评价内容".equals(DPCONTENT)){
			DPCONTENT="";
		}
		this.initProgress("加载中......");
		this.mLoadData.addDpDetail(baseHandler, DefaultConst.CMD_DP_INFO_ADD_RESULT,USRID,DCT_DPUC,
				first1,first2,first3,first4,total,DPCONTENT);
		
	}


	public String getDCT_DPUC() {
		return DCT_DPUC;
	}



	public void setDCT_DPUC(String dCT_DPUC) {
		DCT_DPUC = dCT_DPUC;
	}



	public TextView getFirstTextView() {
		return firstTextView;
	}



	public void setFirstTextView(TextView firstTextView) {
		this.firstTextView = firstTextView;
	}



	public TextView getSecondTextView() {
		return secondTextView;
	}



	public void setSecondTextView(TextView secondTextView) {
		this.secondTextView = secondTextView;
	}



	public RatingBar getTaidulianghao() {
		return taidulianghao;
	}



	public void setTaidulianghao(RatingBar taidulianghao) {
		this.taidulianghao = taidulianghao;
	}



	public RatingBar getYujiaheli() {
		return yujiaheli;
	}



	public void setYujiaheli(RatingBar yujiaheli) {
		this.yujiaheli = yujiaheli;
	}



	public RatingBar getHuowuwanhao() {
		return huowuwanhao;
	}



	public void setHuowuwanhao(RatingBar huowuwanhao) {
		this.huowuwanhao = huowuwanhao;
	}



	public RatingBar getFahuojishi() {
		return fahuojishi;
	}



	public void setFahuojishi(RatingBar fahuojishi) {
		this.fahuojishi = fahuojishi;
	}
	
	public void goodsDelete() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				zonghefen.setRating(0);
				taidulianghao.setRating(0);
				yujiaheli.setRating(0);
				huowuwanhao.setRating(0);
				fahuojishi.setRating(0);
				pingjiaContent.setText("");
			}
		});

	}
	

}

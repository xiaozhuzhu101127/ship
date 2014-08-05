package com.topjet.shipper.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DialogUtil;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.Receivable;
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.DefaultConst;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-13 上午11:07:31  
 * Description:发布货源
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsPublishActivity extends BaseActivity implements Receivable{
	
	private Map<String, Object> params;
	
	private TextView departView;
	private TextView targetView;
	private TextView typeView;
	private EditText typenumView;
	private TextView typedwView;
	private TextView tktypeView;
	private TextView tklengthView;
	private TextView paywayView;
	private TextView loadwayView;
	private EditText transpriceView;
	private EditText infopriceView;
	private TextView timeView;
	private EditText descView;
	
	private Bundle extras;
	private boolean update;
	private boolean copy;
	//TODO:1 自己发布 2修改的发布 3 同行货源的转发 4 历史货源的重发
	//TODO:请输入、请选择的样式，应该和选择完不一样，看能不能输入完之后在click时间中改更改
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_publish);
		super.onCreate(savedInstanceState);	
		extras = getIntent().getExtras();
		if(null != extras){
			update = extras.getBoolean("update");
			copy = extras.getBoolean("copy");
		}
		initView();
	}
	private void initView() {
		params = new HashMap<String, Object>();
		common_title.setText(DefaultConst.GOODPUBLISH);
		common_main.setVisibility(View.VISIBLE);
		footer_center.setText(DefaultConst.PUBLISHNOW);
		footer_delete.setVisibility(View.VISIBLE);
		footer_cancle.setVisibility(View.VISIBLE);
		
		departView = (TextView) findViewById(R.id.goods_publish_depart);
		targetView = (TextView) findViewById(R.id.goods_publish_target);
		typeView = (TextView) findViewById(R.id.goods_publish_type);
		typenumView = (EditText) findViewById(R.id.goods_publish_typenum);
		typedwView = (TextView) findViewById(R.id.goods_publish_type_dw);
		tktypeView = (TextView) findViewById(R.id.goods_publish_tktype);
		tklengthView = (TextView) findViewById(R.id.goods_publish_tklength);
		paywayView = (TextView) findViewById(R.id.goods_publish_payway);
		loadwayView = (TextView) findViewById(R.id.goods_publish_loadway);
		transpriceView = (EditText) findViewById(R.id.goods_publish_transprice);
		infopriceView = (EditText) findViewById(R.id.goods_publish_infoprice);
		timeView = (TextView) findViewById(R.id.goods_publish_gsloaddate);
		descView = (EditText) findViewById(R.id.goods_publish_desc);
		//跳转自详情页的修改
		if(update){
			JSONObject goods = null;
			try {
				goods = new JSONObject((String)extras.get("goods"));
			} catch (JSONException e) {
			}
			initGsInfo(goods);
			return;
		}
		//来自网上货源的转发和历史货源的重发（信息不全，所以再查询一次）
		if(copy){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("GsId", extras.get("GsId"));
			mLoadData.goodsDetail(baseHandler, DefaultConst.CMD_GOODS_DETAIL, params);
			return;
		}
		//默认值
		departView.setText(app.getLocation());
		departView.setTag(app.getLocationCode());
		typeView.setTag("GSTYPE_GENERAL");
		typedwView.setTag("DON");
		tktypeView.setTag("TT_COMMON");
		paywayView.setTag("PAYWAY_COD");
		loadwayView.setTag("GSLOAD_NOW");
		
	}
	public void click(View v){
		switch (v.getId()) {
		//始发地
		case R.id.goods_publish_depart:
			picking = departView;
			popProvincePicker();
			break;
		//目的地
		case R.id.goods_publish_target:
			picking = targetView;
			popProvincePicker();
			break;
		//货物类型
		case R.id.goods_publish_type:
			picking = typeView;
			popGsTypePicker();
			break;
		//单位
		case R.id.goods_publish_type_dw:
			picking = typedwView;
			popGoodsDwPicker();
			break;
		//车型
		case R.id.goods_publish_tktype:
			picking = tktypeView;
			popTkTypePicker();
			break;
		//车长
		case R.id.goods_publish_tklength:
			picking = tklengthView;
			popTkLengPicker();
			break;
		//付款方式
		case R.id.goods_publish_payway:
			picking = paywayView;
			popPayWayPicker();
			break;
		//装卸方式
		case R.id.goods_publish_loadway:
			picking = loadwayView;
			popGsLoadWayPicker();
			break;
		//装货时间
		case R.id.goods_publish_gsloaddate:
			showTime();
			break;
		}
	}
	private void showTime(){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener dateListener =  
			    new DatePickerDialog.OnDateSetListener() { 
			        @Override 
			        public void onDateSet(DatePicker datePicker,  
			                int year, int month, int dayOfMonth) { 
			             //Calendar月份是从0开始,所以month要加1 
			        	String time = year + "年" +  
					             (month+1) + "月" + dayOfMonth + "日";
			        	timeView.setText(time); 
			        	timeView.setTag(time);
			        } 
			    }; 
		Dialog dialog = new DatePickerDialog(this, dateListener, 
                calendar.get(Calendar.YEAR), 
                calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH)); 
		dialog.show();
	}
	@Override
	protected void ioHandler(Message msg) {
		if(msg.what == DefaultConst.CMD_GOODS_DETAIL){
			JSONObject data = (JSONObject) msg.obj;
			JSONObject goods = data.optJSONObject("goodsDetail");
			initGsInfo(goods);
			return;
		}
		showToast("发布成功！");
		startActivity(new Intent(this, GoodsTodayListActivity.class));
	}
	public void doFooterCenter() {
		//目的地
		if(!Common.isEmpty((String)targetView.getTag())){
			params.put("Target", targetView.getTag());
		}else {
			showToast("目的地不能为空!");
			return;
		}
			
		//出发地
		if(!Common.isEmpty((String)departView.getTag())){
			params.put("Depart", departView.getTag());
		}else {
			showToast("出发地不能为空!");
			return;
		}
		//货物类型
		if(!Common.isEmpty((String)typeView.getTag()))
			params.put("GSType", typeView.getTag());
		
		String typenum = typenumView.getText().toString();
		if(!Common.isEmpty(typenum)){
			if("CM".equals((String)typedwView.getTag())){
				params.put("GSUNIT", typenum);//体积
			}else{
				params.put("GSSCALE", typenum);//重量
			}
		}
		//车型
		if(!Common.isEmpty((String)tktypeView.getTag()))
			params.put("TKType", tktypeView.getTag());
		
		//车长
		if(!Common.isEmpty((String)tklengthView.getTag())){
			params.put("TKLen", tklengthView.getTag());
		}
		//付款方式
		if(!Common.isEmpty((String)paywayView.getTag()))
			params.put("PayWay", paywayView.getTag());
		
		//装货方式
		if(!Common.isEmpty((String)loadwayView.getTag()))
			params.put("GSLOAD", loadwayView.getTag());
		
		//运费
		if(!Common.isEmpty(transpriceView.getText().toString())){
			params.put("TRANSPORTPRICE", transpriceView.getText().toString());
		}
		//信息费
		if(!Common.isEmpty(infopriceView.getText().toString())){
			params.put("GSINFOPRICE", infopriceView.getText().toString());
		}
		//时间
		if(!Common.isEmpty((String)timeView.getTag())){
			params.put("GSLOADDATE",(String)timeView.getTag());
		}
		//说明
		if(!Common.isEmpty(descView.getText().toString())){
			params.put("GSDESC", descView.getText().toString());
		}
		mLoadData.publish(baseHandler, DefaultConst.CMD_GOODS_PUBLISH, params);
	}
	@Override
	public void receive(int type, Object... datas) {
		if(showing != null){
			showing.dismiss();
		}
		//市级是不限
		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
			//城市选择不限的时候显示省份
			return;
		}
		picking.setText((String)datas[1]);
		picking.setTag(datas[0]);
		//是省份的时候，继续展示城市
		if(DialogUtil.isProvinceDialog(showing) && !Dict.BLANK.equals(datas[0])){
			showing = DialogUtil.showCityDialog(this, (String)datas[0]);
			return;
		}
	}
	
	//删除货物
	public void goodsDelete() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				departView.setText("请选择");
				targetView.setText("请选择");
				typeView.setText("普货");
				typeView.setTag("GSTYPE_GENERAL");
				typedwView.setText("吨");
				typedwView.setTag("DON");
				tktypeView.setText("普通车");
				tktypeView.setTag("TT_COMMON");
				tklengthView.setText("车长不限");
				paywayView.setText("货到付款");
				paywayView.setTag("PAYWAY_COD");
				loadwayView.setText("一装一卸");
				loadwayView.setTag("GSLOAD_NOW");
				transpriceView.setText("请输入");
				infopriceView.setText("请输入");
				timeView.setText("请选择");
				descView.setText("请输入");
			}
		});
	}
	private void initGsInfo(JSONObject goods){
		//出发地
		departView.setText(Dict.getCityName(goods.optString("CT_DEPART")));
		departView.setTag(goods.optString("CT_DEPART"));
		
		//目的地
		targetView.setText(Dict.getCityName(goods.optString("CT_TARGET")));
		targetView.setTag(goods.optString("CT_TARGET"));
		
		if(!Common.isEmpty(goods.optString("GSTYPE"))){
			typeView.setText(Dict.getGsType(goods.optString("GSTYPE")));
			typeView.setTag(goods.optString("GSTYPE"));
		}else {
			typeView.setText("普货");
			typeView.setTag("GSTYPE_GENERAL");
		}
		
		if(!Common.isEmpty(goods.optString("GSSCALE"))){//重量
			typenumView.setText(goods.optString("GSSCALE"));
			typedwView.setText("吨");
			typedwView.setTag("DON");
		}else if(!Common.isEmpty(goods.optString("GSUNIT"))) {//体积
			typenumView.setText(goods.optString("GSUNIT"));
			typedwView.setText("立方");
			typedwView.setTag("CM");
		}else{
			typenumView.setText("");
			typedwView.setText("吨");
			typedwView.setTag("DON");
		}
		
		if(!Common.isEmpty(goods.optString("TKTYPE"))){
			tktypeView.setText(Dict.getTruckType(goods.optString("TKTYPE")));
			tktypeView.setTag(goods.optString("TKTYPE"));
		}else {
			tktypeView.setText("普通车");
			tktypeView.setTag("TT_COMMON");
		}
		
		if(!Common.isEmpty(goods.optString("TKLEN"))){
			tklengthView.setText(Dict.getTruckLength(goods.optString("TKLEN")));
			tklengthView.setTag(goods.optString("TKLEN"));
		}
		
		if(!Common.isEmpty(goods.optString("DCT_PAYWAY"))){
			paywayView.setText(Dict.getPayWay(goods.optString("DCT_PAYWAY")));
			paywayView.setTag(goods.optString("DCT_PAYWAY"));
		}else {
			paywayView.setText("货到付款");
			paywayView.setTag("PAYWAY_COD");
		}
		
		if(!Common.isEmpty(goods.optString("DCT_GSLOAD"))){
			loadwayView.setText(Dict.getGsLoad(goods.optString("DCT_GSLOAD")));
			loadwayView.setTag(goods.optString("DCT_GSLOAD"));
		}else {
			loadwayView.setText("一装一卸");
			loadwayView.setTag("GSLOAD_NOW");
		}
		
		if(!Common.isEmpty(goods.optString("TRANSPORTPRICE"))){
			transpriceView.setText(goods.optString("TRANSPORTPRICE"));
		}
		
		if(!Common.isEmpty(goods.optString("GSINFOPRICE"))){
			infopriceView.setText(goods.optString("GSINFOPRICE"));
		}
		//TODO:貌似详情里没有这个参数
		if(!Common.isEmpty(goods.optString("GSLOADDATE"))){
			timeView.setText(goods.optString("GSLOADDATE"));
			timeView.setTag(goods.optString("GSLOADDATE"));
		}
		
		if(!Common.isEmpty(goods.optString("GSDESC"))){
			descView.setText(goods.optString("GSDESC"));
		}
	}
}

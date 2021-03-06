package com.topjet.shipper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DialogUtil;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.Receivable;
 
/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-26 上午10:39:49  
 * Description:车源搜索
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class TruckSearchActivity extends BaseActivity implements Receivable{
	
	private TextView departView;
	
	private TextView targetView;
	
	private TextView typeView; 
	
	private TextView lenView;
	
	private ListView listView;
	
	private ImageView noImageView;
	
	private Button delButton;
	
	private ArrayAdapter<String> adapter;
	
	//搜索条件
	private String searchHis;
	
	private String[] his ;
	
	private ArrayList<String> data = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_truck_search);
		super.onCreate(savedInstanceState);	
		initView();
	}
	private void initView() {
		common_title.setText("车源搜索");
		title_place.setVisibility(View.VISIBLE);
		footer_center.setText("立即搜索");
		footer_delete.setVisibility(View.VISIBLE);
		footer_cancle.setVisibility(View.VISIBLE);
		title_place.setText(app.getLocation());
		
		
		departView = (TextView) findViewById(R.id.goods_search_depart);
		targetView = (TextView) findViewById(R.id.goods_search_target);
		departView.setText(app.getLocation());
		typeView = (TextView) findViewById(R.id.goods_search_tktype);
		lenView = (TextView) findViewById(R.id.goods_search_tklen);
		listView = (ListView) findViewById(R.id.search_history_listView);
		noImageView = (ImageView) findViewById(R.id.nosearch);
		delButton = (Button) findViewById(R.id.delhissearch);
		
		searchHis = shareHelper.getSearchTK();
		if(null == searchHis){
			noImageView.setVisibility(View.VISIBLE);
			delButton.setVisibility(View.GONE);
			return;
		}
		his = searchHis.split(";");
		for(int i=his.length;i>0;i--){
			data.add(his[i-1]);
		}
		adapter = new ArrayAdapter<String>(this, R.layout.item_search,data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String history = data.get(position);
				copyData(history);
			}
		});
	}
	private void copyData(final String history){
		Intent intent = new Intent(this,TruckRecommendListActivity.class);
		String[] search = history.split("\\+");
		String depart = search[0];
		String target = search[1];
		String type = search[2];
		String len = search[3];
		//直接查询
		if(!target.equals("全国"))
			intent.putExtra("TKTARGET", Dict.getCityCode(target));
		if(!depart.equals("全国"))
			intent.putExtra("citycode", Dict.getCityCode(depart));
		if(!type.equals("不限"))
			intent.putExtra("DCT_TT", Dict.getTkTypeCode(type));
		if(!len.equals("不限"))
			intent.putExtra("DCT_TKLEN", Dict.getTkLenCode(type));
		startActivity(intent);
		//回显数据
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				if(depart.equals("全国")){
//					departView.setText("请选择");
//				}else{
//					departView.setText(depart);
//					departView.setTag(Dict.getCityCode(depart));
//				}
//				if(target.equals("全国")){
//					targetView.setText("请选择");
//				}else{
//					targetView.setText(target);
//					targetView.setTag(Dict.getGsTypeCode(target));
//				}
//				typeView.setText(type);
//				if(!type.equals("不限"))
//					typeView.setTag(Dict.getCityCode(type));
					
//			}
//		});
	}
	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}
	public void click(View v){
		switch (v.getId()) {
		//始发地
		case R.id.goods_search_depart:
			picking = departView;
			popProvincePicker();
			break;
		//目的地
		case R.id.goods_search_target:
			picking = targetView;
			popProvincePicker();
			break;
		//车型
		case R.id.goods_search_tktype:
			picking = typeView;
			popTkTypePicker();
			break;
		//车长
		case R.id.goods_search_tklen:
			picking = lenView;
			popTkLengPicker();
			break;
		//清除搜索历史
		case R.id.delhissearch:
			deleteSearchHis();
			break;
		}
	}
	private void deleteSearchHis(){
		shareHelper.setSearchTK(null);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				delButton.setVisibility(View.GONE);
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
		});
		
	}
	public void doFooterCenter() {
		Intent intent = new Intent(this,TruckRecommendListActivity.class);
		StringBuffer sbf = new StringBuffer();
		//出发地
		String citycode = null;
		if(!Common.isEmpty((String)departView.getTag())){
			citycode = (String)departView.getTag();
			sbf.append(departView.getText());
		}else if(!Common.isEmpty(departView.getText().toString())){
			String depart = departView.getText().toString();
			if(depart.indexOf("市") != -1){
				depart = depart.substring(0, depart.length()-1);
			}
			citycode = app.getLocationCode();
			sbf.append(depart);
		}else {
			sbf.append("全国");
		}
		//要么是地级市，要么是百度的城市代码
		if(!(citycode.indexOf("_") != -1 || citycode.matches("[0-9]+"))){
			showToast("出发地必须是地级市，请重新选择。");
			return;
		}
		intent.putExtra("citycode", citycode);
		sbf.append("+");
		//目的地
		if(!Common.isEmpty((String)targetView.getTag())){
			intent.putExtra("TKTARGET", (String)targetView.getTag());
			sbf.append(targetView.getText());
		}else {
			sbf.append("全国");
		}
		sbf.append("+");	
		//车型
		if(!Common.isEmpty((String)typeView.getTag())){
			intent.putExtra("DCT_TT", (String)typeView.getTag());
			sbf.append(typeView.getText());
		}else {
			sbf.append("不限");
		}
		sbf.append("+");
		//车长
		if(!Common.isEmpty((String)lenView.getTag())){
			intent.putExtra("DCT_TKLEN", (String)lenView.getTag());
			sbf.append(lenView.getText());
		}else {
			sbf.append("不限");
		}
		if((Common.isEmpty((String)departView.getTag()) && Common.isEmpty((String)departView.getText())) && Common.isEmpty((String)targetView.getTag()) && Common.isEmpty((String)typeView.getTag()) && Common.isEmpty((String)lenView.getTag())){
			showToast("请设置搜索条件！");
			return;
		}
		//保存搜索条件
		if(null == searchHis){
			searchHis = new String();
			sbf.append(";");
			searchHis += sbf;
		}else{
			if(!containSear(searchHis,sbf.toString())){
				sbf.append(";");
				searchHis += sbf;
			}
		}
		
		shareHelper.setSearchTK(searchHis);
		startActivity(intent);
	}
	private boolean containSear(String searchHis,String str){
		boolean flag = false;
		String[] s = searchHis.split(";");
		for(int i=0;i<s.length;i++){
			if(str.equals(s[i]))
				flag = true;
		}
		return flag;
	}
	//清空搜索条件
	public void goodsDelete(){
		//不用runOnUiThread可以不？貌似不可以
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				departView.setText("请选择");
				targetView.setText("请选择");
				typeView.setText("不限");
				lenView.setText("不限");
			}
		});
	}
	@Override
	public void receive(int type, Object... datas) {
		if(showing != null){
			showing.dismiss();
		}
		//切换地区，查询车源
		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
			//城市选择不限的时候显示省份
			//不能查询，必须是城市
//			autoSerach((String)picking.getTag(),(String)picking.getText());
			if(isTitle)
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
	//假如是title的切换地区则查询周边空车
	private  void autoSerach(String depart,String city) {
		if(!isTitle)
			return;
		Intent intent = new Intent(this, TruckRecommendListActivity.class);
		intent.putExtra("Depart", depart);
		intent.putExtra("city", city);
		//周边空车
		intent.putExtra("around", true);
		startActivity(intent);
	}
}

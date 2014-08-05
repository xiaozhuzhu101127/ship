package com.topjet.shipper.adapter;

import java.util.Date;

import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.topjet.shipper.R;
import com.topjet.shipper.activity.DianPingDetailResultActivity;
import com.topjet.shipper.util.DC;
import com.topjet.shipper.util.DisplayUtil;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-30 下午4:25:38  
 * Description:评价列表
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class PingJiaDetailListAdapter extends LocalPhoneBookListAdapter
		implements OnClickListener {

	private DianPingDetailResultActivity context;

	public PingJiaDetailListAdapter(DianPingDetailResultActivity context) {
		super(context);
		this.context = context;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		JSONObject g = (JSONObject) getItem(position);
		ViewHolder viewHolder;
		if (null == view) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context.getApplicationContext())
					.inflate(R.layout.item_pingjia_detail_item, null);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		String DCT_DPUC=g.optString("DCT_DPUC");
		viewHolder.miaoshu1 = (TextView) view.findViewById(R.id.miaoshu1);
		viewHolder.miaoshu2 = (TextView) view.findViewById(R.id.miaoshu2);
		viewHolder.pingfen1 = (RatingBar) view.findViewById(R.id.pingfen1);
		viewHolder.pingfen2 = (RatingBar) view.findViewById(R.id.pingfen2);
		viewHolder.realName = (TextView) view.findViewById(R.id.realName);
		viewHolder.yslAccount = (TextView) view.findViewById(R.id.yslAccount);
		viewHolder.pingjiaContent = (TextView) view.findViewById(R.id.pingjiaContent);
		viewHolder.pingjiaShiJian = (TextView) view.findViewById(R.id.pingjiaShiJian);
		viewHolder.yunjiaheli = (RatingBar) view.findViewById(R.id.yunjiaheli);
		viewHolder.taidulianghao=(RatingBar)view.findViewById(R.id.taidulianghao);
		viewHolder.xingji=(RatingBar)view.findViewById(R.id.xingji);
		
		// 姓名
		viewHolder.realName.setText(g.optString("DPUSRREALNAME"));
		//诚信值
		viewHolder.yslAccount.setText(g.optString("DPYSLACCOUNT"));
		
		//评价内容
		viewHolder.pingjiaContent.setText(g.optString("DPCONTENT"));
		
		//评价时间
		JSONObject t = g.optJSONObject("CREATE_TIME");
		if(!(t==null||t.length()==0)){			
			viewHolder.pingjiaShiJian.setText(DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd hh:mm:ss"));

		}
		int total =g.optInt("POINT");
		viewHolder.xingji.setRating(total);
		//货运方
		if(DC.DPUC_SHIP.equals(DCT_DPUC)){
			viewHolder.miaoshu1.setText("如实描述:");
			viewHolder.miaoshu2.setText("结款及时:");
			viewHolder.pingfen1.setRating(g.optInt("TRUTHPOINT"));
			viewHolder.pingfen2.setRating(g.optInt("PAYMENTPOINT"));
		}else{
			//承运方
			viewHolder.miaoshu1.setText("送货及时:");
			viewHolder.miaoshu2.setText("货物完好:");
			viewHolder.pingfen1.setRating(g.optInt("DELIVERYPOINT"));
			viewHolder.pingfen2.setRating(g.optInt("GOODSPOINT"));
		}
		//运价合理
		viewHolder.yunjiaheli.setRating(g.optInt("PRICEPOINT"));
		//带度良好
		viewHolder.taidulianghao.setRating(g.optInt("ATTITUDEPOINT"));
		return view;
	}

	static class ViewHolder {
		TextView realName, yslAccount,
		pingjiaContent, pingjiaShiJian,
		miaoshu1,miaoshu2;
		RatingBar  xingji,pingfen1,pingfen2,yunjiaheli,taidulianghao;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}

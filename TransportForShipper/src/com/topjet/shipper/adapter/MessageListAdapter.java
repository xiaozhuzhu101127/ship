package com.topjet.shipper.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.audividi.imageloading.core.DisplayImageOptions;
import com.audividi.imageloading.core.ImageLoader;
import com.topjet.shipper.R;
import com.topjet.shipper.model.MessageInfo;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DisplayUtil;


/**
 * 
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-28 上午10:54:20  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MessageListAdapter
extends ArrayBaseAdapter<MessageInfo> {
 
	DisplayImageOptions options;
	public MessageListAdapter(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		options = new DisplayImageOptions.Builder()
		// 默认图片
		.showStubImage(R.drawable.twitter_photo)
		.showImageForEmptyUri(R.drawable.twitter_photo)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			HoldView holdView = null;
		if (null != convertView){
			holdView = (HoldView)convertView.getTag();			
		}
		else{
			convertView = mContext.getLayoutInflater().inflate(R.layout.item_message_item, null);
			holdView = new HoldView();
			convertView.setTag(holdView);
		}
		
		holdView.item_twitter_photo = (ImageView)convertView.findViewById(R.id.item_twitter_photo);
		holdView.item_twitter_name
		= (TextView)convertView.findViewById(R.id.item_twitter_name);
		holdView.item_twitter_credit
		= (TextView)convertView.findViewById(R.id.item_twitter_credit);
		holdView.item_twitter_info
		= (TextView)convertView.findViewById(R.id.item_twitter_info);
		holdView.item_twitter_content
		= (TextView)convertView.findViewById(R.id.item_twitter_content);
 
		
		holdView.item_twitter_photo = (ImageView)convertView.findViewById(R.id.item_twitter_photo);
		holdView.item_twitter_name = (TextView)convertView.findViewById(R.id.item_twitter_name);
		holdView.item_twitter_credit = (TextView)convertView.findViewById(R.id.item_twitter_credit);
		holdView.item_twitter_info= (TextView)convertView.findViewById(R.id.item_twitter_info);
		holdView.item_twitter_content = (TextView)convertView.findViewById(R.id.item_twitter_content);
		holdView.item_msg_mobile = (TextView)convertView.findViewById(R.id.item_msg_mobile);
	 
		MessageInfo messageInfo =(MessageInfo)getItem(position);
		if (!Common.isEmpty(messageInfo.photo)){
			ImageLoader.getInstance().displayImage(
					messageInfo.photo, 
					holdView.item_twitter_photo,
					options);
		}		
		holdView.item_twitter_name.setText(Common.isEmpty(messageInfo.name)?"":messageInfo.name);
		holdView.item_twitter_credit.setText("诚信值："+DisplayUtil.renderNumber(messageInfo.credit));
		holdView.item_twitter_info.setText(messageInfo.info);
		holdView.item_twitter_content.setText(messageInfo.content);
 
		holdView.item_msg_mobile.setText(messageInfo.mobile);
		 
		return convertView;	 
	}
	
	static class HoldView{
		public ImageView item_twitter_photo;	 
		public TextView item_twitter_name ,
						item_twitter_credit,
						item_twitter_info,
						item_twitter_content,
						item_msg_mobile;		
	}

	 
}

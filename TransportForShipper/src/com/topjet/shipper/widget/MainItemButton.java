package com.topjet.shipper.widget;

import com.topjet.shipper.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-27 下午2:50:04  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class MainItemButton extends LinearLayout {

	public MainItemButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.HORIZONTAL);
		View v = LayoutInflater.from(context).inflate(R.layout.widget_main_item, this, true);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainItemView, 0, 0);
		int icon;
		String labelBig;
		String labelSmall;
		try {
			icon = a.getResourceId(R.styleable.MainItemView_icon, -1);
			labelBig = a.getString(R.styleable.MainItemView_labelBig);
			labelSmall = a.getString(R.styleable.MainItemView_labelSmall);
		} finally {
			a.recycle();
		}
		ImageView iconView = (ImageView) v.findViewById(R.id.widget_main_item_icon);
		TextView labelBigView = (TextView) v.findViewById(R.id.widget_main_item_label_big);
		TextView labelSmallView = (TextView) v.findViewById(R.id.widget_main_item_label_small);
		iconView.setImageResource(icon);
		labelBigView.setText(labelBig);
		labelSmallView.setText(labelSmall);
	}
}

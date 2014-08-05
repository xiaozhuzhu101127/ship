package com.topjet.shipper.widget;

import com.topjet.shipper.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-25 上午8:27:20  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class CountDownButton extends Button {

	private String label;
	private int countDown;
	private int _countDown;
	private String countDownLabel;
	private Handler countDownHandler;
	private Handler updateHandler;
	private Runnable countDownTask;
	public CountDownButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownButton, 0, 0);
		try {
			countDown = a.getInt(R.styleable.CountDownButton_countDown, 0);
			countDownLabel = a.getString(R.styleable.CountDownButton_countDownLabel);
		} finally {
			a.recycle();
		}
		label = this.getText().toString();
		countDownTask = new Runnable(){

			@Override
			public void run() {
				_countDown--;
				updateHandler.sendEmptyMessage(0);
			}

		};
		countDownHandler = new Handler(){};
		updateHandler = new CountDownButtonUpdateHandler(this);
	}

	public void countDown(){
		if(countDown <= 0){
			return;
		}
		this.setEnabled(false);
		_countDown = countDown;
		_countDown();
	}

	public void reset(){
		_countDown = 0;
		this.setEnabled(true);
	}

	private void _countDown(){
		countDownHandler.postDelayed(countDownTask, 1000);
	}

	public void showParticularLabel(){
		if(_countDown <= 0){
			this.setEnabled(true);
			setText(label);
			return;
		}
		setText(countDownLabel.replace("%", _countDown + ""));
		_countDown();
	}
	
	class CountDownButtonUpdateHandler extends Handler {
		private CountDownButton btn;

		public CountDownButtonUpdateHandler(CountDownButton btn) {
			this.btn = btn;
		}

		@Override
		public void handleMessage(Message msg) {
			btn.showParticularLabel();
		}
	}
}

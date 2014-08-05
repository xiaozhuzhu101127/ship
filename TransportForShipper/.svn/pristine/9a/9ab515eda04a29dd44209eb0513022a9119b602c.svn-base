package com.topjet.shipper.dialog;

import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
 
import com.topjet.shipper.R;
import com.topjet.shipper.adapter.MapAdapter;

public class CommonDialog extends Dialog {

	private Window window = null;
	private MapAdapter adapter;
	private int layoutResID;
	
	private String sTvName="";
	
	public void setTvName(String sTvName){
		this.sTvName = sTvName;
	}

	public CommonDialog(Context context, int layoutResID, int requestCode) {
		super(context);
		this.layoutResID = layoutResID;
		this.adapter = new MapAdapter(context, requestCode);
	}

	public CommonDialog(Context context, int layoutResID, int requestCode, Map<String, String> data) {
		super(context);
		this.layoutResID = layoutResID;
		this.adapter = new MapAdapter(context, requestCode, data);
	}

	public void setData(Map<String, String> data) {
		this.adapter.setData(data);
	}

	public void notifyDataSetChanged() {
		this.adapter.notifyDataSetChanged();
	}

	public void showDialog() {
		showDialog(0, 0);
	}

	public void showDialog(int x, int y) {
		setContentView(layoutResID);

		windowDeploy(x, y);

		// 设置触摸对话框以外的地方取消对话框
		setCanceledOnTouchOutside(true);
		show();
	}

	// 设置窗口显示
	public void windowDeploy(int x, int y) {
		window = getWindow(); // 得到对话框
		window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
		window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // 设置对话框背景为透明
		WindowManager.LayoutParams wl = window.getAttributes();
		// 根据x，y坐标设置窗口需要显示的位置
		wl.x = x; // x小于0左移，大于0右移
		wl.y = y; // y小于0上移，大于0下移
		// wl.alpha = 0.6f; //设置透明度
		// wl.gravity = Gravity.BOTTOM; //设置重力
		window.setAttributes(wl);
		GridView grid = (GridView) window.findViewById(R.id.gridview);
		grid.setAdapter(adapter);
		TextView tvName = (TextView) window.findViewById(R.id.tv_title_name);
		if (!"".equals(tvName)) tvName.setText(sTvName);
	}
}


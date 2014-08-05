package com.topjet.shipper.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.MyApplication;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;

public class PasswordActivity extends BaseActivity implements OnClickListener{
	
	private TextView tv_titleView;
	
	private EditText  editText;
	
	private Button  btn;
	
	String	mPassword ="";
	
	private Dialog mDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_password);		
		super.onCreate(savedInstanceState);
		editText = (EditText)super.findViewById(R.id.new_password);			
		common_title.setText("密码修改");
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.recommend_send:
			submit();
			break;
		case R.id.score_shop_dialog_cancel:		
			startActivity(new Intent(this,LoginActivity.class));
			finish();
			break;
		case R.id.score_shop_dialog_dial:		
			startActivity(new Intent(this,MainActivity.class));
			finish();
			break;
		}
		
	}
	
	
	public void submit(){
		
		mPassword= editText.getText().toString().trim();
		if (Common.isEmpty(mPassword)) {
			Common.showToast("密码不能为空！", this);		 
			editText.requestFocus();
			return;
		}
		if (mPassword.length()<6||mPassword.length()>8) {
			Common.showToast("请输入6-8位数字密码！", this);		 
			editText.requestFocus();
			return;
		}
		initProgress("正在提交数据，请稍后");
		mLoadData.updatePassword(baseHandler, DefaultConst.CMD_UPDATE_PASSWORD,  mPassword);
		
	}

	@Override
	protected void ioHandler(Message msg) {
		switch(msg.what){
		case DefaultConst.CMD_UPDATE_PASSWORD:	
			MyApplication.getInstance().getShareHelper().setPassword(mPassword);
			//提醒用户
			if (null == mDialog) {
				mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.dialog_score_shop);
				TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);
				view.setText("密码修改成功！");
				TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
				text1.setText("重新登录");
				text1.setOnClickListener(this);
				TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
				text2.setText("返回");
				text2.setOnClickListener(this);
			}
			mDialog.show();
			
			
			
			break;
		
		}

	}

	@Override
	protected void doError(Message msg) {
		Common.showToast((String)msg.obj,this);

	}

}

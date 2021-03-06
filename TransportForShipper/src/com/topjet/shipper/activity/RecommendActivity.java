package com.topjet.shipper.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.PhoneValidator;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-6-6 上午9:58:08  
 * Description:推荐给朋友
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class RecommendActivity extends BaseActivity implements OnClickListener{

	static final int PICK_CONTACT_REQUEST = 1;
	static final int SEND_SMS_REQUEST = 2;

	AutoCompleteTextView mInputView;
	EditText mContentView;
	AlertDialog mCallLogDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_recommend);
		super.onCreate(savedInstanceState);
		
		common_title.setText("推荐给朋友");
		common_main.setVisibility(View.VISIBLE);
		mInputView = (AutoCompleteTextView) findViewById(R.id.recommend_input);
		mContentView = (EditText) findViewById(R.id.recommend_content);
		findViewById(R.id.recommend_from_contact_btn).setOnClickListener(this);
		findViewById(R.id.recommend_send).setOnClickListener(this);
		final ArrayList<String> list = getContacts(this);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item_autocomplete,list);
		mInputView.setAdapter(adapter);
		mInputView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String  text  = ((TextView)view).getText().toString();
				mInputView.setText(Common.getNumber(text));
				hideKeyboard(mInputView);
			}
		});
	}
	@Override
	protected void ioHandler(Message msg) {
		
	}

	@Override
	protected void doError(Message msg) {
		
	}
	private void pickNumberFromContact() {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		intent.setType(Phone.CONTENT_TYPE);
		startActivityForResult(intent, PICK_CONTACT_REQUEST);
	}

	private void sendSMS(){
		String mobile = mInputView.getText().toString().trim().replace(" ", "");
		if(!PhoneValidator.validateMobile(mobile)){
			Common.showToast("手机号码格式不正确！",this);
			mInputView.requestFocus();
			return;
		}
		String content = mContentView.getText().toString().trim();
		if(Common.isEmpty(content)){
			Common.showToast("请输入短信内容！",this);
			mContentView.requestFocus();
			return;
		}
		Uri uri = Uri.parse("smsto:" + mobile); 
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri); 
        smsIntent.putExtra("sms_body", content); 
//        startActivity(smsIntent); 
//        sendSuc();
        startActivityForResult(smsIntent, SEND_SMS_REQUEST);
	}
	public void sendSuc(){
		Dialog dialog = new AlertDialog.Builder(this).setTitle("推荐成功")
				.setMessage("感谢您的推荐，我们将赠送20积分。")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		dialog.show();
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.recommend_from_contact_btn:
			pickNumberFromContact();
			break;
		case R.id.recommend_send:
			sendSMS();
			break;
		case R.id.recommend_from_history_btn:
			pickNumberFromHistory();
			break;
		case R.id.recommend_delete:
			mInputView.setText("");
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == PICK_CONTACT_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// Get the URI that points to the selected contact
				Uri contactUri = data.getData();
				// We only need the NUMBER column, because there will be only
				// one row in the result
				String[] projection = { Phone.NUMBER };

				// Perform the query on the contact to get the NUMBER column
				// We don't need a selection or sort order (there's only one
				// result for the given URI)
				// CAUTION: The query() method should be called from a separate
				// thread to avoid blocking
				// your app's UI thread. (For simplicity of the sample, this
				// code doesn't do that.)
				// Consider using CursorLoader to perform the query.
				Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
				cursor.moveToFirst();

				// Retrieve the phone number from the NUMBER column
				int column = cursor.getColumnIndex(Phone.NUMBER);
				String number = cursor.getString(column);

				mInputView.setText(number);
			}
		}
		if (requestCode == SEND_SMS_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) 
				sendSuc();
		}
	}
	private void pickNumberFromHistory() {
		String[] callLogFields = { android.provider.CallLog.Calls._ID, android.provider.CallLog.Calls.NUMBER,
				android.provider.CallLog.Calls.CACHED_NAME /*
															 * im not using the
															 * name but you can
															 */};
		String viaOrder = android.provider.CallLog.Calls.DATE + " DESC";
		String WHERE = android.provider.CallLog.Calls.NUMBER + " >0"; /*
																	 * filter
																	 * out
																	 * private
																	 * /unknown
																	 * numbers
																	 */

		final Cursor callLog_cursor = getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI,
				callLogFields, WHERE, null, viaOrder);

		AlertDialog.Builder callLogDialogBuilder = new AlertDialog.Builder(this);

		android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int item) {
				callLog_cursor.moveToPosition(item);

				String number = callLog_cursor.getString(callLog_cursor
						.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
				mInputView.setText(number);
				mCallLogDialog.dismiss();
				callLog_cursor.close();
			}
		};
		callLogDialogBuilder.setCursor(callLog_cursor, listener, android.provider.CallLog.Calls.NUMBER);
		callLogDialogBuilder.setTitle("选择一个号码");
		mCallLogDialog = callLogDialogBuilder.create();
		mCallLogDialog.show();
	}
	/**
     * 获取所有联系人内容
     * @param context
     * @param address
     * @return
     */
    public static ArrayList<String> getContacts(Context context) {
    	ArrayList<String> contactList = new ArrayList<String>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
            	StringBuilder sb = new StringBuilder("  ");
                String contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    sb.append(name);
                
                Cursor phones = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    String phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    // 添加Phone的信息
                    sb.append(" ").append(phoneNumber);
                    contactList.add(sb.toString());
                }
                phones.close();
                
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

}

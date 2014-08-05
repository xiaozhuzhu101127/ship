package com.topjet.shipper.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
 
import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.PhoneValidator;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-5-27 下午3:57:52  
 * Description:诚信查询
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class CreditQueryActivity extends BaseActivity {

	static final int PICK_CONTACT_REQUEST = 1;
	static final int PICK_CALL_LOG_REQUEST = 2;

	AutoCompleteTextView mInputView;
	AlertDialog mCallLogDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_credit_query);
		super.onCreate(savedInstanceState);
		
		
		common_title.setText("诚信查询");
		common_main.setVisibility(View.VISIBLE);
		mInputView = (AutoCompleteTextView) findViewById(R.id.credit_query_input);		
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

	private void pickNumberFromContact() {
		Intent intent = new Intent(Intent.ACTION_PICK, 
				ContactsContract.Contacts.CONTENT_URI);
		intent.setType(Phone.CONTENT_TYPE);
		startActivityForResult(intent, PICK_CONTACT_REQUEST);
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

	private void submit() {
		String number = mInputView.getText().toString().trim();
		if(Common.isEmpty(number)){
			Common.showToast("号码不能为空！",this);
			mInputView.requestFocus();
			return;
		}
		if (!PhoneValidator.validatePhoneOrMobile(number) 
			&& !PhoneValidator.validPhone400(number)) {
			Common.showToast("电话或手机号码格式不正确！",this);
			mInputView.requestFocus();
			return;
		}
		Intent intent = new Intent(CreditQueryActivity.this, CreditResultActivity.class);
		intent.putExtra("number", number);
		startActivity(intent);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.credit_query_from_contact_btn:
			pickNumberFromContact();
			break;
		case R.id.credit_query_from_history_btn:
			pickNumberFromHistory();
			break;
		case R.id.credit_query_submit_btn:
			submit();
			break;
		case R.id.credit_delete:
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
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
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

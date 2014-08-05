package com.topjet.shipper.db;

import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

import com.topjet.shipper.MyApplication;
import com.topjet.shipper.model.ComplaintInfo;
import com.topjet.shipper.model.MessageInfo;
import com.topjet.shipper.model.PhoneInfo;
import com.topjet.shipper.util.CommonUtils;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.DisplayUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

 

public class Database {
	private static Database instance = null;
	private  SQLiteDatabase mSQLiteDatabase = null;
	private  DaoHelper mDaoHelper = null;
	private Context mContext;
	private MyApplication app;
	private Database(Context context) throws Exception 
	{
		mContext = context;
		mDaoHelper = new DaoHelper(context);
		mSQLiteDatabase = mDaoHelper.getWritableDatabase();
		app = MyApplication.getInstance();
	}
	public static Database getInstance(Context context) throws Exception 
	{
		if (null ==instance)
		{
			instance = new Database(context);
		}
		return instance;
	}
	
	public   void closeDB(){
		if(mSQLiteDatabase!=null){
			mSQLiteDatabase.close();
			mSQLiteDatabase = null;
		}
		if(mDaoHelper!=null){
			mDaoHelper.close();
			mDaoHelper = null;
		} 
	}
	
	public PhoneInfo queryPhoneInfo(String number) {
		Cursor c = null;
		try {
			c = mSQLiteDatabase.query(true, DbConfig.TAB_PHONE_INFO,
					new String[] { "number", 
					"location", "name", "credit", 
					"complaint", "verified", "companyName" },
					"number=?", new String[] { number }, null, null, null, null);
			int result = c.getCount();
			if (0 == result) {
				return null;
			}
			c.moveToNext();
			return new PhoneInfo(c.getString(0), c.getShort(5) == 1, c.getString(6), c.getString(1), c.getString(2),
					Integer.parseInt(c.getString(3)), Integer.parseInt(c.getString(4)));
		} finally {
			if (null != c) {
				c.close();
			}
		}
		/*Cursor c=null;
		try {
		String sql = "select * from "+DbConfig.TAB_PHONE_INFO+" where number='"+number+"'";
		  c =  mSQLiteDatabase.rawQuery(sql, null);	 
		if (c.moveToFirst()){
			return new PhoneInfo(
					c.getString(c.getColumnIndex("number"))
					, c.getShort(c.getColumnIndex("verified")) == 1, 
					c.getString(c.getColumnIndex("companyName")), 
					c.getString(c.getColumnIndex("location")),
					c.getString(c.getColumnIndex("name")),
					Integer.parseInt(c.getString(c.getColumnIndex("credit"))),
					Integer.parseInt(c.getString(c.getColumnIndex("complaint"))));
		}
		} finally {
			if (null != c) {
				c.close();
			}
		}
		return null;*/
	}
	
	private void clearFilterResult(){
		mSQLiteDatabase.delete(DbConfig.TAB_FILTRT_RESULT, null, null);
	}
	
	public void updateFilterList(ArrayList<ComplaintInfo>  complaintInfos) {
		clearFilterResult();
		
		int i =0;
		for(ComplaintInfo complaintInfo : complaintInfos){
			ContentValues row = new ContentValues();
			row.put("no", i++);
			row.put("number",complaintInfo.number);
			row.put("status", complaintInfo.status);
			row.put("url", complaintInfo.url);
			mSQLiteDatabase.insert(DbConfig.TAB_FILTRT_RESULT, null, row);
		} 
	}

	/**
	 * 清空消息列表
	 */
	public int clearMessageList() {
		 
		return mSQLiteDatabase.delete(DbConfig.TAB_MESSAGE_INFO, null, null);
	}


	public void saveOrUpdatePhoneInfo(PhoneInfo info) throws Exception {
		ContentValues values = new ContentValues();
		values.put("number", info.getNumber());
		values.put("location", info.getLocation());
		values.put("name", info.getName());
		values.put("credit", info.getCredit()+"");
		values.put("complaint", info.getComplaint()+"");
		values.put("verified", info.isVerified() ? 1 : 0);
		values.put("companyName", info.getCompanyName()+"");		
		 
//		int row = mSQLiteDatabase.update(DbConfig.TAB_PHONE_INFO, values, "number=?", new String[] { info.getNumber() });
//		if (row == 0) {
			mSQLiteDatabase.insert(DbConfig.TAB_PHONE_INFO, null, values);
//		}
	 
	}
	
 
	public void addMessage(ArrayList<MessageInfo> msgInfos){
		for(MessageInfo messageInfo:msgInfos){
			ContentValues values = new ContentValues();
			values.put("photo", messageInfo.photo);
			values.put("name", messageInfo.name);
			values.put("credit", messageInfo.credit);
			values.put("info", messageInfo.info);
			values.put("content",messageInfo.content);
			values.put("mobile", messageInfo.mobile);	 
			values.put("userID",MyApplication.getInstance().getShareHelper().getUsername());	 	
			
			mSQLiteDatabase.insert( DbConfig.TAB_MESSAGE_INFO, null, values);
		}		
	}
	
	public ArrayList<MessageInfo> getMessageInfos(String length,String type,String city){
		ArrayList<MessageInfo>  msgInfos =new ArrayList<MessageInfo> ();
		StringBuilder sbd = new StringBuilder();
		if(CommonUtils.notEmpty(length)){
			sbd.append("  content like '%");
			sbd.append(length);
			sbd.append("%' ");
		}
		if(CommonUtils.notEmpty(type)){
			if(sbd.length()>0){
				sbd.append(" and ");
			}
			sbd.append("  content like '%");
			sbd.append(type);
			sbd.append("%' ");
		}
		if(CommonUtils.notEmpty(city)){
			if(sbd.length()>0){
				sbd.append(" and ");
			}
			sbd.append("  content like '%");
			sbd.append(city);
			sbd.append("%' ");
		}
		String sql="";
		if(sbd.length()>0){
			sql = "select * from "+DbConfig.TAB_MESSAGE_INFO+" " +
					" where userID ='"+MyApplication.getInstance().getShareHelper().getUsername()+"'"+" and ( "+sbd.toString()+")"+ " order by _id desc";
		  
		}else{
			sql = "select * from "+DbConfig.TAB_MESSAGE_INFO+" " +
					" where userID ='"+MyApplication.getInstance().getShareHelper().getUsername()+"' order by _id desc";
		   }
		  Cursor cursor = mSQLiteDatabase.rawQuery(sql,null);
		cursor.moveToFirst(); 
		while (!cursor.isAfterLast())
		{
			try
			{ 
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.photo = cursor.getString(cursor.getColumnIndex("photo"));
				messageInfo.name = cursor.getString(cursor.getColumnIndex("name"));
				messageInfo.credit = cursor.getString(cursor.getColumnIndex("credit"));
				messageInfo.info = cursor.getString(cursor.getColumnIndex("info"));
				messageInfo.content = cursor.getString(cursor.getColumnIndex("content"));
				messageInfo.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
				msgInfos.add(messageInfo);
				cursor.moveToNext();
			}
			catch(Exception ex)
			{
				continue;
			}
		}
		cursor.close();	 
		return msgInfos;
	}
	
	
}

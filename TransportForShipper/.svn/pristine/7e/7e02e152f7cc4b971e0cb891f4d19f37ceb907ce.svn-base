package com.topjet.shipper.db;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaoHelper 
extends SQLiteOpenHelper{
	private static final int DB_VERSION = 7;
	private static final String DB_NAME = "CREDIBLE_NUMBER";
 
 
	public DaoHelper(Context context ) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String  tabComplaint = "CREATE TABLE IF NOT EXISTS " 
				+ DbConfig.TAB_FILTRT_RESULT
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "no INTEGER, " 
				+"number Text, " 
				+"status Text, " 
				+"url Text);";		
		db.execSQL(tabComplaint);
		
		String  tabPhoneInfo = "CREATE TABLE IF NOT EXISTS " 
				+ DbConfig.TAB_PHONE_INFO
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "			 
				+"number Text, " 
				+"location Text,"
				+"name Text,"
				+"credit Text,"
				+"complaint Text,"
				+"verified SHORT,"
				+"companyName Text);";		
		db.execSQL(tabPhoneInfo);
		
		String  tabMessageInfo = "CREATE TABLE IF NOT EXISTS " 
				+ DbConfig.TAB_MESSAGE_INFO
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "	
				+"userID Text, " 
				+"photo Text, " 
				+"name Text,"
				+"credit Text,"
				+"info Text,"
				+"content Text,"				 
				+"mobile Text);";		
		db.execSQL(tabMessageInfo);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion>6){
			String DROP_TABLE_COMPLAINT = "DROP TABLE IF EXISTS filter_result";
			String DROP_TABLE_PHONE_INFO = "DROP TABLE IF EXISTS phone_info";
			String DROP_TABLE_MESSAGE_LIST = "DROP TABLE IF EXISTS message_list";
			db.execSQL(DROP_TABLE_COMPLAINT);
			db.execSQL(DROP_TABLE_PHONE_INFO);
			db.execSQL(DROP_TABLE_MESSAGE_LIST);			 
		}
	}
}

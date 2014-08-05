package com.topjet.shipper.util;

import java.util.Set;
import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareHelper {

	private SharedPreferences settings;
	private static ShareHelper instance;
	
	public static final String  TransportPrefsFileName      = "TransportPrefsFile";
 
	private static final String SERIAL_NUMBER = "SerialNumber";
	private static final String TRUCK_STATUS ="TruckStatus"; 
	private static final String CALLING_DIALOG_X ="CallingDialogX";
	private static final String CALLING_DIALOG_Y ="CallingDialogY";
	private static final String UPLOADED ="IsUpload";
	private static final String RECOMMEND_MOBILE ="RecommendMobile";
	private static final String LOCALE_NAME ="LocalName";
	private static final String YSLACCOUNT="Yslaccount";
	private static final String LOCALTRUSTLEVEL="localTrustLevel";
	private static final String LOCALE ="Local";
	private static final String USERTYPE ="userType";
	private static final String REALNAME ="Name";
	private static final String BASE64PHOTO ="BASE64PHOTO";
	private static final String USER_ID = "user_id";
	private static final String USERNAME = "login_username";
	private static final String PASSWORD = "Password";
	private static final String SESSION = "session";
	private static final String CHECKDEAL = "checkdeal";
	private static final String CALLPRICE = "callPrice";
	private static final String ISLOGIN = "isLogin";
	private static final String DCT_UT = "dct_ut";
	private static final String ISCERT = "isCert";
	//搜索货源
	private static final String SEARCHGOODS = "searchGoods";
	//搜索车源
	private static final String SEARCHTK = "searchTK";
	//是否提交行驶证
	private static final String ISDRIVERPIC = "isDriverPic";
	private Editor editor;
	@SuppressLint("CommitPrefEdits")
	private ShareHelper(Context context) {
		super();
		
		String fileName = TransportPrefsFileName;
		
		settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		editor = settings.edit();
	}
	
	public SharedPreferences getSharedPreferences() {
		return settings;
	}
	
	public Editor getEditor() {
		return editor;
	}
	
	
	public void clearAllData(){
		editor.clear();
		editor.commit();
	}

	public static ShareHelper getInstance(Context context) {
		if (instance == null)
			instance = new ShareHelper(context);
		return instance;
	}
	
	public  void setSession(String session){
		putString(SESSION, session);
	}
	
	public String getSession(){
		return getString(SESSION,"");
	}
	/**
	 * 车源搜索历史
	 * @param 
	 */
	public  void setSearchTK(String searchHis){
		putString(SEARCHTK, searchHis);
	}

	/**
	 * 车源搜索历史
	 * @param 
	 */
	
	public  String getSearchTK(){
		return getString(SEARCHTK, null);
	}
	/**
	 * 货源搜索历史
	 * @param 
	 */
	public  void setSearchGoods(String searchHis){
		putString(SEARCHGOODS, searchHis);
	}

	/**
	 * 货源搜索历史
	 * @param 
	 */
	
	public  String getSearchGoods(){
		return getString(SEARCHGOODS, null);
	}
//	/**
//	 * 搜索历史
//	 * @param 
//	 */
//	public  void setSearchHis(TreeSet<String> searchHis){
//		putSet(SEARCHHIS, searchHis);
//	}
//
//	/**
//	 * 搜索历史
//	 * @param 
//	 */
//	
//	public  Set<String> getSearchHis(){
//		return getSet(SEARCHHIS, null);
//	}
	/**
	 * 货运状态
	 * @param full true=满载，false=空车
	 */
	public  void setTruckStatus(boolean full){
		putBoolean(TRUCK_STATUS, full);
	}

	/**
	 * 货运状态
	 * @param full true=满载，false=空车
	 */
	
	public  boolean getTruckStatus(){
		return getBoolean(TRUCK_STATUS, false);
	}
 
	/**
	 * 来电弹窗X坐标
	 * 
	 * @param x
	 */
	public  void setCallingDialogX(int x) {
		putInt(CALLING_DIALOG_X, x);
	}

	/**
	 * 来电弹窗X坐标
	 * 
	 * @param defValue
	 * @return
	 */
	public  int getCallingDialogX(int defValue) {
		return getInt(CALLING_DIALOG_X, defValue);
	}

	/**
	 * 来电弹窗Y坐标
	 * 
	 * @param y
	 */
	public  void setCallingDialogY(int y) {
		 putInt(CALLING_DIALOG_Y, y);
	}

	/**
	 * 来电弹窗Y坐标
	 * 
	 * @param defValue
	 * @return
	 */
	public  int getCallingDialogY(int defValue) {
		return getInt(CALLING_DIALOG_Y, defValue);
	}

	/**
	 * 是否已经上传过通讯录
	 * 
	 * @param uploaded
	 */
	public  void setUploaded(boolean uploaded) {
		putBoolean(UPLOADED, uploaded);
	}

	/**
	 * 是否已经上传过通讯录
	 * 
	 * @return
	 */
	public  boolean isUploaded() {
		return getBoolean(UPLOADED, false);
	}

	/**
	 * 推荐人手机号
	 * 
	 * @param mobile
	 */
	public  void setRecommendMobile(String mobile) {
		putString(RECOMMEND_MOBILE, mobile);
	}

	/**
	 * 推荐人手机号
	 * 
	 * @return
	 */
	public  String getRecommendMobile() {
		return getString(RECOMMEND_MOBILE, "");
	}

	/**
	 * 常驻地
	 * 
	 * @param start
	 */
	public  void setLocaleName(String localeName) {
		putString(LOCALE_NAME, localeName);
	}

	/**
	 * 常驻地
	 * 
	 * @return
	 */
	public  String getLocaleName() {
		return getString(LOCALE_NAME, "");
	}
	
	
	/**
	 * 诚信值
	 * 
	 * @return
	 */
	public  void setYslAccount(Long credit) {
		putLong(YSLACCOUNT, credit);
	}

	/**
	 *诚信值
	 * 
	 * @return
	 */
	public  Long getYslAccount() {
		return getLong(YSLACCOUNT, 0);
	}
	
	
	
	
	/**
	 * 诚信值排名
	 * 
	 * @return
	 */
	public  void setLocalTrustLevel(Long localTrustLevel) {
		putLong(LOCALTRUSTLEVEL, localTrustLevel);
	}

	/**
	 *诚信值排名
	 * 
	 * @return
	 */
	public  Long getLocalTrustLevel() {
		return getLong(LOCALTRUSTLEVEL, 0);
	}

	/**
	 * 常驻地代码
	 * 
	 * @param start
	 */
	public  void setLocale(String locale) {
		putString(LOCALE, locale);
	}

	/**
	 * 常驻地代码
	 * 
	 * @return
	 */
	public  String getLocale() {
		return getString(LOCALE, "");
	}
	
	
	
	/**
	 * 用户类型
	 * 
	 * @param start
	 */
	public  void setUserType(String userType) {
		putString(USERTYPE, userType);
	}

	/**
	 * 用户类型
	 * 
	 * @return
	 */
	public  String getUserType() {
		return getString(USERTYPE, "");
	}

	/**
	 * 用户名称
	 * 
	 * @param start
	 */
	public  void setBase64Photo(String base64Photo) {
		putString(BASE64PHOTO, base64Photo);
	}

	/**
	 * 用户名称
	 * 
	 * @return
	 */
	public  String getBase64Photo() {
		return getString(BASE64PHOTO, "");
	}
	
	
	/**
	 * 用户头像
	 * 
	 * @param start
	 */
	public  void setRealName(String realName) {
		putString(REALNAME, realName);
	}

	/**
	 * 用户头像
	 * 
	 * @return
	 */
	public  String getRealName() {
		return getString(REALNAME, "");
	}
 
	/**
	 * 是否轮询成交信息
	 * 
	 * @param uploaded
	 */
	public  void setCheckDeal(boolean checkDeal) {
		putBoolean(CHECKDEAL, checkDeal);
	}

	/**
	 * 是否轮询成交信息
	 * 
	 * @return
	 */
	public  boolean isCheckDeal() {
		return getBoolean(CHECKDEAL, false);
	}
	/**
	 * 是否报过价
	 * 
	 * @param 
	 */
	public  void setCallPrice(boolean callPrice) {
		putBoolean(CALLPRICE, callPrice);
	}

	/**
	 * 是否报过价
	 * 
	 * @return
	 */
	public  boolean isCallPrice() {
		return getBoolean(CALLPRICE, false);
	}
	/**
	 * 是否登录
	 * 
	 * @param
	 */
	public  void setLogin(boolean isLogin) {
		putBoolean(ISLOGIN, isLogin);
	}

	/**
	 * 是否登录
	 * 
	 * @return
	 */
	public  boolean isLogin() {
		return getBoolean(ISLOGIN, false);
	}
	/**
	 * 身份
	 * 
	 * @param
	 */
	public  void setDct_ut(String dct_ut) {
		putString(DCT_UT, dct_ut);
	}

	/**
	 * 身份
	 * 
	 * @return
	 */
	public  String getDct_ut() {
		return getString(DCT_UT, "");
	}
	/**
	 * 是否验证
	 * 
	 * @param
	 */
	public  void setCert(boolean isCert) {
		putBoolean(ISCERT, isCert);
	}

	/**
	 * 是否验证
	 * 
	 * @return
	 */
	public  boolean isCert() {
		return getBoolean(ISCERT, false);
	}
	/**
	 * 是否提交行驶证照片
	 * 
	 * @param
	 */
	public  void setisDriverPic(boolean isDriverPic) {
		putBoolean(ISDRIVERPIC, isDriverPic);
	}

	/**
	 * 是否提交行驶证照片
	 * 
	 * @return
	 */
	public  boolean isDriverPic() {
		return getBoolean(ISDRIVERPIC, false);
	}
	public void setUserId(Long userId) {
		putLong(USER_ID, userId);
	}

	public Long getUserId() {
		return getLong(USER_ID, -1);
	}

	
	/**
	 * 用户名
	 */
	public void setUsername(String username) {
		putString(USERNAME, username);
	}

	/**
	 * 用户名
	 */
	public String getUsername() {
		return getString(USERNAME, "");
	}

	/**
	 * 密码
	 */
	public void setPassword(String password) {
		putString(PASSWORD, password);
	}

	/**
	 * 密码
	 */
	public String getPassword() {
		return getString(PASSWORD, "");
	}

	 
	public boolean putSerialNumber(String value) {
		return putString(SERIAL_NUMBER,value);
	}
	
	public String getSerialNumber(){
		return getString(SERIAL_NUMBER,null);
	}
		

	public boolean putString(String key, String value) {
		return editor.putString(key, value).commit();
	}

	public String getString(String key, String defaultVaule) {
		return settings.getString(key, defaultVaule);
	}
	
	public boolean putLong(String key, long value) {
		return editor.putLong(key, value).commit();
	}
	
	public long getLong(String key, long defaultVaule) {
		return settings.getLong(key, defaultVaule);
	}
	
	public boolean putInt(String key, int value) {
		return editor.putInt(key, value).commit();
	}
	
	public int getInt(String key, int defaultVaule) {
		return settings.getInt(key, defaultVaule);
	}
	
	public boolean putBoolean(String key, boolean value) {
		return editor.putBoolean(key, value).commit();
	}
	
	public boolean getBoolean(String key, boolean defaultVaule) {
		return settings.getBoolean(key, defaultVaule);
	} 
	
	@SuppressLint("NewApi")
	public boolean putSet(String key, TreeSet<String> value) {
		return editor.putStringSet(key, value).commit();
	}
	
	@SuppressLint("NewApi")
	public Set<String> getSet(String key, Set<String> defaultVaule) {
		return settings.getStringSet(key, defaultVaule);
	} 
	
}

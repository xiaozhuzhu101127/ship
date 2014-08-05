package com.topjet.shipper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.MyApplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.webkit.URLUtil;
import android.widget.Toast;

public class Common {

	private static MessageDigest md5 = null;
	private static TelephonyManager tm;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	private static ShareHelper helper;
  
	public static void readContacts(StringBuilder names ,StringBuilder numbers ) {	 
	
		ContentResolver cr = MyApplication.getInstance()
				.getApplicationContext().getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						String phoneNo = pCur.getString(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						names.append(name).append("$end$");
						numbers.append(phoneNo).append("$end$");
					}
					pCur.close();
				}
			}
		} 
	}
	
	public static String getVersionName(){
		//读取版本信息	 
		PackageManager packageManager = MyApplication.getInstance()
				.getApplicationContext().getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(MyApplication.getInstance()
					.getApplicationContext().getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			return "未知";
		}
	}
	
	public static String getMobile(){
		TelephonyManager tMgr = (TelephonyManager) MyApplication.getInstance().getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tMgr.getLine1Number() == null ? "" : tMgr.getLine1Number();
	}

	public static NetworkInfo getNetworkInfo() {
		ConnectivityManager	mConnectivityManager = (ConnectivityManager) 
				MyApplication.getInstance()
				.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnectivityManager != null) 	return mConnectivityManager.getActiveNetworkInfo();
		return null;		
	}
	
	public  static boolean checkUrlAvailable(String url) {
		if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) 	return true;

		return false;
	}
	
	public static String join(String[] array, String separator){
		StringBuilder buf = new StringBuilder(array.length * 16);

        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
	}



	/**
	 * 判断SDCARD是否有效
	 * 
	 * @return
	 */
	public static boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(status))
			return true;
		return false;
	}

	/**
	 * @Title: EmptyToNUll
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param value
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2012-11-13 上午11:40:34
	 */
	public static String EmptyToNUll(String value) {
		String result = value;
		if (isEmpty(result)) {
			result = null;
		}
		return result;
	}

	/**
	 * 判断String为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return (string == null || "".equalsIgnoreCase(string.trim()));
	}
	public static String weightOrVolume(String weight, String volume){
		if(!isEmpty(weight)){
			return weight+"吨";
		}
		return volume+"立方米";
	}

	/**
	 * 创建文件目录
	 * 
	 * @param dir
	 *            如：/sdcard/log
	 * @return
	 */
	public static File mkDir(String dir) {
		if (isSDCARDMounted()) {
			File file = null;
			try {
				file = new File(dir);
				if (!file.exists()) {
					file.mkdirs();
				}
				return file;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取当前时间日期
	 * 
	 * @param format
	 *            自定义格式,例：yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static String getFormatTime(String format) {
		Date date = new Date();
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat(format);
		String time = df.format(date);
		return time;
	}

	/**
	 * 弹出提示信息
	 * 
	 * @param msg
	 */
	public static void showToast(String msg, Context context) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(int res, Context context) {
		Toast.makeText(context, context.getString(res), Toast.LENGTH_SHORT)
				.show();
	}

	public static String MD5(String str) {
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = str.getBytes();
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return null if device ID is not available.
	 */
	public static String getDeviceId() {
//		if (null == tm ) tm  = (TelephonyManager) TransportApplication.getInstance().getApplicationContext()
//				.getSystemService(Context.TELEPHONY_SERVICE);
		return getDeviceID(MyApplication.getInstance().getApplicationContext());
	}

	public static String getDeviceSoftwareVersion() {
		if (null == tm ) tm  = (TelephonyManager) MyApplication.getInstance().getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceSoftwareVersion();
	}

	
	
	public static String getImsi(){
	    TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);  
        String IMSI = telephonyManager.getSubscriberId();  
        return IMSI;
	}
	/**
	 * get serial
	 * 
	 * @return
	 */
	public static String getSerialNumber() {
		String serial = null;
//		try {
//			Class<?> class1 = Class.forName("android.os.SystemProperties");
//			Method get = class1.getMethod("get", String.class);
//			serial = get.invoke(class1, "ro.serialno").toString();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (serial == null || serial.length() <= 1) {
			String imei = getDeviceId();
			if (imei == null || imei.length() <= 1) {
				serial = helper.getSerialNumber();
				if (serial == null) {
					String number = String.valueOf(System.currentTimeMillis());		
					helper.putSerialNumber(number);// save
				}
			} else {
				serial = imei;
			}
		}
		return serial;
	}

	/**
	 * NETWORK_TYPE_GPRS GPRS 1 NETWORK_TYPE_EDGE EDGE 2 NETWORK_TYPE_UMTS UMTS
	 * 3 NETWORK_TYPE_HSDPA HSDPA 8 NETWORK_TYPE_HSUPA HSUPA 9 NETWORK_TYPE_HSPA
	 * HSPA 10 NETWORK_TYPE_CDMA CDMA,IS95A �� IS95B. 4 NETWORK_TYPE_EVDO_0
	 * EVDO, revision 0. 5 NETWORK_TYPE_EVDO_A EVDO, revision A. 6
	 * NETWORK_TYPE_1xRTT 1xRTT 7
	 */
	public static int getNetworkType() {
		if (null == tm ) tm  = (TelephonyManager) MyApplication.getInstance().getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkType();
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	private static String[] stars;

	 
	public static String encodeUTF(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String encode(String text, String enc) {
		try {
			return URLEncoder.encode(text, enc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getTime() {
		return dateFormat.format(new Date());
	}

	protected static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");

	public static String newDateTime() {
		return sdf.format(new Date());
	}

	public static String picToText(String text, int id) {
		StringBuffer buffer = new StringBuffer(text);
		buffer.append("<img src=\"");
		buffer.append(id);
		buffer.append("\"/>");
		return buffer.toString();
	}

	public static boolean checkNet(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activieNet = conMan.getActiveNetworkInfo();
		if (activieNet != null && activieNet.isConnected())
			if (activieNet.getState() == NetworkInfo.State.CONNECTED)
				return true;
		return false;
	}

	public static int dip2px(float dipValue, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(float pxValue, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static boolean StringToBool(String data, boolean defVal) {
		if (data.toLowerCase().equals("true")) {
			return true;
		} else if (data.toLowerCase().equals("false")) {
			return false;
		} else {
			return defVal;
		}
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return sb.toString();
	}

	public static String encodeChinese(String info) {

		try {
			return URLEncoder.encode(info, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return info;
		}
	}
	
	public static String bytes2Hex(byte[] bytes) {
		String des = "";
		String tmp = null;
		for(int i=0; i<bytes.length; i++) {
			tmp = (Integer.toHexString(bytes[i] & 0xFF));
			if(tmp.length()==1) des+="0";
			des+=tmp;
		}
		
		return des;
	}
 
public static String getDeviceID(Context context) {
		//imei
		TelephonyManager tManager = (TelephonyManager) context
                		.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tManager!=null? tManager.getDeviceId() : null;
		//pseudo-unique ID
		String pseudo = "35"+Build.BRAND.length()%10   + Build.BOARD.length()%10
							+Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10
							+Build.DISPLAY.length()%10 + Build.HOST.length()%10
							+Build.ID.length()%10      + Build.MANUFACTURER.length()%10
							+Build.MODEL.length()%10   + Build.PRODUCT.length()%10
							+Build.TAGS.length()%10    + Build.TYPE.length()%10
							+Build.USER.length()%10;
		
		//android ID
		String androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		//wlan mac
		WifiManager wManager = (WifiManager) context
        				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wManager!=null ? wManager.getConnectionInfo() : null;
		String wlanmac = wifiInfo!=null ?  wifiInfo.getMacAddress() : null;
		
		String deviceID = imei + pseudo + androidID + wlanmac;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(deviceID.getBytes());
			deviceID = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return deviceID;
	}
	//check dct_ut
	public static boolean checkDct_ut(){
		String dct_ut = ShareHelper.getInstance(BaseActivity.context).getDct_ut();
		Boolean isCert = ShareHelper.getInstance(BaseActivity.context).isCert();
		if(!dct_ut.equals("UT_TW") || !isCert )
			return false;
		return true;
	}
	//去掉三级代码和2级中的不限
	public static String splitBX(String code){
		if(Common.isEmpty(code))
			return "";
		String[] str = code.split("_");
		if(str.length>2){
			if(!Dict.isMunicipalCity(str[0]))
			return str[0]+"_"+str[1];
		}
		if(str.length==2){
			if(str[1].equals("BX"))
			return str[0];
		}
		return code;
	}
	//字符串中取出数字
	public static String getNumber(String str){
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	public static boolean noInstallDriver(){
		PackageManager packageManager = MyApplication.getInstance()
				.getApplicationContext().getPackageManager();
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
	
		for(int i=0;i<packages.size();i++) {
		PackageInfo packageInfo = packages.get(i);
//		String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
//		String packageName = packageInfo.packageName;
		if("com.topjet.crediblenumber".equals(packageInfo.packageName)){
			return false;
			}
		}
		return true;
	}
}

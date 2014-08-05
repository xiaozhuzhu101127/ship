package com.topjet.shipper.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.view.View;
import android.widget.TextView;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-28 下午2:34:04  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class DisplayUtil {
	public static String toAbsoluteUrl(String url){
		if(null == url){
			return url;
		}
		if(url.startsWith("http")){
			return url;
		}
		return AppConifg.WEB_ADDRESS + url;
	}

	public static boolean isSameCity(String city1, String city2){
		if(city1 == null || city2 == null){
			return false;
		}
		if(city1.equals(city2)){
			return true;
		}
		if(city1.startsWith(city2)){
			return true;
		}
		if(city2.startsWith(city1)){
			return true;
		}
		if(city1.length() >= 2 && city2.length() >= 2){
			if(city1.substring(0, 2).equals(city2.substring(0, 2))){
				return true;
			}
		}
		return false;
	}

	/**
	 * 去掉城市名称中的后缀“市”
	 * @param city
	 * @return
	 */
	public static String removeCitySuffix(String city){
		if(Common.isEmpty(city)){
			return city;
		}
		if(city.endsWith("市")){
			return city.substring(0, city.length() - 1);
		}
		return city;
	}

	/**
	 * 格式化Date
	 * @param date
	 * @param format
	 * @return
	 */
	public static String renderDate(Date date, String format){
		return new SimpleDateFormat(format, Locale.CHINA).format(date);
	}

	/**
	 * 大于10000时省略4个0用w代替
	 * @param credit
	 * @return
	 */
	public static String renderNumber(int credit){
		if(credit > 9999){
			return credit/10000 + "w";
		}
		return credit + "";
	}
	
	public static String renderNumber(String _credit){
		int credit = Integer.parseInt(_credit);
		if(credit > 9999){
			return credit/10000 + "w";
		}
		return credit + "";
	}

	/**
	 * 将空字符或null替换为"--"
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str){
		if(Common.isEmpty(str)){
			return "--";
		}
		return str;
	}

	public static String renderPhoneAndMobile(String phone, String mobile){
		StringBuilder sb = new StringBuilder();
		if(!Common.isEmpty(mobile)){
			sb.append(mobile);
		}
		if(!Common.isEmpty(phone)){
			if(sb.length() > 0){
				sb.append(" ");
			}
			sb.append(phone);
		}
		return sb.toString();
	}

	/**
	 * 将文本设置到TextView中，如果文本为空则隐藏TextView
	 * @param view
	 * @param text
	 */
	public static void hideOnBlank(TextView view, String text){
		hideOnBlank(view, text, null);
	}

	/**
	 * 将文本设置到TextView中，如果文本为空则隐藏TextView
	 * @param view
	 * @param text
	 * @param prefix
	 */
	public static void hideOnBlank(TextView view, String str, String prefix){
		if(Common.isEmpty(str)){
			view.setVisibility(View.GONE);
			return;
		}
		view.setText(prefix == null?str:prefix + str);
	}

	/**
	 * 去掉.后缀
	 * @param str
	 * @return
	 */
	public static String removeFileExtension(String str){
		if(null == str){
			return str;
		}
		int p = str.lastIndexOf(".");
		if(p == -1){
			return str;
		}
		return str.substring(0, p);
	}

//
//	public static int getWeatherResourceId(String weather){
//		int resoid=R.drawable.s_2;
//		if(weather.indexOf("多云")!=-1 && weather.indexOf("晴")!=-1){//多云、晴
//            resoid=R.drawable.s_1;}
//        else if(weather.indexOf("多云")!=-1&&weather.indexOf("阴")!=-1){//多云、阴
//            resoid=R.drawable.s_2;}
//        else if(weather.indexOf("阴")!=-1&&weather.indexOf("雨")!=-1){//阴、雨
//            resoid=R.drawable.s_3;}
//        else if(weather.indexOf("晴")!=-1&&weather.indexOf("雨")!=-1){
//            resoid=R.drawable.s_12;}
//        else if(weather.indexOf("晴")!=-1&&weather.indexOf("雾")!=-1){
//            resoid=R.drawable.s_12;}
//        else if(weather.indexOf("晴")!=-1){resoid=R.drawable.s_13;}
//        else if(weather.indexOf("多云")!=-1){resoid=R.drawable.s_2;}
//        else if(weather.indexOf("阵雨")!=-1){resoid=R.drawable.s_3;}
//        else if(weather.indexOf("小雨")!=-1){resoid=R.drawable.s_3;}
//        else if(weather.indexOf("中雨")!=-1){resoid=R.drawable.s_4;}
//        else if(weather.indexOf("大雨")!=-1){resoid=R.drawable.s_5;}
//        else if(weather.indexOf("暴雨")!=-1){resoid=R.drawable.s_5;}
//        else if(weather.indexOf("冰雹")!=-1){resoid=R.drawable.s_6;}
//        else if(weather.indexOf("雷阵雨")!=-1){resoid=R.drawable.s_7;}
//        else if(weather.indexOf("小雪")!=-1){resoid=R.drawable.s_8;}
//        else if(weather.indexOf("中雪")!=-1){resoid=R.drawable.s_9;}
//        else if(weather.indexOf("大雪")!=-1){resoid=R.drawable.s_10;}
//        else if(weather.indexOf("暴雪")!=-1){resoid=R.drawable.s_10;}
//        else if(weather.indexOf("扬沙")!=-1){resoid=R.drawable.s_11;}
//        else if(weather.indexOf("沙尘")!=-1){resoid=R.drawable.s_11;}
//        else if(weather.indexOf("雾")!=-1){resoid=R.drawable.s_12;}
//		return resoid;
//	}
}

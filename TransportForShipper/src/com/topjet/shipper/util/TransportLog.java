package com.topjet.shipper.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
 
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
 
public class TransportLog {
	private static boolean        isDebug       = false;     
	private static BufferedWriter mBufferedWriter;
	private static final int      file_max_size = 1 * 1024 * 1024;                                                                                  // FIXME
	private static final String   dir           = Environment.getExternalStorageDirectory().getPath() + "/" + AppConifg.TRANSPORT_DIRECTORY_NAME + "log"; // FIXME
	
	/**
	 * 判断关联AndroidManifest.xml中的debug设置;
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isApplicationDebug(Context context) {
		try
		{
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			int flags = packageInfo.applicationInfo.flags;
			int debug = (flags & ApplicationInfo.FLAG_DEBUGGABLE);
			if (debug != 0)
			{
				// development mode
				isDebug = true;
			}
			else
			{
				// release mode
				isDebug = false;
			}
		}
		catch (NameNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isDebug;
	}
	
	
	
	/**
	 * 写文件
	 */
	private static void writeLogFile(String tag, String msg) {
	 
			if (mBufferedWriter == null && Common.isSDCARDMounted())
			{
				try
				{
					File mFile = new File(new StringBuilder(Common.mkDir(dir).getPath() + File.separator).append("log").append("_").append(new SimpleDateFormat("yyyyMMdd_HH").format(new Date())).append(".txt").toString());
					mBufferedWriter = new BufferedWriter(new FileWriter(mFile, true));
				}
				catch (IOException e)
				{
					e.printStackTrace();
					mBufferedWriter = null;
					return;
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:SSS");
			String buffer = sdf.format(new Date()) + ": " + tag + ": " + msg + "\n";
			try
			{
				mBufferedWriter.append(buffer);
				mBufferedWriter.flush();
			}
			catch (Exception e)
			{
				endWriteFile();
			}
		 
	}
	
	/**
	 * 结束记录文件
	 */
	public static void endWriteFile() {
		if (mBufferedWriter != null)
		{
			try
			{
				mBufferedWriter.flush();
				mBufferedWriter.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			mBufferedWriter = null;
		}
	}
	
	/**
	 * 销毁
	 */
	public static void destroy() {
		endWriteFile();
	}
	
	
	
	public static void v(String tag, String msg) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.v(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void v(String tag, String msg, Throwable thr) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.v(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void d(String tag, String msg) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.d(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void d(String tag, String msg, Throwable thr) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.d(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void i(String tag, String msg) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.i(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void i(String tag, String msg, Throwable thr) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.i(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void w(String tag, String msg) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.w(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void w(String tag, String msg, Throwable thr) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.w(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}
	
	public static void w(String tag, Throwable thr) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.w(tag, buildMessage(""), thr);
			 
		}
	}
	
	public static void e(String tag, String msg) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.e(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
			 
		}
	}
	
	public static void e(String tag, String msg, Throwable thr) {
		if (AppConifg.DEBUG)
		{
			android.util.Log.e(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
			 
		}
	}
	
	private static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
		String className = caller.getClassName();
		String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
		return new StringBuilder().append(simpleClassName).append(".").append(caller.getMethodName()).append("(").append(caller.getLineNumber()).append("):").append(msg).toString();
	}

}

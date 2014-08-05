package com.topjet.shipper.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

public class CrashHandler 
implements UncaughtExceptionHandler  {
	 private static final String   dir           = Environment.getExternalStorageDirectory().getPath() + "/" + AppConifg.TRANSPORT_DIRECTORY_NAME + "log"; // FIXME
	 private static final int   FILE_MAX_SIZE = 1 * 1024 * 1024;     
	 
	 //系统默认的UncaughtException处理类    
	 private Thread.UncaughtExceptionHandler mDefaultHandler;  
	 //CrashHandler实例   
	 private static CrashHandler INSTANCE = new CrashHandler();  
	 //程序的Context对象   
	 private Context mContext;  
	 //用来存储设备信息和异常信息   
	 private Map<String, String> infos = new HashMap<String, String>();  
	 
	 private static boolean        isDebug       = true;  
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (!handleException(ex) && mDefaultHandler != null) {  
			 //如果用户没有处理则让系统默认的异常处理器来处理  
			 mDefaultHandler.uncaughtException(thread, ex);  
		}
		else
		{
			 try {  Thread.sleep(3000);  
			 } catch (InterruptedException e) { 
			 }
			 //退出程序   
			 android.os.Process.killProcess(android.os.Process.myPid());  
			 System.exit(1);  
		}
	}
	
	private boolean handleException(Throwable ex) {  
		 if (ex == null)   return false;  
		 if (!isDebug)  return false;  
		//收集设备参数信息 
		 collectDeviceInfo(mContext);  
		//保存日志文件 
		 saveCrashInfo2File(ex);  
		 return true; 
	}

	/** 保证只有一个CrashHandler实例 */  
	private CrashHandler() { 
	   
	}
	
	public void collectDeviceInfo(Context ctx) { 
		try {
			PackageManager pm = ctx.getPackageManager();  
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
			if (pi != null) {
				 String versionName = pi.versionName == null ? "null" : pi.versionName; 
				 String versionCode = pi.versionCode + "";  
				 infos.put("versionName", versionName);  
				 infos.put("versionCode", versionCode);  
			}
		}catch (NameNotFoundException e) {  
			
		}
		Field[] fields = Build.class.getDeclaredFields(); 
		for (Field field : fields) {
			try { 
				 field.setAccessible(true);  
				 infos.put(field.getName(), field.get(null).toString());  
			}
			catch (Exception e) {  				 
			}
		}
		
	}
	/**
	 * 保存错误信息到文件中 
	 * @param ex
	 * @return
	 */
	private void saveCrashInfo2File(Throwable ex) {  
		StringBuffer sb = new StringBuffer();  
		for (Map.Entry<String, String> entry : infos.entrySet()) { 
			 String key = entry.getKey();  
			 String value = entry.getValue();  
			 sb.append(key + "=" + value + "\n");  
		}
		
		Writer writer = new StringWriter();  
		PrintWriter printWriter = new PrintWriter(writer);  
		ex.printStackTrace(printWriter);  
		Throwable cause = ex.getCause();  
		 while (cause != null) {  
			 cause.printStackTrace(printWriter);  
			 cause = cause.getCause();  
		 }
		 printWriter.close();  
		 String result = writer.toString();  
		 sb.append(result);
		 String error = sb.toString();
		 if (!Common.isEmpty(error))
	 	 { 
 
				BufferedWriter mBufferedWriter = null;
				try
				{
					File mFile = new File(new StringBuilder(Common.mkDir(dir).getPath() + File.separator).append("error_log").append("_").append(new SimpleDateFormat("yyyyMMdd_HH").format(new Date())).append(".txt").toString());
					if (mFile.length() > FILE_MAX_SIZE)
					{
						mFile.delete();
						mFile.createNewFile();
					}
					String time = ">>>>>>>>>>>>>>>>>> " + Common.getFormatTime("yyyy-MM-dd HH:mm:ss:SSS") + "\n";
					mBufferedWriter = new BufferedWriter(new FileWriter(mFile, true));
					mBufferedWriter.append(time);
					mBufferedWriter.append(error);
					mBufferedWriter.append("\n\n");
					mBufferedWriter.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (mBufferedWriter != null)
					{
						try
						{
							mBufferedWriter.close();
							mBufferedWriter = null;
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}				
			}
		}
	}
	 /** 获取CrashHandler实例 ,单例模式 */  
	public static CrashHandler getInstance() {
		return INSTANCE;
	}
	
	public void init(Context context) {
		 mContext = context;  
		//获取系统默认的UncaughtException处理器 
		 mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
		//设置该CrashHandler为程序的默认处理器 
		 Thread.setDefaultUncaughtExceptionHandler(this);  		 
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
		 
	}
}

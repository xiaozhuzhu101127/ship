package com.topjet.shipper.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpStatus;

import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;


import com.topjet.shipper.MyApplication;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.TransportLog;

public class HttpTask 
implements Runnable {
 
	private int timeout;
	private RequestType mRequestType = RequestType.POST;
	private String mUrl;
	private byte[] mRequestData;  
	private boolean isForeign = false; 

	private Handler handler;
	private int cmd;
	
	public enum RequestType {
		POST, GET
	}
	public HttpTask(Handler handler,int cmd){
		this.handler =  handler;
		this.cmd = cmd;
	}
	 
	public void initData(RequestType type,
			String url, byte[] requestdata, int timeout){
	
		this.mRequestType = type;
		this.mUrl = url;
		this.mRequestData = requestdata;
		this.timeout = timeout != -1 ? timeout : DefaultConst.DEFAULT_CONN_TIMEOUT;

	}
	
	public void initData(RequestType type,
			String url, byte[] requestdata, int timeout, boolean isForeign){
		this.mRequestType = type;
		this.mUrl = url;
		this.mRequestData = requestdata;
		this.timeout = timeout != -1 ? timeout : DefaultConst.DEFAULT_CONN_TIMEOUT;
		this.isForeign = isForeign;
	}

 
	private String addJSessionId(String url) {
		if (null != MyApplication.getInstance().getSessionId()) {
			int position = url.indexOf("?");
			if (position != -1) {
				url = new StringBuilder().append(url.substring(0, position)).append(";jsessionid=")
						.append(MyApplication.getInstance().getSessionId()).append(url.substring(position)).toString();
			} else {
				url = new StringBuilder().append(url).append(";jsessionid=").append(MyApplication.getInstance().getSessionId())
						.toString();
			}
		}
		return url;
	}

	@Override
	public void run() {		 
		if (!isForeign) mUrl = addJSessionId(encodeUrl(mUrl));
		TransportLog.i("HttpTask", "请求地址->"+mUrl);
		HttpURLConnection httpConnection = null;
		try { 
			if (!Common.checkUrlAvailable(mUrl)) {
				handler.sendEmptyMessage(DefaultConst.CMD_ERROR_NET_ADD_REQUEST);
				return;
			}
			httpConnection = getConnection(mUrl);	
			if(httpConnection==null){
				
				Message msg = new Message();
				msg.what = DefaultConst.CMD_ERROR_NET_RETURN_FAILED;
				msg.obj = "网络异常，请检查网络是否正常！";
				handler.sendMessage(msg); 	
	 
			}
			// 模拟
			if(isForeign){
				httpConnection.setDoOutput(true);
				httpConnection.setRequestProperty("Pragma", "no-cache");
				httpConnection.setRequestProperty("Cache-Control", "no-cache");
				httpConnection.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0");
			}

			// 添加请求头，标识为手机端的请求
			httpConnection.setRequestProperty("reqSource", "mobile");
			httpConnection.setConnectTimeout(timeout);
//			httpConnection.setReadTimeout(DefaultConst.DEFAULT_READ_TIMEOUT);
			if (mRequestType == RequestType.POST) {
				httpConnection.setRequestMethod("POST");
				httpConnection.setDoOutput(true);
				OutputStream ops = httpConnection.getOutputStream();
				if (mRequestData != null && mRequestData.length > 0) {
					ops.write(mRequestData);
					ops.flush();
				}
			} else {
				httpConnection.setRequestMethod("GET");
			}
			 
			httpConnection.connect();
		 
			int iStatusCode = httpConnection.getResponseCode();
		
			if (iStatusCode != HttpStatus.SC_OK 
			   && iStatusCode != HttpStatus.SC_PARTIAL_CONTENT) {			 
				handler.sendEmptyMessage(DefaultConst.CMD_ERROR_NET_RETURN_FAILED);
 				return;
			}
			 String val = httpConnection.getHeaderField("Content-Type");
			 //表示CMSS之类的，需要登录的界面，那么跳转过去
			 if(val.indexOf("text/html")>=0){
					Message msg = new Message();
					msg.what = DefaultConst.CMD_CMSS_NET_ADD_REQUEST;
					String realURL =httpConnection.getURL().toString();
					msg.obj=realURL;
					handler.sendMessage(msg); 			 
					return; 
			 }
			InputStream reader = httpConnection.getInputStream();
			String content = getResponseString(reader);			 
			JSONObject json = new JSONObject(content);
			
			if(isForeign){
				Message msg = new Message();
				msg.what = cmd;
				msg.obj = json;
				handler.sendMessage(msg);
				return;
			}
			if(json.optInt("succ") != 1){
				Message msg = new Message();
				msg.what = DefaultConst.CMD_ERROR_NET_RETURN_FAILED;
				msg.obj = json.optString("msg");
				handler.sendMessage(msg); 			 
				return;
			}			
			String sessionId = json.optString("id");
			if(!Common.isEmpty(sessionId)){
				MyApplication
				.getInstance()				 
				.setSessionId(sessionId);
			}
			if (cmd == DefaultConst.CMD_APK_UPDATE){				 
				Message msg = new Message();
				msg.what = cmd;
				msg.obj = json;
				handler.sendMessage(msg);
				return;
			}
			Object data = json.optJSONObject("result");
			if(null == data){
				data = json.optJSONArray("result");
			}
			if(null == data){
				data = json;
			}
			Message msg = new Message();
			msg.what = cmd;
			msg.obj = data;
			handler.sendMessage(msg);
			TransportLog.i("HttpTask", "请求返回"+content);
		} catch (Exception e) {
			TransportLog.e("HttpTask", "请求失败!", e);
			Message msg = new Message();
			msg.what = DefaultConst.CMD_ERROR_NET_RETURN_FAILED;
			msg.obj = "网络请求超时，请检查网络是否正常！";
			handler.sendMessage(msg); 	
 
		}
	}

	/**
	 * 为了防止url里面的图片名含有空格或其它不合理字符导致url非法的问题,将其进行转义
	 * 
	 * @param url
	 * @return
	 */
	private String encodeUrl(String url) {
		Uri tempUri = Uri.parse(url);
		String encodeUrl = new String(url);
		String oldPath = tempUri.getLastPathSegment();
		if (oldPath != null && oldPath.length() > 0) {
			String path = tempUri.getLastPathSegment();
			path = URLEncoder.encode(path);
			path = path.replace("+", "%20");
			encodeUrl = encodeUrl.replace(oldPath, path);
		}
		return encodeUrl;
	}

	private HttpURLConnection getConnection(String url) throws IOException {
		HttpURLConnection conn = null;
		String defaultHost = Proxy.getDefaultHost();
		String targetUrl = new String(url);
		int defaultPort = Proxy.getDefaultPort();
		URL hostUrl = null;
		NetworkInfo activeNetworkInfo = Common.getNetworkInfo();
		if (activeNetworkInfo == null||(activeNetworkInfo != null && !activeNetworkInfo.isAvailable())){ 
			return null;
		}
		int type = -1;
		String exrea = null;
		if (null != activeNetworkInfo) {
			type = activeNetworkInfo.getType();
			exrea = activeNetworkInfo.getExtraInfo();
		}
		if (type == ConnectivityManager.TYPE_MOBILE && defaultHost != null && defaultPort > 0) {
			if (exrea != null && (exrea.startsWith("cmwap") || exrea.startsWith("uniwap") || exrea.startsWith("3gwap"))) {
				String host = null;
				String path = null;
				int hostIndex = "http://".length();
				int pathIndex = targetUrl.indexOf('/', hostIndex);
				if (pathIndex < 0) {
					host = targetUrl.substring(hostIndex);
					path = "";
				} else {
					host = targetUrl.substring(hostIndex, pathIndex);
					path = targetUrl.substring(pathIndex);
				}
				conn = (HttpURLConnection) new URL("http://" + defaultHost + ":" + defaultPort + path).openConnection();
				conn.setRequestProperty("X-Online-Host", host);
			} else {
				// 1、某些机型extra会有null情况 并且有代理；
				// 2、APN_TYPE_CTWAP
				java.net.Proxy proxy = new java.net.Proxy(Type.HTTP, new InetSocketAddress(defaultHost, defaultPort));
				conn = (HttpURLConnection) new URL(targetUrl).openConnection(proxy);
			}
		} else { 
			// 非mobile或者没有默认代理地址的情况下，不走proxy
			hostUrl = new URL(url);
			conn = (HttpURLConnection) hostUrl.openConnection();
		}
		return conn;
	}

	/**
	 * 解压GZip数据作为字符串返回
	 * 
	 * @param in
	 * @return
	 */
	private String getResponseGZIPString(InputStream in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] iBuffer = new byte[1024];
		String json = null;
		int bytesRead = 0;
		try {
			GZIPInputStream gzipIs = new GZIPInputStream(in);
			while (true) {
				bytesRead = gzipIs.read(iBuffer);
				if (bytesRead <= 0) {
					break;
				}
				baos.write(iBuffer, 0, bytesRead);
			}
			json = new String(baos.toByteArray(), "utf-8");
		} catch (IOException e) {
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
				baos = null;
			}
		}
		return json;
	}

	/**
	 * 将流中数据作为字符串返回
	 * 
	 * @param in
	 */
	private String getResponseString(InputStream in) {
		String getStr = "";
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int iLength = 1024;
			int bytesRead = 0;
			byte[] buff = new byte[iLength];
			while (true) {
				bytesRead = in.read(buff);
				if (bytesRead < 1) {
					break;
				}
				baos.write(buff, 0, bytesRead);
			}
			getStr = new String(baos.toByteArray(), "utf-8");
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
				baos = null;
			}
		}
		return getStr;
	}
}

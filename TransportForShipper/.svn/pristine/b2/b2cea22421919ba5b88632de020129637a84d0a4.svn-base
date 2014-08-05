package com.topjet.shipper.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.Locale;

import com.topjet.shipper.MyApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
 

public class NetUtils {

	private static ConnectivityManager mConnectivityManager;
	static {
		mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
			.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public enum NetType {
		NET_WIFI,
		NET_3G,
		NET_2G,
		NET_UNKNOW,
		NET_NONE
	}

	/**
	 * 查看是否有网络
	 * 
	 * @return
	 */
	public static synchronized boolean isNetConnected() {
		NetworkInfo activeNetInfo = mConnectivityManager.getActiveNetworkInfo();
		return activeNetInfo == null ? false : activeNetInfo.isConnected();
	}

	public static synchronized NetType getNetType() {
		NetType iType = NetType.NET_NONE;
		NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NetType.NET_NONE;
		}
		int nType = networkInfo.getType();
		switch (nType) {
		case ConnectivityManager.TYPE_MOBILE:
			int subType = networkInfo.getSubtype();
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_CDMA:
				iType = NetType.NET_2G;
				break;
			default:
				iType = NetType.NET_3G;
				break;
			}
			break;
		case ConnectivityManager.TYPE_WIFI:
			iType = NetType.NET_WIFI;
			break;
		default:
			iType = NetType.NET_UNKNOW;
			break;
		}
		return iType;
	}

	public static String APN_TYPE_CTNET = "ctnet";
	public static String APN_TYPE_CTWAP = "ctwap";
	public static String APN_TYPE_CMNET = "cmnet";
	public static String APN_TYPE_CMWAP = "cmwap";
	public static String APN_TYPE_UNINET = "uninet";
	public static String APN_TYPE_UNIWAP = "uniwap";
	public static String APN_TYPE_3GNET = "3gnet";
	public static String APN_TYPE_3GWAP = "3gwap";

	public synchronized static String getNetDetail() {
		NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return "NO_NET";
		}
		String iResult = "UNKNOW";
		int nType = networkInfo.getType();
		switch (nType) {
		case ConnectivityManager.TYPE_MOBILE:
			iResult = parseNetTypeToStr(networkInfo.getExtraInfo());
			break;
		case ConnectivityManager.TYPE_WIFI:
			iResult = "WIFI";
			break;
		}
		return iResult;
	}

	private synchronized static String parseNetTypeToStr(String aExtraInfo) {
		String iDetail = aExtraInfo;
		if (iDetail == null) {
			return "UNKNOW";
		}
		if (iDetail.startsWith(APN_TYPE_CTNET)) {
			return APN_TYPE_CTNET;
		} else if (iDetail.startsWith(APN_TYPE_CTWAP)) {
			return APN_TYPE_CTWAP;
		} else if (iDetail.startsWith(APN_TYPE_CMNET)) {
			return APN_TYPE_CMNET;
		} else if (iDetail.startsWith(APN_TYPE_CMWAP)) {
			return APN_TYPE_CMWAP;
		} else if (iDetail.startsWith(APN_TYPE_UNINET)) {
			return APN_TYPE_UNINET;
		} else if (iDetail.startsWith(APN_TYPE_UNIWAP)) {
			return APN_TYPE_UNIWAP;
		} else if (iDetail.toLowerCase(Locale.US).contains("3g")) {
			return "3G";
		} else {
			return "UNKNOW";
		}
	}

	public synchronized static HttpURLConnection getConnection(String aUrl) throws IOException {
		HttpURLConnection conn = null;
		String defaultHost = Proxy.getDefaultHost();
		String targetUrl = new String(aUrl);
		int defaultPort = Proxy.getDefaultPort();
		URL hostUrl = null;

		ConnectivityManager connectivityManager = mConnectivityManager;
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		int type = -1;
		String exrea = null;
		if (activeNetworkInfo != null) {
			type = activeNetworkInfo.getType();
			exrea = activeNetworkInfo.getExtraInfo();
		}
		System.setProperty("http.keepAlive", "false");
		if (type == ConnectivityManager.TYPE_MOBILE && defaultHost != null && defaultPort > 0) {
			if (exrea != null
				&& (exrea.startsWith(APN_TYPE_CMWAP) || exrea.startsWith(APN_TYPE_UNIWAP) || exrea
					.startsWith(APN_TYPE_3GWAP))) {
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
				conn = (HttpURLConnection) new URL("http://" + defaultHost + ":" + defaultPort
					+ path).openConnection();
				conn.setRequestProperty("X-Online-Host", host);
			} else {
				// 1、某些机型extra会有null情况 并且有代理；
				// 2、APN_TYPE_CTWAP
				java.net.Proxy proxy = new java.net.Proxy(Type.HTTP, new InetSocketAddress(
					defaultHost, defaultPort));
				conn = (HttpURLConnection) new URL(targetUrl).openConnection(proxy);
			}
		} else { // 非mobile或者没有默认代理地址的情况下，不走proxy
			hostUrl = new URL(aUrl);
			conn = (HttpURLConnection) hostUrl.openConnection();
		}
		return conn;
	}
}

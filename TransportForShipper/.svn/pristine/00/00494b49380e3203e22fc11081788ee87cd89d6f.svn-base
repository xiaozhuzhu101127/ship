package com.topjet.shipper.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;
import android.os.StatFs;

public class ApkDownloadTask 
implements Runnable {

	private final int			DEFAULT_CONN_TIMEOUT	= 5000;
	private final int			DEFAULT_READ_TIMEOUT	= 5000;

	private final int			NEED_SPACE				= 1024 * 1024 * 4;	//4M空间下载apk

	private String				mDownloadUrl;

	private boolean				isCancel				= false;

	private int					mCurrentSize;
	private int					mTotalSize;

	private ApkDownloadListener	mApkDownloadListener;

	public ApkDownloadTask(String downloadUrl, ApkDownloadListener listener) {
		mDownloadUrl = downloadUrl;
		mApkDownloadListener = listener;
	}

	public void cancel() {
		isCancel = true;
	}

	@Override
	public void run() {
		downloadApk();
	}

	private void downloadApk() {
		HttpURLConnection httpConn = getHttpURLConnection(mDownloadUrl);
		InputStream is = null;
		FileOutputStream fos = null;
		File apkFile = null;
		File tempFile = null;
//		if (getExternalDirAvailableSpaces() > NEED_SPACE) {
			int p = mDownloadUrl.lastIndexOf("/");
			String fileName = mDownloadUrl.substring(p);
			apkFile = new File(Environment.getExternalStorageDirectory(), fileName);
//		} else {
//			if (mApkDownloadListener != null) {
//				mApkDownloadListener.downloadFail();
//			}
//			return;
//		}
		if (apkFile.exists()) {
			if (mApkDownloadListener != null) {
				mApkDownloadListener.downloadComplete(apkFile);
			}
			return;
		}
		try {
			tempFile = new File(apkFile.getAbsolutePath() + ".temp");
			if (tempFile.exists()) {
				tempFile.delete();
			}
			tempFile.createNewFile();
			if (httpConn != null && httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (mApkDownloadListener != null) {
					mApkDownloadListener.downloadProgress(mCurrentSize, mTotalSize);
				}
				fos = new FileOutputStream(tempFile);
				is = httpConn.getInputStream();
				if (is != null) {
					byte[] buffer = new byte[2048];
					int read = 0;
					do {
						if (isCancel) {
							return;
						}
						read = is.read(buffer);
						if (read > 0) {
							fos.write(buffer, 0, read);
							mCurrentSize += read;
							if (mApkDownloadListener != null) {
								mApkDownloadListener.downloadProgress(mCurrentSize, mTotalSize);
							}
						}
					} while (read > 0);
					// 修改文件权限
					tempFile.renameTo(apkFile);
					Runtime.getRuntime().exec("chmod 777 " + apkFile);
					if (mApkDownloadListener != null) {
						mApkDownloadListener.downloadComplete(apkFile);
					}
				}
			} else {
				if (mApkDownloadListener != null) {
					mApkDownloadListener.downloadFail();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (mApkDownloadListener != null) {
				mApkDownloadListener.downloadFail();
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpConn != null) {
				httpConn.disconnect();
			}
		}

	}

	private HttpURLConnection getHttpURLConnection(String url) {
		HttpURLConnection httpConn = null;
		int retryCount = 5;
		while (retryCount > 0) {
			retryCount--;
			try {
				URL iUrl = new URL(url);
				httpConn = (HttpURLConnection) iUrl.openConnection();
				if (httpConn == null) {
					Thread.sleep(100); // 100毫秒以后，重试
					continue;
				}
				httpConn.setConnectTimeout(DEFAULT_CONN_TIMEOUT);
				httpConn.setReadTimeout(DEFAULT_READ_TIMEOUT);

				httpConn.connect();
				mTotalSize = httpConn.getContentLength();
				if (mTotalSize > 0) {
					return httpConn;
				} else {
					httpConn.disconnect();
					httpConn = null;
					mTotalSize = -1;
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (httpConn != null) {
					httpConn.disconnect();
					httpConn = null;
				}
				mTotalSize = -1;
				continue;
			}
		}
		return httpConn;
	}

	public interface ApkDownloadListener {
		void downloadComplete(File apkFile);

		void downloadProgress(int downloadSize, int totalSize);

		void downloadFail();
	}
	
	private long getExternalDirAvailableSpaces() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
			StatFs stat = new StatFs(path.getAbsolutePath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return blockSize * availableBlocks;// 返回可用空间大小
		}
		return 0;
	}
}

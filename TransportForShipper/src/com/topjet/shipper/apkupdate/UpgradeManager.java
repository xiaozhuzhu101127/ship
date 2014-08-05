package com.topjet.shipper.apkupdate;

import java.io.File;
import org.json.JSONObject;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.MyApplication;
import com.topjet.shipper.apkupdate.ApkDownloadTask.ApkDownloadListener;
import com.topjet.shipper.core.LoadData;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import android.widget.ScrollView;
import android.widget.TextView;

public class UpgradeManager {
	private static UpgradeManager instance;
	private ProgressDialog mDownloadProgressDialog;
	private static final int MSG_DOWNLOAD_PROGRESS = 0x1;
	private static final int MSG_DOWNLOAD_FAILED = MSG_DOWNLOAD_PROGRESS + 1;

	private boolean isShowTip = false;
	private String updateDownloadUrl = ""; // 版本更新的下载地址
	private String updateMessage = "发现最新版本";

	private Thread mUpgradeThread;
	private Context context;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {

			case DefaultConst.CMD_APK_UPDATE:
				ioHandler(msg);
				break;
			case DefaultConst.CMD_ERROR_NET_RETURN_FAILED:
			case DefaultConst.CMD_ERROR_NET_ADD_REQUEST:
				doError(msg);
				break;
			case MSG_DOWNLOAD_PROGRESS:
				mDownloadProgressDialog.setProgress(msg.arg1 * 100 / msg.arg2);
				break;
			case MSG_DOWNLOAD_FAILED:
				showDownloadFailedDialog();
				break;
			}
		}
	};

	/**
	 * 轮询检查更新
	 * 
	 * @param interval
	 */
	/*
	 * public void pollingCheckVersion(){
	 * 
	 * task = new Runnable(){
	 * 
	 * @Override public void run() { handler.sendEmptyMessage(CMD_APK_UPDATE);
	 * handler.postDelayed(task, spinTime); }
	 * 
	 * }; handler.postDelayed(task, spinTime); }
	 */
	private void ioHandler(Message msg) {
		if (isShowTip)
			dismissLoadingDialog();

		JSONObject data = (JSONObject) msg.obj;
		if (data != null) {
			updateDownloadUrl = data.optString("msg");
//		  updateDownloadUrl ="http://www.566560.com/d/test123.apk";
			updateMessage = "检查到新版本 ";
			showForceUpgradeDialog();
		} else if (isShowTip) {
			new AlertDialog.Builder(context).setTitle("提示").setMessage("当前已经是最新版本，无需更新").setPositiveButton("确定", null)
					.show();
		}
	}

	private void doError(Message msg) {
		if (isShowTip) {
			dismissLoadingDialog();
			Common.showToast("网络请求失败", context);
		}
	}

	private UpgradeManager(Context context) {
		this.context = context;
	}

	public synchronized static UpgradeManager getInstance(Context context) {
		if (instance == null) {
			instance = new UpgradeManager(context);
		}
		instance.context=context;
		return instance;
	}

	/**
	 * 检查版本
	 */
	public void checkVersion(final boolean isShowTip) {
		this.isShowTip = isShowTip;
		if (isShowTip) {
			showLoadingDialog();
		}
		LoadData.getInstance().apkUpdate(handler, DefaultConst.CMD_APK_UPDATE, Common.getVersionName());
	}
	
	/**
	 * 通讯录上传版本不对，提示的更新
	 */
	public void contactVersion(JSONObject json) {

        Message message =handler.obtainMessage();
        message.what=DefaultConst.CMD_APK_UPDATE;
        message.obj=json;
		handler.sendMessage(message);
		
	}


	private ProgressDialog mProgressDialog;

	protected void showLoadingDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.setMessage("加载中......");
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setCancelable(false);
		}
		mProgressDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	private void showForceUpgradeDialog() {
		ScrollView sv = new ScrollView(context);
		TextView tv = new TextView(context);
		tv.setText(updateMessage);
		sv.addView(tv);
		AlertDialog updateDialog = new AlertDialog.Builder(context).setTitle("版本更新").setView(sv)
				.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (updateDownloadUrl.toLowerCase().endsWith(".apk")) {
							mDownloadProgressDialog = createDownloadProgressDialog();
							mDownloadProgressDialog.show();
							startDownloadApkFileTask(updateDownloadUrl);
						} else {
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							Uri uri = Uri.parse(updateDownloadUrl);
							intent.setData(uri);
							context.startActivity(intent);
							exitApplication();
						}
					}
				}).setNegativeButton("退出", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						exitApplication();
					}
				}).create();
		updateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				exitApplication();
			}
		});
		updateDialog.setCanceledOnTouchOutside(false);
		updateDialog.show();
	}

	/**
	 * 开始下载新版本的安装文件
	 * 
	 * @param downloadUrl
	 * @param handler
	 */
	private void startDownloadApkFileTask(String downloadUrl) {
		if (null != mUpgradeThread && mUpgradeThread.isAlive())
			mUpgradeThread.interrupt();
		else {
			mUpgradeThread = new Thread(new ApkDownloadTask(downloadUrl, new ApkDownloadListener() {

				@Override
				public void downloadComplete(File apkFile) {
					// TODO Auto-generated method stub
					if (null != mDownloadProgressDialog)
						mDownloadProgressDialog.dismiss();
					//如何判断是否下载完整？
					installApk(apkFile);
					exitApplication();
				}

				@Override
				public void downloadProgress(int downloadSize, int totalSize) {
					// TODO Auto-generated method stub
					handler.obtainMessage(MSG_DOWNLOAD_PROGRESS, downloadSize, totalSize).sendToTarget();
				}

				@Override
				public void downloadFail() {
					// TODO Auto-generated method stub
					handler.obtainMessage(MSG_DOWNLOAD_FAILED).sendToTarget();
				}
			}));

			mUpgradeThread.start();
		}
	}

	/**
	 * 创建下载进度显示的对话框
	 * 
	 * @return
	 */
	private ProgressDialog createDownloadProgressDialog() {
		ProgressDialog downloadDialog = new ProgressDialog(context);
		downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downloadDialog.setIndeterminate(false);
		downloadDialog.setMessage("下载中···");
		downloadDialog.setCancelable(true);
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.setProgress(0);
		downloadDialog.setMax(100);
		downloadDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				exitApplication();
			}
		});
		return downloadDialog;
	}

	/**
	 * 退出应用
	 */
	private void exitApplication() {
		MyApplication.getInstance().onTerminate();
	}

	/**
	 * 安装apk文件
	 * 
	 * @param filename文件的路径
	 */
	private void installApk(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(file), type);
		context.startActivity(intent);
	}

	/**
	 * 显示下载失败对话框
	 */
	private void showDownloadFailedDialog() {
		AlertDialog downloadFailedDialog = new AlertDialog.Builder(context).setTitle("下载失败")
				.setMessage("安装文件下载失败，是否重试？").setPositiveButton("重试", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mDownloadProgressDialog.setProgress(0);
						mDownloadProgressDialog.show();
						startDownloadApkFileTask(updateDownloadUrl);
					}
				}).setNegativeButton("退出", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						exitApplication();
					}
				}).create();
		downloadFailedDialog.setCanceledOnTouchOutside(false);
		downloadFailedDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				exitApplication();
			}
		});
		downloadFailedDialog.show();
	}
}

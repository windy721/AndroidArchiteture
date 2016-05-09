package com.jim.androidarchiteture.util.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.jim.androidarchiteture.R;
import com.jim.androidarchiteture.common.AppLog;
import com.jim.androidarchiteture.common.viewutil.ResourceManger;
import com.jim.androidarchiteture.config.AppSetting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created with IntelliJ IDEA. User: Jace Date: 13-3-5 Time: 下午3:13 软件更新后台服务类
 */
public class UpdateService extends Service {
	// 下载状态
	private final static int DOWNLOAD_COMPLETE = 0;
	private final static int DOWNLOAD_FAIL = 1;

	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	// 通知栏跳转Intent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
	private File updateFile;
	private int downloadCount = 0;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 获取传值

		String downloadUrl = AppSetting.getInstance().getDownloadAddress();

		if (!TextUtils.isEmpty(downloadUrl)) {

			// 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
			new Thread(new updateRunnable(downloadUrl)).start();// 这个是下载的重点，是下载的过程

			// 如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。
			return START_STICKY;
		} else {
			stopSelf();
			// “非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。
			return START_NOT_STICKY;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 初始化下载通知
	 */
	private void initDownloadNotification() {
		downloadCount = AppSetting.getInstance().getDownloadCount();
		updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		updateNotification = new Notification();
		// 设置下载过程中，点击通知栏，回到主界面
		updateIntent = new Intent();
		updateIntent.putExtra("isUpdating", true);
		updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
		// 设置通知栏显示内容
		String title = getResources().getString(R.string.app_name) + " 正在更新中";
		String content = downloadCount + "%";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			updateNotification = new Notification.Builder(this)
					.setLargeIcon(ResourceManger.getBitMap(this, R.mipmap.ic_launcher)).setSmallIcon(android.R.drawable.stat_sys_download)
					.setContentTitle(title).setContentText(content).setTicker(title)
					.setContentIntent(updatePendingIntent)
					.build();
		}
		updateNotification.flags = Notification.FLAG_NO_CLEAR;

		// 发出通知
		updateNotificationManager.notify(0, updateNotification);
	}

	/**
	 * 更新类
	 */
	private class updateRunnable implements Runnable {

		String downloadUrl;

		public updateRunnable(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}

		Message message = updateHandler.obtainMessage();

		@Override
		public void run() {
			try {
				message.what = downloadUpdateFile(downloadUrl);
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = DOWNLOAD_FAIL;
				// 下载失败

			} finally {
				updateHandler.sendMessage(message);
			}
		}
	}

	/**
	 * 下载更新文件
	 * 
	 * @param downloadUrl
	 *            更新文件地址
	 * @return 下载结果
	 * @throws Exception
	 */
	public int downloadUpdateFile(String downloadUrl) throws Exception {
		long totalSize = 0;
		int updateTotalSize = 0;

		File dirFile = new File(AppSetting.STORE_ROOT);

		if (!dirFile.exists()) {
			dirFile.mkdir();
		}

		// 创建文件
		updateFile = new File(AppSetting.STORE_ROOT
				+   "/dabai.apk");
		if (updateFile.exists()) {
			updateFile.delete();
		}
		AppLog.i("update Apk file=%s", updateFile.getAbsolutePath());
		if (updateFile.createNewFile()) {

			initDownloadNotification();

			HttpURLConnection httpConnection = null;
			InputStream is = null;
			FileOutputStream fos = null;

			try {

				URL url = new URL(downloadUrl);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestProperty("User-Agent",
						"PacificHttpClient");
				httpConnection.setConnectTimeout(10000);
				httpConnection.setReadTimeout(20000);
				updateTotalSize = httpConnection.getContentLength();

				if (httpConnection.getResponseCode() == 404) {
					throw new Exception("fail!");
				}
				is = httpConnection.getInputStream();
				fos = new FileOutputStream(updateFile, false);
				byte buffer[] = new byte[4096];
				int readsize = 0;

				while ((readsize = is.read(buffer)) > 0) {
					fos.write(buffer, 0, readsize);
					totalSize += readsize;
					// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
					if ((downloadCount == 0)
							|| (int) (totalSize * 100 / updateTotalSize) - 10 > downloadCount) {
						downloadCount += 10;

						// 保存下载进度，重新启动service时继续下载
						AppSetting.getInstance().saveDownloadCount(downloadCount);
						String title = getResources().getString(R.string.app_name) + " 正在更新中";
						String content = downloadCount + "%";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							updateNotification = new Notification.Builder(this)
									.setLargeIcon(ResourceManger.getBitMap(this, R.mipmap.ic_launcher)).setSmallIcon(android.R.drawable.stat_sys_download)
									.setContentTitle(title).setContentText(content).setTicker(title)
									.setContentIntent(updatePendingIntent)
									.build();
						}

						updateNotificationManager.notify(0, updateNotification);
					}
				}
				return DOWNLOAD_COMPLETE;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
			}
		} else {
			stopSelf();
		}

		return DOWNLOAD_FAIL;
	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:

				// // 点击安装PendingIntent
				// Uri uri = Uri.fromFile(updateFile);
				// Intent installIntent = new Intent(Intent.ACTION_VIEW);
				// installIntent.setDataAndType(uri,
				// "application/vnd.android.package-archive");
				// updatePendingIntent =
				// PendingIntent.getActivity(UpdateService.this, 0,
				// installIntent, 0);
				// updateNotification.flags |= Notification.FLAG_SHOW_LIGHTS;
				// updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
				// updateNotification.icon = R.drawable.stat_sys_download_done;
				// updateNotification.defaults =
				// Notification.DEFAULT_SOUND;//铃声提醒
				// updateNotification.setLatestEventInfo(UpdateService.this,
				// "艾薇er", "下载完成,点击安装。", updatePendingIntent);
				// updateNotificationManager.notify(0, updateNotification);

				updateNotificationManager.cancel(0);

				// 下载完成后置空
				AppSetting.getInstance().saveDownloadCount(0);
				AppSetting.getInstance().saveDownloadAddress("");

				Uri uri2 = Uri.fromFile(updateFile);
				Intent intent2 = new Intent(Intent.ACTION_VIEW);
				intent2.setDataAndType(uri2,
						"application/vnd.android.package-archive");
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent2);

				break;
			}

			stopSelf();
		}
	};
}

package com.jim.androidarchiteture.util.net;

import android.content.Context;
import android.net.Uri;

import com.jim.androidarchiteture.common.StringUtils;
import com.jim.androidarchiteture.config.AppSetting;

/**
 * Created by zhangxiliang on 2015/7/21.
 */
public class DownloadManager {

    public static void download(Context context,String url){
        if (StringUtils.isBlank(url)) {
            return;
        }
        String serviceString = Context.DOWNLOAD_SERVICE;
        android.app.DownloadManager downloadManager;
        downloadManager = (android.app.DownloadManager)context.getSystemService(serviceString);

        Uri uri = Uri.parse(url);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(uri);

        //手机网络和wifi都可以下载
        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_MOBILE | android.app.DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        request.setTitle("大白管家商家端");
        request.setDescription("下载中");
        //在通知栏中显示
        request.setShowRunningNotification(true);
        request.setVisibleInDownloadsUi(true);

        //sdcard的目录下的download文件夹
        request.setDestinationInExternalPublicDir(AppSetting.STORE_ROOT, "DabaiSite.apk");
        long reference = downloadManager.enqueue(request);
    }

}

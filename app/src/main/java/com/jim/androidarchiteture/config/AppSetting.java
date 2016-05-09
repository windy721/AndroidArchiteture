package com.jim.androidarchiteture.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;

import com.jim.androidarchiteture.MyApplication;
import com.jim.androidarchiteture.common.AndroidTools;

import java.io.File;

/**
 * Created by zhangxiliang on 2015/6/11.
 */
public class AppSetting {
    private static AppSetting appSetting;

    public static final String DEF_CHARSET = "utf-8";
    public static final String SHARE_KEY = "key";

    /**
     * APP升级断点下载
     */
    public static final String DOWNLOAD_KEY = "download";
    public static final String DOWNLOAD_COUNT = "downloadCount";

    /***
     * 登陆token
     */
    public static final String TOKEN = "token";

    /**
     * 首次安装
     */
    public static final String FIRST_INSTALL = "first_install";
    /***
     * 百度Location
     */
    public static final String cache_bdlocation = "cache_bdlocation";

    private SharedPreferences settings;

    private static final String ROOT_FOLDER_NAME = "DabaiSite";
    public static String STORE_ROOT = MyApplication.getInstance().getApplicationContext()
            .getFilesDir() + File.separator + ROOT_FOLDER_NAME;
    public static String sPrivateDir = MyApplication.getInstance().getDir("dabaisite", Context.MODE_PRIVATE).getAbsolutePath();

    static {
        if (AndroidTools.isExistSdCard()) {
            STORE_ROOT = Environment.getExternalStorageDirectory() + File.separator + ROOT_FOLDER_NAME;
        }
    }

    public static String IMAGE_ROOT = STORE_ROOT + "/image";
    public static String VOICE_ROOT = STORE_ROOT + "/voice";
    public static String TASK_ROOT = sPrivateDir + "/task";
    public static String LOGO_ROOT = STORE_ROOT + "/logo";

    private AppSetting(Context context) {
        // 解决配置文件多进程共享问题，如在Service上的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings = context.getSharedPreferences(SHARE_KEY,
                    Context.MODE_MULTI_PROCESS);
        } else {
            settings = context.getSharedPreferences(SHARE_KEY,
                    Context.MODE_APPEND);
        }

    }

    public static void initDataServer() {
        // 使用单列会导致跨进程访问，数据不同步的现象
        if (appSetting == null) {
            appSetting = new AppSetting(MyApplication.getInstance());
        }

    }

    public static AppSetting getInstance() {
        // 使用单列会导致跨进程访问，数据不同步的现象
        if (appSetting == null) {
            appSetting = new AppSetting(MyApplication.getInstance());
        }
        return appSetting;
    }

//    public void setBDLocation(BDLocationItem bdLocationItem){
//        Gson gson=new Gson();
//
//        setString(cache_bdlocation, gson.toJson(bdLocationItem));
//    }
//
//    public BDLocationItem getBDLocation(){
//        String bdlocationStr =getString(cache_bdlocation,"");
//        return JsonUtil.parseJsonObj(bdlocationStr, BDLocationItem.class);
//    }

    public void setFirstInstall(boolean firstInstall) {
        setBoolean(FIRST_INSTALL, firstInstall);
    }

    public boolean isFirstInstall() {
        return getBoolean(FIRST_INSTALL, true);
    }

    public String getDownloadAddress() {
        return getString(DOWNLOAD_KEY, "");
    }

    public void saveDownloadAddress(String downloadAddress) {
        setString(DOWNLOAD_KEY, downloadAddress);
    }

    public int getDownloadCount() {
        return getInt(DOWNLOAD_COUNT, 0);
    }

    public void saveDownloadCount(int downloadCount) {
        setInt(DOWNLOAD_COUNT, downloadCount);
    }

    public void setInt(String paramString, int paramValue) {
        settings.edit().putInt(paramString, paramValue).commit();
    }

    public int getInt(String paramString, int defaultValue) {
        int temp = settings.getInt(paramString, defaultValue);
        return temp;
    }

    public void setLong(String paramString, long paramValue) {
        settings.edit().putLong(paramString, paramValue).commit();
    }

    public long getLong(String paramString, long defaultValue) {
        return settings.getLong(paramString, defaultValue);
    }

    public void setString(String paramString, String paramValue) {
        settings.edit().putString(paramString, paramValue).commit();
    }

    public String getString(String paramString, String defaultValue) {
        return settings.getString(paramString, defaultValue);
    }

    public void setBoolean(String paramString, boolean paramBoolean) {
        settings.edit().putBoolean(paramString, paramBoolean).commit();
    }

    public boolean getBoolean(String paramString, boolean defaultValue) {
        return settings.getBoolean(paramString, defaultValue);
    }

    /***
     * 设置登陆token
     *
     * @param token
     */
    public void setLoginToken(String token) {
        setString(TOKEN, token);
    }

    /***
     * 获取登陆token
     */
    public String getLoginToken() {
        return getString(TOKEN, "");
    }

    /**
     * 清除本地保存的登陆token
     */
    public void cleanToken() {
        setString(TOKEN, "");
    }

    /**
     * 清除本地保存的User信息
     */
    public void cleanUserCache() {
        setString(TOKEN, "");
    }
}

package com.jim.androidarchiteture.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Jace
 * Date: 13-12-4
 * Time: 上午11:52
 * To change this template use File | Settings | File Templates.
 */
public class ClientInfo {
    // 渠道号
    public static String fromId = "9100701";
    private static String clienInfo;
    private static TelephonyManager mTelephonyManager;

    public enum NetType {
        NONE, WIFI, CELLULAR
    }

    /**
     * 获取clientInfo
     *
     * @param context
     * @return
     */
    public static String getClientInfo(Context context) {

        String imei = getIMEI(context);
        String model = getModel(context);
        String mac = getMAC(context);

        if (imei.equals("")) {
            imei = mac;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String screen = width + "x" + height;

        String op = getOP(context);
        String netType = getNetTypeName(getNetType(context));
        String osInfo = getOSInfo(context);
        String version = getAppVersion(context);
        String imsi = getSubscriberId(context);
        String metaData = getApplicationMetaData(context, "UMENG_CHANNEL");//CHANNEL_ID
        if (metaData != null && !metaData.equals("")) {
            fromId = metaData;
        }

        String clientInfo = String
                .format(Locale.getDefault(),
                        "{\"model\":\"%s\",\"os\":\"%s\",\"screen\":\"%s\",\"from\":%s,\"version\":\"%s\",\"uniqid\":\"%s\",\"product\":\"%s\",\"mac\":\"%s\",\"net_type\":\"%s\",\"os_info\":\"%s\",\"op\":\"%s\",\"imsi\":\"%s\"}",
                        model, "android", screen, fromId, version, mac,
                        "", mac, netType, osInfo, op, imsi);
        clienInfo = URLEncoder.encode(clientInfo);

        return clienInfo;
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        // 获取telephony系统服务，用于取得SIM卡和网络相关信息  
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        return mTelephonyManager;
    }

    public static String getSubscriberId(Context context) {
        String mSubscriberId = getTelephonyManager(context).getSubscriberId();// String
        return mSubscriberId;
    }

    /**
     * 获取clientInfo
     *
     * @param context
     * @return
     */
    public static String getClientInfoForLogin(Context context) {

        String imei = getIMEI(context);
        String model = getModel(context);
        String mac = getMAC(context);

        if (imei.equals("")) {
            imei = mac;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String screen = width + "x" + height;

        String op = getOP(context);
        String netType = getNetTypeName(getNetType(context));
        String osInfo = getOSInfo(context);
        String version = getAppVersion(context);

        String metaData = getApplicationMetaData(context, "UMENG_CHANNEL");//CHANNEL_ID
        if (metaData != null && !metaData.equals("")) {
            fromId = metaData;
        }

        String clientInfo = String
                .format(Locale.getDefault(),
                        "{\"model\":\"%s\",\"os\":\"%s\",\"screen\":\"%s\",\"from\":\"%s\",\"version\":\"%s\",\"uniqid\":\"%s\",\"product\":\"%s\",\"mac\":\"%s\",\"net_type\":\"%s\",\"os_info\":\"%s\",\"op\":\"%s\"}",
                        model, "android", screen, fromId, version, mac,
                        "", mac, netType, osInfo, op);
        clienInfo = URLEncoder.encode(clientInfo);

        return clienInfo;
    }

    /**
     * vv统计专用的
     *
     * @param context
     * @return
     */
    public static String getClientInfoVV(Context context) throws UnsupportedEncodingException {
        String imei = URLEncoder.encode(getIMEI(context), "utf-8");
        String model = URLEncoder.encode(getModel(context), "utf-8");
        String mac = URLEncoder.encode(getMAC(context), "utf-8");

        if (imei.equals("")) {
            imei = mac;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String screen = URLEncoder.encode(width + "x" + height, "utf-8");

        String op = URLEncoder.encode(getOP(context), "utf-8");
        String netType = URLEncoder.encode(getNetTypeName(getNetType(context)), "utf-8");
        String osInfo = URLEncoder.encode(getOSInfo(context), "utf-8");
        String version = URLEncoder.encode(getAppVersion(context), "utf-8");
        if (fromId == null) {
            fromId = getApplicationMetaData(context, "CHANNEL_ID");
        }

        String clientInfo = String
                .format(Locale.getDefault(),
                        "client_info:model=%s&client_info:os=%s&client_info:screen=%s&client:from=%s&client_info:version=%s&client_info:uniqid=%s&client_info:product=%s&client_info:mac=%s&client_info:net_type=%s&client_info:os_info=%s&client_info:op=%s",
                        model, "android", screen, fromId, version, mac,
                        "", mac, netType, osInfo, op);
        return clientInfo;
    }

    /**
     * 获取手机型号
     *
     * @param context
     * @return
     */
    public static String getModel(Context context) {
        String model = android.os.Build.MODEL;
        if (model == null || model.equals("")) {
            model = "model";
        }
        return model;
    }

    /**
     * 获取设备MAC地址
     *
     * @param context
     * @return
     */
    protected static String getMAC(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        if (mac == null || mac.equals("")) {
            mac = "mac";
        }
        mac = mac.replaceAll(":", "-");
        return mac;
    }

    /**
     * 获取app版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);

            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 0;
        }
        return versionCode;
    }

    /***
     * 获取app版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        String version = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);

            version = info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            version = "1.0.1";
        }
        return version;
    }


    protected static String getOP(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String op = tm.getSimOperator();
        if (op == null || op.equals("")) {
            op = "op";
        }
        return op;
    }

//    public static String getUmengDeviceToken(Context context) {
//        String device_token = UmengRegistrar.getRegistrationId(context);
//        return device_token;
//    }

    /**
     * 获取设备唯一标示imei
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        try {
            imei = tm.getSimSerialNumber();
        } catch (Exception exception) {
        }
        if (imei == null || imei.equals("")) {
            imei = "imei";
        }

        return imei;
    }

    /**
     * 获取系统版本名称
     *
     * @param context
     * @return
     */
    public static String getOSInfo(Context context) {
        String osInfo = "";
        osInfo = "android " + android.os.Build.VERSION.RELEASE;
        if (osInfo == null || "".equals(osInfo)) {
            osInfo = "os_info";
        }
        return osInfo;
    }


    /**
     * 网络状况判定
     *
     * @param context
     * @return
     */
    public static NetType getNetType(Context context) {
        ConnectivityManager conManger = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conManger == null) {
            return NetType.NONE;
        }

        NetworkInfo networkInfo = null;
        try {
            networkInfo = conManger.getActiveNetworkInfo();
        } catch (Exception e) {

        }

        if (networkInfo == null || !networkInfo.isAvailable()) {
            return NetType.NONE;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.CELLULAR;
    }

    /**
     * 网络是否正常
     */
    public static boolean isNetOk(Context context) {

        if (getNetType(context) != NetType.NONE) {
            return true;
        }
        return false;
    }

    public static String getNetTypeName(NetType type) {
        String name = null;
        switch (type) {
            case NONE:
                name = "net_type";
                break;
            case WIFI:
                name = "wifi";
                break;
            case CELLULAR:
                name = "cellular";
                break;
        }
        return name;
    }

    /**
     * 读取AndroidManifest.xml中meta-data标签的值
     *
     * @param name
     * @return
     */
    public static String getApplicationMetaData(Context context, String name) {
        ApplicationInfo info;
        try {
            info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(name) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGpsOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }
}

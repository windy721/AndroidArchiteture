package com.jim.androidarchiteture.common;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangxiliang on 2015/6/11.
 */
public class AndroidTools {

    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!StringUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }

        return false;
    }

    public static String getSDPATH() {
        String SDPATH = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        return SDPATH;
    }

    public static boolean isExistSdCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            return true;
        }
        return false;
    }

    public static boolean isMobileNO(String mobiles) {
        if (mobiles != null && !StringUtils.isEmpty(mobiles)) {
            //        Pattern p = Pattern.compile("[1][3458]\\d{9}");
            Pattern p = Pattern.compile("[1]\\d{10}");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
        return false;
    }

    public static boolean isPhoneCallNO(String phoneCallNO) {
        if (phoneCallNO != null && !StringUtils.isEmpty(phoneCallNO)) {
            Pattern p = Pattern.compile("\\d{6,13}");
            Matcher m = p.matcher(phoneCallNO);
            return m.matches();
        }
        return false;
    }

    public static boolean isCorrectExpressId(String expressid) {
        Pattern p = Pattern.compile("[A-Za-z0-9\\-\\(\\)]{8,32}");
        Matcher m = p.matcher(expressid);
        return m.matches();
    }

    public static String getCorrectExpressId(String expressid) {
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(expressid);
        return m.replaceAll("").trim();
    }

    public static boolean isCorrectAmount(String amount) {

        Pattern p = Pattern.compile("^[+]?(([1-9]\\\\d*[.]?)|(0.))(\\\\d{0,2})?$");
        Matcher m = p.matcher(amount);
        return m.matches();
    }
}

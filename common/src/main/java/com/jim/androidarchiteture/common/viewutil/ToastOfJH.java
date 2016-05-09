package com.jim.androidarchiteture.common.viewutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.jim.androidarchiteture.common.R;

/**
 * 用户统一Toast调用~
 * Created with IntelliJ IDEA.
 * User: Jace
 * Date: 12-12-27
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class ToastOfJH {
    private static Toast mToast;

    public static void showBadNetToast(Context context){
    	 if (mToast == null) {
    		 //大部分时候网络连接是存在的，但在某一个时刻网络会较差，导致请求失败，并非完全意义上的网络异常
             mToast = Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT);
         } else {
             mToast.setText(context.getString(R.string.net_error));
             mToast.setDuration(Toast.LENGTH_SHORT);
         }
         mToast.show();
    }
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    
    public static void showToastLong(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getResources().getString(resId));
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
    
    public static void showAlert(Context context, String text) {
		new AlertDialog.Builder(context).setTitle("提示").setMessage(text).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}
}

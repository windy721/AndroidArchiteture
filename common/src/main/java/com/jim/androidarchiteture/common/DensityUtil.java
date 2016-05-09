package com.jim.androidarchiteture.common;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author zhangbl
 * @date 2013-9-30 上午10:36:55
 * @version 1.0
 * @<b></b>
 */
public class DensityUtil {

	public static int DEVICE_WIDTH = 0;
	public static int DEVICE_HEIGHT = 0;
	
	public static void init(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		DEVICE_WIDTH = dm.widthPixels;
		DEVICE_HEIGHT = dm.heightPixels;
	}
	
	/** 
	* @Description: TODO(获取屏幕的高) 
	* @param context
	* @return    参数 
	* @return int    返回类型 
	*/
	public static int getDeviceHeight(Activity context){
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * @Description: TODO(获取屏幕的高)
	 * @param context
	 * @return    参数
	 * @return int    返回类型
	 */
	public static int getDeviceWidth(Activity context){
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @param context
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().density;
		return (int) (spValue * fontScale + 0.5f);
	}

}

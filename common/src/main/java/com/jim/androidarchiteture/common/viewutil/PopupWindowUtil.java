package com.jim.androidarchiteture.common.viewutil;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by JimGong on 2016/2/3.
 */
public final class PopupWindowUtil {
    public static void showPopupWindow(View pContentView, View pAnchor) {
        // Construct the popup windows
        final PopupWindow popupWindow = new PopupWindow(pContentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(pContentView.getResources().getDrawable(R.drawable.ic_launcher));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(pAnchor);
    }
}

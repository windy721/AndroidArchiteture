package com.jim.androidarchiteture.common.viewutil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jim.androidarchiteture.common.DensityUtil;
import com.jim.androidarchiteture.common.R;
import com.jim.androidarchiteture.common.StringUtils;

/***
 * Diaolog Created By Zhangxiliang Date：2014年9月22日 Version： 2.0 Copyright (c)
 * 2014 56.com Software corporation All Rights Reserved.
 */
public class DialogUtil {


    public static AlertDialog showDialog(Context pContext, String pMessage) {
        return showDialog(pContext, pMessage, null);
    }

    public static AlertDialog showDialog(Context pContext, String pMessage, OnClickListener pConfirmListener) {
        return showDialog(pContext, pMessage, pConfirmListener, null);
    }

    public static AlertDialog showDialog(Context pContext, String pMessage, OnClickListener pConfirmListener, OnClickListener pCancelListener) {
        return showDialog(pContext, "提示", pMessage, "确定", (null == pCancelListener) ? null : "取消", pConfirmListener, pCancelListener);
    }

    public static AlertDialog showDialog(Context pContext, String pTitle, String pMessage, String pOKMsg, String pCancelMsg, OnClickListener pConfirmListener, OnClickListener pCancelLlistener) {
        if (pContext != null && !((Activity) pContext).isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(pContext, R.style.AppCompatAlertDialogStyle)
                    .setTitle(pTitle)
                    .setMessage(pMessage)
                    .setPositiveButton(pOKMsg, pConfirmListener);
            if (!StringUtils.isBlank(pCancelMsg)) {
                builder.setNegativeButton(pCancelMsg, pCancelLlistener);
            }
            final AlertDialog lDialog = builder.create();
            lDialog.setCanceledOnTouchOutside(false);
            lDialog.show();
            return lDialog;
        }
        return null;
    }

    public static AlertDialog showBottomDialog(Context pContext, int pStringResId, View pCustomView) {
        return showBottomDialog(pContext, pContext.getString(pStringResId), pCustomView);
    }

    public static AlertDialog showDialog(Context pContext, View pCustomView) {
        if (pContext != null && !((Activity) pContext).isFinishing()) {
            final AlertDialog lDialog = new AlertDialog.Builder(pContext)
                    .create();
            Window lWindow = lDialog.getWindow();
            ViewParent lParent = pCustomView.getParent();
            if (null != lParent) {
                ((ViewGroup) lParent).removeAllViews();
            }
            lDialog.setView(pCustomView);
            lDialog.show();
            lWindow.setBackgroundDrawableResource(android.R.color.transparent);
            lWindow.setGravity(Gravity.CENTER);

            return lDialog;
        }
        return null;
    }


    public static AlertDialog showBottomDialog(Context pContext, String pTitle, View pCustomView) {
        if (pContext != null && !((Activity) pContext).isFinishing()) {
            final android.support.v7.app.AlertDialog lDialog = new android.support.v7.app.AlertDialog.Builder(pContext).create();
            Window lWindow = lDialog.getWindow();
            ViewParent lParent = pCustomView.getParent();
            if (null != lParent) {
                ((ViewGroup) lParent).removeAllViews();
            }
            LinearLayout titleDialogLayout = (LinearLayout) ((Activity) pContext).getLayoutInflater().inflate(R.layout.layout_title_dialog, null);
            ((TextView) titleDialogLayout.findViewById(R.id.dialog_titleTxt)).setText(pTitle);
            titleDialogLayout.addView(pCustomView);

            lDialog.setView(titleDialogLayout);
            lDialog.show();
            lWindow.setBackgroundDrawableResource(android.R.color.transparent);

            ViewGroup.LayoutParams layoutParams = pCustomView.getLayoutParams();
            if (null != layoutParams) {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = DensityUtil.dip2px(pContext, 300);
                pCustomView.setLayoutParams(layoutParams);
            }
            lWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM | Gravity.RIGHT);

            return lDialog;
        }
        return null;
    }

}

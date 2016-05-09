package com.jim.androidarchiteture.common.viewutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Created by Administrator on 2015/6/5.
 */
public class ViewUtils {

    public static View getItemView(Context context, int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(resource, null);

    }

    public static void getColor(Context context, int color) {

    }
}

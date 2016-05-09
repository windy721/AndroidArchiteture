package com.jim.androidarchiteture.common.viewutil;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;

import java.io.InputStream;

/**
 * Created by zhangxiliang on 2015/6/8.
 */
public class ResourceManger {

    public static int getColor(Context context, int color) {

        return context.getResources().getColor(color);
    }


    public static String getString(Context context, int strid) {
        return context.getResources().getString(strid);
    }


    public static Drawable getDrawable(Context context, int drawableid) {

        return ResourcesCompat.getDrawable(context.getResources(), drawableid, null);
    }

    public static Bitmap getBitMap(Context context, int drawableid) {

        InputStream is = context.getResources().openRawResource(drawableid);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        return mBitmap;
    }


    public static View getItemView(Context context, int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(resource, null);

    }
}

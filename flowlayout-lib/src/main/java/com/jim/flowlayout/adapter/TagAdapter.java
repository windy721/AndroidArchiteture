package com.jim.flowlayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jim.flowlayout.FlowLayout;
import com.jim.flowlayout.listener.OnDataChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JimGong on 2016/6/27.
 */
public abstract class TagAdapter<T> {
    Context mContext;
    int mLayoutId;
    List<T> mData;

    OnDataChangedListener mOnDataChangedListener;

//    private TagAdapter(List<T> pData) {
//        mData = pData;
//    }
//    private TagAdapter(T[] pData) {
//        mData = new ArrayList<T>(Arrays.asList(pData));
//    }
    public TagAdapter(Context pContext, int pLayoutId, List<T> pData) {
        mContext = pContext;
        mLayoutId = pLayoutId;
        mData = pData == null ? new ArrayList<T>() : new ArrayList<T>(pData);
    }

    public View getView(FlowLayout parent, int position, T item) {
        View childView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        convert(parent, childView, position, item);
        return childView;
    }

    protected abstract void convert(FlowLayout parent, View childView, int position, T item);

//    public void notifyDataChanged() {
//
//    }

    public void setOnDataChangedListener(OnDataChangedListener pOnDataChangedListener) {
        mOnDataChangedListener = pOnDataChangedListener;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public int getCount() {
        return null == mData ? 0 : mData.size();
    }
}

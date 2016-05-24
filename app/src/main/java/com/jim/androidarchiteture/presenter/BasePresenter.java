package com.jim.androidarchiteture.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.jim.androidarchiteture.activity.iview.IView;

import java.lang.ref.WeakReference;

/**
 * Created by JimGong on 2016/5/24.
 */
public class BasePresenter<T extends IView> {
    private WeakReference<T> mViewRef;

    @UiThread
    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @UiThread
    protected T getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    protected WeakReference<T> getViewHolder() {
        return mViewRef;
    }

    @UiThread
    public void attachView(@NonNull T pView) {
        mViewRef = new WeakReference<T>(pView);
    }

    @UiThread
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}

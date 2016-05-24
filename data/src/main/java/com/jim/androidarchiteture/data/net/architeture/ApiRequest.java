package com.jim.androidarchiteture.data.net.architeture;

import com.jim.androidarchiteture.data.net.architeture.annotation.RequestInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by JimGong on 2016/5/17.
 */
public abstract class ApiRequest {
    Object mLockObject = new Object();
    WeakReference<? extends ApiResponseListener> mApiResponseListenerHolder;
    protected ApiRequest(WeakReference<? extends ApiResponseListener> pApiResponseListener) {
        mApiResponseListenerHolder = pApiResponseListener;
    }

    protected String getBaseUrl() {
        return ApiRequestManager.getBaseUrl();
    }

    public WeakReference<? extends ApiResponseListener> getResponseListenerHolder() {
        return mApiResponseListenerHolder;
    }

    /** use for {@link ApiRequest#getRequestInfo()) only **/
    private RequestInfo mRequestInfo;
    protected RequestInfo getRequestInfo() {
        if (null == mRequestInfo) {
            synchronized (mLockObject) {
                if (null == mRequestInfo) {
                    Class requestClass = getClass();
                    if (requestClass.isAnnotationPresent(RequestInfo.class)) {
                        mRequestInfo = (RequestInfo) requestClass.getAnnotation(RequestInfo.class);
                    }
                }
            }
        }
        return mRequestInfo;
    }
    private void checkRequestInfo() {
        if (null == getRequestInfo()) {
            throw new IllegalStateException("Request info CANNOT be null.");
        }
    }

    public int getTaskId() {
        checkRequestInfo();
        return getRequestInfo().taskId();
    }
    public String getUrl() {
        checkRequestInfo();
        String url = getRequestInfo().url();
        if (url.startsWith("/")) {
            url = getBaseUrl() + url;
        }
        return url;
    }
    public Class getResponseType() {
        checkRequestInfo();
        return getRequestInfo().responseType();
    }
    public abstract HashMap getParameters();
}

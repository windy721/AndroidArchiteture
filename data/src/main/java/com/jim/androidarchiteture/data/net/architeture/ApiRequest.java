package com.jim.androidarchiteture.data.net.architeture;

import java.util.HashMap;

/**
 * Created by JimGong on 2016/5/17.
 */
public abstract class ApiRequest {
    ApiResponseListener mApiResponseListener;
    protected ApiRequest(ApiResponseListener pApiResponseListener) {
        mApiResponseListener = pApiResponseListener;
    }

    protected String getBaseUrl() {
        return ApiRequestManager.getBaseUrl();
    }

    public abstract int getTaskId();
    public abstract String getUrl();
    public abstract HashMap getParameters();
    public abstract Class getResponseType();

    public ApiResponseListener getResponseListener() {
        return mApiResponseListener;
    }
}

package com.jim.androidarchiteture.data.entity;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.jim.androidarchiteture.common.StringUtils;

/**
 * Created by JimGong on 2016/5/12.
 */
public class AppError {
    public String request;
    public String code;
    public String message;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getErrorCode() {
        return code;
    }

    public void setErrorCode(String code) {
        this.code = code;
    }

    public String getError() {
        return message;
    }

    public void setError(String message) {
        this.message = message;
    }

    public AppError(String message) {
        this.message = message;
    }

    public AppError() {
    }

    @Override
    public String toString() {
        return "AppError{" +
                "request='" + request + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static AppError getNetworkError() {
        return new AppError("当前网络不佳，请稍后再试");
    }

    public static AppError getNetworkError(VolleyError pException) {
        if (null == pException || StringUtils.isBlank(pException.getMessage()) || pException instanceof NetworkError) {
            return getNetworkError();
        }
        return new AppError(pException.getMessage());
    }
}

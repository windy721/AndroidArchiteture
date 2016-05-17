package com.jim.androidarchiteture.data.net.architeture;

import com.jim.androidarchiteture.data.entity.AppError;

/**
 * Created by JimGong on 2016/5/17.
 */
public interface ApiResponseListener {
    void onRequestSuccess(int taskId, BaseMessage message);
    void onRequestError(int taskId, AppError error);
}

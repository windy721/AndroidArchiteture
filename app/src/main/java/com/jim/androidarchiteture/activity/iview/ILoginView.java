package com.jim.androidarchiteture.activity.iview;

import com.jim.androidarchiteture.entity.AppError;

/**
 * Created by JimGong on 2016/5/11.
 */
public interface ILoginView {
    String getUserName();
    String getPasswords();

    void onLoginSuccess(String response);
    void onLoginError(AppError pError);
}

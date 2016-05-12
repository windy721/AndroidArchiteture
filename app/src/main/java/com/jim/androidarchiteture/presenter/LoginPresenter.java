package com.jim.androidarchiteture.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jim.androidarchiteture.activity.iview.ILoginView;
import com.jim.androidarchiteture.common.AppLog;
import com.jim.androidarchiteture.common.StringUtils;
import com.jim.androidarchiteture.common.viewutil.ToastOfJH;
import com.jim.androidarchiteture.data.net.BasePostRequest;
import com.jim.androidarchiteture.entity.AppError;

import java.util.HashMap;

/**
 * Created by JimGong on 2016/5/11.
 */
public class LoginPresenter extends BaseModel {
    private Context mContext;
    private ILoginView mLoginView;
    private static final String LOGIN_CODE_URL = getBaseUrl() + "/user/checkNum";

    public LoginPresenter(Context pContext, ILoginView pLoginView) {
        mContext = pContext;
        mLoginView = pLoginView;
    }

    public void login() {
        String userName = mLoginView.getUserName();
        String passwords = mLoginView.getPasswords();
        if (StringUtils.isBlank(userName)) {
            ToastOfJH.showToast(mContext, "Please input the user name.");
            return;
        }
        if (StringUtils.isBlank(passwords)) {
            ToastOfJH.showToast(mContext, "Please input the passwords");
            return;
        }

        HashMap<String, String> mParams = new HashMap<String, String>();
        mParams.put("mobile", userName);
        mParams.put("validatecode", String.valueOf(passwords));

        BasePostRequest request = new BasePostRequest(LOGIN_CODE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppLog.e("onResponse=%s", response);
                if (hasError(response)) {
                    mLoginView.onLoginError(getError());
                    return;
                }
                mLoginView.onLoginSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoginView.onLoginError(AppError.getNetworkError());
            }
        }, mParams);
        syncRequest(request);
    }
}

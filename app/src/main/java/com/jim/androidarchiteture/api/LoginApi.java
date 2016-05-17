package com.jim.androidarchiteture.api;

import com.jim.androidarchiteture.activity.iview.ILoginView;
import com.jim.androidarchiteture.data.net.architeture.ApiRequest;
import com.jim.androidarchiteture.data.net.architeture.Task;

import java.util.HashMap;

/**
 * Created by JimGong on 2016/5/17.
 */
public class LoginApi {
    public static class LoginRequest extends ApiRequest {
        private String mUserName, mPasswords;
        public LoginRequest(ILoginView pLoginView, String userName, String passwords) {
            super(pLoginView);
            mUserName = userName;
            mPasswords = passwords;
        }

        @Override
        public int getTaskId() {
            return Task.TASK_LOGIN;
        }

        @Override
        public String getUrl() {
            return getBaseUrl() + "/user/checkNum";
        }

        @Override
        public HashMap getParameters() {
            HashMap<String, String> param = new HashMap<>();
            param.put("mobile", mUserName);
            param.put("validatecode", String.valueOf(mPasswords));
            return param;
        }

        @Override
        public Class getResponseType() {
            return String.class;
        }
    }
}

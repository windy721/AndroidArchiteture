package com.jim.androidarchiteture.api;

import com.jim.androidarchiteture.activity.iview.ILoginView;
import com.jim.androidarchiteture.data.net.architeture.ApiRequest;
import com.jim.androidarchiteture.data.net.architeture.Task;
import com.jim.androidarchiteture.data.net.architeture.annotation.RequestInfo;

import java.util.HashMap;

/**
 * Created by JimGong on 2016/5/17.
 */
public class LoginApi {
    @RequestInfo(taskId = Task.TASK_LOGIN, url = "/user/checkNum", responseType = String.class)
    public static class LoginRequest extends ApiRequest {
        private String mUserName, mPasswords;
        public LoginRequest(ILoginView pLoginView, String userName, String passwords) {
            super(pLoginView);
            mUserName = userName;
            mPasswords = passwords;
        }

        @Override
        public HashMap getParameters() {
            HashMap<String, String> param = new HashMap<>();
            param.put("mobile", mUserName);
            param.put("validatecode", String.valueOf(mPasswords));
            return param;
        }
    }
}

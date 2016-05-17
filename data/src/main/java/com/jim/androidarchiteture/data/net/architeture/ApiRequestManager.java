package com.jim.androidarchiteture.data.net.architeture;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jim.androidarchiteture.common.AppLog;
import com.jim.androidarchiteture.common.JsonUtil;
import com.jim.androidarchiteture.data.entity.AppError;
import com.jim.androidarchiteture.data.entity.event.TokenOutOfDateEvent;
import com.jim.androidarchiteture.data.net.BasePostRequest;
import com.jim.androidarchiteture.data.net.MultiPartStringRequest;
import com.jim.androidarchiteture.data.net.VolleyUtil;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by JimGong on 2016/5/17.
 */
public final class ApiRequestManager {
    static final String ERROR_CODE_INVALID_TOKEN = "102030";
    static final String ERROR_CODE_INVALID_TOKEN2 = "102020";

    private static Object sLockObj = new Object();
    private static ApiRequestManager sApiRequestManager;

    private ApiRequestManager() {}

    public static ApiRequestManager getInstance() {
        if (null == sApiRequestManager) {
            synchronized (sLockObj) {
                if (null == sApiRequestManager) {
                    sApiRequestManager = new ApiRequestManager();
                }
            }
        }
        return sApiRequestManager;
    }

    protected String response;

    public static String getBaseUrl() {
//        String server = AppSetting.getInstance().getServerIp();
//        return "http://" + server + "/" + PROJECT_NAME;
        return "http://functest.dabai360.com/imapi/";
    }

    public void post(ApiRequest pApiRequest) {
        final int taskId = pApiRequest.getTaskId();
        String url = pApiRequest.getUrl();
        HashMap<String, String> mParams = pApiRequest.getParameters();
        final Class responseType = pApiRequest.getResponseType();
        final ApiResponseListener responseListener = pApiRequest.getResponseListener();

        BasePostRequest request = new BasePostRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppLog.e("onResponse=%s", response);
                if (hasError(response)) {
                    responseListener.onRequestError(taskId, getError());
                    return;
                }
                Object responseObj = JsonUtil.parseJsonObj(response, responseType);
                responseListener.onRequestSuccess(taskId, new BaseMessage(responseObj));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onRequestError(taskId, AppError.getNetworkError());
            }
        }, mParams);
        syncRequest(request);
    }

    AppError getError() {
        return JsonUtil.parseJsonObj(response, AppError.class);
    }

    void syncRequest(Request request) {
        VolleyUtil.syncRequest(request);
    }

    // TODO to add a method (postMuliPartRequest) and make the following method (syncMuliPartRequest) default.
    public void syncMuliPartRequest(MultiPartStringRequest request) {
        VolleyUtil.syncMultiPartRequest(request);
    }

    boolean hasError(String response) {
        this.response = response;
        String errorCode = JsonUtil.getString(response, "code", null);
        if (errorCode != null) {
            if (ERROR_CODE_INVALID_TOKEN.equals(errorCode) || ERROR_CODE_INVALID_TOKEN2.equals(errorCode)) {
                // Token 过期处理
                EventBus.getDefault().post(new TokenOutOfDateEvent());
            }
            return true;
        }
        return false;
    }
}

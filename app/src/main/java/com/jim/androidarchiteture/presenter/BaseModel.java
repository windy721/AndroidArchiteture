package com.jim.androidarchiteture.presenter;

import com.android.volley.Request;
import com.jim.androidarchiteture.common.JsonUtil;
import com.jim.androidarchiteture.data.net.MultiPartStringRequest;
import com.jim.androidarchiteture.data.net.VolleyUtil;
import com.jim.androidarchiteture.entity.AppError;
import com.jim.androidarchiteture.entity.event.TokenOutOfDateEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by JimGong on 2016/5/12.
 */
public abstract class BaseModel<T> {
    static final String ERROR_CODE_INVALID_TOKEN = "102030";
    static final String ERROR_CODE_INVALID_TOKEN2 = "102020";
    protected String response;

    public static String getBaseUrl() {
//        String server = AppSetting.getInstance().getServerIp();
//        return "http://" + server + "/" + PROJECT_NAME;
        return "http://functest.dabai360.com/imapi/";
    }

    protected AppError getError() {
        return JsonUtil.parseJsonObj(response, AppError.class);
    }

    public void syncRequest(Request request) {
        VolleyUtil.syncRequest(request);
    }

    public void syncMuliPartRequest(MultiPartStringRequest request) {
        VolleyUtil.syncMultiPartRequest(request);
    }

    protected boolean hasError(String response) {
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

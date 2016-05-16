package com.jim.androidarchiteture.data.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jim.androidarchiteture.common.AppConfig;
import com.jim.androidarchiteture.common.AppLog;
import com.jim.androidarchiteture.common.ClientInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * TODO <一句话功能描述>
 *
 * @author zhangbl
 * @version V1.0 <描述当前版本功能>
 * @FileName com.wole56.ishow.service.VolleyUtil.java
 * @date 2014年11月12日 下午6:32:21
 */
public class VolleyUtil {
    static Context sContext;
    static Map<String, String> sNetBaseParameter;

    public static void init(Context pApplicationContext, Map<String, String> pNetBaseParameter) {
        sContext = pApplicationContext;
        sNetBaseParameter = pNetBaseParameter;
    }

    private static RequestQueue mQueue = null;

    private static boolean checkInit() {
        if (null == sContext) {
            Log.e("VolleyUtil", "VolleyUtil - null == sContext");
            return false;
        }
        return true;
    }

    public synchronized static void syncRequest(Request request) {
        if (!checkInit()) {
            return;
        }
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(sContext);
        }
        if (AppConfig.DEBUG_MODE && request instanceof BasePostRequest) {
            interceptor(request, "utf-8");
        }
        addRequest(mQueue, request);
    }

    public synchronized static void syncMultiPartRequest(MultiPartStringRequest request) {
        if (!checkInit()) {
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(sContext, new MultiPartStack());
        addRequest(queue, request);
    }

    private static void addRequest(RequestQueue queue, Request request){
        if (!ClientInfo.isNetOk(sContext)) {
            request.getErrorListener().onErrorResponse(new NoConnectionError());
            return;
        }

        queue.add(request);
    }


    private static void interceptor(Request request, String paramsEncoding) {
        BasePostRequest debugRequest = ((BasePostRequest) request);
        Map<String, String> params = null;
        try {
            params = debugRequest.getParams();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            // encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }

        AppLog.e("request-->\n%s", debugRequest.getUrl() + "?" + encodedParams.toString());
    }
}

package com.jim.androidarchiteture.data.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.jim.androidarchiteture.common.ClientInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO <一句话功能描述>
 *
 * @author zhangbl
 * @version V1.0 <描述当前版本功能>
 * @FileName com.wole56.ishow.service.BasePostRequest.java
 * @date 2014年11月12日 下午7:38:02
 */
public class BasePostRequest extends CustomStringRequest {

    private Map<String, String> mParams;

    public BasePostRequest(String url, Listener<String> listener,
                           ErrorListener errorListener, Map<String, String> mParams) {

        super(Method.POST, url, listener, errorListener);
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        }
        this.mParams = mParams;
        setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return addBaseParams(mParams);
    }

    private Map<String, String> addBaseParams(Map<String, String> mParams) {
        if (null == VolleyUtil.sContext) {
            return null;
        }
        Context context = VolleyUtil.sContext;
        String imei = ClientInfo.getIMEI(context);
        String osInfo = ClientInfo.getOSInfo(context);
        String model = ClientInfo.getModel(context);
        String channel = ClientInfo.getApplicationMetaData(context, "UMENG_CHANNEL");
        String versionName = ClientInfo.getAppVersion(context);
        //String umengDeviceToken= ClientInfo.getUmengDeviceToken(context);

        mParams.put("platform", "android");
        mParams.put("imei", String.valueOf(imei));
        mParams.put("osInfo", String.valueOf(osInfo));
        mParams.put("model", String.valueOf(model));
        mParams.put("channel", String.valueOf(channel));
        mParams.put("currentVer", String.valueOf(versionName));
        mParams.put("login", String.valueOf(TokenManager.getInstance().getToken()));
        return mParams;
    }
}

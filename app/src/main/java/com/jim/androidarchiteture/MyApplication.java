package com.jim.androidarchiteture;

import android.app.Application;

import com.jim.androidarchiteture.common.ClientInfo;
import com.jim.androidarchiteture.data.net.VolleyUtil;
import com.jim.androidarchiteture.internal.di.components.AppComponent;
import com.jim.androidarchiteture.internal.di.components.DaggerAppComponent;
import com.jim.androidarchiteture.internal.di.modules.AppModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JimGong on 2016/4/28.
 */
public class MyApplication extends Application {
    public static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        initInject();
        init();
    }

    private void init() {
        VolleyUtil.init(this, getBaseParameter());
    }

    private void initInject() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public Map<String, String> getBaseParameter() {
        String imei = ClientInfo.getIMEI(this);
        String osInfo = ClientInfo.getOSInfo(this);
        String model = ClientInfo.getModel(this);
        String channel = ClientInfo.getApplicationMetaData(this, "UMENG_CHANNEL");
        String versionName = ClientInfo.getAppVersion(this);
        //String umengDeviceToken= ClientInfo.getUmengDeviceToken(context);

        Map<String, String> mParams = new HashMap<>();
        mParams.put("platform", "android");
        mParams.put("imei", String.valueOf(imei));
        mParams.put("osInfo", String.valueOf(osInfo));
        mParams.put("model", String.valueOf(model));
        mParams.put("channel", String.valueOf(channel));
        mParams.put("currentVer", String.valueOf(versionName));
        return mParams;
    }

}

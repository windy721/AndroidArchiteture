package com.jim.androidarchiteture;

import android.app.Application;

import com.jim.androidarchiteture.internal.di.components.AppComponent;
import com.jim.androidarchiteture.internal.di.components.DaggerAppComponent;
import com.jim.androidarchiteture.internal.di.modules.AppModule;

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
        init();
    }

    private void init() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}

package com.jim.androidarchiteture.internal.di.modules;

import android.app.Application;

import java.util.concurrent.ThreadPoolExecutor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JimGong on 2016/4/1.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application application() {
        return mApplication;
    }

    @Provides
    public ThreadPoolExecutor threadPoolExecutor() {
        return null;
    }
}

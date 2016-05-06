package com.jim.androidarchiteture.internal.di.components;

import android.app.Application;
import android.content.Context;

import com.jim.androidarchiteture.MyApplication;
import com.jim.androidarchiteture.internal.di.modules.AppModule;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by JimGong on 2016/4/1.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // Field injections of any dependencies of the DemoApplication
    void inject(MyApplication application);

    Application application();
    ThreadPoolExecutor threadPollExecutor();
//    ApiService apiService();  // 所有Api请求的管理类
//    SpfManager spfManager();  // SharedPreference管理类
//    DBManager dbManager();  // 数据库管理类
}

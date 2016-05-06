package com.jim.androidarchiteture.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jim.androidarchiteture.MyApplication;
import com.jim.androidarchiteture.internal.di.components.ActivityComponent;
import com.jim.androidarchiteture.internal.di.components.DaggerActivityComponent;
import com.jim.androidarchiteture.internal.di.modules.ActivityModule;

import javax.inject.Inject;

/**
 * Created by JimGong on 2016/4/1.
 */
public class BaseActivity extends AppCompatActivity {
    ActivityComponent mActivityComponent;

    @Inject
    protected String mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //  建议写在基类Activity里
    protected ActivityComponent getActivityComponent() {
        if (null == mActivityComponent) {
            synchronized (this) {
                if (null == mActivityComponent) {
                    mActivityComponent = DaggerActivityComponent.builder()
                            .appComponent(((MyApplication) getApplicationContext()).getAppComponent())
                            .activityModule(getActivityModule())
                            .build();
                    mActivityComponent.inject(this);
                }
            }
        }
        return mActivityComponent;
    }

    //  建议写在基类Activity里
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}

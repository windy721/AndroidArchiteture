package com.jim.androidarchiteture.internal.di.components;

import android.app.Activity;

import com.jim.androidarchiteture.activity.BaseActivity;
import com.jim.androidarchiteture.internal.di.PerActivity;
import com.jim.androidarchiteture.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by JimGong on 2016/4/28.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    Activity activity(); // Expose the activity to sub-graphs.
}

package com.jim.androidarchiteture.internal.di.modules;

import android.app.Activity;

import com.jim.androidarchiteture.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JimGong on 2016/4/28.
 */
@Module
public class ActivityModule {
    Activity mActivity;

    public ActivityModule(Activity pActivity) {
        mActivity = pActivity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    public Activity activity() {
        return mActivity;
    }

    @Provides
    String getToolbar() {
        return "JimToolbar";
    }
}

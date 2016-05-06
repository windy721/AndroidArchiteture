package com.jim.androidarchiteture.internal.di.components;

import com.jim.androidarchiteture.internal.di.PerActivity;
import com.jim.androidarchiteture.internal.di.modules.ActivityModule;
import com.jim.androidarchiteture.internal.di.modules.UserModule;

import dagger.Component;

/**
 * Created by JimGong on 2016/5/5.
 */
//@PerActivity
//@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
}

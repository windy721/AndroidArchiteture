package com.jim.androidarchiteture.internal.di.modules;

import com.jim.androidarchiteture.internal.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JimGong on 2016/5/5.
 */
@Module
public class UserModule {
    @Provides
    @PerActivity
    List<String> provideUserList() {
        List<String> users = new ArrayList<>();
        for (int i=0; i<10; i++) {
            users.add("User" + i);
        }
        return users;
    }

    @Provides
    @PerActivity
    String getUserName() {
        return "Jim - User";
    }
}

package com.jim.androidarchiteture.activity.iview;

import com.jim.androidarchiteture.data.net.architeture.ApiResponseListener;

/**
 * Created by JimGong on 2016/5/11.
 */
public interface ILoginView extends ApiResponseListener {
    String getUserName();
    String getPasswords();
}

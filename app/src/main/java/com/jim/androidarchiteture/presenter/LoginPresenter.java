package com.jim.androidarchiteture.presenter;

import android.content.Context;

import com.jim.androidarchiteture.activity.iview.ILoginView;
import com.jim.androidarchiteture.api.LoginApi;
import com.jim.androidarchiteture.common.StringUtils;
import com.jim.androidarchiteture.common.viewutil.ToastOfJH;
import com.jim.androidarchiteture.data.net.architeture.ApiRequestManager;

/**
 * Created by JimGong on 2016/5/11.
 */
public class LoginPresenter extends BasePresenter<ILoginView> {
    private Context mContext;

    public LoginPresenter(Context pContext, ILoginView pLoginView) {
        mContext = pContext;
        attachView(pLoginView);
    }

    public void login() {
        if (!isViewAttached()) {
            return;
        }
        String userName = getView().getUserName();
        String passwords = getView().getPasswords();
        if (StringUtils.isBlank(userName)) {
            ToastOfJH.showToast(mContext, "Please input the user name.");
            return;
        }
        if (StringUtils.isBlank(passwords)) {
            ToastOfJH.showToast(mContext, "Please input the passwords");
            return;
        }

        ApiRequestManager.getInstance().post(new LoginApi.LoginRequest(getViewHolder(), userName, passwords));
    }
}

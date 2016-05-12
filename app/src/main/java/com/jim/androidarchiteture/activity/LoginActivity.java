package com.jim.androidarchiteture.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jim.androidarchiteture.R;
import com.jim.androidarchiteture.activity.iview.ILoginView;
import com.jim.androidarchiteture.common.viewutil.ToastOfJH;
import com.jim.androidarchiteture.entity.AppError;
import com.jim.androidarchiteture.presenter.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JimGong on 2016/5/11.
 */
public class LoginActivity extends BaseActivity implements ILoginView {
    @Bind(R.id.login_userNameTxt)
    EditText mUserNameTxt;
    @Bind(R.id.login_passwordsTxt)
    EditText mPasswordsTxt;

    LoginPresenter mLoginPresenter;

    @Override
    public void createActivityView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this, this);
    }

    @OnClick(R.id.login_loginBtn)
    public void onClick(View pView) {
        mLoginPresenter.login();
    }

    @Override
    public String getUserName() {
        return mUserNameTxt.getText().toString();
    }

    @Override
    public String getPasswords() {
        return mPasswordsTxt.getText().toString();
    }

    @Override
    public void onLoginSuccess(String response) {
        ToastOfJH.showToast(this, response);
        Log.e("TEST", "Result: " + response);
    }

    @Override
    public void onLoginError(AppError pError) {
        ToastOfJH.showToast(this, pError.message);
    }
}

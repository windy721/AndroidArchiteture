package com.jim.androidarchiteture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jim.androidarchiteture.activity.BaseActivity;
import com.jim.androidarchiteture.activity.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    public void createActivityView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        initInject();
        init();
        getToolBar();
        ButterKnife.bind(this);
    }

    private void initInject() {
        getActivityComponent().inject(this);
    }

    private void init() {
        Toast.makeText(this, "XXX: " + mToolBar, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.main_loginBtn)
    public void onClick(View pView) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}

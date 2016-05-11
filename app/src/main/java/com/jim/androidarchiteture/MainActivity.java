package com.jim.androidarchiteture;

import android.os.Bundle;
import android.widget.Toast;

import com.jim.androidarchiteture.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void createActivityView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        initInject();
        init();
        getToolBar();
    }

    private void initInject() {
        getActivityComponent().inject(this);
    }

    private void init() {
        Toast.makeText(this, "XXX: " + mToolBar, Toast.LENGTH_SHORT).show();
    }
}

package com.jim.androidarchiteture;

import android.os.Bundle;
import android.widget.Toast;

import com.jim.androidarchiteture.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInject();
        init();
    }

    private void initInject() {
        getActivityComponent().inject(this);
    }

    private void init() {
        Toast.makeText(this, "XXX: " + mToolBar, Toast.LENGTH_SHORT).show();
    }
}

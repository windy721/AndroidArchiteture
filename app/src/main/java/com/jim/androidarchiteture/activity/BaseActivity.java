package com.jim.androidarchiteture.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.jim.androidarchiteture.MyApplication;
import com.jim.androidarchiteture.R;
import com.jim.androidarchiteture.internal.di.components.ActivityComponent;
import com.jim.androidarchiteture.internal.di.components.DaggerActivityComponent;
import com.jim.androidarchiteture.internal.di.modules.ActivityModule;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by JimGong on 2016/4/1.
 */
public abstract class BaseActivity extends AppCompatActivity {
    ActivityComponent mActivityComponent;

    protected Toolbar mToolBar;

    public abstract void createActivityView(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivityView(savedInstanceState);
    }

    private void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (null == mToolBar) {
            return;
        }
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
//            mToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    protected Toolbar getToolBar() {
        if (null == mToolBar) {
            initToolBar();
        }
        return mToolBar;
    }

    protected void setToolBarTitle(String titleTv) {
        //init toolbar
        getToolBar();
        getSupportActionBar().setTitle(titleTv);
    }

    protected void setToolBarTitle(int resId) {
        getToolBar();
        getSupportActionBar().setTitle(resId);
    }

    protected void setToolBarCloseOnNevigationClick(boolean pActive) {
        getToolBar().setNavigationOnClickListener(pActive ?
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                } : null
        );
        if (!pActive) {
            mToolBar.setNavigationIcon(null);
        }
    }

    /********************************** Status bar - S ******************************/
    @TargetApi(19)
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getStatusBarTintColor());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    protected int getStatusBarTintColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
    /********************************** Status bar - E ******************************/

    /********************************** Dagger - S ******************************/
    //  建议写在基类Activity里
    protected ActivityComponent getActivityComponent() {
        if (null == mActivityComponent) {
            synchronized (this) {
                if (null == mActivityComponent) {
                    mActivityComponent = DaggerActivityComponent.builder()
                            .appComponent(((MyApplication) getApplicationContext()).getAppComponent())
                            .activityModule(getActivityModule())
                            .build();
                    mActivityComponent.inject(this);
                }
            }
        }
        return mActivityComponent;
    }

    //  建议写在基类Activity里
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
    /********************************** Dagger - E ******************************/
}

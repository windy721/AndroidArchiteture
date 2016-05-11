package com.jim.androidarchiteture.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jim.androidarchiteture.MyApplication;
import com.jim.androidarchiteture.R;
import com.jim.androidarchiteture.common.InputManagerUtils;
import com.jim.androidarchiteture.common.StringUtils;
import com.jim.androidarchiteture.common.viewutil.ViewUtils;
import com.jim.androidarchiteture.internal.di.components.ActivityComponent;
import com.jim.androidarchiteture.internal.di.components.DaggerActivityComponent;
import com.jim.androidarchiteture.internal.di.modules.ActivityModule;
import com.jim.androidarchiteture.widget.ProgressDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by JimGong on 2016/4/1.
 */
public abstract class BaseActivity extends AppCompatActivity {
    ActivityComponent mActivityComponent;

    protected static final int GRAVITY_WHITE = 1;
    protected static final int GRAVITY_TRANSPARENT = 0;
    protected Toolbar mToolBar;
    protected LinearLayout mLoadingView;
    private LinearLayout mNodataView;
    private LinearLayout mNetErrorView;
    private ProgressDialog mProgressDialog;

    public abstract void createActivityView(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivityView(savedInstanceState);
    }

    /**********************************
     * Tool bar - S
     ******************************/
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
    /********************************** Tool bar - E ******************************/

    /**********************************
     * Status bar color - S
     ******************************/
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
    /********************************** Status bar color - E ******************************/

    /**********************************
     * Dagger - S
     ******************************/
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

    /********************************** Common view showing - S ******************************/
    private ViewGroup createNetErrorLayout(View.OnClickListener listener) {
        if (mNetErrorView != null) {
            return mNetErrorView;
        }

        mNetErrorView = (LinearLayout) ViewUtils.getItemView(this, R.layout.layout_neterror);
        Button retryBtn = (Button) mNetErrorView.findViewById(R.id.neterror_retryBtn);
        retryBtn.setOnClickListener(listener);
        return mNetErrorView;
    }

    public void showNetError(View.OnClickListener retryListener) {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        if (null != mNetErrorView) {
            rootView.removeView(mNetErrorView);
        }

        rootView.addView(createNetErrorLayout(retryListener));
    }

    public void hiddenNetError() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.removeView(mNetErrorView);
    }

    private ViewGroup createLoadingLayout(int gravity) {
        if (mLoadingView != null) {
            return mLoadingView;
        }
        mLoadingView = (LinearLayout) ViewUtils.getItemView(this, R.layout.layout_loading);
        mLoadingView.setBackgroundResource(android.R.color.transparent);
        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLoading();
            }
        });
        return mLoadingView;
    }

    public void showLoading() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        if (null != mLoadingView) {
            rootView.removeView(mLoadingView);
        }

        rootView.addView(createLoadingLayout(GRAVITY_TRANSPARENT));
    }

    public void showBlurLoading() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.removeView(mLoadingView);
        rootView.addView(createLoadingLayout(GRAVITY_WHITE));
    }

    public void hiddenLoading() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.removeView(mLoadingView);
    }

    public void showProgressDialog() {
        showProgressDialog("请稍后");
    }

    public void showProgressDialog(String tips) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTips(tips);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected ViewGroup createNodataView(int noDataResId, String tips) {
        if (mNodataView != null) {
            return mNodataView;
        }
        mNodataView = (LinearLayout) ViewUtils.getItemView(this, R.layout.layout_nodata);

        ImageView noDataIv = (ImageView) mNodataView.findViewById(R.id.nodata_iv);
        if (noDataResId != -1) {
            noDataIv.setVisibility(View.VISIBLE);
            noDataIv.setBackgroundResource(noDataResId);
        }

        TextView noDataTv = (TextView) mNodataView.findViewById(R.id.nodata_tv);
        if (!StringUtils.isBlank(tips)) {
            noDataTv.setText(tips);
        }
        mNodataView.setBackgroundResource(android.R.color.transparent);
        return mNodataView;
    }

    protected void showNodataView(String tips) {
        hiddenNoDataView();
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(createNodataView(-1, tips));
    }

    protected void showNodataView(int noDataResId, String tips) {
        hiddenNoDataView();
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();

        rootView.addView(createNodataView(noDataResId, tips));
    }

    protected void hiddenNoDataView() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.removeView(mNodataView);
    }

    // Other utility
    protected void hidenSoftKeyBoard() {
        getCurrentFocus().clearFocus();
        View focusView = getCurrentFocus();
        if (focusView == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    // 点击EditText文本框外任何地方隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (InputManagerUtils.isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
}

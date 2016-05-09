package com.jim.androidarchiteture.data.net;

/**
 * Created by JimGong on 2016/5/9.
 */
public class TokenManager {
    private static Object sLocker = new Object();
    private static TokenManager sTokenManager;

    private String mToken;
    private TokenManager() {}

    public static TokenManager getInstance() {
        if (null == sTokenManager) {
            synchronized (sLocker) {
                if (null == sTokenManager) {
                    sTokenManager = new TokenManager();
                }
            }
        }
        return sTokenManager;
    }

    public void setToken(String pToken) {
        if ((null == mToken && null == pToken) || (null != mToken && mToken.equals(pToken))) {
            // no changed
            return;
        }
        mToken = pToken;
        fireOnTokenChanged(mToken);
    }

    public String getToken() {
        return mToken;
    }

    // Listener
    private OnTokenChangedListener mOnTokenChangedListener;
    public void setOnTokenChangedListener(OnTokenChangedListener pOnTokenChangedListener) {
        mOnTokenChangedListener = pOnTokenChangedListener;
    }
    private void fireOnTokenChanged(String pNewToken) {
        if (null == mOnTokenChangedListener) {
            return;
        }
        mOnTokenChangedListener.onTokenChanged(pNewToken);
    }

    public interface OnTokenChangedListener {
        void onTokenChanged(String pNewToken);
    }
}

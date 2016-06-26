package com.lionelwei.together.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.lionelwei.together.config.preference.Preferences;
import com.lionelwei.together.ui.component.login.LoginActivity;

public class WelcomeActivity extends Activity {
    private static final String TAG = "Welcome";

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 闪屏页不需要设置layout
        // @see https://www.bignerdranch.com/blog/splash-screens-the-right-way/
//        setContentView(R.layout.activity_welcome);

        // 闪屏页消失的太快, 延迟一下...
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.start(WelcomeActivity.this);
                overridePendingTransition(0, 0);
                finish();
            }
        }, 500);
    }

    private boolean canAutoLogin() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        Log.i(TAG, "get local sdk token =" + token);
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
    }

    private void showMainActivity() {
        showMainActivity(null);
    }

    private void showMainActivity(Intent intent) {
        MainActivity.start(WelcomeActivity.this, intent);
        finish();
    }

}

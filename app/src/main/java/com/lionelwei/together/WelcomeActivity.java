package com.lionelwei.together;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.lionelwei.together.config.AccountCache;
import com.lionelwei.together.config.preference.Preferences;
import com.lionelwei.together.component.login.LoginActivity;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

public class WelcomeActivity extends Activity {
    private static final String TAG = "Welcome";

    private static boolean mIsFirstLaunch = true;
    private boolean mIsShowSplash = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // 闪屏页不需要设置layout
        // @see https://www.bignerdranch.com/blog/splash-screens-the-right-way/
        // 更新: 采用云信demo的闪屏页方案
        setContentView(R.layout.activity_welcome);

        if (!mIsFirstLaunch) {
            onIntent();
        } else {
            showSplashView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsFirstLaunch) {
            mIsFirstLaunch = false;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (canAutoLogin()) {
                        onIntent();
                    } else {
                        LoginActivity.start(WelcomeActivity.this);
                        finish();
                    }
                }
            };
            if (mIsShowSplash) {
                new Handler().postDelayed(runnable, 500);
            } else {
                runnable.run();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        /**
         * 如果Activity在，不会走到onCreate，而是onNewIntent，这时候需要setIntent
         * 场景：点击通知栏跳转到此，会收到Intent
         */
        setIntent(intent);
        onIntent();
    }

    // 处理收到的Intent
    private void onIntent() {
        LogUtil.i(TAG, "onIntent...");

        if (TextUtils.isEmpty(AccountCache.getAccount())) {
            // 判断当前app是否正在运行
            LoginActivity.start(this);
            finish();
        } else {
            // 已经登录过了，处理过来的请求
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
                    parseNotifyIntent(intent);
                    return;
                }
            }
            if (!mIsFirstLaunch && intent == null) {
                finish();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showMainActivity();
                    }
                }, 500);
            }
        }
    }

    private boolean canAutoLogin() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        Log.i(TAG, "get local sdk token =" + token);
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
    }

    private void showSplashView() {
        getWindow().setBackgroundDrawableResource(R.drawable.background_splash);
        mIsShowSplash = true;
    }

    @SuppressWarnings("unchecked")
    private void parseNotifyIntent(Intent intent) {
        ArrayList<IMMessage> messages =
                (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
        if (messages == null || messages.size() > 1) {
            showMainActivity(null);
        } else {
            showMainActivity(new Intent().putExtra(NimIntent.EXTRA_NOTIFY_CONTENT, messages.get(0)));
        }
    }

    private void showMainActivity() {
        showMainActivity(null);
    }

    private void showMainActivity(Intent intent) {
        MainActivity.start(WelcomeActivity.this, intent);
        finish();
        overridePendingTransition(0, 0);
    }

}

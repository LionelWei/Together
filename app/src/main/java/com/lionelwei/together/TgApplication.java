package com.lionelwei.together;

import android.app.Application;

import com.lionelwei.together.nimsdk.Nim;
import com.lionelwei.together.common.util.AppUtil;

public class TgApplication extends Application{
    private static TgApplication mContext;
    public static TgApplication getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 初始化网易云信
        Nim.getInstace().initClient();
        if (AppUtil.inMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            Nim.getInstace().initUIKit();
        }
    }

}

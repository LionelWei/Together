package com.lionelwei.together.common.util;

import android.content.Context;
import android.widget.Toast;

import com.lionelwei.together.TgApplication;

/**
 * Created by Lionel on 2016/6/26.
 */
public class ToastUtil {
    private static Context mContext = TgApplication.getContext();
    public static void show(int stringId) {
        Toast.makeText(mContext, stringId, Toast.LENGTH_SHORT).show();
    }
    public static void show(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }
}

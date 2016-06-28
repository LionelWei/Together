package com.lionelwei.together.config;

import com.netease.nim.uikit.NimUIKit;

/**
 * Created by Lionel on 2016/6/29.
 */
public class AccountCache {
    private static String account;
    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        AccountCache.account = account;
        NimUIKit.setAccount(account);
    }
}

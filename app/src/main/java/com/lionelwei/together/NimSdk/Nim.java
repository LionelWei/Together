package com.lionelwei.together.nimsdk;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/25 1.00 初始版本
 */

import android.content.Context;

import com.lionelwei.together.TgApplication;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.contact.ContactProvider;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

public class Nim {

    private static Nim INSTACE = new Nim();

    private Context mContext;
    private UserInfoProvider mUserInfoProvider;
    private NimOption mNimOption;

    public static Nim getInstace() {
        return INSTACE;
    }

    public void initClient() {
        doInit();
        NIMClient.init(mContext, NimLogin.loginInfo(), mNimOption.options());
    }

    // 禁用默认构造器
    private Nim() {
        mContext = TgApplication.getContext();
    }

    private void doInit() {
        if (mContext == null) {
            mContext = TgApplication.getContext();
        }
        mUserInfoProvider = new NimUserInfoProvider();
        mNimOption = new NimOption(mContext, mUserInfoProvider);
    }

    public void initUIKit() {
        // 初始化，需要传入用户信息提供者
        NimUIKit.init(mContext, mUserInfoProvider, contactProvider);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new NimLocationProvider());

        // 会话窗口的定制初始化。 什么鬼? TODO
//        SessionHelper.init();

        // 通讯录列表定制初始化? TODO
//        ContactHelper.init();
    }

    private ContactProvider contactProvider = new ContactProvider() {
        @Override
        public List<UserInfoProvider.UserInfo> getUserInfoOfMyFriends() {
            List<NimUserInfo> nimUsers = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
            List<UserInfoProvider.UserInfo> users = new ArrayList<>(nimUsers.size());
            if (!nimUsers.isEmpty()) {
                users.addAll(nimUsers);
            }

            return users;
        }

        @Override
        public int getMyFriendsCount() {
            return FriendDataCache.getInstance().getMyFriendCounts();
        }

        @Override
        public String getUserDisplayName(String account) {
            return NimUserInfoCache.getInstance().getUserDisplayName(account);
        }
    };


}

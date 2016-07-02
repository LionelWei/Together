package com.lionelwei.together.common.helper;

import android.content.Context;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

/**
 * Created by Lionel on 2016/7/2.
 */
public class SessionHelper {

    public static void startP2PSession(Context context, String account) {
        NimUIKit.startChatting(context, account, SessionTypeEnum.P2P, null);
    }

}

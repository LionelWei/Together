package com.lionelwei.together.nimsdk;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/25 1.00 初始版本
 */

import android.graphics.Bitmap;

import com.lionelwei.together.R;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

public class NimUserInfoProvider implements UserInfoProvider {
    @Override
    public UserInfo getUserInfo(String account) {
        return null;
    }

    @Override
    public int getDefaultIconResId() {
        return R.drawable.ico_user_pic_default;
    }

    @Override
    public Bitmap getTeamIcon(String tid) {
        return null;
    }

    @Override
    public Bitmap getAvatarForMessageNotifier(String account) {
        return null;
    }

    @Override
    public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                   SessionTypeEnum sessionType) {
        return null;
    }
}

package com.lionelwei.together.NimSdk;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/25 1.00 初始版本
 */

import com.netease.nimlib.sdk.auth.LoginInfo;

public class NimLogin {
    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    public static LoginInfo loginInfo() {
        // 从本地读取上次登录成功时保存的用户登录信息
/*
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
*/
        return null;
    }
}

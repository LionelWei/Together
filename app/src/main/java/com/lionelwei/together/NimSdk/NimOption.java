package com.lionelwei.together.nimsdk;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/25 1.00 初始版本
 */

import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Environment;

import com.lionelwei.together.R;
import com.lionelwei.together.WelcomeActivity;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

public class NimOption {
    Context mContext;
    UserInfoProvider mUserInfoProvider;

    public NimOption(Context context, UserInfoProvider userInfoProvider) {
        mContext = context;
        mUserInfoProvider = userInfoProvider;
    }
    // 如果返回值为 null，则全部使用默认参数。
    public SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = WelcomeActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.notification_small_icon;
        // 呼吸灯配置
        config.ledARGB = Color.BLUE;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound =
                RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        .toString();
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory()
                + "/" + mContext.getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 100; // ${Screen.width} / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = mUserInfoProvider;
        return options;
    }
}

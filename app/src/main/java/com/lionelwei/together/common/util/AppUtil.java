package com.lionelwei.together.common.util;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/25 1.00 初始版本
 */

import android.content.Context;

import com.lionelwei.together.TgApplication;

public class AppUtil {
    private static Context mAppContext = TgApplication.getContext();

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = SystemUtil.getProcessName(context);
        return packageName.equals(processName);
    }

}

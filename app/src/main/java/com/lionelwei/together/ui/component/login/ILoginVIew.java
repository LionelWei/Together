package com.lionelwei.together.ui.component.login;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/28 1.00 初始版本
 */

public interface ILoginVIew {
    void onNetworkError();
    void onLoginAccOrPwdError(String errorMsg, int code);
    void onLoginSuccess();
    void onRegisterSuccess();
    void onRegisterFailed();
    void showRegLoading();
    void showLogLoading();
    void hideLoading();
}

package com.lionelwei.together.model.rest.user;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/27 1.00 初始版本
 */

import retrofit2.Call;
import retrofit2.http.POST;

public class LoginApi {
    /**
     * 创建账号
     *  POST https://api.netease.im/nimserver/user/create.action HTTP/1.1
     *  Content-Type:application/x-www-form-urlencoded;charset=utf-8
     * @return
     */
    @POST()
    public Call<Object> register();

    /**
     * 登录
     */
    @POST()
    public Call<Object> login();
}

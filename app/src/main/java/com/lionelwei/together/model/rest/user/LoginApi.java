package com.lionelwei.together.model.rest.user;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/27 1.00 初始版本
 */

import com.lionelwei.together.model.entity.user.BaseBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginApi {
    String BASE_URL = "http://api.netease.im/nimserver/user/";
    /**
     * 创建账号
     *  POST https://api.netease.im/nimserver/user/create.action HTTP/1.1
     *  Content-Type:application/x-www-form-urlencoded;charset=utf-8
     * @return
     */
    @FormUrlEncoded
    @POST("create.action")
    Observable<BaseBean> register(
            @Field("accid") String accountId,
            @Field("name") String nickName,
            @Field("token") String token);

    /**
     * 登录
     */
    @POST()
    Call<Object> login();
}

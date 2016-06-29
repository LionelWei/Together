package com.lionelwei.together.model.rest.user;

import com.lionelwei.together.model.entity.user.UserProfileBean;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Lionel on 2016/6/30.
 */
public interface UserProfileApi {
    @POST("getUinfos.action")
    Observable<UserProfileBean> getUserProfile();
}

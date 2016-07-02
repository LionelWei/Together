package com.lionelwei.together.component.mycenter;

import android.content.Context;

import com.lionelwei.together.common.util.ToastUtil;
import com.lionelwei.together.config.AccountCache;
import com.lionelwei.together.model.entity.user.UserProfileBean;
import com.lionelwei.together.model.rest.BaseUrl;
import com.lionelwei.together.model.rest.core.ServiceGenerator;
import com.lionelwei.together.model.rest.user.UserProfileApi;
import com.lionelwei.together.presenter.AbsPresenter;

import org.json.JSONArray;
import org.json.JSONException;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lionel on 2016/7/2.
 */
public class MyCenterPresenter extends AbsPresenter{
    private IMyCenterView mView;
    private UserProfileApi mProfileApi;
    private String mAccountId;
    private String mNickName;

    public MyCenterPresenter(Context context) {
        super(context);
    }

    public MyCenterPresenter(Context context, IMyCenterView view) {
        super(context);
        mView = view;
    }

    public void getUserProfile() {
        mProfileApi = new ServiceGenerator.Builder()
                .baseUrl(BaseUrl.USER)
                .build()
                .createService(UserProfileApi.class);

        JSONArray array = new JSONArray();
        try {
            array.put(0, AccountCache.getAccount());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mProfileApi.getUserProfile(array)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserProfileBean>() {
                    @Override
                    public void call(UserProfileBean userProfileBean) {
                        for (UserProfileBean.UinfosBean userInfo : userProfileBean.uinfos) {
                            mAccountId = userInfo.accid;
                            mNickName = userInfo.name;
                            // 只取第一组数据
                            break;
                        }

                        mView.onProfileUpdated(new ProfileBean(mAccountId, mNickName));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.show("Get user info error");
                    }
                });
    }

    public void clearLogs() {

    }

    public void logout() {

    }

}

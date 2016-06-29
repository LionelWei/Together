package com.lionelwei.together.ui.component.login;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/28 1.00 初始版本
 */

import android.content.Context;
import android.util.Log;

import com.lionelwei.together.TgApplication;
import com.lionelwei.together.common.util.KeyUtil;
import com.lionelwei.together.config.AccountCache;
import com.lionelwei.together.config.preference.Preferences;
import com.lionelwei.together.model.entity.user.BaseBean;
import com.lionelwei.together.model.rest.BaseUrl;
import com.lionelwei.together.model.rest.core.ServiceGenerator;
import com.lionelwei.together.model.rest.user.LoginApi;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginPresenter {
    private ILoginVIew mLoginView;
    private Retrofit mRetrofit;
    private LoginApi mLoginApi;
    private Context mAppContext;
    private AbortableFuture<LoginInfo> mLoginRequest;

    public LoginPresenter(ILoginVIew loginVIew) {
        mLoginView = loginVIew;
        mAppContext = TgApplication.getContext();
        init();
    }

    public void login(final String account, final String password){
        mLoginView.showLogLoading();
        // 云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
        // 在这里直接使用同步到云信服务器的帐号和token登录。
        // 这里为了简便起见，demo就直接使用了密码的md5作为token。
        // 如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
        final String token = tokenFromPassword(password);
        Log.d("MY_LOGIN", "acc: " + account + ", pwd: " + password + ", token: " + token);
        // 登录
        mLoginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        mLoginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.d("MY_LOGIN", "login success");
                saveLoginInfo(account, token);
                mLoginView.onLoginSuccess();
                mLoginView.hideLoading();

/*
                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                if (UserPreferences.getStatusConfig() == null) {
                    UserPreferences.setStatusConfig(DemoCache.getNotificationConfig());
                }
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();
*/
            }

            @Override
            public void onFailed(int code) {
                mLoginView.hideLoading();
                if (code == 302) {
                    mLoginView.onLoginAccOrPwdError("Account or password error", code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                mLoginView.onLoginAccOrPwdError("Login error", -1);
                mLoginView.hideLoading();
            }
        });
    }

    public void register(final String accountId, String nickName, String password){
        if (!NetworkUtil.isNetAvailable(mAppContext)) {
            mLoginView.onNetworkError();
            return;
        }

        mLoginView.showRegLoading();

        String token = tokenFromPassword(password);
        Log.d("MY_LOGIN", "register pwd: " + password + ", token: " + token);

        long curTime = System.currentTimeMillis();
        String nonce = KeyUtil.getNonce();
        String checkSum = KeyUtil.getCheckSum(nonce, curTime + "");

        Map<String, String> headers = new HashMap<>();
        headers.put("AppKey", KeyUtil.getAppKey());
        headers.put("Nonce", nonce);
        headers.put("CurTime", curTime + "");
        headers.put("CheckSum", checkSum);
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        mLoginApi.register(headers, accountId, nickName, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        Log.d("MY_LOGIN", "code: " + baseBean.code);
                        Log.d("MY_LOGIN", "result: " + baseBean.result);
                        mLoginView.hideLoading();
                        if (baseBean.code != 200) {
                            mLoginView.onRegisterFailed();
                        } else {
                            AccountCache.setAccount(accountId);
                            mLoginView.onRegisterSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mLoginView.hideLoading();
                        mLoginView.onRegisterFailed();
                    }
                });
    }

    private void init() {
        initNetwork();
    }

    private void initNetwork() {
        mLoginApi = new ServiceGenerator.Builder()
                .baseUrl(BaseUrl.USER)
                .build()
                .createService(LoginApi.class);
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        return MD5.getStringMD5(password);
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }
}

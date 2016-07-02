package com.lionelwei.together.component.mycenter;

/**
 * Created by Lionel on 2016/7/2.
 */
public class ProfileBean {
    public ProfileBean(String accountId, String nickName) {
        this.accountId = accountId;
        this.nickName = nickName;
    }

    public ProfileBean(String accountId, String nickName, String avatar) {
        this.accountId = accountId;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public String accountId;
    public String nickName;
    public String avatar;


}

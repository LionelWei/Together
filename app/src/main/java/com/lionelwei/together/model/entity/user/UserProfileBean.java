package com.lionelwei.together.model.entity.user;

import java.util.List;

/**
 * Created by Lionel on 2016/6/30.
 */
public class UserProfileBean {

    /**
     * code : 200
     * uinfos : [{"email":"t1@163.com","accid":"t1","name":"abc","gender":1,"mobile":"18645454545"},{"accid":"t2","name":"def","gender":0}]
     */

    public String code;
    /**
     * email : t1@163.com
     * accid : t1
     * name : abc
     * gender : 1
     * mobile : 18645454545
     */

    public List<UinfosBean> uinfos;

    public static class UinfosBean {
        public String email;
        public String accid;
        public String name;
        public int gender;
        public String mobile;
    }
}

package com.lionelwei.together.model.entity.user;

/*
 * FileName:	
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		weilai
 * Description:	<文件描述>
 * History:		2016/6/28 1.00 初始版本
 */

import com.alibaba.fastjson.annotation.JSONField;

public class BaseBean {
    /**
     * code : 200
     * info : {"token":"xx","accid":"xx","name":"xx"}
     */

    //不保证字段不变 后期再调整
    public int code;
    @JSONField(name="info")
    public String result;
}

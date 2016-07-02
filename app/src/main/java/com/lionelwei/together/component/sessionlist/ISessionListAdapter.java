package com.lionelwei.together.component.sessionlist;

import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * Created by Lionel on 2016/7/2.
 */
public interface ISessionListAdapter {
    void setPresenter(SessionListPresenter presenter);
    void updateData(List<RecentContact> data);
}

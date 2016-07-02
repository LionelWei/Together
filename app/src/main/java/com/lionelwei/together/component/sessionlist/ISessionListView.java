package com.lionelwei.together.component.sessionlist;

/**
 * Created by Lionel on 2016/7/2.
 */
public interface ISessionListView {
    void onLoadingData();
    void onNoData();
    void onShowData();
    void onError();
}

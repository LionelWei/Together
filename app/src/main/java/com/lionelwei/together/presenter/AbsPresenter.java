package com.lionelwei.together.presenter;

import android.content.Context;

import com.lionelwei.together.interfaces.IAdapter;

/**
 * Created by Lionel on 2016/7/2.
 */
public class AbsPresenter {
    protected Context mContext;
    protected IAdapter mAdapter;
    public AbsPresenter(Context context) {
        mContext = context;
    }

    public AbsPresenter(Context context, IAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }
}

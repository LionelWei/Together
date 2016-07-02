package com.lionelwei.together.component.sessionlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lionelwei.together.R;
import com.netease.nim.uikit.common.fragment.TFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionListFragment extends TFragment implements ISessionListView {
    public static final String TAG_FRAGMENT = "SessionListFragment";

    private Context mContext;
    private Unbinder mUnBinder;
    private SessionListPresenter mPresenter;
    private SessionListAdapterImpl mAdapter;

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;

    public SessionListFragment() {
        // Required empty public constructor
    }

    public static SessionListFragment newInstance() {
        SessionListFragment fragment = new SessionListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_session_list, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initPresenter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unRegisterObserver();
        mUnBinder.unbind();
    }

    @Override
    public void onLoadingData() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onNoData() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onShowData() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        mRecyclerView.setVisibility(View.GONE);
    }

    private void initView() {
        mAdapter = new SessionListAdapterImpl(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initPresenter() {
        mPresenter = new SessionListPresenter(this, mAdapter);
        mPresenter.requestData();
        mPresenter.registerObserver();
        mAdapter.setPresenter(mPresenter);
    }
}

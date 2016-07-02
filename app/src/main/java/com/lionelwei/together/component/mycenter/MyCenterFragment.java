package com.lionelwei.together.component.mycenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lionelwei.together.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCenterFragment extends Fragment implements IMyCenterView {
    public static final String TAG_FRAGMENT = "MyCenterFragment";
    public static final String TAG = "MyCenterFragment";
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_id)
    TextView tvId;

    private Unbinder unbinder;
    private MyCenterPresenter mPresenter;

    public MyCenterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyCenterFragment.
     */
    public static MyCenterFragment newInstance() {
        MyCenterFragment fragment = new MyCenterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_center, container, false);
        unbinder = ButterKnife.bind(this, view);

        mPresenter = new MyCenterPresenter(getActivity(), this);
        mPresenter.getUserProfile();
        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.clear_logs)
    void clearLogs() {
        mPresenter.clearLogs();
    }

    @OnClick(R.id.clear_logs)
    void logout() {
        mPresenter.logout();
    }

    @Override
    public void onProfileUpdated(ProfileBean profileBean) {
        tvId.setText(profileBean.accountId);
        tvNickName.setText(profileBean.nickName);
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onClearChatLogs() {

    }
}

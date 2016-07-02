package com.lionelwei.together.ui.component.sessionlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lionelwei.together.R;
import com.lionelwei.together.common.util.TestUtil;
import com.lionelwei.together.config.AccountCache;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionListFragment extends Fragment {
    public static final String TAG_FRAGMENT = "SessionListFragment";
    // TODO
/*
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
*/
    Context mContext;

    @BindView(R.id.jump_test)
    TextView mJumpText;


    private Unbinder mUnBinder;

    public SessionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.*
     *
     * @return A new instance of fragment SessionListFragment.
     */
    public static SessionListFragment newInstance() {
        SessionListFragment fragment = new SessionListFragment();
        return fragment;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }


    @OnClick(R.id.jump_test)
    public void click() {
        String account = AccountCache.getAccount();
        // 聊天对象
        String chatObject =
                TestUtil.TEST_ACCOUNT_1.equals(account)
                ? TestUtil.TEST_ACCOUNT_2
                : TestUtil.TEST_ACCOUNT_1;
        NimUIKit.startChatting(mContext, chatObject, SessionTypeEnum.P2P, null);

//        ConversationActivity.start(mContext);
    }

    private void initRecyclerView() {
/*
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BookshelfAdapter();
        mRecyclerView.setAdapter(mAdapter);
*/
    }

}

package com.lionelwei.together.ui.component.mycenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lionelwei.together.R;
import com.lionelwei.together.common.util.KeyUtil;
import com.lionelwei.together.common.util.ToastUtil;
import com.lionelwei.together.model.entity.user.UserProfileBean;
import com.lionelwei.together.model.rest.BaseUrl;
import com.lionelwei.together.model.rest.core.ServiceGenerator;
import com.lionelwei.together.model.rest.user.UserProfileApi;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCenterFragment extends Fragment {
    public static final String TAG_FRAGMENT = "MyCenterFragment";
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_id)
    TextView tvId;

    private Unbinder unbinder;
    private UserProfileApi mProfileApi;
    private String mAccountId;
    private String mNickName;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        getUserProfile();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getUserProfile() {
        long curTime = System.currentTimeMillis();
        String nonce = KeyUtil.getNonce();
        String checkSum = KeyUtil.getCheckSum(nonce, curTime + "");

        Map<String, String> headers = new HashMap<>();
        headers.put("AppKey", KeyUtil.getAppKey());
        headers.put("Nonce", nonce);
        headers.put("CurTime", curTime + "");
        headers.put("CheckSum", checkSum);
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        mProfileApi = new ServiceGenerator.Builder()
                .baseUrl(BaseUrl.USER)
                .headers(headers)
                .build()
                .createService(UserProfileApi.class);
        
        mProfileApi.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserProfileBean>() {
                    @Override
                    public void call(UserProfileBean userProfileBean) {
                        for (UserProfileBean.UinfosBean userInfo : userProfileBean.uinfos) {
                            mAccountId = userInfo.accid;
                            mNickName = userInfo.name;
                            // 只取第一组数据
                            break;
                        }
                        tvId.setText(mAccountId);
                        tvNickName.setText(mNickName);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.show("Get user info error");
                    }
                });
    }
}

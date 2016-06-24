package com.lionelwei.together;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lionelwei.together.ui.adapter.MainViewPagerAdapter;
import com.lionelwei.together.ui.contacts.ContactsFragment;
import com.lionelwei.together.ui.home.HomeFragment;
import com.lionelwei.together.ui.mycenter.MyCenterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    private static final int INDEX_HOME = 0;
    private static final int INDEX_CONTACTS = 1;
    private static final int INDEX_MY_CENTER = 2;

    // useful?
    public static final String SAVE_STATE_KEY_CUR_INDEX = "cur_index";

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tab_home)
    RelativeLayout mHome;

    @BindView(R.id.tab_contacts)
    RelativeLayout mContacts;

    @BindView(R.id.tab_my_center)
    RelativeLayout mMyCenter;

    // fragment相关
    private static int mCurrIndex = -1;
    private FragmentManager mFragmentManager;
    private String[] mFragmentTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (mCurrIndex == -1) {
            mCurrIndex = INDEX_HOME;
        }
        if (savedInstanceState != null) {
            mCurrIndex = savedInstanceState.getInt(SAVE_STATE_KEY_CUR_INDEX);
        }
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STATE_KEY_CUR_INDEX, mCurrIndex);
    }

    private void initView() {
        initViewPager();
        changeStatusById(mCurrIndex);
//        notifyFragmentChanged();
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrIndex = position;
                changeStatusById(mCurrIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private String[] newFragmentTag() {
        String[] tags = new String[3];
        tags[INDEX_HOME] = HomeFragment.TAG_FRAGMENT;
        tags[INDEX_CONTACTS] = ContactsFragment.TAG_FRAGMENT;
        tags[INDEX_MY_CENTER] = MyCenterFragment.TAG_FRAGMENT;
        return tags;
    }

    private void initViewPager() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTags = newFragmentTag();
        Fragment[] fragments = new Fragment[3];
        fragments[0] = HomeFragment.newInstance();
        fragments[1] = ContactsFragment.newInstance();
        fragments[2] = MyCenterFragment.newInstance();
        MainViewPagerAdapter adapter =
                new MainViewPagerAdapter(mFragmentManager, mFragmentTags, fragments);
        mViewPager.setAdapter(adapter);
    }

    /**
     * index数据改变导致fragment显示状态变化
     */
    private void notifyFragmentChanged() {
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        Fragment fragment = mFragmentManager
                .findFragmentByTag(mFragmentTags[mCurrIndex]);
        if (fragment == null) {
            fragment = newFragment();
        }
        //隐藏所有其他不该显示的fragment
        for (String tag : mFragmentTags) {
            Fragment f = mFragmentManager.findFragmentByTag(tag);
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {//fragment如果已经添加到页面，就直接显示
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.activity_main_container, fragment,
                    mFragmentTags[mCurrIndex]);
        }
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    private Fragment newFragment() {
        switch (mCurrIndex) {
            case INDEX_HOME:
                return HomeFragment.newInstance();
            case INDEX_CONTACTS:
                return ContactsFragment.newInstance();
            case INDEX_MY_CENTER:
                return MyCenterFragment.newInstance();
            default:
                throw new IllegalStateException("MainActivity -> no such index:"
                        + mCurrIndex);
        }
    }

    public void changeStatusById(int index) {
        mHome.setSelected(false);
        mContacts.setSelected(false);
        mMyCenter.setSelected(false);
        mCurrIndex = index;
        switch (index) {
            case INDEX_HOME:
                mHome.setSelected(true);
                break;
            case INDEX_CONTACTS:
                mContacts.setSelected(true);
                break;
            case INDEX_MY_CENTER:
                mMyCenter.setSelected(true);
                break;
        }
    }

    @OnClick({R.id.tab_home, R.id.tab_contacts, R.id.tab_my_center})
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tab_home:
                mCurrIndex = INDEX_HOME;
                break;
            case R.id.tab_contacts:
                mCurrIndex = INDEX_CONTACTS;
                break;
            case R.id.tab_my_center:
                mCurrIndex = INDEX_MY_CENTER;
                break;
        }
        changeStatusById(mCurrIndex);
        mViewPager.setCurrentItem(mCurrIndex, false);
//        notifyFragmentChanged();
    }


}

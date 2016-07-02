package com.lionelwei.together.component.sessionlist;

import android.content.Context;

import com.lionelwei.together.common.helper.SessionHelper;
import com.lionelwei.together.common.util.TestUtil;
import com.lionelwei.together.config.AccountCache;
import com.lionelwei.together.interfaces.IAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * Created by Lionel on 2016/7/2.
 */
/*package*/ class SessionListPresenter {
    // 负责控制view的更新
    private ISessionListView mView;
    // 负责控制adapter数据的更新
    private IAdapter mAdapter;
    // 实际的数据
    private List<RecentContact> mRecentList;


    public SessionListPresenter(ISessionListView view, IAdapter adapter) {
        mView = view;
        mAdapter = adapter;
    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> messages) {
            int index;
            for (RecentContact msg : messages) {
                index = -1;
                for (int i = 0; i < mRecentList.size(); i++) {
                    if (msg.getContactId().equals(mRecentList.get(i).getContactId())
                            && msg.getSessionType() == (mRecentList.get(i).getSessionType())) {
                        index = i;
                        break;
                    }
                }
                // 如果在该消息在列表里面已经存在 则将其删除 然后再添加
                if (index >= 0) {
                    mRecentList.remove(index);
                }
                mRecentList.add(msg);
            }
            mAdapter.updateData(mRecentList);
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
        }
    };

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
        }
    };

    public void requestData() {
        mView.onLoadingData();
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

            @Override
            public void onResult(int code, List<RecentContact> recentList, Throwable exception) {
                if (code != ResponseCode.RES_SUCCESS || recentList == null) {
                    mView.onError();
                    return;
                }
                mRecentList = recentList;
                if (recentList.size() == 0) {
                    mView.onNoData();
                } else {
                    mView.onShowData();
                }
                mAdapter.updateData(mRecentList);
                // 此处如果是界面刚初始化，为了防止界面卡顿，可先在后台把需要显示的用户资料和群组资料在后台加载好，然后再刷新界面
                //
/*
                msgLoaded = true;
                if (isAdded()) {
                    onRecentContactsLoaded();
                }
*/
            }
        });


    }

    public void registerObserver() {
        regObserver(true);
    }

    public void unRegisterObserver() {
        regObserver(false);
    }

    public void startChat(Context context, String contactId) {
        String id = contactId;
        if (id == null) {
            String account = AccountCache.getAccount();
            // 聊天对象
            id = TestUtil.TEST_ACCOUNT_1.equals(account)
                    ? TestUtil.TEST_ACCOUNT_2
                    : TestUtil.TEST_ACCOUNT_1;
        }
        SessionHelper.startP2PSession(context, id);
    }

    private void regObserver(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);
    }
}

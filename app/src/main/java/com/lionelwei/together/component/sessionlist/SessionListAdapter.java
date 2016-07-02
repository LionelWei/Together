package com.lionelwei.together.component.sessionlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lionelwei.together.R;
import com.lionelwei.together.interfaces.IAdapter;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lionel on 2016/7/2.
 */
public class SessionListAdapter
        extends RecyclerView.Adapter<SessionListAdapter.SessionListHolder>
        implements IAdapter<SessionListPresenter, RecentContact> {

    private Context mContext;
    private SessionListPresenter mPresenter;

    private List<RecentContact> mItems;

    public SessionListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void setPresenter(SessionListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public SessionListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SessionListHolder(LayoutInflater
                .from(mContext).inflate(
                        R.layout.session_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SessionListHolder holder, int position) {
        RecentContact contact = mItems.get(position);
        holder.nickname.setText(contact.getFromNick());
        Drawable testDrawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
        holder.avatar.setImageDrawable(testDrawable);

        int unReadCount = contact.getUnreadCount();
        if (unReadCount > 0) {
            holder.unReadTip.setVisibility(View.VISIBLE);
            holder.unReadTip.setText(String.valueOf(unReadCount));
        } else {
            holder.unReadTip.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void updateData(List<RecentContact> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    class SessionListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nickname)
        TextView nickname;

        @BindView(R.id.img_head)
        HeadImageView avatar;

        @BindView(R.id.unread_number_tip)
        TextView unReadTip;

        @OnClick(R.id.item_layout)
        void click() {
            int position = SessionListHolder.this.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String contactId = mItems.get(position).getContactId();
                mPresenter.startChat(mContext, contactId);
            }
        }

        public SessionListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

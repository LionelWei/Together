package com.lionelwei.together.component.contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lionelwei.together.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    public static final String TAG_FRAGMENT = "ContactsFragment";
    @BindView(R.id.add_contact)
    TextView mAddContact;
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerview;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactsFragment.
     */
    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.add_contact, R.id.id_recyclerview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_contact:
                break;
            case R.id.id_recyclerview:
                break;
        }
    }
}

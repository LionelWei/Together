package com.lionelwei.together.component.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lionelwei.together.R;

public class AddFriendActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddFriendActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }
}

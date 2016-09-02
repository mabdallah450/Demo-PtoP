package com.example.axel.ptop;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActivity extends AppCompatActivity {
    RelativeLayout contentFrame;
    @BindView(R.id.left_drawer)RelativeLayout leftDrawer;
    @BindView(R.id.drawer_layout)DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        contentFrame= (RelativeLayout) findViewById(R.id.content_frame);
        contentFrame.addView(LayoutInflater.from(this).inflate(layoutResID,null));

        ButterKnife.bind(this);
    }
    @OnClick(R.id.menuIv)void openMenu(){
        drawerLayout.openDrawer(leftDrawer);
    }
}

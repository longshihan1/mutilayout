package com.longshihan.testlh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.longshihan.lh.ui.StatusLayout;

public class MainActivity extends AppCompatActivity {
    //StatusLayoutManager mStatusLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        StatusLayout layout;
        View view = LayoutInflater.from(this).inflate(R.layout.emptylayout, null);
        //layout.setLayoutConfig();
       /* View view = LayoutInflater.from(this).inflate(R.layout.emptylayout, null);
        mStatusLayoutManager = StatusLayoutManager.newBuilder(this).setEmptyview(view).build();
        mStatusLayoutManager.showEmptyView();*/
    }
}

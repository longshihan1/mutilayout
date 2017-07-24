package com.longshihan.testlh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.longshihan.lh.ui.CustomStateOptions;
import com.longshihan.lh.ui.StatusLayout;

public class MainActivity extends AppCompatActivity {
    //StatusLayoutManager mStatusLayoutManager;

    StatusLayout mStatusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusLayout = (StatusLayout) findViewById(R.id.statuslayout);
        View view = LayoutInflater.from(this).inflate(R.layout.emptylayout, null);
        mStatusLayout.showView(new CustomStateOptions()
                .image(R.mipmap.ic_launcher)
                .buttonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "测试", Toast.LENGTH_SHORT).show();
                    }
                })
                .error());
        mStatusLayout.showEmptyView();
    }
}

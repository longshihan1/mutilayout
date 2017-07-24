package com.longshihan.testlh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.longshihan.lh.ui.*;
import com.longshihan.lh.ui.BuildConfig;

public class MainActivity extends AppCompatActivity {
    //StatusLayoutManager mStatusLayoutManager;

    StatusLayout mStatusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusLayout = (StatusLayout) findViewById(R.id.statuslayout);
        ImageView imageView = (ImageView) mStatusLayout.findViewById(R.id.imageempty);
        View view = LayoutInflater.from(this).inflate(R.layout.emptylayout, null);
        mStatusLayout.setLayoutConfig(new BuildConfig(this)
                                              .setPosition(true)
                                              .setPositionView(imageView));
        mStatusLayout.showView(new CustomStateOptions()
                                       .image(R.mipmap.ic_launcher)
                                       .buttonClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Toast.makeText(MainActivity.this, "测试", Toast.LENGTH_SHORT).show();
                                           }
                                       })
                                       .error());
        mStatusLayout.showGuildeView(new CustomGuildeOptions(this)
                                             .appendView(R.layout.test1, R.id.testnum)
                                             .appendView(R.layout.test2)
                                             .appendView(R.layout.test3)
                                    );
        mStatusLayout.showErrorView();
    }
}

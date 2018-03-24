package com.hongyan.xcj.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.main.info.recommend.ScrollBannerView;

import java.util.ArrayList;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        ScrollBannerView scrollBannerView = findViewById(R.id.myheaderview);
        scrollBannerView.setData(initData());
        scrollBannerView.setOnPageClickListener(new ScrollBannerView.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                Toast.makeText(TestActivity.this, "position---" + position, 100).show();
            }
        });
    }


    private ArrayList<ScrollBannerView.Entity> initData() {
        ArrayList<ScrollBannerView.Entity> list = new ArrayList<ScrollBannerView.Entity>();
        ScrollBannerView.Entity Entity1 = new ScrollBannerView.Entity();
        Entity1.setImageUrl("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");
        Entity1.setTitle("AAA");
        list.add(Entity1);
        ScrollBannerView.Entity Entity2 = new ScrollBannerView.Entity();
        Entity2.setImageUrl("http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg");
        Entity2.setTitle("BBB");
        list.add(Entity2);
        return list;
    }


}
